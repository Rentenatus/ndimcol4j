/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.Collection;
import java.util.List;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArrayTapeHashable<T> extends ArrayTape<T> implements RentenatusHashable, StrategicHashable<T> {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategy<T> strategy;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArrayTapeHashable() {
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
    public ArrayTapeHashable(HashStrategy<T> strategy) {
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
    public ArrayTapeHashable(int initialCapacityOrZero) {
        super(initialCapacityOrZero);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs a new ArrayTape that is a duplicate of the specified ArrayTape. This constructor copies the elements
     * from the given ArrayTape and initializes the page size, update counter, and trim countdown based on the original.
     *
     * @param original the ArrayTape to be duplicated
     */
    public ArrayTapeHashable(ArrayTape<T> original) {
        super(original);
        this.hashCode = original.hashCode();
        this.hashComputed = true;
        //prim:this.strategy = (original instanceof StrategicHashable_APPEND_)
        this.strategy = (original instanceof StrategicHashable<?>)
                //prim:? ((StrategicHashable_APPEND_) original).getStrategy()
                ? ((StrategicHashable<T>) original).getStrategy()
                : new HashStrategy<>();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
    public ArrayTapeHashable(List<T> list) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs a new ArrayTape from the specified List.This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategy to calculate hash code.
     */
    public ArrayTapeHashable(List<T> list, HashStrategy<T> strategy) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategy = strategy;
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
        IterTapeWalker<T> walker = softWalker();
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
    public int combineListHash(int prevHash) {
        return combine(prevHash, size(), hashCode());
    }

    @Override
    public boolean add(T element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
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
    public T set(int index, T element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategy.hashCode(get(index)),
                    strategy.hashCode(element));
        }
        return super.set(index, element);
    }

    @Override
    public boolean equals(T a, Object b) {
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
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    public boolean removeAll(Object element) {
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
    protected T removeFast(int index) {
        hashComputed = false;
        return super.removeFast(index);
    }

    @Override
    public T removeTrim(int index) {
        hashComputed = false;
        return super.removeTrim(index);
    }

    @Override
    public ArrayTape<T> splitInHalf() {
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
    @Override
    public ArrayTape<T> emptyMovie(int initialCapacityOrZero) {
        ArrayTapeHashable<T> ret = new ArrayTapeHashable<>(initialCapacityOrZero);
        ret.setPage(this.getPage());
        ret.setStrategy(this.getStrategy());
        return ret;
    }
}
