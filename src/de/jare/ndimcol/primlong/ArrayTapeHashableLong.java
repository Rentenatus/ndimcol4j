/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import java.util.Collection;
import java.util.List;
import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class ArrayTapeHashableLong extends ArrayTapeLong implements RentenatusHashable {

    private int hashCode;
    private boolean hashComputed;
    protected HashStrategieLong strategie;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong() {
        super();
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategieLong();
    }

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty.The update
     * counter and trim countdown are also initialized.
     *
     * @param strategie to calculate hash code.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong(HashStrategieLong strategie) {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong(int initialCapacityOrZero) {
        super(initialCapacityOrZero);
        this.hashCode = 0;
        this.hashComputed = true;
        this.strategie = new HashStrategieLong();
    }

    /**
     * Constructs a new ArrayTape that is a duplicate of the specified ArrayTape. This constructor copies the elements
     * from the given ArrayTape and initializes the page size, update counter, and trim countdown based on the original.
     *
     * @param original the ArrayTape to be duplicated
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong(ArrayTapeLong original) {
        super(original);
        this.hashCode = original.hashCode();
        this.hashComputed = true;
        this.strategie = (original instanceof ArrayTapeHashableLong)
                ? ((ArrayTapeHashableLong) original).getStrategie()
                : new HashStrategieLong();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong(List<Long> list) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategie = new HashStrategieLong();
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     * @param strategie to calculate hash code.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayTapeHashableLong(List<Long> list, HashStrategieLong strategie) {
        super(list);
        this.hashCode = 10127;
        this.hashComputed = false;
        this.strategie = strategie;
    }

    public HashStrategieLong getStrategie() {
        return strategie;
    }

    public void setStrategie(HashStrategieLong strategie) {
        this.strategie = strategie;
    }

    @Override
    public int hashCode() {
        if (hashComputed) {
            return hashCode;
        }
        hashCode = 0;
        IterTapeWalkerLong walker = softWalker();
        while (walker.hasNext()) {
            hashCode = combine(hashCode, strategie.hashCode(walker.next()));
        }
        hashComputed = true;
        return hashCode;
    }

    @Override
    public boolean add(long element) {
        if (hashComputed) {
            hashCode = combine(hashCode, strategie.hashCode(element));
        }
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
    public long set(int index, long element) {
        if (hashComputed) {
            hashCode = replace(hashCode,
                    size() - 1 - index,
                    strategie.hashCode(get(index)),
                    strategie.hashCode(element));
        }
        return super.set(index, element);
    }

    @Override
    public boolean equals(long a, long b) {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    void deepChanged() {
        hashComputed = false;
    }

    @Override
    public boolean removeAll(long element) {
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
    protected long removeFast(int index) {
        hashComputed = false;
        return super.removeFast(index);
    }

    @Override
    public long removeTrim(int index) {
        hashComputed = false;
        return super.removeTrim(index);
    }

    @Override
    public ArrayTapeLong splitInHalf() {
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
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArrayTapeLong emptyMovie(int initialCapacityOrZero) {
        ArrayTapeHashableLong ret = new ArrayTapeHashableLong(initialCapacityOrZero);
        ret.setPage(this.getPage());
        ret.setStrategie(this.getStrategie());
        return ret;
    }
}
