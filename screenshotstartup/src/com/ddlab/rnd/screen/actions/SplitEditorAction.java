package com.ddlab.rnd.screen.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.ddlab.rnd.eclipse.plugin.util.PluginUtil;

/**
 * This is action class for Eclipse Plugin which helps in spliting the currently
 * focused editor to split into two editors for comparative study and analysis
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SplitEditorAction extends Action {

	/**
	 * Default Constructor
	 */
	public SplitEditorAction() {
		init();
	}

	/**
	 * Method to set the relevant information ie Setting Text Setting ToolTip
	 * Setting Image
	 */
	private void init() {
		try {
			setText("Split Window");
			setToolTipText("Split Window");
			ImageDescriptor sids = ImageDescriptor.createFromImage(PluginUtil
					.getSplitImage());
			setImageDescriptor(sids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		PluginUtil.splitEditorArea();
	}

}
