/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

/**
 * A validator that always considers two elements as matching.
 * <p>
 * This class acts as a "wildcard" or placeholder for validation sequences where the actual comparison between elements
 * is irrelevant or intentionally ignored. It consistently returns {@code INGNORED}, regardless of the values provided.
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
 * @param <T> the type of elements to be validated
 * @author Janusch Rentenatus
 */
public class ValidatorIgnore<T> implements MovieValidator<T> {

    /**
     * Validates two elements by always returning {@code INGNORED}.
     *
     * @param observed the observed element
     * @param expected the expected element
     * @return {@code INGNORED}, regardless of the input values
     */
    @Override
    public int validate(T observed, T expected) {
        return INGNORED;
    }

}
