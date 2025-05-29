/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import java.util.Collection;

/**
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this movie
 */
public interface StrategicHashable<T> {

    HashStrategy<T> getStrategy();

    void setStrategy(HashStrategy<T> strategy);

    boolean equalsCollection(Collection<?> col);
}
