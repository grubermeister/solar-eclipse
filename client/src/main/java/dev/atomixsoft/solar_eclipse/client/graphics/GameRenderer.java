package dev.atomixsoft.solar_eclipse.client.graphics;

import dev.atomixsoft.solar_eclipse.client.AssetLoader;
import dev.atomixsoft.solar_eclipse.client.graphics.render2D.Sprite;
import dev.atomixsoft.solar_eclipse.client.graphics.render2D.SpriteBatch;

import dev.atomixsoft.solar_eclipse.core.game.Actuator;
import dev.atomixsoft.solar_eclipse.core.game.Constants;
import dev.atomixsoft.solar_eclipse.core.game.map.GameMap;
import dev.atomixsoft.solar_eclipse.core.game.map.Tile;


public class GameRenderer {
    private static final int SPRITE_CELL_SIZE = 32;

    private GameMap m_Map;

    public GameRenderer() {

    }

    public GameRenderer(GameMap map) {
        m_Map = map;
    }

    public void render(SpriteBatch batch) {
        if(m_Map == null) return;
        int numLayers = Math.min(m_Map.TileMap.keySet().size(), Constants.MAX_MAP_LAYERS);

        for(var layer = 0; layer < numLayers; ++layer) {
            for(var x = 0; x < m_Map.width; ++x) {
                for(var y = 0; y < m_Map.height; ++y) {
                    Tile currTile = Actuator.GetTileFromMap(m_Map, layer, x, y);
                    if(currTile == null) continue;

                    Sprite sprite = new Sprite(AssetLoader.GetTexture("tileset" + currTile.textureId));
                    sprite.setCellPos(currTile.textureX * SPRITE_CELL_SIZE, currTile.textureY * SPRITE_CELL_SIZE);
                    sprite.setCellSize(SPRITE_CELL_SIZE, SPRITE_CELL_SIZE);

                    sprite.setPosition(x * 32, y * 32, 0);
                    sprite.setSize(32, 32);

                    sprite.draw(batch);
                }
            }
        }
    }

    public void setMap(GameMap newMap) {
        this.m_Map = newMap;
    }

    public GameMap getMap() {
        return m_Map;
    }

}
