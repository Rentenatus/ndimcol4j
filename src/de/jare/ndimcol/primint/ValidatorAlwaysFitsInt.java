/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primint;

/**
 * A validator that always considers two elements as matching.
 * <p>
 * This class acts as a "wildcard" or placeholder for validation sequences where the actual comparison between elements
 * is irrelevant or intentionally ignored. It consistently returns {@code EQUALS}, regardless of the values provided.
 * </p>
 *
 * <p>
 * Typical use cases include:</p>
 * <ul>
 * <li>Skipping validation for optional or non-critical data segments</li>
 * <li>Maintaining structural consistency in validation pipelines</li>
 * <li>Using dummy validators in testing scenarios</li>
 * </ul>
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class ValidatorAlwaysFitsInt implements MovieValidatorInt {

    /**
     * Validates two elements by always returning {@code EQUALS}.
     *
     * @param observed the observed element
     * @param expected the expected element
     * @return {@code EQUALS}, regardless of the input values
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int validate(int observed, int expected) {
        return EQUALS;
    }

}
