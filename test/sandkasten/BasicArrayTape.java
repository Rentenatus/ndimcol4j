/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.ArrayTape;

/**
 *
 * @author jRent
 */
public class BasicArrayTape {

    public static void main(String[] args) {
        ArrayTape<Integer> at = new ArrayTape<>();
        at.add(4);
        at.add(5);
        at.add(6);
        at.add(7);
        at.add(8);
        at.removeAt(2);
        System.out.println("--------------");
        at.debbug(System.out, " . ");
        System.out.println("--------------");
        at.removeTrim(2);
        System.out.println("--------------");
        at.debbug(System.out, " . ");
        System.out.println("--------------");
    }
}
