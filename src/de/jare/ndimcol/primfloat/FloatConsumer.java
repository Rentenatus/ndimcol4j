/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.primfloat;

import java.util.Objects;

/**
 * FloatConsumer is unfortunately not available in java.util.function
 *
 *
 * Represents an operation that accepts a single {@code float}-valued argument and returns no result. This is the
 * primitive type specialization of {@link Consumer} for {@code float}. Unlike most other functional interfaces,
 * {@code FloatConsumer} is expected to operate via side-effects.
 *
 */
// #### This class has exceptionally not been generated in this package.
@FunctionalInterface
public interface FloatConsumer {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(float value);

    /**
     * Returns a composed {@code FloatConsumer} that performs, in sequence, this operation followed by the {@code after}
     * operation. If performing either operation throws an exception, it is relayed to the caller of the composed
     * operation. If performing this operation throws an exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FloatConsumer} that performs in sequence this operation followed by the {@code after}
     * operation
     * @throws NullPointerException if {@code after} is null
     */
    default FloatConsumer andThen(FloatConsumer after) {
        Objects.requireNonNull(after);
        return (float t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
