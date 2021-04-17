
public class Triangle {

	private Gene[][] vectors; // Genome
	private int fitness; // Score
	private int survivalChance; // % Chance to survive until next round
	
	// Dimensions
	private int rows;
	private int columns;
	
	// Constructors
	// Create random DNA
	/* Fills dna with random objects of type Gene */
	public Triangle(int rows, int columns) {
		// Set amount of genes
		this.vectors = new Gene[rows][columns];
		this.rows = rows;
		this.columns = columns;
		
		this.setFitness(0);
		this.setSurvivalChance(0);
		
		// Create Genes
		for (int i = 0; i < rows; i++) {
			for (int g = 0; g < columns; g++)
			this.vectors[i][g] = new Gene();
		}
	}
	
	// Create DNA from both parents
	/* Fills dna with objects of type gene selected randomly from one of the two parents */
	/* Each gene has a chance to be changed to a random new character */
	/*public Triangle(Triangle parent1, Triangle parent2, int mutationRate) {
		this.setFitness(0);
		this.setSurvivalChance(0);
		
		// Copy parent DNA
		int length = parent1.dna.length < parent2.dna.length ? parent1.dna.length : parent2.dna.length;
		
		this.vectors = new Gene[length];
		for (int i = 0; i < length; i++) {
			if ((int) (Math.random() * 2) == 0)
				this.dna[i] = new Gene(parent1.getGene(i));
			else
				this.dna[i] = new Gene(parent2.getGene(i));
		}
		
		// mutate DNA
		this.mutate(mutationRate);
	}*/
	
	// Create DNA from one parent
	/* Fills dna with objects of type Gene all taken from parent */
	/* Each gene has a chance to be changed to a random new character */
	public Triangle(Triangle parent, int mutationRate) {
		this.setFitness(0);
		
		// Copy parent DNA
		this.rows = parent.rows;
		this.columns = parent.columns;
		
		this.vectors = new Gene[this.rows][parent.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int g = 0; g < this.columns; g++) {
				this.vectors[i][g] = new Gene(parent.getVector(i, g));
			}
		}
		
		// mutate DNA
		this.mutate(mutationRate);
	}
	
	// mutate DNA
	/* Randomly changes every object of type Gene in dna to a random new character */
	public void mutate(int mutationRate) {
		for (int i = 0; i < this.rows; i++) {
			for (int g = 0; g < this.columns; g++) {
				if ((int) (Math.random() * 100) < mutationRate) {
					this.vectors[i][g].mutate();
				}
			}
		}
	}
	
	// Setters/Getters
	// Set fitness value
	/* Setter for fitness */
	public void setFitness(int value) {
		if (value >= 0)
			this.fitness = value;
	}
	
	// Set this
	/*  */
	public void setSurvivalChance(int value) {
		if (value > 100)
			value = 100;
		else if (value < 0)
			value = 0;
		
		this.survivalChance = value;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public Gene getVector(int i, int g) {
		return this.vectors[i][g];
	}
	
	public int getSurvivalChance() {
		return this.survivalChance;
	}
	
	public String toString() {
		String string = "";

		for (int i = 0; i < this.rows; i++) {
			for (int g = 0; g < this.columns; g++) {
				string += "(f: " + String.format("%2d", this.vectors[i][g].getForce()) + " -- d: " + String.format("%3d", this.vectors[i][g].getDegree()) + ")	";
			}
			string += "\n";
		}
		
		return string;
	}
}
