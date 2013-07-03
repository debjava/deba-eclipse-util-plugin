package com.ddlab.rnd.screen.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.ddlab.rnd.eclipse.plugin.util.PluginUtil;

/**
 * This class provides the implementation for the screenshot capture
 * functionality. This is an action class for this eclipse plugin.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ScreenShotAction extends Action {

	/**
	 * Workbench window
	 */
	private IWorkbenchWindow window = null;

	/**
	 * Default constructor
	 */
	public ScreenShotAction() {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		init();
	}

	/**
	 * Method to initialize the action.
	 */
	private void init() {
		try {
			setText("Screen Capture");
			setToolTipText("Take and save screenshot (CTRL+`)");
			ImageDescriptor ids = ImageDescriptor.createFromImage(PluginUtil
					.getScreenShotImage());
			setImageDescriptor(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		PluginUtil.saveScreenShot(window);
	}
}
