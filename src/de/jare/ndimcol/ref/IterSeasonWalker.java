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
public class IterSeasonWalker<T> implements IteratorWalker<T> {

    private final IterTapeWalker<ArrayMovie<T>> outerWalker; // Walker for the outer ArrayTape
    private IteratorWalker<T> innerWalker; // Walker for the current inner ArrayTape or ArraySeason
    private final ArraySeason<T> season;
    private int currentIndex; // Tracks the total index across all elements
    private boolean forward;

    /**
     * Constructor: Initializes the IterSeasonWalker with a ArraySeason.
     *
     * @param season the ArraySeason that contains other ArrayMovies
     */
    public IterSeasonWalker(ArraySeason<T> season) {
        this.season = season;
        this.outerWalker = season.data.softWalker();
        this.currentIndex = 0;
        this.forward = true;

        if (outerWalker.hasNext()) {
            this.innerWalker = outerWalker.next().softWalker();
        } else {
            this.innerWalker = null; // No inner tapes available
        }
    }

    /**
     * Returns the ArraySeason that this walker is traversing.
     *
     * @return the ArraySeason being traversed
     */
    @Override
    public ArrayMovie<T> getRelatedMovie() {
        return this.season;
    }

    /**
     * Checks if there are more elements across all ArrayTape (inner and outer).
     *
     * @return true if there are more elements
     */
    @Override
    public boolean hasNext() {
        return (innerWalker != null && innerWalker.hasNext()) || outerWalker.hasNext();
    }

    /**
     * Returns the next element and moves the walkers accordingly. Updates the accumulated index.
     *
     * @return the next element
     */
    @Override
    public T next() {
        if (innerWalker != null && innerWalker.hasNext()) {
            currentIndex++;
            return innerWalker.next();
        } else if (outerWalker.hasNext()) {
            innerWalker = outerWalker.next().softWalker();
            return next();
        }
        throw new IndexOutOfBoundsException("No more elements in the IterSeasonWalker.");
    }

    /**
     * Removes the next element. Throws an IndexOutOfBoundsException if there are no more elements.
     *
     * @return the next element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no more elements in the season
     */
    @Override
    public T removeForward() {
        forward = true;
        return season.removeAt(currentIndex);
    }

    /**
     * Checks if there are previous elements across all ArrayTape (inner and outer).
     *
     * @return true if there are previous elements
     */
    @Override
    public boolean hasPrevious() {
        return (innerWalker != null && innerWalker.hasPrevious()) || outerWalker.hasPrevious();
    }

    /**
     * Returns the previous element and moves the walkers accordingly. Updates the accumulated index.
     *
     * @return the previous element
     */
    @Override
    public T previous() {
        if (innerWalker != null && innerWalker.hasPrevious()) {
            currentIndex--;
            return innerWalker.previous();
        } else if (outerWalker.hasPrevious()) {
            innerWalker = outerWalker.previous().softWalker();
            innerWalker.goLast(); // Go to the last element of the inner tape
            currentIndex--;
            return innerWalker.previous();
        }
        throw new IndexOutOfBoundsException("No previous elements in the IterSeasonWalker.");
    }

    /**
     * Removes the previous element and moves the current index backward. Throws an IndexOutOfBoundsException if there
     * are no previous elements.
     *
     * @return the previous element that was removed from the ArraySeason
     * @throws IndexOutOfBoundsException if there are no previous elements in the season
     */
    @Override
    public T removeBackward() {
        forward = false;
        return season.removeAt(--currentIndex);
    }

    /**
     * Removes the current element.
     *
     * @return the current element that was removed
     * @throws IndexOutOfBoundsException if there are no previous elements in the tape
     */
    @Override
    public T remove() {
        if (forward) {
            return removeForward();
        } else {
            return removeBackward();
        }
    }

    /**
     * Adds a new element to the current inner ArrayTape.
     *
     * @param element the element to be added
     * @return true if the addition is successful
     */
    @Override
    public boolean add(T element) {
        if (innerWalker == null) {
            throw new IllegalStateException("No active inner walker to add elements to.");
        }
        season.deepChanged();
        return innerWalker.add(element);
    }

    /**
     * Sets the current element to the specified element.
     *
     * @param element the element to be set
     * @return the previous element that was replaced
     */
    @Override
    public T set(T element) {
        if (innerWalker == null) {
            throw new IllegalStateException("No active inner walker to add elements to.");
        }
        final T ret = innerWalker.set(element);
        season.replaced(currentIndex, ret, element);
        return ret;
    }

    /**
     * Adds a collection to the current inner ArrayTape.
     *
     * @param col collection containing elements to be added
     * @return true if the addition is successful
     */
    @Override
    public boolean add(Collection<? extends T> col) {
        if (innerWalker == null) {
            throw new IllegalStateException("No active inner walker to add elements to.");
        }
        season.deepChanged();
        return innerWalker.add(col);
    }

    /**
     * Resets both walkers: outer and inner. Resets the accumulated index as well.
     *
     * @return this walker
     */
    @Override
    public IteratorWalker<T> goFirst() {
        outerWalker.goFirst();
        currentIndex = 0;
        forward = true;

        if (outerWalker.hasNext()) {
            innerWalker = outerWalker.next().softWalker();
        } else {
            innerWalker = null;
        }
        return this;
    }

    /**
     * Moves both walkers to the last position. Updates the accumulated index to the total size.
     *
     * @return this walker
     */
    @Override
    public IteratorWalker<T> goLast() {
        outerWalker.goLast();
        currentIndex = season.size() - 1;
        forward = false;

        if (outerWalker.hasPrevious()) {
            innerWalker = outerWalker.previous().softWalker();
            innerWalker.goLast();
        } else {
            innerWalker = null;
        }
        return this;
    }

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
    @Override
    public IteratorWalker<T> goLeafIndex(int index) {
        if (index < 0 || index >= season.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + season.size());
        }
        outerWalker.goFirst();
        currentIndex = 0;

        if (outerWalker.hasNext()) {
            innerWalker = outerWalker.next().softWalker();
        } else {
            return null;
        }
        while (currentIndex <= index) {
            final int searchIndex = index - currentIndex;
            if (searchIndex < innerWalker.size()) {
                currentIndex = index;
                return new IterCoverWalker<>(season, innerWalker.goLeafIndex(searchIndex));
            } else {
                currentIndex = currentIndex + innerWalker.size();
                if (outerWalker.hasNext()) {
                    innerWalker = outerWalker.next().softWalker();
                } else {
                    throw new IndexOutOfBoundsException("Unexpected end of ArrayTape during traversal.");
                }
            }
        }
        return null;
    }

    /**
     * Returns the accumulated index across all ArrayTape.
     *
     * @return the accumulated index
     */
    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the number of elements in the entire SeasonTape.
     *
     * @return the total size
     */
    @Override
    public int size() {
        return season.size();
    }

    /**
     * Returns true if the SeasonTape is empty.
     *
     * @return true if the SeasonTape is empty
     */
    @Override
    public boolean isEmpty() {
        return season.isEmpty();
    }

    /**
     * Returns true if the SeasonTape has a record.
     *
     * @return true if the SeasonTape has a record
     */
    @Override
    public boolean hasRecord() {
        return season.hasRecord();
    }

}
