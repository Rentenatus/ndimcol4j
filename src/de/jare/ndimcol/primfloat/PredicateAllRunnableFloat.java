/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primfloat;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.function.Predicate;

public class PredicateAllRunnableFloat implements Runnable {

    private final Predicate<? super Float> predicate;
    private final ArrayMovieFloat episode;
    private ArrayMovieFloat elements;

    /**
     * Constructs a PredicateAllRunnable with the specified predicate and episode.
     *
     * @param predicate the predicate to be used for filtering
     * @param episode the ArrayMovie to be filtered
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public PredicateAllRunnableFloat(Predicate<? super Float> predicate, ArrayMovieFloat episode) {
        this.predicate = predicate;
        this.episode = episode;
    }

    /**
     * Runs the filter operation on the episode using the predicate.
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    @Override
    public void run() {
        elements = episode.filterAll(predicate);
    }

    /**
     * Returns the ArrayMovie containing all elements that match the predicate.
     *
     * @return the ArrayMovie containing all elements that match the predicate
     */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
    public ArrayMovieFloat getElements() {
        return elements;
    }

}
