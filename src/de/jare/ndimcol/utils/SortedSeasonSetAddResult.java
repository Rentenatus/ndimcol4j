/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.utils;

/**
 *
 * @author Jansuch Rentenatus
 */
public class SortedSeasonSetAddResult {

    public boolean changed = false;
    public int foundIndex = -1;

    public final static SortedSeasonSetAddResult resultOfNull() {
        return new SortedSeasonSetAddResult();
    }

    public final static SortedSeasonSetAddResult resultOfEmpty(boolean add) {
        SortedSeasonSetAddResult ret = new SortedSeasonSetAddResult();
        ret.foundIndex = add ? 0 : -1;
        ret.changed = add;
        return ret;
    }
}
