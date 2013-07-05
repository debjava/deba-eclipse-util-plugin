package com.ddlab.sysutil.tasks;

import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;
import com.ddlab.sysutil.beans.TaskListProcesBean;

/**
 * This class is used to kill a process with the process id
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class TaskKiller {

	// private static String taskKillCmd = "cmd.exe /C "+System.getenv("windir")
	// +"/system32/"+"taskkill.exe /f /pid ";

	/**
	 * Method to kill the Process Id
	 * 
	 * @param tlpBean
	 */
	public static void kill(TaskListProcesBean tlpBean) {
		// Command Structure should be "taskkill /f /pid 1092"
		String taskKillCmd = "cmd.exe /C " + System.getenv("windir")
				+ "/system32/" + "taskkill.exe /f /pid ";
		try {
			String processId = tlpBean.getProcessId();
			PluginLogger.info("ProcessId----->" + processId);
			taskKillCmd = taskKillCmd + processId;
			PluginLogger.info("Complete Command :::" + taskKillCmd);
			Process p = Runtime.getRuntime().exec(taskKillCmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
