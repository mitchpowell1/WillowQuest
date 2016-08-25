package factories;

import java.util.Random;

import gameLogicComponents.RoomDescription;
import interfaces.ICollideableRoom;
import interfaces.ICompareableCoord;
import interfaces.ICoordFactory;
import interfaces.IRoomDescriptionFactory;

public class RoomDescriptionFactory implements IRoomDescriptionFactory {
	private ICoordFactory coordinateFactory;
	private Random rand;
	
	/***
	 * Constructor method for the room description factory object
	 * @param coordFact the coordinate factory dependency passed in through the
	 * composition class.
	 */
	public RoomDescriptionFactory(ICoordFactory coordFact, Random rand){
		this.coordinateFactory = coordFact;
		this.rand = rand;
	}
	
	@Override
	/***
	 * Creates a room description based upon the given height and width of the dungeon
	 */
	public ICollideableRoom getRoom(int dungeonWidth, int dungeonHeight) {
		int maxHeight = dungeonHeight / 10;
		int minHeight = dungeonHeight / 20;
		int maxWidth = dungeonWidth / 10;
		int minWidth = dungeonWidth / 20;
		int roomWidth = rand.nextInt(maxWidth - minWidth) + minWidth;
		int roomHeight = rand.nextInt(maxHeight - minHeight) + minHeight;
		int topLeftX = rand.nextInt(dungeonWidth - roomWidth);
		int topLeftY = rand.nextInt(dungeonHeight - roomHeight);
		ICompareableCoord topLeft = coordinateFactory.getCoord(topLeftX, topLeftY);
		ICompareableCoord topRight = coordinateFactory.getCoord(topLeftX + roomWidth, topLeftY+roomHeight);
		return new RoomDescription(topLeft,topRight);
	}

}
