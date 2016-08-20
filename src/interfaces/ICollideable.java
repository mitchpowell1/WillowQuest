package interfaces;

/***
 * ICollideable interface is a contractual agreement that whatever implements it will contain
 * a method to describe it's collision status with another ICollideable.
 * @author Mitch Powell
 *
 */
public interface ICollideable {
	public boolean collidesWith(ICollideable room);
}
