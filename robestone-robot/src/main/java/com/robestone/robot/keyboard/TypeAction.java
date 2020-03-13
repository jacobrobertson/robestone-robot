/*
 * @version Jan 15, 2006
 */
package com.robestone.robot.keyboard;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.robestone.robot.RobotAction;
import com.robestone.robot.RobotActionRequest;
import com.robestone.robot.RobotActionResponse;
import com.robestone.robot.RobotUtilities;

public class TypeAction implements RobotAction {

	private Logger logger = LogManager.getLogger(getClass().getSimpleName());

	private TypingRobot typingRobot;
	private String text;
	public TypeAction(String text) {
		this.text = text;
		typingRobot = new TypingRobot(RobotUtilities.getCommonRobot());
	}
	
	public RobotActionResponse run(RobotActionRequest request) {
		logger.info("Typing: " + text);
		typingRobot.typeWithWaits(text);
		return RobotActionResponse.getSuccess();
	}

}
