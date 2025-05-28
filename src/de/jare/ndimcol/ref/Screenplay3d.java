/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Janusch Rentenatus
 */
public class Screenplay3d implements Screenplay {

    public final static Screenplay INSTANCE = new Screenplay3d();
    private final Screenplay3dInner inner = new Screenplay3dInner();

    static final int DEFAULT_SIZE_3D = 24;
    static final int START_MAX_EPISODE_SIZE_3D = 8192;
    static final int START_MIN_EPISODE_GLUE_3D = 192;
    static final int DEFAULT_SIZE_3D_INNER = 12;
    static final int START_MAX_EPISODE_SIZE_3D_INNER = 512;
    static final int START_MIN_EPISODE_GLUE_3D_INNER = 128;
    static final int START_3D_SIZE = 4096;

    static class Screenplay3dInner implements Screenplay {

        @Override
        public int getDefaultSize() {
            return DEFAULT_SIZE_3D_INNER;
        }

        @Override
        public int getMaxEpisodeSize(final int fac) {
            return START_MAX_EPISODE_SIZE_3D_INNER + (fac << 1);
        }

        @Override
        public int getMinEpisodeGlue(final int fac) {
            return START_MIN_EPISODE_GLUE_3D_INNER + (fac >> 1);
        }

        @Override
        public <T> ArrayMovie<T> buildMovie(final int parentSize) {
            return new ArrayTape<>();
        }

        @Override
        public <T> ArrayMovie<T> buildMovieHashable(final int parentSize) {
            return new ArrayTapeHashable<>();
        }

    }

    @Override
    public int getDefaultSize() {
        return DEFAULT_SIZE_3D;
    }

    @Override
    public int getMaxEpisodeSize(final int fac) {
        return START_MAX_EPISODE_SIZE_3D + (fac << 3);
    }

    @Override
    public int getMinEpisodeGlue(final int fac) {
        return START_MIN_EPISODE_GLUE_3D + (fac << 1);
    }

    @Override
    public <T> ArrayMovie<T> buildMovie(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeason<>(inner) : new ArrayTape<>();
    }

    @Override
    public <T> ArrayMovie<T> buildMovieHashable(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonHashable<>(inner) : new ArrayTapeHashable<>();
    }

}
