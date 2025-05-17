/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.SortedSeasonSet;
import de.jare.ndimcol.ref.ArraySeason;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * 
 * @author Janusch Rentenatus
 */
public class RandomTreeSetTest {

    public RandomTreeSetTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start RandomTreeSetTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End RandomTreeSetTest.");
        System.out.println("===============================================");
    }

    @Test
    public void testRandomAddition() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        Comparator<Integer> compT = Integer::compare;
        ArraySeason<Integer> season = new SortedSeasonSet<>(compT, true);
        Random random = new Random();

        for (int i = 0; i < 80000; i++) {
            int randomNumber = random.nextInt(40001); // Zufälliger Integer zwischen 0 und 20000
            boolean ok1 = treeSet.add(randomNumber);
            boolean ok2 = season.add(randomNumber);
            assertEquals(ok1, ok2, "Random number error: " + randomNumber);
        }

        // Assertions für die Größen und Grenzen
        assertEquals(treeSet.size(), season.size());
        assertEquals(treeSet.first(), season.first());
        assertEquals(treeSet.last(), season.last());
    }

    @Test
    public void testRemovalConsistency() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        Comparator<Integer> compT = Integer::compare;
        ArraySeason<Integer> season = new SortedSeasonSet<>(compT, true);
        Random random = new Random();

        for (int i = 0; i < 80000; i++) {
            int randomNumber = random.nextInt(40001);
            treeSet.add(randomNumber);
            season.add(randomNumber);
        }

        // Überprüfung der Konsistenz beim Entfernen
        for (Integer in : treeSet) {
            assertTrue(season.remove(in), "Error removing: " + in);
        }

        // Finaler Zustand
        assertEquals(season.size(), 0, "The SortedSeasonSet should be empty.");
    }

    @Test
    public void testSortedSeasonOrder() {
        Comparator<Integer> compT = Integer::compare;
        ArraySeason<Integer> season = new SortedSeasonSet<>(compT, true);
        Random random = new Random();

        for (int i = 0; i < 80000; i++) {
            int randomNumber = random.nextInt(40001);
            season.add(randomNumber);
        }

        // Überprüfung der Sortierung
        int last = -1;
        for (Integer test : season) {
            assertTrue(test > last, "Incorrect sorted: " + last + " > " + test);
            last = test;
        }
    }
}
