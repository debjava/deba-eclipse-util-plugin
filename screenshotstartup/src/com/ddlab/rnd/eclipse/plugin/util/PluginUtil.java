package com.ddlab.rnd.eclipse.plugin.util;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSashContainer;
import org.eclipse.ui.internal.EditorStack;
import org.eclipse.ui.internal.ILayoutContainer;
import org.eclipse.ui.internal.LayoutPart;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.PartSashContainer;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PartStack;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.ddlab.util.images.ScreenShotImages;

/**
 * This class provides the actual functionalities for the screen capture and
 * editor splitting. All the functionalities are considered as utilites for this
 * plugin.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
@SuppressWarnings("restriction")
public class PluginUtil {

	/**
	 * Default Desktop Path
	 */
	final static String DEFAULT_DESKTOP_PATH = System.getProperty("user.home")
			+ File.separator + "Desktop";
	/**
	 * An increment counter for the file name
	 */
	private static int counter = 0;

	/**
	 * This method provides screenshot icon
	 * 
	 * @return screenshot image icon
	 */
	public static Image getScreenShotImage() {
		Image image = new Image(Display.getCurrent(), ScreenShotImages.class
				.getResourceAsStream("sc16.png"));
		return image;
	}

	/**
	 * This method provides image for the icon editor splitting.
	 * 
	 * @return icon for editor split
	 */
	public static Image getSplitImage() {
		Image image = new Image(Display.getCurrent(), ScreenShotImages.class
				.getResourceAsStream("editorspilit.png"));
		return image;
	}

	/**
	 * This method provides the implementation for the capturing the current
	 * eclipse image and save to a file system with dynamic name.
	 * 
	 * @param window
	 *            of type {@link IWorkbenchWindow}
	 */
	public static void saveScreenShot(IWorkbenchWindow window) {
		try {
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
			Rectangle imgArea = new Rectangle(0, 0, client.width, heightVal);
			Image image = new Image(display, imgArea);
			gc.copyArea(image, client.x, client.y);
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			counter++;
			String defaultImgDirPath = System.getProperty("user.home")
					+ File.separator + "Desktop";
			String prefDirPath = Platform.getPreferencesService().getString(
					"com.ddlab.rnd.tornado.eclipse.util", "IMG_SAVE_PATH",
					defaultImgDirPath, null);
			String imgDir = (prefDirPath == null || prefDirPath.equals("")) ? defaultImgDirPath
					: prefDirPath;
			File imgDirFile = new File(imgDir);
			imgDir = (!imgDirFile.exists()) ? DEFAULT_DESKTOP_PATH : imgDir;
			String imgPath = imgDir + File.separator + "screen" + counter
					+ ".png";
			imageLoader.save(imgPath, SWT.IMAGE_PNG);
			gc.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method provides the implementation of the editor split
	 * functionality. This technical aspect has been implementation by the
	 * design made by Dimitri Missoh. The link is given below.
	 * 
	 * http://eclipse.dzone.com/tips/programmatically-split-editor-
	 */
	public static void splitEditorArea() {
		try {
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IWorkbenchPart part = workbenchPage.getActivePart();
			PartPane partPane = ((PartSite) part.getSite()).getPane();
			LayoutPart layoutPart = partPane.getPart();

			IEditorReference[] editorReferences = workbenchPage
					.getEditorReferences();
			// In case of more than one editor
			if (editorReferences.length > 1) {
				PartPane currentEditorPartPane = ((PartSite) workbenchPage
						.getActiveEditor().getSite()).getPane();
				EditorSashContainer editorSashContainer = null;
				ILayoutContainer rootLayoutContainer = layoutPart
						.getContainer();
				if (rootLayoutContainer instanceof LayoutPart) {
					ILayoutContainer editorSashLayoutContainer = ((LayoutPart) rootLayoutContainer)
							.getContainer();
					if (editorSashLayoutContainer instanceof EditorSashContainer) {
						editorSashContainer = ((EditorSashContainer) editorSashLayoutContainer);
					}
				}
				/*
				 * Create a new part stack (i.e. a workbook)
				 */
				PartStack newPart = createStack(editorSashContainer);
				if (editorSashContainer != null) {
					editorSashContainer.stack(currentEditorPartPane, newPart);
					if (rootLayoutContainer instanceof LayoutPart) {
						ILayoutContainer cont = ((LayoutPart) rootLayoutContainer)
								.getContainer();
						if (cont instanceof PartSashContainer) {
							// "Split" the editor area by adding the new part
							((PartSashContainer) cont).add(newPart);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method to create a part stack container (a new workbook)
	 * 
	 * @param editorSashContainer
	 *            the <code>EditorSashContainer</code> to set for the returned
	 *            <code>PartStack</code>
	 * @return a new part stack container
	 */
	private static PartStack createStack(EditorSashContainer editorSashContainer) {
		EditorStack newWorkbook = null;
		try {
			WorkbenchPage workbenchPage = (WorkbenchPage) PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			newWorkbook = EditorStack.newEditorWorkbook(editorSashContainer,
					workbenchPage);
		} catch (Exception e) {
		}
		return newWorkbook;
	}

}
