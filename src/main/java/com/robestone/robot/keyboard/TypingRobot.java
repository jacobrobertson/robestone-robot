/*
 * @version Mar 20, 2006
 */
package com.robestone.robot.keyboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.KeyStroke;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.robestone.robot.RobotUtilities;
import com.robestone.robot.WaitAction;


/**
 * Encapsulates the logic to allow typing to look "natural".
 * 
 * @todo this isn't at all implemented
 * @author Jacob Robertson
 * 
 * @todo this only adds a random element, doesn't actually take into consideration that names
 * like "Jaeza" are hard to type because they use the pinky so much in a row.
 * 
 * @todo break the robot out from the randomizing strategy
 */
public class TypingRobot {

	private Logger logger = LogManager.getLogger(getClass().getSimpleName());

	public static void main(String[] args) throws Exception {
		TypingRobot tr = new TypingRobot(new Robot());
		for (int i = 0; i < 100; i++) {
			tr.typeRandomized("selling chaos runes {125} each -- {qw33n}\n");
		}
	}
	
	private static TypingRobot singleton;
	public static TypingRobot getTypingRobot() {
		if (singleton == null) {
			singleton = new TypingRobot(RobotUtilities.getCommonRobot());
		}
		return singleton;
	}
	
	private Keyboard keyboard = new Keyboard();
	private Robot robot;
	
	public static final int charInnerWaitMin = 5;
	public static final int charInnerWaitMax = 15;
	private int charWaitMin = 10;
	private int charWaitMax = 25;
//	private int wordWaitMin = 10;
//	private int wordWaitMax = 25;
	
//	private float oddsThatAGivenWordWillWaitLonger = .1f;
//	private float oddsThatAGivenCharWillWaitLonger = .05f;
	private float oddsThatAGivenCharWillMistype = 1f / 50f;
	private float oddsThatAGivenCharWillTakeExtraLong = 1f / 50f;
	
//	private float oddsThatAGivenMistypedCharWillBackspace = .05f;
//	private float oddsThatAGivenCharWillFlipped = .05f;
	
    public TypingRobot(Robot robot) {
		this.robot = robot;
	}
    
    /**
     * Type with randomized string and with waits
     */
    public void typeRandomized(String message) {
    	message = getRandomizedString(message);
   		typeWithWaits(message);
    }
	public void typeEnterKey() {
		typeSpecialKey(KeyEvent.VK_ENTER);
	}
	public void typeBackspace() {
		typeSpecialKey(KeyEvent.VK_BACK_SPACE);
	}
	public void typeSpecialKey(int key) {
		TypingRobot.clickKey(robot, key, charInnerWaitMin, charInnerWaitMax);
	}
    private String toString(List<MessageSegment> list) {
    	StringBuffer buf = new StringBuffer();
    	for (MessageSegment ms: list) {
    		String value = ms.value;
    		if (!ms.isProtected)	 {
    			value = doGetRandomizedString(value);
    		}
    		buf.append(value);
    	}
    	return buf.toString();
    }
    public String getRandomizedString(String s) {
    	List<MessageSegment> list = parseMessage(s);
    	String r = toString(list);
    	return r;
    }
    private String doGetRandomizedString(String s) {
    	int len = s.length();
    	StringBuffer buf = new StringBuffer();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (isChosen(oddsThatAGivenCharWillMistype)) {
				c = getRandomChar(c);
			}
			buf.append(c);
		}
    	return buf.toString();
    }
    private char getRandomChar(char c) {

    	int x = 0;
    	int y = 0;
    	
		int type = (int) (Math.random() * 4);
    	float oddsItWillBeSimple = .8f;
    	if (isChosen(oddsItWillBeSimple)) {
    		switch (type) {
    		case 0: x++; break; 
    		case 1: x--; break; 
    		case 2: y++; break; 
    		case 3: y--; break;
    		default: throw new IllegalArgumentException();
    		}
    	} else {
    		switch (type) {
    		case 0: x++; y++; break; 
    		case 1: x--; y++; break; 
    		case 2: x++; y--; break; 
    		case 3: x--; y--; break;
    		default: throw new IllegalArgumentException();
    		}
    	}
    	
    	c = keyboard.getKey(c, x, y);
    	
    	return c;
    }
    private boolean isChosen(float odds) {
    	float got = (float) Math.random();
    	return (got <= odds);
    }
//    private void wait(float odds, int min, int max) {
//    	if (isChosen(odds)) {
//    		WaitAction.wait(min, max);
//    	}
//    }
    
    /**
     * TODO this is pretty weak - doesn't cover all actual cases,
     * and doesn't handle double delimited things like "hello, dolly"
     */
//    private boolean isCharWordDelimiter(char c) {
//    	return c == ' ' || c == ',' || c == '.' || c == '-';
//    }
//    private Pattern MESSAGE_PATTERN = Pattern.compile("(?:(\\{.*\\})?(.*)?)*");
    public List<MessageSegment> parseMessage(String s) {
    	List<MessageSegment> list = new ArrayList<MessageSegment>();
    	StringTokenizer tok = new StringTokenizer(s, "{}", true);
    	while (tok.hasMoreElements()) {
			MessageSegment ms = new MessageSegment();
    		String next = tok.nextToken();
    		if (next.equals("{")) {
    			ms.value = tok.nextToken();
    			tok.nextToken();
    			ms.isProtected = true;
    		} else {
    			ms.value = next;
    		}
    		list.add(ms);
    	}
    	return list;
    }
    public static class MessageSegment {
    	String value;
    	boolean isProtected;
    }
    
    /**
     * Type with no randomized string, but with intervals between typing, etc.
     */
	public void typeWithWaits(String text) {
		logger.debug("typeWithWaits." + text);
		int len = text.length();
        for (int i = 0; i < len; i++) {
            if (i > 0) {
            	WaitAction.wait(charWaitMin, charWaitMax);
                if (isChosen(oddsThatAGivenCharWillTakeExtraLong)) {
                	WaitAction.wait(charWaitMin, charWaitMax);
                }
            }
            char c = text.charAt(i);
            char u = Character.toUpperCase(c);
            int code = KeyStroke.getKeyStroke(u, 0).getKeyCode();
            boolean shift = Character.isUpperCase(c);
            if (shift) {
            	robot.keyPress(KeyEvent.VK_SHIFT);
            }
            robot.keyPress(code);
            WaitAction.wait(charInnerWaitMin, charInnerWaitMax);
            robot.keyRelease(code);
            if (shift) {
            	robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }
	
    public static void rightArrow(Robot robot, int innerWaitMin, int innerWaitMax) {
        clickKey(robot, KeyEvent.VK_RIGHT, innerWaitMin, innerWaitMax);
    }
    public static void leftArrow(Robot robot, int innerWaitMin, int innerWaitMax) {
        clickKey(robot, KeyEvent.VK_LEFT, innerWaitMin, innerWaitMax);
    }
    public static void upArrow(Robot robot, int innerWaitMin, int innerWaitMax) {
        clickKey(robot, KeyEvent.VK_UP, innerWaitMin, innerWaitMax);
    }
    public static void clickKey(Robot robot, int key, int innerWaitMin, int innerWaitMax) {
        robot.keyPress(key);
        WaitAction.wait(innerWaitMin, innerWaitMax);
        robot.keyRelease(key);
    }

}
