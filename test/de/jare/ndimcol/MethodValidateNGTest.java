/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.primint.ArrayMovieInt;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArraySeason;
import de.jare.ndimcol.ref.MovieValidator;
import de.jare.ndimcol.ref.ValidatorEqualsCheck;
import de.jare.ndimcol.ref.ValidatorIdentityCheck;
import de.jare.ndimcol.ref.ValidatorIgnore;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class MethodValidateNGTest {

    public MethodValidateNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start MethodValidateNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End MethodValidateNGTest.");
        System.out.println("===============================================");
    }

    @Test
    public void testEqualsValidatorOnly() {
        ArrayMovie<String> expected = new ArraySeason<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        ArrayMovie<String> observed = new ArraySeason<>();
        observed.add("A");
        observed.add("X");
        observed.add("C");

        ArrayMovie<MovieValidator<String>> validatorList = new ArraySeason<>();
        validatorList.add(new ValidatorEqualsCheck<>());
        validatorList.add(new ValidatorEqualsCheck<>());
        validatorList.add(new ValidatorEqualsCheck<>());

        ArrayMovieInt result = expected.validate(observed, validatorList, new ValidatorIgnore<>());

        assertEquals(result.get(0), MovieValidatorConst.EQUALS);
        assertEquals(result.get(1), MovieValidatorConst.DIFFERENT);
        assertEquals(result.get(2), MovieValidatorConst.EQUALS);
    }

    @Test
    public void testIdentityValidatorWithFallback() {
        String shared = "shared";

        ArrayMovie<String> expected = new ArraySeason<>();
        expected.add(shared);
        expected.add("B");
        expected.add("C");

        ArrayMovie<String> observed = new ArraySeason<>();
        observed.add(shared);
        observed.add("B");
        observed.add("C");

        ArrayMovie<MovieValidator<String>> validatorList = new ArraySeason<>();
        validatorList.add(new ValidatorIdentityCheck<>());
        validatorList.add(null); // fallback will be used
        validatorList.add(new ValidatorIdentityCheck<>());

        ArrayMovieInt result = expected.validate(observed, validatorList, new ValidatorEqualsCheck<>());

        assertEquals(result.get(0), MovieValidatorConst.EQUALS); // same reference
        assertEquals(result.get(1), MovieValidatorConst.EQUALS); // fallback equals
        assertEquals(result.get(2), MovieValidatorConst.EQUALS); // same reference
    }

    @Test
    public void testAlwaysFitsAsDefault() {
        ArrayMovie<String> expected = new ArraySeason<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        ArrayMovie<String> observed = new ArraySeason<>();
        observed.add("X");
        observed.add("Y");
        observed.add("Z");

        ArrayMovie<MovieValidator<String>> validatorList = new ArraySeason<>();
        validatorList.add(null); // fallback
        validatorList.add(null); // fallback
        validatorList.add(null); // fallback

        ArrayMovieInt result = expected.validate(observed, validatorList, new ValidatorIgnore<>());

        assertEquals(result.get(0), MovieValidatorConst.INGNORED);
        assertEquals(result.get(1), MovieValidatorConst.INGNORED);
        assertEquals(result.get(2), MovieValidatorConst.INGNORED);
    }

    @Test
    public void testMixedValidatorsAndLengthMismatch() {
        ArrayMovie<String> expected = new ArraySeason<>();
        expected.add("A");
        expected.add("B");

        ArrayMovie<String> observed = new ArraySeason<>();
        observed.add("A");
        observed.add("B");
        observed.add("C"); // extra element

        ArrayMovie<MovieValidator<String>> validatorList = new ArraySeason<>();
        validatorList.add(new ValidatorEqualsCheck<>());
        validatorList.add(null); // fallback

        ArrayMovieInt result = expected.validate(observed, validatorList, new ValidatorIgnore<>());

        assertEquals(result.size(), 3);
        assertEquals(result.get(0), MovieValidatorConst.EQUALS);
        assertEquals(result.get(1), MovieValidatorConst.INGNORED); // fallback
        assertEquals(result.get(2), MovieValidatorConst.LONGER); // observed has extra
    }
}
