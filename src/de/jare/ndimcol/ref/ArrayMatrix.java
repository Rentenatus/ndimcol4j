/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import static de.jare.ndimcol.ref.HashStrategy._equals;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this movie
 */
public class ArrayMatrix<T> implements Collection<T> {

    private int cols;
    private int rows;
    private int size;
    private final T initValue;
    ArrayTape<ArrayTape<T>> content;
    ArraySeason<T> cover;

    public ArrayMatrix(int cols, int rows, T initValue) {
        this.cols = cols;
        this.rows = rows;
        this.size = cols * rows;
        this.initValue = initValue;

        ArrayTape<T> template = new ArrayTape<>(cols);
        for (int i = 0; i < cols; i++) {
            template.add(initValue);
        }
        this.content = new ArrayTape<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayTape<T> row = new ArrayTape<>(cols);
            row.addMovie(template);
            this.content.add(row);
        }
        this.cover = new ArraySeason(content, size);
    }

    @Override
    public int size() {
        return this.size;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public T get(int col, int row) {
        return content.get(row).get(col);
    }

    public T set(int col, int row, T value) {
        return content.get(row).set(col, value);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object ob) {
        for (ArrayTape<T> row : content) {
            if (row.contains(ob)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return cover.iterator();
    }

    @Override
    public Object[] toArray() {
        return cover.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        return cover.toArray(arr);
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> col) {
        return cover.containsAll(col);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        this.cols = 0;
        this.rows = 0;
        this.size = 0;
        this.content = new ArrayTape<>();
        this.cover = new ArraySeason(content, size);
    }

    public ArrayMovie<T> toArrayMovie() {
        return cover.cloneMovie();
    }

    @Override
    public int hashCode() {
        return cover.hashCode();
    }

    @Override
    //prim:public boolean equals(Object ob) {
    public boolean equals(Object ob) {
        //noprim.start  
        if (this == ob) {
            return true;
        }
        //noprim.ende  
        if (!(ob instanceof ArrayMatrix<?>)) {
            if (!(ob instanceof Collection<?>)) {
                return false;
            }
            return equalsCollection((Collection<?>) ob);
        }
        return equalsMatrix((ArrayMatrix<?>) ob);
    }

    public boolean equalsCollection(Collection<?> col) {
        return cover.equalsCollection(col);
    }

    public boolean equalsMatrix(ArrayMatrix<?> col) {
        if (this == col) {
            return true;
        }
        if (col == null) {
            return false;
        }
        if (getRows() != col.getRows() && getCols() != col.getCols()) {
            return false;
        }
        return cover.equalsCollection(col);
    }

    public boolean equals(T a, Object b) {
        return _equals(a, b);
    }

    /**
     * Copies the elements of this movie to the specified array, starting at the specified offset.
     *
     * @param arr the array to copy the elements into
     * @param offset the offset in the array where to start copying
     */
    public void copyToArray(Object[] arr, int offset) {
        cover.copyToArray(arr, offset);
    }

    public int debug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < rows; i++) {
            ArrayMovie<T> episode = content.get(i);
            offset = episode.debug(out, prefix + "row[" + i + "]  ", offset);
        }
        return offset;
    }
}
