/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.*;

/**
 * ArraySeason as List.
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this list
 */
public class ListSeason<T> extends ArraySeasonHashable<T> implements List<T> {

    public ListSeason() {
        super();
    }

    public ListSeason(final Screenplay screenplay) {
        super(screenplay);
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, T element) {
        super.add(index, element);
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts
     * one from their indices). Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    @Override
    public T remove(int index) {
        return super.removeAt(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     *
     * @return a list iterator over the elements in this list.
     */
    @Override
    public ListIterator<T> listIterator() {
        return Collections.unmodifiableList(subList(0, size)).listIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position
     * in the list. The specified index indicates the first element that would be returned by an initial call to next.
     * It must be a valid index (from 0 to size(), inclusive), and it is an error to pass an index outside the range.
     *
     * @param index index of the first element to be returned from the list iterator (by a call to next)
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position
     * in the list.
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return Collections.unmodifiableList(subList(0, size)).listIterator(index);
    }

    @Override
    public ListSeason<T> splitInHalf() {
        return (ListSeason<T>) super.splitInHalf();
    }

    /**
     * Returns the number of elements in this list.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ListSeason containing the elements in the specified range
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return (ListSeason<T>) subMovie(fromIndex, toIndex);
    }

    /**
     * Creates a new empty list with the same screenplay. The new movie is not a copy of this list.
     *
     * @param initialCapacityOrZero not used
     * @return a new empty list with the same screenplay
     */
    @Override
    public ListSeason<T> emptyMovie(int initialCapacityOrZero) {
        return new ListSeason<>(screenplay);
    }

}
