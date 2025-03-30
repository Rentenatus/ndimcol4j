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
        System.out.println("--------------");
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

}
