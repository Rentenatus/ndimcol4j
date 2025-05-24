/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

/**
 * A generic hashing strategy for determining hash codes and equality of objects.
 *
 * @param <T> the type of elements in this strategy
 * @author Janusch Rentenatus
 */
public class HashStrategie<T> {

    /**
     * Computes the hash code of the specified element. If the element is {@code null}, it returns {@code 0}; otherwise,
     * it delegates to {@code element.hashCode()}.
     *
     * @param element the object whose hash code is to be computed
     * @return the hash code of the object or {@code 0} if the object is {@code null}
     */
    public int hashCode(T element) {
        return element == null ? 0 : element.hashCode();
    }

    /**
     * Checks whether two objects are equal using the internal equality method.
     *
     * @param a the first object to compare
     * @param b the second object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    public boolean equals(T a, Object b) {
        return _equals(a, b);
    }

    /**
     * Compares two objects for equality.
     * <p>
     * If both objects are {@code null}, they are considered equal. If one is {@code null} and the other is not, they
     * are not equal. Otherwise, it uses the {@code equals} method of the first object to compare them.
     * </p>
     *
     * @param <U> the type of objects being compared
     * @param a the first object to compare
     * @param b the second object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    public static <U> boolean _equals(U a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }
}
