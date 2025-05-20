/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetLong extends ArraySeasonLong  {

    private final BiPredicateLongLong predicate;
    private final SortedSeasonSetWorkerLong workerAdd = new SortedSeasonSetWorkerAddLong();
    private final SortedSeasonSetWorkerLong workerRemove = new SortedSeasonSetWorkerRemoveLong();

    
     /**
     * Constructor for SortedSeasonSet.
     *
     */
    public SortedSeasonSetLong() {
        this.predicate = new BiPredicateLongLongGr();
    }

    /**
     * Constructor for SortedSeasonSet.
     *
     * @param predicate a BiPredicate&lt;T, T&gt; to compare elements
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public SortedSeasonSetLong(final BiPredicateLongLong predicate) {
        this.predicate = predicate;
    }

    /**
     * Adds the specified element to this collection if it is not already present.
     *
     * @param element element whose presence in this collection is to be ensured
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(long element) {
        if (isEmpty()) {
            return super.add(element);
        }
        return work(workerAdd, element);
    }

    /**
     * Adds the specified element to this collection without check, if it is not already present.
     *
     * @param element element whose be added to this collection
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    protected boolean superAdd(long element) {
        return super.add(element);
    }

    /**
     * Adds the specified element to this collection at the specified index without check, if it is not already present.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    protected boolean superAddAt(int index, long element) {
        return super.addAt(index, element);
    }

    /**
     * Run the worker on the element. A Worker is a class that implements the SortedSeasonSetWorker interface. The
     * worker can be used to perform different operations on the element, such as adding or removing it from the
     * SortedSeasonSet.
     *
     * @param worker the worker to be used
     * @param element the element to be processed
     * @return true if the worker was able to process the element, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    protected boolean work(SortedSeasonSetWorkerLong worker, long element) {

        int indexRData = data.size() - 1;
        final ArrayMovieLong rightData = data.get(indexRData);
        if (indexRData == 0) {
            return worker.episodeDo(this, rightData, element);
        }

        long right = rightData.get(rightData.size() - 1);
        if (predicate.test(right, element)) {
            return worker.episodeToBigDo(this, element);
        }
        if (predicate.test(rightData.get(0), element)) {
            return worker.episodeDo(this, rightData, element);
        } else if (!predicate.test(element, rightData.get(0))) {
            return worker.elementEqualsDo(this, rightData, 0, element);
        }

        final ArrayMovieLong leftData = data.get(0);
        long left = leftData.get(0);
        if (predicate.test(element, left)) {
            return worker.episodeToSmallDo(this, element);
        }
        if (predicate.test(element, leftData.get(leftData.size() - 1))) {
            return worker.episodeDo(this, leftData, element);
        }

        int indexLData = 0;
        while (indexLData < indexRData) {
            int indexMData = (indexLData + indexRData) >> 1;
            final ArrayMovieLong episode = data.get(indexMData);
            final long first = episode.get(0);
            if (predicate.test(element, first)) {
                indexRData = indexMData;
                if (indexLData + 1 == indexRData) {
                    return worker.episodeDo(this, data.get(indexLData), element);
                }
            } else if (predicate.test(first, element)) {
                indexLData = indexMData;
                if (indexLData + 1 == indexRData) {
                    final ArrayMovieLong post = data.get(indexRData);
                    if (predicate.test(post.get(0), element)) {
                        return worker.episodeDo(this, post, element);
                    }
                    // indexLData = indexMData ==> data.get(indexLData) == episode
                    return worker.episodeDo(this, episode, element);
                }
            } else {
                return worker.elementEqualsDo(this, episode, 0, element);
            }
        }
        return false;
    }

    /**
     * Work on the episode. The episode is a part of the data structure that contains the elements. The worker is used
     * to perform different operations on the episode, such as adding or removing an element from the SortedSeasonSet.
     *
     * @param worker the worker to be used
     * @param episode the episode to be processed
     * @param element the element to be processed
     * @return true if the worker was able to process the episode, false otherwise
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    protected boolean workEpisode(SortedSeasonSetWorkerLong worker, final ArrayMovieLong episode, long element) {
        long left = episode.get(0);
        if (predicate.test(element, left)) {
            return worker.elementToSmallDo(this, episode, element);
        } else if (!predicate.test(left, element)) {
            return worker.elementEqualsDo(this, episode, 0, element);
        }
        int indexR = episode.size() - 1;
        long right = episode.get(indexR);
        if (predicate.test(right, element)) {
            return worker.elementToBigDo(this, episode, element);
        } else if (!predicate.test(element, right)) {
            return worker.elementEqualsDo(this, episode, indexR, element);
        }

        int indexL = 0;
        while (indexL < indexR) {
            if (indexL + 1 == indexR) {
                if (!predicate.test(element, episode.get(indexR))) {
                    return worker.elementEqualsDo(this, episode, indexR, element);
                }
                if (!predicate.test(episode.get(indexL), element)) {
                    return worker.elementEqualsDo(this, episode, indexL, element);
                }
                return worker.elementPassedDo(this, episode, indexR, element);
            }
            int indexM = (indexL + indexR) >> 1;
            final long candidate = episode.get(indexM);
            if (predicate.test(element, candidate)) {
                indexR = indexM;
            } else if (predicate.test(candidate, element)) {
                indexL = indexM;
            } else {
                return worker.elementEqualsDo(this, episode, indexM, element);
            }
        }
        return false;
    }

    /**
     * Adds all elements in the specified collection to this set.
     *
     * @param col collection containing elements to be added to this collection
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addAll(Collection<? extends Long> col) {
        boolean changed = false;
        for (long element : col) {
            if (add(element)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Adds the specified element to this set at the specified index.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addAt(int index, long element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index > 0 && !predicate.test(get(index - 1), element)) {
            return false;
        }
        final int nextIndex = index + 1;
        if (nextIndex < size() && !predicate.test(element, get(nextIndex))) {
            return false;
        }
        return super.addAt(index, element);
    }

    /**
     * Adding the specified element to this set at the first free index is not supported.
     *
     * @param element the element to be added
     * @return nothing
     * @throws UnsupportedOperationException always
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addFirstFree(long element) {
        throw new UnsupportedOperationException("Not supported in " + getClass().getSimpleName() + ".");
    }

    /**
     * Remove the specified element from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     * @throws ClassCastException if the element is not of the same type as the elements in this set
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(long element) {
        return removeT(element);
    }

    /**
     * Remove the specified element of the type T from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public boolean removeT(long element) {
        if (isEmpty()) {
            return false;
        }
        return work(workerRemove, element);
    }

}
