package macnaught.cory.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Goal extends Shape{
	
	public Goal() {
		super(Color.YELLOW);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.getColor());
		g2d.fillOval((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
	}

}
