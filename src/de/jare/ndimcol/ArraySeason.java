/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.io.PrintStream;
import java.util.*;

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
 * ArrayMovie<T> extends Collection<T>.
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

    public ArraySeason(ArrayTape<T> original) {
        screenplay = Screenplay2d.INSTANCE;
        data = new ArrayTape<>(5);
        data.add(new ArrayTape<>(original));
        size = original.size();
        splitOrGlue();
        splitOrGlue();
        splitOrGlue();
        splitOrGlue();
        updateCounter = 0;
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
            final ArrayMovie<T> first = screenplay.buildMovie();
            data.add(first);
            first.add(element);
            size = 1;
            this.updateCounter++;
            return true;
        }
        final ArrayMovie<T> episode = data.get(data.size() - 1);
        if (episode.size() >= midEpisodeSize && episode.pageSpaceLeft() <= 8) {
            final ArrayMovie<T> nextFree = screenplay.buildMovie();
            data.add(nextFree);
            nextFree.add(element);
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
            final ArrayMovie<T> nextFree = screenplay.buildMovie();
            data.add(nextFree);
            size++;
            nextFree.add(element);
        } else {
            ArrayMovie<T> episode = data.get(episodeIndex);
            episode.add(element);
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        IteratorWalker<T> walker = getLeafWalkerAtIndex(index);
        if (walker.add(element)) {
            if (walker.size() > maxEpisodeSize) {
                splitOrGlue();
            }
            return true;
        }
        return false;
    }

    /**
     * Inserts all of the elements in the specified collection into this season, starting at the specified position.
     *
     * @param index index at which to insert the first element from the specified collection
     * @param col collection containing elements to be added to this season
     * @return true if this season changed as a result of the call
     */
    public boolean addAll(int index, Collection<? extends T> col) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        IteratorWalker<T> walker = getLeafWalkerAtIndex(index);
        if (walker.add(col)) {
            if (walker.size() > maxEpisodeSize) {
                splitOrGlue();
            }
            return true;
        }
        return false;
    }

    /**
     * Adds all elements in the specified collection to this collection.
     *
     * @param col collection containing elements to be added to this collection
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends T> col) {
        if (data.isEmpty()) {
            final ArrayMovie<T> first = screenplay.buildMovie();
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
     * Returns the element at the specified index in this collection.
     *
     * @param index the index of the element to return
     * @return the element at the specified index in this collection
     */
    @Override
    public T get(int index) {
        return getLeafWalkerAtIndex(index).next();
    }

    /**
     * Returns the first element in this collection.
     *
     * @return the first element in this collection
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
     * Removes the element in this collection.
     *
     * @param element to remove
     * @return true, if the element has removed from this collection
     */
    @Override
    public boolean remove(Object element) {
        IteratorWalker<T> walker = getLeafWalkerAtElement(element);
        if (walker == null) {
            return false;
        }
        size--;
        this.updateCounter++;
        walker.removeForward();
        if (walker.size() < 8) {
            splitOrGlue();
        }
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
        IteratorWalker<T> walker = getLeafWalkerAtIndex(index);
        T removedElement = walker.removeForward();
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
            } else if (lastSize + episodeSize < minEpisodeGlue
                    || episodeSize < 8 && i > 0
                    || lastSize < 8) {
                lastEpisode = null;
                data.removeAt(i);
                i--; // Move back to recheck the merged episode
                final ArrayMovie<T> prevEpisode = data.get(i);
                prevEpisode.addAll(episode);
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
     * Returns the index of the first free episode in this collection. If no free episode is found, it returns -1. Free
     * movies are those that have a size less than the maximum episode size.
     *
     * @return the index of the first free episode, or -1 if no free episode is found
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
     * Returns the leaf walker at the specified index in this collection.
     *
     * @param index the index to search for
     * @return the leaf walker at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    protected IteratorWalker<T> getLeafWalkerAtIndex(int index) {
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
     * Returns the leaf walker at the specified element in this collection.
     *
     * @param element the element to search for
     * @return the leaf walker at the specified element, or null if not found
     */
    protected IteratorWalker<T> getLeafWalkerAtElement(Object element) {
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeIndex = episode.indexOf(element);
            if (episodeIndex >= 0) {
                return episode.leafWalker(episodeIndex);
            }
        }
        return null;
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
        int accumulatedSize = 0;
        for (int i = 0; i < data.size(); i++) {
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
    public int lastIndexOf(Object element) {
        int accumulatedSize = size;
        for (int i = data.size() - 1; i >= 0; i--) {
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
     * Returns an array containing all elements in this collection. The runtime type of the returned array is that of
     * the specified array. If the collection fits in the specified array, it is returned therein. Otherwise, a new
     * array is allocated with the runtime type of the specified array and the size of this collection.
     *
     * @param arr the array into which the elements of this collection are to be stored
     * @return an array containing all elements in this collection
     */
    @SuppressWarnings("unchecked")
    @Override
    public <U> U[] toArray(U[] arr) {
        if (arr.length < size) {
            arr = (U[]) java.lang.reflect.Array.newInstance(arr.getClass().getComponentType(), size);
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
        if (arr.length > size) {
            arr[size] = null;
        }
    }

    /**
     * Copy the elements of this movie to the new ArraySeason.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ArrayMovie that is a sub-movie of the current ArraySeason
     */
    public ArrayMovie<T> subMovie(int fromIndex, int toIndex) {
        if (fromIndex >= size || fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", Size: " + size + ".");
        }
        if (toIndex >= size || toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", Size: " + size + ".");
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ".");
        }

        ArraySeason<T> subMovie = emptyMovie(0);
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
    public ArraySeason<T> emptyMovie(int initialCapacityOrZero) {
        return new ArraySeason<>(screenplay);
    }

    @Override
    public boolean containsAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        for (Object ob : col) {
            if (!contains(ob)) {
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
        for (Object o : col) {
            while (remove(o)) {
                modified = true;
            }
        }
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

    @Override
    public ArrayMovie<T> splitInHalf() {
        if (data.size() <= 8) {
            return null;
        }
        lastEpisode = null;
        lastAccumulatedSize = 0;
        this.updateCounter++;
        ArrayTape<ArrayMovie<T>> other = data.splitInHalf();
        updateSize();
        if (other == null) {
            return null;
        }
        ArraySeason<T> ret = new ArraySeason<>();
        ret.screenplay = screenplay;
        ret.data.addAll(other);
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
    public IteratorWalker<T> leafWalker(int atIndex
    ) {
        if (softWalker != null) {
            return softWalker.goLeafIndex(atIndex);
        }
        softWalker = new IterSeasonWalker<>(this);
        return softWalker.goLeafIndex(atIndex);
    }

    @Override
    public int debbug(PrintStream out, String prefix,
            int offset
    ) {
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            out.println(prefix + "s[" + i + "] .size() =  " + episode.size());
            offset = episode.debbug(out, prefix + "s[" + i + "]  ", offset);
        }
        return offset;
    }

    /**
     * Nothing to do.
     *
     * @return 0
     */
    @Override
    public int pageSpaceLeft() {
        return 0;
    }
}
