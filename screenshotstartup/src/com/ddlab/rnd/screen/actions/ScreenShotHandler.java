package com.ddlab.rnd.screen.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ddlab.rnd.eclipse.plugin.util.PluginUtil;

/**
 * This class is used to handle the key binding functionality for caturing the
 * screenshot on pressing of (CTRL+`) or CONTROL+Tilde key.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ScreenShotHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		PluginUtil.saveScreenShot(window);
		return null;
	}

}
