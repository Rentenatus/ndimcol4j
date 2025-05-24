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
public class ArraySeasonHashable<T> extends ArraySeason<T> implements Hashable {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategie<T> strategie;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArraySeasonHashable() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategie<>();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategie to calculate hash code.
     */
    public ArraySeasonHashable(HashStrategie<T> strategie) {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = strategie;
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param screenplay the screenplay to be used for this season
     */
    public ArraySeasonHashable(final Screenplay screenplay) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategie<>();
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param screenplay the screenplay to be used for this season
     * @param strategie to calculate hash code.
     */
    public ArraySeasonHashable(final Screenplay screenplay, HashStrategie<T> strategie) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategie<>();
    }

    public HashStrategie<T> getStrategie() {
        return strategie;
    }

    public void setStrategie(HashStrategie<T> strategie) {
        this.strategie = strategie;
    }

    @Override
    public boolean add(T element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategie.hashCode(element));
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
            hashCode = combine(hashCode,
                    movie.size(),
                    movie.hashCode());
        }
        return super.addMovie(movie);
    }

    @Override
    public boolean addFirstFree(T element) {
        hashComputed = false;
        return super.addFirstFree(element);
    }

    public boolean equals(T a, T b) {
        return strategie.equals(a, b);
    }

    @Override
    public void clear() {
        this.hashCode = 0;
        this.hashComputed = true;
        super.clear();
    }

    /**
     * Here the tape are informed that private data or inner movie has been changed from outside.
     */
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    void replaced(int index, T old, T element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategie.hashCode(old),
                    strategie.hashCode(element));
        }
    }

    @Override
    public boolean removeAll(Collection<?> col) {
        boolean ret = super.removeAll(col);
        if (ret) {
            hashComputed = false;
        }
        return ret;
    }

    @Override
    public boolean retainAll(Collection<?> col) {
        boolean ret = super.retainAll(col);
        if (ret) {
            hashComputed = false;
        }
        return ret;
    }

    /**
     * Creates a new empty season with the same screenplay. The new movie is not a copy of this movie.
     *
     * @param initialCapacityOrZero not used
     * @return a new empty movie with the same screenplay
     */
    @Override
    public ArraySeasonHashable<T> emptyMovie(int initialCapacityOrZero) {
        ArraySeasonHashable<T> ret = new ArraySeasonHashable<>(screenplay);
        ret.setStrategie(this.getStrategie());
        return ret;
    }
}
