package com.ddlab.sysutil.beans;

/**
 * Bean class for the system information
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SysInfoBean {

	/**
	 * Name of the system infor to display
	 */
	private String name;
	/**
	 * Value for the system info to display
	 */
	private String value;

	/**
	 * Default constructor
	 * 
	 * @param name
	 * @param value
	 */
	public SysInfoBean(String name, String value) {
		this.name = name;
		this.value = value;
	}

	// ~~ Getter methods
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (name + " = " + value);
	}

}
