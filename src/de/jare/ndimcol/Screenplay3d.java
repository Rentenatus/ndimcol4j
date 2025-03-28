/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 *
 * @author Janusch Rentenatus
 * @param <T>
 */
public class Screenplay3d implements Screenplay {

    public final static Screenplay INSTANCE = new Screenplay3d();
    public final Screenplay3dInner inner = new Screenplay3dInner();

    static final int DEFAULT_SIZE_3D = 12;
    static final int START_MAX_EPISODE_SIZE_3D = 1024;
    static final int START_MIN_EPISODE_GLUE_3D = 192;
    static final int DEFAULT_SIZE_3D_INNER = 8;
    static final int START_MAX_EPISODE_SIZE_3D_INNER = 128;
    static final int START_MIN_EPISODE_GLUE_3D_INNER = 48;

    class Screenplay3dInner implements Screenplay {

        @Override
        public int getDefaultSize() {
            return DEFAULT_SIZE_3D_INNER;
        }

        @Override
        public int getFactor(int dataSize) {
            return dataSize;
        }

        @Override
        public int getMaxEpisodeSize(int fac) {
            return START_MAX_EPISODE_SIZE_3D_INNER + (fac << 1);
        }

        @Override
        public int getMinEpisodeGlue(int fac) {
            return START_MIN_EPISODE_GLUE_3D_INNER + fac;
        }

        @Override
        public ArrayMovie buildMovie() {
            return new ArrayTape<>();
        }
    }

    @Override
    public int getDefaultSize() {
        return DEFAULT_SIZE_3D;
    }

    @Override
    public int getFactor(int dataSize) {
        return dataSize;
    }

    @Override
    public int getMaxEpisodeSize(int fac) {
        return START_MAX_EPISODE_SIZE_3D + (fac << 2);
    }

    @Override
    public int getMinEpisodeGlue(int fac) {
        return START_MIN_EPISODE_GLUE_3D + fac;
    }

    @Override
    public ArrayMovie buildMovie() {
        return new ArraySeason<>(inner);
    }
}
