package macnaught.cory;

import java.util.ArrayList;
import macnaught.cory.shapes.Triangle;

// NEXT STEP: Store all generation information and allow users to freely go through all existing generations
public class Generations {

	private ArrayList<Population> populationList = new ArrayList<Population>();
	private int generationIndex;
	private static int POPULATION_SIZE;
	
	public Generations() {
		this.setPopulationSize(300);
		this.add(new Population(POPULATION_SIZE));
		
		this.setGenerationIndex(0);
	}

	// Create the next generation
	public void generateNextGeneration() {
		Triangle[] nextGeneration = new Triangle[this.getCurrentPopulation().size()];
		ArrayList<Triangle> geneticPool = this.selectGeneticPool();
		
		for (int i = 0; i < this.getCurrentPopulation().size(); i++) {
			// Select both parents
			Triangle parent1 = geneticPool.get((int) (Math.random() * geneticPool.size()));
			Triangle parent2 = geneticPool.get((int) (Math.random() * geneticPool.size()));
			
			//Triangle triangle = new Triangle(parent1, parent2);
			Triangle triangle = new Triangle(parent1);
			triangle.setBounds(50, 50, 25, 25);
			nextGeneration[i] = triangle;
		}
		Population population = new Population(nextGeneration);
		this.add(population);
		this.incrementGenerationIndex();
	}
	
	private ArrayList<Triangle> selectGeneticPool() {
		ArrayList<Triangle> geneticPool = new ArrayList<Triangle>();
		
		// Calculates total fitness
		int totalFitness = 0;
		for (int i = 0; i < this.getCurrentPopulation().size(); i++) {
			Triangle triangle = this.getCurrentPopulation().get(i);
			totalFitness += triangle.getFitness();
		}
		
		for (int i = 0; i < this.getCurrentPopulation().size(); i++) {
			Triangle triangle = this.getCurrentPopulation().get(i);
			int fitness = triangle.getFitness();
			// Weighs the fitness of all the triangles in the generation and uses that weight to choose 1000 slots in the genetic pool for next generation
			for (int g = 0; g < (int) Math.round(fitness * 1000 / totalFitness); g++) {
				// The overall genetic pool is getting too large
				// The array eventually takes up all space in memory
				geneticPool.add(triangle);
			}
		}
		return geneticPool;
	}
	
	private void add(Population population) {
		this.populationList.add(population);
	}
	
	private void setPopulationSize(int value) {
		if (value >= 0) {
			Generations.POPULATION_SIZE = value;
		} else {
			System.out.println("Population Size Value Out of Bounds");
		}
	}

	private void setGenerationIndex(int value) {
		if (value >= 0)
			this.generationIndex = value;
	}
	
	private void incrementGenerationIndex() {
		this.setGenerationIndex(this.generationIndex + 1);
	}
	
	private void decrementGenerationIndex() {
		this.setGenerationIndex(this.generationIndex - 1);
	}
	
	public int getGenerationIndex() {
		return this.generationIndex;
	}
	
	public Population getCurrentPopulation() {
		return populationList.get(this.getGenerationIndex());
	}
	
	public Population get(int index) {
		return populationList.get(index);
	}
	
	public int numberOfGenerations() {
		return populationList.size();
	}
	
	public String toString() {
		String string = "";
		
		string = "Number of Generations: " + this.numberOfGenerations() + " -- Generation: " + this.getGenerationIndex(); 
		
		return string;
	}
}
