/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import java.util.ConcurrentModificationException;

/**
 * A IterTapeWalkerFreeze allows for traversal over the elements in an ArrayTape in a linear fashion, while ensuring no
 * concurrent modifications occur during the iteration.
 *
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class IterTapeWalkerFreezeFloat extends IterTapeWalkerFloat implements IteratorWalkerFloat {

    private int initialUpdateCounter;

    /**
     * Constructs a TapeWalkerFreeze for the specified ArrayTape. The current index is initialized to the beginning of
     * the ArrayTape, and the initial update counter is set to the current update counter of the tape.
     *
     * @param tape the ArrayTape to be traversed
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public IterTapeWalkerFreezeFloat(ArrayTapeFloat tape) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public float next() {
        checkForConcurrentModification();
        return super.next();
    }

    /**
     * Removes the element in the ArrayTape and changes the current index.
     *
     * Checks for concurrent modifications before proceeding and updates the initial update counter after removal.
     *
     * @return the element that was removed from the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public float removeForward() {
        checkForConcurrentModification();
        float ret = super.removeForward();
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public float previous() {
        checkForConcurrentModification();
        return super.previous();
    }

    /**
     * Removes the element in the ArrayTape . Checks for concurrent modifications before proceeding and updates the
     * initial update counter after removal.
     *
     * @return the element that was removed from the ArrayTape
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public float removeBackward() {
        checkForConcurrentModification();
        float ret = super.removeBackward();
        this.initialUpdateCounter = tape.getUpdateCounter();
        return ret;
    }

    /**
     * Checks for concurrent modifications of the ArrayTape. If the update counter has changed since the
     * TapeWalkerFreeze was created, throws a ConcurrentModificationException.
     *
     * @throws ConcurrentModificationException if the ArrayTape has been modified during iteration
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private void checkForConcurrentModification() {
        if (initialUpdateCounter != tape.getUpdateCounter()) {
            throw new ConcurrentModificationException("ArrayTape was modified during iteration.");
        }
    }
}
