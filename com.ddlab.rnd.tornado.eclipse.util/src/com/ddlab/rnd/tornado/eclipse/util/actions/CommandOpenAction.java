package com.ddlab.rnd.tornado.eclipse.util.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.ddlab.rnd.tornado.eclipse.threads.CommandThread;
import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * Action class for executing the command propmt
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class CommandOpenAction implements IObjectActionDelegate {

	/**
	 * Selected artifact
	 */
	private ISelection selection;

	/**
	 * Method to execute the command for the command prompt by passing the
	 * command and the folder path
	 * 
	 * @param command
	 * @param folderPath
	 */
	private void executeCmd(String command, String folderPath) {
		try {
			String actualCmd = "";
			// Command option /C or /K
			String cmdName = "cmd.exe /C";
			String startCmd = "start";
			String pushdCmd = "pushd";
			actualCmd = new StringBuilder(actualCmd).append(cmdName)
					.append(" ").append(startCmd).append(" ").append(pushdCmd)
					.toString();
			// Process process =
			// Runtime.getRuntime().exec("cmd.exe /c start pushd", null, new
			// File(folderPath));
			Thread cmdThread = new CommandThread(actualCmd, folderPath);
			cmdThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			PluginLogger.error(e);
			MessageDialog.openError(new Shell(), "Error",
					"Unexpected Error.\nReport to debadatta.mishra@gmail.com");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		/*
		 * Select and execute the command
		 */
		Iterator<?> itr = ((IStructuredSelection) selection).iterator();
		while (itr.hasNext()) {
			Object selctionElement = itr.next();
			if (selctionElement instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) selctionElement;
				IResource resource = (IResource) adaptable
						.getAdapter(IResource.class);
				String resourcePath = resource.getLocationURI().getPath();

				resourcePath = resourcePath.startsWith("/") ? resourcePath
						.substring(resourcePath.indexOf("/") + 1, resourcePath
								.length()) : resourcePath;
				System.out.println("Resource Path---->" + resourcePath);
				String command = "explorer.exe /select,";
				resourcePath = (selctionElement instanceof IFile) ? resourcePath
						.substring(0, resourcePath.lastIndexOf("/"))
						: resourcePath;
				executeCmd(command, resourcePath);
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
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}
}
