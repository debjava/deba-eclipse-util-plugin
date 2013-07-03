package com.ddlab.rnd.screen.actions;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.ddlab.rnd.eclipse.plugin.util.ActionUtil;

/**
 * This class is used to handle the startup functionality at Eclpse startup.
 * This plugin creates two actions in the eclipse status bar. One action is for
 * eclipse screenshot capture functionality and another for editor splitting
 * functionality.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
@SuppressWarnings("restriction")
public class ScreenShotStartUp implements IStartup {

	/**
	 * This method is used to initialize the actions at the eclipse startup. It
	 * creates two action in the statusline manager for screen capture and
	 * editor splitting functionlity.
	 */
	public void init() {
		try {
			final IWorkbench workbench = PlatformUI.getWorkbench();
			workbench.getDisplay().asyncExec(new Runnable() {
				public void run() {
					final IWorkbenchWindow window = workbench
							.getActiveWorkbenchWindow();
					if (window != null) {
						StatusLineManager sm = ((WorkbenchWindow) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow())
								.getStatusLineManager();
						sm.insertBefore(StatusLineManager.BEGIN_GROUP,
								ActionUtil.getScreenShotAction());
						sm.insertBefore(StatusLineManager.BEGIN_GROUP,
								ActionUtil.getSplitAction());
						sm.update(true);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		init();
	}
}
