package com.ddlab.rnd.tornado.eclipse.util.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.ddlab.rnd.tornado.eclipse.util.PluginUtil;
import com.ddlab.rnd.tornado.eclipse.util.ScreenUtil;
import com.ddlab.rnd.tornado.eclipse.util.ScreenUtil4;

/**
 * This class is used to maximize the Eclipse window to increase the
 * readability.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class FullScreenAction implements IWorkbenchWindowActionDelegate {

	/**
	 * Default Eclipse Window
	 */
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public FullScreenAction() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		if (PluginUtil.getEclipseVersion() > 3.7)
			ScreenUtil4.perform(window);
		else
			ScreenUtil.perform(window);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
