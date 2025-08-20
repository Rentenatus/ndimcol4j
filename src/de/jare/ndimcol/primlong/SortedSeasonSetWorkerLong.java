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
public abstract class SortedSeasonSetWorkerLong {

    boolean episodeDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final long element) {
        return caller.workEpisode(this, episode, element);
    }

    boolean episodeToSmallDo(final SortedSeasonSetLong caller, final long element) {
        return false;
    }

    boolean episodeToBigDo(final SortedSeasonSetLong caller, final long element) {
        return false;
    }

    boolean elementPassedDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        return false;
    }

    boolean elementEqualsDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final int index, final long element) {
        return false;
    }

    boolean elementToSmallDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final long element) {
        return false;
    }

    boolean elementToBigDo(final SortedSeasonSetLong caller, final ArrayMovieLong episode, final long element) {
        return false;
    }

}
