/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import java.util.Collection;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class ArraySeasonHashableLong extends ArraySeasonLong implements RentenatusHashable, StrategicHashableLong {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategyLong strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableLong() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyLong();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableLong(HashStrategyLong strategy) {
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
    public ArraySeasonHashableLong(final ScreenplayLong screenplay) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyLong();
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
    public ArraySeasonHashableLong(final ScreenplayLong screenplay, HashStrategyLong strategy) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyLong();
    }

    @Override
    public HashStrategyLong getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(HashStrategyLong strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(long a, long b) {
        return strategy.equals(a, b);
    }

    @Override
    public boolean equals(Object ob) {
        if (hashComputed && ob instanceof StrategicHashableLong) {
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
        de.jare.ndimcol.ref.IterTapeWalker<ArrayMovieLong> walker = data.softWalker();
        while (walker.hasNext()) {
            ArrayMovieLong nextMovie = walker.next();
            hashCode = combine(hashCode,
                    nextMovie.size(),
                    nextMovie.hashCode());
        }
        hashComputed = true;
        return hashCode;
    }

    /**
     * Incorporates the hash of this list into an existing rolling hash value.
     * <p>
     * The method applies the list's {@code hashCode()} at the position corresponding to its current {@code size()},
     * using the rolling-hash combination rule:
     *
     * <pre>
     *     h' = (prevHash * 7^size() + hashCode()) mod 2^30
     * </pre>
     *
     * This allows the list to contribute a single aggregated hash value while preserving positional semantics within a
     * larger hash structure.
     *
     * @param prevHash the previously accumulated hash value
     * @return the updated hash after incorporating this list's hash
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int combineListHash(int prevHash) {
        return combine(prevHash, size(), hashCode());
    }

    /**
     * Here the tape are informed that private data has been changed.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void added(long element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
    }

    @Override
    public boolean add(long element) {
        added(element);
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends Long> col) {
        if (hashComputed && col.size() <= 128) {
            boolean warwas = false;
            for (long element : col) {
                warwas = add(element) || warwas;
            }
            return warwas;
        }
        hashComputed = false;
        return super.addAll(col);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Long> col) {
        hashComputed = false;
        return super.addAll(index, col);
    }

    @Override
    public boolean addAt(int index, long element) {
        hashComputed = false;
        return super.addAt(index, element);
    }

    @Override
    public boolean addMovie(ArrayMovieLong movie) {
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
    public boolean addFirstFree(long element) {
        hashComputed = false;
        return super.addFirstFree(element);
    }

    @Override
    ArrayMovieLong buildInnerMovie(final int parentSize) {
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
    void replaced(int index, long old, long element) {
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
    public ArraySeasonHashableLong emptyMovie(int initialCapacityOrZero) {
        ArraySeasonHashableLong ret = new ArraySeasonHashableLong(screenplay);
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
