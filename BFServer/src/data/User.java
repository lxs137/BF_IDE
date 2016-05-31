package data;

import java.io.File;
import java.util.ArrayList;

public class User 
{
	private String userID;
	private String password;
	private ArrayList<BFfile> files;
	
	public User(String userID,String password)
	{
		this.userID=new String(userID);
		this.password=new String(password);
		files=new ArrayList<BFfile>();
	}
	
	public String getUserID()
	{
		return userID;
	}
	
	public boolean checkPassword(String password)
	{
		if(this.password.equals(password)) return true;
		else return false;
	}
	
	public void createFile(String fileName,String fileContent)
	{
		BFfile bFfile=new BFfile(fileName, fileContent);
		files.add(bFfile);
		bFfile.save();
	}
	
	

}
