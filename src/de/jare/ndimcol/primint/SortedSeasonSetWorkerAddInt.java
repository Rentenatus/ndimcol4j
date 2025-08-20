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

/**
 *
 * @author Jansuch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetWorkerAddInt extends SortedSeasonSetWorkerInt {

    @Override
    boolean episodeToSmallDo(final SortedSeasonSetInt caller, final int element) {
        return caller.superAddAt(0, element);
    }

    @Override
    boolean episodeToBigDo(final SortedSeasonSetInt caller, final int element) {
        return caller.superAdd(element);
    }

    @Override
    boolean elementToSmallDo(final SortedSeasonSetInt caller, final ArrayMovieInt episode, final int element) {
        episode.addAt(0, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }

    @Override
    boolean elementToBigDo(final SortedSeasonSetInt caller, final ArrayMovieInt episode, final int element) {
        episode.add(element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }

    @Override
    boolean elementPassedDo(final SortedSeasonSetInt caller, final ArrayMovieInt episode, final int index, final int element) {
        episode.addAt(index, element);
        caller.size++;
        if (episode.size() > caller.maxEpisodeSize) {
            caller.splitOrGlue();
        }
        return true;
    }
}
