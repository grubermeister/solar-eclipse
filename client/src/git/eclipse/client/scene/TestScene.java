package git.eclipse.client.scene;

import git.eclipse.client.AssetLoader;
import git.eclipse.client.audio.AudioMaster;
import git.eclipse.client.audio.AudioSource;
import git.eclipse.core.game.Constants;
import git.eclipse.client.graphics.render2D.Sprite;
import git.eclipse.client.graphics.cameras.OrthoCamera;
import git.eclipse.client.graphics.render2D.SpriteBatch;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class TestScene extends SceneAdapter {

    private List<Sprite> spriteList;
    private OrthoCamera camera;
    private SpriteBatch batch;

    private int sfxBuffer, mscBuffer;
    private AudioSource audioSource;

    @Override
    public void show() {
        spriteList = new ArrayList<>();
        camera = new OrthoCamera(600, 400);

        AssetLoader.AddShader("basic", "basic");

        AssetLoader.AddTexture("tileset1", "tilesets/1.bmp");
        AssetLoader.AddTexture("tileset2", "tilesets/2.bmp");

        for(int y = Constants.MAX_MAPY; y > -Constants.MAX_MAPY; --y) {
            for(int x = -Constants.MAX_MAPX; x < Constants.MAX_MAPX; ++x) {
                Sprite sprite = new Sprite(AssetLoader.GetTexture("tileset1"));
                sprite.setPosition(x * 32, y * 32, 0);
                sprite.setSize(32, 32);

                sprite.setCellPos(new Vector2f(0, 32));
                sprite.setCellSize(new Vector2f(32, 32));
                spriteList.add(sprite);
            }
        }

        Sprite sprite = new Sprite(AssetLoader.GetTexture("tileset2"));
        sprite.setSize(32, 32);
        sprite.setCellPos(new Vector2f(32, 32 * 6));
        sprite.setCellSize(new Vector2f(32, 32));

        spriteList.add(sprite);
        batch = new SpriteBatch(AssetLoader.GetShader("basic"));

        sfxBuffer = AudioMaster.LoadSound("sfxTest", "assets/sound/Decision1.wav");
        audioSource = new AudioSource();

        mscBuffer = AudioMaster.LoadMusic("mscTest", "assets/music/blackadder.mid");
        audioSource.play(mscBuffer);
        audioSource.setVolume(10);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void update(double dt) {
        float zoom = camera.getZoom();
        zoom += 0.5f * (float) dt;

        zoom = Math.clamp(1.0f, 1.75f, zoom);

        camera.setZoom(zoom);

        Sprite sprite = spriteList.get(spriteList.size()-1);
        Vector3f pos = sprite.getPosition();

        sprite.setPosition(pos.x, pos.y, pos.z);
    }

    @Override
    public void render() {
        batch.begin(camera);

        float width = camera.getWidth(), height = camera.getHeight();
        float aspectRatio = camera.getAspectRatio();
        float zoom = camera.getZoom();

        for(Sprite sprite : spriteList) {
            Vector2f pos = new Vector2f(sprite.getPosition().x, sprite.getPosition().y);

            if(pos.x - sprite.getSize().x / 2.0f > (width / aspectRatio) * zoom)
                continue;
            else if(pos.x + sprite.getSize().x / 2.0f < (-width / aspectRatio) * zoom)
                continue;

            if(pos.y - sprite.getSize().y / 2.0f > (height / aspectRatio) * zoom)
                continue;
            else if(pos.y + sprite.getSize().y / 2.0f < (-height / aspectRatio) * zoom)
                continue;


            sprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        int aspectRatio = 4 / 3;

        int w, h;
        if(width > height) {
            w = width * aspectRatio;
            h = height / aspectRatio;
        } else {
            w = width / aspectRatio;
            h = height * aspectRatio;
        }

        camera.resize(w, h);
    }

    @Override
    public void dispose() {
        audioSource.dispose();

        if(spriteList != null) {
            spriteList.clear();
            spriteList = null;
        }

        if(batch != null)
            batch.dispose();
    }

}
