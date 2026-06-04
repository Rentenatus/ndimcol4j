/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this walker
 */
public class IterSeasonWalkerImmutable<T> extends IterSeasonWalker<T> implements IteratorWalker<T> {

    /**
     * Constructor: Initializes the immutable IterSeasonWalker with a ArraySeason.
     *
     * @param season the ArraySeason that contains other ArrayMovies
     */
    public IterSeasonWalkerImmutable(ArraySeason<T> season) {
        super(season);
    }

    /**
     * Removes the element. Throws an IndexOutOfBoundsException if there are no more elements.
     *
     * @return the element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no more elements in the season
     */
    @Override
    final public T removeForward() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * Removes the element. Throws an IndexOutOfBoundsException if there are no previous elements.
     *
     * @return the element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no previous elements in the season
     */
    @Override
    final public T removeBackward() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    @Override
    final public T remove() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    @Override
    final public boolean add(T element) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

    /**
     * This walker doas not allow any changes.
     *
     * @param element
     * @return never
     * @throws IllegalCallerException
     */
    @Override
    final public T set(T element) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    @Override
    final public boolean add(Collection<? extends T> col) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

}
