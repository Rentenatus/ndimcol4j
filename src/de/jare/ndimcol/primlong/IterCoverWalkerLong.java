/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;

/**
 *
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class IterCoverWalkerLong implements IteratorWalkerLong {

    final IteratorWalkerLong inner;
    final ArraySeasonLong observer;

    /**
     * Returns the ArraySeason observing the traversal.
     *
     * @return the ArraySeason being observed
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArrayMovieLong getRelatedMovie() {
        return this.observer;
    }

    /**
     * Constructs a CoverWalker.
     *
     * @param inner the IteratorWalker to be cover.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public IterCoverWalkerLong(ArraySeasonLong observer, IteratorWalkerLong inner) {
        this.inner = inner;
        this.observer = observer;
    }

    /**
     * Returns true if there are more elements when moving in the forward direction.
     *
     * @return true if the current index is less than the size of the ArrayTape
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean hasNext() {
        return inner.hasNext();
    }

    /**
     * Returns the next element.
     *
     * @return the next element in the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no more elements in the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public long next() {
        return inner.next();
    }

    /**
     * Adds the specified element to the inner IteratorWalker.
     *
     *
     * @param element the element to be added to the inner IteratorWalker
     * @return true
     * @throws OutOfMemoryError if there is not enough memory to create an element with the increased capacity
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(long element) {
        if (inner.add(element)) {
            observer.deepChanged(); // we cannot know if we are at the end
            observer.updateCounter++;
            observer.size++;
            return true;
        }
        return false;
    }

    /**
     * Adds a collection to the current inner walker.
     *
     * @param col collection containing elements to be added
     * @return true if the addition is successful
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(Collection<? extends Long> col) {
        if (inner.add(col)) {
            observer.deepChanged();
            observer.updateCounter++;
            observer.size += col.size();
            return true;
        }
        return false;
    }

    @Override
    public long set(long element) {
        long ret = inner.set(element);
        {
            observer.replaced(getCurrentIndex(), ret, element);
            observer.updateCounter++;
        }
        return ret;
    }

    /**
     * Removes the next element in the inner IteratorWalker.
     *
     * @return the next element that was removed from the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no more elements in the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public long removeForward() {
        long ret = inner.removeForward();
        {
            observer.deepChanged();
            observer.updateCounter++;
            observer.size--;
        }
        if (inner.size() < 8) {
            observer.splitOrGlue();
        }
        return ret;
    }

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean hasPrevious() {
        return inner.hasPrevious();
    }

    /**
     * Returns the previous element in the inner IteratorWalker.
     *
     * @return the previous element in the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no previous elements
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public long previous() {
        return inner.previous();
    }

    /**
     * Removes the previous element .
     *
     * @return the previous element that was removed from the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no previous elements in the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public long removeBackward() {
        long ret = inner.removeBackward();
        {
            observer.deepChanged();
            observer.updateCounter++;
            observer.size--;
        }
        return ret;
    }

    /**
     * Removes the current element in the inner IteratorWalker.
     *
     * @return the current element that was removed from the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no more elements in the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public long remove() {
        long ret = inner.remove();
        {
            observer.deepChanged();
            observer.updateCounter++;
            observer.size--;
        }
        return ret;
    }

    /**
     * Resets the current index to the beginning .
     *
     * @return the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IteratorWalkerLong goFirst() {
        return inner.goFirst();
    }

    /**
     * Sets the current index to the last element.
     *
     * @return the inner IteratorWalker
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IteratorWalkerLong goLast() {
        return inner.goLast();
    }

    /**
     * Sets the current index to the specified position .
     *
     * @param index the index to set as the current position
     * @return IteratorWalker of the deepest ArrayTape of movie
     * @throws IndexOutOfBoundsException if the index is out of range
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IteratorWalkerLong goLeafIndex(int index) {
        return inner.goLeafIndex(index);
    }

    /**
     * Returns the current index position in the inner IteratorWalker.
     *
     * @return the current index position
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int getCurrentIndex() {
        return observer.getOffset(inner.getRelatedMovie()) + inner.getCurrentIndex();
    }

    /**
     * Returns the number of elements in the ArrayTape.
     *
     * @return the current size of own ArrayTape
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean hasRecord() {
        return inner.hasRecord();
    }
}
