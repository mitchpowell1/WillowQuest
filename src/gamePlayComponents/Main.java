package gamePlayComponents;

import gamePlayComponents.Composition;
import gameSettingComponents.DungeonDensity;
import gameDisplayComponents.DungeonPrinter;
import gameLogicComponents.Dungeon;
import gameLogicComponents.DungeonFactory;

public class Main {

	public static void main(String[] args) {
		Composition comp = new Composition();
		DungeonFactory fact = new DungeonFactory(
				comp.getRandomGen(), 
				comp.getRoomDescriptionFactory()
				);
		DungeonPrinter print = new DungeonPrinter();
		print.printDungeon(fact.getDungeon());
		System.out.println();
		fact.setRoomDensity(DungeonDensity.SPARSE);
		print.printDungeon(fact.getDungeon());
		System.out.println();
		fact.setRoomDensity(DungeonDensity.DENSE);
		print.printDungeon(fact.getDungeon());
		System.out.println(comp.getRandomGen().nextFloat());
	}

}
