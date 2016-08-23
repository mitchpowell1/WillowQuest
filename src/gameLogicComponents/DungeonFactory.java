package gameLogicComponents;

import java.util.ArrayList;
import java.util.Random;

import gameSettingComponents.DungeonDensity;
import gameSettingComponents.MonsterLevels;
import interfaces.IRoomDescriptionFactory;
import interfaces.IRoomGenerator;
import interfaces.ICollideableRoom;
import interfaces.ICorridorGenerator;
import interfaces.IMonsterGenerator;

public class DungeonFactory {
	private Random randomGenerator;
	private IRoomGenerator roomGenerator;
	private ICorridorGenerator corridorGenerator;
	private IMonsterGenerator monsterGenerator;
	private int roomDensity;
	private int dungeonWidth;
	private int dungeonHeight;
	private double monsterRoomProb;
	private double monsterCorrProb;
	

	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(Random rand, IRoomGenerator roomGen, ICorridorGenerator corGen, 
			IMonsterGenerator monsterGenerator) {
		this.randomGenerator = rand;
		this.roomGenerator = roomGen;
		this.corridorGenerator = corGen;
		this.monsterGenerator = monsterGenerator;
		this.dungeonWidth = 50;
		this.dungeonHeight = 50;
		setRoomDensity(DungeonDensity.MEDIUM);
		setMonsterProb(MonsterLevels.MEDIUM);


	}

	/***
	 * Sets the probabilities of monsters occurring in rooms and corridors.
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
		addWalls(cells);
		addCorridors(cells);
		addMonsters(cells);
		addEntranceExit(cells);
		return new Dungeon(cells);
	}

	private void addCorridors(Cell[][] cells) {
		this.corridorGenerator.generateCorridors(cells);	
	}

	/***
	 * Adds wall blocks to border the perimeter of the dungeon and border rooms.
	 * @param cells the array of cells to have walls added.
	 */
	private void addWalls(Cell[][] cells) {
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
		this.monsterGenerator.addMonsters(cellArray,
				this.monsterRoomProb, this.monsterCorrProb);
	}

	/***
	 * Initializes an array of cells, each of which is of type STONE.
	 * 
	 * @param height
	 *            the height of the 2D cell array
	 * @param width
	 *            the width of the 2D cell array
	 * @return the 2D cell array of STONE cells
	 */
	private Cell[][] initDungeon() {
		Cell[][] cells = new Cell[this.dungeonHeight][this.dungeonWidth];
		// Iterate through every row and column and set the type to STONE
		for (int row = 0; row < this.dungeonHeight; row++) {
			for (int col = 0; col < this.dungeonWidth; col++) {
				cells[row][col] = Cell.STONE;
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
			// If the entrance is on the ceiling
			if (enterOnCeiling) {
				addEntranceExitCeiling(cellArray,Cell.ENTRANCE);
				addEntranceExitFloor(cellArray,Cell.EXIT);
			} else {
				addEntranceExitCeiling(cellArray,Cell.EXIT);
				addEntranceExitFloor(cellArray,Cell.ENTRANCE);
			}
			// If the entrance and exit are on the left and right walls
		} else {
			boolean enterLeft = randomGenerator.nextBoolean();
			// If the entrance is on the left
			if (enterLeft) {
				// Place the entrances and exits appropriately.
				addEntranceExitLeft(cellArray, Cell.ENTRANCE);
				addEntranceExitRight(cellArray, Cell.EXIT);
			} else {
				addEntranceExitLeft(cellArray, Cell.EXIT);
				addEntranceExitRight(cellArray, Cell.ENTRANCE);
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
	/***
	 * Programmatically adds rooms to the array of cells based on the room
	 * density instance variable.
	 * 
	 * @param cellArray
	 *            the array of cells to add rooms to.
	 */
	private void addRooms(Cell[][] cellArray) {
		this.roomGenerator.generateRooms(cellArray, this.roomDensity);
	}
}
