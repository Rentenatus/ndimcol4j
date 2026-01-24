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
public abstract class SortedSeasonSetWorkerShort {

    boolean episodeDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final short element) {
        return caller.workEpisode(this, episode, element);
    }

    boolean episodeToSmallDo(final SortedSeasonSetShort caller, final short element) {
        return false;
    }

    boolean episodeToBigDo(final SortedSeasonSetShort caller, final short element) {
        return false;
    }

    boolean elementPassedDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final int index, final short element) {
        return false;
    }

    boolean elementEqualsDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final int index, final short element) {
        return false;
    }

    boolean elementToSmallDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final short element) {
        return false;
    }

    boolean elementToBigDo(final SortedSeasonSetShort caller, final ArrayMovieShort episode, final short element) {
        return false;
    }

}
