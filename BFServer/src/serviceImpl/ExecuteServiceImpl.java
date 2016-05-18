//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Stack;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService 
{
	/**
	 * 请实现该方法
	 */
	private ArrayList<Character> codeArrayList;
	private ArrayList<Character> calculateArrayList;
	private Stack<Integer> bracketStack;
	private String param;
	public ExecuteServiceImpl()
	{
		super();
		codeArrayList=new ArrayList<Character>();
		calculateArrayList=new ArrayList<Character>();
		bracketStack=new Stack<Integer>();
		param=new String();
	}
	
	@Override
	public String execute(String code, String param) throws RemoteException 
	{
		initData(code, param);
		String result=doCalculate();
		return result;
	}

	private void initData(String code,String param) 
	{
		this.param=param;
		codeArrayList.clear();
		calculateArrayList.clear();
		int i=0,n=code.length();
		while(i<n)
		{
			codeArrayList.add(code.charAt(i));
			i++;
		}
	}
	private String doCalculate()
	{
		StringBuffer result=new StringBuffer();
		int code_index=0,code_n=codeArrayList.size(),calculate_index=0,param_index=0;
		while(code_index<code_n)
		{
			switch (codeArrayList.get(code_index)) 
			{
			case '>':
				calculate_index++;
				code_index++;
				break;
			case '<':
				calculate_index--;
				code_index++;
				break;
			case '+':
				if(calculateArrayList.size()>calculate_index) calculateArrayList.set(calculate_index,(char) (calculateArrayList.get(calculate_index)+1));
				else calculateArrayList.add(calculate_index, (char)1);
				code_index++;
				break;
			case '-':
				if(calculateArrayList.size()>calculate_index) calculateArrayList.set(calculate_index,(char) (calculateArrayList.get(calculate_index)-1));
				else return "error:code is wrong1";
				code_index++;
				break;
			case '.':
				result.append((char)calculateArrayList.get(calculate_index));
				code_index++;
				break;
			case ',':
				if(calculateArrayList.size()>calculate_index) calculateArrayList.set(calculate_index,(char) (param.charAt(param_index)));
				else calculateArrayList.add(calculate_index, (char) (param.charAt(param_index)));
				code_index++;
				param_index++;
				break;
			case '[':
				if(calculateArrayList.get(calculate_index)==0) code_index=jumpNext(code_index);
				else code_index++;
				break;
			case ']':
				if(calculateArrayList.get(calculate_index)!=0) code_index=jumpBefore(code_index);
				else code_index++;
				break;
			default:
				return "error:code is wrong2";
			}
		}
		return result.toString();
	}
	private int jumpNext(int code_index)
	{
		bracketStack.clear();
		bracketStack.push(code_index);
		int i=code_index+1,n=codeArrayList.size();
		while(!bracketStack.isEmpty())
		{
			while(i<n)
			{
				if(codeArrayList.get(i)=='['||codeArrayList.get(i)==']') break;
				else i++;
			}
			if(i<n&&codeArrayList.get(i)==codeArrayList.get(bracketStack.peek())) bracketStack.push(i);
			else if(i<n&&codeArrayList.get(i)!=codeArrayList.get(bracketStack.peek())) bracketStack.pop();
			else break;
			i++;
		}
		if(!bracketStack.isEmpty()) return -1;
		else return i-1;
	}
	private int jumpBefore(int code_index)
	{
		bracketStack.clear();
		bracketStack.push(code_index);
		int i=code_index-1;
		while(!bracketStack.isEmpty())
		{
			while(i>=0)
			{
				if(codeArrayList.get(i)=='['||codeArrayList.get(i)==']') break;
				else i--;
			}
			if(i>=0&&codeArrayList.get(i)==codeArrayList.get(bracketStack.peek())) bracketStack.push(i);
			else if(i>=0&&codeArrayList.get(i)!=codeArrayList.get(bracketStack.peek())) bracketStack.pop();
			else break;
			i--;
		}
		if(!bracketStack.isEmpty()) return -1;
		else return i+1;
	}	
}
