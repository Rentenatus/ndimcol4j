/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.primfloat;

/**
 * A validator for primitive {@code float} values that allows relative tolerance margins.
 * <p>
 * This implementation checks whether the observed value lies within a relative corridor around the expected value. The
 * corridor is defined by a lower and upper tolerance factor, allowing asymmetric percentage-based deviation.
 * </p>
 *
 * <p>
 * <strong>Validation logic:</strong></p>
 * <ul>
 * <li>If {@code observed == expected}, the result is {@code EQUALS}.</li>
 * <li>If {@code observed} lies within the range
 * {@code [expected * (1 - lowerTolerance), expected * (1 + upperTolerance)]}, the result is {@code ACCEPTABLE}.</li>
 * <li>Otherwise, the result is {@code DIFFERENT}.</li>
 * </ul>
 *
 * <p>
 * <strong>Example:</strong></p>
 * <pre>
 * lowerTolerance = 0.10 &rarr; 10% below
 * upperTolerance = 0.20 &rarr; 20% above
 * expected = 15.0
 * valid range = [13.5, 18.0]
 *
 * observed = 13.5 &rarr; ACCEPTABLE
 * observed = 16.1 &rarr; ACCEPTABLE
 * observed = 18.0 &rarr; ACCEPTABLE
 * observed = 12.2 &rarr; DIFFERENT
 * </pre>
 *
 * <p>
 * This validator is useful when comparing floating-point values where proportional deviation is acceptable, such as in
 * scientific measurements, financial calculations, or tolerance-based data matching.
 * </p>
 *
 * @author Janusch Rentenatus
 */
// #### This class has exceptionally not been generated in this package.
public class ValidatorRelToleranceCheckFloat implements MovieValidatorFloat {

    /**
     * Relative lower tolerance factor (e.g. 0.90 for 10% below expected)
     */
    private final float lowerTolerance;

    /**
     * Relative upper tolerance factor (e.g. 1.20 for 20% above expected)
     */
    private final float upperTolerance;

    /**
     * Constructs a validator with specified lower and upper relative tolerance margins.
     *
     * @param lowerTolerance the maximum allowed relative deviation below the expected value (e.g. 0.10 for 10%)
     * @param upperTolerance the maximum allowed relative deviation above the expected value (e.g. 0.20 for 20%)
     */
    public ValidatorRelToleranceCheckFloat(float lowerTolerance, float upperTolerance) {
        this.lowerTolerance = 1.0f - lowerTolerance;
        this.upperTolerance = 1.0f + upperTolerance;
    }

    /**
     * Constructs a symmetric validator with the same relative tolerance above and below the expected value.
     *
     * @param tolerance the maximum allowed relative deviation (e.g. 0.15 for ±15%)
     */
    public ValidatorRelToleranceCheckFloat(float tolerance) {
        this.lowerTolerance = 1.0f - tolerance;
        this.upperTolerance = 1.0f + tolerance;
    }

    /**
     * Validates whether the observed value is equal to or acceptably close to the expected value, based on relative
     * tolerance margins.
     *
     * @param observed the measured or actual value
     * @param expected the reference or target value
     * @return {@code EQUALS} if values are identical, {@code ACCEPTABLE} if within relative tolerance range,
     * {@code DIFFERENT} otherwise
     */
    @Override
    public int validate(float observed, float expected) {
        if (observed == expected) {
            return EQUALS;
        }
        return (observed <= expected * upperTolerance)
                && (observed >= expected * lowerTolerance)
                        ? ACCEPTABLE : DIFFERENT;
    }
}
