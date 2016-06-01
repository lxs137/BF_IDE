package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import rmi.RemoteHelper;
import ui.MainFrame;

public class ClientRunner 
{
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
		MainFrame mainFrame = new MainFrame();
	}
	
	public void test()
	{
		try 
		{
			remoteHelper.getUserService().signUp("123","123");
			remoteHelper.getUserService().signUp("124","124");
			remoteHelper.getUserService().signUp("125","125");
			System.out.println(remoteHelper.getUserService().login("123","123"));
			System.out.println(remoteHelper.getUserService().logout("1234"));
			System.out.println(remoteHelper.getUserService().logout("123"));
			System.out.println(remoteHelper.getUserService().login("12","123"));
			System.out.println(remoteHelper.getUserService().login("123","1234"));
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
