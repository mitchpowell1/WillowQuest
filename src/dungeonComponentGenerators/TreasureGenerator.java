package dungeonComponentGenerators;

import java.util.Random;

import gameLogicComponents.Cell;
import gameSettingComponents.TreasureSettings;
import interfaces.ITreasureGenerator;

public class TreasureGenerator implements ITreasureGenerator{
	private Random randGen;
	
	public TreasureGenerator(Random rand){
		this.randGen = rand;
	}

	@Override
	public void generateTreasure(Cell[][] cells, TreasureSettings treas) {
		for(int row=0; row<cells.length; row++){
			for(int col=0; col<cells[0].length; col++){
				if(cells[row][col] == Cell.ROOM){
					double treasNum = randGen.nextDouble();
					if(treasNum < treas.getTreasureProb()){
						cells[row][col] = Cell.TREASURE;
					}
				}
			}
		}
	}

}
