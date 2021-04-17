package macnaught.cory.shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Random;

/* The class that contains the information for an individual gene */
public class Gene extends Shape{

	private int degree; // The encoding of the direction
	private int force; // The encoding of the direction
	
	private static final int MAX_FORCE = 10;
	private static final int MAX_DEGREE = 359;
	
	// Contructors
	// Generate random gene
	/* Default constructor */
	
	public Gene() {
		this.mutate();
	}
	
	public Gene(Gene vector) {
		this(vector.getDegree(), vector.getForce());
		this.setBounds(vector.getX(), vector.getY(), vector.getWidth(), vector.getHeight());
	}
	
	// Generate a preset gene
	/* Create class with a preset character */
	/* Sets gene to the parameter c if c is contained in alphabet */
	/* else sets gene to the first character in alphabet */
	public Gene(int degree, int force) {
		this.setDegree(degree);
		this.setForce(force);
	}
	
	// Functions
	// Mutate the gene
	/* Changes gene to a random character in the alphabet */
	public void mutate() {
		Random rnd = new Random();
		this.setDegree(rnd.nextInt(Gene.MAX_DEGREE + 1));
		this.setForce(rnd.nextInt(Gene.MAX_FORCE + 1));
	}
	
	public double forceX() {
		return this.getForce() * Math.cos(Math.toRadians(this.getDegree()));
	}
	
	public double forceY() {
		return this.getForce() * Math.sin(Math.toRadians(this.getDegree()));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		Point[] points = {new Point(this.getX(), this.getBottomY()), new Point(this.getCenterX(), this.getY()), new Point(this.getRightX(), this.getBottomY())};
		
		AffineTransform.getRotateInstance(Math.toRadians(this.getDegree()), this.getCenterX(), this.getCenterY()).transform(points, 0, points, 0, 3);
		
		g2d.setColor(this.getColor());
		g2d.fillPolygon(new int[] {(int) points[0].getX(), (int) points[1].getX(), (int) points[2].getX()}, new int[] {(int) points[0].getY(), (int) points[1].getY(), (int) points[2].getY()}, 3);
	}
	
	// Setters/Getters
	private void setDegree(int degree) {
		if (degree >= 0 && degree < Gene.MAX_DEGREE) {
			this.degree = degree;
		}
	}
	
	private void setForce(int force) {
		if (force >= 0 && force <= Gene.MAX_FORCE) {
			this.force = force;
			this.setColor(new Color(force * 255 / Gene.MAX_FORCE, 0, 0));
		}
	}
	
	public int getDegree() {
		return this.degree;
	}
	
	// Get gene encoding
	/* Getter for gene */
	public int getForce() {
		return this.force;
	}
}