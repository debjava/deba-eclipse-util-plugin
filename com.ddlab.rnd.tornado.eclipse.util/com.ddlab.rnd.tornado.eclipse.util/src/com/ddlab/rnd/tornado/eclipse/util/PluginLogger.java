package com.ddlab.rnd.tornado.eclipse.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * This is a dialog class which displays about the features for this plugin.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class PluginLogger {

	/**
	 * Method to log the information
	 * 
	 * @param message
	 */
	public static void info(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	/**
	 * Method to log the error
	 * 
	 * @param exception
	 */
	public static void error(Throwable exception) {
		error("DebaUtilPlugin UnExpected Exception ...\n", exception); //$NON-NLS-1$
	}

	/**
	 * Method to log the error
	 * 
	 * @param message
	 * @param exception
	 */
	public static void error(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	/**
	 * Method to log in your custom way
	 * 
	 * @param severity
	 * @param code
	 * @param message
	 * @param exception
	 */
	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	/**
	 * Method to create a status in your own way and to log the information
	 * 
	 * @param severity
	 * @param code
	 * @param message
	 * @param exception
	 * @return
	 */
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, Activator.getDefault().getBundle()
				.getSymbolicName(), code, message, exception);
	}

	/**
	 * Method to log the created status
	 * 
	 * @param status
	 */
	public static void log(IStatus status) {
		Activator.getDefault().getLog().log(status);
	}
}
