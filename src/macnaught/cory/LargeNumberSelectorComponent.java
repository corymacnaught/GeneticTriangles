package macnaught.cory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	
	public LargeNumberSelectorComponent(String labelName, int value) {
		
		this.setValue(value);
		label.setText(labelName);
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
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(buttonUp, c);
		
		c.gridy = 1;
		this.add(label, c);
		
		c.gridy = 2;
		c.ipady = 10;
		this.add(txt, c);
		
		c.gridy = 3;
		this.add(buttonDown, c);
	}
	
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
