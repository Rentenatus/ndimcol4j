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

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class Screenplay3dFloat implements ScreenplayFloat {

    public final static ScreenplayFloat INSTANCE = new Screenplay3dFloat();
    private final Screenplay3dFloatInner inner = new Screenplay3dFloatInner();

    static final int DEFAULT_SIZE_3D = 24;
    static final int START_MAX_EPISODE_SIZE_3D = 2048;
    static final int START_MIN_EPISODE_GLUE_3D = 192;
    static final int DEFAULT_SIZE_3D_INNER = 12;
    static final int START_MAX_EPISODE_SIZE_3D_INNER = 512;
    static final int START_MIN_EPISODE_GLUE_3D_INNER = 128;
    static final int START_3D_SIZE = 4096;

    static class Screenplay3dFloatInner implements ScreenplayFloat {

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
        public ArrayMovieFloat buildMovie(final int parentSize) {
            return new ArrayTapeFloat();
        }

        @Override
        public ArrayMovieFloat buildMovieHashable(final int parentSize) {
            return new ArrayTapeHashableFloat();
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
    public ArrayMovieFloat buildMovie(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonFloat(inner) : new ArrayTapeFloat();
    }

    @Override
    public ArrayMovieFloat buildMovieHashable(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonHashableFloat(inner) : new ArrayTapeHashableFloat();
    }

}
