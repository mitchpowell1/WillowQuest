package interfaces;

/***
 * Defines interface contract for an integer coordinate system that will have methods to
 * compare itself to another ICompareableCoord
 * @author mitch
 *
 */
public interface ICompareableCoord {
	public int getX();
	public int getY();
	public boolean topLeftOf(ICompareableCoord other);
	public boolean bottomRightOf(ICompareableCoord other);
}
