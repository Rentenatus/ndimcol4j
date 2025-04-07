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
 * @author Jansuch Rentenatus
 * @param <T>
 */
public interface IteratorWalker<T> {

    public boolean hasNext();

    public T removeForward();

    public T next();

    public boolean hasPrevious();

    public T previous();

    public T removeBackward();

    public T remove();

    public boolean add(T element);

    public boolean add(Collection<? extends T> col);

    /**
     * Resets the current index to the beginning .
     *
     * @return this
     */
    public IteratorWalker<T> goFirst();

    /**
     * Sets the current index to the last element.
     *
     * @return this
     */
    public IteratorWalker<T> goLast();

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
    public IteratorWalker<T> goLeafIndex(int index);

    public int getCurrentIndex();

    public int size();

    public boolean isEmpty();

    public boolean hasRecord();
}
