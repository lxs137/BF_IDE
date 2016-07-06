package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rmi.RemoteHelper;

public class MainFrame extends JFrame
{
	public String userID;
	
	private JFrame mainFrame; 
	private LoginDialog loginDialog;
	private FileNameDialog fileNameDialog;
		
	private DefaultListModel<String> listModel;
	private JList<String> fileList;
	private JTextArea codeText;
	private JTextArea paramText;
	private JTextArea resultLabel;
	private JLabel hintLabel;
	private VersionMenu versionMenu;

	public MainFrame(String userID)  {
		// 创建窗体
		this.userID=userID;
		mainFrame= new JFrame("BF Client");
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);

		//创建菜单栏，包含File，Version，Run三个子菜单
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
		versionMenu=new VersionMenu("Version");
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
		saveMenuItem.addActionListener(new MenuItemActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());
		loginButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				loginDialog.showDialog();				
			}
		});
										
		//显示代码
		codeText = new JTextArea();
		codeText.setMargin(new Insets(10, 10, 10, 10));
		codeText.setBackground(Color.WHITE);
		codeText.setLineWrap(true);
		codeText.setFont(new Font(Font.MONOSPACED,Font.PLAIN,25));	
		codeText.setSize(600,300);		
		JScrollPane textScrollPane=new JScrollPane(codeText);
		textScrollPane.setSize(442, 300);
		textScrollPane.setLocation(150, 0);
		mainFrame.add(textScrollPane);
		
		//显示提示
		hintLabel=new JLabel();
		hintLabel.setText("param                                  result");
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
		resultLabel = new JTextArea();
		resultLabel.setBackground(Color.DARK_GRAY);
		resultLabel.setForeground(Color.GREEN);
		resultLabel.setText("");
		resultLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));	
		resultLabel.setSize(260,20);
		resultLabel.setLocation(330,350);
		mainFrame.add(resultLabel);

		//设置关闭窗口事件
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		mainFrame.setSize(600, 450);
		mainFrame.setLocation(300, 100);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//登录界面初始化
		loginDialog=new LoginDialog(this.mainFrame,mainFrame.getX()+100,mainFrame.getY()+100, 
				(int)(mainFrame.getWidth()/1.5),mainFrame.getHeight()/2);
		try {
			String fileStr;
			if(!userID.equals("")) fileStr=RemoteHelper.getInstance().getIOService().readFileList(userID);
			else fileStr="noFile\r\n";
			fileStr=((fileStr==null)?(""):fileStr);
			String[] fileStrArra=fileStr.split("\r\n");
			initList(fileStrArra);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
	public void showFrame(){ mainFrame.setVisible(true);}
	
	public void hideFrame(){ mainFrame.setVisible(false);}
	
	public void showLoginDialog(){ loginDialog.showDialog();}
	
	public void exitFrame(){
		mainFrame.setVisible(false);
		mainFrame.dispose();
	}

	private void initList(String[] fileStrArra){
		/**
		 * 初始化文件列表
		 */
		listModel=new DefaultListModel<String>();
		for(String temp:fileStrArra)
			listModel.addElement(temp);	
		fileList=new JList<String>(listModel);
		fileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fileList.setVisibleRowCount(10);
		fileList.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				versionMenu.setCurrentFileVersion("noFile");
				versionMenu.clearVersionList();
				versionMenu.initVersionList(userID,fileList.getSelectedValue(),codeText);
				codeText.setText("");				
			}
		});
		
		Border fileListBorder=BorderFactory.createEtchedBorder();
		JScrollPane fileListScroll=new JScrollPane(fileList);
		fileListScroll.setSize(150,300);
		fileListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		fileListScroll.setLocation(0,0);
		fileListScroll.setVisible(true);
		fileListScroll.setBorder(BorderFactory.createTitledBorder(fileListBorder, "File List"));
		mainFrame.add(fileListScroll);
	}

	class MenuItemActionListener implements ActionListener {
		/**
		 * 菜单栏子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("New File")) {
				fileNameDialog=new FileNameDialog(mainFrame,mainFrame.getX()+150,mainFrame.getY()+150, 
						(int)(mainFrame.getWidth()/2),(int)(mainFrame.getHeight()/4));		
				fileNameDialog.showDialog();
				try{
					//服务器监测文件名是否重名
					if(!RemoteHelper.getInstance().getIOService().newFile(userID, fileNameDialog.getFileName()))
						JOptionPane.showMessageDialog(mainFrame, "文件名已存在","",JOptionPane.ERROR_MESSAGE);					
					else listModel.addElement(fileNameDialog.getFileName());
				}catch(RemoteException re)
				{
					re.printStackTrace();
				}
			} 
			else if (cmd.equals("Run"))  {
				try {
					resultLabel.setText("");
					resultLabel.setText(RemoteHelper.getInstance().getExecuteService().execute(codeText.getText(),paramText.getText()+'\n'));
				} catch (RemoteException e1) 
				{
					e1.printStackTrace();
				}
			}
			else if (cmd.equals("Save File")) {
				String code = codeText.getText();
				try {
					//保存文件内容到服务器端
					RemoteHelper.getInstance().getIOService().writeFile(code,userID,
							fileList.getSelectedValue(),versionMenu.getCurrentFileVersion());
					versionMenu.initVersionList(userID,fileList.getSelectedValue(),codeText);
					versionMenu.setCurrentFileVersion(versionMenu.getNewestVersion());
				} catch (RemoteException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
	}	
}
