/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

/**
 * A generic or primitive, generated validator that checks whether two elements are identical by reference.
 * <p>
 * This implementation uses the {@code ==} operator to determine if the observed and expected elements refer to the
 * exact same object in memory. It does not perform deep or value-based comparison.
 * </p>
 *
 * <p>
 * Typical use cases include:</p>
 * <ul>
 * <li>Detecting shared object instances</li>
 * <li>Ensuring reference integrity in object graphs</li>
 * <li>Validating singleton or cached object usage</li>
 * </ul>
 *
 * @param <T> the type of elements to be validated
 * @author Janusch Rentenatus
 */
public class ValidatorIdentityCheck<T> implements Validator<T> {

    /**
     * Validates whether the observed and expected elements are the same object.
     * <p>
     * Returns {@code EQUALS} if both references point to the same object, otherwise returns {@code DIFFERENT}.
     * </p>
     *
     * @param observed the observed element
     * @param expected the expected element
     * @return {@code EQUALS} if both references are identical, {@code DIFFERENT} otherwise
     */
    @Override
    public int validate(T observed, T expected) {
        return (observed == expected) ? EQUALS : DIFFERENT;
    }

}
