/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this set
 */
public class SortedSeasonSet<T> extends ArraySeason<T> implements Set<T> {

    private final BiPredicate<T, T> predicate;
    private final BiPredicate<T, T> ambiguity;
    private final SortedSeasonSetWorker<T> workerAdd = new SortedSeasonSetWorkerAdd<>();
    private final SortedSeasonSetWorker<T> workerRemove = new SortedSeasonSetWorkerRemove<>();
    private final SortedSeasonSetWorkerIndexOf<T> workerIndexOf = new SortedSeasonSetWorkerIndexOf<>();

//noprim.start  
    /**
     * Constructor for SortedSeasonSet. The given comparator is morphed into a BiPredicate. The logic of this set is
     * only on element1 &lt element2 xor element1 &gt element2 implemented.
     *
     * @param compT a Comparator&lt;T&gt; to compare elements
     * @param forward true for ascending order, false for descending order
     */
    public SortedSeasonSet(final Comparator<T> compT, boolean forward) {
        this.predicate = forward
                ? (T element1, T element2) -> compT.compare(element1, element2) < 0
                : (T element1, T element2) -> compT.compare(element1, element2) > 0;
        this.ambiguity = null;
    }
//noprim.end
//prim:    
//prim:     /**
//prim:     * Constructor for SortedSeasonSet.
//prim:     *
//prim:     */
//prim:    public SortedSeasonSet_APPEND_() {
//prim:        this.predicate = new BiPredicate_APPEND__APPEND_Gr();
//prim:        this.ambiguity = null;
//prim:    }
//prim.ende

