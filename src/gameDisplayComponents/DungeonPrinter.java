package gameDisplayComponents;

import gameLogicComponents.Cell;
import interfaces.IRenderableDungeon;

public class DungeonPrinter {
	
	public void printDungeon(IRenderableDungeon dungeon){
		Cell[][] cells = dungeon.getCells();
		for(int row=0; row<cells.length; row++){
			for(int col=0; col<cells[0].length; col++){
				System.out.print(cells[row][col].getChar()+" ");
			}
			System.out.println();
		}
	}
}
