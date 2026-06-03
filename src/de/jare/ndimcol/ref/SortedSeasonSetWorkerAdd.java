/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.utils.SortedSeasonSetAddResult;
import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 * @param <T> the type of elements in this worker
 */
public class SortedSeasonSetWorkerAdd<T> extends SortedSeasonSetWorker<T> {

    private final SortedSeasonSetAddResult result = new SortedSeasonSetAddResult();

    public SortedSeasonSetAddResult getResult() {
        return result;
    }

    @Override
    boolean episodeLeftToSmallDo(final SortedSeasonSet<T> caller, final T element) {
        result.foundIndex = 0;
        return result.changed = caller.superAddAt(0, element);
    }

    @Override
    boolean episodeRightToBigDo(final SortedSeasonSet<T> caller, final T element) {
        result.foundIndex = caller.size();
        return result.changed = caller.superAdd(element);
    }

    @Override
    boolean elementToSmallDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        result.foundIndex = caller.getOffset(episode);
        result.changed = episode.addAt(0, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

    @Override
    boolean elementToBigDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        result.foundIndex = caller.getOffset(episode) + episode.size();
        result.changed = episode.add(element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

    @Override
    boolean elementPassedDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        result.foundIndex = caller.getOffset(episode) + index;
        result.changed = episode.addAt(index, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

}
