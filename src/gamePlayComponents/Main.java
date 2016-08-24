package gamePlayComponents;

import gamePlayComponents.Composition;
import gameSettingComponents.DungeonDensity;
import factories.DungeonFactory;
import gameDisplayComponents.DungeonPrinter;
import gameLogicComponents.Dungeon;

public class Main {

	public static void main(String[] args) {
		Composition comp = new Composition();
		DungeonFactory fact = new DungeonFactory( 
				comp.getRoomGenerator(),
				comp.getCorridorGenerator(),
				comp.getMonsterGenerator(),
				comp.getTerminalGenerator()
				);
		DungeonPrinter print = new DungeonPrinter();
		print.printDungeon(fact.getDungeon());
	}

}
