/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.primlong;

import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Janusch Rentenatus
 */
public interface IteratorLong {

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
     * would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    long next();

    /**
     * Removes from the underlying collection the last element returned by this iterator (optional operation). This
     * method can be called only once per call to {@link #next}.
     * <p>
     * The behavior of an iterator is unspecified if the underlying collection is modified while the iteration is in
     * progress in any way other than by calling this method, unless an overriding class has specified a concurrent
     * modification policy.
     * <p>
     * The behavior of an iterator is unspecified if this method is called after a call to the
     * {@link #forEachRemaining forEachRemaining} method.
     *
     * @implSpec The default implementation throws an instance of {@link UnsupportedOperationException} and performs no
     * other action.
     *
     * @throws UnsupportedOperationException if the {@code remove} operation is not supported by this iterator
     *
     * @throws IllegalStateException if the {@code next} method has not yet been called, or the {@code remove} method
     * has already been called after the last call to the {@code next} method
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * Performs the given action for each remaining element until all elements have been processed or the action throws
     * an exception. Actions are performed in the order of iteration, if that order is specified. Exceptions thrown by
     * the action are relayed to the caller.
     * <p>
     * The behavior of an iterator is unspecified if the action modifies the collection in any way (even by calling the
     * {@link #remove remove} method or other mutator methods of {@code Iterator} subtypes), unless an overriding class
     * has specified a concurrent modification policy.
     * <p>
     * Subsequent behavior of an iterator is unspecified if the action throws an exception.
     *
     * @implSpec
     * <p>
     * The default implementation behaves as if:      <pre>{@code
     *     while (hasNext())
     *         action.accept(next());
     * }</pre>
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @since 1.8
     */
    default void forEachRemaining(Consumer<Long> action) {
        Objects.requireNonNull(action);
        while (hasNext()) {
            action.accept(next());
        }
    }
}
