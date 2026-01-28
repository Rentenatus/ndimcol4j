/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import static de.jare.ndimcol.RentenatusHashable._combine;
import static de.jare.ndimcol.ref.HashStrategy._equals;
import static de.jare.ndimcol.ref.HashStrategy._hashCode;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * The ArraySeason class represents a collection of episodes, each containing a list of elements. The splitting and
 * gluing of episodes is managed by the screenplay, which defines the maximum size of an episode and the minimum size
 * for gluing episodes together.
 * <p>
 * Thus splitting in episodes makes the ArraySeason faster as a linear array list by shifting the elements and by
 * resizing the space. Resizing and shifting are needed by adding and by removing elements.
 * <p>
 * This class implements the ArrayMovie interface and provides methods for adding, removing, and accessing elements, as
 * well as for iterating over the elements in the array.
 * <p>
 * ArrayMovie&lt;T&gt; extends Collection&lt;T&gt;.
 *
 * @param <T> the type of elements in this season
 */
public class ArraySeason<T> implements ArrayMovie<T> {

    Screenplay screenplay;
    ArrayTape<ArrayMovie<T>> data;
    int size;
    int maxEpisodeSize;
    int midEpisodeSize;
    int minEpisodeGlue;
    int updateCounter;
    private int lastAccumulatedSize = 0;
    private ArrayMovie<T> lastEpisode = null;
    private IterSeasonWalker<T> softWalker;

    ArraySeason(ArrayTape<ArrayMovie<T>> data, int size) {
        screenplay = Screenplay2d.INSTANCE;
        this.data = data;
        this.size = size;
        updateCounter = 0;
        recalculateScope();
    }

    /**
     * Creates a new ArraySeason with the default screenplay (Screenplay2d).
     */
    public ArraySeason() {
        screenplay = Screenplay2d.INSTANCE;
        data = new ArrayTape<>(screenplay.getDefaultSize());
        size = 0;
        updateCounter = 0;
        recalculateScope();
    }

    /**
     * Creates a new ArraySeason with the specified screenplay.
     *
     * @param screenplay the screenplay to be used for this season
     */
    public ArraySeason(final Screenplay screenplay) {
        this.screenplay = screenplay;
        data = new ArrayTape<>(screenplay.getDefaultSize());
        size = 0;
        updateCounter = 0;
        recalculateScope();
    }

    /**
     * Here the tape are informed that private data has been changed from outside.
     */
    void added(T element) {
        //NoOp
    }

    /**
     * Adds the specified element to the end of this collection. If the collection is empty, a new episode is created.
     * If the last episode is full, a new episode is created.
     *
     * @param element the element to be added
     * @return true if the element was added successfully
     */
    @Override
    public boolean add(T element) {
        if (data.isEmpty()) {
            final ArrayMovie<T> first = buildInnerMovie(0);
            data.add(first); // add to empty data need not be checked
            first.add(element); // add to empty episode need not be checked
            size = 1;
            this.updateCounter++;
            return true;
        }
        final ArrayMovie<T> episode = data.last();
        if (episode.size() >= midEpisodeSize && episode.pageSpaceLeft() <= 3) {
            final ArrayMovie<T> nextFree = buildInnerMovie(this.size);
            if (!data.add(nextFree)) {
                return false;
            }
            nextFree.add(element); // add to empty episode need not be checked
            size++;
            this.updateCounter++;
            recalculateScope();
            return true;
        }
        episode.add(element);
        size++;
        this.updateCounter++;
        return true;
    }

