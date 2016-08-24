package dungeonComponentGenerators;

import gameLogicComponents.Cell;
import interfaces.IDoorGenerator;

public class DoorGenerator implements IDoorGenerator{
	private Cell[][] cells;
	private boolean[][] connected;

	@Override
	public void makeDoors(Cell[][] cells) {
		this.cells = cells;
		initConnected();
	}
	
	private void initConnected(){
		for(int row=0; row<cells.length; row++){
			for(int col=0; col<cells[0].length; col++){
				connected[row][col] = false;
			}
		}
	}
	
	/***
	 * Evaluates a cell and decides whether or not it is a connectable cell
	 * @param cell the cell whose connectability is being decided
	 * @return whether or not the cell is connectable.
	 */
	private boolean isConnectable(Cell cell){
		return((cell == Cell.ROOM || 
				cell == Cell.CORRIDOR || 
				cell == Cell.ENTRANCE ||
				cell == Cell.EXIT));
	}

}
