/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.IteratorWalker;
import de.jare.ndimcol.ref.SortedSeasonSet;
import de.jare.ndimcol.utils.BiPredicateAmbiguityIdentity;
import de.jare.ndimcol.utils.BiPredicateHashGr;
import java.util.function.BiPredicate;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class SortedSeasonSetNGTest {
    
    public class CoInteger {
        
        Integer i;
        
        public CoInteger(int i) {
            this.i = i;
        }
        
        @Override
        public boolean equals(Object ob) {
            return i.equals(((CoInteger) ob).i);
        }
        
        @Override
        public int hashCode() {
            return super.hashCode() + 31 * i;
        }
        
        @Override
        public String toString() {
            return i.toString();
        }
        
        public int intValue() {
            return i.intValue();
        }
        
    }
    
    public class CoIntegerWrapper {
        
        CoInteger coi;
        
        public CoIntegerWrapper(CoInteger coi) {
            this.coi = coi;
        }
        
        public CoInteger getCoi() {
            return coi;
        }
    }
    
    class BiPredicateCoIntegerGr implements BiPredicate<CoInteger, CoInteger> {

        /**
         * Evaluates this predicate o2.intValue() greater as o1.intValue().
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
         *
         */
        @Override
        public boolean test(CoInteger o1, CoInteger o2) {
            return o2.intValue() > o1.intValue();
        }
        
    }
    
    class BiPredicateCoIntegerEv implements BiPredicate<CoInteger, CoInteger> {

        /**
         * Evaluates this predicate o2 == o1.
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code true} if the input arguments are the same
         *
         */
        @Override
        public boolean test(CoInteger o1, CoInteger o2) {
            return o2 == o1;
        }
        
    }
    
    class BiPredicateCoIntegerNever implements BiPredicate<CoInteger, CoInteger> {

        /**
         * Never match.
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code false}
         *
         */
        @Override
        public boolean test(CoInteger o1, CoInteger o2) {
            return false;
        }
        
    }
    
    public SortedSeasonSetNGTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start SortedSeasonSetNGTest.");
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End SortedSeasonSetNGTest.");
        System.out.println("===============================================");
    }
    
    @Test
    public void testSomeMethod() {
        SortedSeasonSet<CoInteger> setDef = new SortedSeasonSet<>(new BiPredicateCoIntegerGr());
        SortedSeasonSet<CoInteger> setEven = new SortedSeasonSet<>(new BiPredicateCoIntegerGr(), new BiPredicateCoIntegerEv());
        SortedSeasonSet<CoInteger> setAmbi = new SortedSeasonSet<>(new BiPredicateCoIntegerGr(), new BiPredicateCoIntegerNever());
        final BiPredicateAmbiguityIdentity<CoInteger> ambiguity1 = new BiPredicateAmbiguityIdentity<>();
        final BiPredicateHashGr<CoInteger> predicate1 = new BiPredicateHashGr<>();
        SortedSeasonSet<CoInteger> setHash = new SortedSeasonSet<>(predicate1, ambiguity1);
        CoInteger[] src = {
            new CoInteger(2),
            new CoInteger(7),
            new CoInteger(0),
            new CoInteger(2),
            new CoInteger(3),
            new CoInteger(7),
            null
        };
        src[6] = src[4];
        System.out.println("<-- source array");
        try {
            for (int i = 0; i < src.length; i++) {
                setDef.add(src[i]);
                setEven.add(src[i]);
                setAmbi.add(src[i]);
                setHash.add(src[i]);
                System.out.println(src[i] + "  -1->  " + src[i].hashCode());
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            fail("Adding failed.", ex);
        }
        for (int i = 0; i < src.length; i++) {
            setDef.add(src[i]);
            setEven.add(src[i]);
            setAmbi.add(src[i]);
            setHash.add(src[i]);
            System.out.println(src[i] + "  -2->  " + src[i].hashCode());
        }
        
        assertEquals(setDef.get(0).hashCode(), src[2].hashCode());
        assertEquals(setDef.get(1).hashCode(), src[0].hashCode());
        assertEquals(setDef.get(2).hashCode(), src[4].hashCode());
        assertEquals(setDef.get(3).hashCode(), src[1].hashCode());
        
        setDef.remove(src[1]);
        setEven.remove(src[1]);
        setAmbi.remove(src[1]);
        setHash.remove(src[1]);
        System.out.println("<-- remove src[1]");
        System.out.println(src[1] + "  -r->  " + src[1].hashCode());
        
        System.out.println("--- simple set");
        IteratorWalker<CoInteger> walker = setDef.softWalker();
        while (walker.hasNext()) {
            CoInteger ob = walker.next();
            System.out.println(ob + "  -->  " + ob.hashCode());
        }
        System.out.println("--- identity == ");
        walker = setEven.softWalker();
        while (walker.hasNext()) {
            CoInteger ob = walker.next();
            System.out.println(ob + "  -->  " + ob.hashCode());
        }
        System.out.println("--- multiple ambiguity");
        walker = setAmbi.softWalker();
        while (walker.hasNext()) {
            CoInteger ob = walker.next();
            System.out.println(ob + "  -->  " + ob.hashCode());
        }
        System.out.println("--- hash set");
        walker = setHash.softWalker();
        while (walker.hasNext()) {
            CoInteger ob = walker.next();
            System.out.println(ob + "  -->  " + ob.hashCode());
        }
        
        assertEquals(setDef.size(), 3);
        assertEquals(setEven.size(), 5);
        assertEquals(setAmbi.size(), 14);
        assertEquals(setHash.size(), 5);
    }
    
}
