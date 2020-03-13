/*
 * @version Jan 10, 2008
 */
package com.robestone.robot.mouse;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Captures the possible operations without specifying how they will be performed.
 * 
 * Three main implementations will include a realistic mouse, an instantaneous mouse,
 * and a no-op mouse.
 * 
 * @author Jacob Robertson
 */
public interface MouseService {

	/**
	 * Moves to a some non-defined spot within the rectangle (inclusive).
	 * @return the Point actually moved to
	 */
	Point move(Rectangle area);
	
	Point click(Rectangle area);
	Point doubleClick(Rectangle area);
	Point rightClick(Rectangle area);

	/**
	 * Clicks on the current spot.
	 * A convenience method that isn't normally used.
	 */
	void click();

	/**
	 * A convenience method that isn't normally used.
	 */
	Point move(Point point);

}