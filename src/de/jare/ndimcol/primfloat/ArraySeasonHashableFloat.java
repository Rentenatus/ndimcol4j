/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import java.util.Collection;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class ArraySeasonHashableFloat extends ArraySeasonFloat implements RentenatusHashable {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategieFloat strategie;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableFloat() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategieFloat();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategie to calculate hash code.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableFloat(HashStrategieFloat strategie) {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableFloat(final ScreenplayFloat screenplay) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategieFloat();
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param screenplay the screenplay to be used for this season
     * @param strategie to calculate hash code.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArraySeasonHashableFloat(final ScreenplayFloat screenplay, HashStrategieFloat strategie) {
        super(screenplay);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategieFloat();
    }

    public HashStrategieFloat getStrategie() {
        return strategie;
    }

    public void setStrategie(HashStrategieFloat strategie) {
        this.strategie = strategie;
    }

    @Override
    public boolean equals(float a, float b) {
        return strategie.equals(a, b);
    }

    @Override
    public int hashCode() {
        if (hashComputed) {
            return hashCode;
        }
        hashCode = 0;
        de.jare.ndimcol.ref.IterTapeWalker<ArrayMovieFloat> walker = data.softWalker();
        while (walker.hasNext()) {
            ArrayMovieFloat nextMovie = walker.next();
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void added(float element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategie.hashCode(element));
        }
    }

    @Override
    public boolean add(float element) {
        added(element);
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends Float> col) {
        if (hashComputed && col.size() <= 128) {
            boolean warwas = false;
            for (float element : col) {
                warwas = add(element) || warwas;
            }
            return warwas;
        }
        hashComputed = false;
        return super.addAll(col);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Float> col) {
        hashComputed = false;
        return super.addAll(index, col);
    }

    @Override
    public boolean addAt(int index, float element) {
        hashComputed = false;
        return super.addAt(index, element);
    }

    @Override
    public boolean addMovie(ArrayMovieFloat movie) {
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
    public boolean addFirstFree(float element) {
        hashComputed = false;
        return super.addFirstFree(element);
    }

    @Override
    ArrayMovieFloat buildInnerMovie(final int parentSize) {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    void replaced(int index, float old, float element) {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArraySeasonHashableFloat emptyMovie(int initialCapacityOrZero) {
        ArraySeasonHashableFloat ret = new ArraySeasonHashableFloat(screenplay);
        ret.setStrategie(this.getStrategie());
        return ret;
    }
}
