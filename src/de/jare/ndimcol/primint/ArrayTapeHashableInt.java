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
import java.util.List;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class ArrayTapeHashableInt extends ArrayTapeInt implements RentenatusHashable, StrategicHashableInt {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategyInt strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableInt() {
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
    public ArrayTapeHashableInt(HashStrategyInt strategy) {
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
    public ArrayTapeHashableInt(int initialCapacityOrZero) {
        super(initialCapacityOrZero);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategyInt();
    }

    /**
     * Constructs a new ArrayTape that is a duplicate of the specified ArrayTape. This constructor copies the elements
     * from the given ArrayTape and initializes the page size, update counter, and trim countdown based on the original.
     *
     * @param original the ArrayTape to be duplicated
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableInt(ArrayTapeInt original) {
        super(original);
        this.hashCode = original.hashCode();
        this.hashComputed = true;
        this.strategy = (original instanceof StrategicHashableInt)
                ? ((StrategicHashableInt) original).getStrategy()
                : new HashStrategyInt();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableInt(List<Integer> list) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = new HashStrategyInt();
    }

    /**
     * Constructs a new ArrayTape from the specified List.This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategy to calculate hash code.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableInt(List<Integer> list, HashStrategyInt strategy) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = strategy;
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
        IterTapeWalkerInt walker = softWalker();
        while (walker.hasNext()) {
            hashCode = combine(hashCode, strategy.hashCode(walker.next()));
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

    @Override
    public boolean add(int element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
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
    public int set(int index, int element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategy.hashCode(get(index)),
                    strategy.hashCode(element));
        }
        return super.set(index, element);
    }

    @Override
    public boolean equals(int a, int b) {
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
    public boolean removeAll(int element) {
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
    protected int removeFast(int index) {
        hashComputed = false;
        return super.removeFast(index);
    }

    @Override
    public int removeTrim(int index) {
        hashComputed = false;
        return super.removeTrim(index);
    }

    @Override
    public ArrayTapeInt splitInHalf() {
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
    public ArrayTapeInt emptyMovie(int initialCapacityOrZero) {
        ArrayTapeHashableInt ret = new ArrayTapeHashableInt(initialCapacityOrZero);
        ret.setPage(this.getPage());
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
