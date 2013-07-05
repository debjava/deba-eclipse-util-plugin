package com.ddlab.rnd.screen.actions;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.ddlab.rnd.eclipse.plugin.util.ActionUtil;

/**
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
@SuppressWarnings("restriction")
public class ScreenShotStartUp implements IStartup {

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
						sm.update(true);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void earlyStartup() {
		init();
	}
}
