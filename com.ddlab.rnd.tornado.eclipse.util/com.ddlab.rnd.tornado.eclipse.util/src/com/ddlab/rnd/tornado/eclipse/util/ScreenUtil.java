package com.ddlab.rnd.tornado.eclipse.util;

import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.Canvas;
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
public class ScreenUtil {
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
		Shell shell = window.getShell();
		boolean statusBarFlag = Activator.getDefault().getPreferenceStore()
				.getBoolean("STATUS_FIELD_VALUE");

		if (maxFlag) {
			normalize(window);
		} else {
			shell.setMenuBar(null);
			hideToolBar(window);
			shell.setFullScreen(true);
			maxFlag = true;
			if (statusBarFlag) {
				hideStatusBar(window);
			}
			shell.layout();
		}
	}

	/**
	 * Method to hide the Eclipse Tool Bar
	 * 
	 * @param window
	 */
	private static void hideToolBar(IWorkbenchWindow window) {
		Control[] children = window.getShell().getChildren();
		for (Control child : children) {
			// Hide Tool Bar
			if (child.getClass().equals(CBanner.class)) {
				CBanner banner = (CBanner) child;
				banner.setVisible(false);
				break;
			}
		}
	}

	/**
	 * Method to hide the status bar.
	 * 
	 * @param window
	 */
	private static void hideStatusBar(IWorkbenchWindow window) {
		Shell shell = window.getShell();
		for (Control c : shell.getChildren()) {
			if (c.getClass().equals(Canvas.class)
					|| c.getClass().equals(Composite.class)) {
				continue;
			} else {
				c.setVisible(false);
			}
		}
	}

	/**
	 * Method to unhide status bar
	 * 
	 * @param window
	 */
	private static void unHideStatusBar(IWorkbenchWindow window) {
		Shell shell = window.getShell();
		for (Control c : shell.getChildren()) {
			if (c.getClass().equals(Canvas.class)
					|| c.getClass().equals(Composite.class)) {
				continue;
			} else {
				c.setVisible(true);
			}
		}
	}

	/**
	 * Method to bring back the full screen to normal mode.
	 * 
	 * @param window
	 */
	public static void normalize(IWorkbenchWindow window) {
		System.out.println("Max Flag :::" + maxFlag);
		if (maxFlag) {
			Shell shell = window.getShell();
			shell.setMenuBar(Activator.getDefault().getMenuBar());
			Control[] children = shell.getChildren();
			for (Control child : children) {
				if (child.getClass().equals(CBanner.class)) {
					CBanner banner = (CBanner) child;
					banner.setVisible(true);
				}
			}
			unHideStatusBar(window);
			shell.setFullScreen(false);
			maxFlag = false;
		}
	}
}
