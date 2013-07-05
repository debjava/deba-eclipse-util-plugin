package com.ddlab.rnd.tornado.eclipse.util;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * This class is used to provide the utility for eclipse screen to maximize and
 * to minimize.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ScreenUtil4 {
	/**
	 * Boolean flag to track the status of the screen
	 */
	private static boolean maxFlag = false;

	/**
	 * Method to normalize or maximize the eclipse screen.
	 * 
	 * @param window
	 */
	public static void perform(IWorkbenchWindow window) {
		boolean statusBarFlag = Activator.getDefault().getPreferenceStore()
				.getBoolean("STATUS_FIELD_VALUE");
		Shell shell = window.getShell();
		if (maxFlag) {
			normalize(window);
		} else {
			shell.setMenuBar(null);
			Control[] children = shell.getChildren();
			// For ToolBar
			Composite compo1 = (Composite) children[1];
			// Still Extra checking
			if (compo1.getChildren()[0].getClass().getName().equals(
					"org.eclipse.e4.ui.widgets.ImageBasedFrame")) {
				compo1.setVisible(false);
				;
			}
			// For status Line
			Composite compo2 = (Composite) children[2];
			// Still Extra checking
			if (compo2.getChildren()[0].getClass().getName().equals(
					"org.eclipse.jface.action.StatusLine")
					&& statusBarFlag) {
				compo2.setVisible(false);
			}
			maxFlag = true;
			shell.setFullScreen(true);
		}
		shell.layout();
	}

	/**
	 * Method to bring back the full screen to normal mode.
	 * 
	 * @param window
	 */
	public static void normalize(IWorkbenchWindow window) {
		Shell shell = window.getShell();
		shell.setMenuBar(Activator.getDefault().getMenuBar());
		Control[] children = shell.getChildren();
		// For ToolBar
		Composite compo1 = (Composite) children[1];
		// Still Extra checking
		if (compo1.getChildren()[0].getClass().getName().equals(
				"org.eclipse.e4.ui.widgets.ImageBasedFrame")) {
			compo1.setVisible(true);
		}
		Composite compo2 = (Composite) children[2];
		// Still Extra checking
		if (compo2.getChildren()[0].getClass().getName().equals(
				"org.eclipse.jface.action.StatusLine")) {
			compo2.setVisible(true);
			;
		}
		shell.setFullScreen(false);
		maxFlag = false;
	}
}
