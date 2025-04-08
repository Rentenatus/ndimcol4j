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
 */
public class Screenplay2d implements Screenplay {

    public final static Screenplay INSTANCE = new Screenplay2d();

    static final int DEFAULT_SIZE_2D = 10;
    static final int START_MAX_EPISODE_SIZE_2D = 512;
    static final int START_MIN_EPISODE_GLUE_2D = 128;

    @Override
    public int getDefaultSize() {
        return DEFAULT_SIZE_2D;
    }

    @Override
    public int getMaxEpisodeSize(int fac) {
        return START_MAX_EPISODE_SIZE_2D + (fac << 2);
    }

    @Override
    public int getMinEpisodeGlue(int fac) {
        return START_MIN_EPISODE_GLUE_2D + fac;
    }

    @Override
    public <T> ArrayMovie<T> buildMovie() {
        return new ArrayTape<>();
    }
}
