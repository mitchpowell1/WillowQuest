package dungeonComponentGenerators;

import java.util.LinkedList;
import java.util.Queue;

import gameLogicComponents.Cell;
import interfaces.ICompareableCoord;
import interfaces.ICoordFactory;
import interfaces.IDoorGenerator;

public class DoorGenerator implements IDoorGenerator {
	private ICoordFactory coordFact;
	private Cell[][] cells;
	private boolean[][] connected;
	private int dungeonHeight;
	private int dungeonWidth;
	private ICompareableCoord entrance;

	public DoorGenerator(ICoordFactory coordFact) {
		this.coordFact = coordFact;
	}

	/***
	 * After the main corridor has been connected to the entrance,
	 * this function iterates through all of the connected cells, sees if they have
	 * neighbors that could be doors, and then creates doors, connecting
	 * the rooms that the doors attach to in the process.
	 */
	private void fillRemaining() {
		for(int row=0; row<dungeonHeight; row++){
			for(int col=0;col<dungeonWidth; col++){
				if(connected[row][col]){
					ICompareableCoord east = coordFact.getCoord(row, col+1);
					ICompareableCoord west = coordFact.getCoord(row, col-1);
					ICompareableCoord north = coordFact.getCoord(row-1, col);
					ICompareableCoord south = coordFact.getCoord(row+1, col);
					if(potentialDoorLocation(east)){
						ICompareableCoord twoEast = coordFact.getCoord(row, col+2);
						if(isConnectable(twoEast)){
							cells[row][col+1] = Cell.DOOR;
								floodFill(twoEast);
							}
					}
					if(potentialDoorLocation(west)){
						ICompareableCoord twoWest = coordFact.getCoord(row, col-2);
						if(isConnectable(twoWest)){
							cells[row][col-1] = Cell.DOOR;
							floodFill(twoWest);
						}
					}
					if(potentialDoorLocation(south)){
						ICompareableCoord twoSouth = coordFact.getCoord(row+2, col);
						if(isConnectable(twoSouth)){
							cells[row+1][col] = Cell.DOOR;
							floodFill(twoSouth);
						}
					}
					if(potentialDoorLocation(north)){
						ICompareableCoord twoNorth = coordFact.getCoord(row-2, col);
						if(isConnectable(twoNorth)){
							cells[row-1][col] = Cell.DOOR;
							floodFill(twoNorth);
						}
					}
				}
			}
		}
		
	}

	/***
	 * Finds cells that can be connected to and then connects them.
	 * Works just like the paint bucket from Microsoft paint!
	 * @param startCell the cell to begin the flood fill on.
	 */
	private void floodFill(ICompareableCoord startCell) {
		Queue<ICompareableCoord> fillQueue = new LinkedList<ICompareableCoord>();
		fillQueue.add(startCell);
		// While the fill queue is not empty
		while (!fillQueue.isEmpty()) {
			// Dequeue a cell from the queue
			ICompareableCoord currentCell = fillQueue.remove();
			int currentRow = currentCell.getRow();
			int currentCol = currentCell.getCol();
			// Create east and and west markers starting at the position of the
			// current cell
			ICompareableCoord east = coordFact.getCoord(currentRow, currentCol);
			ICompareableCoord west = coordFact.getCoord(currentRow, currentCol);
			// Move the west marker west until it is over a non-connectable
			// cell.
			// If the currentcell is not on the western border
			// Move the column west until it is over a cell that can not be
			// connected
			if(!isWesternBorder(west)){
				west.setCol(west.getCol()-1);
			}
			while (isConnectable(west)) {
				west.setCol(west.getCol() - 1);
			}
			// If the currentCell is not on the eastern border of the dungeon
			// Move the current cell east until it is over a non-connectable
			// cell
			if(!isEasternBorder(east)){
				east.setCol(east.getCol()+1);
			}
			while (isConnectable(east)) {
				east.setCol(east.getCol() + 1);
			}

			// For every cell in between the western column, and the eastern
			// column
			for (int i = west.getCol(); i <= east.getCol(); i++) {
				// Mark the cell as connected
				if (isConnectable(coordFact.getCoord(currentRow, i))) {
					connected[currentRow][i] = true;
				}
				ICompareableCoord north = coordFact.getCoord(currentRow - 1, i);
				ICompareableCoord south = coordFact.getCoord(currentRow + 1, i);
				// If the current cell is not on the northern border of the
				// dungeon
				// and the cell to the north of it is connectable
				if (!isNorthernBorder(currentCell) && isConnectable(north)
						&& (cells[currentRow][i] == Cell.CORRIDOR || cells[currentRow][i] ==Cell.ROOM
						|| cells[currentRow][i] == Cell.ENTRANCE || cells[currentRow][i] == Cell.EXIT)) {
					// add the cell to the north
					fillQueue.add(north);
				}
				// If the current cell is not on the southern border of the
				// dungeon
				// and the cell to the south of it is connectable
				if (!isSouthernBorder(currentCell) && isConnectable(south) &&
						(cells[currentRow][i] == Cell.CORRIDOR || cells[currentRow][i] ==Cell.ROOM
								|| cells[currentRow][i] == Cell.ENTRANCE || cells[currentRow][i] == Cell.EXIT)) {
					// add the cell to the south to the fillQueue
					fillQueue.add(south);
				}
			}

		}
	}


