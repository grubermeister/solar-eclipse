package dev.atomixsoft.solar_eclipse.client.scene;

import java.util.ArrayList;
import java.util.List;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import dev.atomixsoft.solar_eclipse.core.game.Constants;

import dev.atomixsoft.solar_eclipse.client.util.input.Controller;

import dev.atomixsoft.solar_eclipse.client.AssetLoader;

import dev.atomixsoft.solar_eclipse.client.graphics.render2D.Sprite;
import dev.atomixsoft.solar_eclipse.client.graphics.render2D.SpriteBatch;
import dev.atomixsoft.solar_eclipse.client.graphics.cameras.OrthoCamera;
import dev.atomixsoft.solar_eclipse.client.audio.AudioMaster;
import dev.atomixsoft.solar_eclipse.client.audio.AudioSource;


public class TestScene extends SceneAdapter{
    private int sfxBuffer, mscBuffer;

    private List<Sprite> spriteList;
    private OrthoCamera camera;
    private SpriteBatch batch;
    private AudioSource mscSrc, sfxSource;
    private Controller input;


    @Override
    public void show() {
        spriteList = new ArrayList<>();
        input = new Controller();

        input.addBinding("music", GLFW_KEY_P);
        input.addBinding("sound", GLFW_KEY_SPACE);

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

        sfxBuffer = AudioMaster.LoadSound("sfxTest", "client/assets/sound/Decision2.wav");
        mscBuffer = AudioMaster.LoadMusic("mscTest", "client/assets/music/reddwarf.mid");

        sfxSource = new AudioSource();
        mscSrc = new AudioSource();

        sfxSource.setVolume(25);
        mscSrc.setVolume(10);
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

        if(input.justPressed("music"))
            mscSrc.start(mscBuffer);

        if(input.justPressed("sound"))
            sfxSource.start(sfxBuffer);

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
        mscSrc.dispose();

        if(spriteList != null) {
            spriteList.clear();
            spriteList = null;
        }

        if(batch != null)
            batch.dispose();
    }
}
