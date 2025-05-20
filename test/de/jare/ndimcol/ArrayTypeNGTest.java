/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.ArrayTape;
import de.jare.ndimcol.ref.ArrayMovie;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class ArrayTypeNGTest {

    public ArrayTypeNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start ArrayTypeNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End ArrayTypeNGTest.");
        System.out.println("===============================================");
    }

    protected ArrayTape<Integer> dataMiniArray() {
        ArrayTape<Integer> at = new ArrayTape<>();
        at.add(4);
        at.add(5);
        at.add(6);
        at.add(7);
        at.add(8);
        return at;
    }

    protected ArrayTape<Integer> dataBigArray(int numberElems) {
        ArrayTape<Integer> at = new ArrayTape<>();
        for (int i = 0; i < numberElems; i++) {
            at.add((Integer) i);
        }
        return at;
    }

    @Test
    public void testRmoveAt() {
        ArrayTape<Integer> data = dataMiniArray();
        data.removeAt(2);
        System.out.println("-------------- testRmoveAt");
        data.debug(System.out, " . ");
        System.out.println("--------------");
        data.removeTrim(2);
        System.out.println("--------------");
        data.debug(System.out, " . ");
        System.out.println("--------------");
        assertEquals(Integer.valueOf(data.size()), Integer.valueOf(3));
        assertEquals(data.get(0), Integer.valueOf(4));
        assertEquals(data.get(1), Integer.valueOf(5));
        assertEquals(data.get(2), Integer.valueOf(8));
    }

    @Test
    public void testFirst() {
        ArrayTape<Integer> data = dataMiniArray();
        assertEquals(data.first(), Integer.valueOf(4));
    }

    @Test
    public void testLast() {
        ArrayTape<Integer> data = dataMiniArray();
        assertEquals(data.last(), Integer.valueOf(8));
    }

    @Test
    public void testAddAtIndex() {
        ArrayTape<Integer> data = dataMiniArray();
        data.addAt(2, 99);
        assertEquals(data.get(2), Integer.valueOf(99));
        assertEquals(data.size(), 6);
    }

    @Test
    public void testGet() {
        ArrayTape<Integer> data = dataMiniArray();
        assertEquals(data.get(1), Integer.valueOf(5));
    }

    @Test
    public void testRemoveAt() {
        ArrayTape<Integer> data = dataMiniArray();
        data.removeAt(2);
        assertEquals(data.size(), 4);
        assertEquals(data.get(2), Integer.valueOf(7));
    }

    @Test
    public void testSplitInHalf() {
        ArrayTape<Integer> data = dataBigArray(10);
        ArrayMovie<Integer> split = data.splitInHalf();
        assertEquals(split.size(), 5);
        assertEquals(data.size(), 5);
    }

    @Test
    public void testIndexOf() {
        ArrayTape<Integer> data = dataMiniArray();
        assertEquals(data.indexOf(6), 2);
        assertEquals(data.indexOf(99), -1);
    }

    @Test
    public void testCopyToArray() {
        ArrayTape<Integer> data = dataMiniArray();
        Object[] array = new Object[10];
        data.copyToArray(array, 2);
        assertEquals(array[2], Integer.valueOf(4));
        assertEquals(array[6], Integer.valueOf(8));
        assertNull(array[0]);
    }

    @Test
    public void testDebbug() {
        ArrayTape<Integer> data = dataMiniArray();
        int offset = data.debug(System.out, "TestPrefix", 0);
        assertTrue(offset > 0); // Abhängig von der Rückgabelogik
    }

    @Test
    public void testBigArraySize() {
        ArrayTape<Integer> data = dataBigArray(2800);
        assertEquals(data.size(), 2800);
    }

    @Test
    public void testBigArrayFirstAndLast() {
        ArrayTape<Integer> data = dataBigArray(2800);
        assertEquals(data.first(), Integer.valueOf(0));
        assertEquals(data.last(), Integer.valueOf(2799));
    }

    @Test
    public void testBigArrayGetMiddle() {
        ArrayTape<Integer> data = dataBigArray(2800);
        assertEquals(data.get(1400), Integer.valueOf(1400));
    }

    @Test
    public void testBigArrayRemoveAt() {
        ArrayTape<Integer> data = dataBigArray(2800);
        data.removeAt(1400);
        assertEquals(data.size(), 2799);
        assertEquals(data.get(1400), Integer.valueOf(1401));
    }

    @Test
    public void testBigArrayIndexOf() {
        ArrayTape<Integer> data = dataBigArray(2800);
        assertEquals(data.indexOf(1400), 1400);
        assertEquals(data.indexOf(9999), -1);
    }

    @Test
    public void testBigArraySplitInHalf() {
        ArrayTape<Integer> data = dataBigArray(2800);
        ArrayMovie<Integer> split = data.splitInHalf();
        assertEquals(split.size(), 1400);
        assertEquals(data.size(), 1400);
        assertEquals(split.get(0), Integer.valueOf(1400));
        assertEquals(data.get(0), Integer.valueOf(0));
    }

    @Test
    public void testBigArrayCopyToArray() {
        ArrayTape<Integer> data = dataBigArray(2800);
        Object[] array = new Object[3000];
        data.copyToArray(array, 100);
        assertEquals(array[100], Integer.valueOf(0));
        assertEquals(array[1399], Integer.valueOf(1299));
        assertEquals(array[1400], Integer.valueOf(1300));
        assertNull(array[0]);
        assertNull(array[2999]);
    }

}
