package factories;

import gameLogicComponents.IntCoord;
import interfaces.ICompareableCoord;
import interfaces.ICoordFactory;

/***
 * Instantiates coordinate pairs
 * @author mitch
 *
 */
public class CoordinateFactory implements ICoordFactory{

	@Override
	/***
	 * Returns an integer coordinate pair corresponding with the x and y values passed as arguments.
	 */
	public ICompareableCoord getCoord(int x, int y) {
		return new IntCoord(x,y);
	}

}
