/*
 * @version Mar 18, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointTransformerList implements PointTransformer {

	private List<PointTransformer> list = new ArrayList<PointTransformer>();
	
	public void add(PointTransformer pt) {
		list.add(pt);
	}
	
	public Point transform(Point from, Point to, Point current) {
		Point p = current;
		for (PointTransformer pt: list) {
			p = pt.transform(from, to, p);
		}
		return p;
	}

}
