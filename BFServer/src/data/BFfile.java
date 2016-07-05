package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BFfile
{
	private String fileName;
	private String fileContent;
	private String filePath;
	private ArrayList<BFfileVersion> versionList;
	
	public BFfile(String fileName,String fileContent)
	{
		this.fileName=fileName;
		this.fileContent=fileContent;
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
		for(BFfileVersion fileVersion:versionList)
			if(fileVersion.version.equals(version)) return fileVersion.content;
		return "";
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void write(String fileContent,String version)
	{
		for(BFfileVersion fileVersion:versionList)
			if(fileVersion.version.equals(version)) 
			{
				fileVersion.content=fileContent;
				fileVersion.version=version;
			}
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
	public void save(String userID,String version)
	{
		
		this.filePath="E:\\"+userID+"\\"+fileName;
		if(!new File(this.filePath).exists()) new File(this.filePath).mkdirs();
		BFfileVersion newVersion=new BFfileVersion(fileContent,version);
		versionList.add(newVersion);
		File file=new File(this.filePath+"\\"+newVersion.version+".bf");
		FileWriter fileWriter;
		try 
		{
			fileWriter = new FileWriter(file);
			fileWriter.write(this.fileContent);
			fileWriter.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	

}
