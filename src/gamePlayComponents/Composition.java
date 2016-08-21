package gamePlayComponents;

import java.util.Random;

import gameLogicComponents.CoordinateFactory;
import gameLogicComponents.RoomDescriptionFactory;
import interfaces.ICoordFactory;
import interfaces.IRoomDescriptionFactory;

/***
 * This Composition class behaves as the dependency injector for the project. Any non system-library
 * dependencies that a class depends on will be passed to that class through a corresponding
 * method in this dependency injector. This way, any changes that need to be made to a 
 * classes dependencies can be made by changing a single line in this class.
 * @author Mitch Powell
 *
 */
public class Composition {
	private Random rand;
	private ICoordFactory coordFact;
	private RoomDescriptionFactory roomDescriptionFactory;
	
	public Composition(){
		rand = new Random();
		coordFact = new CoordinateFactory();
		roomDescriptionFactory = new RoomDescriptionFactory(coordFact, rand);
	}
	
	/***
	 * Here I will use a single global random number generator to make reproducing errors
	 * simple.
	 * @return the global random number generator to be used by any classes needing random
	 * number generation
	 */
	public Random getRandomGen(){
		return rand;
	}
	
	public IRoomDescriptionFactory getRoomDescriptionFactory(){
		return roomDescriptionFactory;
	}
	
	public void IRoomGenerator(){
		
	}
}
