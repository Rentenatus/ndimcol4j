/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiPredicate;

/**
 *
 * @author Janusch Rentenatus
 * @param <T>
 */
public class SortedSeasonSet<T> extends ArraySeason<T> implements Collection<T> {

    private BiPredicate<T, T> predicate;
    private final SortedSeasonSetWorker<T> workerAdd = new SortedSeasonSetWorkerAdd<>();
    private final SortedSeasonSetWorker<T> workerRemove = new SortedSeasonSetWorkerRemove<>();

    public SortedSeasonSet(final Comparator<T> compT, boolean forward) {
        this.predicate = forward
                ? (T element1, T element2) -> compT.compare(element1, element2) < 0
                : (T element1, T element2) -> compT.compare(element1, element2) > 0;
    }

    public SortedSeasonSet(final BiPredicate<T, T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (isEmpty()) {
            return super.add(element);
        }
        return work(workerAdd, element);
    }

    protected boolean superAdd(T element) {
        return super.add(element);
    }

    protected boolean superAdd(int index, T element) {
        return super.add(index, element);
    }

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
        if (predicate.test(rightData.get(0), element)) {
            return worker.episodeDo(this, rightData, element);
        } else if (!predicate.test(element, rightData.get(0))) {
            return worker.elementEqualsDo(this, rightData, 0, element);
        }

        final ArrayMovie<T> leftData = data.get(0);
        T left = leftData.get(0);
        if (predicate.test(element, left)) {
            return worker.episdoeToSmallDo(this, element);
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
                return worker.elementEqualsDo(this, episode, 0, element);
            }
        }
        return false;
    }

    protected boolean workEpisode(SortedSeasonSetWorker<T> worker, final ArrayMovie<T> episode, T element) {
        T left = episode.get(0);
        if (predicate.test(element, left)) {
            return worker.elementToSmallDo(this, episode, element);
        } else if (!predicate.test(left, element)) {
            return worker.elementEqualsDo(this, episode, 0, element);
        }
        int indexR = episode.size() - 1;
        T right = episode.get(indexR);
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
            final T candidate = episode.get(indexM);
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

    @Override
    public boolean add(int index, T element) {
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
        return super.add(index, element);
    }

    @Override
    public boolean addFirstFree(T element) {
        throw new UnsupportedOperationException("Not supported in " + getClass().getSimpleName() + ".");
    }

    @Override
    public boolean remove(Object element) {
        if (element == null) {
            return false;
        }
        return removeT((T) element);

    }

    public boolean removeT(T element) {
        if (isEmpty()) {
            return false;
        }
        boolean ret = work(workerRemove, element);
        return ret;
    }

}
