package com.ddlab.rnd.tornado.eclipse.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import com.ddlab.rnd.tornado.eclipse.images.MyPluginImage;

/**
 * This is a dialog class which displays about the features for this plugin.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class AboutDialog extends Dialog {

	/**
	 * Parent Shell for the DialogBox
	 */
	protected Shell shlAboutDebasEclipse;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AboutDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();

		shlAboutDebasEclipse.open();
		shlAboutDebasEclipse.layout();

		Display display = getParent().getDisplay();
		while (!shlAboutDebasEclipse.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return null;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAboutDebasEclipse = new Shell(getParent(), getStyle());
		Image dialogImg = new Image(Display.getCurrent(), MyPluginImage.class
				.getResourceAsStream("about-icon24.png"));
		shlAboutDebasEclipse.setImage(dialogImg);
		shlAboutDebasEclipse.setSize(534, 300);
		shlAboutDebasEclipse.setText("About Deba's Eclipse Plugin");
		shlAboutDebasEclipse.setLayout(null);
		Label lblImage = new Label(shlAboutDebasEclipse, SWT.BORDER);
		Image image = new Image(Display.getCurrent(), MyPluginImage.class
				.getResourceAsStream("img220.jpg"));
		lblImage.setBounds(0, 0, 173, 246);
		lblImage.setText("Image");
		lblImage.setImage(image);

		StyledText styledText = new StyledText(shlAboutDebasEclipse, SWT.NONE);
		styledText.setBounds(179, 0, 349, 246);
		styledText.setEditable(false);

		String desc = "An Eclipse utility plugin for developers";
		styledText.setText(desc);
		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = desc.length();
		styleRange.fontStyle = SWT.BOLD;
		styledText.setStyleRange(styleRange);
		styledText.append("\n\n");
		styledText.append("Contains the following utilities ");
		styledText.append("\n");
		styledText.append("* Copy File Path (Windows Style , Multi Selection)");
		styledText.append("\n");
		styledText.append("* Copy File Path (Unix Style , Multi Selection)");
		styledText.append("\n");
		styledText.append("* Open in Windows Command Prompt (Multi Selection)");
		styledText.append("\n");
		styledText.append("* Open Command Prompt in Eclipse Console");
		styledText.append("\n");
		styledText.append("* Open in Windows Explorer (Multi Selection)");
		styledText.append("\n");
		styledText.append("* Full Screen");
		styledText.append("\n");
		styledText
				.append("* Shortcut for Windows Command Prompt from Eclipse console ");
		styledText.append("\n");
		styledText.append("* Send Files to Desktop ");
		styledText.append("\n");
		styledText.append("* Zip files and send to Desktop");
		styledText.append("\n");
		styledText.append("* Capture screenshot and save to Desktop");
		styledText.append("\n");
		styledText.append("* Split Eclipse Text Editor");
		styledText.append("\n");
		styledText.append("* System view and Kill a Process Id ");
		styledText.append("\n");
		styledText.append("* Preferences for Utilities ");
		styledText.append("\n\n\n");
		styledText.append("Base Version: 1.0.0.0");
		styledText.append("\n");
		styledText.append("Build ID: 05082012");
		styledText.append("\n");
		styledText.append("Build Date: 05082012");

		styledText.append("\n");
		styledText
				.append("For any issues , report to Debadatta Mishra \n\ndebadatta.mishra@gmail.com");

		Label lblNewLabel = new Label(shlAboutDebasEclipse, SWT.NONE);
		lblNewLabel.setBounds(0, 252, 528, 20);
		lblNewLabel
				.setText("An artifact from TORNADO Project - Managed by Debadatta Mishra");
	}
}
