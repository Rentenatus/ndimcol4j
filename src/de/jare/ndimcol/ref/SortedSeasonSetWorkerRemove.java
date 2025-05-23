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
public class SortedSeasonSetWorkerRemove<T> extends SortedSeasonSetWorker<T> {

    @Override
    boolean elementEqualsDo(final SortedSeasonSet<T> caller, final ArrayMovie<T> episode, final int index, final T element) {
        T rem = episode.removeAt(index);
        //prim: if (rem!=element) {
        if (!rem.equals(element)) {
            System.out.println(rem + ":::" + element);
        }
        caller.size--;
        if (episode.size() < 8) {
            caller.splitOrGlue();
        }
        return true;
    }
}
