/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class ArraySeasonNGTest {

    public ArraySeasonNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start ArraySeasonNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End ArraySeasonNGTest.");
        System.out.println("===============================================");
    }

    protected ArraySeason<Integer> dataMiniArray() {
        ArraySeason<Integer> as = new ArraySeason<>();
        as.add(4);
        as.add(5);
        as.add(6);
        as.add(7);
        as.add(8);
        return as;
    }

    protected ArraySeason<Integer> dataBigArray(int numberElems) {
        ArraySeason<Integer> as = new ArraySeason<>();
        for (int i = 0; i < numberElems; i++) {
            as.add((Integer) i);
        }
        return as;
    }

    @Test
    public void testRmoveAt() {
        ArraySeason<Integer> data = dataMiniArray();
        data.removeAt(2);
        System.out.println("-------------- testRmoveAt");
        data.debbug(System.out, " . ");
        System.out.println("--------------");
        data.removeAt(2);
        System.out.println("--------------");
        data.debbug(System.out, " . ");
        System.out.println("--------------");
        assertEquals("" + data.size(), "3");
        assertEquals("" + data.get(0), "4");
        assertEquals("" + data.get(1), "5");
        assertEquals("" + data.get(2), "8");
    }

    @Test
    public void testFirst() {
        ArraySeason<Integer> data = dataMiniArray();
        assertEquals(data.first(), Integer.valueOf(4));
    }

    @Test
    public void testLast() {
        ArraySeason<Integer> data = dataMiniArray();
        assertEquals(data.last(), Integer.valueOf(8));
    }

    @Test
    public void testAddAtIndex() {
        ArraySeason<Integer> data = dataMiniArray();
        data.add(2, 99);
        assertEquals(data.get(2), Integer.valueOf(99));
        assertEquals(data.size(), 6);
    }

    @Test
    public void testGet() {
        ArraySeason<Integer> data = dataMiniArray();
        assertEquals(data.get(1), Integer.valueOf(5));
    }

    @Test
    public void testRemoveAt() {
        ArraySeason<Integer> data = dataMiniArray();
        data.removeAt(2);
        assertEquals(data.size(), 4);
        assertEquals(data.get(2), Integer.valueOf(7));
    }

    @Test
    public void testIndexOf() {
        ArraySeason<Integer> data = dataMiniArray();
        assertEquals(data.indexOf(6), 2);
        assertEquals(data.indexOf(99), -1);
    }

    @Test
    public void testCopyToArray() {
        ArraySeason<Integer> data = dataMiniArray();
        Object[] array = new Object[10];
        data.copyToArray(array, 2);
        assertEquals(array[2], Integer.valueOf(4));
        assertEquals(array[6], Integer.valueOf(8));
        assertNull(array[0]);
    }

    @Test
    public void testDebbug() {
        ArraySeason<Integer> data = dataMiniArray();
        int offset = data.debbug(System.out, "TestPrefix", 0);
        assertTrue(offset > 0); // Abhängig von der Rückgabelogik
    }

    @Test
    public void testBigArraySize() {
        ArraySeason<Integer> data = dataBigArray(2800);
        assertEquals(data.size(), 2800);
    }

    @Test
    public void testBigArrayFirstAndLast() {
        ArraySeason<Integer> data = dataBigArray(2800);
        assertEquals(data.first(), Integer.valueOf(0));
        assertEquals(data.last(), Integer.valueOf(2799));
    }

    @Test
    public void testBigArrayGetMiddle() {
        ArraySeason<Integer> data = dataBigArray(2800);
        assertEquals(data.get(1400), Integer.valueOf(1400));
    }

    @Test
    public void testBigArrayRemoveAt() {
        ArraySeason<Integer> data = dataBigArray(2800);
        data.removeAt(1400);
        assertEquals(data.size(), 2799);
        assertEquals(data.get(1400), Integer.valueOf(1401));
    }

    @Test
    public void testBigArrayIndexOf() {
        ArraySeason<Integer> data = dataBigArray(2800);
        assertEquals(data.indexOf(1400), 1400);
        assertEquals(data.indexOf(9999), -1);
    }

    @Test
    public void testBigArraySplitInHalf() {
        ArraySeason<Integer> data = dataBigArray(2800);
        ArrayMovie<Integer> split = data.splitInHalf();
        assertEquals(split.get(0), Integer.valueOf(data.size()));
        assertEquals(data.get(0), Integer.valueOf(0));
    }

    @Test
    public void testBigArrayCopyToArray() {
        ArraySeason<Integer> data = dataBigArray(2800);
        Object[] array = new Object[3000];
        data.copyToArray(array, 100);
        assertEquals(array[100], Integer.valueOf(0));
        assertEquals(array[1399], Integer.valueOf(1299));
        assertEquals(array[1400], Integer.valueOf(1300));
        assertNull(array[0]);
        assertNull(array[2999]);
    }

}
