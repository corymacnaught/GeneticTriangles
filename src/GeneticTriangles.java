
public class GeneticTriangles {

	public GeneticTriangles() {
	}

	public static void main(String[] args) {
		// Genetic Algorithm Code
		//GeneticAlgorithm algo = new GeneticAlgorithm();
		
		// Tests
		
		// Gene test
		Gene gene = new Gene();
		System.out.println(gene.getDegree());
		System.out.println(gene.getForce());
		
		// Triangle test
		// Create random Triangle
		Triangle tri1 = new Triangle(2, 2);
		//System.out.println(tri1.toString());
		
		//Create Triangle from 1 parent
		//No mutation
		Triangle tri2 = new Triangle(tri1, 0);
		//System.out.println("No Mutation = Parent:\n" + tri1.toString() + " -- Child:\n" + tri2.toString());
		
		//High mutation
		Triangle tri3 = new Triangle(tri1, 75);
		System.out.println("High Mutation = Parent:\n" + tri1.toString() + " -- Child:\n" + tri3.toString());
		
		//Create DNA from 2 parents
		//DNA dna4 = new DNA(7);
		//DNA dna5 = new DNA(7);
		//No mutation
		//DNA dna6 = new DNA(dna4, dna5, 0);
		//System.out.println("No Mutation = Parent1: " + dna4.toString() + " -- Parent2: " + dna5.toString() + " -- Child: " + dna6.toString());
		
		//High mutation
		//DNA dna7 = new DNA(dna4, dna5, 75);
		//System.out.println("High Mutation = Parent1: " + dna4.toString() + " -- Parent2: " + dna5.toString() + " -- Child: " + dna7.toString());
		
		
		// Population test
		// Create random population
		//Population pop1 = new Population(100);
		//System.out.println(pop1.toString());
		
		// Fitness test
		//pop1.calculateFitness();
		//System.out.println("DNA: " + pop1.getDNA(0).toString() + " -- Fitness: " + pop1.getDNA(0).getFitness());
		
		// Best DNA test
		//System.out.println(pop1.getBestDNA());
	}
}
