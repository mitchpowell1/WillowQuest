package gameLogicComponents;

import java.awt.Color;

/***
 * The Cell enumerable type is used to label the type of each cell in a dungeon
 * @author Mitch Powell
 *
 */
public enum Cell {
	CORRIDOR ('C', Color.WHITE),
	DOOR	('D', Color.YELLOW),
	ENTRANCE ('N', Color.BLUE),
	EXIT ('X', Color.CYAN),
	ROOM ('R', Color.LIGHT_GRAY),
	TRAP ('T', Color.RED),
	TREASURE ('G', Color.GREEN),
	WALL ('W', Color.BLACK);
	
	/**
	 * Setting some display variables for each of the different kinds of cells
	 * to avoid having a lot of switch-case statements in other classes.
	 */
	private char character;
	private Color color;
	
	/***
	 * Cell constructor method
	 * @param character character used to represent the cell.
	 * @param color color used to represent the cell.
	 */
	Cell(char character, Color color){
		this.character = character;
		this.color = color;
	}
	
	/***
	 * Returns the character associated with the cell for ASCII printing.
	 * @return the ASCII character for the cell.
	 */
	public char getChar(){
		return this.character;
	}
	
	/***
	 * Returns the color associated with the cell for graphical printing.
	 * @return  the color for the cell.
	 */
	public Color getColor(){
		return this.color;
	}
}
