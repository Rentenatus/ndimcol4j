/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.ref.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArrayListHashable<T> extends ArrayList<T> implements RentenatusHashable, StrategicHashable<T> {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategy<T> strategy;

    /**
     * Constructs an empty ArrayListHashable with an initial capacity of ten and a default page size of thirty. The
     * update counter and trim countdown are also initialized.
     */
    public ArrayListHashable() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs an empty ArrayListHashable with an initial capacity of ten and a default page size of thirty.The
     * update counter and trim countdown are also initialized.
     *
     * @param strategy to calculate hash code.
     */
    public ArrayListHashable(HashStrategy<T> strategy) {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = strategy;
    }

    /**
     * Constructs an empty ArrayListHashable with the specified initial capacity and a default page size of thirty. The
     * update counter and trim countdown are also initialized.
     *
     * @param initialCapacityOrZero the initial capacity of the ArrayTape
     */
    public ArrayListHashable(int initialCapacityOrZero) {
        super(initialCapacityOrZero);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategy = new HashStrategy<>();
    }

    /**
     * Constructs a new ArrayListHashable that is a duplicate of the specified ArrayTape. This constructor copies the
     * elements from the given ArrayList and initializes the page size, update counter, and trim countdown based on the
     * original.
     *
     * @param original the ArrayTape to be duplicated
     */
    public ArrayListHashable(List<T> original) {
        super(original);
        this.hashCode = original.hashCode();
        this.hashComputed = true;
        this.strategy = (original instanceof StrategicHashable<?>)
                ? ((StrategicHashable<T>) original).getStrategy()
                : new HashStrategy<>();
    }

    /**
     * Constructs a new ArrayTape from the specified List.This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategy to calculate hash code.
     */
    public ArrayListHashable(List<T> list, HashStrategy<T> strategy) {
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
    public int hashCode() {
        if (hashComputed) {
            return hashCode;
        }
        hashCode = 0;
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            hashCode = combine(hashCode, strategy.hashCode(iter.next()));
        }
        hashComputed = true;
        return hashCode;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Collection<?>)) {
            return false;
        }
        if (hashComputed && ob instanceof StrategicHashable<?>) {
            return hashCode == ob.hashCode() && equalsCollection((Collection<?>) ob);
        }
        return equalsCollection((Collection<?>) ob);
    }

    @Override
    public boolean equalsCollection(Collection<?> col) {
        if (this == col) {
            return true;
        }
        if (size() != col.size()) {
            return false;
        }
        Iterator<?> iter = col.iterator();
        Iterator<T> walker = iterator();
        while (walker.hasNext()) {
            if (!equals(walker.next(), iter.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(T element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategy.hashCode(element));
        }
        return super.add(element);
    }

    @Override
    public void add(int index, T element) {
        hashComputed = false;
        super.add(index, element);
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
    public T set(int index, T element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategy.hashCode(get(index)),
                    strategy.hashCode(element));
        }
        return super.set(index, element);
    }

    public boolean equals(T a, Object b) {
        return strategy.equals(a, b);
    }

    @Override
    public void clear() {
        this.hashCode = 0;
        this.hashComputed = true;
        super.clear();
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
    public T remove(int index) {
        hashComputed = false;
        return super.remove(index);
    }

    @Override
    public T removeFirst() {
        hashComputed = false;
        return super.removeFirst();
    }

    @Override
    public T removeLast() {
        hashComputed = false;
        return super.removeLast();
    }

    @Override
    public boolean remove(Object o) {
        hashComputed = false;
        return super.remove(o);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        hashComputed = false;
        super.removeRange(fromIndex, toIndex);
    }
}
