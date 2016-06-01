package data;

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
	
	public boolean writeFile(String fileName,String fileContent)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile!=null)
			tempFile.write(fileContent);
		else createFile(fileName, fileContent);
		return true;
	}
	
	public String readFile(String fileName)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile!=null)
			return tempFile.getFileContent();
		else return null;
	}
	
	public void createFile(String fileName,String fileContent)
	{
		BFfile bFfile=new BFfile(fileName, fileContent);
		files.add(bFfile);
		bFfile.save();
	}
	
	public BFfile findFileByName(String fileName)
	{
		for(BFfile tempFile:files)
		{
			if(tempFile.getFileName().equals(fileName))
				return tempFile;
		}
		return null;
	}
	
	public String getFileList()
	{
		String strFileList="";
		for(BFfile temp:files) strFileList+=temp.getFileName()+"\r\n";		
		return strFileList;
	}
	
	

}
