/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primint;

/** 
 * @author Janusch Rentenatus
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class Screenplay2dInt implements ScreenplayInt {

    public final static ScreenplayInt INSTANCE = new Screenplay2dInt();

    static final int DEFAULT_SIZE_2D = 32;
    static final int START_MAX_EPISODE_SIZE_2D = 400;
    static final int START_MIN_EPISODE_GLUE_2D = 100;

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
    public ArrayMovieInt buildMovie(final int parentSize) {
        return new ArrayTapeInt();
    }

    @Override
    public ArrayMovieInt buildMovieHashable(final int parentSize) {
        return new ArrayTapeHashableInt();
    }

}
