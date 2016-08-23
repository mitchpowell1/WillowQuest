package interfaces;

import gameLogicComponents.Cell;

public interface IRoomGenerator {
	public void generateRooms(Cell[][] cells, int roomDensity);
}
