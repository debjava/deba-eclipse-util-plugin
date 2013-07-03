package com.ddlab.sysutil.tasks;

import java.util.List;

import com.ddlab.sysutil.beans.TaskListProcesBean;

/**
 * This class is used to provide the system details.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SysTask implements Runnable {

	/**
	 * File path to access the system properties
	 */
	private String filePath = null;
	/**
	 * List containing the system details
	 */
	private List<TaskListProcesBean> tlpBeanList = null;

	/**
	 * Default constructor
	 * 
	 * @param filePath
	 * @param tlpBeanList
	 */
	public SysTask(String filePath, List<TaskListProcesBean> tlpBeanList) {
		this.filePath = filePath;
		this.tlpBeanList = tlpBeanList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		/*
		 * Details of system properties gathering
		 */
		System.out.println("TaskList File Path---->" + filePath);
		FileUtil fileUtil = new FileUtil();
		try {
			fileUtil.generateProcessList(filePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			List<String> processList = fileUtil.getContentsAsList(filePath);
			for (String process : processList) {
				String[] processes = process.split("\"");
				TaskListProcesBean tlPBean = new TaskListProcesBean();
				tlPBean.setProcessId(processes[3]);
				tlPBean.setProcessName(processes[1]);
				tlPBean.setMemUsage(processes[9].replaceAll("\"", ""));
				tlpBeanList.add(tlPBean);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// new File( filePath ).delete();
	}

}
