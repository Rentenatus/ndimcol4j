/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.io.PrintStream;
import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T>
 */
public interface ArrayMovie<T> extends Collection<T> {

    /**
     * Get the first element of the movie.
     *
     * @return the first element of the movie
     * @throws java.lang.IndexOutOfBoundsException if the list is empty
     * @see #last()
     * @see #get(int)
     * @see #removeAt(int)
     * @see #size()
     */
    public T first();

    /**
     * Get the last element of the movie.
     *
     * @return the last element of the movie
     * @throws java.lang.IndexOutOfBoundsException if the list is empty
     * @see #first()
     * @see #get(int)
     * @see #removeAt(int)
     * @see #size()
     */
    public T last();

    /**
     * Inserts the specified element at the specified position in this list . Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    public boolean addAt(int index, T element);

    public default void add(int index, T element) {
        addAt(index,element);
    }
    /**
     * Appends the specified element to the end of this list .
     *
     * @param element element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    public boolean add(T element);

    /**
     * Get the element at the specified index.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T get(int index);

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to remove
     * @return the element or null
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     * @see #remove(Object)
     */
    public T removeAt(int index);

    /**
     * Splits this movie into two movies. This movie contains the first half of the elements, and the second movie
     * contains the second half of the elements. Half is to be understood colloquially; it is not guaranteed that the
     * size of both parts is the same.
     *
     * @return a new movie containing the second half of the elements or null, if this movie is to small for splitting
     */
    public ArrayMovie<T> splitInHalf();

    /**
     * Returns a simple shared IteratorWalker for this movie. This simple iterator can not be used parallel.
     *
     * @return this one simple IteratorWalker for this movie
     */
    public IteratorWalker<T> softWalker();

    /**
     * Returns a shared IteratorWalker for deepest ArrayTape of movie. A ArraySaesson in 2d is a ArrayTape of ArrayTape.
     * A ArraySaesson in 3d is a ArrayTape of 2d ArraySaesson means ArrayTape of ArrayTape of ArrayTape.
     * <p>
     * This iterator can not be used parallel.
     *
     * @param atIndex the index of the element to start, counted across all elements of the external movie.
     * @return this one IteratorWalker for the deepest ArrayTape of movie with the given index as position
     */
    public IteratorWalker<T> leafWalker(int atIndex);

    /**
     * Index of the first equal element in the movie. If the specified element is null, it checks for null elements in
     * the ArrayTape.
     *
     * @param element the element to search for
     * @return the index of the first equal element in the movie or -1 if not found
     */
    public int indexOf(Object element);

    /**
     * Returns the index of the last occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param o the element to search for in the ArrayTape
     * @return the index of the last occurrence of the specified element, or -1 if the element is not found
     */
    public int lastIndexOf(Object element);


    /**
     * Copy the elements of this movie to the specified array.
     *
     * @param array the array to copy the elements to
     * @param offset the offset in the array to start copying to
     */
    public void copyToArray(Object[] array, int offset);

    /**
     * Check if the movies in a season are to big and need to be split or if they are to small and need be glued
     * together. Nothing to do for a movie itself.
     */
    public void splitOrGlue();

    /**
     * Debugging method to print the elements of the movie to the specified output stream.
     *
     * @param out the output stream to print to
     * @param prefix the prefix to print before each element
     * @param offset the offset to add to the index of each element (if this is a movie of movies)
     * @return the number of elements printed plus offset (the index of the next element from the next movie)
     */
    public int debbug(PrintStream out, String prefix, int offset);

    /**
     * Debugging method to print the elements of the movie to the specified output stream.
     *
     * @param out the output stream to print to
     * @param prefix the prefix to print before each element
     * @return the number of elements printed
     */
    public default int debbug(PrintStream out, String prefix) {
        return debbug(out, prefix, 0);
    }

    /**
     * Returns the number of elements that can be added to the ArrayTape before it needs to be resized.
     *
     * @return space still available or zero if the Movie is not an ArrayTape
     */
    int pageSpaceLeft();
}
