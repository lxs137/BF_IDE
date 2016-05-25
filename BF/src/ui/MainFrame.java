package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rmi.RemoteHelper;


public class MainFrame extends JFrame 
{
	private JTextArea codeText;
	private JTextArea paramText;
	private JLabel resultLabel;
	private JLabel hintLabel;
	private JScrollPane textScrollPane;

	public MainFrame() 
	{
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);
		frame.setResizable(false);

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
		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());
		
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
		textScrollPane.setBorder(BorderFactory.createEtchedBorder());
		frame.add(textScrollPane);
		
		//显示提示
		hintLabel=new JLabel();
		hintLabel.setText("hint");
		hintLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		hintLabel.setSize(580,30);
		hintLabel.setLocation(8,310);
		hintLabel.setBorder(BorderFactory.createBevelBorder(10));
		frame.add(hintLabel);

		//显示输入
		paramText=new JTextArea();
		paramText.setText("");
		paramText.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		paramText.setSize(290,50);
		paramText.setLocation(8,350);
		frame.add(paramText);
		
		//显示结果
		resultLabel = new JLabel();
		resultLabel.setText("");
		resultLabel.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));	
		resultLabel.setSize(290,50);
		resultLabel.setLocation(300,350);
		frame.add(resultLabel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setLocation(300, 100);
		frame.setVisible(true);
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
					// TODO Auto-generated catch block
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
