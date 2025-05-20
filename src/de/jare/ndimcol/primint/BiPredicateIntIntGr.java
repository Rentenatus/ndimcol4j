/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.primint;

/**
 *
 * @author Janusch Rentenatus
 */
public class BiPredicateIntIntGr implements BiPredicateIntInt {

    /**
     * Evaluates this predicate e2 greater as e1.
     *
     *
     * @param e1 the first input argument
     * @param e2 the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     *
     */
    @Override
    public boolean test(int e1, int e2) {
        return e2 > e1;
    }

}
