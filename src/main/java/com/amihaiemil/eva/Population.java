package com.amihaiemil.eva;

import com.amihaiemil.eva.abstractions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
final class Population {
    private List<Solution> individuals;
    private Random chance = new Random();

    Population(SolutionsGenerator generator, int size) {
        this.individuals = new ArrayList<Solution>();
        for(int i=0;i<size;i++) {
            individuals.add(generator.generateRandomSolution());
        }
    }

    /**
     * Get the list of individuals from this population.
     * @return A list of individuals.
     */
    List<Solution> getIndividuals() {
        return this.individuals;
    }

    /**
     * An individual is selected from the population based on chance.
     * @return The selected individual.
     */
    Solution selectIndividual() {
        Solution candidate1 = individuals.get(chance.nextInt(individuals.size()));
        Solution candidate2 = individuals.get(chance.nextInt(individuals.size()));
        if(candidate1.getFitness().compareTo(candidate2.getFitness()) == 1) {
            return candidate1;
        } else {
            return candidate2;
        }
    }

    /**
     * Gets the best indivitual in this population.
     * @return The best individual based on fitness.
     */
    Solution bestIndividual() {
        Solution best = individuals.get(0);
        for(int i=1;i<individuals.size();i++) {
            if(individuals.get(i).getFitness().isOk() &&
                    individuals.get(i).getFitness().compareTo(best.getFitness()) == 1) {
                best = individuals.get(i);
            }
        }
        return best;
    }
}
