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

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 *
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetInt extends ArraySeasonInt  {

    private final BiPredicateIntInt predicate;
    private final BiPredicateIntInt ambiguity;
    private final SortedSeasonSetWorkerInt workerAdd = new SortedSeasonSetWorkerAddInt();
    private final SortedSeasonSetWorkerInt workerRemove = new SortedSeasonSetWorkerRemoveInt();

    
     /**
     * Constructor for SortedSeasonSet.
     *
     */
    public SortedSeasonSetInt() {
        this.predicate = new BiPredicateIntIntGr();
        this.ambiguity = null;
    }

    /**
     * Constructor for SortedSeasonSet.
     *
     * @param predicate a BiPredicate&lt;T, T&gt; to compare elements. If A is not smaller than B and A is not greater
     * than B, then A is equal to B.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public SortedSeasonSetInt(final BiPredicateIntInt predicate) {
        this.predicate = predicate;
        this.ambiguity = null;
    }

    /**
     * Constructor for SortedSeasonSet.
     *
     * If A is not smaller than B and A is not greater than B, then A can still be unequal to B if ambiguity prevents
     * the test for equality. In this case, A and B are stored, with the order being random.
     *
     * @param predicate a BiPredicate&lt;T, T&gt; to compare elements in their order
     * @param ambiguity a BiPredicate&lt;T, T&gt; to compare elements in their equality
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public SortedSeasonSetInt(final BiPredicateIntInt predicate, final BiPredicateIntInt ambiguity) {
        this.predicate = predicate;
        this.ambiguity = ambiguity;
    }

    /**
     * Adds the specified element to this collection if it is not already present.
     *
     * @param element element whose presence in this collection is to be ensured
     * @return true if this collection changed as a result of the call
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean add(int element) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    protected boolean superAdd(int element) {
        return super.add(element);
    }

    /**
     * Adds the specified element to this collection at the specified index without check, if it is not already present.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @return true if this collection changed as a result of the call
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    protected boolean superAddAt(int index, int element) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    protected boolean work(SortedSeasonSetWorkerInt worker, int element) {

        int indexRData = data.size() - 1;
        final ArrayMovieInt rightData = data.get(indexRData);
        if (indexRData == 0) {
            return worker.episodeDo(this, rightData, element);
        }

        int right = rightData.get(rightData.size() - 1);
        if (predicate.test(right, element)) {
            return worker.episodeToBigDo(this, element);
        }
        final int candidateR = rightData.get(0);
        if (predicate.test(candidateR, element)) {
            return worker.episodeDo(this, rightData, element);
        } else if (!predicate.test(element, candidateR)) {
            return workElementEquals(worker, rightData, 0, element, candidateR);
        }

        final ArrayMovieInt leftData = data.get(0);
        int left = leftData.get(0);
        if (predicate.test(element, left)) {
            return worker.episodeToSmallDo(this, element);
        }
        if (predicate.test(element, leftData.get(leftData.size() - 1))) {
            return worker.episodeDo(this, leftData, element);
        }

        int indexLData = 0;
        while (indexLData < indexRData) {
            int indexMData = (indexLData + indexRData) >> 1;
            final ArrayMovieInt episode = data.get(indexMData);
            final int first = episode.get(0);
            if (predicate.test(element, first)) {
                indexRData = indexMData;
                if (indexLData + 1 == indexRData) {
                    return worker.episodeDo(this, data.get(indexLData), element);
                }
            } else if (predicate.test(first, element)) {
                indexLData = indexMData;
                if (indexLData + 1 == indexRData) {
                    final ArrayMovieInt post = data.get(indexRData);
                    if (predicate.test(post.get(0), element)) {
                        return worker.episodeDo(this, post, element);
                    }
                    // indexLData = indexMData ==> data.get(indexLData) == episode
                    return worker.episodeDo(this, episode, element);
                }
            } else {
                return workElementEquals(worker, episode, 0, element, first);
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    protected boolean workEpisode(SortedSeasonSetWorkerInt worker, final ArrayMovieInt episode, int element) {
        int left = episode.get(0);
        if (predicate.test(element, left)) {
            return worker.elementToSmallDo(this, episode, element);
        } else if (!predicate.test(left, element)) {
            return workElementEquals(worker, episode, 0, element, left);
        }
        int indexR = episode.size() - 1;
        int right = episode.get(indexR);
        if (predicate.test(right, element)) {
            return worker.elementToBigDo(this, episode, element);
        } else if (!predicate.test(element, right)) {
            return workElementEquals(worker, episode, indexR, element, right);
        }

        int indexL = 0;
        while (indexL < indexR) {
            if (indexL + 1 == indexR) {
                final int candidateR = episode.get(indexR);
                if (!predicate.test(element, candidateR)) {
                    return workElementEquals(worker, episode, indexR, element, candidateR);
                }
                final int candidateL = episode.get(indexL);
                if (!predicate.test(candidateL, element)) {
                    return workElementEquals(worker, episode, indexL, element, candidateL);
                }
                return worker.elementPassedDo(this, episode, indexR, element);
            }
            int indexM = (indexL + indexR) >> 1;
            final int candidate = episode.get(indexM);
            if (predicate.test(element, candidate)) {
                indexR = indexM;
            } else if (predicate.test(candidate, element)) {
                indexL = indexM;
            } else {
                return workElementEquals(worker, episode, indexM, element, candidate);
            }
        }
        return false;
    }

    public boolean workElementEquals(SortedSeasonSetWorkerInt worker, final ArrayMovieInt episode, int indexM, int element, int candidate) {
        if (ambiguity == null) {
            return worker.elementEqualsDo(this, episode, indexM, element);
        }
        if (ambiguity.test(element, candidate)) {
            return worker.elementEqualsDo(this, episode, indexM, element);
        }
        IterSeasonWalkerInt setWalker = new IterSeasonWalkerInt(this);
        // Look to the right:
        setWalker.gotoIndex(indexM, true);
        int move = 0;
        while (setWalker.hasNext()) {
            int next = setWalker.next();
            move++;
            if (predicate.test(element, next)) {
                break;
            } else if (ambiguity.test(element, next)) {
                return worker.elementEqualsDo(this, episode, indexM + move, element);
            }
        }
        // Look to the left:
        setWalker.gotoIndex(indexM, false);
        move = 0;
        while (setWalker.hasPrevious()) {
            int prev = setWalker.previous();
            move++;
            if (predicate.test(prev, element)) {
                break;
            } else if (ambiguity.test(element, prev)) {
                return worker.elementEqualsDo(this, episode, indexM - move, element);
            }
        }
        // Nothing found:
        return worker.elementPassedDo(this, episode, indexM, element);
    }

    /**
     * Adds all elements in the specified collection to this set.
     *
     * @param col collection containing elements to be added to this collection
     * @return true if this collection changed as a result of the call
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addAll(Collection<? extends Integer> col) {
        boolean changed = false;
        for (int element : col) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addAt(int index, int element) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public boolean addFirstFree(int element) {
        throw new UnsupportedOperationException("Not supported in " + getClass().getSimpleName() + ".");
    }

    /**
     * Remove the specified element from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     * @throws ClassCastException if the element is not of the same type as the elements in this set
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(int element) {
        return removeT(element);
    }

    /**
     * Remove the specified element of the type T from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean removeT(int element) {
        if (isEmpty()) {
            return false;
        }
        return work(workerRemove, element);
    }

    /**
     * Creates a new empty season with the same screenplay. The new movie is not a copy of this movie.
     *
     * @param initialCapacityOrZero not used
     * @return a new empty movie with the same screenplay
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public ArraySeasonInt emptyMovie(int initialCapacityOrZero) {
        return new SortedSeasonSetInt(predicate, ambiguity);
    }

    /**
     * Copy this an add all of ArrayMovie.
     *
     * @param os ArrayMovie
     * @return big set.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public SortedSeasonSetInt union(ArrayMovieInt os) {
        SortedSeasonSetInt set = new SortedSeasonSetInt(predicate, ambiguity);
        de.jare.ndimcol.ref.IterTapeWalker<ArrayMovieInt> dataWalker = data.softWalker();
        while (dataWalker.hasNext()) {
            set.addMovie(dataWalker.next().cloneMovie());
        }
        IteratorWalkerInt walker = os.softWalker();
        while (walker.hasNext()) {
            set.add(walker.next());
        }
        return set;
    }

}
