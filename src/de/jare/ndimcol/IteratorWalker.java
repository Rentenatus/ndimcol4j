/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

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

    public IteratorWalker<T> goFirst();

    public IteratorWalker<T> goLast();

    public IteratorWalker<T> goLeafIndex(int index);

    public int getCurrentIndex();

    public int size();

    public boolean isEmpty();

    public boolean hasRecord();
}
