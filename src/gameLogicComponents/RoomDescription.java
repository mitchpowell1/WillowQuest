package gameLogicComponents;

import interfaces.ICollideableRoom;
import interfaces.ICompareableCoord;

public class RoomDescription implements ICollideableRoom {

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
		// Checks all the conditions that could mean two rooms haven't collided.
		// if none of them are true, that means the two rooms collide :D
		return !(
				// The two rooms do not collide if:
				// The left side of the other room is to the right of the
				// right side of this room.
				room.getTopLeft().getRow() > this.getBottomRight().getRow() ||
				// Or if the right side of the other room is to the left of the left
				// side of this room.
				room.getBottomRight().getRow() < this.getTopLeft().getRow() ||
				// Or if the top of the other room is below the bottom of this room.
				room.getTopLeft().getCol() > this.bottomRight.getCol() ||
				// Or if the bottom of the other room is above the top of this room.
				room.getBottomRight().getCol() < this.topLeft.getCol()
				);
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