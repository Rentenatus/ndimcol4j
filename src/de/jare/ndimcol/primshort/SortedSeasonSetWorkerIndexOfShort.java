/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primshort;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetWorkerIndexOfShort extends SortedSeasonSetWorkerShort {

    int index = -1;
    ArrayMovieShort episode = null;
    short found;

    @Override
    boolean elementEqualsDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final int index, final short candidate) {
        this.index = index;
        this.episode = episode;
        this.found = candidate;
        return true;
    }

    public int getIndex() {
        return index;
    }

    public ArrayMovieShort getEpisode() {
        return episode;
    }

    public short getFound() {
        return found;
    }

}
