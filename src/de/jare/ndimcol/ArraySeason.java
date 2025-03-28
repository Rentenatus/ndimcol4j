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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySeason<T> implements ArrayMovie<T> {

    private Screenplay screenplay;
    ArrayTape<ArrayMovie<T>> data;
    int size;
    int maxEpisodeSize;
    int minEpisodeGlue;
    int updateCounter;
    private int lastAccumulatedSize = 0;
    private ArrayMovie<T> lastEpisode = null;
    private IterSeasonWalker<T> softWalker;

    public ArraySeason() {
        screenplay = Screenplay2d.INSTANCE;
        data = new ArrayTape<>(screenplay.getDefaultSize());
        size = 0;
        updateCounter = 0;
        maxEpisodeSize = screenplay.getMaxEpisodeSize(0);
        minEpisodeGlue = screenplay.getMinEpisodeGlue(0);
    }

    public ArraySeason(final Screenplay screenplay) {
        this.screenplay = screenplay;
        data = new ArrayTape<>(screenplay.getDefaultSize());
        size = 0;
        updateCounter = 0;
        maxEpisodeSize = screenplay.getMaxEpisodeSize(0);
        minEpisodeGlue = screenplay.getMinEpisodeGlue(0);
    }

    public ArraySeason(ArraySeason<T> original) {
        screenplay = original.screenplay;
        final int originalSize = original.size();
        data = new ArrayTape<>(originalSize + 4);
        for (int i = 0; i < originalSize; i++) {
            ArrayMovie<T> tile = original.data.get(i);
            ArrayMovie copy = screenplay.buildMovie();
            if (tile != null) {
                copy.addAll(tile);
            }
            data.add(copy);
        }
        size = original.size();
        final int fac = screenplay.getFactor(originalSize);
        maxEpisodeSize = screenplay.getMaxEpisodeSize(fac);
        minEpisodeGlue = screenplay.getMinEpisodeGlue(fac);
        updateCounter = 0;
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
        episode.add(element);
        size++;
        this.updateCounter++;
        if (episode.size() > maxEpisodeSize) {
            splitOrGlue();
        }
        return true;
    }

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

    @Override
    public boolean add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
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

    @Override
    public T get(int index) {
        return getLeafWalkerAtIndex(index).next();
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Season is empty.");
        }
        return (T) data.first().first();
    }

    @Override
    public T last() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Season is empty.");
        }
        return (T) data.last().last();
    }

    @Override
    public boolean remove(Object element) {
        IteratorWalker<T> walker = getLeafWalkerAtElement(element);
        if (walker == null) {
            return false;
        }
        size--;
        this.updateCounter++;
        walker.removeNext();
        if (walker.size() < 8) {
            splitOrGlue();
        }
        return true;
    }

    @Override
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        IteratorWalker<T> walker = getLeafWalkerAtIndex(index);
        T removedElement = walker.removeNext();
        if (walker.isEmpty()) {
            splitOrGlue();
        }
        return removedElement;
    }

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
                data.add(i + 1, newEpisode);
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
        final int fac = screenplay.getFactor(data.size());
        maxEpisodeSize = screenplay.getMaxEpisodeSize(fac);
        minEpisodeGlue = screenplay.getMinEpisodeGlue(fac);
    }

    private int firstFreeEpisode() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).size() < maxEpisodeSize) {
                return i;
            }
        }
        return -1;
    }

    private IteratorWalker<T> getLeafWalkerAtIndex(int index) {
        if (lastEpisode != null
                && lastAccumulatedSize <= index
                && index < lastAccumulatedSize + lastEpisode.size()) {
            return new IterCoverWalker(this, lastEpisode.leafWalker(index - lastAccumulatedSize));
        }
        int accumulatedSize = 0;
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeSize = episode.size();
            if (index < accumulatedSize + episodeSize) {
                lastAccumulatedSize = accumulatedSize;
                lastEpisode = episode;
                return new IterCoverWalker(this, episode.leafWalker(index - accumulatedSize));
            }
            accumulatedSize += episodeSize;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private IteratorWalker<T> getLeafWalkerAtElement(Object element) {
        for (int i = 0; i < data.size(); i++) {
            final ArrayMovie<T> episode = data.get(i);
            int episodeIndex = episode.indexOf(element);
            if (episodeIndex >= 0) {
                return episode.leafWalker(episodeIndex);
            }
        }
        return null;
    }

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

    @Override
    public int size() {
        return size;
    }

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

    @Override
    public boolean contains(Object o) {
        for (ArrayMovie<T> episode : data) {
            if (episode.contains(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        final IterSeasonWalker<T> walker = new IterSeasonWalker<>(this);
        return new Iterator<T>() {
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

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        copyToArray(array, 0);
        return array;
    }

    @Override
    public <U> U[] toArray(U[] arr) {
        if (arr.length < size) {
            arr = (U[]) java.lang.reflect.Array.newInstance(arr.getClass().getComponentType(), size);
        }
        copyToArray(arr, 0);
        return arr;
    }

    @Override
    public void copyToArray(Object[] arr, int offset) {
        if (arr.length < size + offset) {
            throw new IndexOutOfBoundsException("My size + offset: " + (size + offset)
                    + ", Target array size: " + arr.length);
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

    @Override
    public boolean containsAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null");
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
            throw new NullPointerException("Collection cannot be null");
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
            throw new NullPointerException("Collection cannot be null");
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
        ArraySeason ret = new ArraySeason<T>();
        ret.screenplay = screenplay;
        ret.data.addAll(other);
        ret.updateSize();
        return ret;
    }

    protected void updateSize() {
        size = 0;
        for (int i = 0; i < data.size(); i++) {
            ArrayMovie<T> episode = data.get(i);
            size += episode.size();
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

}
