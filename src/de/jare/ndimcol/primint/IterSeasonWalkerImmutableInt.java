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

import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class IterSeasonWalkerImmutableInt extends IterSeasonWalkerInt implements IteratorWalkerInt {

    /**
     * Constructor: Initializes the immutable IterSeasonWalker with a ArraySeason.
     *
     * @param season the ArraySeason that contains other ArrayMovies
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public IterSeasonWalkerImmutableInt(ArraySeasonInt season) {
        super(season);
    }

    /**
     * Removes the element. Throws an IndexOutOfBoundsException if there are no more elements.
     *
     * @return the element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no more elements in the season
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public int removeForward() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * Removes the element. Throws an IndexOutOfBoundsException if there are no previous elements.
     *
     * @return the element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no previous elements in the season
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public int removeBackward() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public int remove() {
        throw new IllegalCallerException("This walker doas not allow any changes.");
    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public boolean add(int element) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

    /**
     * This walker doas not allow any changes.
     *
     * @param element
     * @return never
     * @throws IllegalCallerException
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public int set(int element) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

    /**
     * This walker doas not allow any changes.
     *
     * @return never
     * @throws IllegalCallerException
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    final public boolean add(Collection<? extends Integer> col) {
        throw new IllegalCallerException("This walker doas not allow any changes.");

    }

}
