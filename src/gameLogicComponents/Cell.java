package gameLogicComponents;

import java.awt.Color;

/***
 * The Cell enumerable type is used to label the type of each cell in a dungeon
 * @author Mitch Powell
 *
 */
public enum Cell {
	CORRIDOR ("\u25A1", Color.WHITE),
	DOOR	("\u25EB", Color.YELLOW),
	ENTRANCE ("\u2193", Color.BLUE),
	EXIT ("\u2193", Color.CYAN),
	MONSTER("\u2639", Color.magenta),
	ROOM ("\u25A1", Color.LIGHT_GRAY),
	STONE ("\u25A0", Color.BLACK),
	TRAP ("T", Color.RED),
	TREASURE ("$", Color.GREEN),
	WALL ("\u25A0", Color.DARK_GRAY);
	
	/**
	 * Setting some display variables for each of the different kinds of cells
	 * to avoid having a lot of switch-case statements in other classes.
	 */
	private String string;
	private Color color;
	
	/***
	 * Cell constructor method
	 * @param character character used to represent the cell.
	 * @param color color used to represent the cell.
	 */
	Cell(String string, Color color){
		this.string = string;
		this.color = color;
	}
	
	/***
	 * Returns the color associated with the cell for graphical printing.
	 * @return  the color for the cell.
	 */
	public Color getColor(){
		return this.color;
	}
	
	/***
	 * Returns the string associated with the cell for ASCII printing.
	 * @return the ASCII character for the cell.
	 */
	public String getString(){
		return this.string;
	}
}
