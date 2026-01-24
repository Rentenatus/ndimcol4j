/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primshort;

import de.jare.ndimcol.ref.ArrayMovie;

/** 
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class Screenplay3dShort implements ScreenplayShort {

    public final static ScreenplayShort INSTANCE = new Screenplay3dShort();
    private final Screenplay3dShortInner inner = new Screenplay3dShortInner();

    static final int DEFAULT_SIZE_3D = 24;
    static final int START_MAX_EPISODE_SIZE_3D = 3200;
    static final int START_MIN_EPISODE_GLUE_3D = 512;
    static final int DEFAULT_SIZE_3D_INNER = 12;
    static final int START_MAX_EPISODE_SIZE_3D_INNER = 400;
    static final int START_MIN_EPISODE_GLUE_3D_INNER = 100;
    static final int START_3D_SIZE = 1024;

    static class Screenplay3dShortInner implements ScreenplayShort {

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
        public ArrayMovieShort buildMovie(final int parentSize) {
            return new ArrayTapeShort();
        }

        @Override
        public ArrayMovieShort buildMovieHashable(final int parentSize) {
            return new ArrayTapeHashableShort();
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
    public ArrayMovieShort buildMovie(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonShort(inner) : new ArrayTapeShort();
    }

    @Override
    public ArrayMovieShort buildMovieHashable(final int parentSize) {
        return parentSize > START_3D_SIZE ? new ArraySeasonHashableShort(inner) : new ArrayTapeHashableShort();
    }

}
