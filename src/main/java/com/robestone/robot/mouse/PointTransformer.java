/*
 * @version Mar 16, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Point;

public interface PointTransformer {

	/**
	 * Given the from and to points, figure out the "path" the current point should go on.
	 */
	Point transform(Point from, Point to, Point current);
	
}
