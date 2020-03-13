package com.robestone.robot;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class WaitAction implements Runnable, RobotAction {

	private static Logger logger = LogManager.getLogger(WaitAction.class.getSimpleName());

    public int lower;
    public int upper;
    private String reason;
    
    private static final float percent = .75f;
    
    public WaitAction() {
    }
    public WaitAction(int waitTime) {
        this(getLower(waitTime), getUpper(waitTime));
    }
    private static int getLower(int waitTime) {
    	return (int) (waitTime * percent);
    }
    private static int getUpper(int waitTime) {
    	return (int) (waitTime * (2f - percent));
    }
    public WaitAction(int lower, int upper) {
    	this(null, lower, upper);
    }
    public WaitAction(String reason, int lower, int upper) {
    	this.reason = reason;
        this.lower = lower;
        this.upper = upper;
    }
    
    public void setLower(int lower) {
        this.lower = lower;
    }
    public void setUpper(int upper) {
        this.upper = upper;
    }
    
    public int getLower() {
        return lower;
    }
    public int getUpper() {
        return upper;
    }
    public RobotActionResponse run(RobotActionRequest request) {
    	run();
    	return RobotActionResponse.getSuccess();
    }
    public void run() {
    	wait(reason, lower, upper);
    }
    public static void waitMinutes(int lower, int upper) {
    	waitSeconds(lower * 60, upper * 60);
    }
    public static void waitSeconds(int lower, int upper) {
    	wait(lower * 1000, upper * 1000);
    }
    public static void wait(int lower, int upper) {
    	wait(null, lower, upper);
    }
    public static void waitCalculated(int waitTime) {
        wait(getLower(waitTime), getUpper(waitTime));
    }
    public static void waitOneMillisecond() {
    	waitSimple(1);
    }
    /**
     * Only called when the wait isn't going to make it look like a robot.
     * For example, when building images and stuff.
     */
    public static void waitSimple(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            // don't care
        }
    }
    public static void wait(String reason, int lower, int upper) {
    	if (reason != null) {
    		reason = " (" + reason + ")";
    	}
        int waitTime = (int) getWaitTime(lower, upper);
        try {
        	if (waitTime > 1000 && reason != null) {
        		logger.info("Waiting for " + waitTime + " ms" + reason);
        	}
            Thread.sleep(waitTime);
//            logger.info("<< done waiting..");
        } catch (InterruptedException ie) {
            // don't care
        }
    }
    public static long getWaitTime(long lower, long upper) {
    	return (long) (Math.random() * (upper - lower) + lower);
    }
    @Override
    public String toString() {
    	return "Wait." + lower + "-" + upper + "(" + reason + ")";
    }

    /**
     * Convert an action into a list with a wait following the action.
     */
    public static ActionList toActionList(RobotAction action, int lower, int upper) {
    	ActionList list = new ActionList();
    	list.add(action);
    	list.add(new WaitAction(lower, upper));
    	return list;
    }
}
