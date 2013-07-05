package com.ddlab.rnd.tornado.eclipse.handlers;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.ddlab.rnd.tornado.eclipse.threads.CommandThread;
/**This is an ActionDelegate class for executing the command from from.
 * @author <a href="mailto:debadatta.mishra@gmail.com"> Debadatta Mishra (PIKU)
 * @since 2013
 *
 */
public class CommandActionDelegate implements IViewActionDelegate  {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	@Override
	public void init(IViewPart view) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		String actualCmd = "";
		//Command option /C or /K
		String cmdName = "cmd.exe /C";
		String startCmd = "start";
		String pushdCmd = "pushd";
		String folderPath = "C:/";
		//Command creation
		actualCmd = new StringBuilder(actualCmd).append(cmdName)
				.append(" ").append(startCmd).append(" ")
				.append(pushdCmd).toString();
		//			Process process = Runtime.getRuntime().exec("cmd.exe /c start pushd", null, new File(folderPath));
		Thread cmdThread = new CommandThread( actualCmd,folderPath );
		cmdThread.start();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
