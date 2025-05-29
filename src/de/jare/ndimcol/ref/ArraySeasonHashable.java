/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.Collection;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArraySeasonHashable<T> extends ArraySeason<T> implements RentenatusHashable, StrategicHashable<T> {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategy<T> strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArraySeasonHashable() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategy to calculate hash code.
     */
    public ArraySeasonHashable(HashStrategy<T> strategy) {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = strategy;
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
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param screenplay the screenplay to be used for this season
     * @param strategy to calculate hash code.
     */
    public ArraySeasonHashable(final Screenplay screenplay, HashStrategy<T> strategy) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategy<>();
    }

    @Override
    public HashStrategy<T> getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(HashStrategy<T> strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(T a, Object b) {
        return strategy.equals(a, b);
    }

    @Override
    //prim:public boolean equals(Object ob) {
    public boolean equals(Object ob) {
        //noprim.start  
        if (this == ob) {
            return true;
        }
        //noprim.ende  
        //prim:if (hashComputed && ob instanceof StrategicHashable_APPEND_) {
        if (hashComputed && ob instanceof StrategicHashable<?>) {
            return hashCode == ob.hashCode() && super.equals(ob);
        }
        return super.equals(ob);
    }

    @Override
    public int hashCode() {
        if (hashComputed) {
            return hashCode;
        }
        hashCode = 0;
        IterTapeWalker<ArrayMovie<T>> walker = data.softWalker();
        while (walker.hasNext()) {
            ArrayMovie<T> nextMovie = walker.next();
            hashCode = combine(hashCode,
                    nextMovie.size(),
                    nextMovie.hashCode());
        }
        hashComputed = true;
        return hashCode;
    }

    /**
     * Here the tape are informed that private data has been changed.
     */
    @Override
    void added(T element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
    }

    @Override
    public boolean add(T element) {
        added(element);
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

    @Override
    ArrayMovie<T> buildInnerMovie(final int parentSize) {
        return screenplay.buildMovieHashable(parentSize);
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
                    strategy.hashCode(old),
                    strategy.hashCode(element));
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
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
