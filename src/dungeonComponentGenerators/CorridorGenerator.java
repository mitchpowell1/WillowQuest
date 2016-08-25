package dungeonComponentGenerators;

import java.util.ArrayList;
import java.util.Random;

import gameLogicComponents.Cell;
import interfaces.ICompareableCoord;
import interfaces.ICoordFactory;
import interfaces.ICorridorGenerator;
import interfaces.IDoorGenerator;
import interfaces.ITerminalGenerator;

public class CorridorGenerator implements ICorridorGenerator {

	private Random randGen;
	private ICoordFactory coordFact;
	private IDoorGenerator doorGen;
	private ITerminalGenerator termGen;
	private Cell[][] cells;
	private boolean[][] visited;

	public CorridorGenerator(Random rand, ICoordFactory coordFact, IDoorGenerator doorGen, ITerminalGenerator termGen) {
		this.randGen = rand;
		this.coordFact = coordFact;
		this.doorGen = doorGen;
		this.termGen = termGen;
	}

	@Override
	public void generateCorridors(Cell[][] cells) {
		int dungeonWidth = cells[0].length;
		int dungeonHeight = cells.length;
		this.cells = cells;
		initVisited();
		for (int row = 1; row < dungeonHeight; row++) {
			for (int col = 1; col < dungeonWidth; col++) {
				if (!visited[row][col] && cells[row][col] != Cell.WALL) {
					generateMaze(row, col);
				}
			}
		}
		generateTerminals();
		System.out.println("Terminals Generated");
		generateDoors();
		System.out.println("Doors Generated");
		removeDeadEnds();
	}

	/***
	 * Iterates through all the cells, Identifying and then eliminating dead
	 * ends
	 */
	private void removeDeadEnds() {
		int dungeonHeight = cells.length;
		int dungeonWidth = cells[0].length;
		for (int row = 1; row < dungeonHeight - 1; row++) {
			for (int col = 1; col < dungeonWidth - 1; col++) {
				if (isDeadEnd(row, col)) {
					weedDeadEnd(row, col);
				}
			}
		}
	}

	/***
	 * Once a dead end has been identified, this method reduces the dead end
	 * corridor until it is no longer a dead end.
	 * 
	 * @param row
	 * @param col
	 */
	private void weedDeadEnd(int row, int col) {
		int newRow = row;
		int newCol = col;
		while (isDeadEnd(newRow, newCol)) {
			cells[newRow][newCol] = Cell.STONE;
			Cell north = cells[newRow - 1][newCol];
			Cell south = cells[newRow + 1][newCol];
			Cell east = cells[newRow][newCol + 1];
			if (north == Cell.CORRIDOR || north == Cell.DOOR) {
				newRow--;
			} else if (south == Cell.CORRIDOR || south == Cell.DOOR) {
				newRow++;
			} else if (east == Cell.CORRIDOR || east == Cell.DOOR) {
				newCol++;
			} else {
				newCol--;
			}
		}
	}

	/***
	 * Checks if a cell at a row and column index is a dead end
	 * 
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 * @return whether or not the cell is a dead end corridor.
	 */
	private boolean isDeadEnd(int row, int col) {
		if (cells[row][col] == Cell.CORRIDOR) {
			Cell[] neighbors = { cells[row - 1][col], cells[row + 1][col], cells[row][col + 1], cells[row][col - 1] };
			int usefulNeighbors = 0;
			for (Cell cell : neighbors) {
				if (cell == Cell.CORRIDOR || cell == Cell.DOOR || cell == Cell.ENTRANCE || cell == Cell.EXIT) {
					usefulNeighbors++;
				}
			}
			return usefulNeighbors < 2;

		}
		return false;
	}

	/***
	 * Use the door generator object to create some doors.
	 */
	private void generateDoors() {
		doorGen.makeDoors(cells);

	}

	/***
	 * Use the terminal generator to create some doors.
	 */
	private void generateTerminals() {
		termGen.generateTerminals(cells);
	}

