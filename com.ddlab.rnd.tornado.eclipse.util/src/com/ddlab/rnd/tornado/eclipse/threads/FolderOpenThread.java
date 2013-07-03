package com.ddlab.rnd.tornado.eclipse.threads;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

/**
 * This class is used to open the selected folder or file in the Windows
 * Explorer in a separate thread.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class FolderOpenThread extends Thread {

	/**
	 * Folder path to open in Windows Explorer
	 */
	private String folderPath;

	/**
	 * Default Constructor
	 * 
	 * @param folderPath
	 */
	public FolderOpenThread(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * Method to open the Windows Explorer by executing the explorer command for
	 * the selected path.
	 * 
	 * @param folderPath
	 */
	public void openWindowsExplorer(String folderPath) {
		try {
			if (folderPath == null)
				throw new NullPointerException(
						"File/Folder path is null, select a file or a folder to open.");
			String command = "Explorer /select,";
			Runtime.getRuntime().exec(
					command + new File(folderPath).getAbsolutePath());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			PluginLogger.error(
					"UnExpected Excepton while executing folder opener ...\n",
					npe);
			MessageDialog.openError(new Shell(), "Error",
					"NullPointer Exception while opening Windows Explorer."
							+ "\nReport to debadatta.mishra@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error",
					"UnExpected Error while opening Windows Explorer."
							+ "\nReport to debadatta.mishra@gmail.com");
			MessageDialog.openError(new Shell(), "Error",
					"Unexpected Error while opening Windows Explorer."
							+ "\nReport to debadatta.mishra@gmail.com");
		}
	}

	@Override
	public void run() {
		openWindowsExplorer(folderPath);
	}

}
