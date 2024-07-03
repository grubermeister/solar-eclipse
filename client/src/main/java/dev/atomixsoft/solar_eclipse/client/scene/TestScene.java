package dev.atomixsoft.solar_eclipse.client.scene;

import dev.atomixsoft.solar_eclipse.client.graphics.GameRenderer;
import dev.atomixsoft.solar_eclipse.core.game.Actuator;
import dev.atomixsoft.solar_eclipse.core.game.map.GameMap;
import dev.atomixsoft.solar_eclipse.core.game.map.Tile;
import org.joml.Vector3f;

import dev.atomixsoft.solar_eclipse.core.game.Constants;

import dev.atomixsoft.solar_eclipse.client.util.input.Controller;

import dev.atomixsoft.solar_eclipse.client.AssetLoader;

import dev.atomixsoft.solar_eclipse.client.graphics.render2D.SpriteBatch;
import dev.atomixsoft.solar_eclipse.client.graphics.cameras.OrthoCamera;

import static org.lwjgl.glfw.GLFW.*;


public class TestScene extends SceneAdapter{

    private OrthoCamera camera;
    private SpriteBatch batch;
    private Controller input;
    private GameRenderer mapRender;

    int mapSize = 10;

    @Override
    public void show() {
        input = new Controller();
        mapRender = new GameRenderer();

        input.addBinding("camUp", GLFW_KEY_UP);
        input.addBinding("camDown", GLFW_KEY_DOWN);
        input.addBinding("camLeft", GLFW_KEY_LEFT);
        input.addBinding("camRight", GLFW_KEY_RIGHT);

        camera = new OrthoCamera(800, 600);

        Vector3f pos = camera.getPosition();
        pos.x = (camera.getWidth() - 16) / camera.getAspectRatio();
        pos.y = (camera.getHeight() - 16) / camera.getAspectRatio();
        camera.setPosition(pos);

        AssetLoader.AddShader("basic", "basic");

        AssetLoader.AddTexture("tileset1", "tilesets/1.bmp");
        AssetLoader.AddTexture("tileset2", "tilesets/2.bmp");

        batch = new SpriteBatch(AssetLoader.GetShader("basic"));

        GameMap testMap = new GameMap(0, 0, 20, 20);

        Tile grassTile = new Tile();
        grassTile.textureId = 1;
        grassTile.textureX = 0;
        grassTile.textureY = 1;

        grassTile.type = Constants.TILE_TYPE_WALKABLE;
        grassTile.roof = false;

        Tile trunkTile = new Tile(grassTile);
        trunkTile.textureX = 4;
        trunkTile.textureY = 0;
        trunkTile.type = Constants.TILE_TYPE_BLOCKED;

        Actuator.FillMapLayer(testMap, grassTile, 0);

        Actuator.AddTileToMap(testMap, trunkTile, 1, 2, 2);
        Actuator.AddTileToMap(testMap, trunkTile, 1, 10, 8);
        Actuator.AddTileToMap(testMap, trunkTile, 1, 10, 13);

        mapRender.setMap(testMap);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void update(double dt) {
        Vector3f position = camera.getPosition();

        float cameraSpeed = 300; // Adjust this as needed

        // Example control: move the camera with arrow keys
        if (input.isPressed("camUp"))
            position.y += cameraSpeed * dt;
        else if (input.isPressed("camDown"))
            position.y -= cameraSpeed * dt;

        if (input.isPressed("camLeft"))
            position.x -= cameraSpeed * dt;
        else if (input.isPressed("camRight"))
            position.x += cameraSpeed * dt;

        camera.setPosition(position);
    }

    @Override
    public void render() {
        batch.begin(camera);

        mapRender.render(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        if(batch != null)
            batch.dispose();
    }
}
