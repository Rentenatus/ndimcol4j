/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.io.PrintStream;
import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T>
 */
public interface ArrayMovie<T> extends Collection<T> {

    public T first();

    public T last();

    public boolean add(int index, T element);

    public T get(int index);

    public T removeAt(int index);

    public ArrayMovie<T> splitInHalf();

    public IteratorWalker<T> softWalker();

    public IteratorWalker<T> leafWalker(int atIndex);

    public int indexOf(Object element);

    public void copyToArray(Object[] array, int offset);

    public void splitOrGlue();

    public int debbug(PrintStream out, String prefix, int offset);

    public default int debbug(PrintStream out, String prefix) {
        return debbug(out, prefix, 0);
    }

}
