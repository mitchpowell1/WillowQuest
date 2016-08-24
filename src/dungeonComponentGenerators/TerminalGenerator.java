package dungeonComponentGenerators;

import java.util.Random;

import gameLogicComponents.Cell;
import interfaces.ITerminalGenerator;

public class TerminalGenerator implements ITerminalGenerator {
	private Random randomGenerator;
	int dungeonWidth;
	int dungeonHeight;
	
	/***
	 * Terminal generator is passed a random number generator by constructor injection
	 * @param randGen the injected random number generator
	 */
	public TerminalGenerator(Random randGen){
		randomGenerator = randGen;
	}

	@Override
	public void generateTerminals(Cell[][] cells) {
		this.dungeonWidth = cells[0].length;
		this.dungeonHeight = cells.length;
		boolean ceilings = randomGenerator.nextBoolean();
		// If the entrance and exit are on the ceiling and floor
		if (ceilings) {
			// Decide the position of the entrance
			boolean enterOnCeiling = randomGenerator.nextBoolean();
			// If the entrance is on the ceiling
			if (enterOnCeiling) {
				addEntranceExitCeiling(cells,Cell.ENTRANCE);
				addEntranceExitFloor(cells,Cell.EXIT);
			} else {
				addEntranceExitCeiling(cells,Cell.EXIT);
				addEntranceExitFloor(cells,Cell.ENTRANCE);
			}
			// If the entrance and exit are on the left and right walls
		} else {
			boolean enterLeft = randomGenerator.nextBoolean();
			// If the entrance is on the left
			if (enterLeft) {
				// Place the entrances and exits appropriately.
				addEntranceExitLeft(cells, Cell.ENTRANCE);
				addEntranceExitRight(cells, Cell.EXIT);
			} else {
				addEntranceExitLeft(cells, Cell.EXIT);
				addEntranceExitRight(cells, Cell.ENTRANCE);
			}
		}

	}
	/***
	 * Adds an entrance or exit to the ceiling, making sure that the entrance or exit
	 * is connected to either a corridor or a room.
	 * @param cellArray the array of cells to be manipulated
	 * @param cell the type of cell to be added (Entrance or Exit).
	 */
	private void addEntranceExitCeiling(Cell[][] cellArray, Cell cell){
		int index = this.randomGenerator.nextInt(dungeonWidth);
		while(cellArray[1][index] != Cell.ROOM && cellArray[1][index] != Cell.CORRIDOR){
			index = this.randomGenerator.nextInt(dungeonWidth);
		}
		cellArray[0][index] = cell;
	}
	
	/***
	 * Adds either an entrance or exit to floor, making sure the added cell touches a room or corridor block.
	 * @param cellArray the array of cells to be manipulated
	 * @param cell the cell to be added.
	 */
	private void addEntranceExitFloor(Cell[][] cellArray, Cell cell){
		int index = this.randomGenerator.nextInt(dungeonWidth);
		while(cellArray[dungeonHeight-2][index] != Cell.ROOM && cellArray[cellArray.length-2][index] != Cell.CORRIDOR){
			index = this.randomGenerator.nextInt(dungeonWidth);
		}
		cellArray[dungeonHeight-1][index] = cell;
	}
	
	/***
	 * Adds an entrance or exit to the left wall, ensuring the entrance/exit touches a room or corridor.
	 * @param cellArray the array of cells to be manipulated
	 * @param cell the kind of cell to add.
	 */
	private void addEntranceExitLeft(Cell[][] cellArray, Cell cell){
		int index = this.randomGenerator.nextInt(dungeonHeight);
		while(cellArray[index][1] != Cell.ROOM && cellArray[index][1] != Cell.CORRIDOR){
			index = this.randomGenerator.nextInt(dungeonHeight);
		}
		cellArray[index][0] = cell;
	}
	
	/***
	 * Adds either an entrance or an exit to the array of cells.
	 * @param cellArray the array of cells to be manipulated
	 * @param cell the type of cell to add.
	 */
	private void addEntranceExitRight(Cell[][] cellArray, Cell cell){
		int index = this.randomGenerator.nextInt(dungeonHeight);
		while(cellArray[index][dungeonWidth-2] != Cell.ROOM && cellArray[index][dungeonWidth-2] !=Cell.CORRIDOR){
			index = this.randomGenerator.nextInt(dungeonHeight);
		}
		cellArray[index][dungeonWidth-1] = cell;

	}
}
