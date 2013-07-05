package com.ddlab.sysutil.beans;

import java.io.Serializable;
/**Bean class for hodling the information for the process details and port details
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 *
 */
public class TaskListProcesBean implements Serializable {
	
	private static final long serialVersionUID = 4770416845050677932L;
	private String processName = "";
	private String processId = "";
	private String memUsage = "";
	
	//For curr ports
	private String protocol = "";
	private String localPort = "";
	private String status = "";
	//~~ Getter and Setter methods
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		processId = processId.replaceAll("\"", "");
		this.processId = processId;
	}
	public String getMemUsage() {
		return memUsage;
	}
	public void setMemUsage(String memUsage) {
		this.memUsage = memUsage;
	}
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getLocalPort() {
		return localPort;
	}
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((processId == null) ? 0 : processId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskListProcesBean other = (TaskListProcesBean) obj;
		if (processId == null) {
			if (other.processId != null)
				return false;
		} else if (!processId.equals(other.processId))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskListProcesBean [processName=" + processName
				+ ", processId=" + processId + ", memUsage=" + memUsage
				+ ", protocol=" + protocol + ", localPort=" + localPort
				+ ", status=" + status + "]";
	}

	
	

}
