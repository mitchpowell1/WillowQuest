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
		addEntranceExitCeiling(cells, Cell.ENTRANCE);
		addEntranceExitFloor(cells, Cell.EXIT);
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
	
}
