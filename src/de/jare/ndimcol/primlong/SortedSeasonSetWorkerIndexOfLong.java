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

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetWorkerIndexOfLong extends SortedSeasonSetWorkerLong {

    int episodenIndex = -1;
    ArrayMovieLong episode = null;
    long found;

    public SortedSeasonSetWorkerIndexOfLong restart() {
        episodenIndex = -1;
        episode = null;
        return this;
    }

    @Override
    boolean elementEqualsDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long candidate) {
        this.episodenIndex = index;
        this.episode = episode;
        this.found = candidate;
        return true;
    }

    public int getEpisodenIndex() {
        return episodenIndex;
    }

    public int getIndex(final SortedSeasonSetLong caller) {
        return caller.getOffset(episode) + episodenIndex;
    }

    public ArrayMovieLong getEpisode() {
        return episode;
    }

    public long getFound() {
        return found;
    }

}
