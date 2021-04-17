package macnaught.cory;

import java.awt.Dimension;
import java.awt.Toolkit;

public interface ISpecifics {
	
	// Window Information
	final static int FRAME_WIDTH = 1000 + 16;
	final static int FRAME_HEIGHT = 600 + 39;
	final static int PANEL_WIDTH = 1000 + 10;
	final static int PANEL_HEIGHT = 600 + 10;
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Genetic Algorithm Variables
	final static int NUM_ROWS = 10;
	final static int NUM_COLUMNS = 15;
	
	//Boundary Information
	final static int BOUNDARY_CORRECTION_X = 7;
	final static int BOUNDARY_CORRECTION_Y = 30;
	
	// Game Loop information
	final static int NUM_TICKS = 1000;
}
