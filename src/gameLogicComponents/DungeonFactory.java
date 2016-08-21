package gameLogicComponents;

import java.util.ArrayList;
import java.util.Random;

import interfaces.IRoomDescriptionFactory;
import interfaces.ICollideableRoom;

public class DungeonFactory {
	private Random randomGenerator;
	private IRoomDescriptionFactory roomFactory;
	private int roomDensity;
	
	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(Random rand, IRoomDescriptionFactory roomFact){
		this.randomGenerator = rand;
		this.roomFactory = roomFact;
		this.roomDensity = 100;
		
	}
	
	/***
	 * Publicly exposed getDungeon method generates a random dungeon
	 * object based upon the dungeon factory's settings.
	 * @param dungeonHeight the height of the dungeon to be generated.
	 * @param dungeonWidth the width of the dungeon to be generated.  
	 * @return a new dungeon generated based upon the parameters of the dungeon
	 * factory.
	 */
	public Dungeon getDungeon(int dungeonHeight, int dungeonWidth){
		Cell[][] cells = initDungeon(dungeonHeight, dungeonWidth);
		addEntranceExit(cells);
		addRooms(cells,dungeonHeight,dungeonWidth);
		return new Dungeon(cells);
	}
	
	/***
	 * Set the new room density parameters for dungeon generation.
	 * @param newDensity
	 */
	public void setRoomDensity(int newDensity){
		this.roomDensity = newDensity;
	}
	
	
	/***
	 * Initializes an array of cells, each of which is of type WALL.
	 * @param height the height of the 2D cell array
	 * @param width the width of the 2D cell array
	 * @return the 2D cell array of WALL cells
	 */
	private Cell[][] initDungeon(int height, int width){
		Cell [][] cells = new Cell[height][width];
		//Iterate through every row and column and set the type to WALL
		for(int row=0; row<height; row++){
			for(int col=0; col<width; col++){
				cells[row][col] = Cell.WALL;
			}
		}
		return cells;
	}
	
	/***
	 * Adds an entrance and exit block to the dungeon. The Entrance and the
	 * will always be on opposing walls in order to prevent the navigation of the
	 * maze from being too short.
	 * @param cellArray the array of cells that an entrance and exit will be
	 * added to.
	 * @return
	 */
	private void addEntranceExit(Cell[][] cellArray){
		int numRows = cellArray.length;
		int numCols = cellArray[0].length;
		boolean ceilings = randomGenerator.nextBoolean();
		// If the entrance and exit are on the ceiling and floor
		if(ceilings){
			// Decide the position of the entrance
			boolean enterOnCeiling = randomGenerator.nextBoolean();
			int entrance = randomGenerator.nextInt(numCols);
			int exit = randomGenerator.nextInt(numCols);
			// If the entrance is on the ceiling
			if(enterOnCeiling){
				//Put the entrance in it's appropriate place at the top
				cellArray[0][entrance] = Cell.ENTRANCE;
				//Put the exit at it's appropriate place at the bottom
				cellArray[numRows-1][exit] = Cell.EXIT;
			// If the entrance is on the floor
			} else {
				cellArray[0][exit] = Cell.EXIT;
				cellArray[numRows-1][entrance] = Cell.ENTRANCE;
			}
		// If the entrance and exit are on the left and right walls
		} else {
			boolean enterLeft = randomGenerator.nextBoolean();
			int entrance = randomGenerator.nextInt(numRows);
			int exit = randomGenerator.nextInt(numRows);
			// If the entrance is on the left
			if(enterLeft){
				// Place the entrance at it's appropriate place in the leftmost column
				cellArray[entrance][0] = Cell.ENTRANCE;
				// Place the entrance at it's appropriate place at the rightmost column
				cellArray[exit][numCols-1] = Cell.EXIT;
			// If the entrance is on the right
			} else {
				cellArray[exit][0] = Cell.EXIT;
				cellArray[entrance][numCols-1] = Cell.ENTRANCE;
			}
		}
	}
	
	private void addRooms(Cell[][] cellArray, int width, int height){
		ArrayList<ICollideableRoom> rooms = new ArrayList<ICollideableRoom>();
		for(int attempt=0; attempt < this.roomDensity; attempt++){
			ICollideableRoom room = this.roomFactory.getRoom(width, height);
			boolean collision = false;
			for(int existingRoom = 0; existingRoom < rooms.size(); existingRoom++){
				if(room.collidesWith(rooms.get(existingRoom))){
					collision = true;
				}
			}
			if(!collision){
				rooms.add(room);
			}
		}
	}
}
