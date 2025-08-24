/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.primlong;

/**
 * A validator for primitive {@code long} values that allows absolute tolerance margins.
 * <p>
 * This implementation checks whether the observed value is within a defined corridor around the expected value. The
 * corridor is defined by a lower and upper tolerance, allowing asymmetric deviation.
 * </p>
 *
 * <p>
 * Validation logic:</p>
 * <ul>
 * <li>If {@code observed == expected}, the result is {@code EQUALS}.</li>
 * <li>If {@code observed} lies within {@code [expected - lowerTolerance, expected + upperTolerance]}, the result is
 * {@code ACCEPTABLE}.</li>
 * <li>Otherwise, the result is {@code DIFFERENT}.</li>
 * </ul>
 *
 * <p>
 * Example:</p>
 * <pre>
 * lowerTolerance = 2
 * upperTolerance = 3
 * expected = 15
 * valid range = [13, 18]
 *
 * observed = 13 &rarr; ACCEPTABLE
 * observed = 16 &rarr; ACCEPTABLE
 * observed = 18 &rarr; ACCEPTABLE
 * observed = 12 &rarr; DIFFERENT
 * </pre>
 *
 * @author Janusch Rentenatus
 */
// #### This class has exceptionally not been generated in this package.
public class ValidatorAbsToleranceCheckLong implements MovieValidatorLong {

    /**
     * Allowed deviation below the expected value
     */
    private final long lowerTolerance;

    /**
     * Allowed deviation above the expected value
     */
    private final long uperTolerance;

    /**
     * Constructs a validator with specified lower and upper tolerance margins.
     *
     * @param lowerTolerance the maximum allowed deviation below the expected value
     * @param uperTolerance the maximum allowed deviation above the expected value
     */
    public ValidatorAbsToleranceCheckLong(long lowerTolerance, long uperTolerance) {
        this.lowerTolerance = lowerTolerance;
        this.uperTolerance = uperTolerance;
    }

    /**
     * Constructs a validator with specified tolerance margins.
     *
     * @param tolerance the maximum allowed deviation below and above the expected value
     */
    public ValidatorAbsToleranceCheckLong(long tolerance) {
        this.lowerTolerance = tolerance;
        this.uperTolerance = tolerance;
    }

    /**
     * Validates whether the observed value is equal to or acceptably close to the expected value.
     *
     * @param observed the measured or actual value
     * @param expected the reference or target value
     * @return {@code EQUALS} if values are identical, {@code ACCEPTABLE} if within tolerance range, {@code DIFFERENT}
     * otherwise
     */
    @Override
    public int validate(long observed, long expected) {
        if (observed == expected) {
            return EQUALS;
        }
        return (observed <= expected + uperTolerance)
                && (observed >= expected - lowerTolerance)
                        ? ACCEPTABLE : DIFFERENT;
    }

}
