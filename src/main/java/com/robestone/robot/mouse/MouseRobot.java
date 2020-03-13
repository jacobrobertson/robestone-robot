/*
 * @version Mar 18, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.robestone.robot.WaitAction;

/**
 * Encapsulates all the things a mouse would want to do, with
 * customizable parameters.
 * 
 * This robot class is also capable of tracking where it has been,
 * and especially where it's clicked.
 * 
 * @author Jacob Robertson
 */
public class MouseRobot implements MouseService {

	private Logger logger = LogManager.getLogger(getClass().getSimpleName());

	public static void main(String[] args) throws Exception {
		int innerWait = 10;
		getMouseRobot().move(new Rectangle(100, 400, 10, 10), innerWait);
		new Robot().mousePress(InputEvent.BUTTON1_MASK);
		getMouseRobot().move(new Rectangle(400, 400, 10, 10), innerWait);
		getMouseRobot().move(new Rectangle(400, 100, 10, 10), innerWait);
		getMouseRobot().move(new Rectangle(100, 100, 10, 10), innerWait);
		getMouseRobot().move(new Rectangle(100, 400, 10, 10), innerWait);
		new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	private static final MouseRobot mouseRobot = new MouseRobot();
	/**
	 * Singleton - for now this should be fine, since these
	 * rules apply to all activities.  If we want to have different behaviors,
	 * would need to use multiple instances configured differently.
	 */
	public static MouseRobot getMouseRobot() {
		return mouseRobot;
	}
	/**
	 * For now, disallow instantiation.
	 */
	private MouseRobot() {
	}
	
	class PointInfo {
		Point point;
		int clickCount;
		/**
		 * Just counts the times it was moved to, (but didn't click?).
		 * Doesn't count the times it "moved over".
		 */
		int moveToCount;
	}
	
	private Map<String, PointInfo> points = new HashMap<String, PointInfo>();
	private PointMover pointMoverAction = new PointMover();
	private Robot robot = getRobot();
	private WaitAction innerClickWait = new WaitAction(100);
	private WaitAction afterMoveWait = new WaitAction(100);
	
	/**
	 * Moves to that exact spot.  Private method only.
	 * Should never call this externally.
	 */
	private Point move(Point p, boolean transform, int innerWait) {
		if (innerWait > 0) {
			pointMoverAction.run(p, robot, transform, innerWait);
		} else {
			pointMoverAction.run(p, robot, transform);
		}
		return p;
	}
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#move(java.awt.Rectangle)
	 */
	public Point move(Rectangle r) {
		return doMove(r, false, true, -1);
	}
	
    public Point move(Point point) {
    	move(point, false, -1);
    	return point;
    }
	
	public Point move(Rectangle r, int innerWait) {
		return doMove(r, false, true, innerWait);
	}
	/**
	 * Use this method with caution - it will "look like a robot"...
	 */
	public Point moveStraight(Rectangle r, int innerWait) {
		return doMove(r, false, false, innerWait);
	}
	private Point doMove(Rectangle r, boolean willClick, boolean transform, int innerWait) {
		Point p = getRandomPoint(r);
		if (!willClick) {
			addPointInfo(p, true, false);
		}
		move(p, transform, innerWait);
		return p;
	}
	private void addPointInfo(Point p, boolean move, boolean click) {
		String key = getKey(p);
		PointInfo pi = points.get(key);
		if (pi == null) {
			pi = new PointInfo();
			pi.point = new Point(p.x, p.y);
			points.put(key, pi);
		}
		if (move) {
			pi.moveToCount++;
		}
		if (click) {
			pi.clickCount++;
			if (pi.clickCount > 5) {
				logger.info("MouseRobot.clickCount." + key + "=" + pi.clickCount);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#clickOnCalculatedPoint(java.awt.Point)
	 */
	public Point clickOnCalculatedPoint(Point p) {
		Rectangle r = new Rectangle(p.x, p.y, 1, 1);
		return click(r, 0);
	}
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#click(java.awt.Rectangle)
	 */
	public Point click(Rectangle r) {
		return click(r, 0);
	}
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#click(java.awt.Rectangle, int)
	 */
	public Point click(Rectangle r, int innerWait) {
		Point p = doMove(r, true, true, -1);
		addPointInfo(p, false, true);
		wait(afterMoveWait, innerWait);
		click();
		return p;
	}
    /* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#doubleClick(java.awt.Rectangle)
	 */
    public Point doubleClick(Rectangle r) {
        click(r);
    	innerClickWait.run();
        return click(r);
    }
    private void wait(WaitAction wa, int innerWait) {
		if (innerWait <= 0) {
			wa.run();
		} else {
			WaitAction.waitCalculated(innerWait);
		}
    }
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#rightClick(java.awt.Rectangle)
	 */
	public Point rightClick(Rectangle r) {
		return rightClick(r, 0);
	}
	public Point rightClick(Rectangle r, int innerWait) {
		Point p = doMove(r, true, true, -1);
		addPointInfo(p, false, true);
		wait(afterMoveWait, innerWait);
		rightClick();
		return p;
	}
	/* (non-Javadoc)
	 * @see com.robestone.robot.mouse.MouseService#click()
	 */
    public void click() {
    	robot.mousePress(InputEvent.BUTTON1_MASK);
    	innerClickWait.run();
    	robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
	/**
	 * Right-clicks on the current spot.
	 */
    private void rightClick() {
    	robot.mousePress(InputEvent.BUTTON3_MASK);
    	innerClickWait.run();
    	robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
	private Point getRandomPoint(Rectangle r) {
		
		int x = (int) (Math.random() * r.width);
		int y = (int) (Math.random() * r.height);
		Point p = new Point(r.x + x, r.y + y);
		return p;
	}
    private static Robot getRobot() {
    	try {
	    	Robot r = new Robot();
	    	r.setAutoWaitForIdle(true);
	    	return r;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    private String getKey(Point p) {
    	return p.x + "," + p.y;
    }
    
}
