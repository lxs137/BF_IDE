package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import rmi.RemoteHelper;


public class VersionMenu extends JMenu{
	
	private String currentFileVersion;
	private ArrayList<JMenuItem> versionList;
	
	public VersionMenu(String title)
	{
		super(title);
		this.currentFileVersion="noFile";
		this.versionList=new ArrayList<JMenuItem>();
	}
	
	public String getNewestVersion()
	{
		return this.versionList.get(versionList.size()-1).getText();
	}
	
	public void setCurrentFileVersion(String currentVersion)
	{
		this.currentFileVersion=currentVersion;
	}
	
	public String getCurrentFileVersion()
	{
		return this.currentFileVersion;
	}
	
	public void clearVersionList()
	{
		this.versionList.clear();
	}
	
	public void initVersionList(String userID, String fileListSelected,JTextArea codeText)
	{		
		this.removeAll();
		String versionStr="";
		if(!userID.equals("")&&fileListSelected!=null) 
			try{
				versionStr=RemoteHelper.getInstance().getIOService().readVersionList(userID,fileListSelected);
				versionStr=((versionStr==null)?(""):versionStr);
			}
			catch (RemoteException re) {
				re.printStackTrace();
			}
		else versionStr="noFile\r\n";		
		String[] versionStrArra=versionStr.split("\r\n");
		for(String version:versionStrArra)
			if(!version.equals("")&&!checkVersionIsExist(version)) 
			{
				versionList.add(new JMenuItem(version));
				currentFileVersion=versionList.get(versionList.size()-1).getText();
			}
		for(JMenuItem version:versionList)
		{
			this.add(version);
			version.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentFileVersion=version.getText();
					try {
						
						codeText.setText(RemoteHelper.getInstance().getIOService()
								.readFile(userID,fileListSelected,currentFileVersion));
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}
	
	private boolean checkVersionIsExist(String version)
	{
		for(JMenuItem item:versionList)
			if(item.getText().equals(version)) return true;
		return false;
	}

}
