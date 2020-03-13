/*
 * @version Mar 13, 2006
 */
package com.robestone.robot.mouse;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import com.robestone.robot.WaitAction;

public class PointMover {

	public static void main(String[] args) throws Exception {
		new Robot().mouseMove(800, 100);
		PointMover a = new PointMover();
		a.run(new Point(300, 800), new Robot());
	}
	
	/**
	 * How far to move each time.
	 */
	private int moveSegmentLength;
//	private WaitAction innerWait;
	private int innerWait;
	private PointTransformer pointTransformer;
	
	public PointMover() {
		this(10, 10);
	}
	public PointMover(int moveSegmentLength, int innerWait) {
		this.moveSegmentLength = moveSegmentLength;
//		this.innerWait = new WaitAction(innerWait);
		this.innerWait = innerWait;
		PointTransformerList pointTransformerList = new PointTransformerList();
		pointTransformerList.add(new SimpleCurvePointTransformer());
		pointTransformerList.add(new VariancePointTransformer(5));
		this.pointTransformer = pointTransformerList;
	}
	public void run(Point to, Robot robot) {
		run(to, robot, true);
	}
	public void run(Point to, Robot robot, boolean transform) {
		run(to, robot, transform, innerWait);
	}
	public void run(Point to, Robot robot, boolean transform, int innerWait) {
//		logger.info("MOVE TO : " + to);
		// get from
		Point from = MouseInfo.getPointerInfo().getLocation();
		// get distance
		float x = to.x - from.x;
		float y = to.y - from.y;
		float len = (float) Math.sqrt(x * x + y * y);
		// get the length -- @todo need to make sure this is the right number
		float segments = len / moveSegmentLength;
		float xd = x / segments;
		float yd = y / segments;
//		logger.info(len + "-" + segments + "-" + x + "," + y + "/" + xd + "," + yd);
		int xm = 0;
		int ym = 0;
		for (int i = 0; i < segments; i++) {
			xm = from.x + (int) (xd * i);
			ym = from.y + (int) (yd * i);
			Point p = new Point(xm, ym);
//			logger.info(p);
			if (transform) {
				p = pointTransformer.transform(from, to, p);
//				logger.info(">"+p);
			}
//			logger.info(p);
//			logger.info(">"+np);
			robot.mouseMove(p.x, p.y);
			WaitAction.waitCalculated(innerWait);
		}
		// the last move needs to be to the right point
		// - the idea is that the point might already be random and we can't miss it
		if (xm != to.x || ym != to.y) {
			robot.mouseMove(to.x, to.y);
		}
	}
	
}
