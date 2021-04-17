package macnaught.cory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionsPane extends JPanel implements ISpecifics{
	
	private LargeNumberSelectorComponent triangleSelector;
	private LargeNumberSelectorComponent generationSelector;
	
	public OptionsPane() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		this.triangleSelector = new LargeNumberSelectorComponent("Triangle", 0);
		this.generationSelector = new LargeNumberSelectorComponent("Generation", 0);
		
		this.add(triangleSelector);
		this.add(generationSelector);
	}
}
