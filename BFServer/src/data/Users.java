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
	
	public boolean createUser(String userID,String password)
	{
		if(!checkUser(userID, password)){
			users.add(new User(userID, password));
			return true;
		}
		else return false;
	}
	
	public boolean addCurrentUser(String userID,String password)
	{
		if(checkUser(userID, password)&&findCurrentUserByID(userID)==null){ 
			currentUsers.add(new User(userID, password));
			return true;
		}
		else return false;
	}
	
	public boolean removeCurrentUser(String userID)
	{
		if(findCurrentUserByID(userID)!=null){
			currentUsers.remove(findCurrentUserByID(userID));
	        return true;	
		}
		else return false;
	}
	
	public User findCurrentUserByID(String userID)
	{
		for(User temp:currentUsers)
		{
			if(temp.getUserID().equals(userID)) 
				return temp;
		}
		return null;
	}
	
	public boolean checkUser(String userID,String password)
	{
		for(User temp:users)
		{
			if(temp.getUserID().equals(userID)&&temp.checkPassword(password))
				return true;
		}
		return false;
	}

}
