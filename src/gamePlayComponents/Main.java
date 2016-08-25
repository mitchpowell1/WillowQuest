package gamePlayComponents;

import gamePlayComponents.Composition;
import factories.DungeonFactory;
import gameDisplayComponents.DungeonGUI;
import gameDisplayComponents.HTMLDungeonPrinter;

public class Main {

	public static void main(String[] args) {
		Composition comp = new Composition();
		DungeonFactory fact = new DungeonFactory( 
				comp.getRoomGenerator(),
				comp.getCorridorGenerator(),
				comp.getMonsterGenerator(),
				comp.getWallGenerator(),
				comp.getTreasureGenerator()
				);
		HTMLDungeonPrinter print = new HTMLDungeonPrinter();
		@SuppressWarnings("unused")
		DungeonGUI gui = new DungeonGUI(print,fact);
	}

}
