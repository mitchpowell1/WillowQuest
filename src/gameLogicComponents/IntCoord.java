package gameLogicComponents;

import interfaces.ICompareableCoord;

/***
 * I rolled my own integer coordinate class to keep track of room coordinates because the Java
 * AWT Point2D class uses floats/doubles, and I wanted to add cleaner methods for evaluating
 * relative location to other coordinate pairs.
 * @author Mitch Powell
 *
 */
public class IntCoord implements ICompareableCoord{
	private int x;
	private int y;
	
	/***
	 * Integer coordinate pair constructor function
	 * @param x cartesian x coordinate
	 * @param y cartesian y coordinate
	 */
	public IntCoord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/***
	 * Evaluates whether or not this object is to the bottom right relative to
	 * another object
	 * @param other the integer coordinate object for comparison.
	 * @return the "bottomRightness" of this object compared to the other.
	 */
	@Override
	public boolean bottomRightOf(ICompareableCoord other){
		return this.x > other.getRow() && this.y < other.getCol();
	}
	
	/***
	 * Get the y coordinate of the cooridinate pair
	 * @return the y-coordinate
	 */
	@Override
	public int getCol(){
		return this.y;
	}
	
	/***
	 * Get the x coordinate of the coordinate pair
	 * @return the x-coordinate
	 */
	@Override
	public int getRow(){
		return this.x;
	}
	
	@Override
	public void setCol(int newCol){
		this.y = newCol;
	}
	
	@Override
	public void setRow(int newRow){
		this.x = newRow;
	}
	
	/***
	 * Evaluautes whether or not this object is to the top left relative to
	 * another object
	 * @param other the integer coordinate object for comparision
	 * @return the "topLeftness" of this object compared to another.
	 */
	@Override
	public boolean topLeftOf(ICompareableCoord other){
		return this.x < other.getRow() && this.y > other.getCol();
	}

}
