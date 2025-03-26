/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.ArraySeason;
import de.jare.ndimcol.Screenplay3d;

/**
 *
 * @author jRent
 */
public class BasicArraySeason {

    public static void main(String[] args) {
        ArraySeason<Integer> as = new ArraySeason<>(Screenplay3d.INSTANCE);
        for (int i = 0; i < 1025; i++) {
            as.add(i);
        }
        as.add(0, -5);
        as.add(0, -6);
        as.add(0, -7);
        as.add(0, -8);
        as.removeAt(2);
        System.out.println("--------------");
        as.removeAt(2);
        System.out.println("--------------");
        as.debbug(System.out, "  ");
        System.out.println("--------------");
        for (int i = 0; i < as.size(); i++) {
            System.out.println(" . " + as.get(i));
        }
    }
}
