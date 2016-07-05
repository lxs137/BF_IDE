package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public class FileNameDialog
{	
	private JDialog dialog;
	private JTextField fileNameText;
	private JButton createButton;
	private JLabel nameErrorLabel;

	public FileNameDialog(JFrame parent,int x,int y,int width,int height) {
		dialog=new JDialog(parent,"输入文件名",true);
		fileNameText=new JTextField();
		createButton=new JButton("确认");
		nameErrorLabel=new JLabel("");
		GroupLayout groupLayout=new GroupLayout(dialog.getContentPane());
		dialog.getContentPane().setLayout(groupLayout);
		
		GroupLayout.SequentialGroup horizontalGroup=groupLayout.createSequentialGroup();
		horizontalGroup.addGap(10);
		horizontalGroup.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(fileNameText).addComponent(nameErrorLabel));
		horizontalGroup.addGap(10);
		horizontalGroup.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
				.addComponent(createButton));
		horizontalGroup.addGap(10);
		groupLayout.setHorizontalGroup(horizontalGroup);
		
		GroupLayout.SequentialGroup verticalgroup=groupLayout.createSequentialGroup();
		verticalgroup.addGap(25);
		verticalgroup.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
				.addComponent(fileNameText).addComponent(createButton));
		verticalgroup.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(nameErrorLabel));
		verticalgroup.addGap(20);
		groupLayout.setVerticalGroup(verticalgroup);
		
		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileNameText.getText().equals("")){
					nameErrorLabel.setText("文件名不能为空");
					nameErrorLabel.setForeground(Color.RED);
				}
				else if(!fileNameText.getText().matches("(\\w|[_])*")){
					nameErrorLabel.setText("文件名只能含数字字母或下划线");
					nameErrorLabel.setForeground(Color.RED);
				}
				else {
					nameErrorLabel.setText("");
					hideDialog();
				}
					
			}
		});
		
		dialog.setSize(width, height);
		dialog.setLocation(x, y);
		dialog.setVisible(false);
	}
	
	public void showDialog()
	{
		this.dialog.setVisible(true);
	}
	
	public void hideDialog()
	{
		this.dialog.setVisible(false);
	}
	
	public String getFileName()
	{
		return fileNameText.getText();
	}

}
