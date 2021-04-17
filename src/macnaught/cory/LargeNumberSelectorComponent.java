package macnaught.cory;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LargeNumberSelectorComponent  extends JPanel implements ISpecifics{
	
	private JButton buttonUp = new JButton("+");
	private JLabel label = new JLabel();
	private JTextField txt = new JTextField("0");
	private JButton buttonDown = new JButton("-");
	
	private int value;
	
	// Constructors
	public LargeNumberSelectorComponent(String labelName, int value) {
		
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
			incrementValue();
		});
		
		this.buttonDown.addActionListener((ActionEvent event) ->
		{
			decrementValue();
		});
		
		// Layout of items
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
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
		}
		
		txt.setText(Integer.toString(this.getValue()));
	}
	
	public int getValue() {
		return value;
	}
}