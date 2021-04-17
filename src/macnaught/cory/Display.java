package macnaught.cory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import macnaught.cory.shapes.Goal;
import macnaught.cory.shapes.Obstacle;
import macnaught.cory.shapes.Wall;
import macnaught.cory.shapes.Checkpoint;

public class Display extends JPanel implements ISpecifics, MouseListener, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Population population; // Population that is displaying
	private Goal goal; // Goal that is Displaying
	private Wall wall; // Wall to be created
	private ArrayList<Obstacle> obstacleList; // List of obstacles to display
	
	//public boolean displayVectors; // For debugging (not managed here, managed in GeneticTriangles)
	
	private int lastButtonPressed = 0;
	private int displayedTriangle = 0;
	
	// Constructors
	public Display(Population population, Goal goal, ArrayList<Obstacle> obstacleList) {
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.population = population;
		this.goal = goal;	
		this.obstacleList = obstacleList;
		this.wall = new Wall();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		if (displayedTriangle == 0) {
			
			// Display Population
			for (int i = 0; i < population.size(); i++) {
				population.get(i).draw(g2d);
			}
		}else {
			// Display Vector
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int h = 0; h < NUM_COLUMNS; h++) {
					population.get(displayedTriangle - 1).getVector(i, h).draw(g2d);
				}
			}
			
			// Display Population
			population.get(displayedTriangle - 1).draw(g2d);
		}
		
		// Display obstacles
		for (Obstacle obstacle : this.obstacleList) {
			obstacle.draw(g2d);
		}
		
		// When the new wall obstacle has not yet been placed, the supposed area it will cover will be displayed
		this.wall.draw(g2d);
		
		// Display Goal
		this.goal.draw(g2d);
	}
	
	
	private void addObstacle(Obstacle obstacle){
		fireDisplayEvent(new DisplayEvent(obstacle));
	}
	
	// Setters/Getters
	// Set Population for next generation
	public void setPopulation(Population population) {
		this.population = population;
	}
		
	//Event Listeners
	protected EventListenerList listenerList = new EventListenerList();
	
	public void addDisplayEventListener(DisplayEventListener listener)
	{
		listenerList.add(DisplayEventListener.class, listener);
	}
	
	public void removeDisplayEventListener(DisplayEventListener listener)
	{
		listenerList.remove(DisplayEventListener.class, listener);
	}

	public void fireDisplayEvent(DisplayEvent evt)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2)
		{
			if (listeners[i] == DisplayEventListener.class)
			{
				((DisplayEventListener)listeners[i + 1]).displayEventOccurred(evt);
			}
		}
	}
	
	
	// Mouse listener buttons
	// Later store wall list in main class and make this activate a custom event listener that updates the wall list
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.lastButtonPressed = e.getButton();
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			wall = new Wall();
			wall.setX(e.getX());
			wall.setY(e.getY());
			
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Checkpoint checkpoint = new Checkpoint();
			checkpoint.setBounds(e.getX(), e.getY(), 25, 25);
			this.addObstacle(checkpoint);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			this.addObstacle(wall);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.lastButtonPressed == 1) {
			wall.setWidth(e.getX() - wall.getX());
			wall.setHeight(e.getY() - wall.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
}
