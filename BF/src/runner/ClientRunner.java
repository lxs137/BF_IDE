package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import rmi.RemoteHelper;
import ui.MainFrame;

public class ClientRunner 
{
	public static MainFrame mainFrame;
	private RemoteHelper remoteHelper;
	
	public ClientRunner() 
	{
		linkToServer();
		initGUI();
	}
	
	private void linkToServer() 
	{
		try 
		{
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initGUI() 
	{
		mainFrame = new MainFrame("");
		mainFrame.showLoginDialog();
	}
	
	public void test()
	{
		try 
		{
			remoteHelper.getUserService().signUp("","");
		} catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		ClientRunner clientRunner = new ClientRunner();
		clientRunner.test();
	}
}
