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
 * Sort over hash code.A hash set can be implemented using:
 *
 * {@code final BiPredicateAmbiguityIdentity<T> ambiguity = new BiPredicateAmbiguityIdentity<>();}
 * {@code final BiPredicateHashGr<T> predicate = new BiPredicateHashGr<>();}
 * {@code SortedSeasonSet<T> setHash = new SortedSeasonSet<>(predicate, ambiguity);}
 *
 * The elements are sorted according to their hash code using `BiPredicateHashGr`.
 *
 *
 * The hash search is then performed using interval nesting.
 *
 * The order of objects with the same hash code is random. Ambiguity is described using `BiPredicateAmbiguityIdentity`.
 *
 * The value of this solution is that the objects do not need to be wrapped in objects of a tree search or similar,
 * resulting in fewer objects in the virtual machine.
 *
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this predicate
 */
public class BiPredicateHashGr<T> implements BiPredicate<T, T> {

    /**
     * Evaluates this hashCode of o2 greater as hashCode of o1.
     *
     *
     * @param o1 the first input argument
     * @param o2 the second input argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     *
     */
    @Override
    public boolean test(T o1, T o2) {
        return o2.hashCode() > o1.hashCode();
    }

}
