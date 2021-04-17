import java.util.Random;

/* The class that contains the information for an individual gene */
public class Gene {

	private int degree; // The encoding of the direction
	private int force; // The encoding of the direction
	
	private final int MAX_FORCE = 10;
	private final int MAX_DEGREE = 360;
	
	// Contructors
	// Generate random gene
	/* Default constructor */
	public Gene() {
		this.mutate();
	}
	
	public Gene(Gene vector) {
		this(vector.getDegree(), vector.getForce());
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
		this.setDegree(rnd.nextInt(this.MAX_DEGREE + 1));
		this.setForce(rnd.nextInt(this.MAX_FORCE + 1));
	}
	
	// Setters/Getters
	
	private void setDegree(int degree) {
		if (degree >= 0 && degree <= this.MAX_DEGREE) {
			this.degree = degree;
		}
	}
	
	private void setForce(int force) {
		if (force >= 0 && force <= this.MAX_FORCE) {
			this.force = force;
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