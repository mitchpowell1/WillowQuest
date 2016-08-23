package gameLogicComponents;

import java.util.ArrayList;

import interfaces.ICollideableRoom;
import interfaces.IRoomDescriptionFactory;
import interfaces.IRoomGenerator;
/***
 * Generates rooms in a 2D array of cells.
 * @author mitch
 *
 */
public class RoomGenerator implements IRoomGenerator{
	private IRoomDescriptionFactory roomDescriptionFactory;
	
	public RoomGenerator(IRoomDescriptionFactory fact){
		this.roomDescriptionFactory = fact;
	}
	@Override
	public void generateRooms(Cell[][] cells, int roomDensity) {
		int dungeonWidth = cells[0].length;
		int dungeonHeight = cells.length;
		
		ArrayList<ICollideableRoom> rooms = new ArrayList<ICollideableRoom>();

		// Generating non-colliding rooms
		for (int attempt = 0; attempt < roomDensity; attempt++) {
			// Fetch a new room from the room factory
			ICollideableRoom room = this.roomDescriptionFactory.getRoom(dungeonWidth, dungeonHeight);
			// start with no collisions detected
			boolean collision = false;
			// iterate through every room in the existing arraylist of rooms
			for (int existingRoom = 0; existingRoom < rooms.size(); existingRoom++) {
				// If the newly generated room collides with an existing room,
				// update
				// the collision boolean
				if (room.collidesWith(rooms.get(existingRoom))) {
					collision = true;
				}
			}
			// Add the new room to the list of rooms if there has not been a
			// collision
			if (!collision) {
				rooms.add(room);
			}
		}
		// Next, update the cells in the cell array to reflect the positions of
		// all of the rooms
		for (ICollideableRoom room : rooms) {
			int topLeftX = room.getTopLeft().getRow();
			int topLeftY = room.getTopLeft().getCol();
			int bottomRightX = room.getBottomRight().getRow();
			int bottomRightY = room.getBottomRight().getCol();
			int roomHeight = bottomRightY - topLeftY;
			int roomWidth = bottomRightX - topLeftX;
			for (int i = 0; i < roomHeight; i++) {
				for (int j = 0; j < roomWidth; j++) {
					cells[topLeftY + i][topLeftX + j] = Cell.ROOM;
				}
			}
		}
	}
}
