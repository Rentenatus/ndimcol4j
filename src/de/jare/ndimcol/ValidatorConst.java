/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 * Defines standard constants used by {@link de.jare.ndimcol.ref.Validator} implementations to represent the result of a
 * validation operation.
 * <p>
 * These constants provide a unified way to express whether two elements are equal, different, or deviate in a specific
 * way (e.g. length or tolerance).
 * </p>
 *
 * <p>
 * Typical usage:</p>
 * <pre>{@code
 * if (validator.validate(a, b) == ValidatorConst.EQUALS) {
 *     // elements match exactly
 * }
 * }</pre>
 *
 * @author Janusch Rentenatus
 */
public interface ValidatorConst {

    /**
     * Indicates that the observed and expected elements are considered equal. This is typically used when the
     * comparison yields a perfect match.
     */
    int EQUALS = 0;

    /**
     * Indicates that the observed and expected elements are not equal. This is the default result when no match or
     * acceptable deviation is found.
     */
    int DIFFERENT = 1;

    /**
     * Indicates that the observed element deviates from the expected value, but the deviation is within an acceptable
     * corridor or tolerance. Useful for numeric comparisons, fuzzy matching, or domain-specific thresholds.
     */
    int ACCEPTABLE = 2;

    /**
     * This value is returned only when comparing collections of values: if the observed collection contains more
     * elements than the expected collection.
     */
    int LONGER = 4;

    /**
     * This value is returned only when comparing collections of values: if the observed collection contains fewer
     * elements than the expected collection.
     */
    int SHORTER = 8;

}
