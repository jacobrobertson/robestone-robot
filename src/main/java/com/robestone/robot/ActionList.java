/*
 * @version Jan 14, 2006
 */
package com.robestone.robot;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * @author Jacob Robertson
 */
public class ActionList implements RobotAction {

	private Logger logger = LogManager.getLogger(getClass().getSimpleName());

	private List<RobotAction> actionList;
	private List<String> messagesList = new ArrayList<String>();
	private int times;
	private int innerWaitMin;
	private int innerWaitMax;
	private String message;

	/**
	 * Just runs the actions once.
	 */
	public ActionList() {
		this(1);
	}
	public ActionList(List<RobotAction> actionList) {
		init(actionList, 1, 0, 0, null);
	}

	/**
	 * @param loop true indicates to repeat the list forever
	 */
	public ActionList(boolean loop) {
		this(loop ? -1 : 1);
	}
	/**
	 * @param times indicates how many times to repeat the loop.  1 is the default
	 */
	public ActionList(int times) {
		init(null, times, 0, 0, null);
	}
	/**
	 * @param times negative means forever
	 */
	public ActionList(RobotAction action, int times, int innerWait, String message) {
		this(action, times, innerWait, innerWait, message);
	}
	public ActionList(RobotAction action, int times, int innerWaitMin, int innerWaitMax, String message) {
		List<RobotAction> actions = new ArrayList<RobotAction>();
		init(actions, times, innerWaitMin, innerWaitMax, message);
		addAction(action);
	}
	public ActionList(int times, int innerWait, String message) {
		init(null, times, innerWait, innerWait, message);
	}
	private void init(List<RobotAction> actionList, int times, int innerWaitMin, int innerWaitMax, String message) {
		this.actionList = actionList;
		if (this.actionList == null) {
			this.actionList = new ArrayList<RobotAction>();
		}
		this.times = times;
		this.innerWaitMin = innerWaitMin;
		this.innerWaitMax = innerWaitMax;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public void add(RobotAction action) {
		addAction(action);
	}
	/**
	 * Same as "add".
	 */
	public void addAction(RobotAction action) {
		addAction(action, null);
	}
	public void addAction(RobotAction action, String message) {
		actionList.add(action);
		if (message == null) {
			message = this.message;
		}
		messagesList.add(message);
	}
	public void setInnerWait(int innerWait) {
		this.innerWaitMax = innerWait;
		this.innerWaitMin = innerWait;
	}
	public void setInnerWait(int innerWaitMax, int innerWaitMin) {
		this.innerWaitMax = innerWaitMax;
		this.innerWaitMin = innerWaitMin;
	}
	public RobotActionResponse run(RobotActionRequest request) {
		RobotActionResponse last = null;
		int count = 0;
		while (true) {
			count++;
			log(count + "/" + times, message);
			last = runAll(request);
			if (times > 0 && count >= times) {
				break;
			}
		}
		return last;
	}
	private RobotActionResponse runAll(RobotActionRequest request) {
		RobotActionResponse last = RobotActionResponse.getFail();
		int len = actionList.size();
		for (int i = 0; i < len; i++) {
			RobotAction action = actionList.get(i);
			String message = messagesList.get(i);
			log("Running: " + action, message);
			last = action.run(request);
			log("Ran: " + action, message);
			WaitAction.wait(innerWaitMin, innerWaitMax);
		}
		return last;
	}
	private void log(String m, String message) {
		if (message != null) {
			logger.info(message + " -- " + m);
		}
	}

}
