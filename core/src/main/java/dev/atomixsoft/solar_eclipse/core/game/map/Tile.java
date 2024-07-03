package dev.atomixsoft.solar_eclipse.core.game.map;

import dev.atomixsoft.solar_eclipse.core.game.Constants;

import java.util.Objects;

public class Tile {

    public int textureId;
    public int textureX, textureY;

    public byte type;
    public int x, y;
    public boolean roof;

    public Tile() {
        this.type = Constants.TILE_TYPE_WALKABLE;
        this.textureId = 1;
        this.textureX = this.textureY = 0;

        this.x = this.y = 0;
        this.roof = false;
    }

    public Tile(Tile tile) {
        this.type = tile.type;

        this.textureId = tile.textureId;
        this.textureX = tile.textureX;
        this.textureY = tile.textureY;

        this.x = tile.x;
        this.y = tile.y;
        this.roof = tile.roof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return textureId == tile.textureId && textureX == tile.textureX && textureY == tile.textureY && x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textureId, textureX, textureY, x, y);
    }
}
