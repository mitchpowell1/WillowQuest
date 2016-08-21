package interfaces;

/***
 * Describes the interface a coordinate factory must be programmed to meet.
 * @author Mitch Powell
 *
 */
public interface ICoordFactory {
	public ICompareableCoord getCoord(int x, int y);
}
