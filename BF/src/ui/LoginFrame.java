package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;
import runner.ClientRunner;

import javax.swing.GroupLayout.Alignment;

public class LoginFrame 
{
	private JFrame loginFrame;
	private JLabel userIDJLabel;
	private JTextField userIDJText;
	private JLabel passwordJLabel;
	private JPasswordField passwordJText;
	private JLabel hintErrorJLabel1;
	private JLabel hintErrorJLabel2;
	private JButton signUpJButton;
	private JButton loginJButton;
	
	public LoginFrame(int width,int height,int x,int y)
	{
		loginFrame=new JFrame("Login");
		userIDJLabel=new JLabel("              UserID");
	    userIDJText=new JTextField();
		passwordJLabel=new JLabel("             Password");
		passwordJText=new JPasswordField();
		hintErrorJLabel1=new JLabel(" ");		
		hintErrorJLabel2=new JLabel(" ");		
		signUpJButton=new JButton("Sign Up");
		loginJButton=new JButton(" Login  ");
		GroupLayout groupLayout=new GroupLayout(loginFrame.getContentPane());
		loginFrame.getContentPane().setLayout(groupLayout);
		
		
		loginFrame.setSize(width,height);
		loginFrame.setLocation(x,y);
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);
		
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
		
		loginJButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try 
				{
					if(RemoteHelper.getInstance().getUserService().login(userIDJText.getText(),
							passwordJText.getText())){
						hideFrame();
						ClientRunner.mainFrame.showFrame();
						RemoteHelper.getInstance().getUserService().logout(MainFrame.userID);
						MainFrame.userID=userIDJText.getText();
						hintErrorJLabel1.setText(" ");
						hintErrorJLabel2.setText(" ");
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
						hideFrame();
						ClientRunner.mainFrame.showFrame();
						RemoteHelper.getInstance().getUserService().logout(MainFrame.userID);
						MainFrame.userID=userIDJText.getText();
						hintErrorJLabel1.setText(" ");
						hintErrorJLabel2.setText(" ");
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
	
	public void showFrame()
	{
		loginFrame.setVisible(true);
	}
	
	public void hideFrame()
	{
		loginFrame.setVisible(false);
	}
	

}
