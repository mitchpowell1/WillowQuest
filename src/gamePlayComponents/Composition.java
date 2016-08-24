package gamePlayComponents;

import java.util.Random;

import dungeonComponentGenerators.CorridorGenerator;
import dungeonComponentGenerators.DoorGenerator;
import dungeonComponentGenerators.MonsterGenerator;
import dungeonComponentGenerators.RoomGenerator;
import dungeonComponentGenerators.TerminalGenerator;
import factories.CoordinateFactory;
import factories.RoomDescriptionFactory;
import interfaces.ICoordFactory;
import interfaces.ICorridorGenerator;
import interfaces.IDoorGenerator;
import interfaces.IMonsterGenerator;
import interfaces.IRoomDescriptionFactory;
import interfaces.IRoomGenerator;
import interfaces.ITerminalGenerator;

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
	private ICorridorGenerator corGen;
	private IDoorGenerator doorGen;
	private IRoomDescriptionFactory roomDescriptionFactory;
	private IMonsterGenerator monstGen;
	private IRoomGenerator roomGen;
	private ITerminalGenerator termGen;
	
	public Composition(){
		rand = new Random();
		coordFact = new CoordinateFactory();
		roomDescriptionFactory = new RoomDescriptionFactory(coordFact, rand);
		doorGen = new DoorGenerator(coordFact);
		monstGen = new MonsterGenerator(rand);
		roomGen = new RoomGenerator(roomDescriptionFactory);
		termGen = new TerminalGenerator(rand);
		corGen = new CorridorGenerator(rand,coordFact,doorGen,termGen);

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
	
	public IRoomGenerator getRoomGenerator(){
		return roomGen;
	}
	
	public ICorridorGenerator getCorridorGenerator(){
		return corGen;
	}
	
	public IMonsterGenerator getMonsterGenerator(){
		return monstGen;
	}
	
	public ITerminalGenerator getTerminalGenerator(){
		return termGen;
	}
}
