package macnaught.cory;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberDisplayComponent extends JPanel{
	private JLabel label = new JLabel();
	private JLabel seperator = new JLabel(": ");
	private JLabel value = new JLabel();
	
	public NumberDisplayComponent(String name, int value) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		this.setLabel(name);
		this.setValue(value);
		
		this.add(this.label);
		this.add(this.seperator);
		this.add(this.value);
	}
	
	
	private void setLabel(String name) {
		if (name.matches("[a-zA-Z]+")) {
			this.label.setText(name);
		}
	}
	
	public String getLabel() {
		return this.label.getText();
	}
	
	public void setValue(int value) {
		this.value.setText(Integer.toString(value));
	}
	
	public int getValue() {
		return Integer.parseInt(this.value.getText());
	}
}
