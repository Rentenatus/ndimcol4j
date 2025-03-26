/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

public class IterSeasonWalker<T> implements IteratorWalker<T> {

    private final IterTapeWalker<ArrayMovie<T>> outerWalker; // Walker for the outer ArrayTape
    private IteratorWalker<T> innerWalker; // Walker for the current inner ArrayTape or ArraySeason
    private final ArraySeason<T> season;
    private int currentIndex; // Tracks the total index across all elements

    /**
     * Constructor: Initializes the IterSeasonWalker with a ArraySeason.
     *
     * @param season the ArraySeason that contains other ArrayMovies
     */
    public IterSeasonWalker(ArraySeason<T> season) {
        this.season = season;
        this.outerWalker = season.data.softWalker();
        this.currentIndex = 0;

        if (outerWalker.hasNext()) {
            this.innerWalker = outerWalker.next().softWalker();
        } else {
            this.innerWalker = null; // No inner tapes available
        }
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
    public T removeNext() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more elements in the season.");
        }
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
    public T removePrev() {
        if (!hasPrevious()) {
            throw new IndexOutOfBoundsException("No previous elements in the season.");
        }
        return season.removeAt(--currentIndex);
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
        return innerWalker.add(element);
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

        if (outerWalker.hasPrevious()) {
            innerWalker = outerWalker.previous().softWalker();
            innerWalker.goLast();
        } else {
            innerWalker = null;
        }
        return this;
    }

    /**
     * Moves to the index.
     *
     * @param index
     * @return Leaf walker
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
     * Returns the number of elements in the entire ArrayTape.
     *
     * @return the total size
     */
    @Override
    public int size() {
        return season.size();
    }

    @Override
    public boolean isEmpty() {
        return season.isEmpty();
    }

    @Override
    public boolean hasRecord() {
        return season.hasRecord();
    }

}
