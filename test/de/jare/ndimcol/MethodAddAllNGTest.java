/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.primfloat.ArraySeasonFloat;
import de.jare.ndimcol.primint.ArraySeasonInt;
import de.jare.ndimcol.primint.SortedSeasonSetInt;
import de.jare.ndimcol.primlong.ArraySeasonLong;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArrayTape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class MethodAddAllNGTest {

    public MethodAddAllNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start MethodAddAllNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End MethodAddAllNGTest.");
        System.out.println("===============================================");
    }

    @Test
    public void testRefAddAllCollection() {
        ArrayTape<String> collection = new ArrayTape<>();
        List<String> input = Arrays.asList("Apfel", "Banane", "Kirsche");

        boolean changed = collection.addAll(input);

        assertTrue(changed, "Die Collection sollte sich geändert haben.");
        assertEquals(collection.size(), 3);
        assertTrue(collection.contains("Banane"));
    }

    @Test
    public void testRefAddAllArray() {
        ArrayTape<Integer> collection = new ArrayTape<>();
        Integer[] input = {1, 2, 3, 4};

        boolean changed = collection.addAll(input);

        assertTrue(changed, "Die Collection sollte sich geändert haben.");
        assertEquals(collection.size(), 4);
        assertTrue(collection.contains(3));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testRefAddAllArrayWithNull() {
        ArrayTape<String> collection = new ArrayTape<>();
        String[] input = null;

        collection.addAll(input);
        fail("Sollte eine NullPointerException werfen");
    }

    @Test
    public void testLongAddAllCollection() {
        ArraySeasonLong collection = new ArraySeasonLong();
        List<Long> input1 = new ArrayList<>();
        for (int i = 0; i < 44000; i++) {
            input1.add(i * 200L);
        }
        List<Long> input2 = Arrays.asList(123L, 456L, 789L);

        boolean changed = collection.addAll(input1);
        assertTrue(changed, "Die Collection sollte sich geändert haben.");
        changed = collection.addAll(input2);
        assertTrue(changed, "Die Collection sollte sich geändert haben.");
        assertEquals(collection.size(), 44003);
        assertTrue(collection.contains(456L));
    }

    @Test
    public void testLongAddAllArray() {
        ArraySeasonLong collection = new ArraySeasonLong();
        long[] input = {1, 2, 3, 4};

        boolean changed = collection.addAll(input);

        assertTrue(changed, "Die Collection sollte sich geändert haben.");
        assertEquals(collection.size(), 4);
        assertTrue(collection.contains(3));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testLongAddAllArrayWithNull() {
        ArraySeasonLong collection = new ArraySeasonLong();
        long[] input = null;

        collection.addAll(input);
        fail("Sollte eine NullPointerException werfen");
    }

}
