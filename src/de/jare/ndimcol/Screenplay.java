/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T>
 */
public interface Screenplay {

    int getDefaultSize();

    public int getFactor(int dataSize);

    int getMaxEpisodeSize(int fac);

    int getMinEpisodeGlue(int fac);

    ArrayMovie buildMovie();
}
