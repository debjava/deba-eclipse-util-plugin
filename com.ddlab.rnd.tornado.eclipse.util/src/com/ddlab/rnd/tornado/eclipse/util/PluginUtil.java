package com.ddlab.rnd.tornado.eclipse.util;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
/**This is a utility class containing handy eclipse util methods.
 * This class provides the path for the eclipse plugin, dropins and others
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 *
 */
public class PluginUtil {
	
	/**Method for obtaining the driopins path
	 * @return Dropins path
	 */
	public static String getEclipseDropinsPath() {
		return new Path(Platform.getInstallLocation().getURL().getPath()
				.toString()).append("/dropins").toString();
	}
	
	/**Method for obtaining the plugins path
	 * @return Plugins path
	 */
	public static String getEclipsePluginsPath() {
		return new Path(Platform.getInstallLocation().getURL().getPath()
				.toString()).append("/plugins").toString();
	}
	
	/**Method for obtaining the Eclipse Home path
	 * @return Eclipse Home path
	 */
	public static String getEclipseRootPath() {
		return new Path(Platform.getInstallLocation().getURL().getPath()
				.toString()).toString();
	}
	
	/**Method for internal data folder path
	 * @return data folder path
	 */
	public static String getDropinsDataPath()
	{
		String dropInsDataPath = null;
		String dropInPath = getEclipseDropinsPath();
		dropInsDataPath = dropInPath+File.separator+"data";
		File file = new File(dropInsDataPath);
		if( !file.exists() ) file.mkdirs();
		return dropInsDataPath;
	}
	
}
