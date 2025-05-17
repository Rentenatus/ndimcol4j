/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;

/**
 * A walker is an interface for an iterator that can move forward and backward.
 *
 * @author Jansuch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public interface IteratorWalkerFloat {

    /**
     * Returns the ArrayMovie that this walker is traversing.
     *
     * @return the ArrayMovie being traversed
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    ArrayMovieFloat getRelatedMovie();

    /**
     * Returns the current element. Takes forward movement by positioning.
     *
     * @return the current element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean hasNext();

    /**
     * Returns the current element and returns it. Takes forward movement by positioning.
     *
     * @return the current element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    float removeForward();

    /**
     * Returns the next element. Takes forward movement by positioning.
     *
     * @return the next element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    float next();

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean hasPrevious();

    /**
     * Returns the current element and returns it. Takes backward movement by positioning.
     *
     * @return the current element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    float previous();

    /**
     * Removes the current element and returns it. Takes backward movement by positioning.
     *
     * @return the removed element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    float removeBackward();

    /**
     * Removes the current element and returns it. Takes into account the last forward or backward movement when
     * positioning
     *
     * @return the removed element
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    float remove();

    /**
     * Adds the specified element to the ArrayMovie at current index of the Walker.
     *
     * @param element the element to be added
     * @return true if the element was added successfully, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean add(float element);

    /**
     * Adds all elements in the specified collection to the ArrayMovie at current index of the Walker.
     *
     * @param col the collection of elements to be added
     * @return true if the elements were added successfully, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean add(Collection<? extends Float> col);

    float set(float element);

    /**
     * Resets the current index to the beginning .
     *
     * @return this
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    IteratorWalkerFloat goFirst();

    /**
     * Sets the current index to the last element.
     *
     * @return this
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    IteratorWalkerFloat goLast();

    /**
     * Sets the current index to the specified position .
     * <p>
     * A ArraySaesson in 2d is a ArrayTape of ArrayTape. A ArraySaesson in 3d is a ArrayTape of 2d ArraySaesson means
     * ArrayTape of ArrayTape of ArrayTape.
     *
     * @param index the index to set as the current position
     * @return IteratorWalker of the deepest ArrayTape of movie
     * @throws IndexOutOfBoundsException if the index is out of range
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    IteratorWalkerFloat goLeafIndex(int index);

    /**
     * Returns the current index of the Walker.
     *
     * @return the current index position
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    int getCurrentIndex();

    /**
     * Returns the number of elements in the ArrayMovie.
     *
     * @return the current size of own ArrayMovie
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    int size();

    /**
     * Returns true if the ArrayMovie is empty.
     *
     * @return true if the ArrayMovie is empty, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean isEmpty();

    /**
     * Returns true if the ArrayMovie contains one or more elements.
     *
     * @return true if the ArrayMovie has elements, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    boolean hasRecord();

}
