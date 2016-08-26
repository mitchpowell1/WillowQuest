package gamePlayComponents;

import java.util.Random;

import dungeonComponentGenerators.CorridorGenerator;
import dungeonComponentGenerators.DoorGenerator;
import dungeonComponentGenerators.MonsterGenerator;
import dungeonComponentGenerators.RoomGenerator;
import dungeonComponentGenerators.TerminalGenerator;
import dungeonComponentGenerators.TreasureGenerator;
import dungeonComponentGenerators.WallGenerator;
import factories.CoordinateFactory;
import factories.RoomDescriptionFactory;
import interfaces.ICoordFactory;
import interfaces.ICorridorGenerator;
import interfaces.IDoorGenerator;
import interfaces.IMonsterGenerator;
import interfaces.IRoomDescriptionFactory;
import interfaces.IRoomGenerator;
import interfaces.ITerminalGenerator;
import interfaces.ITreasureGenerator;
import interfaces.IWallGenerator;

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
	private ITreasureGenerator treasGen;
	private IWallGenerator wallGen;
	
	public Composition(){
		long seed = (System.currentTimeMillis());
		rand = new Random(seed); //100: No doors (seems to only be when entrance is to the north
		coordFact = new CoordinateFactory();
		roomDescriptionFactory = new RoomDescriptionFactory(coordFact, rand);
		doorGen = new DoorGenerator(coordFact);
		monstGen = new MonsterGenerator(rand);
		roomGen = new RoomGenerator(roomDescriptionFactory);
		termGen = new TerminalGenerator(rand);
		corGen = new CorridorGenerator(rand,coordFact,doorGen,termGen);
		treasGen = new TreasureGenerator(rand);
		wallGen = new WallGenerator();

	}
	
	public ICorridorGenerator getCorridorGenerator(){
		return corGen;
	}
	
	public IMonsterGenerator getMonsterGenerator(){
		return monstGen;
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
	
	public ITerminalGenerator getTerminalGenerator(){
		return termGen;
	}
	
	public ITreasureGenerator getTreasureGenerator(){
		return treasGen;
	}
	
	public IWallGenerator getWallGenerator(){
		return wallGen;
	}
}
