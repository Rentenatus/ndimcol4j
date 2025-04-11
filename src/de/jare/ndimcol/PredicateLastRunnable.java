package de.jare.ndimcol;

import java.util.function.Predicate;

public class PredicateLastRunnable<T> implements Runnable{


        private final Predicate<? super T> predicate;
        private final ArrayMovie<T> episode;
        private IteratorWalker<T> walker;

    /**
     * Constructs a PredicateLastRunnable with the specified predicate and episode.
     * @param predicate the predicate to be used for filtering
     * @param episode the ArrayMovie to be filtered
     */
    public PredicateLastRunnable(Predicate<? super T> predicate, ArrayMovie<T> episode) {
        this.predicate = predicate;
        this.episode = episode;
    }

    /**
     * Runs the filter operation on the episode using the predicate.
     */
        @Override
        public void run() {
        walker = episode.filterLast(predicate);
    }

        /**
         * Returns the IteratorWalker containing the last element that matches the predicate.
         *
         * @return the IteratorWalker containing the last element that matches the predicate
         */
        public IteratorWalker<T> getWalker() {
        return walker;
    }

}
