package interfaces;

import gameComponents.Cell;

/***
 * IRenderable interface defines the renderable contract for dungeons.
 * Any valid renderable object will be able to return a 2D array of cells
 * for rendering.
 * @author Mitch Powell
 *
 */
public interface IRenderable {
	public Cell[][] getCells();
}
