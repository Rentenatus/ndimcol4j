/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArraySeason;
import de.jare.ndimcol.ref.ArraySeasonHashable;
import de.jare.ndimcol.ref.ArrayTape;
import de.jare.ndimcol.ref.ArrayTapeHashable;
import de.jare.ndimcol.ref.IteratorWalker;
import de.jare.ndimcol.ref.ListSeason;
import java.util.ArrayList;
import java.util.Collection;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author jRent
 */
public class IteratorWalkerNGTest {

    public IteratorWalkerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start IteratorWalkerNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End IteratorWalkerNGTest.");
        System.out.println("===============================================");
    }

    private Collection<ArrayMovie<String>> dataCollection() {
        final Collection<ArrayMovie<String>> ret;
        ret = new ArrayList<>(5);
        ret.add(new ArrayTape<>());
        ret.add(new ArraySeason<>());
        ret.add(new ArrayTapeHashable<>());
        ret.add(new ArraySeasonHashable<>());
        ret.add(new ListSeason<>());
        for (ArrayMovie<String> movie : ret) {
            movie.add("A");
            movie.add(" B");
            movie.add("  C");
            movie.add("   D");
            movie.add("    E");
            movie.add("     F");
            movie.add("      G");
        }
        return ret;
    }

    private void printMovie(ArrayMovie<String> movie) {
        final int index[] = new int[1];
        movie.forEach(str -> {
            System.out.println((index[0]++) + " >    : " + str);
        });
    }

    /**
     * Test of getRelatedMovie method, of class IteratorWalker.
     */
    @Test
    public void testGetRelatedMovie() {
        System.out.println("--------------  ");

    }

    /**
     * Test of removeForward method, of class IteratorWalker.
     */
    @Test
    public void testRemoveForward() {
        final Collection<ArrayMovie<String>> data = dataCollection();

        for (ArrayMovie<String> movie : data) {
            System.out.println("--------------  " + movie.getClass().getSimpleName() + ".removeForward");
            printMovie(movie);
            IteratorWalker<String> walker = movie.softWalker();
            while (walker.hasNext()) {
                String next = walker.next();
                if (next.endsWith("A")) {
                    System.out.println("found:   " + next);
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeForward());
                }
                if (next.endsWith("G")) {
                    System.out.println("found:   " + next);
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeForward());
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeForward());
                }
            }
            System.out.println("removed(1)" + movie.removeAt(1));
            printMovie(movie);
            assertTrue(movie.size() == 3);
            assertTrue(movie.get(0).endsWith("B"));
            assertTrue(movie.get(1).endsWith("D"));
            assertTrue(movie.get(2).endsWith("E"));
        }
    }

    /**
     * Test of removeBackward method, of class IteratorWalker.
     */
    @Test
    public void testRemoveBackward() {
        final Collection<ArrayMovie<String>> data = dataCollection();

        for (ArrayMovie<String> movie : data) {
            System.out.println("--------------  " + movie.getClass().getSimpleName() + ".removeBackward");
            printMovie(movie);
            IteratorWalker<String> walker = movie.softWalkerBackwards();
            while (walker.hasPrevious()) {
                String prev = walker.previous();
                System.out.println("p >    : " + prev);
                if (prev.endsWith("A")) {
                    System.out.println("found:   " + prev);
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeBackward());
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeBackward());
                }
                if (prev.endsWith("G")) {
                    System.out.println("found:   " + prev);
                    System.out.println("at " + walker.getCurrentIndex());
                    System.out.println("removed: " + walker.removeBackward());
                }
            }
            printMovie(movie);
            System.out.println("removed(1)" + movie.removeAt(1));
            printMovie(movie);
            assertTrue(movie.size() == 3);

            assertTrue(movie.get(0).endsWith("C"));
            assertTrue(movie.get(1).endsWith("E"));
            assertTrue(movie.get(2).endsWith("F"));
        }
    }

    /**
     * Test of remove method, of class IteratorWalker.
     */
    @Test
    public void testRemove() {
        System.out.println("--------------  ");

    }

    /**
     * Test of add method, of class IteratorWalker.
     */
    @Test
    public void testAdd_GenericType() {
        System.out.println("--------------  ");

    }

    /**
     * Test of add method, of class IteratorWalker.
     */
    @Test
    public void testAdd_Collection() {
        System.out.println("--------------  ");

    }

    /**
     * Test of set method, of class IteratorWalker.
     */
    @Test
    public void testSet() {
        System.out.println("--------------  ");

    }

    /**
     * Test of goFirst method, of class IteratorWalker.
     */
    @Test
    public void testGoFirst() {
        System.out.println("--------------  ");

    }

    /**
     * Test of goLast method, of class IteratorWalker.
     */
    @Test
    public void testGoLast() {
        System.out.println("--------------  ");

    }

    /**
     * Test of gotoIndex method, of class IteratorWalker.
     */
    @Test
    public void testGotoIndex() {
        System.out.println("--------------  ");

    }

    /**
     * Test of goLeafIndex method, of class IteratorWalker.
     */
    @Test
    public void testGoLeafIndex() {
        System.out.println("--------------  ");

    }

    /**
     * Test of getCurrentIndex method, of class IteratorWalker.
     */
    @Test
    public void testGetCurrentIndex() {
        System.out.println("--------------  ");

    }

}