    /**
     * Adds the element to the first free episode in the ArraySeason. If no free episode is found, a new episode is
     * created. Can only be used if compliance with the sequence is not expected.
     *
     * @param element the element to be added
     * @return true if the element was added successfully
     */
    public boolean addFirstFree(T element) {
        int episodeIndex = firstFreeEpisode();
        if (episodeIndex == -1) {
            final ArrayMovie<T> nextFree = buildInnerMovie(this.size);
            if (!data.add(nextFree)) {
                return false;
            }
            size++;
            nextFree.add(element); // add to empty episode need not be checked
        } else {
            ArrayMovie<T> episode = data.get(episodeIndex);
            if (!episode.add(element)) {
                return false;
            }
            size++;
            if (episode.size() > maxEpisodeSize) {
                splitOrGlue();
            }
        }
        this.updateCounter++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this collection. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean addAt(int index, T element) {
        if (lastEpisode != null
                && lastAccumulatedSize <= index
                && index < lastAccumulatedSize + lastEpisode.size()) {
            if (!lastEpisode.addAt(index - lastAccumulatedSize, element)) {
                return false;
            }
            updateCounter++;
            size++;
            if (lastEpisode.size() >= maxEpisodeSize) {
                splitOrGlue();
            }
            return true;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        int accumulatedSize = 0;
        final int dataSize = data.size();
        {
            final ArrayMovie<T> episode = data.get(0);
            int episodeSize = episode.size();
            if (index < episodeSize) {
                lastEpisode = null;
                if (!episode.addAt(index, element)) {
                    return false;
                }
                updateCounter++;
                size++;
                if (episode.size() >= midEpisodeSize && episode.pageSpaceLeft() <= 8) {
                    splitOrGlue();
                }
                return true;
            }
            accumulatedSize = episodeSize;
        }
        for (int i = 1; i < dataSize; i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeSize = episode.size();
            if (index < accumulatedSize + episodeSize) {
                lastAccumulatedSize = accumulatedSize;
                lastEpisode = episode;
                if (!episode.addAt(index - lastAccumulatedSize, element)) {
                    return false;
                }
                updateCounter++;
                size++;
                if (episode.size() >= midEpisodeSize && episode.pageSpaceLeft() <= 8) {
                    splitOrGlue();
                }
                return true;
            }
            accumulatedSize += episodeSize;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");

//        IteratorWalker<T> walker = getWalkerAtIndex(index);
//        if (walker.add(element)) {
//            if (walker.size() > maxEpisodeSize) {
//                splitOrGlue();
//            }
//            return true;
//        }
//        return false;
    }

    /**
     * Inserts all elements in the specified collection into this season, starting at the specified position.
     *
     * @param index index at which to insert the last element from the specified collection
     * @param col collection containing elements to be added to this season
     * @return true if this season changed as a result of the call
     */
    public boolean addAll(int index, Collection<? extends T> col) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        IteratorWalker<T> walker = getWalkerAtIndex(index);
        if (walker.add(col)) {
            if (walker.size() > maxEpisodeSize) {
                splitOrGlue();
            }
            return true;
        }
        return false;
    }

    /**
     * Adds all elements in the specified collection to this seasson.
     *
     * @param col collection containing elements to be added to this seasson
     * @return true if this seasson changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends T> col) {
        if (data.isEmpty()) {
            final ArrayMovie<T> first = buildInnerMovie(0);
            data.add(first);
            first.addAll(col);
            size = col.size();
            this.updateCounter++;
            if (size > maxEpisodeSize) {
                splitOrGlue();
            }
            return true;
        }
        final ArrayMovie<T> episode = data.get(data.size() - 1);
        boolean modified = episode.addAll(col);
        size += col.size();
        this.updateCounter++;
        if (episode.size() > maxEpisodeSize) {
            splitOrGlue();
        }
        return modified;
    }

    /**
     * Adds all of the elements in the specified array to the end of this seasson.
     *
     * @param arr array containing elements to be added to this seasson
     * @return {@code true} if this seasson changed as a result of the call
     * @see #add(Object)
     * @see #addAll(Collection)
     */
    @Override
    public boolean addAll(T[] arr) {
        if (arr == null) {
            throw new NullPointerException("Array cannot be null.");
        }
        if (arr.length == 0) {
            return false;
        }
        final ArrayTape<T> episode = new ArrayTape<>(arr.length);
        episode.addAll(arr);
        boolean modified = glueMovie(episode);
        if (episode.size() > maxEpisodeSize) {
            splitOrGlue();
        }
        return modified;
    }

    /**
     * Adds all of the elements in the specified movie to this seasson.
     *
     * @param movie movie containing elements to be added to this collection
     * @return {@code true} if this seasson changed as a result of the call
     */
    @Override
    public boolean addMovie(ArrayMovie<T> movie) {
        final ArrayTape<T> episode = new ArrayTape<>(movie.size());
        episode.addMovie(movie);
        boolean modified = glueMovie(episode);
        if (episode.size() > maxEpisodeSize) {
            splitOrGlue();
        }
        return modified;
    }

    /**
     * Assimilate the specified movie to this collection.
     *
     * Therefore, the application must not write to this object, or ideally, access it at all.
     *
     * @param episode movie to be assimilate to this collection
     * @return {@code true} if this collection changed as a result of the call
     */
    @Override
    public boolean glueMovie(ArrayMovie<T> episode) {
        if (episode.isEmpty()) {
            return false;
        }
        episode.assimilateInto(data);
        size += episode.size();
        this.updateCounter++;
        return true;
    }

    @Override
    public void assimilateInto(ArrayTape<ArrayMovie<T>> othersData) {
        othersData.addMovie(this.data);
    }

    /**
     * Sets the element at the specified position.Replaces an old element at the specified position in this list with
     * the specified element.
     *
     * @param index index at which the specified element is to be changed
     * @param element element to be appended to this list
     * @return the old element at the specified position
     */
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        IteratorWalker<T> walker = getWalkerAtIndex(index);
        return walker.set(element);
    }

    @Override
    //prim:public boolean equals(Object ob) {
    public boolean equals(Object ob) {
        //noprim.start  
        if (this == ob) {
            return true;
        }
        if (ob == null) {
            return false;
        }
        //noprim.ende  
        if (!(ob instanceof ArrayMovie<?>)) {
            if (!(ob instanceof Collection<?>)) {
                return false;
            }
            return equalsCollection((Collection<?>) ob);
        }
        ArrayMovie<?> movie = (ArrayMovie<?>) ob;
        if (size() != movie.size()) {
            return false;
        }
        IteratorWalker<?> mWalker = movie.softWalker();
        IteratorWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            if (!equals(walker.next(), mWalker.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsCollection(Collection<?> col) {
        if (this == col) {
            return true;
        }
        if (col == null) {
            return false;
        }
        if (size() != col.size()) {
            return false;
        }
        Iterator<?> iter = col.iterator();
        IteratorWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            if (!equals(walker.next(), iter.next())) {
                return false;
            }
        }
        return true;
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

    //prim:public boolean equals(_PRIM_ a, Object b) {
    //prim:    return _equals(a, b);
    //prim:}
    //prim.ende
    @Override
    public int hashCode() {
        int hashCode = 0;
        IteratorWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            hashCode = _combine(hashCode, _hashCode(walker.next()));
        }
        return hashCode;
    }

    ArrayMovie<T> buildInnerMovie(final int parentSize) {
        return screenplay.buildMovie(parentSize);
    }

    /**
     * Returns the element at the specified index in this collection.
     *
     * @param index the index of the element to return
     * @return the element at the specified index in this collection
     */
    @Override
    public T get(int index) {
        return getWalkerAtIndex(index).next();
    }

    /**
     * Returns the last element in this collection.
     *
     * @return the last element in this collection
     */
    @Override
    public T first() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Season is empty.");
        }
        return data.first().first();
    }

    /**
     * Returns the last element in this collection.
     *
     * @return the last element in this collection
     */
    @Override
    public T last() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Season is empty.");
        }
        return data.last().last();
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is one or more times present.
     *
     * @param element one times to remove
     * @return true, if the element has removed from this collection
     */
    @Override
    public boolean remove(Object element) {
        IteratorWalker<T> walker = getWalkerAtElement(element);
        if (walker == null) {
            return false;
        }
        // walker ist a CoverWalker, not a leaf walker
        // walker push observer.size--; and observer.updateCounter++;
        walker.removeForward();
        return true;
    }

