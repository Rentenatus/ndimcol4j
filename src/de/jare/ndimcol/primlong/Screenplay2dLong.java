/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primlong;

import de.jare.ndimcol.ref.ArrayMovie;

/**
 *
 * @author Janusch Rentenatus
 */
// This code has been generated. Please do not make any changes here. Modify package 'de.jare.ndimcol' and use 'GeneratePrimitiveJavaFiles'
public class Screenplay2dLong implements ScreenplayLong {

    public final static ScreenplayLong INSTANCE = new Screenplay2dLong();

    static final int DEFAULT_SIZE_2D = 30;
    static final int START_MAX_EPISODE_SIZE_2D = 800;
    static final int START_MIN_EPISODE_GLUE_2D = 200;

    @Override
    public int getDefaultSize() {
        return DEFAULT_SIZE_2D;
    }

    @Override
    public int getMaxEpisodeSize(final int fac) {
        return START_MAX_EPISODE_SIZE_2D + (fac << 2);
    }

    @Override
    public int getMinEpisodeGlue(final int fac) {
        return START_MIN_EPISODE_GLUE_2D + fac;
    }

    @Override
    public ArrayMovieLong buildMovie(final int parentSize) {
        return new ArrayTapeLong();
    }
}
