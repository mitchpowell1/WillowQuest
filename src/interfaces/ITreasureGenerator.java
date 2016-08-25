package interfaces;

import gameLogicComponents.Cell;
import gameSettingComponents.TreasureSettings;

public interface ITreasureGenerator {
	public void generateTreasure(Cell[][] cells, TreasureSettings treas);
}
