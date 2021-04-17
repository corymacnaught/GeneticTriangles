package macnaught.cory.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Shape{

	private Color color;
	
	// Position
	private double x;
	private double y;
	
	// Dimensions
	private int width;
	private int height;
	
	// Constructors
	public Shape() {
		this(Color.WHITE);
	}
	
	public Shape(Color color) {
		super();
		this.setColor(color);
		this.setBounds(0, 0, 0, 0);
	}
	
	// Functions
	public void addX(double value) {
		this.setX(this.x + value);
	}
	
	public void addY(double value) {
		this.setY(this.y + value);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void setBounds(double x, double y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public boolean intersects(Shape shape) {
		float xmin = Math.max(this.getX(), shape.getX());
	    float xmax1 = this.getX() + this.getWidth();
	    float xmax2 = shape.getX() + shape.getWidth();
	    float xmax = Math.min(xmax1, xmax2);
	    if (xmax > xmin) {
	        float ymin = Math.max(this.getY(), shape.getY());
	        float ymax1 = this.getY() + this.getHeight();
	        float ymax2 = shape.getY() + shape.getHeight();
	        float ymax = Math.min(ymax1, ymax2);
	        if (ymax > ymin) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public boolean isIntersecting(ArrayList<Obstacle> obstacleList) {
		for (Obstacle obstacle : obstacleList) {
			if (obstacle instanceof Wall) {
				if (this.intersects(obstacle)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract void draw(Graphics2D g2d);
	
	// Setters/Getters
	//Positions
	public void setX(double value) {
		this.x = value;
	}
	
	public void setY(double value) {
		this.y = value;
	}
	
	public int getX() {
		return (int) Math.round(this.x);
	}
	
	public int getY() {
		return (int) Math.round(this.y);
	}
	
	public int getCenterX() {
		return this.getX() + this.getWidth() / 2;
	}
	
	public int getCenterY() {
		return this.getY() + this.getHeight() / 2;
	}
	
	public int getRightX() {
		return this.getX() + this.getWidth();
	}
	
	public int getBottomY() {
		return this.getY() + this.getHeight();
	}
	
	//Dimensions
	public void setWidth(int value) {
		this.width = value > 0 ? value : 0;
	}
	
	public void setHeight(int value) {
		this.height = value > 0 ? value : 0;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	//Color (Should be set to protected)
	public void setColor(Color value) {
		this.color = value;
	}
	
	public Color getColor() {
		return this.color;
	}
}
