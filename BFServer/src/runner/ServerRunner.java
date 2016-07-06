package runner;

import data.Users;
import rmi.RemoteHelper;

public class ServerRunner {
	
	public static Users users;
	
	public ServerRunner() {
		new RemoteHelper();
		users=new Users();
	}
	
	public static void main(String[] args) {
		new ServerRunner();
	}
}
