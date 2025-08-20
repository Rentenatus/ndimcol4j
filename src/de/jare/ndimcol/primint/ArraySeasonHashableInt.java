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
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class ArraySeasonHashableInt extends ArraySeasonInt implements RentenatusHashable, StrategicHashableInt {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategyInt strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableInt() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyInt();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableInt(HashStrategyInt strategy) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableInt(final ScreenplayInt screenplay) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyInt();
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param screenplay the screenplay to be used for this season
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableInt(final ScreenplayInt screenplay, HashStrategyInt strategy) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyInt();
    }

    @Override
    public HashStrategyInt getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(HashStrategyInt strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(int a, int b) {
        return strategy.equals(a, b);
    }

    @Override
    public boolean equals(Object ob) {
        if (hashComputed && ob instanceof StrategicHashableInt) {
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
        de.jare.ndimcol.ref.IterTapeWalker<ArrayMovieInt> walker = data.softWalker();
        while (walker.hasNext()) {
            ArrayMovieInt nextMovie = walker.next();
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void added(int element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
    }

    @Override
    public boolean add(int element) {
        added(element);
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> col) {
        if (hashComputed && col.size() <= 128) {
            boolean warwas = false;
            for (int element : col) {
                warwas = add(element) || warwas;
            }
            return warwas;
        }
        hashComputed = false;
        return super.addAll(col);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Integer> col) {
        hashComputed = false;
        return super.addAll(index, col);
    }

    @Override
    public boolean addAt(int index, int element) {
        hashComputed = false;
        return super.addAt(index, element);
    }

    @Override
    public boolean addMovie(ArrayMovieInt movie) {
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
    public boolean addFirstFree(int element) {
        hashComputed = false;
        return super.addFirstFree(element);
    }

    @Override
    ArrayMovieInt buildInnerMovie(final int parentSize) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    void replaced(int index, int old, int element) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArraySeasonHashableInt emptyMovie(int initialCapacityOrZero) {
        ArraySeasonHashableInt ret = new ArraySeasonHashableInt(screenplay);
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
