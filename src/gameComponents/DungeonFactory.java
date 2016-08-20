package gameComponents;
public class DungeonFactory {
	private int roomDensity;
	
	/***
	 * Constructor method for a dungeon factory object.
	 */
	public DungeonFactory(){
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
		
		return new Dungeon(initDungeon(dungeonHeight,dungeonWidth));
	}
	
	/***
	 * Set the new room density parameters for dungeon generation.
	 * @param newDensity
	 */
	public void setroomDensity(int newDensity){
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
		return cells;
	}
}
