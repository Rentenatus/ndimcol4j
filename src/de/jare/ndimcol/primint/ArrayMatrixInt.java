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
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
 public class ArrayMatrixInt {

    /**
     * Number of columns in the matrix.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private int cols;

    /**
     * Number of rows in the matrix.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private int rows;

    /**
     * Total number of elements (rows × cols).
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private int size;

    /**
     * Default initialization value for each cell.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private final int initValue;

    /**
     * Nested structure holding rows of elements.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    de.jare.ndimcol.ref.ArrayTape<ArrayTapeInt> content;

    /**
     * Flattened view of all elements for iteration and collection operations.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    ArraySeasonInt cover;

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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayMatrixInt(int cols, int rows, int initValue) {
        this.cols = cols;
        this.rows = rows;
        this.size = cols * rows;
        this.initValue = initValue;

        ArrayTapeInt template = new ArrayTapeInt(cols);
        for (int i = 0; i < cols; i++) {
            template.add(initValue);
        }
        this.content = new de.jare.ndimcol.ref.ArrayTape<>(rows);
        de.jare.ndimcol.ref.ArrayTape<ArrayMovieInt> s_content = new de.jare.ndimcol.ref.ArrayTape<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayTapeInt row = new ArrayTapeInt(cols);
            row.addMovie(template);
            this.content.add(row);
            s_content.add(row);
        }
        this.cover = new ArraySeasonInt(s_content, size);
    }

    /**
     * @return total number of elements in the matrix
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public int size() {
        return this.size;
    }

    /**
     * @return number of columns
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int getCols() {
        return cols;
    }

    /**
     * @return number of rows
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int get(int col, int row) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int set(int col, int row, int value) {
        return content.get(row).set(col, value);
    }

    /**
     * @return {@code true} if the matrix contains no elements
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks whether the matrix contains the given object.
     *
     * @param ob object to search for
     * @return {@code true} if found in any row
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean contains(int ob) {
        for (ArrayTapeInt row : content) {
            if (row.contains(ob)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return iterator over all elements (flattened view)
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public IteratorInt iterator() {
        return cover.iterator();
    }

    /**
     * @return array containing all elements
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public int[] toArray() {
        return cover.toArray();
    }

    /**
     * Copies elements into the provided array.
     *
     * @param arr
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public  int[] toArray(int[] arr) {
        return cover.toArray(arr);
    }

    /**
     * UnsupportedOperation
     *
     * @param element
     * @return -
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean add(int element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param ob
     * @return -
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean remove(int ob) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns {@code true} if this collection contains all of the elements in the specified collection.
     *
     * @param col collection to be checked for containment in this collection
     * @return {@code true} if this collection contains all of the elements in the specified collection
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean containsAll(Collection<?> col) {
        return cover.containsAll(col);
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean addAll(Collection<? extends Integer> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean removeAll(Collection<?> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * UnsupportedOperation
     *
     * @param col
     * @return -
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public boolean retainAll(Collection<?> col) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Clears the matrix, resetting dimensions to zero and removing all content.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    // Primitives don't overwrite anything. 
    public void clear() {
        this.cols = 0;
        this.rows = 0;
        this.size = 0;
        this.content = new de.jare.ndimcol.ref.ArrayTape<>();
        this.cover = new ArraySeasonInt();
    }

    /**
     * Converts the matrix into an {@link ArrayMovie} representation.
     *
     * @return cloned movie view of the matrix
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayMovieInt toArrayMovie() {
        return cover.cloneMovie();
    }

    /**
     * @return hash code based on the flattened cover view
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean equals(Object ob) {
        if (!(ob instanceof ArrayMatrixInt)) {
            if (!(ob instanceof Collection<?>)) {
                return false;
            }
            return equalsCollection((Collection<?>) ob);
        }
        return equalsMatrix((ArrayMatrixInt) ob);
    }

    /**
     * Equality check against a generic collection.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean equalsCollection(Collection<?> col) {
        return cover.equalsCollection(col);
    }

    /**
     * Equality check against another matrix.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean equalsMatrix(ArrayMatrixInt col) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean equals(int a, int b) {
        return _equals(a, b);
    }

    /**
     * Copies the elements of this movie to the specified array, starting at the specified offset.
     *
     * @param arr the array to copy the elements into
     * @param offset the offset in the array where to start copying
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public void copyToArray(int[] arr, int offset) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int debug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < rows; i++) {
            ArrayMovieInt episode = content.get(i);
            offset = episode.debug(out, prefix + "row[" + i + "]  ", offset);
        }
        return offset;
    }
}
