package com.ddlab.rnd.tornado.eclipse.view;

import java.io.File;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rnd.tornado.eclipse.util.Activator;
import com.ddlab.rnd.tornado.eclipse.util.PluginUtil;
import com.ddlab.sysutil.beans.SysInfoBean;
import com.ddlab.sysutil.beans.TaskListProcesBean;
import com.ddlab.sysutil.tasks.FileUtil;
import com.ddlab.sysutil.tasks.SysDetailsProcessor;
import com.ddlab.sysutil.tasks.TaskKiller;

/**
 * This class is used as View for displaying the Protocol port and system
 * information
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SystemView extends ViewPart {

	/**
	 * TableViewer for the port details
	 */
	private TableViewer portTableViewer;
	/**
	 * TableViewer for the system information
	 */
	private TableViewer sysInfoTableViewer;

	/**
	 * Action to delete the Protocol port
	 */
	private Action deletePortAction;
	/**
	 * Action for the view refresh
	 */
	private Action globalRefreshAction;

	/**
	 * Populate the port details
	 */
	private void initPortInfo() {
		String taskFilePath = PluginUtil.getDropinsDataPath() + File.separator
				+ "taskList.txt";
		String cpFilePath = PluginUtil.getDropinsDataPath() + File.separator
				+ "cp.txt";
		SysDetailsProcessor sdp = new SysDetailsProcessor(taskFilePath,
				cpFilePath);
		portTableViewer.setInput(sdp.getProcessList());
	}

	/**
	 * Populate the system information
	 */
	private void initSysInfo() {
		String filePath = PluginUtil.getDropinsDataPath() + File.separator
				+ "temp1.txt";
		FileUtil fileUtil = new FileUtil();
		List<SysInfoBean> sysInfoList = fileUtil.getSystemInfo(filePath);
		sysInfoTableViewer.setInput(sysInfoList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		/*
		 * Method to create the required components
		 */
		Composite firstCompo = new Composite(parent, SWT.BORDER);
		Composite secondCompo = new Composite(parent, SWT.BORDER);

		IWorkbenchPartSite site = getSite();
		PortInfoTableCreator portInfoCreator = new PortInfoTableCreator(site,
				firstCompo);
		portTableViewer = portInfoCreator.create();

		makeGlobalActions();
		contributeToPortActionBars();

		SysInfoTableCreator sysInfoCreator = new SysInfoTableCreator(site,
				secondCompo);
		sysInfoTableViewer = sysInfoCreator.create();
		addSysTableViewerListener();
	}

	/**
	 * Method to create global action for the system view.
	 */
	private void makeGlobalActions() {
		ImageDescriptor globalRefreshIds = Activator
				.getImageDescriptor("icons/globalRefreshIcon124.png");
		ImageDescriptor deleteIds = Activator
				.getImageDescriptor("icons/delete16.png");
		/*
		 * For Global Refresh Action
		 */
		globalRefreshAction = new Action() {
			public void run() {
				initPortInfo();
				initSysInfo();
				portTableViewer.setSelection(null);
				sysInfoTableViewer.setSelection(null);
			}
		};
		globalRefreshAction.setText("Refresh All");
		globalRefreshAction.setToolTipText("Refresh All");
		globalRefreshAction.setImageDescriptor(globalRefreshIds);
		/*
		 * For Delete port action
		 */
		deletePortAction = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) portTableViewer
						.getSelection();
				System.out.println("Selection :::" + selection);
				Object firstElement = selection.getFirstElement();
				if (firstElement != null) {
					TaskListProcesBean tlpBean = (TaskListProcesBean) firstElement;
					TaskKiller.kill(tlpBean);
					initPortInfo();
				}
				portTableViewer.setSelection(null);
				portTableViewer.refresh(true);
			}
		};
		deletePortAction.setText("Kill");
		deletePortAction.setToolTipText("Kill Task");
		deletePortAction.setImageDescriptor(deleteIds);
	}

	/**
	 * Method to creation and plaction in the view tool bar
	 */
	private void contributeToPortActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Method to populate action for the view tool bar manager
	 * 
	 * @param manager
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(deletePortAction);
		manager.add(globalRefreshAction);
	}

	private void addSysTableViewerListener() {
		sysInfoTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						portTableViewer.setSelection(null);
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

}
