package interfaces;

import gameLogicComponents.Cell;

public interface IMonsterGenerator {
	/***
	 * Adds monsters to a 2D array of cells based on the probability of monsters being generated
	 * in rooms, and monsters being generated in corridors.
	 * @param cells The array of cells to add monsters to
	 * @param monsterRoomProb the probability occurs on a room block
	 * @param monsterCorrProb the probability a monster occurs on a corridor block.
	 */
	public void addMonsters(Cell[][] cells, double monsterRoomProb, double monsterCorrProb);
}
