package com.ddlab.rnd.eclipse.plugin.util;

import java.io.File;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.ddlab.util.images.ScreenShotImages;

@SuppressWarnings("restriction")
public class PluginUtil {

	private static int counter = 0;

	public static Image getScreenShotImage()
	{
		Image image = new Image(Display.getCurrent(),
				ScreenShotImages.class.getResourceAsStream(
						"sc16.png"));
		return image;
	}

	public static Image getSplitImage()
	{
		Image image = new Image(Display.getCurrent(),
				ScreenShotImages.class.getResourceAsStream(
				"editorspilit.png"));
		return image;
	}

	public static void saveScreenShot( IWorkbenchWindow window )
	{
		try 
		{
			Display display = Display.getCurrent();
			GC gc = new GC(display);
			Rectangle disrect = display.getClientArea();
			Rectangle client = display.getActiveShell().getClientArea();
			StatusLineManager sm = ((WorkbenchWindow) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow()).getStatusLineManager();
			Rectangle smRect = sm.getControl().getBounds();
			/*
			 * Calculate the height so that screen shot icon will be eliminated
			 */
			int heightVal = disrect.height - smRect.height;
			/*
			 * Form the rectangle smartly
			 */
			Rectangle imgArea = new Rectangle(0,0,client.width,heightVal);
			Image image = new Image(display, imgArea);
			gc.copyArea(image, client.x, client.y);
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			counter++;
			String desktopImgPath = System.getProperty("user.home")
			+ File.separator + "Desktop" + File.separator + "screen"
			+ counter + ".png";
			imageLoader.save(desktopImgPath, SWT.IMAGE_PNG);
			gc.dispose();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
