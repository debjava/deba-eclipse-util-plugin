package com.ddlab.sysutil.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ddlab.rnd.systool.CurrPortExe;
import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;
import com.ddlab.sysutil.beans.SysInfoBean;

/**
 * This is a utility class which provides handy methods for file related.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class FileUtil {

	/**
	 * Method to read the file and returning the contents of file as text or
	 * string
	 * 
	 * @param filePath
	 * @return
	 */
	public synchronized List<String> getContentsAsList(String filePath) {

		List<String> processList = new ArrayList<String>();

		Reader reader = null;
		BufferedReader buffReader = null;
		try {
			reader = new FileReader(filePath);
			buffReader = new BufferedReader(reader);
			String line = null;
			while ((line = buffReader.readLine()) != null) {
				processList.add(line);
			}

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (buffReader != null)
					buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return processList;
	}

	/**
	 * Method used to generate the process details in a file by executing the
	 * command
	 * 
	 * @param filePath
	 * @return
	 */
	public String generateProcessList(String filePath) {
		String command = "cmd.exe /C " + System.getenv("windir") + "/system32/"
				+ "tasklist.exe /fo CSV > " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error err) {
			err.printStackTrace();
		}
		return filePath;
	}

	/**
	 * Method used to generate the os system details in file system
	 * 
	 * @param filePath
	 * @return
	 */
	public String generateSysInfoProcessList(String filePath) {
		String command = "cmd.exe /C " + System.getenv("windir") + "/system32/"
				+ "systeminfo.exe /fo LIST > " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error err) {
			err.printStackTrace();
		}
		return filePath;
	}

	/**
	 * Method used to generate the port details in a file by executing the
	 * command.
	 * 
	 * @param filePath
	 */
	public void generateCurrPortList(String filePath) {
		InputStream src = null;
		try {
			src = (InputStream) CurrPortExe.class.getResource("cports.exe")
					.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File exeTempFile = null;
		try {
			exeTempFile = File.createTempFile("cports", ".exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(exeTempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] temp = new byte[32768];
		int rc;
		try {
			while ((rc = src.read(temp)) > 0)
				out.write(temp, 0, rc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (src != null)
					src.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// exeTempFile.deleteOnExit();
		try {
			Process p = Runtime.getRuntime().exec(
					exeTempFile.toString() + " /stab " + filePath);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// exeTempFile.delete();
	}

	/**
	 * Method used to provide the system details in Propeties file.
	 * 
	 * @param lineList
	 * @return
	 */
	public List<SysInfoBean> getSysInfoProperties(List<String> lineList) {
		List<SysInfoBean> sysInfoList = new ArrayList<SysInfoBean>();
		// Properties prop = new Properties();
		for (String line : lineList) {
			if (line.startsWith(" ") || line.startsWith("Hotfix")
					|| line.length() == 0 || line.startsWith("Network")) {
				continue;
			} else {
				String key = line.substring(0, line.indexOf(":"));
				String value = line.substring(line.indexOf(":") + 1);
				SysInfoBean sysInfoBean = new SysInfoBean(key, value.trim());
				sysInfoList.add(sysInfoBean);
			}
		}
		return sysInfoList;
	}

	/**
	 * Method used to provide the system info.
	 * 
	 * @param filePath
	 * @return
	 */
	public List<SysInfoBean> getSystemInfo(String filePath) {
		List<SysInfoBean> sysInfoList = new ArrayList<SysInfoBean>();
		try {
			sysInfoList.add(new SysInfoBean("IP Address", getIPAddress()));
			sysInfoList.add(new SysInfoBean("MAC Address", getMACAddress()));
			generateSysInfoProcessList(filePath);
			List<String> lineList = new FileUtil().getContentsAsList(filePath);
			sysInfoList.addAll(getSysInfoProperties(lineList));

			Properties javaProp = System.getProperties();
			String javaVMName = javaProp.getProperty("java.vm.name");
			String javaVersion = javaProp.getProperty("java.runtime.version");
			String userName = javaProp.getProperty("user.name");
			String javaHome = javaProp.getProperty("java.home");

			sysInfoList.add(new SysInfoBean("Java VM", javaVMName));
			sysInfoList.add(new SysInfoBean("Java Version", javaVersion));
			sysInfoList.add(new SysInfoBean("User Name", userName));
			sysInfoList.add(new SysInfoBean("Java Home", javaHome));
		} catch (Exception e) {
			// No issue , let it return blank properties
			e.printStackTrace();
		}
		return sysInfoList;
	}

	/**
	 * Method to provide the IP address of the system.
	 * 
	 * @return
	 */
	private String getIPAddress() {
		String ipAddress = "";
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ipAddress;
	}

	/**
	 * Method to provide the MAC Address
	 * 
	 * @return
	 */
	private String getMACAddress() {
		StringBuilder sb = new StringBuilder();
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			PluginLogger.info("Current IP address : " + ip.getHostAddress());
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			PluginLogger.info("Current MAC address : ");
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			PluginLogger.info(sb.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
