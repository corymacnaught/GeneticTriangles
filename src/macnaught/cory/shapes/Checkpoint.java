package macnaught.cory.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Checkpoint extends Obstacle{

	private final int ID;
	private static int nextID = 0;
	
	public Checkpoint() {
		super(Color.RED);
		this.ID = nextID++;
	}
	
	public int getID() {
		return this.ID;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.getColor());
		g2d.fillOval((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
	}
}
