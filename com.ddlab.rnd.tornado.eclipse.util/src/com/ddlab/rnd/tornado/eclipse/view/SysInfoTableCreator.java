package com.ddlab.rnd.tornado.eclipse.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
import com.ddlab.sysutil.beans.SysInfoBean;
import com.ddlab.sysutil.tasks.FileUtil;

/**
 * This class is used to create a tableviewer for displaying basic OS details.
 * 
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 * 
 */
public class SysInfoTableCreator {

	/**
	 * Workbench site
	 */
	private IWorkbenchPartSite site = null;
	/**
	 * Composite to create a tableviewer
	 */
	private Composite parent = null;
	/**
	 * TableViewer for the system information
	 */
	private TableViewer sysInfoTableViewer;
	/**
	 * Table from the TableViewer
	 */
	private Table sysTable;
	/**
	 * Action for the export system information
	 */
	private Action exportSysAction;
	/**
	 * Action for the refresh the view
	 */
	private Action refreshSysAction;

	/**
	 * Default Constructor
	 * 
	 * @param site
	 * @param parent
	 */
	public SysInfoTableCreator(IWorkbenchPartSite site, Composite parent) {
		this.parent = parent;
		this.site = site;
	}

	/**
	 * Method to create Tableviewer
	 * 
	 * @return
	 */
	public TableViewer create() {
		createSysInfoTableViewer(parent);
		makeSysInfoActions();
		hookSysInfoContextMenu();

		return sysInfoTableViewer;
	}

	/**
	 * Method to create context menu for the tableviewer
	 */
	private void hookSysInfoContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillSysInfoContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(sysInfoTableViewer.getControl());
		sysInfoTableViewer.getControl().setMenu(menu);
		site.registerContextMenu(menuMgr, sysInfoTableViewer);
	}

	/**
	 * Method to add the action for the ManuManager for the TableViewer
	 * 
	 * @param manager
	 */
	private void fillSysInfoContextMenu(IMenuManager manager) {
		manager.add(exportSysAction);
		manager.add(refreshSysAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * Method to create Action for the refresh and export functionalities
	 */
	private void makeSysInfoActions() {
		ImageDescriptor exportIds = Activator
				.getImageDescriptor("icons/forwardIcon16.png");
		ImageDescriptor refreshIds = Activator
				.getImageDescriptor("icons/refresh16.png");

		refreshSysAction = new Action() {
			public void run() {
				// initPortInfo();
				initSysInfo();
				sysInfoTableViewer.setSelection(null);
			}
		};
		refreshSysAction.setText("Refresh");
		refreshSysAction.setToolTipText("Refresh");
		refreshSysAction.setImageDescriptor(refreshIds);

		exportSysAction = new Action() {
			@SuppressWarnings("unchecked")
			public void run() {

				Object inputs = sysInfoTableViewer.getInput();
				List<SysInfoBean> sysBeanList = (List<SysInfoBean>) inputs;
				String desktopDirPath = System.getProperty("user.home")
						+ File.separator + "Desktop";
				String filePath = desktopDirPath + File.separator
						+ "SystemInfo.txt";
				try {
					OutputStream outStream = new FileOutputStream(filePath,
							true);
					for (SysInfoBean sysBean : sysBeanList) {
						outStream.write(sysBean.toString().getBytes());
						outStream.write("\n".getBytes());
					}
					outStream.flush();
					outStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				sysInfoTableViewer.setSelection(null);
			}
		};
		exportSysAction.setText("Export to Desktop");
		exportSysAction.setToolTipText("Export to Desktop");
		exportSysAction.setImageDescriptor(exportIds);
	}

	/**
	 * Method to create TableViewer for the system information
	 * 
	 * @param parent
	 */
	private void createSysInfoTableViewer(Composite parent) {
		sysInfoTableViewer = new TableViewer(parent, SWT.BORDER
				| SWT.FULL_SELECTION);
		sysTable = sysInfoTableViewer.getTable();
		sysTable.setHeaderVisible(true);
		sysTable.setLinesVisible(true);

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				sysInfoTableViewer, SWT.NONE);
		TableColumn tblclmnFirst = tableViewerColumn.getColumn();
		layout.setColumnData(tblclmnFirst, new ColumnWeightData(3,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnFirst.setText("Name");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				sysInfoTableViewer, SWT.NONE);
		TableColumn tblclmnLast = tableViewerColumn_1.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnLast, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnLast.setText("Value");

		sysInfoTableViewer.setLabelProvider(new SysInfoTableLabelProvider());
		sysInfoTableViewer.setContentProvider(new SysInfoContentProvider());

		initSysInfo();
		sysInfoTableViewer.refresh();

		// addSysTableViewerListener();
	}

	/**
	 * Method to populate the system information
	 */
	private void initSysInfo() {
		String filePath = PluginUtil.getDropinsDataPath() + File.separator
				+ "temp1.txt";
		FileUtil fileUtil = new FileUtil();
		List<SysInfoBean> sysInfoList = fileUtil.getSystemInfo(filePath);
		sysInfoTableViewer.setInput(sysInfoList);
	}

	/**
	 * Inner class for the TableViewer
	 * 
	 */
	private class SysInfoTableLabelProvider extends LabelProvider implements
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
			 * Populate the contents for the columns
			 */
			SysInfoBean sysBean = (SysInfoBean) element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = sysBean.getName();
				break;
			case 1:
				result = sysBean.getValue();
				break;
			default:
				result = "";
			}
			return result;
		}
	}

	/**
	 * Inner class for the TableViewer
	 * 
	 */
	private static class SysInfoContentProvider implements
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
