package com.robestone.robot;

import com.robestone.robot.keyboard.TypingRobot;

public class ExampleRobot {

	public static void main(String[] args) throws Exception {
		TypingRobot typingRobot = TypingRobot.getTypingRobot();
		typingRobot.typeWithWaits("Hello");
	}
}
