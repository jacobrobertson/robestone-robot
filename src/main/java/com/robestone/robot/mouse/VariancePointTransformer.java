/*
 * @version Mar 18, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Point;

public class VariancePointTransformer implements PointTransformer {

	private int variance;
	
	public VariancePointTransformer() {
		this(10);
	}
	public VariancePointTransformer(int variance) {
		this.variance = variance + 1;
	}
	private int randomNegative() {
		return (Math.random() > .5 ? -1 : -1); 
	}
	public Point transform(Point from, Point to, Point current) {
		int x = (int) (Math.random() * variance) * randomNegative();
		int y = (int) (Math.random() * variance) * randomNegative();
		Point p = new Point(current.x + x, current.y + y);
		return p;
	}

}
