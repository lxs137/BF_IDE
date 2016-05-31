package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;


public class MainFrame extends JFrame
{
	private JFrame mainFrame;
	private JFrame loginFrame; 
	private JTextArea codeText;
	private JTextArea paramText;
	private JLabel resultLabel;
	private JLabel hintLabel;
	private JScrollPane textScrollPane;

	public MainFrame() 
	{
		// 创建窗体
		mainFrame= new JFrame("BF Client");
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New File");
		newMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open File");
		openMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save File");
		saveMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		fileMenu.add(saveMenuItem);
		JMenu runMenu=new JMenu("Run");
		runMenu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		menuBar.add(runMenu);		
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		runMenu.add(runMenuItem);
		JButton loginButton=new JButton("Login");
		loginButton.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		loginButton.setFocusable(false);
		menuBar.add(Box.createRigidArea(new Dimension(400,10)));
		menuBar.add(loginButton);
		mainFrame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());
		loginButton.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				initLoginDialog();				
			}
		});
		
		//显示代码
		codeText = new JTextArea();
		codeText.setMargin(new Insets(10, 10, 10, 10));
		codeText.setBackground(Color.WHITE);
		codeText.setLineWrap(true);
		codeText.setFont(new Font(Font.MONOSPACED,Font.PLAIN,25));	
		codeText.setSize(600,300);
		textScrollPane=new JScrollPane(codeText);
		textScrollPane.setSize(600, 300);
		textScrollPane.setLocation(0, 0);
		mainFrame.add(textScrollPane);
		
		//显示提示
		hintLabel=new JLabel();
		hintLabel.setText("hint");
		hintLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		hintLabel.setSize(580,30);
		hintLabel.setLocation(8,310);
		mainFrame.add(hintLabel);

		//显示输入
		paramText=new JTextArea();
		paramText.setText("");
		paramText.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		paramText.setSize(290,20);
		paramText.setLocation(8,350);
		mainFrame.add(paramText);
		
		//显示结果
		resultLabel = new JLabel();
		resultLabel.setText("");
		resultLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));	
		resultLabel.setSize(290,50);
		resultLabel.setLocation(300,350);
		mainFrame.add(resultLabel);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600, 500);
		mainFrame.setLocation(300, 100);
		mainFrame.setVisible(false);
		initLoginDialog();
	}
	
	public void initLoginDialog()
	{
		loginFrame=new JFrame("Login");
		JLabel userIDJLabel=new JLabel("              UserID");
		JTextField userIDJText=new JTextField();
		JLabel passwordJLabel=new JLabel("             Password");
		JPasswordField passwordJText=new JPasswordField();
		JLabel hintErrorJLabel1=new JLabel("    用户名已存在    ");
		hintErrorJLabel1.setForeground(Color.RED);
		JLabel hintErrorJLabel2=new JLabel("    用户名已存在    ");
		hintErrorJLabel2.setForeground(Color.RED);
		JButton signUpJButton=new JButton("Sign Up");
		JButton loginJButton=new JButton(" Login  ");
		GroupLayout groupLayout=new GroupLayout(loginFrame.getContentPane());
		loginFrame.getContentPane().setLayout(groupLayout);
		
		
		loginFrame.setSize((int)(mainFrame.getWidth()/1.5),mainFrame.getHeight()/2);
		loginFrame.setLocation(mainFrame.getX()+100,mainFrame.getY()+100);
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
				// TODO Auto-generated method stub
				loginFrame.setVisible(false);
				mainFrame.setVisible(true);
			}
		});
	}

	class MenuItemActionListener implements ActionListener 
	{
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("New File")) 
			{
				codeText.setText("New File");
			} 
			else if (cmd.equals("Open File")) 
			{
				codeText.setText("Open File");
			} 
			else if (cmd.equals("Run")) 
			{
				try 
				{
					resultLabel.setText("");
					resultLabel.setText(RemoteHelper.getInstance().getExecuteService().execute(codeText.getText(),paramText.getText()));
				} catch (RemoteException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
	}

	class SaveActionListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String code = codeText.getText();
			try 
			{
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
	
}
