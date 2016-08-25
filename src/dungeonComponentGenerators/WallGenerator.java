package dungeonComponentGenerators;

import gameLogicComponents.Cell;
import interfaces.IWallGenerator;

public class WallGenerator implements IWallGenerator{

	@Override
	public void generateWalls(Cell[][] cells) {
		for(int i = 0; i<cells[0].length; i++){
			cells[0][i] = Cell.WALL;
			cells[cells.length-1][i] = Cell.WALL;
		}
		for(int row=1; row<cells.length-1; row++){
			// Every row is bordered by a wall on either side
			cells[row][0] = Cell.WALL;
			cells[row][cells[row].length-1] = Cell.WALL;
			for(int col=1;col<cells[0].length-1; col++){
				if(cells[row][col] == Cell.STONE){
					//Stone Cells that border rooms are turned into walls.
					if(cells[row+1][col] == Cell.ROOM ||
							cells[row-1][col] == Cell.ROOM ||
							cells[row][col+1] == Cell.ROOM ||
							cells[row][col-1] == Cell.ROOM ||
							cells[row+1][col+1] == Cell.ROOM ||
							cells[row-1][col-1] == Cell.ROOM ||
							cells[row+1][col-1] == Cell.ROOM ||
							cells[row-1][col+1] == Cell.ROOM){
						cells[row][col] = Cell.WALL;
					}
				}
			}
		}
	}

}
