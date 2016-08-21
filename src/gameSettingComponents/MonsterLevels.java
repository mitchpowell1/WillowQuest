package gameSettingComponents;

public enum MonsterLevels {
	LOW (.01, .001),
	MEDIUM (.02, .002),
	HIGH (.04, .004);
	
	private double roomProb;
	private double corrProb;
	MonsterLevels(double roomProb, double corrProb){
		this.roomProb = roomProb;
		this.corrProb = corrProb;
	}
	
	public double getRoomProb(){
		return this.roomProb;
	}
	
	public double getCorrProb(){
		return this.corrProb;
	}
}
