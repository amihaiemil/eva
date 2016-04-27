package com.amihaiemil.eva;

import com.amihaiemil.eva.util.BinaryArraySolution;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for {@link Population}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class PopulationTestCase {
    /**
     * Population under test.
     */
    private Population popUt;

    @Before
    public void setup() {
        SolutionsGenerator generator = Mockito.mock(SolutionsGenerator.class);
        FitnessEvaluator evaluator = Mockito.mock(FitnessEvaluator.class);

        Solution solution = Mockito.spy(new BinaryArraySolution());
        Fitness fitness = Mockito.mock(Fitness.class);
        Selection selection = Mockito.mock(Selection.class);
        BestSelection bestSelection = Mockito.mock(BestSelection.class);

        Mockito.when(generator.generateRandomSolution()).thenReturn(solution);
        Mockito.when(evaluator.calculateFitnessForSolution(solution)).thenReturn(fitness);
        Mockito.when(selection.select(Mockito.anyCollectionOf(Solution.class))).thenReturn(solution);
        Mockito.when(bestSelection.selectBest(Mockito.anyCollectionOf(Solution.class))).thenReturn(solution);

        popUt = new Population(evaluator, generator, selection, bestSelection, 5);
    }

    /**
     * A Population of solutions can tell its size.
     */
    @Test
    public void getsSize() {
        assertTrue(popUt.getSize() == 5);
    }

    /**
     * A new individual can be added to the population.
     */
    @Test
    public void addIndividual() {
        int initialSize = popUt.getSize();
        popUt.addIndividual(Mockito.mock(Solution.class));
        assertTrue(popUt.getSize() == initialSize + 1);
    }

    /**
     * An individual can be selected from the population.
     */
    @Test
    public void selectsIndividual() {
        assertTrue(popUt.selectIndividual() != null);
    }

    /**
     * The best individual can be selected from the population.
     */
    @Test
    public void selectsBestIndividual() {
        assertTrue(popUt.bestIndividual() != null);
    }

    /**
     * A population can return its list of individuals.
     */
    @Test
    public void getsListOfIndividualt() {
        assertTrue(popUt.getIndividuals() != null);
        assertTrue(popUt.getIndividuals().size() == 5);
    }

    /**
     * Individuals of a population can be evaluated.
     */
    @Test
    public void evaluatesIndividuals() {
        List<Solution> individuals = popUt.getIndividuals();
        for(int i=0;i<individuals.size();i++) {
            assertTrue(individuals.get(i).getFitness() == null);
        }
        popUt.evaluateIndividuals();
        for(int i=0;i<individuals.size();i++) {
            assertTrue(individuals.get(i).getFitness() != null);
        }
    }
}
