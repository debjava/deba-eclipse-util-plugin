package com.ddlab.rnd.eclipse.plugin.util;

import org.eclipse.jface.action.Action;

import com.ddlab.rnd.screen.actions.ScreenShotAction;
import com.ddlab.rnd.screen.actions.SplitEditorAction;

/**
 * This class is used provide handle actions for scren capture and editor split
 * functionality. It is designed on the base line of Flyweight pattern.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ActionUtil {

	private static ScreenShotAction screenShotAction = new ScreenShotAction();
	private static SplitEditorAction splitAction = new SplitEditorAction();

	private ActionUtil() {

	}

	public static Action getScreenShotAction() {
		return screenShotAction;
	}

	public static Action getSplitAction() {
		return splitAction;
	}
}
