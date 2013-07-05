package com.ddlab.sysutil.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.ddlab.sysutil.beans.TaskListProcesBean;

/**
 * This is a significant class to provide a single engagement view for the
 * system details.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SysDetailsProcessor {
	/**
	 * File Path for the task details
	 */
	private String taskFilePath = null;
	/**
	 * List for the task details
	 */
	private List<TaskListProcesBean> tlpBeanList = new ArrayList<TaskListProcesBean>();
	/**
	 * File path for the port details
	 */
	private String cpFilePath = null;
	/**
	 * List for the port details
	 */
	private List<TaskListProcesBean> cpBeanList = new ArrayList<TaskListProcesBean>();

	/**
	 * Defaut constructor
	 * 
	 * @param taskFilePath
	 * @param cpFilePath
	 */
	public SysDetailsProcessor(String taskFilePath, String cpFilePath) {
		this.taskFilePath = taskFilePath;
		this.cpFilePath = cpFilePath;
	}

	/**
	 * Method used to provide the task details from Windows.
	 * 
	 * @return
	 */
	public List<TaskListProcesBean> getProcessList() {
		SysTask sysTask = new SysTask(taskFilePath, tlpBeanList);
		CurrTask currTask = new CurrTask(cpFilePath, cpBeanList);
		List<TaskListProcesBean> sysList = new ArrayList<TaskListProcesBean>();

		try {
			ExecutorService exService = Executors.newFixedThreadPool(2);
			exService.execute(sysTask);
			exService.execute(currTask);
			exService.shutdown();
			while (!exService.isTerminated()) {
			}
			// System.out.println("Terminated .......");//Thread terminated

			for (TaskListProcesBean tlpbCP : cpBeanList) {
				for (TaskListProcesBean tlpbTS : tlpBeanList) {
					if (tlpbCP.equals(tlpbTS)) {
						tlpbCP.setProcessName(tlpbTS.getProcessName());
						tlpbCP.setMemUsage(tlpbTS.getMemUsage());
						sysList.add(tlpbCP);
						break;
					} else {
						continue;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysList;
	}
}
