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
 * * A two-dimensional generic matrix implementation backed by nested {@link ArrayTape} structures.
 * <p>
 * The matrix is organized into rows and columns, with each cell initialized to a given value. Internally, the matrix
 * content is stored as an {@code ArrayTape} of {@code ArrayTape<T>} objects, representing rows of elements. A flattened
 * {@link ArraySeason} view provides iteration and collection-level operations across all elements.
 * </p>
 *
 * <h2>Key Features</h2>
 * <ul>
 * <li>Stores elements in a fixed-size grid defined by {@code rows} and {@code cols}.</li>
 * <li>Provides indexed access via {@link #get(int, int)} and {@link #set(int, int, Object)}.</li>
 * <li>Implements the {@link Collection} interface for compatibility with Java collections.</li>
 * <li>Supports equality checks against other {@code ArrayMatrix} instances or generic collections.</li>
 * <li>Offers utility methods for copying, debugging, and converting to {@link ArrayMovie} views.</li>
 * </ul>
 *
 * <h2>Limitations</h2>
 * <ul>
 * <li>Modification methods defined by {@link Collection} (e.g. {@code add}, {@code remove}) are not supported and will
 * throw {@link UnsupportedOperationException}.</li>
 * <li>Matrix dimensions are fixed at construction; resizing requires {@link #clear()} or creating a new instance.</li>
 * </ul>
 *
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements stored in the matrix
 */
//prim: public class ArrayMatrix_APPEND_ {
public class ArrayMatrix<T> implements Collection<T>, Iterable<T> {

    /**
     * Number of columns in the matrix.
     */
    private int cols;

    /**
     * Number of rows in the matrix.
     */
    private int rows;

    /**
     * Total number of elements (rows × cols).
     */
    private int size;

    /**
     * Default initialization value for each cell.
     */
    private final T initValue;

    /**
     * Nested structure holding rows of elements.
     */
    de.jare.ndimcol.ref.ArrayTape<ArrayTape<T>> content;

    /**
     * Flattened view of all elements for iteration and collection operations.
     */
    ArraySeason<T> cover;

    /**
     * Constructs a new matrix with the given dimensions and initialization value.
     * <p>
     * Each cell is pre-filled with {@code initValue}.
     * </p>
     *
     * @param cols number of columns
     * @param rows number of rows
     * @param initValue default value for all cells
     */
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
        ArrayTape<ArrayMovie<T>> s_content = new ArrayTape<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayTape<T> row = new ArrayTape<>(cols);
            row.addMovie(template);
            this.content.add(row);
            s_content.add(row);
        }
        //prim:this.cover = new ArraySeason_APPEND_(s_content, size);
        this.cover = new ArraySeason(s_content, size);
    }

    /**
     * @return total number of elements in the matrix
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retrieves the element at the specified column and row.
     *
     * @param col column index
     * @param row row index
     * @return element at the given position
     */
    public T get(int col, int row) {
        return content.get(row).get(col);
    }

    /**
     * Replaces the element at the specified position.
     *
     * @param col column index
     * @param row row index
     * @param value new value to set
     * @return the previous value at that position
     */
    public T set(int col, int row, T value) {
        return content.get(row).set(col, value);
    }

    /**
     * @return {@code true} if the matrix contains no elements
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks whether the matrix contains the given object.
     *
     * @param ob object to search for
     * @return {@code true} if found in any row
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean contains(Object ob) {
        for (ArrayTape<T> row : content) {
            if (row.contains(ob)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return iterator over all elements (flattened view)
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public Iterator<T> iterator() {
        return cover.iterator();
    }

    /**
     * @return array containing all elements
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public Object[] toArray() {
        return cover.toArray();
    }

    /**
     * Copies elements into the provided array.
     *
     * @param arr
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    //prim:public  _PRIM_[] toArray(_PRIM_[] arr) {
    public <T> T[] toArray(T[] arr) {
        return cover.toArray(arr);
    }

    /**
     * UnsupportedOperation
     *
     * @param element
     * @return -
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean add(T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param ob
     * @return -
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean remove(Object ob) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns {@code true} if this collection contains all of the elements in the specified collection.
     *
     * @param col collection to be checked for containment in this collection
     * @return {@code true} if this collection contains all of the elements in the specified collection
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean containsAll(Collection<?> col) {
        return cover.containsAll(col);
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean addAll(Collection<? extends T> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean removeAll(Collection<?> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public boolean retainAll(Collection<?> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Clears the matrix, resetting dimensions to zero and removing all content.
     */
    //prim:// Primitives don't overwrite anything. 
    @Override
    public void clear() {
        this.cols = 0;
        this.rows = 0;
        this.size = 0;
        this.content = new ArrayTape<>();
        this.cover = new ArraySeason<>();
    }

    /**
     * Converts the matrix into an {@link ArrayMovie} representation.
     *
     * @return cloned movie view of the matrix
     */
    public ArrayMovie<T> toArrayMovie() {
        return cover.cloneMovie();
    }

    /**
     * @return hash code based on the flattened cover view
     */
    @Override
    public int hashCode() {
        return cover.hashCode();
    }

    /**
     * Compares this matrix with another object for equality.
     * <p>
     * Supports comparison with:
     * <ul>
     * <li>Another {@code ArrayMatrix} (row/column dimensions and content must match).</li>
     * <li>A generic {@link Collection} (content must match).</li>
     * </ul>
     * </p>
     *
     * @param ob object to compare
     * @return {@code true} if equal
     */
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

    /**
     * Equality check against a generic collection.
     */
    public boolean equalsCollection(Collection<?> col) {
        return cover.equalsCollection(col);
    }

    /**
     * Equality check against another matrix.
     */
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
        return cover.equals(col.cover);
    }

    /**
     * Equality check for elements.
     *
     * @param a element of type T
     * @param b other element
     * @return true, if both are equals
     */
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

    /**
     * Prints a debug representation of the matrix to the given stream.
     *
     * @param out output stream
     * @param prefix prefix string for each row
     * @param offset starting offset for debug output
     * @return updated offset after printing
     */
    public int debug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < rows; i++) {
            ArrayMovie<T> episode = content.get(i);
            offset = episode.debug(out, prefix + "row[" + i + "]  ", offset);
        }
        return offset;
    }
}
