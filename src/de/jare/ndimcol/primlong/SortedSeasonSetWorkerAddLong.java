/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import de.jare.ndimcol.utils.SortedSeasonSetAddResult;
import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetWorkerAddLong extends SortedSeasonSetWorkerLong {

    private final SortedSeasonSetAddResult result = new SortedSeasonSetAddResult();

    public SortedSeasonSetAddResult getResult() {
        return result;
    }

    @Override
    boolean episodeLeftToSmallDo(final SortedSeasonSetLong caller, final long element) {
        result.foundIndex = 0;
        return result.changed = caller.superAddAt(0, element);
    }

    @Override
    boolean episodeRightToBigDo(final SortedSeasonSetLong caller, final long element) {
        result.foundIndex = caller.size();
        return result.changed = caller.superAdd(element);
    }

    @Override
    boolean elementToSmallDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        result.foundIndex = caller.getOffset(episode);
        result.changed = episode.addAt(0, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

    @Override
    boolean elementToBigDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        result.foundIndex = caller.getOffset(episode) + episode.size();
        result.changed = episode.add(element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

    @Override
    boolean elementPassedDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        result.foundIndex = caller.getOffset(episode) + index;
        result.changed = episode.addAt(index, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return result.changed;
    }

    @Override
    boolean elementEqualsDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        result.foundIndex = caller.getOffset(episode) + index;
        return false;
    }

}
