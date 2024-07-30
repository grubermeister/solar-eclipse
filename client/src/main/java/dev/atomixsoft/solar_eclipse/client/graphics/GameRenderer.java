package dev.atomixsoft.solar_eclipse.client.graphics;

import dev.atomixsoft.solar_eclipse.client.AssetLoader;

import dev.atomixsoft.solar_eclipse.client.graphics.render2D.Sprite;
import dev.atomixsoft.solar_eclipse.client.graphics.render2D.SpriteBatch;
import dev.atomixsoft.solar_eclipse.core.game.Actuator;
import dev.atomixsoft.solar_eclipse.core.game.Constants;
import dev.atomixsoft.solar_eclipse.core.game.character.Character;
import dev.atomixsoft.solar_eclipse.core.game.map.GameMap;
import dev.atomixsoft.solar_eclipse.core.game.map.Tile;

import java.util.List;

/**
 * <p>Handles rendering for the visible Game World on the client side. (NPCs, PCs, Tiles, Resources, etc)</p>
 */
public class GameRenderer {
    private static final int SPRITE_CELL_SIZE = 32;

    private GameMap m_Map;
    public GameRenderer() { }

    public GameRenderer(GameMap map) {
        this();
        m_Map = map;
    }

    public void render(SpriteBatch batch) {
        if(m_Map == null) return;
        int numLayers = Math.min(m_Map.TileMap.keySet().size(), Constants.MAX_MAP_LAYERS);

        renderTiles(batch, numLayers, false);
        renderCharacters(batch, m_Map.MapCharacters);
        renderTiles(batch, numLayers, true);
    }

    private void renderTiles(SpriteBatch batch, int layers, boolean roofs) {
        for(var layer = 0; layer < layers; ++layer) {
            for(var x = 0; x < m_Map.width; ++x) {
                for(var y = 0; y < m_Map.height; ++y) {
                    Tile currTile = Actuator.GetTileFromMap(m_Map, layer, x, y);
                    if(currTile == null) continue;

                    // Roof Check
                    if(roofs && !currTile.roof) continue;
                    else if(!roofs && currTile.roof) continue;

                    // Creates sprite to render based on the Tile information
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

    private void renderCharacters(SpriteBatch batch, List<Character> charList) {
        for(var i = 0; i < charList.size(); ++i) {
            Character c = charList.get(i);
            if(c == null || c.removed) continue;

            int keyFrame = Math.max(0, Math.min(c.keyFrame, 3));
            Sprite sprite = new Sprite(AssetLoader.GetTexture("char" + c.textureId));

            float cellWidth = sprite.getTexture().getWidth() / 4.0f, cellHeight = sprite.getTexture().getHeight() / 4.0f;
            sprite.setCellSize(cellWidth, cellHeight);

            Character.Direction dir = c.facing;
            switch (dir) {
                case UP -> sprite.setCellPos(keyFrame * cellWidth, cellHeight * 3);
                case DOWN -> sprite.setCellPos(keyFrame * cellWidth, 0);
                case LEFT -> sprite.setCellPos(keyFrame * cellWidth, cellHeight);
                case RIGHT -> sprite.setCellPos(keyFrame * cellWidth, cellHeight * 2);
            }

            sprite.setPosition(c.x * cellWidth, c.y * cellHeight + cellHeight / 2, 0);
            sprite.setSize(cellWidth * 2, cellHeight * 2);

            sprite.draw(batch);
        }
    }

    public void setMap(GameMap newMap) {
        this.m_Map = newMap;
    }

    public GameMap getMap() {
        return m_Map;
    }

}
