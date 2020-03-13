/*
 * @version Jan 14, 2006
 */
package com.robestone.robot;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class RobotActionRequest {

	public Rectangle area;
	public BufferedImage image;
	public Robot robot;
	public String message;
	
	public RobotActionRequest(Robot robot) {
		this.robot = robot;
	}
	public RobotActionRequest() {
	}
	public RobotActionRequest(boolean useDefaultRobot) {
		if (useDefaultRobot) {
			robot = RobotUtilities.getCommonRobot();
		}
	}
}
