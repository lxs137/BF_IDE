package serviceImpl;

import java.rmi.RemoteException;

import service.UserService;
import data.*;
import runner.ServerRunner;

public class UserServiceImpl implements UserService{

	@Override
	public boolean login(String username, String password) throws RemoteException {
		return ServerRunner.users.addCurrentUser(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return ServerRunner.users.removeCurrentUser(username);
	}

	@Override
	public boolean signUp(String username,String password) throws RemoteException
	{
		return ServerRunner.users.createUser(username, password);
	}
}
