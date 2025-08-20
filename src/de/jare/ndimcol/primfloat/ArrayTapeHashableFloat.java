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

import java.util.Collection;
import java.util.List;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class ArrayTapeHashableFloat extends ArrayTapeFloat implements RentenatusHashable, StrategicHashableFloat {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategyFloat strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyFloat();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat(HashStrategyFloat strategy) {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = strategy;
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param initialCapacityOrZero the initial capacity of the ArrayTape
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat(int initialCapacityOrZero) {
        super(initialCapacityOrZero);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyFloat();
    }

    /**
     * Constructs a new ArrayTape that is a duplicate of the specified ArrayTape. This constructor copies the elements
     * from the given ArrayTape and initializes the page size, update counter, and trim countdown based on the original.
     *
     * @param original the ArrayTape to be duplicated
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat(ArrayTapeFloat original) {
        super(original);
        this.hashCode = original.hashCode();
        this.hashComputed = true;
        this.strategy = (original instanceof StrategicHashableFloat)
                ? ((StrategicHashableFloat) original).getStrategy()
                : new HashStrategyFloat();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat(List<Float> list) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = new HashStrategyFloat();
    }

    /**
     * Constructs a new ArrayTape from the specified List.This constructor copies the elements from the given List and
 initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableFloat(List<Float> list, HashStrategyFloat strategy) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = strategy;
    }

    @Override
    public HashStrategyFloat getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(HashStrategyFloat strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object ob) {
        if (hashComputed && ob instanceof StrategicHashableFloat) {
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
        IterTapeWalkerFloat walker = softWalker();
        while (walker.hasNext()) {
            hashCode = combine(hashCode, strategy.hashCode(walker.next()));
        }
        hashComputed = true;
        return hashCode;
    }

    @Override
    public boolean add(float element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
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
    public float set(int index, float element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategy.hashCode(get(index)),
                    strategy.hashCode(element));
        }
        return super.set(index, element);
    }

    @Override
    public boolean equals(float a, float b) {
        return strategy.equals(a, b);
    }

    @Override
    public void clear() {
        this.hashCode = 0;
        this.hashComputed = true;
        super.clear();
    }

    /**
     * Here the tape are informed that private data has been changed from outside.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    public boolean removeAll(float element) {
        boolean ret = super.removeAll(element);
        if (ret) {
            hashComputed = false;
        }
        return ret;
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

    @Override
    protected float removeFast(int index) {
        hashComputed = false;
        return super.removeFast(index);
    }

    @Override
    public float removeTrim(int index) {
        hashComputed = false;
        return super.removeTrim(index);
    }

    @Override
    public ArrayTapeFloat splitInHalf() {
        hashComputed = false;
        return super.splitInHalf();
    }

    /**
     * Creates a new empty ArrayTape with the specified initial capacity. The new ArrayTape will have the same page size
     * as the original.
     *
     * @param initialCapacityOrZero the initial capacity of the new movie or zero if no initial capacity is needed
     * @return a new empty ArrayTape with the specified initial capacity
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArrayTapeFloat emptyMovie(int initialCapacityOrZero) {
        ArrayTapeHashableFloat ret = new ArrayTapeHashableFloat(initialCapacityOrZero);
        ret.setPage(this.getPage());
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
