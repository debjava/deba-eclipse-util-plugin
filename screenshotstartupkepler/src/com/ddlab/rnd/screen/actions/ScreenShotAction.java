package com.ddlab.rnd.screen.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import com.ddlab.rnd.eclipse.plugin.util.PluginUtil;

public class ScreenShotAction extends Action {

	private IWorkbenchWindow window = null;

	public ScreenShotAction() {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		init();
	}

	private void init() {
		try {
			setText("Screen Capture");
			setToolTipText("Take and save screenshot (CTRL+`)");
			ImageDescriptor ids = ImageDescriptor
					.createFromImage(PluginUtil.getScreenShotImage());
			setImageDescriptor(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		PluginUtil.saveScreenShot(window);
	}

}