	/***
	 * Generates a maze in between all of the rooms in the dungeon
	 * 
	 * @param row
	 * @param col
	 */
	private void generateMaze(int row, int col) {
		//Instantiate an array list of cell coordinates
		ArrayList<ICompareableCoord> cellList = new ArrayList<ICompareableCoord>();
		//Add the initial cell to the list of cells
		cellList.add(this.coordFact.getCoord(row, col));
		//Instantiate the current index in the cell list to 0 (the only element so far)
		int currentIndex = 0;
		//While cells are in the cell list
		while (cellList.size() > 0) {
			//Set the current row
			int curRow = cellList.get(currentIndex).getRow();
			//Set the current column
			int curCol = cellList.get(currentIndex).getCol();
			//Instantiate a list of visitable neighbors
			ArrayList<ICompareableCoord> visitableNeighbors = getVisitableNeighbors(curRow, curCol);
			//If the cell has visitable neighbors
			if (visitableNeighbors.size() > 0) {
				//Mark the current cell as a corridor
				cells[curRow][curCol] = Cell.CORRIDOR;
				//Pick one of the visitable neighbors to visit next
				ICompareableCoord visitNext = visitableNeighbors.get(this.randGen.nextInt(visitableNeighbors.size()));
				//cellList.add(this.coordFact.getCoord(curRow, curCol));
				//mark the current cell as visited
				visited[curRow][curCol] = true;
				//add the next cell to the list of visitable cells.
				cellList.add(visitNext);
				//select the next cell to visit from the cellList
				currentIndex = this.randGen.nextInt(cellList.size() - 1);
			//If the cell has no visitable neighbors
			} else {
				//remove the current cell from the list of cells;
				cellList.remove(currentIndex);
				//If there are still cells left in the list of cells
				if (cellList.size() > 0) {
					//Pick another one at random
					currentIndex = this.randGen.nextInt(cellList.size());
				}
			}
		}
	}

	/**
	 * Gets a list of a cells neighbors that are "visitable" e.g. Haven't been
	 * visited, are not walls, and whose neighbors have not been visited.
	 * 
	 * @param row
	 *            the row value of the cell to get the visitable neighbors of
	 * @param col
	 *            the column value of the cell to get the visitable neighbors of
	 * @return an array list of the coordinates of the visitable neighbors of
	 *         the cell.
	 */
	private ArrayList<ICompareableCoord> getVisitableNeighbors(int row, int col) {
		//Instantiate a new list of cell coordinates
		ArrayList<ICompareableCoord> visitableNeighbors = new ArrayList<ICompareableCoord>();
		//If the neighbor to the south hasn't been visited already, and is not a wall, and none of its
		//neighboring cells have been visited yet
		if (!visited[row + 1][col] && cells[row + 1][col] != Cell.WALL && hasNoVisitedNeighbors(row + 1, col)) {
			visitableNeighbors.add(coordFact.getCoord(row + 1, col));
		}
		if (!visited[row - 1][col] && cells[row - 1][col] != Cell.WALL && hasNoVisitedNeighbors(row - 1, col)) {
			visitableNeighbors.add(coordFact.getCoord(row - 1, col));
		}
		if (!visited[row][col + 1] && cells[row][col + 1] != Cell.WALL && hasNoVisitedNeighbors(row, col + 1)) {
			visitableNeighbors.add(coordFact.getCoord(row, col + 1));
		}
		if (!visited[row][col - 1] && cells[row][col - 1] != Cell.WALL && hasNoVisitedNeighbors(row, col - 1)) {
			visitableNeighbors.add(coordFact.getCoord(row, col - 1));
		}
		return visitableNeighbors;
	}

	/***
	 * Checks if the cell at (row,col) has neighbors that have been visited
	 * 
	 * @param row
	 *            the row value of the cell to check.
	 * @param col
	 *            the column value of the cell to check.
	 * @return whether or not the cell has visited neighbors
	 */
	private boolean hasNoVisitedNeighbors(int row, int col) {
		return !(visited[row - 1][col] || visited[row + 1][col] || visited[row][col - 1] || visited[row][col + 1]);
	}

	/***
	 * Creates a 2D array of visited states, initially marking all wall and room
	 * blocks as visited, and all others as unvisited.
	 * 
	 * @param cells
	 *            the 2D array of cells to compare against
	 * @return the 2D visited state array.
	 */
	private void initVisited() {
		visited = new boolean[cells.length][cells[0].length];
		for (int row = 0; row < visited.length; row++) {
			for (int col = 0; col < visited[0].length; col++) {
				if (cells[row][col] == Cell.ROOM) {
					visited[row][col] = true;
				} else {
					visited[row][col] = false;
				}
			}
		}
	}

}
