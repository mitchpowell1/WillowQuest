package gamePlayComponents;

import gamePlayComponents.Composition;
import factories.DungeonFactory;
import gameDisplayComponents.DungeonPrinter;

public class Main {

	public static void main(String[] args) {
		Composition comp = new Composition();
		DungeonFactory fact = new DungeonFactory( 
				comp.getRoomGenerator(),
				comp.getCorridorGenerator(),
				comp.getMonsterGenerator()
				);
		DungeonPrinter print = new DungeonPrinter();
		print.printDungeon(fact.getDungeon());
	}

}
