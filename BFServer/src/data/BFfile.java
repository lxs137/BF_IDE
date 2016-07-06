package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BFfile
{
	private String fileName;
	private String filePath;
	private ArrayList<BFfileVersion> versionList;
	
	public BFfile(String fileName,String fileContent)
	{
		this.fileName=fileName;
		versionList=new ArrayList<BFfileVersion>();
	}
	
	public void rename(String newName)
	{
		this.fileName=newName;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public String getFileContent(String version)
	{
		return findBFfileByVersion(version).content;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void write(String fileContent,String version)
	{
		if(version.equals("noFile")) 
			versionList.add(new BFfileVersion(fileContent));
		else if(!getFileContent(version).equals(fileContent))
			versionList.add(new BFfileVersion(fileContent));
	}
	
	public String getVersionList()
	{
		String versionListStr="";
		for(BFfileVersion version:versionList) versionListStr+=version.version+"\r\n";		
		return versionListStr;
	}
	
	/**
	 * 文件保存到服务器磁盘上
	 */
	public void save(String userID)
	{
		
		this.filePath="E:\\"+userID+"\\"+fileName;
		if(!new File(this.filePath).exists()) new File(this.filePath).mkdirs();
		for(BFfileVersion bFfileVersion:versionList)
		{
			if(!bFfileVersion.saved)
			{
				File file=new File(this.filePath+"\\"+bFfileVersion.version+".bf");
				FileWriter fileWriter;
				try 
				{
					fileWriter = new FileWriter(file);
					fileWriter.write(bFfileVersion.content);
					fileWriter.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
				bFfileVersion.saved=true;
			}
		}
		
	}
	
	private BFfileVersion findBFfileByVersion(String version)
	{
		for(BFfileVersion bFfileVersion:versionList)
			if(bFfileVersion.version.equals(version))
				return bFfileVersion;
		return null;
	}
	

}
