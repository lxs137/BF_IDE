package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BFfile
{
	String fileName;
	String fileContent;
	String filePath;
	
	public BFfile(String fileName,String fileContent)
	{
		this.fileName=fileName;
		this.fileContent=fileContent;
		this.filePath="E:\\"+fileName+".bf";
	}
	
	public void save()
	{
		File file=new File(this.filePath);
		FileWriter fileWriter;
		try 
		{
			fileWriter = new FileWriter(file);
			fileWriter.write(this.fileContent);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
