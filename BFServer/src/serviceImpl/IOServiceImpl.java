package serviceImpl;


import java.rmi.RemoteException;

import runner.ServerRunner;
import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean newFile(String userId,String fileName)
	{
		return ServerRunner.users.newFile(userId, fileName);
	}
	
	@Override
	public boolean renameFile(String userId,String oldName,String newName)
	{
		return ServerRunner.users.renameFile(userId,oldName,newName);
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName,String version) {
		return ServerRunner.users.writeFile(file, userId, fileName,version);
	}

	@Override
	public String readFile(String userId, String fileName,String version) {
		// TODO Auto-generated method stub
		return ServerRunner.users.readFile(userId, fileName,version);
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		return ServerRunner.users.getFileList(userId);
	}

	@Override
	public String readVersionList(String userId, String fileName) throws RemoteException {
		// TODO Auto-generated method stub
		return ServerRunner.users.getVersionList(userId, fileName);
	}
	
}
