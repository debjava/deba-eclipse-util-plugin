package com.ddlab.rnd.tornado.eclipse.util;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This is the preference class for this plugin which provides felxibility to
 * select the folder for copy,zip files, save the screen shot and to hide or
 * unhide the statusline bar.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class DebaPluginPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Text Field for the Image Path
	 */
	private Text imgText;
	/**
	 * Text Field for the Send2Path
	 */
	private Text send2Text;
	/**
	 * Text Field for the zipping the files
	 */
	private Text zipFileText;
	/**
	 * Browse button for selecting the image path
	 */
	private Button imgBrowseBtn = null;
	/**
	 * Browse button for selecting the send2 path
	 */
	private Button send2BrowseBtn = null;
	/**
	 * Browse button for selecting the zip path
	 */
	private Button zipBrowseBtn = null;
	/**
	 * Check box button to hide or unhide the Statusline bar
	 */
	private Button btnHideStatusbar = null;

	/**
	 * Default Constructor
	 */
	public DebaPluginPreferencePage() {
		super(GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createContents
	 * (org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(final Composite parent) {
		/*
		 * Method to create the UI components for the Preference Dialog
		 */
		btnHideStatusbar = new Button(parent, SWT.CHECK);
		btnHideStatusbar.setBounds(77, 10, 104, 16);
		btnHideStatusbar.setText("Hide StatusBar");

		Group grpMyTestGroup = new Group(parent, SWT.NONE);
		grpMyTestGroup.setText("Image Path Details");
		grpMyTestGroup.setBounds(72, 57, 471, 66);

		imgText = new Text(grpMyTestGroup, SWT.BORDER);
		imgText.setBounds(158, 17, 230, 21);
		imgText.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		imgText.setEditable(false);
		// Default Listener
		imgBrowseBtn = new Button(grpMyTestGroup, SWT.NONE);
		imgBrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					imgText.setText(dir);
				}
			}
		});
		imgBrowseBtn.setBounds(394, 15, 63, 25);
		imgBrowseBtn.setText("Browse");

		Label lblScreenshotsImageLocation = new Label(grpMyTestGroup, SWT.NONE);
		lblScreenshotsImageLocation.setBounds(10, 20, 142, 15);
		lblScreenshotsImageLocation.setText("Screenshots Image Path : ");

		Group grpMySecondGroup = new Group(parent, SWT.NONE);
		grpMySecondGroup.setBounds(72, 137, 439, 66);
		grpMySecondGroup.setText("Send2 Files Details");

		send2Text = new Text(grpMySecondGroup, SWT.BORDER);
		send2Text.setBounds(125, 17, 230, 21);
		send2Text.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		send2Text.setEditable(false);
		// Default Listener
		send2BrowseBtn = new Button(grpMySecondGroup, SWT.NONE);
		send2BrowseBtn.setText("Browse");
		send2BrowseBtn.setBounds(361, 15, 65, 25);
		send2BrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
				String dir = dlg.open();
				if (dir != null) {
					send2Text.setText(dir);
				}
			}
		});

		Label lblSendToFiles = new Label(grpMySecondGroup, SWT.NONE);
		lblSendToFiles.setBounds(10, 20, 109, 15);
		lblSendToFiles.setText("Send to Files Path : ");

		Group grpFilesZipDetails = new Group(parent, SWT.NONE);
		grpFilesZipDetails.setText("Files Zip Details");
		grpFilesZipDetails.setBounds(72, 225, 439, 66);

		zipFileText = new Text(grpFilesZipDetails, SWT.BORDER);
		zipFileText.setBounds(125, 17, 230, 21);
		zipFileText.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		zipFileText.setEditable(false);

		zipBrowseBtn = new Button(grpFilesZipDetails, SWT.NONE);
		zipBrowseBtn.setText("Browse");
		zipBrowseBtn.setBounds(361, 15, 65, 25);
		// Default Listener
		zipBrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
				String dir = dlg.open();
				if (dir != null) {
					zipFileText.setText(dir);
				}
			}
		});

		Label lblZipFilesPath = new Label(grpFilesZipDetails, SWT.NONE);
		lblZipFilesPath.setText("Zip Files Path : ");
		lblZipFilesPath.setBounds(10, 20, 109, 15);
		/*
		 * Set the Default initial values
		 */
		setDefaultValues();
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Minimal Configuration for Deba's Util Plugin");
	}

	public void setDefaultValues() {
		String zipSaveVal = getPreferenceStore().getString(
				PluginConstants.ZIP_FILE_PATH);

		zipSaveVal = (zipSaveVal == null || zipSaveVal.equals("")) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: zipSaveVal;
		zipFileText.setText(zipSaveVal);
		PluginLogger.info("Zip Save Path Value---->" + zipSaveVal);

		String imgSaveVal = getPreferenceStore().getString(
				PluginConstants.IMG_SAVE_PATH);
		imgSaveVal = (imgSaveVal == null || imgSaveVal.equals("")) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: imgSaveVal;
		imgText.setText(imgSaveVal);
		PluginLogger.info("Img Save Path Value---->" + zipSaveVal);

		String send2PathVal = getPreferenceStore().getString(
				PluginConstants.SEND_2_PATH);
		send2PathVal = (send2PathVal == null || send2PathVal.equals("")) ? PluginConstants.DEFAULT_DESKTOP_PATH
				: send2PathVal;
		send2Text.setText(send2PathVal);
		PluginLogger.info("Send2Path Save Path Value---->" + zipSaveVal);
		btnHideStatusbar.setSelection(getPreferenceStore().getBoolean(
				PluginConstants.HIDE_STATUS_BAR));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		/*
		 * On clicking of OK Button
		 */
		performApply();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		/*
		 * Default values for the preference
		 */
		imgText.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		send2Text.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		zipFileText.setText(PluginConstants.DEFAULT_DESKTOP_PATH);
		btnHideStatusbar.setSelection(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	protected void performApply() {
		/*
		 * Following will be executed on clicking Apply button
		 */
		boolean hideStatusBarFlag = btnHideStatusbar.getSelection();
		getPreferenceStore().setValue(PluginConstants.IMG_SAVE_PATH,
				imgText.getText());
		getPreferenceStore().setValue(PluginConstants.HIDE_STATUS_BAR,
				hideStatusBarFlag);
		getPreferenceStore().setValue(PluginConstants.SEND_2_PATH,
				send2Text.getText());
		getPreferenceStore().setValue(PluginConstants.ZIP_FILE_PATH,
				zipFileText.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors
	 * ()
	 */
	@Override
	protected void createFieldEditors() {
	}
}