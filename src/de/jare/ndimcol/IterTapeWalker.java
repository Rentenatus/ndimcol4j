/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 * A IterTapeWalker allows for traversal over the elements in an ArrayTape in a linear fashion, providing methods to
 * move forward, backward, and to specific positions.
 *
 * @param <T> the type of elements in the ArrayTape
 */
public class IterTapeWalker<T> implements IteratorWalker<T> {

    ArrayTape<T> tape;
    private int currentIndex;
    private boolean forward;

    /**
     * Constructs a TapeWalker for the specified ArrayTape. The current index is initialized to the beginning of the
     * ArrayTape.
     *
     * @param tape the ArrayTape to be traversed
     */
    public IterTapeWalker(ArrayTape<T> tape) {
        this.tape = tape;
        this.currentIndex = 0;
        this.forward = true;
    }

    /**
     * Constructs a TapeWalker for the specified ArrayTape.The current index is initialized to the beginning of the
     * ArrayTape.
     *
     * @param tape the ArrayTape to be traversed
     * @param atIndex
     */
    public IterTapeWalker(ArrayTape<T> tape, int atIndex) {
        this.tape = tape;
        this.currentIndex = atIndex;
    }

    /**
     * Returns true if there are more elements when moving in the forward direction.
     *
     * @return true if the current index is less than the size of the ArrayTape
     */
    @Override
    public boolean hasNext() {
        return currentIndex < tape.size();
    }

    /**
     * Returns the next element in the ArrayTape and advances the current index. Throws an IndexOutOfBoundsException if
     * there are no more elements.
     *
     * @return the next element in the ArrayTape
     * @throws IndexOutOfBoundsException if there are no more elements in the tape
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more elements in the tape.");
        }
        forward = true;
        return tape.get(currentIndex++);
    }

    /**
     * Adds the specified element to the the ArrayTape at current index of Walker.
     *
     *
     * @param element the element to be added to the ArrayTape
     * @return true
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    @Override
    public boolean add(T element) {
        return tape.add(currentIndex, element);
    }

    /**
     * Removes the next element in the ArrayTape and advances the current index. Throws an IndexOutOfBoundsException if
     * there are no more elements.
     *
     * @return the next element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if there are no more elements in the tape
     */
    @Override
    public T removeForward() {
        return tape.removeAt(currentIndex);
    }

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
    @Override
    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    /**
     * Returns the previous element in the ArrayTape and moves the current index backward. Throws an
     * IndexOutOfBoundsException if there are no previous elements.
     *
     * @return the previous element in the ArrayTape
     * @throws IndexOutOfBoundsException if there are no previous elements in the tape
     */
    @Override
    public T previous() {
        if (!hasPrevious()) {
            throw new IndexOutOfBoundsException("No previous elements in the tape.");
        }
        forward = false;
        return tape.get(--currentIndex);
    }

    /**
     * Removes the previous element in the ArrayTape and moves the current index backward. Throws an
     * IndexOutOfBoundsException if there are no previous elements.
     *
     * @return the previous element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if there are no previous elements in the tape
     */
    @Override
    public T removeBackward() {
        return tape.removeAt(--currentIndex);
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
     * Resets the current index to the beginning of the ArrayTape.
     *
     * @return
     */
    @Override
    public IterTapeWalker<T> goFirst() {
        currentIndex = 0;
        forward = true;
        return this;
    }

    /**
     * Sets the current index to the last element in the ArrayTape.
     *
     * @return
     */
    @Override
    public IterTapeWalker<T> goLast() {
        currentIndex = tape.size() - 1;
        forward = false;
        return this;
    }

    /**
     * Sets the current index to the specified position in the ArrayTape.Throws an IndexOutOfBoundsException if the
     * index is out of range.
     *
     * @param index the index to set as the current position
     * @return
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index
     * >= tape.size())
     */
    @Override
    public IterTapeWalker<T> goLeafIndex(int index) {
        if (index < 0 || index >= tape.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + tape.size());
        }
        currentIndex = index;
        return this;
    }

    /**
     * Returns the current index position in the ArrayTape.
     *
     * @return the current index position
     */
    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the number of elements in the ArrayTape.
     *
     * @return the current size of own ArrayTape
     */
    @Override
    public int size() {
        return tape.size();
    }

    @Override
    public boolean isEmpty() {
        return tape.isEmpty();
    }

    @Override
    public boolean hasRecord() {
        return tape.hasRecord();
    }
}
