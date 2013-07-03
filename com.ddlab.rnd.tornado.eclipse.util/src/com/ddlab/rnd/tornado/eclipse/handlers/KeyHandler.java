package com.ddlab.rnd.tornado.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ddlab.rnd.tornado.eclipse.util.ScreenUtil;

/**
 * This class is used for key binding for the full screen functionality. This
 * handler class will be activated when the used presses CTRL+TAB to maximize
 * the screen or to make the full screen or bring back to normal mode
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class KeyHandler extends AbstractHandler {

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
		 * Code for screen maximization and normalization
		 */
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		ScreenUtil.perform(window);
		return null;
	}
}
