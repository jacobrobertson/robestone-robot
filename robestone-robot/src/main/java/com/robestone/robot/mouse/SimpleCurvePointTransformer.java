/*
 * @version Mar 16, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Point;

public class SimpleCurvePointTransformer implements PointTransformer {

	private double squashFactor;
	
	public SimpleCurvePointTransformer() {
		this(.5d);
	}
	public SimpleCurvePointTransformer(double squashFactor) {
		this.squashFactor = squashFactor;
	}
	
	public Point transform(Point from, Point to, Point current) {
		double height = to.y - from.y;
		double width = from.x - to.x;
		if (Math.abs(height) < Math.abs(width)) {
			return transformY(height, width, from, current);
		} else {
			return transformX(height, width, from, current);
		}
	}
	private Point transformX(double height, double width, Point from, Point current) {
		double cy = from.y - current.y;
		double fy = cy * (width / height);
		double x = Math.sin((cy / height) * Math.PI) * width * squashFactor + fy; 
		
		Point n = new Point(from.x + (int) x, current.y);
		return n;
	}
	private Point transformY(double height, double width, Point from, Point current) {
		double cx = current.x - from.x;
		double fx = cx * (height / width);
		double y = Math.sin((cx / width) * Math.PI) * height * squashFactor + fx; 
		
		Point n = new Point(current.x, from.y - (int) y);
		return n;
	}

}
