/*
 * @version Jan 14, 2006
 */
package com.robestone.robot.keyboard;

import java.awt.event.KeyEvent;

import com.robestone.robot.RobotAction;
import com.robestone.robot.RobotActionRequest;
import com.robestone.robot.RobotActionResponse;

/**
 * @deprecated use TypingRobot instead
 * @author Jacob Robertson
 */
public class EnterKeyAction implements RobotAction {

	public RobotActionResponse run(RobotActionRequest request) {
		TypingRobot.clickKey(request.robot, KeyEvent.VK_ENTER, 10, 25);
		return RobotActionResponse.getSuccess();
	}

}
