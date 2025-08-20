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
import de.jare.ndimcol.primlong.ArraySeasonLong;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArrayTape;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class ForEachNGTest {

    public ForEachNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start ForEachNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End ForEachNGTest.");
        System.out.println("===============================================");
    }

    @Test
    public void testFloat() {
        ArraySeasonFloat data = new ArraySeasonFloat();
        float f = 0.012345f;
        for (int i = 0; i < 77420; i++) {
            data.add(i * f);
            f = -f;
        }
        data.forEach(
                value -> value > 0, // Predicate: checks whether the value is positive
                value -> assertTrue(value > 0),
                value -> assertFalse(value > 0)
        );
    }

    @Test
    public void testLong() {
        ArraySeasonLong data = new ArraySeasonLong();
        long f = 120;
        for (int i = 0; i < 77420; i++) {
            data.add(i * f);
            f = -f;
        }
        data.forEach(
                value -> value > 0, // Predicate: checks whether the value is positive
                value -> assertTrue(value > 0),
                value -> assertFalse(value > 0)
        );
    }

    @Test
    public void testInt() {
        ArraySeasonInt data = new ArraySeasonInt();
        int f = 1;
        for (int i = 0; i < 77420; i++) {
            data.add(i * f);
            f = -f;
        }
        data.forEach(
                value -> value > 0, // Predicate: checks whether the value is positive
                value -> assertTrue(value > 0),
                value -> assertFalse(value > 0)
        );
    }
}
