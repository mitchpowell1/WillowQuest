package gameLogicComponents;

import interfaces.IRenderableDungeon;

/***
 * Dungeon object definition. A dungeon primarily houses an (m x n) array of cells.
 * Implements IRenderable interface for effective decoupling from the dungeon
 * renderer.
 * @author Mitch Powell
 *
 */
public class Dungeon implements IRenderableDungeon{
	
	private Cell [][] cells;
	private int width;
	private int height;
	
	/***
	 * The dungeon constructor class accepts a 2D array of Cells as input and
	 * sets the height and width attributes as a derivation of the size of the
	 * Cell array
	 * @param newCells
	 */
	public Dungeon(Cell [][] newCells){
		this.cells = newCells;
		this.width = newCells[0].length;
		this.height = newCells.length;
	}
	
	/***
	 * Accesser method for the width of the dungeon.
	 * @return the width of the dungeon in cells
	 */
	public int getWidth(){
		return this.width;
	}
	
	/***
	 * Accessor method for the height of the dungeon
	 * @return the height of the dungeon in cells.
	 */
	public int getHeight(){
		return this.height;
	}
	
	/***
	 * Accessor method for the dungeon's cell array.
	 * @return the array of cells in a dungeon.
	 */
	public Cell[][] getCells(){
		return this.cells;
	}
}
