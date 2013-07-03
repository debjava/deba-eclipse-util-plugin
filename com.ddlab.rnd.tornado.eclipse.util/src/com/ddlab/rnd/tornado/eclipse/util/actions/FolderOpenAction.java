package com.ddlab.rnd.tornado.eclipse.util.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.ddlab.rnd.tornado.eclipse.threads.FolderOpenThread;
import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * This is an action class for opening the file/s and folder/s in Windows
 * Explorer
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class FolderOpenAction implements IObjectActionDelegate {
	/**
	 * For selection
	 */
	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		/*
		 * For executing the functionlity
		 */
		try {
			List<String> pathsList = getDirectoryPaths();
			for (String path : pathsList) {
				Thread exOpenThread = new FolderOpenThread(path);
				exOpenThread.start();
			}
		} catch (NullPointerException npe) {
			PluginLogger.error(npe);
			MessageDialog.openError(new Shell(), "Error", npe.getMessage()
					+ " " + "\nReport to debadatta.mishra@gmail.com");
		} catch (Exception e) {
			PluginLogger.error(e);
			MessageDialog.openError(new Shell(), "Error", e.getMessage() + " "
					+ "\nReport to debadatta.mishra@gmail.com");
		}
	}

	/**
	 * Method to get the location of the directory to open
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getDirectoryPaths() throws Exception {
		List<String> resourceList = new ArrayList<String>();

		Iterator<?> itr = ((IStructuredSelection) selection).iterator();
		while (itr.hasNext()) {
			Object selctionElement = itr.next();
			System.out.println("Selected Element--->" + selctionElement);

			if (selctionElement instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) selctionElement;
				IResource resource = (IResource) adaptable
						.getAdapter(IResource.class);
				String resourcePath = resource.getLocationURI().getPath();
				resourcePath = resourcePath.startsWith("/") ? resourcePath
						.substring(resourcePath.indexOf("/") + 1, resourcePath
								.length()) : resourcePath;
				resourceList.add(resourcePath);
			}
		}
		return resourceList;
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
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub

	}

}
