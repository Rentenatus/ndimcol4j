/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Jansuch Rentenatus
 * @param <T> the type of elements in this worker
 */
public class SortedSeasonSetWorkerIndexOf<T> extends SortedSeasonSetWorker<T> {

    int index = -1;
    ArrayMovie<T> episode = null;

    @Override
    boolean elementEqualsDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        this.index = index;
        this.episode = episode;
        return true;
    }

    public int getIndex() {
        return index;
    }

    public ArrayMovie<T> getEpisode() {
        return episode;
    }
    
    
}
