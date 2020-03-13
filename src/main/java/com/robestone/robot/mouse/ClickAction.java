/*
 * @version Mar 19, 2006
 */
package com.robestone.robot.mouse;

import java.awt.Rectangle;

import com.robestone.robot.DefaultRectangleProvider;
import com.robestone.robot.RobotAction;
import com.robestone.robot.RobotActionRequest;
import com.robestone.robot.RobotActionResponse;

import com.robestone.robot.RectangleProvider;

public class ClickAction implements RobotAction {

	private RectangleProvider rectangle;
	
	public ClickAction() {
		this((RectangleProvider) null);
	}
	public ClickAction(Rectangle rectangle) {
		this(new DefaultRectangleProvider(rectangle));
	}
	public ClickAction(RectangleProvider rectangle) {
		this.rectangle = rectangle;
	}

	public RobotActionResponse run(RobotActionRequest request) {
		Rectangle area;
		if (this.rectangle == null) {
			area = request.area;
		} else {
			area = this.rectangle.getRectangle();
		}
		MouseRobot.getMouseRobot().click(area);
		return RobotActionResponse.getSuccess();
	}

}
