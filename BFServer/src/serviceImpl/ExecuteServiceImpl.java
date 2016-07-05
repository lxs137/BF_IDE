//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Stack;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService 
{
	
	private ArrayList<Character> codeArrayList; //储存所有代码的ArrayList
	private ArrayList<Character> calculateArrayList; //储存计算中间结果的ArrayList
	private Stack<Integer> bracketStack; //储存'[',']'符号的栈，以便于跳转的实现
	private String param;//储存输入的字符串
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

	/**
	 * 清空上一次计算后残留的代码空间，并将要处理的代码放入codeArrayList，输入放入param
	 * @param code
	 * @param param
	 */
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
			//逐个扫描字符，实现各自的功能
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
	/**
	 * 返回下一个']'字符的位置
	 * @param code_index 当前字符所在位置
	 */
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
	
	/**
	 * 返回上一个'['字符的位置
	 * @param code_index 当前字符的位置
	 */
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
