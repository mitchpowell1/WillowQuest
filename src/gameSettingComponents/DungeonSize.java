package gameSettingComponents;

public enum DungeonSize {
	SMALL(50,50),
	MEDIUM(75,75),
	LARGE(100,100);
	
	private int rows;
	private int cols;
	
	DungeonSize(int rows,int cols){
		this.rows = rows;
		this.cols = cols;
	}
	
	public int getCols(){
		return this.cols;
	}
	
	public int getRows(){
		return this.rows;
	}
}