    /**
     * Constructor for SortedSeasonSet.
     *
     * @param predicate a BiPredicate&lt;T, T&gt; to compare elements. If A is not smaller than B and A is not greater
     * than B, then A is equal to B.
     */
    public SortedSeasonSet(final BiPredicate<T, T> predicate) {
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
    public SortedSeasonSet(final BiPredicate<T, T> predicate, final BiPredicate<T, T> ambiguity) {
        this.predicate = predicate;
        this.ambiguity = ambiguity;
    }

    /**
     * Adds the specified element to this collection if it is not already present.
     *
     * @param element element whose presence in this collection is to be ensured
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean add(T element) {
        //noprim.start  
        if (element == null) {
            return false;
        }
        //noprim.end  
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
    protected boolean superAdd(T element) {
        return super.add(element);
    }

    /**
     * Adds the specified element to this collection at the specified index without check, if it is not already present.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @return true if this collection changed as a result of the call
     */
    protected boolean superAddAt(int index, T element) {
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
    protected boolean work(SortedSeasonSetWorker<T> worker, T element) {

        int indexRData = data.size() - 1;
        final ArrayMovie<T> rightData = data.get(indexRData);
        if (indexRData == 0) {
            return worker.episodeDo(this, rightData, element);
        }

        T right = rightData.get(rightData.size() - 1);
        if (predicate.test(right, element)) {
            return worker.episodeToBigDo(this, element);
        }
        final T candidateR = rightData.get(0);
        if (predicate.test(candidateR, element)) {
            return worker.episodeDo(this, rightData, element);
        } else if (!predicate.test(element, candidateR)) {
            return workElementEquals(worker, rightData, 0, element, candidateR);
        }

        final ArrayMovie<T> leftData = data.get(0);
        T left = leftData.get(0);
        if (predicate.test(element, left)) {
            return worker.episodeToSmallDo(this, element);
        }
        if (predicate.test(element, leftData.get(leftData.size() - 1))) {
            return worker.episodeDo(this, leftData, element);
        }

        int indexLData = 0;
        while (indexLData < indexRData) {
            int indexMData = (indexLData + indexRData) >> 1;
            final ArrayMovie<T> episode = data.get(indexMData);
            final T first = episode.get(0);
            if (predicate.test(element, first)) {
                indexRData = indexMData;
                if (indexLData + 1 == indexRData) {
                    return worker.episodeDo(this, data.get(indexLData), element);
                }
            } else if (predicate.test(first, element)) {
                indexLData = indexMData;
                if (indexLData + 1 == indexRData) {
                    final ArrayMovie<T> post = data.get(indexRData);
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
    protected boolean workEpisode(SortedSeasonSetWorker<T> worker, final ArrayMovie<T> episode, T element) {
        T left = episode.get(0);
        if (predicate.test(element, left)) {
            return worker.elementToSmallDo(this, episode, element);
        } else if (!predicate.test(left, element)) {
            return workElementEquals(worker, episode, 0, element, left);
        }
        int indexR = episode.size() - 1;
        T right = episode.get(indexR);
        if (predicate.test(right, element)) {
            return worker.elementToBigDo(this, episode, element);
        } else if (!predicate.test(element, right)) {
            return workElementEquals(worker, episode, indexR, element, right);
        }

        int indexL = 0;
        while (indexL < indexR) {
            if (indexL + 1 == indexR) {
                final T candidateR = episode.get(indexR);
                if (!predicate.test(element, candidateR)) {
                    return workElementEquals(worker, episode, indexR, element, candidateR);
                }
                final T candidateL = episode.get(indexL);
                if (!predicate.test(candidateL, element)) {
                    return workElementEquals(worker, episode, indexL, element, candidateL);
                }
                return worker.elementPassedDo(this, episode, indexR, element);
            }
            int indexM = (indexL + indexR) >> 1;
            final T candidate = episode.get(indexM);
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

    public boolean workElementEquals(SortedSeasonSetWorker<T> worker, final ArrayMovie<T> episode, int indexM, T element, T candidate) {
        if (ambiguity == null) {
            return worker.elementEqualsDo(this, episode, indexM, candidate);
        }
        if (ambiguity.test(element, candidate)) {
            return worker.elementEqualsDo(this, episode, indexM, candidate);
        }
        IterSeasonWalker<T> setWalker = new IterSeasonWalker<>(this);
        // Look to the right:
        setWalker.gotoIndex(indexM, true);
        int move = 0;
        while (setWalker.hasNext()) {
            T next = setWalker.next();
            move++;
            if (predicate.test(element, next)) {
                break;
            } else if (ambiguity.test(element, next)) {
                return worker.elementEqualsDo(this, episode, indexM + move, next);
            }
        }
        // Look to the left:
        setWalker.gotoIndex(indexM, false);
        move = 0;
        while (setWalker.hasPrevious()) {
            T prev = setWalker.previous();
            move++;
            if (predicate.test(prev, element)) {
                break;
            } else if (ambiguity.test(element, prev)) {
                return worker.elementEqualsDo(this, episode, indexM - move, prev);
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
    @Override
    public boolean addAll(Collection<? extends T> col) {
        boolean changed = false;
        for (T element : col) {
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
    @Override
    public boolean addAt(int index, T element) {
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
    @Override
    public boolean addFirstFree(T element) {
        throw new UnsupportedOperationException("Not supported in " + getClass().getSimpleName() + ".");
    }

    /**
     * Perform a brute force search.
     *
     * It uses equals comparison like any other standard list.
     *
     * Returns the index of the first occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the specified element, or -1 if the element is not found
     */
    public int indexOfByBruteForce(Object element) {
        return super.indexOf(element);
    }

    /**
     * Performs a search in sequential order. Uses interval nesting.
     *
     * It uses test of ambiguity if this predicate is set.
     *
     * Returns the index of the first occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the specified element, or -1 if the element is not found
     */
    @Override
    public int indexOf(Object element) {
        if (isEmpty()) {
            return -1;
        }
        boolean found = work(workerIndexOf, (T) element);
        return found ? workerIndexOf.getIndex() : -1;
    }

    /**
     * Performs a search in sequential order. Uses interval nesting.
     *
     * It uses test of ambiguity if this predicate is set.
     *
     * Returns the first object that occupies the space of the object being searched for (it does not have to be the
     * same object), or null if the element is not found. If the specified element is null, it returns null.
     *
     * @param element the like element to search for
     * @return Returns the first object that occupies the space of the object being searched for (it does not have to
     * be, or null if the element is not found the same object).
     */
    public T get(Object element) {
        if (isEmpty() || element == null) {
            return null;
        }
        boolean found = work(workerIndexOf, (T) element);
        return found ? workerIndexOf.getFound() : null;
    }

    /**
     * Returns the walker at the specified element in this collection.
     *
     * Perform a brute force search.
     *
     * It uses equals comparison like any other standard list.
     *
     * @param element the element to search for
     * @return the walker at the specified element, or null if element not found
     */
    public IteratorWalker<T> getWalkerAtElementByBruteForce(final Object element) {
        return super.getWalkerAtElement(element);
    }

    /**
     * Performs a search in sequential order. Uses interval nesting.
     *
     * It uses test of ambiguity if this predicate is set.
     *
     * Returns the walker at the specified element in this collection.
     *
     * @param element the element to search for
     * @return the walker at the specified element, or null if element not found
     */
    @Override
    public IteratorWalker<T> getWalkerAtElement(final Object element) {
        if (isEmpty()) {
            return null;
        }
        boolean found = work(workerIndexOf, (T) element);
        if (!found) {
            return null;
        }
        ArrayMovie<T> episode = workerIndexOf.getEpisode();
        int accumulatedSize = 0;
        IterTapeWalker<ArrayMovie<T>> dataWalker = data.softWalker();
        while (dataWalker.hasNext()) {
            ArrayMovie<T> next = dataWalker.next();
            if (next == episode) {
                break;
            }
            accumulatedSize += next.size();
        }
        return new IterCoverWalker<>(this,
                workerIndexOf.getEpisode().leafWalker(
                        workerIndexOf.getIndex() - accumulatedSize));
    }

    /**
     * Remove the specified element from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     * @throws ClassCastException if the element is not of the same type as the elements in this set
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object element
    ) {
        //noprim.start  
        if (element == null) {
            return false;
        }
        return removeT((T) element);
        //noprim.end
        //prim:return removeT(element);
        //prim.end
    }

    /**
     * Remove the specified element of the type T from this set.
     *
     * @param element the element to be removed
     * @return true if this collection changed as a result of the call
     */
    public boolean removeT(T element) {
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
    @Override
    public ArraySeason<T> emptyMovie(int initialCapacityOrZero) {
        return new SortedSeasonSet<>(predicate, ambiguity);
    }

    /**
     * Copy this an add all of ArrayMovie.
     *
     * @param os ArrayMovie
     * @return big set.
     */
    public SortedSeasonSet<T> union(ArrayMovie<T> os) {
        SortedSeasonSet<T> set = new SortedSeasonSet<>(predicate, ambiguity);
        IterTapeWalker<ArrayMovie<T>> dataWalker = data.softWalker();
        while (dataWalker.hasNext()) {
            set.addMovie(dataWalker.next().cloneMovie());
        }
        IteratorWalker<T> walker = os.softWalker();
        while (walker.hasNext()) {
            set.add(walker.next());
        }
        return set;
    }

}
