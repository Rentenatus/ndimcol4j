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

    int index = -1;
    ArrayMovieLong episode = null;
    long found;

    @Override
    boolean elementEqualsDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long candidate) {
        this.index = index;
        this.episode = episode;
        this.found = candidate;
        return true;
    }

    public int getIndex() {
        return index;
    }

    public ArrayMovieLong getEpisode() {
        return episode;
    }

    public long getFound() {
        return found;
    }

}
