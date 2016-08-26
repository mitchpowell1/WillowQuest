package factories;

import gameLogicComponents.Cell;
import gameLogicComponents.Dungeon;
import gameSettingComponents.DungeonDensity;
import gameSettingComponents.DungeonSize;
import gameSettingComponents.MonsterLevels;
import gameSettingComponents.TreasureSettings;
import interfaces.IRoomGenerator;
import interfaces.ITreasureGenerator;
import interfaces.IWallGenerator;
import interfaces.ICorridorGenerator;
import interfaces.IMonsterGenerator;

public class DungeonFactory {
	private IRoomGenerator roomGenerator;
	private ICorridorGenerator corridorGenerator;
	private IMonsterGenerator monsterGenerator;
	private ITreasureGenerator treasureGenerator;
	private IWallGenerator wallGenerator;
	private MonsterLevels monsLevel;
	private DungeonDensity densityLevel;
	private DungeonSize dungeonSize;
	private TreasureSettings treasureSet;
	private int roomDensity;
	private int dungeonWidth;
	private int dungeonHeight;
	private double monsterRoomProb;
	private double monsterCorrProb;
	

	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(IRoomGenerator roomGen, ICorridorGenerator corGen, 
		IMonsterGenerator monsterGenerator, IWallGenerator wallGenerator, 
		ITreasureGenerator treasureGenerator) {
		this.roomGenerator = roomGen;
		this.corridorGenerator = corGen;
		this.monsterGenerator = monsterGenerator;
		this.treasureGenerator = treasureGenerator;
		this.wallGenerator = wallGenerator;
		this.monsLevel = MonsterLevels.MEDIUM;
		this.densityLevel = DungeonDensity.MEDIUM;
		this.dungeonSize = DungeonSize.MEDIUM;
		this.treasureSet = TreasureSettings.PIRATE;
		setSize(dungeonSize);
		setRoomDensity(DungeonDensity.MEDIUM);
		setMonsterProb(MonsterLevels.MEDIUM);
		


	}
	
	/**
	 * Set the size of the dungeon
	 * @param size
	 */
	public void setSize(DungeonSize size) {
		this.dungeonSize = size;
		setDungeonHeight(size.getRows());
		setDungeonWidth(size.getCols());
	}
	
	/**
	 * Get the size of the dungeon
	 * @return the size of the dungeon
	 */
	public DungeonSize getDungeonSize(){
		return this.dungeonSize;
	}

	/**
	 * Get the current monster level of the dungeon
	 * @return the monster level of the dungeon
	 */
	public MonsterLevels getMonsterLevel(){
		return this.monsLevel;
	}
	
	/**
	 * Get the current room density setting for the dungeon
	 * @return current room density
	 */
	public DungeonDensity getRoomDensity(){
		return this.densityLevel;
	}
	
	/**
	 * Get the current treasure setting for the dungeon factory
	 * @return the dungeon factory's treasure settings.
	 */
	public TreasureSettings getTreasureSettings(){
		return this.treasureSet;
	}

	/***
	 * Sets the probabilities of monsters occurring in rooms and corridors.
	 * @param lev the level of monsters.
	 */
	public void setMonsterProb(MonsterLevels lev){
		this.monsLevel = lev;
		this.monsterCorrProb = lev.getCorrProb();
		this.monsterRoomProb = lev.getRoomProb();
	}
	
	/**
	 * Update the treasure settings
	 * @param treas the new treasure settings.
	 */
	public void setTreasureSettings(TreasureSettings treas){
		this.treasureSet = treas;
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
		addTreasure(cells);
		return new Dungeon(cells);
	}

	/**
	 * Calls the treasure generator to add treasure to the dungeon.
	 * @param cells
	 */
	private void addTreasure(Cell[][] cells) {
		this.treasureGenerator.generateTreasure(cells, treasureSet);
	}

	/**
	 * Generate dungeon corridors.
	 * @param cells
	 */
	private void addCorridors(Cell[][] cells) {
		this.corridorGenerator.generateCorridors(cells);	
	}

	/***
	 * Adds wall blocks to border the perimeter of the dungeon and border rooms.
	 * @param cells the array of cells to have walls added.
	 */
	private void addWalls(Cell[][] cells) {
		wallGenerator.generateWalls(cells);
	}

	/***
	 * Adjusts the density of the dungeon based on it's area.
	 * 
	 * @param newDensity
	 */
	public void setRoomDensity(DungeonDensity dens) {
		this.densityLevel = dens;
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
	 * Uses the room generator object to the dungeon.
	 * 
	 * @param cellArray
	 *            the array of cells to add rooms to.
	 */
	private void addRooms(Cell[][] cellArray) {
		this.roomGenerator.generateRooms(cellArray, this.roomDensity);
	}
}
