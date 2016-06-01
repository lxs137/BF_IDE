package data;

import java.util.ArrayList;

public class Users 
{
	private ArrayList<User> users;         //所有的用户，服务器每次关机时会保存当前数据
	private ArrayList<User> currentUsers;  //当前登录的用户，服务器每次关机后清空
	
	public Users()
	{
		users=new ArrayList<User>();
		currentUsers=new ArrayList<User>();
	}
	
	/**
	 * 创建新用户
	 * @param userID  新用户的用户名
	 * @param password  新用户的密码
	 * @return  是否创建成功
	 */
	public boolean createUser(String userID,String password)
	{
		if(!checkUser(userID, password)){
			users.add(new User(userID, password));
			return true;
		}
		else return false;
	}
	
	/**
	 * 用户登录
	 * @param userID 登录用户的用户名
	 * @param password 登录用户的密码
	 * @return 是否登录成功
	 */
	public boolean addCurrentUser(String userID,String password)
	{
		if(checkUser(userID, password)&&findCurrentUserByID(userID)==null){			
			currentUsers.add(new User(userID, password));
			return true;
		}
		else return false;
	}
	
	/**
	 * 用户登出
	 * @param userID 登出用户的用户名
	 * @return 是否登出成功
	 */
	public boolean removeCurrentUser(String userID)
	{
		if(findCurrentUserByID(userID)!=null){
			currentUsers.remove(findCurrentUserByID(userID));
	        return true;	
		}
		else return false;
	}
	
	/**
	 * 按用户名获取该用户的文件列表
	 * @param userID 该用户的用户名
	 * @return 该用户的文件列表
	 */
	public String getFileList(String userID)
	{
		User tempUser=findCurrentUserByID(userID);
		if(tempUser!=null)
			return tempUser.getFileList();
		else return null;
	}
	
	public boolean writeFile(String fileContent,String userID,String fileName)
	{
		User tempUser=findCurrentUserByID(userID);
		if(tempUser!=null){
			tempUser.writeFile(fileName, fileContent);
			return true;
		}
		else return false;	
	}
	
	public String readFile(String userID,String fileName)
	{
		User tempUser=findCurrentUserByID(userID);
		if(tempUser!=null)
			return tempUser.readFile(fileName);
		else return null;
	}
	
	/**
	 * 按用户名获得当前登录用户的对象
	 * @param userID  用户名
	 * @return  如用户名当前登录，则返回该用户对象，否则，返回null
	 */
	public User findCurrentUserByID(String userID)
	{
		for(User tempUser:currentUsers)
		{
			if(tempUser.getUserID().equals(userID)) 
				return tempUser;
		}
		return null;
	}
	
	/**
	 * 检查用户名和密码是否有效
	 * @param userID 用户名
	 * @param password 密码
	 * @return 返回该用户名和密码是否有效
	 */
	public boolean checkUser(String userID,String password)
	{
		for(User tempUser:users)
		{
			if(tempUser.getUserID().equals(userID)&&tempUser.checkPassword(password))
				return true;
		}
		return false;
	}

}
