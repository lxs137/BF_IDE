package ui;

import java.util.Stack;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UndoTextArea extends JTextArea implements DocumentListener{
	private String initialValue;
	private Stack<DocumentCmd> undoStack;
	private Stack<DocumentCmd> redoStack;
	
	public UndoTextArea(String initialContent) 
	{
		if(initialContent!=null)
			this.initialValue=initialContent;
		else this.initialValue="";
		undoStack=new Stack<DocumentCmd>();
		redoStack=new Stack<DocumentCmd>();
		this.getDocument().addDocumentListener(this);
	}
	
	public boolean redo()
	{
		if(redoStack.isEmpty())
			return false;
		else 
		{
			undoStack.push(redoStack.pop());
			this.setText(undoStack.peek().getResult());
			if(!undoStack.isEmpty()) undoStack.pop();
			if(!undoStack.isEmpty()) undoStack.pop();
			return true;
		}
	}
	
	public boolean undo()
	{
		if(undoStack.isEmpty())
			return false;
		else 
		{
			redoStack.push(undoStack.pop());
			if(undoStack.isEmpty())
				this.setText(initialValue);
			else
				this.setText(undoStack.peek().getResult());
			if(!undoStack.isEmpty()) undoStack.pop();
			if(!undoStack.isEmpty()) undoStack.pop();
			return true;
		}
	}
	
	public void clearHistory()
	{
		this.initialValue=this.getText();
		undoStack.clear();
		redoStack.clear();
	}
	private void updateStack(DocumentEvent e)
	{
		DocumentCmd cmd=new DocumentCmd(e.getType().toString(),e.getOffset(),e.getLength(),this.getText());
		if(undoStack.isEmpty())
			undoStack.push(cmd);
		else {
			if(cmd.checkMerge(undoStack.peek()))
				undoStack.push(cmd.merge(undoStack.pop()));
			else
				undoStack.push(cmd);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateStack(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateStack(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
	
}

