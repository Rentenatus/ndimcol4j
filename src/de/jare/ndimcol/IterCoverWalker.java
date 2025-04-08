/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.util.Collection;

/**
 *
 * @param <T> the type of elements in the ArrayTape
 */
public class IterCoverWalker<T> implements IteratorWalker<T> {

    IteratorWalker<T> inner;
    ArraySeason<T> observer;

    /**
     * Constructs a CoverWalker.
     *
     * @param inner the IteratorWalker to be cover.
     */
    public IterCoverWalker(ArraySeason<T> observer, IteratorWalker<T> inner) {
        this.inner = inner;
        this.observer = observer;
    }

    /**
     * Returns true if there are more elements when moving in the forward direction.
     *
     * @return true if the current index is less than the size of the ArrayTape
     */
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
    @Override
    public T next() {
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
    @Override
    public boolean add(T element) {
        if (inner.add(element)) {
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
    @Override
    public boolean add(Collection<? extends T> col) {
        if (inner.add(col)) {
            observer.updateCounter++;
            observer.size += col.size();
            return true;
        }
        return false;
    }

    @Override
    public    T set(T element) {
        T ret = inner.set( element);
        if (ret != null) {
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
    @Override
    public T removeForward() {
        T ret = inner.removeForward();
        if (ret != null) {
            observer.updateCounter++;
            observer.size--;
        }
        return ret;
    }

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
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
    @Override
    public T previous() {
        return inner.previous();
    }

    /**
     * Removes the previous element .
     *
     * @return the previous element that was removed from the inner IteratorWalker
     * @throws IndexOutOfBoundsException if there are no previous elements in the inner IteratorWalker
     */
    @Override
    public T removeBackward() {
        T ret = inner.removeBackward();
        if (ret != null) {
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
    @Override
    public T remove() {
        T ret = inner.remove();
        if (ret != null) {
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
    @Override
    public IteratorWalker<T> goFirst() {
        return inner.goFirst();
    }

    /**
     * Sets the current index to the last element.
     *
     * @return the inner IteratorWalker
     */
    @Override
    public IteratorWalker<T> goLast() {
        return inner.goLast();
    }

    /**
     * Sets the current index to the specified position .
     *
     * @param index the index to set as the current position
     * @return IteratorWalker of the deepest ArrayTape of movie
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public IteratorWalker<T> goLeafIndex(int index) {
        return inner.goLeafIndex(index);
    }

    /**
     * Returns the current index position in the inner IteratorWalker.
     *
     * @return the current index position
     */
    @Override
    public int getCurrentIndex() {
        return inner.getCurrentIndex();
    }

    /**
     * Returns the number of elements in the ArrayTape.
     *
     * @return the current size of own ArrayTape
     */
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
