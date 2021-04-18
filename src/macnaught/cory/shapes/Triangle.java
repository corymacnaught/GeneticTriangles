package macnaught.cory.shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import macnaught.cory.interfaces.ISpecifics;

public class Triangle extends Shape{

	private Gene[][] vectors; // Genome
	private int fitness; // Score
	private double fitnessMultiplier; // multiplier of the fit
	private double degree;
	
	// Starting Position
	public static final double STARTING_X = 50;
	public static final double STARTING_Y = 50;
	
	// Chance to mutate
	private int mutationChance;
	
	private Point[][] genePositions = new Point[ISpecifics.NUM_ROWS][ISpecifics.NUM_COLUMNS];
	
	private enum Force {
	    CLOSEST,
	    SURROUNDING,
	    ALL,
	    ALL_FAST
	}
	private Force force;
	
	// Constructors
	// Create random DNA
	/* Fills dna with random objects of type Gene */
	public Triangle() {
		super(Color.GREEN);
		this.initialize();
		this.placeGeneLocation(25, 25);
		
		// Create Genes
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				Gene gene = new Gene();
				Point point = genePositions[i][g];
				gene.setBounds(point.getX(), point.getY(), 25, 25);
				this.vectors[i][g] = gene;
			}
		}
	}
	
	// Create DNA from both parents
	/* Fills dna with objects of type gene selected randomly from one of the two parents */
	/* Each gene has a chance to be changed to a random new character */
	public Triangle(Triangle parent1, Triangle parent2) {
		super(Color.GREEN);
		this.initialize();
		
		// Copy parent DNA
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				if ((int) (Math.random() * 2) == 0)
					this.vectors[i][g] = new Gene(parent1.getVector(i, g));
				else
					this.vectors[i][g] = new Gene(parent2.getVector(i, g));
			}
		}
		
		// Select which parent to take the mutation chance from
		if ((int) (Math.random() * 2) == 0)
			this.setMutationChance(parent1.getMutationChance());
		else
			this.setMutationChance(parent2.getMutationChance());
		
		// mutate DNA
		this.mutate();
	}
	
	// Create DNA from one parent
	/* Fills dna with objects of type Gene all taken from parent */
	/* Each gene has a chance to be changed to a random new character */
	public Triangle(Triangle parent) {
		super(Color.GREEN);
		this.initialize();
		
		// Copy parent DNA
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				this.vectors[i][g] = new Gene(parent.getVector(i, g));
			}
		}
		
		// Copy parent mutation chance
		this.setMutationChance(parent.getMutationChance());
		
		// mutate DNA
		this.mutate();
	}
	
	// Common function that all contructors call
	private void initialize() {
		this.fitness = 0;
		this.fitnessMultiplier = 1;
		this.force = Force.ALL;
		this.vectors = new Gene[ISpecifics.NUM_ROWS][ISpecifics.NUM_COLUMNS];
	}
	
	private void placeGeneLocation(int width, int height) {
		// Fill gene positions
		double x = 0;
		double y = 0;
		double startx = x;
		double starty = y;
		double finalx = ISpecifics.PANEL_WIDTH - width;
		double finaly = ISpecifics.PANEL_HEIGHT - height;
		double jumpx = (finalx - startx) / (ISpecifics.NUM_COLUMNS - 1);
		double jumpy = (finaly - starty) / (ISpecifics.NUM_ROWS - 1);
		
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				this.genePositions[i][g] = new Point((int) Math.round(x), (int) Math.round(y));
				x += jumpx;
			}
			x = startx;
			y += jumpy;
		}
	}
	
	// mutate DNA
	/* Randomly changes every object of type Gene in dna to a random new character */
	private void mutate() {
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				if ((int) (Math.random() * 100) < this.getMutationChance()) {
					this.getVector(i, g).mutate();
				}
			}
		}
	}
	
	public void applyForce() {
		// Calculate net force
		double forceX = 0;
		double forceY = 0;
		
		switch (this.force) {
		
			// (Apply closest force)
			case CLOSEST:
				Gene closestGene = this.closestGene();
			
				forceX = closestGene.forceX();
				forceY = closestGene.forceY();
				break;
				
			// (Apply closest and surrounding forces)
			case SURROUNDING:
				break;
				
			// (Apply all forces)
			case ALL:
				double totalCoefficient = 0;
				for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
					for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
						totalCoefficient += Math.pow(distanceToVector(this.getVector(i, g)), 10);
					}
				}
		
				for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
					for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
						double coefficient = Math.pow(distanceToVector(this.getVector(i, g)), 10);
						double forceMultiplier = coefficient / totalCoefficient;
						
						forceX += this.getVector(i, g).forceX() * forceMultiplier;
						forceY += this.getVector(i, g).forceY() * forceMultiplier;
					}
				}
				break;
			// (Apply all forces)
			case ALL_FAST:
				ArrayList<Gene> vectors = new ArrayList<Gene>();
				
				double localCoefficient = 0;
				for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
					for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
						if (distanceToVector(this.getVector(i, g)) < 200) {
							vectors.add(this.getVector(i, g));
							localCoefficient += Math.pow(distanceToVector(this.getVector(i, g)), 10);
						}
					}
				}
		
				for(Gene vector : vectors) {
					double coefficient = Math.pow(distanceToVector(vector), 3);
					double forceMultiplier = coefficient / localCoefficient;
					
					forceX += vector.forceX() * forceMultiplier;
					forceY += vector.forceY() * forceMultiplier;
				}
				break;
		}
		
		// Set Angle of trajectory
		this.calculateDegree(forceX, forceY);
		
		// Apply force
		this.addX(forceX);
		this.addY(forceY);
	}
	
	private Gene closestGene() {
		double closestDistance = Integer.MAX_VALUE;
		Gene closestGene = new Gene(Integer.MIN_VALUE, Integer.MIN_VALUE);
		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				double distance = this.distanceToVector(this.getVector(i, g));
				
				if (distance < closestDistance) {
					closestDistance = distance;
					closestGene = this.vectors[i][g];
				}
			}
		}
		return closestGene;
	}
	
	private double distanceToVector(Gene vector) {
		double distanceX = Math.abs(vector.getCenterX() - this.getCenterX());
		double distanceY = Math.abs(vector.getCenterY() - this.getCenterY());
		
		double a = Math.pow(distanceX, 2);
		double b = Math.pow(distanceY, 2);
		return Math.pow(a + b, 0.5);
	}
	
	private void calculateDegree(double forceX, double forceY) {
		this.degree = (Math.atan2(forceY, forceX) + Math.PI / 2) % (2 * Math.PI);
	}
	
	public int calculateFitness(Goal goal, ArrayList<Obstacle> obstacleList) {
		int fitness = 0;
		
		// Distance between the starting position of the Triangles and Goal
		double distanceStartingPosX = goal.getX() - Triangle.STARTING_X;
		double distanceStartingPosY = goal.getY() - Triangle.STARTING_Y;
		double distanceStartingPos = Math.abs(Math.pow(Math.pow(distanceStartingPosX, 2) + Math.pow(distanceStartingPosY, 2), 0.5));
		
		// Distance between the ending position of the Triangle and Goal
		double distanceX = goal.getX() - this.getX();
		double distanceY = goal.getY() - this.getY();
		//double distance = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);
		double distance = Math.abs(Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5));
		
		// Fitness is the difference between the distance at the beginning of the simulation and current distance of the goal
		//fitness = (int) Math.round(Math.pow(distanceStartingPos - distance, 2));
		fitness = (int) Math.round(distanceStartingPos - distance);
		
		// Triangles that touch the goal are three times as likely to reproduce
		if (this.intersects(goal)) {
			
		}
		
		for (Obstacle obstacle : obstacleList) {
			// change fitness if wall was hit
			if (obstacle instanceof Wall) {
				if (this.intersects(obstacle)) {
					fitness /= 2;
				}
			// Change fitness if checkpoint was hit
			} else if (obstacle instanceof Checkpoint) {
			}
		}
		
		return this.fitness = fitness > 0 ? (int) (fitness * fitnessMultiplier) : 0;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		Point[] points = {new Point(this.getX(), this.getBottomY()), new Point(this.getCenterX(), this.getY()), new Point(this.getRightX(), this.getBottomY())};
		
		AffineTransform.getRotateInstance(this.getDegreeRad(), this.getCenterX(), this.getCenterY()).transform(points, 0, points, 0, 3);
		
		g2d.setColor(this.getColor());
		g2d.fillPolygon(new int[] {(int) points[0].getX(), (int) points[1].getX(), (int) points[2].getX()}, new int[] {(int) points[0].getY(), (int) points[1].getY(), (int) points[2].getY()}, 3);
	}
	
	public void resetPosition() {
		this.setX(Triangle.STARTING_X);
		this.setY(Triangle.STARTING_Y);
	}
	
	// Setters/Getters
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		
		//Used when starting positions were not final
		//this.startingX = x;
		//this.startingY = y;
	}
	
	@Override
	public void setBounds(double x, double y, int width, int height) {
		super.setBounds(x, y, width, height);
		
		//Used when starting positions were not final
		//this.startingX = x;
		//this.startingY = y;
	}
	
	public void setMutationChance(int value) {
		this.mutationChance = value > 0 ? value : 0;
	}
	
	public int getMutationChance() {
		return this.mutationChance;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public Gene getVector(int i, int g) {
		return this.vectors[i][g];
	}
	
	public double getDegreeRad() {
		return this.degree;
	}
	
	public double getDegree() {
		return this.degree * 180 / Math.PI;
	}
	
	public String toString() {
		String string = "";

		for (int i = 0; i < ISpecifics.NUM_ROWS; i++) {
			for (int g = 0; g < ISpecifics.NUM_COLUMNS; g++) {
				string += "(f: " + String.format("%2d", this.vectors[i][g].getForce()) + " -- d: " + String.format("%3d", this.vectors[i][g].getDegree()) + ")	";
			}
			string += "\n";
		}
		
		return string;
	}
}
