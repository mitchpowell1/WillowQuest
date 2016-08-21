package gameSettingComponents;

public enum DungeonDensity {
	SPARSE (500),
	MEDIUM (100),
	DENSE (20);
	
	private int divFactor;
	DungeonDensity(int divFactor){
		this.divFactor = divFactor;
	}
	
	public int getDivFactor(){
		return this.divFactor;
	}
}
