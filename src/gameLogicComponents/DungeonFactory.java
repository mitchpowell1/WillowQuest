package gameLogicComponents;

import java.util.ArrayList;
import java.util.Random;

import gameSettingComponents.DungeonDensity;
import gameSettingComponents.MonsterLevels;
import interfaces.IRoomDescriptionFactory;
import interfaces.ICollideableRoom;

public class DungeonFactory {
	private Random randomGenerator;
	private IRoomDescriptionFactory roomFactory;
	private int roomDensity;
	private int dungeonWidth;
	private int dungeonHeight;
	private double monsterRoomProb;
	private double monsterCorrProb;

	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(Random rand, IRoomDescriptionFactory roomFact) {
		this.randomGenerator = rand;
		this.roomFactory = roomFact;
		this.dungeonWidth = 100;
		this.dungeonHeight = 100;
		setRoomDensity(DungeonDensity.MEDIUM);
		setMonsterProb(MonsterLevels.MEDIUM);


	}

	/***
	 * Sets the probabilities of monsters occuring in rooms and corridors.
	 * @param lev the level of monsters.
	 */
	public void setMonsterProb(MonsterLevels lev){
		this.monsterCorrProb = lev.getCorrProb();
		this.monsterRoomProb = lev.getRoomProb();
	}

	/***
	 * Publicly exposed getDungeon method generates a random dungeon object
	 * based upon the dungeon factory's settings.
	 * 
	 * @param dungeonHeight
	 *            the height of the dungeon to be generated.
	 * @param dungeonWidth
	 *            the width of the dungeon to be generated.
	 * @return a new dungeon generated based upon the parameters of the dungeon
	 *         factory.
	 */
	public Dungeon getDungeon() {
		Cell[][] cells = initDungeon();
		addRooms(cells);
		addEntranceExit(cells);
		addMonsters(cells);
		return new Dungeon(cells);
	}

	/***
	 * Adjusts the density of the dungeon based on it's area.
	 * 
	 * @param newDensity
	 */
	public void setRoomDensity(DungeonDensity dens) {
		this.roomDensity = (this.dungeonWidth * this.dungeonHeight) / dens.getDivFactor();
	}

	/***
	 * Adjusts the width of the dungeon.
	 * @param newWidth
	 */
	public void setDungeonWidth(int newWidth) {
		this.dungeonWidth = newWidth;
	}

	/***
	 * Adjusts the height of the dungeon
	 * @param newHeight
	 */
	public void setDungeonHeight(int newHeight) {
		this.dungeonHeight = newHeight;
	}
	
	/***
	 * Populate the dungeon with monsters based upon the dungeon factory's
	 * Monster level
	 * @param cellArray
	 */
	private void addMonsters(Cell[][] cellArray) {
		for(int row=0; row<cellArray.length; row++){
			for(int col=0; col<cellArray[0].length; col++){
				if(cellArray[row][col]== Cell.ROOM){
					if(this.randomGenerator.nextDouble() < this.monsterRoomProb){
						cellArray[row][col] = Cell.MONSTER;
					}
				} else if(cellArray[row][col] == Cell.CORRIDOR){
					if(this.randomGenerator.nextDouble() < this.monsterCorrProb){
						cellArray[row][col] = Cell.MONSTER;
					}
				}
			}
		}
	}

	/***
	 * Initializes an array of cells, each of which is of type WALL.
	 * 
	 * @param height
	 *            the height of the 2D cell array
	 * @param width
	 *            the width of the 2D cell array
	 * @return the 2D cell array of WALL cells
	 */
	private Cell[][] initDungeon() {
		Cell[][] cells = new Cell[this.dungeonHeight][this.dungeonWidth];
		// Iterate through every row and column and set the type to WALL
		for (int row = 0; row < this.dungeonHeight; row++) {
			for (int col = 0; col < this.dungeonWidth; col++) {
				cells[row][col] = Cell.WALL;
			}
		}
		return cells;
	}

