package com.ddlab.sysutil.tasks;
import java.util.List;

import com.ddlab.sysutil.beans.TaskListProcesBean;
/**This class is used to provide the port details.
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 *
 */
public class CurrTask implements Runnable {
	
	private String filePath = null;
	private List<TaskListProcesBean> cpBeanList = null;
	
	public CurrTask( String filePath , List<TaskListProcesBean> cpBeanList  )
	{
		this.filePath = filePath;
		this.cpBeanList = cpBeanList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		FileUtil fileUtil = new FileUtil();
		try {
			fileUtil.generateCurrPortList(filePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			List<String> currPortList = fileUtil.getContentsAsList(filePath);
			for( String process : currPortList )
			{
				String[] processes = process.split("\t");
				TaskListProcesBean cpBean = new TaskListProcesBean();
				String pid = ( processes[1] != null ) ? processes[1] : "";
				cpBean.setProcessId(pid);
				String protocol = ( processes[2] != null ) ? processes[2] : "";
				cpBean.setProtocol(protocol);
				String localPort = ( processes[3] != null ) ? processes[3] : "";
				cpBean.setLocalPort(localPort);
				String status = ( processes[10] != null ) ? processes[10] : "";
				cpBean.setStatus(status);
				cpBeanList.add(cpBean);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		new File( filePath ).delete();
	}

	
}
