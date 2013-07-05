package com.ddlab.rnd.eclipse.plugin.util;

import org.eclipse.jface.action.Action;

import com.ddlab.rnd.screen.actions.ScreenShotAction;
//import com.ddlab.rnd.screen.actions.SplitEditorAction;

public class ActionUtil {
	
	private static ScreenShotAction screenShotAction = new ScreenShotAction();
//	private static SplitEditorAction splitAction = new SplitEditorAction();
	
	private ActionUtil()
	{
		
	}
	
	public static Action getScreenShotAction()
	{
		return screenShotAction;
	}
	
//	public static Action getSplitAction()
//	{
//		return splitAction;
//	}

}
