package com.ddlab.rnd.tornado.eclipse.util.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * This action class is used to copy the file/s in windows style
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class WindowsStyleCopyAction implements IWorkbenchWindowActionDelegate {
	/**
	 * Default Window
	 */
	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	/**
	 * Selection for the files and folders
	 */
	private ISelection selection;

	/**
	 * Default constructor
	 */
	public WindowsStyleCopyAction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			StringBuilder pathBuilder = new StringBuilder();
			Clipboard clipboard = null;
			try {
				Iterator<?> itr = ((IStructuredSelection) selection).iterator();
				while (itr.hasNext()) {
					Object selctionElement = itr.next();
					if (selctionElement instanceof IAdaptable) {
						IAdaptable adaptable = (IAdaptable) selctionElement;
						IResource resource = (IResource) adaptable
								.getAdapter(IResource.class);
						System.out.println("OS Path : "
								+ resource.getLocation().toOSString());
						String resourcePath = resource.getLocation()
								.toOSString();
						pathBuilder.append(resourcePath).append("\n");
					}
				}
				if (pathBuilder.toString().endsWith("\n"))
					pathBuilder = pathBuilder.delete(pathBuilder
							.lastIndexOf("\n"), pathBuilder.lastIndexOf("\n"));
				PluginLogger.info("All Path :::" + pathBuilder.toString());
				/*
				 * Copy to clipboard
				 */
				clipboard = new Clipboard(Display.getCurrent());
				TextTransfer textTransfer = TextTransfer.getInstance();
				Transfer[] transfers = new Transfer[] { textTransfer };
				String filePathsStr = pathBuilder.toString().endsWith("\n") ? pathBuilder
						.substring(0, pathBuilder.lastIndexOf("\n"))
						: pathBuilder.toString();
				Object[] data = new Object[] { filePathsStr };
				clipboard.setContents(data, transfers);
			} catch (Exception e) {
				e.printStackTrace();
				PluginLogger.error(e);
				MessageDialog
						.openError(new Shell(), "Error",
								"Unexpected Error.\nReport to debadatta.mishra@gmail.com");
			} finally {
				if (clipboard != null)
					clipboard.dispose();
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