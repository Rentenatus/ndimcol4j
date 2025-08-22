/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

/**
 * A generic or primitive, generated validator that checks for equality between two elements.
 * <p>
 * This implementation compares the observed and expected values using {@link Object#equals(Object)}. If both values are
 * {@code null}, they are considered equal. If only one is {@code null}, they are considered different.
 * </p>
 *
 * <p>
 * Typical use cases include:</p>
 * <ul>
 * <li>Validating exact matches between expected and actual data</li>
 * <li>Ensuring consistency in data streams or transformation pipelines</li>
 * <li>Comparing values in unit tests or verification routines</li>
 * </ul>
 *
 * @param <T> the type of elements to be validated
 * @author Janusch Rentenatus
 */
public class ValidatorEqualsCheck<T> implements MovieValidator<T> {

    /**
     * Validates whether the observed and expected elements are equal.
     * <p>
     * Returns {@code EQUALS} if both elements are equal (including both being {@code null}), otherwise returns
     * {@code DIFFERENT}.
     * </p>
     *
     * @param observed the observed element
     * @param expected the expected element
     * @return {@code EQUALS} if the elements are equal, {@code DIFFERENT} otherwise
     */
    @Override
    public int validate(T observed, T expected) {
        if (observed == null) {
            return expected == null ? EQUALS : DIFFERENT;
        }
        return observed.equals(expected) ? EQUALS : DIFFERENT;
    }

}
