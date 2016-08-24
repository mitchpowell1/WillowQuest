package dungeonComponentGenerators;

import java.util.ArrayList;
import java.util.Random;

import gameLogicComponents.Cell;
import interfaces.ICompareableCoord;
import interfaces.ICoordFactory;
import interfaces.ICorridorGenerator;

public class CorridorGenerator implements ICorridorGenerator {
	
	private Random randGen;
	private ICoordFactory coordFact;
	private Cell[][] cells;
	private boolean[][] visited;
	
	public CorridorGenerator(Random rand, ICoordFactory coordFact){
		this.randGen = rand;
		this.coordFact = coordFact;
	}

	@Override
	public void generateCorridors(Cell[][] cells) {
		this.cells = cells;
		initVisited();
		for(int row=1; row<this.cells.length-1; row++){
			for(int col=1; col<this.cells.length-1; col++){
				if(!visited[row][col] && cells[row][col]!= Cell.WALL){
					generateMaze(row,col);
				}
			}
		}
	}
	
	/***
	 * Generates a maze
	 * @param cells
	 * @param visited
	 * @param row
	 * @param col
	 */
	private void generateMaze(int row, int col) {
		//TODO: Fix the maze generation algorithm so that it does not generate a corridor
		// on every square.
/**		ArrayList<ICompareableCoord> cellList = new ArrayList<ICompareableCoord>();
		int currRow = row;
		int currCol = col;
		//While the list of cells to check has not been exhausted
		while(cellList.size()>0){
			// Get a list of the visitable neighbors of the cell at the current row and column
			ArrayList<ICompareableCoord> visitableNeighbors = getVisitableNeighbors(currRow,currCol);
			// if the cell has visitable neighbors
			if(visitableNeighbors.size() > 0){
				//Mark the current cell as a corridor
				cells[currRow][currCol] = Cell.CORRIDOR;
				//Mark the current cell as visited
				visited[currRow][currCol] = true;
				//Add this cell to the list of cells to be revisited
				cellList.add(coordFact.getCoord(currRow, currCol));
				currentCell
			}
			
		}**/
		
	 ArrayList<ICompareableCoord> cellList = new ArrayList<ICompareableCoord>();
		cellList.add(this.coordFact.getCoord(row, col));
		int currentIndex = 0;
		while(cellList.size() > 0){
			int curRow = cellList.get(currentIndex).getRow();
			int curCol = cellList.get(currentIndex).getCol();
			ArrayList<ICompareableCoord> visitableNeighbors = getVisitableNeighbors(curRow,curCol);
			if(visitableNeighbors.size() > 0){
				cells[curRow][curCol] = Cell.CORRIDOR;
				ICompareableCoord visitNext =
						visitableNeighbors.get(this.randGen.nextInt(visitableNeighbors.size()));
				cellList.add(this.coordFact.getCoord(curRow, curCol));
				visited[curRow][curCol] = true;
				cellList.add(visitNext);
				currentIndex = cellList.size()-1;
			} else {
				cellList.remove(currentIndex);
				if(cellList.size() > 0){
					currentIndex = this.randGen.nextInt(cellList.size());
				}
			}
		}
	}
	
	/**
	 * Gets a list of a cells neighbors that are "visitable" e.g.
	 * Haven't been visited, are not walls, and whose neighbors have not
	 * been visited.
	 * @param row the row value of the cell to get the visitable neighbors of
	 * @param col the column value of the cell to get the visitable neighbors of
	 * @return an array list of the coordinates of the visitable neighbors of the cell.
	 */
	private ArrayList<ICompareableCoord> getVisitableNeighbors(int row,int col){
		ArrayList<ICompareableCoord> visitableNeighbors = new ArrayList<ICompareableCoord>();
		if(!visited[row+1][col] && cells[row+1][col] != Cell.WALL
				&& hasNoVisitedNeighbors(row+1,col)){
			visitableNeighbors.add(coordFact.getCoord(row+1, col));
		}
		if(!visited[row-1][col] && cells[row-1][col] != Cell.WALL
				&& hasNoVisitedNeighbors(row-1,col)){
			visitableNeighbors.add(coordFact.getCoord(row-1, col));
		}
		if(!visited[row][col+1] && cells[row][col+1] != Cell.WALL
				&& hasNoVisitedNeighbors(row,col+1)){
			visitableNeighbors.add(coordFact.getCoord(row, col+1));
		}
		if(!visited[row][col-1] && cells[row][col-1] != Cell.WALL 
				&& hasNoVisitedNeighbors(row,col-1)){
			visitableNeighbors.add(coordFact.getCoord(row, col-1));
		}
		return visitableNeighbors;
	}
	
	/***
	 * Checks if the cell at (row,col) has neighbors that have been visited
	 * @param row the row value of the cell to check.
	 * @param col the column value of the cell to check.
	 * @return whether or not the cell has visited neighbors
	 */
	private boolean hasNoVisitedNeighbors(int row, int col){
		return !(visited[row-1][col] ||
				 visited[row+1][col] ||
				 visited[row][col-1] ||
				 visited[row][col+1]);
	}

	/***
	 * Creates a 2D array of visited states, initially marking all wall and room blocks as visited,
	 * and all others as unvisited.
	 * @param cells the 2D array of cells to compare against
	 * @return the 2D visited state array.
	 */
	private void initVisited(){
		visited = new boolean[cells.length][cells[0].length];
		for(int row=0; row<visited.length; row++){
			for(int col=0; col<visited[0].length; col++){
				if(cells[row][col] == Cell.ROOM){
					visited[row][col] = true;
				} else {
					visited[row][col] = false;
				}
			}
		}
	}

}
