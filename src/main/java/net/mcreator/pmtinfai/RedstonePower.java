package net.mcreator.pmtinfai;

import net.minecraft.util.IStringSerializable;

public enum RedstonePower implements IStringSerializable {
	// Enums
	NONE("none"), ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), 
	ELEVEN("11"), TWELVE("12"), THIRTEEN("13"), FOURTEEN("14"), FIFTEEN("15");
	
	//Variablen
	private final String name;

	/**
	 * Konstruktor
	 */
	private RedstonePower(String name) {
		this.name = name;
	}

	/**
	 * Wandelt das Enum in einen String
	 * @return Name des Enums
	 */
	public String toString() {
		return this.getName();
	}
	
	/**
	 * Gibt den Namen zur�ck
	 * @return Name des Enums
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Abfrage ob Enum Aktiv ist
	 * @return Aktiv wenn der Redstonewert existiert und gr��er als 0 ist
	 */
	public boolean isActive() {
		return this != ZERO&& this != NONE;
	}

	/**
	 * Wandelt den Integer in das Enum um
	 * @param x RedstoneValue
	 * @return zugeh�riges Enum
	 */
	public static RedstonePower GetEnum(int x) {
		switch (x) {
			case 1 :
				return RedstonePower.ONE;
			case 2 :
				return RedstonePower.TWO;
			case 3 :
				return RedstonePower.THREE;
			case 4 :
				return RedstonePower.FOUR;
			case 5 :
				return RedstonePower.FIVE;
			case 6 :
				return RedstonePower.SIX;
			case 7 :
				return RedstonePower.SEVEN;
			case 8 :
				return RedstonePower.EIGHT;
			case 9 :
				return RedstonePower.NINE;
			case 10 :
				return RedstonePower.TEN;
			case 11 :
				return RedstonePower.ELEVEN;
			case 12 :
				return RedstonePower.TWELVE;
			case 13 :
				return RedstonePower.THIRTEEN;
			case 14 :
				return RedstonePower.FOURTEEN;
			case 15 :
				return RedstonePower.FIFTEEN;
			default :
				return RedstonePower.ZERO;
		}
	}
}
