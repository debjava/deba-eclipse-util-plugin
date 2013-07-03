package com.ddlab.rnd.tornado.eclipse.util.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.ddlab.rnd.tornado.eclipse.threads.ZipFilesJobRunner;
import com.ddlab.rnd.tornado.eclipse.util.Activator;
import com.ddlab.rnd.tornado.eclipse.util.PluginConstants;
import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * This action class is used to zip the files and send to Desktop
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ZipFilesAction implements IWorkbenchWindowActionDelegate {
	/**
	 * Default Window
	 */
	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	/**
	 * Selection for the files
	 */
	private ISelection selection;

	/**
	 * Default constructor
	 */
	public ZipFilesAction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		/*
		 * Operation to execute
		 */
		if (selection instanceof IStructuredSelection) {
			String send2PathVal = Activator.getDefault().getPreferenceStore()
					.getString(PluginConstants.SEND_2_PATH);
			send2PathVal = (send2PathVal == null) ? PluginConstants.DEFAULT_DESKTOP_PATH
					: send2PathVal;
			try {
				IStructuredSelection iSelection = (IStructuredSelection) selection;
				Thread th1 = new Thread(new ZipFilesJobRunner(iSelection));
				th1.start();
			} catch (Exception e) {
				e.printStackTrace();
				PluginLogger.error(e);
				MessageDialog
						.openError(new Shell(), "Error",
								"Unexpected Error.\nReport to debadatta.mishra@gmail.com");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}