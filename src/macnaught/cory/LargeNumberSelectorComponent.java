package macnaught.cory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

public class LargeNumberSelectorComponent extends JPanel implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonUp = new JButton("+");
	private JLabel label = new JLabel();
	private JTextField txt = new JTextField("0");
	private JButton buttonDown = new JButton("-");
	
	private int value;
	
	// Constructors
	public LargeNumberSelectorComponent(String labelName, int value) {
		this.addKeyListener(this);
		
		this.setValue(value);
		label.setText(labelName);
		
		// Listeners
		this.txt.addKeyListener(new KeyAdapter(){
        	@Override
	        public void keyReleased(KeyEvent ke) {
	            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
	            	String typed = txt.getText();
	            	
	            	if(typed.matches("[0-9]+")) setValue(Integer.parseInt(typed));
	            	else setValue(0);
	            }
	        }
    	});
		
		this.buttonUp.addActionListener((ActionEvent event) ->
		{
			this.incrementValue();
			this.requestFocus();
		});
		
		this.buttonDown.addActionListener((ActionEvent event) ->
		{
			this.decrementValue();
			this.requestFocus();
		});
		
		// Layout of items
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		this.add(label, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(buttonUp, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(txt, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(buttonDown, c);
	}
	
	// Functions
	private void incrementValue() {
		if (this.getValue() + 1 <= Integer.MAX_VALUE) {
			this.setValue(this.getValue() + 1);
		}
	}
	
	private void decrementValue() {
		if (this.getValue() - 1 >= Integer.MIN_VALUE) {
			this.setValue(this.getValue() - 1);
		}
	}
	
	// Setters/Getters
	private void setValue(int value) {
		if (value >= 0) {
			this.value = value;
			fireLargeNumberSelectorComponentEvent(new LargeNumberSelectorComponentEvent(this.getValue()));
		}
		else {
			this.value = 0;
		}
		
		txt.setText(Integer.toString(this.getValue()));
	}
	
	public int getValue() {
		return value;
	}
	
	//Event Listeners
	protected EventListenerList listenerList = new EventListenerList();
	
	public void addLargeNumberSelectorComponentEventListener(LargeNumberSelectorComponentEventListener listener)
	{
		listenerList.add(LargeNumberSelectorComponentEventListener.class, listener);
	}
	
	public void removeLargeNumberSelectorComponentEventListener(LargeNumberSelectorComponentEventListener listener)
	{
		listenerList.remove(LargeNumberSelectorComponentEventListener.class, listener);
	}

	public void fireLargeNumberSelectorComponentEvent(LargeNumberSelectorComponentEvent evt)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2)
		{
			if (listeners[i] == LargeNumberSelectorComponentEventListener.class)
			{
				((LargeNumberSelectorComponentEventListener)listeners[i + 1]).largeNumberSelectorComponentEventOccurred(evt);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP) {
        	this.buttonUp.doClick();
        }
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	this.buttonDown.doClick();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}