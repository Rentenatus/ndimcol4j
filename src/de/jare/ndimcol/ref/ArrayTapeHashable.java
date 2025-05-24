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
import java.util.List;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArrayTapeHashable<T> extends ArrayTape<T> implements Hashable {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategie<T> strategie;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArrayTapeHashable() {
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
    public ArrayTapeHashable(HashStrategie<T> strategie) {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = strategie;
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
        this.strategie = new HashStrategie<>();
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
        this.strategie = (original instanceof ArrayTapeHashable)
                ? ((ArrayTapeHashable) original).getStrategie()
                : new HashStrategie<>();
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
        this.strategie = new HashStrategie<>();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategie to calculate hash code.
     */
    public ArrayTapeHashable(List<T> list, HashStrategie<T> strategie) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategie = strategie;
    }

    public HashStrategie<T> getStrategie() {
        return strategie;
    }

    public void setStrategie(HashStrategie<T> strategie) {
        this.strategie = strategie;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof ArrayMovie<?>)) {
            return false;
        }
        ArrayMovie<?> movie = (ArrayMovie<?>) ob;
        if (size() != movie.size()) {
            return false;
        }
        IteratorWalker<?> mWalker = movie.softWalker();
        IterTapeWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            if (!strategie.equals(walker.next(), mWalker.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hashComputed) {
            return hashCode;
        }
        hashCode = 0;
        IterTapeWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            hashCode = combine(hashCode, strategie.hashCode(walker.next()));
        }
        hashComputed = true;
        return hashCode;
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
    public T set(int index, T element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategie.hashCode(get(index)),
                    strategie.hashCode(element));
        }
        return super.set(index, element);
    }

    @Override
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
        ret.setStrategie(this.getStrategie());
        return ret;
    }
}
