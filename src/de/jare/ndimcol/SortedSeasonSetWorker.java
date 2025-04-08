/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 *
 * @author Jansuch Rentenatus
 * @param <T> the type of elements in this worker
 */
public abstract class SortedSeasonSetWorker<T> {

    boolean episodeDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        return caller.workEpisode(this, episode, element);
    }

    boolean episdoeToSmallDo(final SortedSeasonSet<T> caller, final T element) {
        return false;
    }

    boolean episodeToBigDo(final SortedSeasonSet<T> caller, final T element) {
        return false;
    }

    boolean elementPassedDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        return false;
    }

    boolean elementEqualsDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        return false;
    }

    boolean elementToSmallDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        return false;
    }

    boolean elementToBigDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final T element) {
        return false;
    }

}
