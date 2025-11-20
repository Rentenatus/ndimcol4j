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
 * A helpful class when working with ambiguities in SortedSeasonSet.
 *
 * @author Janusch Rentenatus
 * 
 * @param <T> the type of elements in this set
 */
public class BiPredicateAmbiguityIdentity<T> implements BiPredicate<T, T> {

    /**
     * A singleton instance of {@link BiPredicateAmbiguityEquals}.
     * <p>
     * This instance can be used wherever a reusable, stateless {@code BiPredicateAmbiguityEquals} implementation is
     * needed. It avoids creating multiple instances of the same predicate.
     */
    public final static BiPredicateAmbiguityIdentity INSTANCE = new BiPredicateAmbiguityIdentity();

    /**
     * Evaluates this predicate o1 == o2.
     *
     *
     * @param o1 the first input argument
     * @param o2 the second input argument
     * @return {@code true} if the inputs are the same, otherwise {@code false}
     *
     */
    @Override
    public boolean test(T o1, T o2) {
        return o1 == o2;
    }

}
