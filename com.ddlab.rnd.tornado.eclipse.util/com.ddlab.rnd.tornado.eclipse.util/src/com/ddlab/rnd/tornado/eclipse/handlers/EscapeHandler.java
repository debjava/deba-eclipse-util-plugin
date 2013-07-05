package com.ddlab.rnd.tornado.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ddlab.rnd.tornado.eclipse.util.PluginUtil;
import com.ddlab.rnd.tornado.eclipse.util.ScreenUtil;
import com.ddlab.rnd.tornado.eclipse.util.ScreenUtil4;

/**
 * This class is used as Key Binding Handler to bring back the maximized window
 * to normal mode.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class EscapeHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands
	 * .ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*
		 * Handler to normalize the full screen
		 */
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		if (PluginUtil.getEclipseVersion() > 3.7)
			ScreenUtil4.normalize(window);
		else
			ScreenUtil.normalize(window);
		return null;
	}
}
