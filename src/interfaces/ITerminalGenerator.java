package interfaces;

import gameLogicComponents.Cell;

/***
 * Defines a contract to add entrances and exits to a 2D array of cells.
 * @author Mitch Powell
 *
 */
public interface ITerminalGenerator {
	/***
	 * Generates an entrance and exit in a 2D array of cells.
	 * @param cells the cells to generate an entrance and exit in.
	 */
	public void generateTerminals(Cell[][] cells);
}
