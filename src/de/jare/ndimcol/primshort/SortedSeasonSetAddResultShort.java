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

/**
 *
 * @author Jansuch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class SortedSeasonSetAddResultShort {

    boolean changed = false;
    boolean replaced = false;
    int foundIndex = -1;
    short oldElement;


    final static SortedSeasonSetAddResultShort resultOfEmpty(boolean add) {
        SortedSeasonSetAddResultShort ret = new SortedSeasonSetAddResultShort();
        ret.foundIndex = add ? 0 : -1;
        ret.changed = add;
        return ret;
    }
}
