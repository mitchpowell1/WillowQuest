package gameDisplayComponents;

import gameLogicComponents.Cell;
import interfaces.IRenderableDungeon;

public class HTMLDungeonPrinter {
	
	public String printDungeon(IRenderableDungeon dungeon){
		String dungeonString = "<html><p style=\"font-family: 'Courier New', Courier, monospace\"><pre>";
		Cell[][] cells = dungeon.getCells();
		for(int row=0; row<cells.length; row++){
			for(int col=0; col<cells[0].length; col++){
				dungeonString+= cells[row][col].getString()+" ";
			}
			dungeonString+="<br>";
		}
		dungeonString+="</pre></p></html>";
		return dungeonString;
	}
}
