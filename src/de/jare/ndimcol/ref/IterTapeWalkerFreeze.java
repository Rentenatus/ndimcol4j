/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.ConcurrentModificationException;

/**
 * A IterTapeWalkerFreeze allows for traversal over the elements in an ArrayTape in a linear fashion, while ensuring no
 * concurrent modifications occur during the iteration.
 *
 * @param <T> the type of elements in this walker
 */
public class IterTapeWalkerFreeze<T> extends IterTapeWalker<T> implements IteratorWalker<T> {

    private int initialUpdateCounter;

    /**
     * Constructs a TapeWalkerFreeze for the specified ArrayTape. The current index is initialized to the beginning of
     * the ArrayTape, and the initial update counter is set to the current update counter of the tape.
     *
     * @param tape the ArrayTape to be traversed
     */
    public IterTapeWalkerFreeze(ArrayTape<T> tape) {
        super(tape);
        this.initialUpdateCounter = tape.getUpdateCounter();
    }

    /**
     * Returns true if there are more elements when moving in the forward direction. Checks for concurrent modifications
     * before proceeding.
     *
     * @return true if the current index is less than the size of the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public boolean hasNext() {
        checkForConcurrentModification();
        return super.hasNext();
    }

    /**
     * Returns the next element in the ArrayTape and advances the current index. Checks for concurrent modifications
     * before proceeding.
     *
     * @return the next element in the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public T next() {
        checkForConcurrentModification();
        return super.next();
    }

    /**
     * Removes the next element in the ArrayTape and advances the current index. Checks for concurrent modifications
     * before proceeding and updates the initial update counter after removal.
     *
     * @return the next element that was removed from the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public T removeForward() {
        checkForConcurrentModification();
        T ret = super.removeForward();
        this.initialUpdateCounter = tape.getUpdateCounter();
        return ret;
    }

    /**
     * Returns true if there are more elements when moving in the backward direction. Checks for concurrent
     * modifications before proceeding.
     *
     * @return true if the current index is greater than 0
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public boolean hasPrevious() {
        checkForConcurrentModification();
        return super.hasPrevious();
    }

    /**
     * Returns the previous element in the ArrayTape and moves the current index backward. Checks for concurrent
     * modifications before proceeding.
     *
     * @return the previous element in the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public T previous() {
        checkForConcurrentModification();
        return super.previous();
    }

    /**
     * Removes the previous element in the ArrayTape and moves the current index backward. Checks for concurrent
     * modifications before proceeding and updates the initial update counter after removal.
     *
     * @return the previous element that was removed from the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    @Override
    public T removeBackward() {
        checkForConcurrentModification();
        T ret = super.removeBackward();
        this.initialUpdateCounter = tape.getUpdateCounter();
        return ret;
    }

    /**
     * Checks for concurrent modifications of the ArrayTape. If the update counter has changed since the
     * TapeWalkerFreeze was created, throws a ConcurrentModificationException.
     *
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    private void checkForConcurrentModification() {
        if (initialUpdateCounter != tape.getUpdateCounter()) {
            throw new ConcurrentModificationException("ArrayTape was modified during iteration.");
        }
    }
}
