/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
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
        System.out.println("--------------");
        data.debbug(System.out, " . ");
        System.out.println("--------------");
        data.removeTrim(2);
        System.out.println("--------------");
        data.debbug(System.out, " . ");
        System.out.println("--------------");
        assertEquals("" + data.size(), "3");
        assertEquals("" + data.get(0), "4");
        assertEquals("" + data.get(1), "5");
        assertEquals("" + data.get(2), "8");
    }

}