	/***
	 * Adds an entrance and exit block to the dungeon. The Entrance and the will
	 * always be on opposing walls in order to prevent the navigation of the
	 * maze from being too short.
	 * 
	 * @param cellArray
	 *            the array of cells that an entrance and exit will be added to.
	 * @return
	 */
	private void addEntranceExit(Cell[][] cellArray) {
		int numRows = cellArray.length;
		int numCols = cellArray[0].length;
		boolean ceilings = randomGenerator.nextBoolean();
		// If the entrance and exit are on the ceiling and floor
		if (ceilings) {
			// Decide the position of the entrance
			boolean enterOnCeiling = randomGenerator.nextBoolean();
			int entrance = randomGenerator.nextInt(numCols);
			int exit = randomGenerator.nextInt(numCols);
			// If the entrance is on the ceiling
			if (enterOnCeiling) {
				// Put the entrance in it's appropriate place at the top
				cellArray[0][entrance] = Cell.ENTRANCE;
				// Put the exit at it's appropriate place at the bottom
				cellArray[numRows - 1][exit] = Cell.EXIT;
				// If the entrance is on the floor
			} else {
				cellArray[0][exit] = Cell.EXIT;
				cellArray[numRows - 1][entrance] = Cell.ENTRANCE;
			}
			// If the entrance and exit are on the left and right walls
		} else {
			boolean enterLeft = randomGenerator.nextBoolean();
			int entrance = randomGenerator.nextInt(numRows);
			int exit = randomGenerator.nextInt(numRows);
			// If the entrance is on the left
			if (enterLeft) {
				// Place the entrance at it's appropriate place in the leftmost
				// column
				cellArray[entrance][0] = Cell.ENTRANCE;
				// Place the entrance at it's appropriate place at the rightmost
				// column
				cellArray[exit][numCols - 1] = Cell.EXIT;
				// If the entrance is on the right
			} else {
				cellArray[exit][0] = Cell.EXIT;
				cellArray[entrance][numCols - 1] = Cell.ENTRANCE;
			}
		}
	}

	/***
	 * Programmatically adds rooms to the array of cells based on the room
	 * density instance variable.
	 * 
	 * @param cellArray
	 *            the array of cells to add rooms to.
	 * @param width
	 *            the width of the dungeon. Used by the room factory.
	 * @param height
	 *            the height of the dungeon. Used by the room factory.
	 */
	private void addRooms(Cell[][] cellArray) {
		ArrayList<ICollideableRoom> rooms = new ArrayList<ICollideableRoom>();

		// Generating non-colliding rooms
		for (int attempt = 0; attempt < this.roomDensity; attempt++) {
			// Fetch a new room from the room factory
			ICollideableRoom room = this.roomFactory.getRoom(this.dungeonWidth, this.dungeonHeight);
			// start with no collisions detected
			boolean collision = false;
			// iterate through every room in the existing arraylist of rooms
			for (int existingRoom = 0; existingRoom < rooms.size(); existingRoom++) {
				// If the newly generated room collides with an existing room,
				// update
				// the collision boolean
				if (room.collidesWith(rooms.get(existingRoom))) {
					collision = true;
				}
			}
			// Add the new room to the list of rooms if there has not been a
			// collision
			if (!collision) {
				rooms.add(room);
			}
		}
		// Next, update the cells in the cell array to reflect the positions of
		// all of the rooms
		for (ICollideableRoom room : rooms) {
			int topLeftX = room.getTopLeft().getX();
			int topLeftY = room.getTopLeft().getY();
			int bottomRightX = room.getBottomRight().getX();
			int bottomRightY = room.getBottomRight().getY();
			int roomHeight = bottomRightY - topLeftY;
			int roomWidth = bottomRightX - topLeftX;
			for (int i = 0; i < roomHeight; i++) {
				for (int j = 0; j < roomWidth; j++) {
					cellArray[topLeftY + i][topLeftX + j] = Cell.ROOM;
				}
			}
		}
	}
}
