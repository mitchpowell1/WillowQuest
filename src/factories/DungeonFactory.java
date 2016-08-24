package factories;

import gameLogicComponents.Cell;
import gameLogicComponents.Dungeon;
import gameSettingComponents.DungeonDensity;
import gameSettingComponents.MonsterLevels;
import interfaces.IRoomGenerator;
import interfaces.ITerminalGenerator;
import interfaces.ICorridorGenerator;
import interfaces.IMonsterGenerator;

public class DungeonFactory {
	private IRoomGenerator roomGenerator;
	private ICorridorGenerator corridorGenerator;
	private IMonsterGenerator monsterGenerator;
	private ITerminalGenerator terminalGenerator;
	private int roomDensity;
	private int dungeonWidth;
	private int dungeonHeight;
	private double monsterRoomProb;
	private double monsterCorrProb;
	

	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(IRoomGenerator roomGen, ICorridorGenerator corGen, 
			IMonsterGenerator monsterGenerator, ITerminalGenerator termGen) {
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
	 * Uses the terminal generator object to add an entrance and exit to the dungeon
	 * 
	 * @param cellArray
	 *            the array of cells that an entrance and exit will be added to.
	 * @return
	 */
	private void addEntranceExit(Cell[][] cellArray) {
		terminalGenerator.generateTerminals(cellArray);
	}
	/***
	 * Uses the room generator object to the dungeon.
	 * 
	 * @param cellArray
	 *            the array of cells to add rooms to.
	 */
	private void addRooms(Cell[][] cellArray) {
		this.roomGenerator.generateRooms(cellArray, this.roomDensity);
	}
}
