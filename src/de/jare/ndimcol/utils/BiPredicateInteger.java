/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.utils;

import java.util.function.BiPredicate;

/**
 *
 * @author Janusch Rentenatus
 */
public class BiPredicateInteger implements BiPredicate<Integer, Integer> {

    /**
     * A singleton instance of {@link BiPredicateInteger}.
     * <p>
     * This instance can be used wherever a reusable, stateless {@code BiPredicateInteger} implementation is needed. It
     * avoids creating multiple instances of the same predicate.
     */
    public final static BiPredicateInteger INSTANCE = new BiPredicateInteger();

    /**
     * Evaluates this predicate e2 greater as e1.
     *
     *
     * @param o1 the first input argument
     * @param o2 the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     *
     */
    @Override
    public boolean test(Integer o1, Integer o2) {
        return o2.intValue() > o1.intValue();
    }

}
