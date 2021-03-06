package net.mcreator.pmtinfai.enums;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Direction;
import net.minecraft.client.resources.I18n;

public enum InputSide implements IStringSerializable {
    //Enum
    WEST("west"), EAST("east"), NORTH("north"), SOUTH("south"), NONE("none");

    //Variablen
    private final String name;

    /**
     * Konstruktor
     */
    InputSide(String name) {
        this.name = name;
    }

    /**
     * Wandelt das Enum in einen String
     *
     * @return Name des Enums
     */
    public String toString() {
        return this.getName();
    }

    /**
     * Gibt den Namen zur�ck
     *
     * @return Name des Enums
     */
    public String getName() {
        return this.name;
    }

    /**
     * Abfrage ob Enum Aktiv ist
     *
     * @return Aktiv wenn die InputSeite existiert
     */

    public String getTranslationName() {
        if(this==WEST){
            return I18n.format("enum.pmtinfai.west");
        }
        if(this==EAST){
            return I18n.format("enum.pmtinfai.east");
        }
        if(this==NORTH){
            return I18n.format("enum.pmtinfai.north");
        }
        if(this==SOUTH){
            return I18n.format("enum.pmtinfai.south");
        }
        if(this==NONE){
            return I18n.format("enum.pmtinfai.none");
        }
        return this.getName();
    }

    public boolean isActive() {
        return this != NONE;
    }

    /**
     * Wandelt die Direction in das Enum um
     *
     * @param d Direction zum Umwandeln
     * @return zugeh�riges Enum
     */
    public static InputSide GetEnum(Direction d) {
        if (d == Direction.EAST)
            return InputSide.EAST;
        if (d == Direction.WEST)
            return InputSide.WEST;
        if (d == Direction.NORTH)
            return InputSide.NORTH;
        if (d == Direction.SOUTH)
            return InputSide.SOUTH;
        return InputSide.NONE;
    }

    public Direction GetDirection() {
        if (this == InputSide.EAST)
            return Direction.EAST;
        if (this == InputSide.WEST)
            return Direction.WEST;
        if (this == InputSide.NORTH)
            return Direction.NORTH;
        if (this == InputSide.SOUTH)
            return Direction.SOUTH;
        return null;
    }

}
