package macnaught.cory;

import java.util.EventListener;
import java.util.EventObject;

import macnaught.cory.shapes.Obstacle;

@SuppressWarnings("serial")
public class DisplayEvent extends EventObject
{
	private Obstacle obstacle;
	
	public DisplayEvent(Object source)
	{
		super(source);
		if (source instanceof Obstacle)
			this.obstacle = (Obstacle)source;
	}
	
	public Obstacle getObstacle() {
		return this.obstacle;
	}
}

 interface DisplayEventListener extends EventListener
 {
	 public void displayEventOccurred(DisplayEvent evt);
 }