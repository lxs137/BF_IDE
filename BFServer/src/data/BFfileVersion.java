package data;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BFfileVersion {
	public String version;
	public String content;
	public boolean saved;
	
	public BFfileVersion(String content)
	{
		Date now=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
		this.version=dateFormat.format(now);
		this.content=content;
		this.saved=false;
	}
}
