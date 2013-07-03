package com.ddlab.rnd.tornado.eclipse.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.ddlab.rnd.tornado.eclipse.util.Activator;
import com.ddlab.rnd.tornado.eclipse.util.PluginConstants;
import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * This class is used to copy the selected files from the Eclipse
 * Navigator/Project Explorer etc. It is executed in a separate thread so that
 * other copy action can be executed simultaneously.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class FileCopyJobRunner implements Runnable {

	/**
	 * Selection for the selected element
	 */
	private IStructuredSelection iSelection = null;

	/**
	 * Default constructor
	 * 
	 * @param iSelection
	 */
	public FileCopyJobRunner(IStructuredSelection iSelection) {
		this.iSelection = iSelection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		/*
		 * Based upon the volume of file/s , the entire operation will be
		 * executed in the Eclipse UI Job thread so that user can track the file
		 * copy operation.
		 */
		runWorkspaceJob(iSelection);
	}

	/**
	 * Method used to execute the Eclipse UI job with Progress Indicator
	 * 
	 * @param iSelection
	 */
	public void runWorkspaceJob(final IStructuredSelection iSelection) {
		final int totalFiles = iSelection.size();
		String send2PathVal = Activator.getDefault().getPreferenceStore()
				.getString(PluginConstants.SEND_2_PATH);
		PluginLogger.info("Initial send2Path Value --->" + send2PathVal);
		send2PathVal = (send2PathVal == null || send2PathVal.equals("")) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: send2PathVal;
		PluginLogger.info("Final send2Path Value --->" + send2PathVal);
		final String filePath = send2PathVal;
		try {
			/*
			 * Eclipse UI Event Job for the Progress
			 */
			Job job = new Job("File Copy In Progress...") {
				protected IStatus run(IProgressMonitor monitor) {
					try {
						int eachP = (100 / totalFiles);
						int counter = 0;
						// monitor.beginTask("File Copy In Progress", 100);
						monitor.beginTask("", 100);
						Iterator<?> itr = iSelection.iterator();
						while (itr.hasNext()) {
							Object selctionElement = itr.next();
							if (selctionElement instanceof IAdaptable) {
								IAdaptable adaptable = (IAdaptable) selctionElement;
								IResource resource = (IResource) adaptable
										.getAdapter(IResource.class);
								String resourcePath = resource.getLocationURI()
										.getPath();
								if (monitor.isCanceled())
									break;
								monitor.subTask("Copying file ... "
										+ resource.getName());
								copyFile(resourcePath, filePath);
								counter = counter + eachP;
								monitor.worked(counter);
							}
						}
						monitor.worked(100);
					} catch (Exception e) {
						e.printStackTrace();
						PluginLogger.error(
								"UnExpected Excepton while executing send "
										+ "to files operation in UI Job ...\n",
								e);
					}
					return Status.OK_STATUS;
				}

			};
			job.setUser(true);
			job.schedule();
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error",
					"Unexpected Error.\nReport to debadatta.mishra@gmail.com");
			PluginLogger
					.error(
							"UnExpected Excepton while executing send to files operation ...\n",
							e);
		}
	}

	/**
	 * Method to copy file from source to destinaton
	 * 
	 * @param src
	 * @param destn
	 */
	public void copyFile(String src, String destn) {
		File srcFile = new File(src);
		File destnDir = new File(destn);
		destn = (!destnDir.exists()) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: destn;
		InputStream inStream = null;
		OutputStream outStream = null;
		// byte[] fileBytes = new byte[(int)srcFile.length()];
		byte[] fileBytes = new byte[1024];
		try {
			inStream = new FileInputStream(srcFile);
			outStream = new FileOutputStream(destn + File.separator
					+ srcFile.getName());
			int length;
			while ((length = inStream.read(fileBytes)) > 0) {
				outStream.write(fileBytes, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.flush();
					outStream.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
