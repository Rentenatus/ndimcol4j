/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 * @param <T> the type of elements in this worker
 */
public class SortedSeasonSetWorkerAdd<T> extends SortedSeasonSetWorker<T> {

    @Override
    boolean episodeToSmallDo(final SortedSeasonSet<T> caller, final T element) {
        return caller.superAddAt(0, element);
    }

    @Override
    boolean episodeToBigDo(final SortedSeasonSet<T> caller, final T element) {
        return caller.superAdd(element);
    }

    @Override
    boolean elementToSmallDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        episode.addAt(0, element);
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
        episode.addAt(index, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }
}
