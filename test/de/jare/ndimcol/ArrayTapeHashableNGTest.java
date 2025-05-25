/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArraySeasonHashable;
import de.jare.ndimcol.ref.ArrayTape;
import de.jare.ndimcol.ref.ArrayTapeHashable;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class ArrayTapeHashableNGTest {

    public ArrayTapeHashableNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start ArrayTapeHashableNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End ArrayTapeHashableNGTest.");
        System.out.println("===============================================");
    }

    protected ArrayTapeHashable<Integer> dataMiniArray() {
        ArrayTapeHashable<Integer> at = new ArrayTapeHashable<>();
        at.add(4);
        at.add(5);
        at.add(6);
        at.add(7);
        at.add(8);
        return at;
    }

    protected ArrayTapeHashable<Integer> dataBigArray(int numberElems) {
        ArrayTapeHashable<Integer> at = new ArrayTapeHashable<>();
        for (int i = 0; i < numberElems; i++) {
            at.add((Integer) i);
        }
        return at;
    }

    @Test
    public void testRmoveAt() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
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
        ArrayTapeHashable<Integer> data = dataMiniArray();
        assertEquals(data.first(), Integer.valueOf(4));
    }

    @Test
    public void testLast() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        assertEquals(data.last(), Integer.valueOf(8));
    }

    @Test
    public void testAddAtIndex() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        data.addAt(2, 99);
        assertEquals(data.get(2), Integer.valueOf(99));
        assertEquals(data.size(), 6);
    }

    @Test
    public void testGet() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        assertEquals(data.get(1), Integer.valueOf(5));
    }

    @Test
    public void testRemoveAt() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        data.removeAt(2);
        assertEquals(data.size(), 4);
        assertEquals(data.get(2), Integer.valueOf(7));
    }

    @Test
    public void testSplitInHalf() {
        ArrayTapeHashable<Integer> data = dataBigArray(10);
        ArrayMovie<Integer> split = data.splitInHalf();
        assertEquals(split.size(), 5);
        assertEquals(data.size(), 5);
    }

    @Test
    public void testIndexOf() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        assertEquals(data.indexOf(6), 2);
        assertEquals(data.indexOf(99), -1);
    }

    @Test
    public void testCopyToArray() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        Object[] array = new Object[10];
        data.copyToArray(array, 2);
        assertEquals(array[2], Integer.valueOf(4));
        assertEquals(array[6], Integer.valueOf(8));
        assertNull(array[0]);
    }

    @Test
    public void testDebbug() {
        ArrayTapeHashable<Integer> data = dataMiniArray();
        int offset = data.debug(System.out, "TestPrefix", 0);
        assertTrue(offset > 0); // Abhängig von der Rückgabelogik
    }

    @Test
    public void testBigArraySize() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        assertEquals(data.size(), 2800);
    }

    @Test
    public void testBigArrayFirstAndLast() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        assertEquals(data.first(), Integer.valueOf(0));
        assertEquals(data.last(), Integer.valueOf(2799));
    }

    @Test
    public void testBigArrayGetMiddle() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        assertEquals(data.get(1400), Integer.valueOf(1400));
    }

    @Test
    public void testBigArrayRemoveAt() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        data.removeAt(1400);
        assertEquals(data.size(), 2799);
        assertEquals(data.get(1400), Integer.valueOf(1401));
    }

    @Test
    public void testBigArrayIndexOf() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        assertEquals(data.indexOf(1400), 1400);
        assertEquals(data.indexOf(9999), -1);
    }

    @Test
    public void testBigArraySplitInHalf() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        ArrayMovie<Integer> split = data.splitInHalf();
        assertEquals(split.size(), 1400);
        assertEquals(data.size(), 1400);
        assertEquals(split.get(0), Integer.valueOf(1400));
        assertEquals(data.get(0), Integer.valueOf(0));
    }

    @Test
    public void testBigArrayCopyToArray() {
        ArrayTapeHashable<Integer> data = dataBigArray(2800);
        Object[] array = new Object[3000];
        data.copyToArray(array, 100);
        assertEquals(array[100], Integer.valueOf(0));
        assertEquals(array[1399], Integer.valueOf(1299));
        assertEquals(array[1400], Integer.valueOf(1300));
        assertNull(array[0]);
        assertNull(array[2999]);
    }

    @Test
    public void testBigArrayHash() {
        ArrayTapeHashable<Integer> data1 = dataBigArray(6800);
        ArrayTape<Integer> data2 = data1.emptyMovie(6803);
        data2.addAll(data1);
        System.out.println("Hash: " + data1.hashCode());
        assertEquals(data1.hashCode(), data2.hashCode());
        data1.add(31);
        data2.add(31);
        System.out.println("Hash: " + data1.hashCode());
        assertEquals(data1.hashCode(), data2.hashCode());
        data1.removeAt(101);
        data2.removeAt(101);
        System.out.println("Hash: " + data1.hashCode());
        assertEquals(data1.hashCode(), data2.hashCode());
        data1.add(31);
        data2.add(31);
        System.out.println("Hash: " + data1.hashCode());
        assertEquals(data1.hashCode(), data2.hashCode());
        int hash = data1.combine(data1.hashCode(), data2.size(), data2.hashCode());
        data1.addAll(data2);
        System.out.println("Hash: " + hash);
        assertEquals(data1.hashCode(), hash);

    }
}
