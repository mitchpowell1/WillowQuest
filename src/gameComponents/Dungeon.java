package gameComponents;

public class Dungeon {
	
	private Cell [][] cells;
	private int width;
	private int height;
	
	public Dungeon(Cell [][] newCells){
		this.cells = newCells;
		this.width = newCells[0].length;
		this.height = newCells.length;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
}
