package gameComponents;
public class DungeonGenerator {
	private int width;
	private int height;
	private Dungeon dungeon;
	/***
	 * Constructor method for a dungeon generator object
	 * @param width
	 * @param height
	 */
	public DungeonGenerator(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
}
