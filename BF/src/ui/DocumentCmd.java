package ui;

public class DocumentCmd {
	
	private String type;
	private int offset;
	private int length;
	private String result;
	
	public DocumentCmd(String type,int offset,int length,String result)
	{
		this.type=type;
		this.offset=offset;
		this.length=length;
		this.result=result;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public int getOffset()
	{
		return this.offset;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public String getResult()
	{
		return this.result;
	}
	
	public boolean checkMerge(DocumentCmd beforeCmd)
	{
		if(!beforeCmd.getType().equals(this.type)) 
			return false;
		else if(beforeCmd.getType().equals("INSERT"))
			if(beforeCmd.getOffset()+beforeCmd.getLength()==this.offset)
				return true;
			else 
				return false;
		else if(beforeCmd.getType().equals("REMOVE"))
			if(beforeCmd.getOffset()==this.offset+this.length)
				return true;
			else 
				return false;
		else 
			return false;
	}
	
	public DocumentCmd merge(DocumentCmd beforeCmd)
	{
		DocumentCmd mergeCmd;
		if(beforeCmd.getType().equals("INSERT"))
			mergeCmd=new DocumentCmd(this.type,beforeCmd.getOffset(),this.length+beforeCmd.getLength(),this.result);
		else
			mergeCmd=new DocumentCmd(this.type,this.offset,this.length+beforeCmd.getLength(),this.result);
		return mergeCmd;
	}

}

