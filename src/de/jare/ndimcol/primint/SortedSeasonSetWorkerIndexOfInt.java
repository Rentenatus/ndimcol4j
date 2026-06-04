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
public class SortedSeasonSetWorkerIndexOfInt extends SortedSeasonSetWorkerInt {

    int episodenIndex = -1;
    ArrayMovieInt episode = null;
    int found;

    public SortedSeasonSetWorkerIndexOfInt restart() {
        episodenIndex = -1;
        episode = null;
        return this;
    }

    @Override
    boolean elementEqualsDo(final SortedSeasonSetInt caller, final ArrayMovieInt episode, final int index, final int candidate) {
        this.episodenIndex = index;
        this.episode = episode;
        this.found = candidate;
        return true;
    }

    public int getEpisodenIndex() {
        return episodenIndex;
    }

    public int getIndex(final SortedSeasonSetInt caller) {
        return caller.getOffset(episode) + episodenIndex;
    }

    public ArrayMovieInt getEpisode() {
        return episode;
    }

    public int getFound() {
        return found;
    }

}
