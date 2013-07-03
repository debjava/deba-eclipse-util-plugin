package com.ddlab.rnd.tornado.eclipse.view;

import java.io.File;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;

import com.ddlab.rnd.tornado.eclipse.util.Activator;
import com.ddlab.rnd.tornado.eclipse.util.PluginUtil;
import com.ddlab.sysutil.beans.TaskListProcesBean;
import com.ddlab.sysutil.tasks.SysDetailsProcessor;

/**
 * This class is used to create a tableviewer to display the protocol port ,
 * name and other system details. It also provides flexibility to refresh the
 * window.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class PortInfoTableCreator {

	/**
	 * Composite to create the components
	 */
	private Composite parent = null;
	/**
	 * Tableviewr to display the information
	 */
	private TableViewer portTableViewer = null;
	/**
	 * Table from the tableviewer
	 */
	private Table portTable = null;
	/**
	 * Action for the referesh action
	 */
	private Action refreshPortAction = null;

	/**
	 * Workbench site
	 */
	private IWorkbenchPartSite site = null;

	/**
	 * Default constructor
	 * 
	 * @param site
	 * @param parent
	 */
	public PortInfoTableCreator(IWorkbenchPartSite site, Composite parent) {
		this.parent = parent;
		this.site = site;
	}

	/**
	 * Method to create a tableviewer
	 * 
	 * @return
	 */
	public TableViewer create() {
		createPortTableViewer(parent);
		makePortActions();
		hookPortContextMenu();
		return portTableViewer;
	}

	/**
	 * Method to create a TableViewer by passing Composite
	 * 
	 * @param parent
	 */
	private void createPortTableViewer(Composite parent) {
		portTableViewer = new TableViewer(parent, SWT.BORDER
				| SWT.FULL_SELECTION);
		portTable = portTableViewer.getTable();
		portTable.setHeaderVisible(true);
		portTable.setLinesVisible(true);

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn tblclmnFirst = tableViewerColumn.getColumn();
		layout.setColumnData(tblclmnFirst, new ColumnWeightData(3,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnFirst.setText("Process Id");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn tblclmnLast = tableViewerColumn_1.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnLast, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnLast.setText("Process Name");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn tblclmnTitle = tableViewerColumn_2.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnTitle, new ColumnWeightData(3,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnTitle.setText("Protocol");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn tblclmnEmail = tableViewerColumn_3.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnEmail, new ColumnWeightData(2,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnEmail.setText("Port");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn statusCol = tableViewerColumn_4.getColumn();
		// Specify width using weights
		layout.setColumnData(statusCol, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		statusCol.setText("Status");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				portTableViewer, SWT.NONE);
		TableColumn memoryCol = tableViewerColumn_5.getColumn();
		// Specify width using weights
		layout.setColumnData(memoryCol, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		memoryCol.setText("Memory Usage");

		portTableViewer.setLabelProvider(new PortTableLabelProvider());
		portTableViewer.setContentProvider(new PortContentProvider());

		initPortInfo();
		portTableViewer.refresh();
	}

	/**
	 * Method to create a context menu from the tableviewer
	 */
	private void hookPortContextMenu() {
		System.out.println("portTableViewer--------->" + portTableViewer);
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillPortContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(portTableViewer.getControl());
		portTableViewer.getControl().setMenu(menu);
		site.registerContextMenu(menuMgr, portTableViewer);
	}

	/**
	 * Method to add to the menu manager for the refresh action
	 * 
	 * @param manager
	 */
	private void fillPortContextMenu(IMenuManager manager) {
		// manager.add(deletePortAction);//Dangerous operation
		manager.add(refreshPortAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * Method to create action for the refresh functionality
	 */
	private void makePortActions() {
		ImageDescriptor refreshIds = Activator
				.getImageDescriptor("icons/refresh16.png");
		refreshPortAction = new Action() {
			public void run() {
				initPortInfo();
				portTableViewer.setSelection(null);
			}
		};
		refreshPortAction.setText("Refresh");
		refreshPortAction.setToolTipText("Refresh");
		refreshPortAction.setImageDescriptor(refreshIds);
	}

	/**
	 * Method to initialize and gather port information
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
	 * Innter class for the tableviewer
	 * 
	 */
	private class PortTableLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
		 * lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			/*
			 * Method to populate the table columns
			 */
			TaskListProcesBean tlpb = (TaskListProcesBean) element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = tlpb.getProcessId();
				break;
			case 1:
				result = tlpb.getProcessName();
				break;
			case 2:
				result = tlpb.getProtocol();
				break;
			case 3:
				result = tlpb.getLocalPort();
				break;
			case 4:
				result = (tlpb.getStatus() == null) ? " " : tlpb.getStatus();
				break;
			case 5:
				result = (tlpb.getMemUsage() == null) ? " " : tlpb
						.getMemUsage();
				break;
			default:
				result = "";
			}
			return result;
		}
	}

	/**
	 * Inner class for the tableviewer
	 * 
	 */
	private static class PortContentProvider implements
			IStructuredContentProvider {
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			List<?> list = (List) inputElement;
			return list.toArray();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
		 * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

}