    /**
     * Removes the element at the specified index in this collection.
     *
     * @param index the index of the element to remove
     * @return the element that was removed from this collection
     */
    @Override
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        IteratorWalker<T> walker = getWalkerAtIndex(index);
        // walker ist a CoverWalker, not a leaf walker
        // walker push observer.size--; and observer.updateCounter++;
        T removedElement = walker.removeBackward();
        if (walker.isEmpty()) {
            splitOrGlue();
        }
        return removedElement;
    }

    /**
     * Splits or glues the episodes in this collection based on their sizes. This method iterates through the episodes
     * and checks their sizes. If an episode is larger than the maximum size, it is split in half. If two episodes are
     * smaller than the minimum size, they are glued together. If an episode itself is to small, this episode are glued
     * to the next episode together. If an episode is empty, it is removed from the collection. At last, the maximum
     * episode size and minimum glue size are updated based on the current number of episodes.
     */
    @Override
    public void splitOrGlue() {
        int lastSize = maxEpisodeSize;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            final int episodeSize = episode.size();
            if (episodeSize == 0) {
                data.removeAt(i);
                i--; // Move back to recheck the merged episode
            } else if (episodeSize > maxEpisodeSize) {
                lastEpisode = null;
                ArrayMovie<T> newEpisode = episode.splitInHalf();
                if (newEpisode == null) {
                    continue;
                }
                data.addAt(i + 1, newEpisode);
                i++; // Skip the newly added episode to avoid immediate reprocessing
                lastSize = maxEpisodeSize;
            } else if ((lastSize + episodeSize < minEpisodeGlue
                    || episodeSize < 8
                    || lastSize < 8) && i > 0) {
                lastEpisode = null;
                data.removeAt(i);
                i--; // Move back to recheck the merged episode
                final ArrayMovie<T> prevEpisode = data.get(i);
                prevEpisode.glueMovie(episode);
                lastSize = prevEpisode.size();
            } else {
                lastSize = episode.size();
                episode.splitOrGlue();
            }
        }
        recalculateScope();
    }

    /**
     * Recalculates the maximum episode size and minimum episode glue size based on the current number of episodes in
     * this collection.
     */
    protected void recalculateScope() {
        final int fac = data.size();
        maxEpisodeSize = screenplay.getMaxEpisodeSize(fac);
        minEpisodeGlue = screenplay.getMinEpisodeGlue(fac);
        midEpisodeSize = maxEpisodeSize - ArrayTape.DEFAULT_PAGE;
    }

    /**
     * Returns the index of the first free episode in this collection.If no free episode is found, it returns -1. Free
     * movies are those that have a size less than the maximum episode size.
     *
     * @return the index of the last free episode, or -1 if no free episode is found
     */
    protected int firstFreeEpisode() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).size() < maxEpisodeSize) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the walker at the specified index in this collection.
     *
     * @param index the index to search for
     * @return the cover walker at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    protected IteratorWalker<T> getWalkerAtIndex(int index) {
        if (lastEpisode != null
                && lastAccumulatedSize <= index
                && index < lastAccumulatedSize + lastEpisode.size()) {
            return new IterCoverWalker<>(this, lastEpisode.leafWalker(index - lastAccumulatedSize));
        }
        int accumulatedSize = 0;
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeSize = episode.size();
            if (index < accumulatedSize + episodeSize) {
                lastAccumulatedSize = accumulatedSize;
                lastEpisode = episode;
                return new IterCoverWalker<>(this, episode.leafWalker(index - accumulatedSize));
            }
            accumulatedSize += episodeSize;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
    }

    /**
     * Returns the walker at the specified element in this collection.
     *
     * @param element the element to search for
     * @return the walker at the specified element, or null if element not found
     */
    public IteratorWalker<T> getWalkerAtElement(final Object element) {
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            final int episodeIndex = episode.indexOf(element);
            if (episodeIndex >= 0) {
                return new IterCoverWalker<>(this, episode.leafWalker(episodeIndex));

            }
        }
        return null;
