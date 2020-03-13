/*
 * @version Jan 14, 2006
 */
package com.robestone.robot;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class RobotActionResponse {
	
	public static final RobotActionResponse getFail() {
		return new RobotActionResponse(false);
	}
	public static final RobotActionResponse getSuccess() {
		return new RobotActionResponse(true);
	}

	public Rectangle area;
	public BufferedImage image;
	public String message;
	public boolean success;
	
	public RobotActionResponse(boolean success) {
		this.success = success;
	}
	public RobotActionResponse(RobotActionResponse copy) {
		this.image = copy.image;
		this.message = copy.message;
		this.area = copy.area;
		this.success = copy.success;
	}

}
