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

    int episodenIndex = -1;
    ArrayMovie<T> episode = null;
    T found;

    public SortedSeasonSetWorkerIndexOf<T> restart() {
        episodenIndex = -1;
        episode = null;
        return this;
    }

    @Override
    boolean elementEqualsDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T candidate) {
        this.episodenIndex = index;
        this.episode = episode;
        this.found = candidate;
        return true;
    }

    public int getEpisodenIndex() {
        return episodenIndex;
    }

    public int getIndex(final SortedSeasonSet<T> caller) {
        return caller.getOffset(episode) + episodenIndex;
    }

    public ArrayMovie<T> getEpisode() {
        return episode;
    }

    public T getFound() {
        return found;
    }

}
