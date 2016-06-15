package serviceImpl;


import runner.ServerRunner;
import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean newFile(String userId,String fileName)
	{
		return ServerRunner.users.newFile(userId, fileName);
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		return ServerRunner.users.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		return ServerRunner.users.readFile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		return ServerRunner.users.getFileList(userId);
	}
	
}
