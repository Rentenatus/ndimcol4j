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
 * A IterTapeWalker allows for traversal over the elements in an ArrayTape in a linear fashion, providing methods to
 * move forward, backward, and to specific positions.
 *
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class IterTapeWalkerInt implements IteratorWalkerInt {

    final ArrayTapeInt tape;
    private int currentIndex;
    private boolean forward;

    /**
     * Constructs a TapeWalker for the specified ArrayTape. The current index is initialized to the beginning of the
     * ArrayTape.
     *
     * @param tape the ArrayTape to be traversed
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public IterTapeWalkerInt(ArrayTapeInt tape) {
        this.tape = tape;
        this.currentIndex = 0;
        this.forward = true;
    }

    /**
     * Constructs a TapeWalker for the specified ArrayTape.The current index is initialized to the beginning of the
     * ArrayTape.
     *
     * @param tape the ArrayTape to be traversed
     * @param atIndex the index to start at
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public IterTapeWalkerInt(ArrayTapeInt tape, int atIndex) {
        this.tape = tape;
        this.currentIndex = atIndex;
    }

    /**
     * Returns the ArrayTape that this walker is traversing.
     *
     * @return the ArrayTape being traversed
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArrayMovieInt getRelatedMovie() {
        return this.tape;
    }

    /**
     * Returns true if there are more elements when moving in the forward direction.
     *
     * @return true if the current index is less than the size of the ArrayTape
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more elements in the tape.");
        }
        forward = true;
        return tape.get(currentIndex++);
    }

    /**
     * Adds the specified element to the ArrayTape at current index of Walker.
     *
     *
     * @param element the element to be added to the ArrayTape
     * @return true
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(int element) {
        return tape.addAt(currentIndex, element);
    }

    /**
     * Adds a collection to the current tape.
     *
     * @param col collection containing elements to be added
     * @return true if the addition is successful
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(Collection<? extends Integer> col) {
        return tape.addAll(currentIndex, col);
    }

    @Override
    public int set(int element) {
        return tape.set(currentIndex, element);
    }

    /**
     * Removes the next element in the ArrayTape and advances the current index. Throws an IndexOutOfBoundsException if
     * there are no more elements.
     *
     * @return the next element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if there are no more elements in the tape
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int removeForward() {
        return tape.removeAt(currentIndex);
    }

    /**
     * Returns true if there are more elements when moving in the backward direction.
     *
     * @return true if the current index is greater than 0
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int previous() {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int removeBackward() {
        return tape.removeAt(--currentIndex);
    }

    /**
     * Removes the current element.
     *
     * @return the current element that was removed
     * @throws IndexOutOfBoundsException if there are no previous elements in the tape
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int remove() {
        if (forward) {
            return removeForward();
        } else {
            return removeBackward();
        }
    }

    /**
     * Resets the current index to the beginning of the ArrayTape.
     *
     * @return this walker
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IterTapeWalkerInt goFirst() {
        currentIndex = 0;
        forward = true;
        return this;
    }

    /**
     * Sets the current index to the last element in the ArrayTape.
     *
     * @return this walker
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IterTapeWalkerInt goLast() {
        currentIndex = tape.size() - 1;
        forward = false;
        return this;
    }

    public IteratorWalkerInt gotoIndex(int index, boolean headForward) {
        if (index < 0 || index >= tape.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + tape.size());
        }
        currentIndex = index;
        forward = headForward;
        return this;
    }

    /**
     * Sets the current index to the specified position in the ArrayTape.Throws an IndexOutOfBoundsException if the
     * index is out of range.
     *
     * @param index the index to set as the current position
     * @return this walker
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public IterTapeWalkerInt goLeafIndex(int index) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the number of elements in the ArrayTape.
     *
     * @return the current size of own ArrayTape
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public int size() {
        return tape.size();
    }

    /**
     * Returns true if the ArrayTape is empty.
     *
     * @return true if the ArrayTape is empty
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean isEmpty() {
        return tape.isEmpty();
    }

    /**
     * Returns true if the ArrayTape has a record.
     *
     * @return true if the ArrayTape has a record
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean hasRecord() {
        return tape.hasRecord();
    }
}
