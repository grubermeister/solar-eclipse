package dev.atomixsoft.solar_eclipse.core.game.character;

import dev.atomixsoft.solar_eclipse.core.game.Constants;

import java.util.Objects;

public class Character {

    public final Inventory inventory = new Inventory();
    public final Stats stats = new Stats();

    public int textureId;
    public String name;

    public int x, y;
    public byte sex;
    public boolean player;

    public Character() {
        this.textureId = 1;
        this.name = "niL";

        this.x = this.y = 0;
        this.sex = Constants.SEX_MALE;
        this.player = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;
        return textureId == character.textureId && x == character.x && y == character.y && sex == character.sex && Objects.equals(inventory, character.inventory) && Objects.equals(stats, character.stats) && Objects.equals(name, character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory, stats, textureId, name, x, y, sex);
    }

}
