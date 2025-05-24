/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.Hashable;
import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArrayTapeHashable<T> extends ArrayTape<T> implements Hashable {

    private int hashCode = 10127;
    private boolean hashComputed = true;

    @Override
    public boolean add(T element) {
        if (hashComputed && element != null) {
            hashCode = combine(hashCode, element.hashCode());
        }
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends T> col) {
        if (hashComputed && col.size() <= 128) {
            boolean warwas = false;
            for (T element : col) {
                warwas = add(element) || warwas;
            }
            return warwas;
        }
        hashComputed = false;
        return super.addAll(col);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> col) {
        hashComputed = false;
        return super.addAll(index, col);
    }

    @Override
    public boolean addAt(int index, T element) {
        hashComputed = false;
        return super.addAt(index, element);
    }

    @Override
    public boolean addMovie(ArrayMovie<T> movie) {
        if (movie.isEmpty()) {
            return false;
        }
        if (hashComputed) {
            hashCode = combine(hashCode, movie.size(), movie.hashCode());
        }
        return super.addMovie(movie);
    }

}
