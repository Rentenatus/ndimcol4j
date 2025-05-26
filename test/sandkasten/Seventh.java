/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.RentenatusHashable;

/**
 *
 * @author jRent
 */
public class Seventh implements RentenatusHashable {

    public static void main(String[] args) {
        System.out.println("final static int MASK = " + MASK + ";\n");

        System.out.println("final static long[] SEVENTH ={ ");
        long seven = 1;
        for (int i = 0; i < 128; i++) {
            System.out.println(seven + ", ");
            seven = (seven * 7) & LONG_MASK;

        }
        System.out.println("}; ");

        System.out.println("public final static long[] SEVENTH128 ={ ");
        long seven128 = 1;
        for (int i = 0; i < 128; i++) {
            System.out.println(seven128 + ", ");
            seven128 = (seven * seven128) & LONG_MASK;

        }
        System.out.println("}; ");

        System.out.println("public final static long[] SEVENTH16384 ={ ");
        long seven16384 = 1;
        for (int i = 0; i < 128; i++) {
            System.out.println(seven16384 + ", ");
            seven16384 = (seven16384 * seven128) & LONG_MASK;

        }
        System.out.println("}; ");

        System.out.println("public final static long[] SEVENTH2097152 ={ ");
        long seven2097152 = 1;
        for (int i = 0; i < 128; i++) {
            System.out.println(seven2097152 + ", ");
            seven2097152 = (seven16384 * seven2097152) & LONG_MASK;

        }

        System.out.println("}; \n\npublic final static long SEVENTH268435456 = " + seven2097152 + ";\n");

        //System.out.println("test = " + ((LONG_MASK * 7) & LONG_MASK));
        //System.out.println("test = " + ((MASK * 7) & MASK));
    }

}
