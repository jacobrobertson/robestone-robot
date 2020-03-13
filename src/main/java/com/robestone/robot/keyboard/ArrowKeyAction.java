/*
 * @version Apr 22, 2006
 */
package com.robestone.robot.keyboard;

import com.robestone.robot.RobotAction;
import com.robestone.robot.RobotActionRequest;
import com.robestone.robot.RobotActionResponse;

public class ArrowKeyAction implements RobotAction {

//	public static final ArrowKeyAction LEFT = new ArrowKeyAction(true);
//	public static final ArrowKeyAction RIGHT = new ArrowKeyAction(false);

	public static final ArrowKeyAction getLeft(int innerWaitMin, int innerWaitMax) {
		return new ArrowKeyAction(true, innerWaitMin, innerWaitMax);
	}
	public static final ArrowKeyAction getRight(int innerWaitMin, int innerWaitMax) {
		return new ArrowKeyAction(false, innerWaitMin, innerWaitMax);
	}
	public static final ArrowKeyAction getLeft() {
		return getLeft(25, 55);
	}
	public static final ArrowKeyAction getRight() {
		return getRight(25, 55);
	}
	
	// could pass this in
	private int innerWaitMin;
	private int innerWaitMax;
	
	private boolean left;
	
	private ArrowKeyAction(boolean left, int innerWaitMin, int innerWaitMax) {
		this.left = left;
		this.innerWaitMax = innerWaitMax;
		this.innerWaitMin = innerWaitMin;
	}
	
	public RobotActionResponse run(RobotActionRequest request) {
		if (left) {
			TypingRobot.leftArrow(request.robot, innerWaitMin, innerWaitMax);
		} else {
			TypingRobot.rightArrow(request.robot, innerWaitMin, innerWaitMax);
		}
		return RobotActionResponse.getSuccess();
	}
	@Override
	public String toString() {
		return "ArrowKeyAction." + (left ? "left" : "right");
	}

}
