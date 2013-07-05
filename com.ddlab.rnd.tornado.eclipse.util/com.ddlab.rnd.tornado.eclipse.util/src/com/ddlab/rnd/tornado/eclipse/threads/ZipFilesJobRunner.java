package com.ddlab.rnd.tornado.eclipse.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
 * This class is used to zip the file/s and send to Desktop or the path
 * configured in Eclipse Preference. This class performs the functionality in a
 * separate thread.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class ZipFilesJobRunner implements Runnable {

	/**
	 * Increment counter for the file naming convention
	 */
	private static int counter = 0;
	/**
	 * Selection for the selected file/s
	 */
	private IStructuredSelection iSelection = null;

	/**
	 * Default constructor
	 * 
	 * @param iSelection
	 */
	public ZipFilesJobRunner(IStructuredSelection iSelection) {
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
		 * Execting the functionality in the Eclipse progress thread
		 */
		runWorkspaceJob(iSelection);
	}

	/**
	 * Method used to zip file/s in the Eclipse UI Job thread to provide a
	 * progress indicator.
	 * 
	 * @param iSelection
	 */
	public void runWorkspaceJob(final IStructuredSelection iSelection) {
		final int totalFiles = iSelection.size();
		counter++;
		String send2PathVal = Activator.getDefault().getPreferenceStore()
				.getString(PluginConstants.ZIP_FILE_PATH);
		send2PathVal = (send2PathVal == null || send2PathVal.equals("")) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: send2PathVal;
		String zipDirPath = send2PathVal;
		File zipFile = new File(zipDirPath);
		zipDirPath = (!zipFile.exists()) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: zipDirPath;
		final String zipFilePath = zipDirPath + File.separator + "files_"
				+ counter + ".zip";
		PluginLogger.info("Zip File Path ::: " + zipFilePath);
		final byte[] buffer = new byte[1024];
		try {
			FileOutputStream fos = new FileOutputStream(zipFilePath);
			final ZipOutputStream zos = new ZipOutputStream(fos);

			Job job = new Job("Files Zipping In Progress...") {
				protected IStatus run(IProgressMonitor monitor) {
					try {
						int eachP = (100 / totalFiles);
						int counter = 0;
						// monitor.beginTask("Files Zipping In Progress", 100);
						monitor.beginTask("", 100);
						// monitor.setTaskName("Files Zipping In Progress ...");

						Iterator<?> itr = iSelection.iterator();
						while (itr.hasNext()) {
							Object selctionElement = itr.next();
							if (selctionElement instanceof IAdaptable) {
								IAdaptable adaptable = (IAdaptable) selctionElement;
								IResource resource = (IResource) adaptable
										.getAdapter(IResource.class);
								String resourcePath = resource.getLocationURI()
										.getPath();

								String fileName = resource.getName();
								monitor.subTask("Zipping File ... "
										+ resource.getName());
								ZipEntry ze = new ZipEntry(fileName);
								zos.putNextEntry(ze);
								FileInputStream in = new FileInputStream(
										resourcePath);
								int len;
								while ((len = in.read(buffer)) > 0) {
									zos.write(buffer, 0, len);
								}
								in.close();

								if (monitor.isCanceled())
									break;

								counter = counter + eachP;
								monitor.worked(counter);
							}
						}
						zos.closeEntry();
						zos.close();
						monitor.worked(100);
					} catch (Exception e) {
						e.printStackTrace();
						PluginLogger
								.error(
										"UnExpected Excepton while zipping the files and sending to Desktop ...\n",
										e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();
		} catch (Exception e) {
			e.printStackTrace();
			PluginLogger
					.error(
							"UnExpected Excepton while zipping the files and sending to Desktop ...\n",
							e);
			MessageDialog.openError(new Shell(), "Error",
					"Unexpected Error.\nReport to debadatta.mishra@gmail.com");
		}
	}
}
