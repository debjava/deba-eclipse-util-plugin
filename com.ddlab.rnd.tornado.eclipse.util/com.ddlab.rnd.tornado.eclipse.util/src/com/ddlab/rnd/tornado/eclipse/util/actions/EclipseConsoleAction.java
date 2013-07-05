package com.ddlab.rnd.tornado.eclipse.util.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.ddlab.rnd.tornado.eclipse.threads.EclipseConsoleThread;

/**
 * This action class is used to execute the command in Eclipse console.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class EclipseConsoleAction implements IObjectActionDelegate {

	/**
	 * For Selection
	 */
	private ISelection selection;

	/**
	 * Method to execute the command in eclipse console by opening the Eclipse
	 * console view. Operation gets executed in a separate thread.
	 * 
	 * @param folderPath
	 */
	private void executeCmd(String folderPath) {
		try {
			@SuppressWarnings("unused")
			IViewPart consoleView = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().showView(
							"org.eclipse.ui.console.ConsoleView");
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog
					.openError(
							new Shell(),
							"Error",
							"Unexpected Error while opening Eclipse Console.\nReport to debadatta.mishra@gmail.com");
		}
		Thread consoleTh = new EclipseConsoleThread(folderPath);
		consoleTh.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		/*
		 * Method to execute the operation for Command in Eclipse Console
		 */
		Object selctionElement = ((IStructuredSelection) selection)
				.getFirstElement();
		if (selctionElement instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) selctionElement;
			IResource resource = (IResource) adaptable
					.getAdapter(IResource.class);
			System.out.println("Location :::"
					+ resource.getLocation().toString());
			String resourcePath = resource.getLocation().toString();
			System.out.println("Resource Path ::: " + resourcePath);
			resourcePath = (selctionElement instanceof IFile) ? resourcePath
					.substring(0, resourcePath.lastIndexOf("/")) : resourcePath;
			executeCmd(resourcePath);
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
