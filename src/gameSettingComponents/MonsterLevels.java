package gameSettingComponents;

/***
 * Determines the probability of a given cell in a room or corridor containing a monster.
 * Implemented to avoid passing around "Magic Numbers"
 * @author mitch
 *
 */
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
	
	/***
	 * Get the probability that a room block will have a monster.
	 * @return room block probability level
	 */
	public double getRoomProb(){
		return this.roomProb;
	}
	
	/***
	 * Get the probability that a corridor block will have a monster.
	 * @return corridor block probability level.
	 */
	public double getCorrProb(){
		return this.corrProb;
	}
}