//        if (data.isEmpty()) {
//            return null;
//        }
//        if (element == null) {
//            return filterFirst(Objects::isNull);
//        }
//        return filterFirst(element::equals);
    }

    /**
     * Returns the index of the first occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the specified element, or -1 if the element is not found
     */
    @Override
    public int indexOf(Object element) {
        final int dataSize = data.size();
        if (dataSize == 1) {
            final ArrayMovie<T> episode = data.first();
            return episode.indexOf(element);
        }
        int accumulatedSize = 0;
        for (int i = 0; i < dataSize; i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeIndex = episode.indexOf(element);
            if (episodeIndex >= 0) {
                return accumulatedSize + episodeIndex;
            }
            accumulatedSize += episode.size();
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for in the ArrayTape
     * @return the index of the last occurrence of the specified element, or -1 if the element is not found
     */
    @Override
    public int lastIndexOf(Object element) {
        final int dataSize = data.size();
        if (dataSize == 1) {
            final ArrayMovie<T> episode = data.first();
            return episode.lastIndexOf(element);
        }
        int accumulatedSize = size;
        for (int i = dataSize - 1; i >= 0; i--) {
            final ArrayMovie<T> episode = data.get(i);
            accumulatedSize -= episode.size();
            int episodeIndex = episode.lastIndexOf(element);
            if (episodeIndex >= 0) {
                return accumulatedSize + episodeIndex;
            }
        }
        return -1;
    }

    /**
     * Returns the offset of the specified related movie (chunk) in this collection. The offset is the number of
     * elements before the specified movie starts.
     *
     * @param relatedMovie the related movie to find the offset for
     * @return the offset of the specified related movie in this collection
     */
    public int getOffset(ArrayMovie<T> relatedMovie) {
        int offset = 0;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            if (episode == relatedMovie) {
                return offset;
            }
            offset += episode.size();
        }
        return 0;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this collection contains no elements.
     *
     * @return true if this collection contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if the ArrayMovie contains one or more elements.
     *
     * @return true if the ArrayMovie has elements, false otherwise
     */
    @Override
    public boolean hasRecord() {
        return this.size > 0;
    }

    /**
     * Returns true if the specified element is present in this collection.
     *
     * @param element the element to check for
     * @return true if the specified element is present in this collection, false otherwise
     */
    @Override
    public boolean contains(Object element) {
        for (ArrayMovie<T> episode : data) {
            if (episode.contains(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this collection in proper sequence.
     *
     * @return an iterator over the elements in this collection in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        final IterSeasonWalker<T> walker = new IterSeasonWalker<>(this);
        return new Iterator<>() {
            private int initialUpdateCounter = Integer.MIN_VALUE;

            @Override
            public boolean hasNext() {
                return walker.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (initialUpdateCounter == Integer.MIN_VALUE) {
                    initialUpdateCounter = updateCounter;
                } else if (initialUpdateCounter != updateCounter) {
                    throw new ConcurrentModificationException("ArraySeasson was modified during iteration.");
                }

                return walker.next();
            }
        };
    }

    /**
     * Returns an array containing all elements in this collection.
     *
     * @return an array containing all elements in this collection
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        copyToArray(array, 0);
        return array;
    }

    /**
     * Returns an array containing all elements in this collection.The runtime type of the returned array is that of the
     * specified array. If the collection fits in the specified array, it is returned therein. Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of this collection.
     *
     * @param <U> the type
     * @param arr the array into which the elements of this collection are to be stored
     * @return an array containing all elements in this collection
     */
    @SuppressWarnings("unchecked")
    @Override
    public <U> U[] toArray(U[] arr) {
        if (arr.length < size) {
            //prim:arr=new _PRIM_[size];
            arr = (U[]) Array.newInstance(arr.getClass().getComponentType(), size);
        }
        copyToArray(arr, 0);
        return arr;
    }

    /**
     * Copies the elements of this movie to the specified array, starting at the specified offset.
     *
     * @param arr the array to copy the elements into
     * @param offset the offset in the array where to start copying
     */
    @Override
    public void copyToArray(Object[] arr, int offset) {
        if (arr.length < size + offset) {
            throw new IndexOutOfBoundsException("My size + offset: " + (size + offset)
                    + ", Target array size: " + arr.length + ".");
        }
        int index = offset;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            episode.copyToArray(arr, index);
            index += episode.size();
        }
        //noprim.start
        if (arr.length > size) {
            arr[size] = null;
        }
        //noprim.end
    }

    /**
     * Copy the elements of this movie to the new ArraySeason.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ArrayMovie that is a sub-movie of the current ArraySeason
     */
    @Override
    public ArrayMovie<T> subMovie(int fromIndex, int toIndex) {
        if (fromIndex >= size || fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", Size: " + size + ".");
        }
        if (toIndex > size || toIndex <= 0) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", Size: " + size + ".");
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ".");
        }

        ArraySeason<T> subMovie = emptyMovie(0);
        if (fromIndex == toIndex) {
            return subMovie;
        }
        int accumulatedSize = 0;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            int episodeSize = episode.size();
            int nextSize = accumulatedSize + episodeSize;
            if (nextSize > fromIndex) {
                int startIndex = Math.max(0, fromIndex - accumulatedSize);
                int endIndex = Math.min(episodeSize, toIndex - accumulatedSize + 1);
                subMovie.data.add(episode.subMovie(startIndex, endIndex));
            }
            accumulatedSize = nextSize;
            if (accumulatedSize >= toIndex) {
                break;
            }
        }
        subMovie.updateSize();
        return subMovie;
    }

    /**
     * Creates a new empty season with the same screenplay. The new movie is not a copy of this movie.
     *
     * @param initialCapacityOrZero not used
     * @return a new empty movie with the same screenplay
     */
    @Override
    public ArraySeason<T> emptyMovie(int initialCapacityOrZero) {
        return new ArraySeason<>(screenplay);
    }

    @Override
    public boolean containsAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        //prim: for (Object element : col) {
        for (Object element : col) {
            //prim: if ( !(element instanceof _PRIMBOX_) || !contains((_PRIM_)element) ) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        boolean modified = false;
        //noprim.start  
        for (Object ob : col) {
            while (remove(ob)) {
                modified = true;
            }
        }
        //noprim.end  
        //prim:for (Object ob : col) {
        //prim:    if (ob instanceof _PRIMBOX_) {
        //prim:        while (remove((_PRIM_) ob)) {
        //prim:            modified = true;
        //prim:        }
        //prim:    }
        //prim:}
        //prim.end
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        if (col.isEmpty()) {
            clear();
            return true;
        }
        int accumulatedSize = 0;
        boolean modified = false;
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            if (episode.retainAll(col)) {
                modified = true;
            }
            accumulatedSize += episode.size();
        }
        size = accumulatedSize;
        this.updateCounter++;
        return modified;
    }

    @Override
    public void clear() {
        data = new ArrayTape<>(screenplay.getDefaultSize());
        size = 0;
        this.updateCounter++;
        lastAccumulatedSize = 0;
        lastEpisode = null;
    }

    /**
     * Here the tape are informed that private data or inner tape has been changed from outside.
     */
    void deepChanged() {
        //NoOp
    }

    void replaced(int index, T ret, T element) {
        //NoOp
    }

    /**
     * Splits this season into two. This season contains the first half of the elements, and the second season contains
     * the second half of the elements. Half is to be understood colloquially; it is not guaranteed that the size of
     * both parts is the same.
     *
     * @return a new season containing the second half of the elements or null, if this movie is to small for splitting
     */
    @Override
    public ArrayMovie<T> splitInHalf() {
        if (data.isEmpty()) {
            return null;
        }
        lastEpisode = null;
        lastAccumulatedSize = 0;
        this.updateCounter++;
        ArraySeason<T> ret = emptyMovie((size >> 1) + screenplay.getDefaultSize());
        ret.screenplay = screenplay;
        if (data.size() == 1) {
            ArrayMovie<T> other = data.get(0).splitInHalf();
            updateSize();
            if (other == null) {
                return null;
            }
            ret.data.add(other);
        } else {
            ArrayTape<ArrayMovie<T>> other = data.splitInHalf();
            updateSize();
            if (other == null) {
                return null;
            }
            ret.data.addAll(other);
        }
        ret.updateSize();
        return ret;
    }

    protected void updateSize() {
        int oldSize = size;
        size = 0;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            size += episode.size();
        }
        if (oldSize != size) {
            recalculateScope();
        }
    }

    @Override
    public IteratorWalker<T> softWalker() {
        if (softWalker != null) {
            return softWalker.goFirst();
        }
        softWalker = new IterSeasonWalker<>(this);
        return softWalker;
    }

    @Override
    public IteratorWalker<T> softWalkerBackwards() {
        if (softWalker != null) {
            return softWalker.goLast();
        }
        softWalker = new IterSeasonWalker<>(this);
        return softWalker.goLast();
    }

    @Override
    public IteratorWalker<T> leafWalker(int atIndex) {
        if (softWalker != null) {
            return softWalker.goLeafIndex(atIndex);
        }
        softWalker = new IterSeasonWalker<>(this);
        return softWalker.goLeafIndex(atIndex);
    }

    @Override
    public int debug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            out.println(prefix + "s[" + i + "] .size() =  " + episode.size());
            offset = episode.debug(out, prefix + "s[" + i + "]  ", offset);
        }
        return offset;
    }

    /**
     * Returns the number of episodes that can be added.
     *
     * @return space still available
     */
    @Override
    public int pageSpaceLeft() {
        return data.pageSpaceLeft();
    }

    @Override
    public IteratorWalker<T> filterFirst(Predicate<? super T> predicate) {
        int startupdateCounter = updateCounter;
        for (int i = 0; i < data.size(); i++) {
            IteratorWalker<T> walker = data.get(i).filterFirst(predicate);
            if (walker != null) {
                if (startupdateCounter != updateCounter) {
                    throw new ConcurrentModificationException("ArraySeasson was modified during iteration.");
                }
                return new IterCoverWalker<>(this, walker);
            }
        }
        return null;
    }

    @Override
    public IteratorWalker<T> filterLast(Predicate<? super T> predicate) {
        int startupdateCounter = updateCounter;
        for (int i = data.size() - 1; i >= 0; i--) {
            IteratorWalker<T> walker = data.get(i).filterLast(predicate);
            if (walker != null) {
                if (startupdateCounter != updateCounter) {
                    throw new ConcurrentModificationException("ArraySeasson was modified during iteration.");
                }
                return new IterCoverWalker<>(this, walker);
            }
        }
        return null;
    }

    @Override
    public ArrayMovie<T> filterAll(Predicate<? super T> predicate) {
        ArraySeason<T> ret = emptyMovie(data.size() << 3);
        IterTapeWalker<ArrayMovie<T>> walker = data.walker();
        while (walker.hasNext()) {
            ArrayMovie<T> elements = walker.next();
            if (elements.hasRecord()) {
                ret.data.add(elements);
            }
        }
        ret.updateSize();
        return ret;
    }

    /**
     * Return a new movie containing all indexes of entries that match the given predicate.
     *
     * @param predicate the predicate to be used for the filter
     * @return a new movie containing all indexes of entries that match the given predicate
     */
    @Override
    public de.jare.ndimcol.primint.ArrayMovieInt filterAllIndexes(Predicate<? super T> predicate) {
        final de.jare.ndimcol.primint.ArrayMovieInt ret = new de.jare.ndimcol.primint.ArraySeasonInt();
        forEach(predicate, null, 0, idx -> ret.add(idx));
        return ret;
    }

    /**
     * Performs the given action for each element of the {@code Iterable} until all elements have been processed or the
     * action throws an exception. Actions are performed in the order of iteration, if that order is specified.
     * Exceptions thrown by the action are relayed to the caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        IterTapeWalker<ArrayMovie<T>> walker = data.walker();
        while (walker.hasNext()) {
            ArrayMovie<T> elements = walker.next();
            elements.forEach(action);
        }
    }

    /**
     * Executes the given action for each element of the {@code Iterable}, but only if the provided {@code Predicate}
     * evaluates to {@code true} for that element.Actions are performed in the order of iteration, if such order is
     * defined. Exceptions thrown by the action are propagated to the caller.
     *
     * @param predicate The condition to test each element against
     * @param thenAction The action to perform on each element that satisfies the predicate
     * @throws NullPointerException if {@code predicate} or {@code action} is {@code null}
     */
    @Override
    public void forEach(Predicate<? super T> predicate, Consumer<? super T> thenAction) {
        IterTapeWalker<ArrayMovie<T>> walker = data.walker();
        while (walker.hasNext()) {
            ArrayMovie<T> elements = walker.next();
            elements.forEach(predicate, thenAction);
        }
    }

    /**
     * Executes the given action for each element of the ArrayTape, but only if the provided {@code Predicate} evaluates
     * to {@code true} for that element.Actions are performed in the order of iteration, if such order is
     * defined.Exceptions thrown by the action are propagated to the caller.
     *
     * @param predicate The condition to test each element against
     * @param thenActionOrNull The action to perform on each element that satisfies the predicate
     * @param startOffset index offset
     * @param thenIndexActionOrNull The action to perform on index thus element
     * @throws NullPointerException if {@code predicate} or {@code action} is {@code null}
     */
    @Override
    public void forEach(Predicate<? super T> predicate, Consumer<? super T> thenActionOrNull,
            int startOffset, IntConsumer thenIndexActionOrNull) {
        int offset = startOffset;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            episode.forEach(predicate, thenActionOrNull, offset, thenIndexActionOrNull);
            offset += episode.size();
        }
    }

    /**
     * Executes one of the given actions for each element of the {@code Iterable}, depending on the result of the
     * provided {@code Predicate}. If the predicate evaluates to {@code true} for an element, {@code thenAction} is
     * executed. Otherwise, {@code elseAction} is executed. Actions are performed in the order of iteration, if such
     * order is defined. Exceptions thrown by either action are propagated to the caller.
     *
     * @param predicate The condition to test each element against
     * @param thenAction The action to perform on elements that satisfy the predicate
     * @param elseAction The action to perform on elements that do not satisfy the predicate
     * @throws NullPointerException if {@code predicate}, {@code thenAction}, or {@code elseAction} is {@code null}
     */
    @Override
    public void forEach(Predicate<? super T> predicate,
            Consumer<? super T> thenAction, Consumer<? super T> elseAction) {
        IterTapeWalker<ArrayMovie<T>> walker = data.walker();
        while (walker.hasNext()) {
            ArrayMovie<T> elements = walker.next();
            elements.forEach(predicate, thenAction, elseAction);
        }
    }

    /**
     * Return a new movie containing all elements that match the given predicate.
     * <p>
     * This method uses multiple threads to filter the elements in parallel.
     *
     * @param predicate the predicate to be used for the filter
     * @return a new movie containing all elements that match the given predicate
     */
    @SuppressWarnings("unchecked")
    public ArrayMovie<T> filterParallel(Predicate<? super T> predicate) {
        Thread[] threads = new Thread[data.size()];
        Runnable[] tasks = new Runnable[data.size()];
        for (int i = 0; i < data.size(); i++) {
            tasks[i] = new PredicateAllRunnable<T>(predicate, data.get(i));
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        ArraySeason<T> ret = emptyMovie(data.size() << 3);
        for (int i = 0; i < data.size(); i++) {
            try {
                threads[i].join();
                ArrayMovie<T> elements = ((PredicateAllRunnable<T>) tasks[i]).getElements();
                if (elements.hasRecord()) {
                    ret.data.add(elements);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted.", e);
            }
        }
        ret.updateSize();
        return ret;
    }

}
