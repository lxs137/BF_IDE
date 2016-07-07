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
	
	/**
	 * 检查当前用户的文件列表中是否存在要新建的文件名，若存在，返回false；否则，新建一个文件，并返回true
	 * @param fileName 要新建的文件名
	 * @return
	 */
	public boolean newFile(String fileName)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile!=null)
			return false;
		else 
		{
			createFile(fileName, "");
			return true;
		}
	}
	
	/**
	 * 重命名文件名
	 * @param oldName 之前的文件名
	 * @param newName 要更改为的文件名
	 */
	public boolean renameFile(String oldName,String newName)
	{
		BFfile tempFile=findFileByName(oldName);
		if(tempFile!=null)
		{
			tempFile.rename(newName);
			return true;
		}
		else return false;
	}
	
	/**
	 * 在指定文件名的文件中写入文件内容，并保存在磁盘上,若文件内容没变，则不保存
	 * @param fileName 文件名
	 * @param fileContent 文件内容
	 */
	public boolean writeFile(String fileName,String fileContent,String version)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile!=null){
			tempFile.write(fileContent, version);
			tempFile.save(userID);
			return true;
		}
		else return false;
	}
	
	/**
	 * 按文件名读出文件内容，作为返回值
	 * @param fileName
	 */
	public String readFile(String fileName,String version)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile!=null)
			return tempFile.getFileContent(version);
		else return null;
	}
	
	/**
	 * 新建一个bf文件
	 * @param fileName 文件名
	 * @param fileContent  文件内容
	 */
	public void createFile(String fileName,String fileContent)
	{
		BFfile bFfile=new BFfile(fileName, fileContent);
		files.add(bFfile);
	}
	
	/**
	 * 按指定的文件名在当前用户的文件列表中搜寻文件
	 * @param fileName 要搜索的文件名
	 * @return 返回搜索到的文件类本身，若没有找到，返回null
	 */
	public BFfile findFileByName(String fileName)
	{
		for(BFfile tempFile:files)
		{
			if(tempFile.getFileName().equals(fileName))
				return tempFile;
		}
		return null;
	}
	
	/**
	 * 获得当前用户文件列表的String格式，便于传送给客户端
	 */
	public String getFileList()
	{
		String strFileList="";
		for(BFfile temp:files) strFileList+=temp.getFileName()+"\r\n";		
		return strFileList;
	}
	
	public String getVersionList(String fileName)
	{
		BFfile tempFile=findFileByName(fileName);
		if(tempFile==null)
			return null;
		else 
			return tempFile.getVersionList();
	}
	

}
