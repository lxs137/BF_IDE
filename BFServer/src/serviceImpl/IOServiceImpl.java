package serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		
		return true;
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		return "OK";
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		return "OK";
	}
	
}
