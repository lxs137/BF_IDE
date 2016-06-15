package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import rmi.RemoteHelper;


public class MainFrame extends JFrame
{
	public static String userID;
	
	private JFrame mainFrame; 
	private LoginFrame loginFrame;
	
	
	private DefaultListModel<String> listModel;
	private JList<String> fileList;
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
		JMenuItem saveMenuItem = new JMenuItem("Save File");
		saveMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		fileMenu.add(saveMenuItem);
		JMenu versionMenu=new JMenu("Version");
		versionMenu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		menuBar.add(versionMenu);
		JMenu runMenu=new JMenu("Run");
		runMenu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		menuBar.add(runMenu);		
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenuItem.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		runMenu.add(runMenuItem);
		JButton loginButton=new JButton("Login");
		loginButton.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		loginButton.setFocusable(false);
		menuBar.add(Box.createRigidArea(new Dimension(300,10)));
		menuBar.add(loginButton);
		mainFrame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());
		loginButton.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				loginFrame.showFrame();				
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
		textScrollPane.setSize(442, 300);
		textScrollPane.setLocation(150, 0);
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
		loginFrame=new LoginFrame((int)(mainFrame.getWidth()/1.5),mainFrame.getHeight()/2,
				mainFrame.getX()+100,mainFrame.getY()+100);
		
		//显示文件列表
		try 
		{
			String fileStr=RemoteHelper.getInstance().getIOService().readFileList(userID);
			fileStr=((fileStr==null)?(""):fileStr);
			String[] fileStrArra=fileStr.split("\r\n");
			initList(fileStrArra);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//showFrame();
	}
	
	public void showFrame()
	{
		mainFrame.setVisible(true);
	}
	
	public void hideFrame()
	{
		mainFrame.setVisible(false);
	}
	
	private void initList(String[] fileStrArra)
	{
		listModel=new DefaultListModel<String>();
		for(String temp:fileStrArra)
		{
			listModel.addElement(temp);
		}	
		fileList=new JList<String>(listModel);
		fileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fileList.setVisibleRowCount(10);
		Border fileListBorder=BorderFactory.createEtchedBorder();
		JScrollPane fileListScroll=new JScrollPane(fileList);
		fileListScroll.setSize(150,300);
		fileListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		fileListScroll.setLocation(0,0);
		fileListScroll.setVisible(true);
		fileListScroll.setBorder(BorderFactory.createTitledBorder(fileListBorder, "File List"));
		mainFrame.add(fileListScroll);
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
