package interfaces;

/***
 * Defines interface contract for an integer coordinate system that will have methods to
 * compare itself to another ICompareableCoord
 * @author mitch
 *
 */
public interface ICompareableCoord {
	public boolean bottomRightOf(ICompareableCoord other);
	public int getCol();
	public int getRow();
	public void setCol(int newCol);
	public void setRow(int newRow);
	public boolean topLeftOf(ICompareableCoord other);
}
