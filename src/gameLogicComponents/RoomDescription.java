package gameLogicComponents;

import java.awt.geom.Point2D;
import java.util.Random;

import interfaces.ICollideableRoom;
import interfaces.ICompareableCoord;

public class RoomDescription implements ICollideableRoom {
	private int height;
	private int width;
	private ICompareableCoord topLeft;
	private ICompareableCoord bottomRight;
	/**
	 * Constructor object for a room description
	 * @param height the height of the room
	 * @param width the width of the room
	 * @param topLeft the cartesian location of the top left cell
	 * @param bottomRight the cartesian location of the bottom right cell
	 */
	public RoomDescription(ICompareableCoord topLeft, ICompareableCoord bottomRight){
		this.height = topLeft.getY()-bottomRight.getY();
		this.width = bottomRight.getX()-topLeft.getX();
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	@Override
	/***
	 * Checks if this room collides with another room.
	 * @param room the room to compare against
	 * @return whether or not the two rooms collide
	 */
	public boolean collidesWith(ICollideableRoom room) {
		boolean collision = false;
		// If the top left corner of this room is to the top left of
		// the bottom right corner of the other room, and to the bottom
		// right of the top left corner of the other room, there is a collision.
		if(this.topLeft.topLeftOf(room.getBottomRight())
				&& this.topLeft.bottomRightOf(room.getTopLeft())){
			collision = true;
		// Otherwise, if the bottom right corner of this object is to the
		// bottom right of the top left corner of the other object, and the
		// the top left of the bottom right corner of the other object,
		// there is a collision.
		} else if(this.bottomRight.bottomRightOf(room.getTopLeft())
				&& this.bottomRight.topLeftOf(room.getBottomRight())){
			collision = true;
		}
		return collision;
	}

	@Override
	/***
	 * Returns the coordinates of the top left cell of the room
	 * @return top left cell coordinates
	 */
	public ICompareableCoord getTopLeft() {
		return this.topLeft;
	}

	@Override
	/***
	 * Returns the coordinates of the bottom right cell of the room
	 * @return bottom right cell coordinates
	 */
	public ICompareableCoord getBottomRight() {
		return this.bottomRight;
	}

}