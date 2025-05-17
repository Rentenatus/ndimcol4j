/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public abstract class SortedSeasonSetWorkerFloat {

    boolean episodeDo(final SortedSeasonSetFloat caller, final ArrayMovieFloat episode, final float element) {
        return caller.workEpisode(this, episode, element);
    }

    boolean episodeToSmallDo(final SortedSeasonSetFloat caller, final float element) {
        return false;
    }

    boolean episodeToBigDo(final SortedSeasonSetFloat caller, final float element) {
        return false;
    }

    boolean elementPassedDo(final SortedSeasonSetFloat caller, final ArrayMovieFloat episode, final int index, final float element) {
        return false;
    }

    boolean elementEqualsDo(final SortedSeasonSetFloat caller, final ArrayMovieFloat episode, final int index, final float element) {
        return false;
    }

    boolean elementToSmallDo(final SortedSeasonSetFloat caller, final ArrayMovieFloat episode, final float element) {
        return false;
    }

    boolean elementToBigDo(final SortedSeasonSetFloat caller, final ArrayMovieFloat episode, final float element) {
        return false;
    }

}
