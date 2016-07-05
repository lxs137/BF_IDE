package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import rmi.RemoteHelper;
import runner.ClientRunner;

public class LoginDialog{
	private JDialog loginDialog;
	private JLabel userIDJLabel;
	private JTextField userIDJText;
	private JLabel passwordJLabel;
	private JPasswordField passwordJText;
	private JLabel hintErrorJLabel1;
	private JLabel hintErrorJLabel2;
	private JButton signUpJButton;
	private JButton loginJButton;

	public LoginDialog(JFrame parent,int x,int y,int width,int height)
	{
		loginDialog=new JDialog(parent, "登录或注册",true);
		userIDJLabel=new JLabel("              UserID");
	    userIDJText=new JTextField();
		passwordJLabel=new JLabel("             Password");
		passwordJText=new JPasswordField();
		hintErrorJLabel1=new JLabel(" ");		
		hintErrorJLabel2=new JLabel(" ");		
		signUpJButton=new JButton("Sign Up");
		loginJButton=new JButton(" Login  ");
		GroupLayout groupLayout=new GroupLayout(loginDialog.getContentPane());
		loginDialog.getContentPane().setLayout(groupLayout);
		
		loginDialog.setSize(width, height);
		loginDialog.setLocation(x, y);
		loginDialog.setResizable(false);
		loginDialog.setVisible(false);
		
		//使用GroupLayout布局方式，横向布局
		GroupLayout.SequentialGroup horizontalGroup=groupLayout.createSequentialGroup();
		horizontalGroup.addGap(30);
		horizontalGroup.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(userIDJLabel)
				.addComponent(passwordJLabel).addComponent(loginJButton));
		horizontalGroup.addGap(40);
		horizontalGroup.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(userIDJText).addComponent(hintErrorJLabel1)
				.addComponent(passwordJText).addComponent(hintErrorJLabel2).addComponent(signUpJButton));
		horizontalGroup.addGap(30);
		groupLayout.setHorizontalGroup(horizontalGroup);
		//使用GroupLayout布局方式，纵向布局
		GroupLayout.SequentialGroup verticalGroup=groupLayout.createSequentialGroup();
		verticalGroup.addGap(50);
		verticalGroup.addGroup(groupLayout.createParallelGroup().addComponent(userIDJLabel).addComponent(userIDJText));
		verticalGroup.addGroup(groupLayout.createParallelGroup().addComponent(hintErrorJLabel1));
		verticalGroup.addGap(5);
		verticalGroup.addGroup(groupLayout.createParallelGroup().addComponent(passwordJLabel).addComponent(passwordJText));
		verticalGroup.addGroup(groupLayout.createParallelGroup().addComponent(hintErrorJLabel2));
		verticalGroup.addGap(20);
		verticalGroup.addGroup(groupLayout.createParallelGroup().addComponent(loginJButton).addComponent(signUpJButton));
		verticalGroup.addGap(30);
		groupLayout.setVerticalGroup(verticalGroup);
		//登陆按钮监听事件
		loginJButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try 
				{
					if(RemoteHelper.getInstance().getUserService().login(userIDJText.getText(),
							passwordJText.getText())){											
						RemoteHelper.getInstance().getUserService().logout(ClientRunner.mainFrame.userID);//之前用户登出
						ClientRunner.mainFrame.exitFrame();//前一用户界面资源的销毁
						hintErrorJLabel1.setText(" ");
						hintErrorJLabel2.setText(" ");
						hideDialog();
						ClientRunner.mainFrame=new MainFrame(userIDJText.getText());//重新初始化主界面
						ClientRunner.mainFrame.showFrame();
						
					}
					else {
						hintErrorJLabel1.setText("    用户名或密码错误    ");
						hintErrorJLabel2.setText("    用户名或密码错误    ");
						hintErrorJLabel1.setForeground(Color.RED);
						hintErrorJLabel2.setForeground(Color.RED);
					}
				} catch (RemoteException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		signUpJButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try 
				{
					if(RemoteHelper.getInstance().getUserService().signUp(userIDJText.getText(),
							passwordJText.getText()))
					{						
						RemoteHelper.getInstance().getUserService().logout(ClientRunner.mainFrame.userID);//之前用户登出
						ClientRunner.mainFrame.exitFrame();//前一用户界面资源的销毁
						hintErrorJLabel1.setText(" ");
						hintErrorJLabel2.setText(" ");
						hideDialog();						
						ClientRunner.mainFrame=new MainFrame(userIDJText.getText());//重新初始化主界面
						ClientRunner.mainFrame.showFrame();
						
					}
					else
					{
						hintErrorJLabel1.setText("    用户名已存在    ");
						hintErrorJLabel2.setText("    用户名已存在    ");
						hintErrorJLabel1.setForeground(Color.RED);
						hintErrorJLabel2.setForeground(Color.RED);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	public void showDialog()
	{
		this.loginDialog.setVisible(true);
	}
	
	public void hideDialog()
	{
		this.loginDialog.setVisible(false);
	}

}
