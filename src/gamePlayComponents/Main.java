package gamePlayComponents;

import gameDisplayComponents.DungeonPrinter;
import gameLogicComponents.Dungeon;
import gameLogicComponents.DungeonFactory;

public class Main {
	private static int HEIGHT = 1;
	private static int WIDTH = 4;

	public static void main(String[] args) {
		DungeonFactory fact = new DungeonFactory();
		DungeonPrinter print = new DungeonPrinter();
		for(int i=0; i<1000; i++){
			Dungeon dungeon = fact.getDungeon(HEIGHT,WIDTH);
			print.printDungeon(dungeon);
			System.out.println();
		}
	}

}
