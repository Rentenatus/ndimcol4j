/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;

/**
 * A walker is an interface for an iterator that can move forward and backward.
 *
 * @author Jansuch Rentenatus
 * @param <T> the type of elements in this walker
 */
public interface IteratorWalker<T> {

    /**
     * Returns the ArrayMovie that this walker is traversing.
     *
     * @return the ArrayMovie being traversed
     */
    ArrayMovie<T> getRelatedMovie();

    /**
     * Returns the current element. Takes forward movement by positioning.
     *
     * @return the current element
     */
    boolean hasNext();

    /**
     * Returns the current element and returns it. Takes forward movement by positioning.
     *
     * @return the current element
     */
    T removeForward();

    /**
     * Returns the next element. Takes forward movement by positioning.
     *
     * @return the next element
     */
    T next();

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
    boolean hasPrevious();

    /**
     * Returns the current element and returns it. Takes backward movement by positioning.
     *
     * @return the current element
     */
    T previous();

    /**
     * Removes the current element and returns it. Takes backward movement by positioning.
     *
     * @return the removed element
     */
    T removeBackward();

    /**
     * Removes the current element and returns it. Takes into account the last forward or backward movement when
     * positioning
     *
     * @return the removed element
     */
    T remove();

    /**
     * Adds the specified element to the ArrayMovie at current index of the Walker.
     *
     * @param element the element to be added
     * @return true if the element was added successfully, false otherwise
     */
    boolean add(T element);

    /**
     * Adds all elements in the specified collection to the ArrayMovie at current index of the Walker.
     *
     * @param col the collection of elements to be added
     * @return true if the elements were added successfully, false otherwise
     */
    boolean add(Collection<? extends T> col);

    T set(T element);

    /**
     * Resets the current index to the beginning .
     *
     * @return this
     */
    IteratorWalker<T> goFirst();

    /**
     * Sets the current index to the last element.
     *
     * @return this
     */
    IteratorWalker<T> goLast();

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
    IteratorWalker<T> goLeafIndex(int index);

    /**
     * Returns the current index of the Walker.
     *
     * @return the current index position
     */
    int getCurrentIndex();

    /**
     * Returns the number of elements in the ArrayMovie.
     *
     * @return the current size of own ArrayMovie
     */
    int size();

    /**
     * Returns true if the ArrayMovie is empty.
     *
     * @return true if the ArrayMovie is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns true if the ArrayMovie contains one or more elements.
     *
     * @return true if the ArrayMovie has elements, false otherwise
     */
    boolean hasRecord();

}