	/***
	 * Instantiate the 2D connected array as false for every cell in the
	 * dungeon.
	 */
	private void initConnected() {
		connected = new boolean[dungeonHeight][dungeonWidth];
		for (int row = 0; row < dungeonHeight; row++) {
			for (int col = 0; col < dungeonWidth; col++) {
				if (cells[row][col] == Cell.ENTRANCE) {
					this.entrance = coordFact.getCoord(row, col);
				}
				connected[row][col] = false;
			}
		}
	}
	/***
	 * Evaluates a cell and returns whether or not the cell is a bordering wall
	 * of the dungeon
	 * 
	 * @param cell
	 *            the coordinate pair of the cell to check.
	 * @return true if the cell is a border cell, false otherwise.
	 */
	private boolean isBorderWall(ICompareableCoord cell) {
		return (isNorthernBorder(cell) || isSouthernBorder(cell) || isEasternBorder(cell) || isWesternBorder(cell));
	}

	/***
	 * Evaluates a cell and decides whether or not it is a connectable cell
	 * 
	 * @param cell
	 *            the cell whose connectability is being decided
	 * @return whether or not the cell is connectable.
	 */
	private boolean isConnectable(ICompareableCoord cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		// A cell is connectable if it is a room, a corridor, an entrance, or an
		// exit
		// and it has not already been connected.
		return ((cells[row][col] == Cell.ROOM || cells[row][col] == Cell.CORRIDOR
		// cells[row][col] == Cell.ENTRANCE ||
		/* cells[row][col] == Cell.EXIT */) && !connected[row][col]);
	}

	/***
	 * Checks if the cell is on the eastern border of the dungeon
	 * @param cell the cell to be checked
	 * @return whether or not the cell is on the southern border of the screen.
	 */
	private boolean isEasternBorder(ICompareableCoord cell) {
		return cell.getCol() == dungeonWidth - 1;
	}

	/***
	 * Checks if the cell is on the northern border of the dungeon
	 * @param cell the cell to be checked
	 * @return whether or not the cell is on the northern border
	 */
	private boolean isNorthernBorder(ICompareableCoord cell) {
		return cell.getRow() == 0;
	}

	/***
	 * Checks if the cell is on the southern border of the dungeon
	 * @param cell the cell to be checked.
	 * @return whether or not the cell is on the southern border of the screen.
	 */
	private boolean isSouthernBorder(ICompareableCoord cell) {
		return cell.getRow() == dungeonHeight - 1;
	}

	/***
	 * Checks if the cell is on the western border of the dungeon
	 * @param cell the cell to be checked
	 * @return whether or not the cell is on the western border of the screen.
	 */
	private boolean isWesternBorder(ICompareableCoord cell) {
		return cell.getCol() == 0;
	}

	@Override
	public void makeDoors(Cell[][] cells) {
		this.cells = cells;
		this.dungeonWidth = cells[0].length;
		this.dungeonHeight = cells.length;
		initConnected();
		floodFill(entrance);
		fillRemaining();
	}

	/***
	 * Method to determine whether or not a cell could be a potential door location
	 * @param cell
	 * @return
	 */
	private boolean potentialDoorLocation(ICompareableCoord cell){
		return(cells[cell.getRow()][cell.getCol()] == Cell.WALL
				&& !isBorderWall(cell));
	}
}
