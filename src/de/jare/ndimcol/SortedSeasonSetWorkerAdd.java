/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 *
 * @author Jansuch Rentenatus
 */
class SortedSeasonSetWorkerAdd<T> extends SortedSeasonSetWorker<T> {

    @Override
    boolean episdoeToSmallDo(final SortedSeasonSet<T> caller, final T element) {
        return caller.superAdd(0, element);
    }

    @Override
    boolean episodeToBigDo(final SortedSeasonSet<T> caller, final T element) {
        return caller.superAdd(element);
    }

    @Override
    boolean elementToSmallDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        episode.add(0, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }

    @Override
    boolean elementToBigDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        episode.add(element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }

    @Override
    boolean elementPassedDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        episode.add(index, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }
}
