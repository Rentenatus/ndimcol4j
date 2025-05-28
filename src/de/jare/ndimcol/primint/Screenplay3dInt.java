/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primint;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class Screenplay3dInt implements ScreenplayInt {

    public final static ScreenplayInt INSTANCE = new Screenplay3dInt();
    private final Screenplay3dIntInner inner = new Screenplay3dIntInner();

    static final int DEFAULT_SIZE_3D = 24;
    static final int START_MAX_EPISODE_SIZE_3D = 8192;
    static final int START_MIN_EPISODE_GLUE_3D = 192;
    static final int DEFAULT_SIZE_3D_INNER = 12;
    static final int START_MAX_EPISODE_SIZE_3D_INNER = 512;
    static final int START_MIN_EPISODE_GLUE_3D_INNER = 128;
    static final int START_3D_SIZE = 4096;

    static class Screenplay3dIntInner implements ScreenplayInt {

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
        public ArrayMovieInt buildMovie(final int parentSize) {
            return new ArrayTapeInt();
        }

        @Override
        public ArrayMovieInt buildMovieHashable(final int parentSize) {
            return new ArrayTapeHashableInt();
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
    public ArrayMovieInt buildMovie(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonInt(inner) : new ArrayTapeInt();
    }

    @Override
    public ArrayMovieInt buildMovieHashable(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonHashableInt(inner) : new ArrayTapeHashableInt();
    }

}
