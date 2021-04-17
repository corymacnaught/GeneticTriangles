package macnaught.cory;
import java.util.ArrayList;

import macnaught.cory.shapes.Goal;
import macnaught.cory.shapes.Triangle;
import macnaught.cory.shapes.Obstacle;

public class Population {

	private Triangle[] population; // Array containing all the Triangle of the given population
	
	// Create a new randomly generated population
	public Population(int size) {
		this.population = new Triangle[size];
		
		// Generate random population
		for (int i = 0; i < this.population.length; i++) {
			Triangle triangle = new Triangle();
			triangle.setBounds(50, 50, 25, 25);
			triangle.setMutationChance(5);
			this.population[i] = triangle;
		}
	}
	
	// Copy a population
	public Population(Triangle[] population) {
		this.population = population;
	}
	
	// Calculates the fitness of the entire population
	public void calculateFitness(Goal goal, ArrayList<Obstacle> obstacleList) {
		for (Triangle triangle : this.getPopulation()) {
			triangle.calculateFitness(goal, obstacleList);
		}
	}
	
	// Finds the Triangle with the highest fitness
	public Triangle calculateBestTriangle() {
		Triangle best = this.get(0);
		for (int i = 0; i < this.getPopulation().length; i++) {
			if (this.get(i).getFitness() > best.getFitness())
				best = this.get(i);
		}
		return best;
	}
	
	// Calculates the average fitness in the population
	public double calculateAvgPopulationFitness() {
		int fitness = 0;
		int i = 0;
		for (; i < this.getPopulation().length; i++) {
			fitness += this.get(i).getFitness();
		}
		return fitness / this.getPopulation().length;
	}
	
	public void resetPositions() {
		for (Triangle triangle : this.getPopulation()) {
			triangle.resetPosition();
		}
	}
	
	// Returns the all Triangle in the population
	private Triangle[] getPopulation() {
		return this.population;
	}
	
	public int size() {
		return this.getPopulation().length;
	}
	
	// returns the selected Triangle in the population
	public Triangle get(int i) {
		return this.population[i];
	}
	
	// Returns all of the Triangle in the population in String form
	public String toString() {
		String string = "";
		for (Triangle triangle : this.getPopulation()) {
			string += triangle.toString();
			string += "\n-------\n";
		}
		return string;
	}
}
