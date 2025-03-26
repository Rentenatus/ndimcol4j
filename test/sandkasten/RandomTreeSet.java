/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.ArraySeason;
import de.jare.ndimcol.SortedSeasonSet;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

public class RandomTreeSet {

    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        Comparator<Integer> compT = Integer::compare;
        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        ArraySeason<Integer> season = new SortedSeasonSet<>(compT, true);
        Random random = new Random();

        for (int i = 0; i < 80000; i++) {
            int randomNumber = random.nextInt(40001); // Zufälliger Integer zwischen 0 und 20000
            boolean ok1 = treeSet.add(randomNumber);
            boolean ok2 = season.add(randomNumber);
            if (ok1 != ok2) {
                System.out.println("Fehler:                " + randomNumber);
                break;
            }
        }

        System.out.println("--------------");
        System.out.println("TreeSet:            #  " + treeSet.size());
        System.out.println("Erste Zahl:            " + treeSet.first());
        System.out.println("Letzte Zahl:           " + treeSet.last());
        System.out.println("--------------");
        System.out.println("SortedSeasonSet:     # " + season.size());
        System.out.println("Erste Zahl:            " + season.first());
        System.out.println("Letzte Zahl:           " + season.last());

        System.out.println("--------------");
        for (Integer in : treeSet) {
            if (!season.remove(in)) {
                System.out.println(in);
            }
            int last = -1;
            for (Integer test : season) {
                if (test <= last) {
                    System.out.println("???    " + in + "  ~~> " + last + " > " + test
                    );
                    return;
                }
                last = test;
            }
        }
        System.out.println("--------------");
        System.out.println("Removed size:        # " + season.size());

    }
}
