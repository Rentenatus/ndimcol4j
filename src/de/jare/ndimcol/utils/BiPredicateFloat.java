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
public class BiPredicateFloat implements BiPredicate<Float, Float> {

    /**
     * A singleton instance of {@link BiPredicateFloat}.
     * <p>
     * This instance can be used wherever a reusable, stateless {@code BiPredicateFloat} implementation is needed. It
     * avoids creating multiple instances of the same predicate.
     */
    public final static BiPredicateFloat INSTANCE = new BiPredicateFloat();

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
    public boolean test(Float o1, Float o2) {
        return o2.floatValue() > o1.floatValue();
    }

}
