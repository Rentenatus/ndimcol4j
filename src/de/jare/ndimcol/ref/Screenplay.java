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
public interface Screenplay {

    int getDefaultSize();

    int getMaxEpisodeSize(final int fac);

    int getMinEpisodeGlue(final int fac);

    <T> ArrayMovie<T> buildMovie(final int parentSize);
}
