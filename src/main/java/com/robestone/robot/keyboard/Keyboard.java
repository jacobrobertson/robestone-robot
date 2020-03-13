/*
 * @version Dec 6, 2006
 */
package com.robestone.robot.keyboard;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {

	private static String[] keyboardStrings = {
		"1234567890-=",
		"qwertyuiop[]\\",
		"asdfghjkl;'",
		"zxcvbnm,./",
	};
	
	private char[][] keyboard;
	private Map<Character, Point> positions;
	
	public Keyboard() {
		positions = new HashMap<Character, Point>();
		keyboard = new char[keyboardStrings.length][];
		for (int i = 0; i < keyboardStrings.length; i++) {
			String row = keyboardStrings[i];
			int len = row.length();
			keyboard[i] = new char[len];
			for (int j = 0; j < len; j++) {
				char c = row.charAt(j);
				keyboard[i][j] = c;
				positions.put(c, new Point(j, i));
			}
		}
	}
	
	/**
	 * @return the key at the given pos, or the nearest key if at edge
	 * @param xDiff = -1, 0, or 1
	 * @param yDiff = -1, 0, or 1
	 */
	public char getKey(char key, int xDiff, int yDiff) {
		boolean up = Character.isUpperCase(key);
		if (up) {
			key = Character.toLowerCase(key);
		}
		Point p = positions.get(key);
		if (p == null) {
			// TODO doesn't cover caps
			return key; // covers all other keys
		}
		int x = p.x + xDiff;
		int y = p.y + yDiff;
		if (y < 0) {
			y = 0;
		}
		if (y >= keyboard.length) {
			y = keyboard.length - 1;
		}
		if (x < 0) {
			x = 0;
		}
		if (x >= keyboard[y].length) {
			x  = keyboard[y].length - 1;
		}
		char newKey = keyboard[y][x];
		if (up) {
			newKey = Character.toUpperCase(newKey);
		}
		return newKey;
	}
	
}
