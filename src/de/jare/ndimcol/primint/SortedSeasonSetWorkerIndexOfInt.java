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

    int index = -1;
    ArrayMovieInt episode = null;

    @Override
    boolean elementEqualsDo(final SortedSeasonSetInt caller, final ArrayMovieInt episode, final int index, final int element) {
        this.index = index;
        this.episode = episode;
        return true;
    }

    public int getIndex() {
        return index;
    }

    public ArrayMovieInt getEpisode() {
        return episode;
    }
    
    
}
