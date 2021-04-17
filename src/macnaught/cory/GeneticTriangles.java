package macnaught.cory;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;

import macnaught.cory.shapes.Goal;
import macnaught.cory.shapes.Triangle;
import macnaught.cory.shapes.Wall;
import macnaught.cory.shapes.Obstacle;

public class GeneticTriangles implements ISpecifics{
	
	private Thread thread;
	
	private static JFrame frame;
	private static Display display;
	
	// Game engine variables
	final int TICKS_PER_SECOND = 60;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 5;
	
	// Genetic Algorithm Variables
	static Generations generations;
	static Goal goal;
	int tick = 0;
	
	//Lists
	private static ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>(); // Obstacles that are displaying
	
	// Border Walls
	static Wall wall[] = new Wall[4];

	public GeneticTriangles() {
		thread = new Thread() {
			
			public void run() {
				long lastLoopTime = System.nanoTime();
			    final int TARGET_FPS = 60;
			    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
			    long lastFpsTime = 0;
			    
			    while(true){
			        long now = System.nanoTime();
			        long updateLength = now - lastLoopTime;
			        lastLoopTime = now;
			        double delta = updateLength / ((double)OPTIMAL_TIME);

			        lastFpsTime += updateLength;
			        if (lastFpsTime >= 1000000000){
			            lastFpsTime = 0;
			        }

			        game_loop();

			        display_game();

			        try {
			            long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
			            Thread.sleep(gameTime);
			        } catch(Exception e){}
			    }
			}
		};
		
		thread.start();
	}
	
	public static void addComponentListener(JFrame frame) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	//resize display
            	//display.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            	
            	// resize boundaries
    			/*wall[0].setBounds(0, 0, frame.getWidth(), 1); // Top boundary
    			
    			wall[1].setBounds(frame.getWidth() - BOUNDARY_CORRECTION_X, 0, 1, frame.getHeight()); // Right boundary
    			
    			wall[2].setBounds(0, 0, 1, frame.getHeight()); // Left boundary
    			
    			wall[3].setBounds(0, frame.getHeight() - BOUNDARY_CORRECTION_Y, frame.getWidth(), 1); // Bottom boundary*/
            }
        });
    }
	
	// Function should be changed to not use set Color
	public void game_loop() {
		// Update the fitness score of the entire population
		generations.getCurrentPopulation().calculateFitness(goal, getObstacleList());
		
		for (int i = 0; i < generations.getCurrentPopulation().size(); i++) {
			// Apply force if it is not intersecting with anything
			Triangle triangle = generations.getCurrentPopulation().get(i);
			if (!triangle.intersects(goal) && !triangle.isIntersecting(getObstacleList()))
				triangle.applyForce();
			
			// All triangle colors get set to green
			triangle.setColor(Color.GREEN);
		}
		
		// best triangle gets set to blue
		Triangle best = generations.getCurrentPopulation().calculateBestTriangle();
		best.setColor(Color.BLUE);
		
		// Next generation
		if (tick > ISpecifics.NUM_TICKS) {
			tick = 0;
			
			//if (generations.numberOfGenerations() == 5)
	    	//	generations.setGenerationIndex(3);
			
			// Generate next population
	    	//population.calculateFitness(goal, display.getWallList());
			System.out.println("Best fitness: " + best.getFitness() + " -- Average fitness: " + generations.getCurrentPopulation().calculateAvgPopulationFitness());
			if (this.generate()) {
				generations.generateNextGeneration();
			} else {
				generations.getCurrentPopulation().resetPositions();
			}
	    	display.setPopulation(generations.getCurrentPopulation());
	    	System.out.println(generations.toString());
		}
		
		tick++;
	}
	
	private boolean generate() {
		return generations.getGenerationIndex() + 1 == generations.numberOfGenerations();
	}
	
	public void display_game() {
		display.repaint();
	}
	
	public static void addObstacle(Obstacle obstacle) {
		obstacleList.add(obstacle);
	}
	
	public static ArrayList<Obstacle> getObstacleList() {
		return obstacleList;
	}
	
	public static void main(String[] args) {
		generations = new Generations();
		goal = new Goal();
		goal.setBounds(900, 500, 25, 25);
		
		// Genetic Algorithm Code
		EventQueue.invokeLater(() ->
		{
			//Create the frame
			frame = new JFrame("Genetic Rockets");
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			frame.setLocation((int) screenSize.getWidth() / 2 - FRAME_WIDTH / 2, (int) screenSize.getHeight() / 2 - FRAME_HEIGHT / 2);
			frame.setLayout(null);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
	
			
			// Display Simulation
			display = new Display(generations.getCurrentPopulation(), goal, obstacleList);
			display.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			display.setBackground(Color.BLACK);
			display.setFocusable(true);
		    display.requestFocus();
			//display.displayVectors = true; No Longer Used
			display.addDisplayEventListener(new DisplayEventListener()
			{
				@Override
				public void displayEventOccurred(DisplayEvent evt)
				{
					obstacleList.add(evt.getObstacle());
				}
			});
			
			// Add boundaries
			wall[0] = new Wall();
			wall[0].setBounds(0, 0, frame.getWidth(), 1);
			addObstacle(wall[0]); // Top boundary
			
			wall[1] = new Wall();
			wall[1].setBounds(frame.getWidth() - BOUNDARY_CORRECTION_X, 0, 1, frame.getHeight());
			addObstacle(wall[1]); // Right boundary
			
			wall[2] = new Wall();
			wall[2].setBounds(0, 0, 1, frame.getHeight());
			addObstacle(wall[2]); // Left boundary
			
			wall[3] = new Wall();
			wall[3].setBounds(0, frame.getHeight() - BOUNDARY_CORRECTION_Y, frame.getWidth(), 1);
			addObstacle(wall[3]); // Bottom boundary
			
			addComponentListener(frame);
			frame.add(display);
			
			// Create Simulation
			new GeneticTriangles();
		});
	}
}
