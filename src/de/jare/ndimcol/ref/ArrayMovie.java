/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this movie
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
    T first();

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
    T last();

    /**
     * Inserts the specified element at the specified position in this list . Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @return true (as specified by {@link Collection#add})
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    boolean addAt(int index, T element);

    default void add(int index, T element) {
        addAt(index, element);
    }

    /**
     * Adds all of the elements in the specified collection to this collection (optional operation).The behavior of this
     * operation is undefined if the specified collection is modified while the operation is in progress. (This implies
     * that the behavior of this call is undefined if the specified collection is this collection, and this collection
     * is nonempty.) If the specified collection has a defined
     * <a href="SequencedCollection.html#encounter">encounter order</a>, processing of its elements generally occurs in
     * that order.
     *
     * @param col collection containing elements to be added to this collection
     * @return {@code true} if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by this collection
     * @throws ClassCastException if the class of an element of the specified collection prevents it from being added to
     * this collection
     * @throws NullPointerException if the specified collection contains a null element and this collection does not
     * permit null elements, or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the specified collection prevents it from
     * being added to this collection
     * @throws IllegalStateException if not all the elements can be added at this time due to insertion restrictions
     * @see #add(Object)
     */
    @Override
    boolean addAll(Collection<? extends T> col);

    /**
     * Adds all of the elements in the specified movie to this collection.
     *
     * @param movie movie containing elements to be added to this collection
     * @return {@code true} if this collection changed as a result of the call
     */
    boolean addMovie(ArrayMovie<T> movie);

    /**
     * Adds the specified movie to this collection.
     *
     * The most important difference from addMovie is that the movie doesn't necessarily have to be copied; the object
     * can also be assimilated in its entirety. Therefore, the application must not write to this object, or ideally,
     * access it at all.
     *
     * @param movie movie containing elements to be added to this collection
     * @return {@code true} if this collection changed as a result of the call
     */
    boolean glueMovie(ArrayMovie<T> movie);

    void assimilateInto(ArrayTape<ArrayMovie<T>> othersData);

    /**
     * Appends the specified element to the end of this list .
     *
     * @param element element to be appended to this list
     * @return true (as specified by {@link Collection#add})
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    @Override
    boolean add(T element);

    /**
     * Get the element at the specified index.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    T get(int index);

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to remove
     * @return the element or null
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     * @see #remove(Object)
     */
    T removeAt(int index);

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional operation).
     * More formally, removes an element {@code e} such that {@code Objects.equals(o, e)}, if this collection contains
     * one or more such elements. Returns {@code true} if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     *
     * @param o element to be removed from this collection, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the type of the specified element is incompatible with this collection
     * ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified element is null and this collection does not permit null elements
     * ({@linkplain Collection##optional-restrictions optional})
     * @throws UnsupportedOperationException if the {@code remove} operation is not supported by this collection
     */
    @Override
    boolean remove(Object o);

    /**
     * Removes all of this collection's elements that are also contained in the specified collection (optional
     * operation).After this call returns, this collection will contain no elements in common with the specified
     * collection.
     *
     * @param col collection containing elements to be removed from this collection
     * @return {@code true} if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the {@code removeAll} method is not supported by this collection
     * @throws ClassCastException if the types of one or more elements in this collection are incompatible with the
     * specified collection ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if this collection contains one or more null elements and the specified collection
     * does not support null elements ({@linkplain Collection##optional-restrictions optional}) or if the specified
     * collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    boolean removeAll(Collection<?> col);

    /**
     * Retains only the elements in this collection that are contained in the specified collection (optional
     * operation).In other words, removes from this collection all of its elements that are not contained in the
     * specified collection.
     *
     * @param col collection containing elements to be retained in this collection
     * @return {@code true} if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the {@code retainAll} operation is not supported by this collection
     * @throws ClassCastException if the types of one or more elements in this collection are incompatible with the
     * specified collection ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if this collection contains one or more null elements and the specified collection
     * does not permit null elements ({@linkplain Collection##optional-restrictions optional}) or if the specified
     * collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    boolean retainAll(Collection<?> col);

    /**
     * Removes all of the elements from this collection (optional operation). The collection will be empty after this
     * method returns.
     *
     * @throws UnsupportedOperationException if the {@code clear} operation is not supported by this collection
     */
    @Override
    void clear();

    /**
     * Returns the number of elements in this collection. If this collection contains more than
     * {@code Integer.MAX_VALUE} elements, returns {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this collection
     */
    @Override
    int size();

    /**
     * Returns true if the movie is empty.
     *
     * @return true if the movie is empty, false otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Returns true if the movie contains one or more elements.
     *
     * @return true if the movie has elements, false otherwise
     */
    boolean hasRecord();

    /**
     * Splits this movie into two movies. This movie contains the first half of the elements, and the second movie
     * contains the second half of the elements. Half is to be understood colloquially; it is not guaranteed that the
     * size of both parts is the same.
     *
     * @return a new movie containing the second half of the elements or null, if this movie is to small for splitting
     */
    ArrayMovie<T> splitInHalf();

    /**
     * Returns a simple shared IteratorWalker for this movie. This simple iterator can not be used parallel.
     *
     * @return this one simple IteratorWalker for this movie
     */
    IteratorWalker<T> softWalker();

    /**
     * Returns a shared IteratorWalker for deepest ArrayTape of movie. A ArraySaesson in 2d is a ArrayTape of ArrayTape.
     * A ArraySaesson in 3d is a ArrayTape of 2d ArraySaesson means ArrayTape of ArrayTape of ArrayTape.
     * <p>
     * This iterator can not be used parallel.
     *
     * @param atIndex the index of the element to start, counted across all elements of the external movie.
     * @return this one IteratorWalker for the deepest ArrayTape of movie with the given index as position
     */
    IteratorWalker<T> leafWalker(int atIndex);

    /**
     * Returns an iterator over the elements in this collection. There are no guarantees concerning the order in which
     * the elements are returned (unless this collection is an instance of some class that provides a guarantee).
     *
     * @return an {@code Iterator} over the elements in this collection
     */
    @Override
    Iterator<T> iterator();

    /**
     * Index of the first equal element in the movie. If the specified element is null, it checks for null elements in
     * the ArrayTape.
     *
     * @param element the element to search for
     * @return the index of the first equal element in the movie or -1 if not found
     */
    int indexOf(Object element);

    /**
     * Returns {@code true} if this collection contains the specified element. More formally, returns {@code true} if
     * and only if this collection contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this collection is to be tested
     * @return {@code true} if this collection contains the specified element
     * @throws ClassCastException if the type of the specified element is incompatible with this collection
     * ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified element is null and this collection does not permit null elements
     * ({@linkplain Collection##optional-restrictions optional})
     */
    @Override
    boolean contains(Object o);

    /**
     * Returns {@code true} if this collection contains all of the elements in the specified collection.
     *
     * @param col collection to be checked for containment in this collection
     * @return {@code true} if this collection contains all of the elements in the specified collection
     * @throws ClassCastException if the types of one or more elements in the specified collection are incompatible with
     * this collection ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified collection contains one or more null elements and this collection
     * does not permit null elements ({@linkplain Collection##optional-restrictions optional}) or if the specified
     * collection is null.
     * @see #contains(Object)
     */
    @Override
    boolean containsAll(Collection<?> col);

    /**
     * Returns the index of the last occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for in the ArrayTape
     * @return the index of the last occurrence of the specified element, or -1 if the element is not found
     */
    int lastIndexOf(Object element);

    /**
     * Copy the elements of this movie to the specified array.
     *
     * @param array the array to copy the elements to
     * @param offset the offset in the array to start copying to
     */
    void copyToArray(Object[] array, int offset);

    /**
     * Returns an array containing all of the elements in this collection. If this collection makes any guarantees as to
     * what order its elements are returned by its iterator, this method must return the elements in the same order. The
     * returned array's {@linkplain Class#getComponentType
     * runtime component type} is {@code Object}.
     *
     * <p>
     * The returned array will be "safe" in that no references to it are maintained by this collection. (In other words,
     * this method must allocate a new array even if this collection is backed by an array). The caller is thus free to
     * modify the returned array.
     *
     * @apiNote This method acts as a bridge between array-based and collection-based APIs. It returns an array whose
     * runtime type is {@code Object[]}. Use {@link #toArray(Object[]) toArray(T[])} to reuse an existing array, or use
     * {@link #toArray(IntFunction)} to control the runtime type of the array.
     *
     * @return an array, whose {@linkplain Class#getComponentType runtime component
     * type} is {@code Object}, containing all of the elements in this collection
     */
    @Override
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned array is
     * that of the specified array.If the collection fits in the specified array, it is returned therein. Otherwise, a
     * new array is allocated with the runtime type of the specified array and the size of this collection.
     *
     * <p>
     * If this collection fits in the specified array with room to spare (i.e., the array has more elements than this
     * collection), the element in the array immediately following the end of the collection is set to {@code null}.
     * (This is useful in determining the length of this collection <i>only</i> if the caller knows that this collection
     * does not contain any {@code null} elements.)
     *
     * <p>
     * If this collection makes any guarantees as to what order its elements are returned by its iterator, this method
     * must return the elements in the same order.
     *
     * @apiNote This method acts as a bridge between array-based and collection-based APIs. It allows an existing array
     * to be reused under certain circumstances. Use {@link #toArray()} to create an array whose runtime type is
     * {@code Object[]}, or use {@link #toArray(IntFunction)} to control the runtime type of the array.
     *
     * <p>
     * Suppose {@code x} is a collection known to contain only strings. The following code can be used to dump the
     * collection into a previously allocated {@code String} array:
     *
     * <pre>
     *     String[] y = new String[SIZE];
     *     ...
     *     y = x.toArray(y);</pre>
     *
     * <p>
     * The return value is reassigned to the variable {@code y}, because a new array will be allocated and returned if
     * the collection {@code x} has too many elements to fit into the existing array {@code y}.
     *
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to {@code toArray()}.
     *
     * @param <U> the component type of the array to contain the collection
     * @param arr the array into which the elements of this collection are to be stored, if it is big enough; otherwise,
     * a new array of the same runtime type is allocated for this purpose.
     * @return an array containing all of the elements in this collection
     * @throws ArrayStoreException if the runtime type of any element in this collection is not assignable to the {@linkplain Class#getComponentType
     *         runtime component type} of the specified array
     * @throws NullPointerException if the specified array is null
     */
    @Override
    <U> U[] toArray(U[] arr);

    /**
     * Copy the elements of this movie to the new ArrayMovie.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ArrayMovie that is a sub-movie of the current ArrayMovie
     */
    ArrayMovie<T> subMovie(int fromIndex, int toIndex);

    /**
     * Creates a new empty movie with the specified initial capacity if possible. The new movie is not a copy of this
     * movie.
     *
     * @param initialCapacityOrZero the initial capacity of the new movie or zero if no initial capacity is needed
     * @return a new empty movie with the specified initial capacity
     */
    ArrayMovie<T> emptyMovie(int initialCapacityOrZero);

    /**
     * Check if the movies in a season are to big and need to be split or if they are to small and need be glued
     * together. Nothing to do for a movie itself.
     */
    void splitOrGlue();

    /**
     * Debugging method to print the elements of the movie to the specified output stream.
     *
     * @param out the output stream to print to
     * @param prefix the prefix to print before each element
     * @param offset the offset to add to the index of each element (if this is a movie of movies)
     * @return the number of elements printed plus offset (the index of the next element from the next movie)
     */
    int debug(PrintStream out, String prefix, int offset);

    /**
     * Debugging method to print the elements of the movie to the specified output stream.
     *
     * @param out the output stream to print to
     * @param prefix the prefix to print before each element
     * @return the number of elements printed
     */
    default int debug(PrintStream out, String prefix) {
        return debug(out, prefix, 0);
    }

    /**
     * Returns the number of elements that can be added to the ArrayTape before it needs to be resized.
     *
     * @return space still available or zero if the Movie is not an ArrayTape
     */
    int pageSpaceLeft();

    /**
     * Returns a leaf walker for the first occurrence of a hit considering the given predicate.
     *
     * @param predicate the predicate to be used for the search
     * @return a leaf walker for the first occurrence of a hit
     */
    IteratorWalker<T> filterFirst(Predicate<? super T> predicate);

    /**
     * Returns a leaf walker for the last occurrence of a hit considering the given predicate.
     *
     * @param predicate the predicate to be used for the search
     * @return a leaf walker for the last occurrence of a hit
     */
    IteratorWalker<T> filterLast(Predicate<? super T> predicate);

    /**
     * Return a new movie containing all elements that match the given predicate.
     *
     * @param predicate the predicate to be used for the filter
     * @return a new movie containing all elements that match the given predicate
     */
    ArrayMovie<T> filterAll(Predicate<? super T> predicate);

}
