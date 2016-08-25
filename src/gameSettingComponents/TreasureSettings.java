package gameSettingComponents;

public enum TreasureSettings {
	
	BEGGAR (.01),
	PIRATE (.02),
	KING (.04);
	
	private double treasureProb;
	
	TreasureSettings(double treasProb){
		this.treasureProb = treasProb;
	}
	
	public double getTreasureProb(){
		return treasureProb;
	}

}
