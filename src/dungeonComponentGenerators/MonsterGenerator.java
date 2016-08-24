package dungeonComponentGenerators;

import java.util.Random;

import gameLogicComponents.Cell;
import interfaces.IMonsterGenerator;
/***
 * Generates monsters in a 2D array of cells.
 * @author Mitch Powell
 *
 */
public class MonsterGenerator implements IMonsterGenerator {
	private Random randomGenerator;
	/***
	 * Constructor function for a monster generator. Random number generator
	 * passed in through constructor injection.
	 * @param randomGenerator the random number generator used to create monsters.
	 */
	public MonsterGenerator(Random randomGenerator){
		this.randomGenerator = randomGenerator;
	}
	
	@Override
	public void addMonsters(Cell[][] cells, double monsterRoomProb, double monsterCorrProb) {
		for(int row=0; row<cells.length; row++){
			for(int col=0; col<cells[0].length; col++){
				if(cells[row][col]== Cell.ROOM){
					if(this.randomGenerator.nextDouble() < monsterRoomProb){
						cells[row][col] = Cell.MONSTER;
					}
				} else if(cells[row][col] == Cell.CORRIDOR){
					if(this.randomGenerator.nextDouble() < monsterCorrProb){
						cells[row][col] = Cell.MONSTER;
					}
				}
			}
		}
	}

}
