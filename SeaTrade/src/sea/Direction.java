package sea;

// Fahrtrichtung des Schiffs 

//Alle enums bieten automatisch folgenden Methoden:
//String name() - Liefert der Enum-Wert als String
//int ordinal() - Liefert den Enum-Wert als Zahl
//EnumObj EnumName.valueOf(String name) - wandelt einen String in Enum-Wert um

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST,
	NONE
}
