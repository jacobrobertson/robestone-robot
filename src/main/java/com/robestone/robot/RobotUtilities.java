/*
 * @version Dec 24, 2007
 */
package com.robestone.robot;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class RobotUtilities {

	private static Robot ROBOT;
	static {
		try {
			ROBOT = new Robot();
		} catch (Exception e) {
			///....
		}
	}
	public static Robot getCommonRobot() {
		return ROBOT;
	}
	public static BufferedImage getCurrentScreenCapture() {
		try {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage screen = getCommonRobot().createScreenCapture(
					new Rectangle(0, 0, dim.width, dim.height));
			return screen;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
