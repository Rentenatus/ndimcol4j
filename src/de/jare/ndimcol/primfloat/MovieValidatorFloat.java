/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import de.jare.ndimcol.MovieValidatorConst;

/**
 * An interface for validating the similarity or equivalence of two elements.
 * <p>
 * Implementations of this interface define specific validation strategies for comparing an {@code observed} value
 * against an {@code expected} value. The result of the comparison is returned as an integer constant, typically defined
 * in {@link MovieValidatorConst}, such as {@code EQUALS}, {@code DIFFERENT}, or other domain-specific codes.
 * </p>
 *
 * <p>
 * Validators may implement strict equality checks, identity comparisons, or more flexible tolerance-based evaluations.
 * For example:
 * </p>
 * <ul>
 * <li>{@code ValidatorEqualsCheck} – compares values using {@code equals()}</li>
 * <li>{@code ValidatorIdentityCheck} – compares object references using {@code ==}</li>
 * <li>custom validator tolerance check – allows deviations within a defined corridor (e.g. ±5%)</li>
 * <li>{@code ValidatorAlwaysFits} – always returns {@code EQUALS}, acting as a wildcard</li>
 * </ul>
 *
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public interface MovieValidatorFloat extends MovieValidatorConst {

    /**
     * Validates the observed value against the expected value.
     * <p>
     * The implementation defines what constitutes a match. This may be strict equality, identity, or a domain-specific
     * tolerance range.
     * </p>
     *
     * @param observed the value that was observed or measured
     * @param expected the value that is expected or considered correct
     * @return an integer code representing the validation result, such as {@code EQUALS} or {@code DIFFERENT}
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    int validate(float observed, float expected);
}
