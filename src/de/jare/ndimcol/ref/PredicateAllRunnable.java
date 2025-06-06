/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;
import java.util.function.Predicate;

public class PredicateAllRunnable<T> implements Runnable {

    private final Predicate<? super T> predicate;
    private final ArrayMovie<T> episode;
    private ArrayMovie<T> elements;

    /**
     * Constructs a PredicateAllRunnable with the specified predicate and episode.
     *
     * @param predicate the predicate to be used for filtering
     * @param episode the ArrayMovie to be filtered
     */
    public PredicateAllRunnable(Predicate<? super T> predicate, ArrayMovie<T> episode) {
        this.predicate = predicate;
        this.episode = episode;
    }

    /**
     * Runs the filter operation on the episode using the predicate.
     */
    @Override
    public void run() {
        elements = episode.filterAll(predicate);
    }

    /**
     * Returns the ArrayMovie containing all elements that match the predicate.
     *
     * @return the ArrayMovie containing all elements that match the predicate
     */
    public ArrayMovie<T> getElements() {
        return elements;
    }

}
