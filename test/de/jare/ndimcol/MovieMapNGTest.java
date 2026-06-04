/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import de.jare.ndimcol.primint.MovieMapInt;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.IteratorWalker;
import de.jare.ndimcol.ref.MovieMap;
import de.jare.ndimcol.utils.BiPredicateAmbiguityIdentity;
import de.jare.ndimcol.utils.BiPredicateHashGr;
import java.util.Comparator;
import java.util.function.BiPredicate;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Janusch Rentenatus
 */
public class MovieMapNGTest {

    public class Plant {

        String name;

        public Plant(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object ob) {
            return name.equals(((Plant) ob).name);
        }

        @Override
        public int hashCode() {
            return super.hashCode() + 31 * name.hashCode();
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }

    }

    public class PlantWrapper {

        Plant plant;

        public PlantWrapper(Plant plant) {
            this.plant = plant;
        }

        public Plant getPlant() {
            return plant;
        }
    }

    class BiPredicatePlantGr implements BiPredicate<Plant, Plant> {

        /**
         * Evaluates this predicate o2.name compareTo o1.name > 0.
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code true} if the input arguments match the predicate, otherwise
         *         {@code false}
         *
         */
        @Override
        public boolean test(Plant o1, Plant o2) {
            return o2.name.compareTo(o1.name) > 0;
        }

    }

    class BiPredicatePlantEv implements BiPredicate<Plant, Plant> {

        /**
         * Evaluates this predicate o2 == o1.
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code true} if the input arguments are the same
         *
         */
        @Override
        public boolean test(Plant o1, Plant o2) {
            return o2 == o1;
        }

    }

    class BiPredicatePlantNever implements BiPredicate<Plant, Plant> {

        /**
         * Never match.
         *
         *
         * @param o1 the first input argument
         * @param o2 the second input argument
         * @return {@code false}
         *
         */
        @Override
        public boolean test(Plant o1, Plant o2) {
            return false;
        }

    }

    public MovieMapNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start MovieMapNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End MovieMapNGTest.");
        System.out.println("===============================================");
    }

    @Test
    public void testSomeMethod() {
        BiPredicatePlantGr plantGr = new BiPredicatePlantGr();
        BiPredicatePlantEv plantEv = new BiPredicatePlantEv();
        BiPredicatePlantNever plantNever = new BiPredicatePlantNever();

        MovieMap<Plant, String> mapDef = new MovieMap<>(plantGr, null);
        MovieMap<Plant, String> mapEven = new MovieMap<>(plantGr, plantEv);
        MovieMap<Plant, String> mapAmbi = new MovieMap<>(plantGr, plantNever);
        final BiPredicateAmbiguityIdentity<Plant> ambiguity1 = new BiPredicateAmbiguityIdentity<>();
        final BiPredicateHashGr<Plant> predicate1 = new BiPredicateHashGr<>();
        MovieMap<Plant, String> mapHash = new MovieMap<>(predicate1, ambiguity1);

        Plant[] plants = {
                new Plant("Eiche"),
                new Plant("Zeder"),
                new Plant("Ahorn"),
                new Plant("Eiche"),
                new Plant("Birke"),
                new Plant("Zeder")
        };

        String[] plantValues = {
                "Quercus robur",
                "Cedrus libani",
                "Acer pseudoplatanus",
                "Quercus robur",
                "Betula pendula",
                "Cedrus libani"
        };

        System.out.println("<-- source array");
        for (int i = 0; i < plants.length; i++) {
            mapDef.put(plants[i], plantValues[i]);
            mapEven.put(plants[i], plantValues[i]);
            mapAmbi.put(plants[i], plantValues[i]);
            mapHash.put(plants[i], plantValues[i]);
            System.out.println(plants[i] + " : " + plantValues[i] + "  -1->  " + plants[i].hashCode());
        }
        for (int i = 0; i < plants.length; i++) {
            mapDef.put(plants[i], plantValues[i]);
            mapEven.put(plants[i], plantValues[i]);
            mapAmbi.put(plants[i], plantValues[i]);
            mapHash.put(plants[i], plantValues[i]);
            System.out.println(plants[i] + " : " + plantValues[i] + "  -2->  " + plants[i].hashCode());
        }

        assertEquals(mapDef.getKey(0).name, "Ahorn");
        assertEquals(mapDef.getValue(0), "Acer pseudoplatanus");
        assertEquals(mapDef.getKey(1).name, "Birke");
        assertEquals(mapDef.getValue(1), "Betula pendula");
        assertEquals(mapDef.getKey(2).name, "Eiche");
        assertEquals(mapDef.getValue(2), "Quercus robur");
        assertEquals(mapDef.getKey(3).name, "Zeder");
        assertEquals(mapDef.getValue(3), "Cedrus libani");

        mapDef.removeByKey(plants[1]);
        mapEven.removeByKey(plants[1]);
        mapAmbi.removeByKey(plants[1]);
        mapHash.removeByKey(plants[1]);
        System.out.println("<-- remove plants[1]");
        System.out.println(plants[1] + " : " + plantValues[1] + "  -r->  " + plants[1].hashCode());

        System.out.println("--- simple map");
        IteratorWalker<Plant> walker = mapDef.getKeysWalker();
        while (walker.hasNext()) {
            Plant ob = walker.next();
            System.out.println(ob + " : " + mapDef.getByKey(ob) + "  -->  " + ob.hashCode());
        }
        System.out.println("--- identity == ");
        walker = mapEven.getKeysWalker();
        while (walker.hasNext()) {
            Plant ob = walker.next();
            System.out.println(ob + " : " + mapEven.getByKey(ob) + "  -->  " + ob.hashCode());
        }
        System.out.println("--- multiple ambiguity");
        for (int i = 0; i < mapAmbi.size() && i < 20; i++) {
            Plant key = mapAmbi.getKey(i);
            String value = mapAmbi.getValue(i);
            System.out.println(key + " : " + value + "  -->  " + key.hashCode());
        }
        System.out.println("--- hash map");
        walker = mapHash.getKeysWalker();
        while (walker.hasNext()) {
            Plant ob = walker.next();
            System.out.println(ob + " : " + mapHash.getByKey(ob) + "  -->  " + ob.hashCode());
        }

        assertEquals(mapDef.size(), 3);
        assertEquals(mapEven.size(), 5);
        assertEquals(mapAmbi.size(), 12);
        assertEquals(mapHash.size(), 5);
    }

    /**
     * Debug-Helper: Gibt Episoden-Informationen per Reflection aus.
     */
    private void printEpisodesInfo(String mapName, Object keysSet) {
        try {
            System.err.println("=== DEBUG: " + mapName + " ===");

            // Zugriff auf data-Feld per Reflection
            java.lang.reflect.Field dataField = keysSet.getClass().getSuperclass().getDeclaredField("data");
            dataField.setAccessible(true);
            Object data = dataField.get(keysSet);

            // data ist ArrayTape<ArrayMovie<T>>
            System.err.println("Total size: " + keysSet.getClass().getMethod("size").invoke(keysSet));

            // Zugriff auf size-Feld von ArrayTape
            java.lang.reflect.Field tapeSizeField = data.getClass().getDeclaredField("size");
            tapeSizeField.setAccessible(true);
            int tapeSize = (Integer) tapeSizeField.get(data);
            System.err.println("Number of episodes (tape size): " + tapeSize);

            Object[] tapeData = ((ArrayMovie) data).toArray();

            // tapeData ist ArrayMovie[] oder ArrayList
            for (int epIdx = 0; epIdx < tapeData.length; epIdx++) {
                Object episodeObj = tapeData[epIdx];
                if (episodeObj != null) {
                    Class<?> episodeClass = episodeObj.getClass();
                    int epSize = (Integer) episodeClass.getMethod("size").invoke(episodeObj);
                    Object first = epSize > 0 ? episodeClass.getMethod("get", int.class).invoke(episodeObj, 0)
                            : "empty";
                    Object last = epSize > 0 ? episodeClass.getMethod("get", int.class).invoke(episodeObj, epSize - 1)
                            : "empty";
                    System.err.println("Episode " + epIdx + ": size=" + epSize
                            + ", first=" + first + ", last=" + last);
                }
            }

        } catch (Exception e) {
            System.err.println("DEBUG ERROR: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        System.err.println("======================");
    }

    @Test
    public void testGetAtIndex() {
        // Test MovieMap<Integer, Integer> mit getAt für i=0 bis 480
        // Verwende gleiche Integer-Referenzen für Keys, um Referenz-Equality zu umgehen
        final Comparator<Integer> naturalOrder = Comparator.naturalOrder();
        MovieMap<Integer, Integer> mapObj = new MovieMap<>(naturalOrder, true);
        for (int i = 0; i <= 480; i++) {
            Integer key = Integer.valueOf(i);
            mapObj.put(key, i);

            // Debug-Prüfungen
            Integer actualKey = mapObj.getKey(i);
            Integer actualValue = mapObj.getValue(i);
            Integer valueByKey = mapObj.getByKey(key);

            if (!key.equals(actualKey)) {
                printEpisodesInfo("MovieMap (getKey)", mapObj.getKeysMovie());
                mapObj.put(99999, 99999);
                printEpisodesInfo("MovieMap (getKey)", mapObj.getKeysMovie());
            }
            if (!Integer.valueOf(i).equals(actualValue)) {
                printEpisodesInfo("MovieMap (getValue)", mapObj.getKeysMovie());
                mapObj.put(99999, 99999);
                printEpisodesInfo("MovieMap (getValue)", mapObj.getKeysMovie());
            }
            if (!Integer.valueOf(i).equals(valueByKey)) {
                printEpisodesInfo("MovieMap (getByKey)", mapObj.getKeysMovie());
                mapObj.put(99999, 99999);
                printEpisodesInfo("MovieMap (getValue)", mapObj.getKeysMovie());
            }

            // Prüfe: Key an Position i muss i sein
            assertEquals(mapObj.getKey(i), key, "Key mismatch at index " + i);
            // Prüfe: Value an Position i muss i sein
            assertEquals(mapObj.getValue(i), Integer.valueOf(i), "Value mismatch at index " + i);
            // Prüfe: getByKey(i) muss i zurückgeben
            assertEquals(mapObj.getByKey(key), Integer.valueOf(i), "getByKey mismatch for key " + i);
        }

        // Test MovieMapInt<Integer> mit getAt für i=0 bis 480
        MovieMapInt<Integer> mapInt = new MovieMapInt<>();
        for (int i = 0; i <= 480; i++) {
            mapInt.put(i, i);

            // Debug-Prüfungen
            int actualKeyInt = mapInt.getKey(i);
            Integer actualValueInt = mapInt.getValue(i);
            Integer valueByKeyInt = mapInt.getByKey(i);

            if (actualKeyInt != i) {
                printEpisodesInfo("MovieMapInt (getKey)", getKeysField(mapInt));
            }
            if (!Integer.valueOf(i).equals(actualValueInt)) {
                printEpisodesInfo("MovieMapInt (getValue)", getKeysField(mapInt));
            }
            if (!Integer.valueOf(i).equals(valueByKeyInt)) {
                printEpisodesInfo("MovieMapInt (getByKey)", getKeysField(mapInt));
            }

            // Prüfe: Key an Position i muss i sein
            assertEquals(mapInt.getKey(i), i, "MovieMapInt key mismatch at index " + i);
            // Prüfe: Value an Position i muss i sein
            assertEquals(mapInt.getValue(i), Integer.valueOf(i), "MovieMapInt value mismatch at index " + i);
            // Prüfe: getByKey(i) muss i zurückgeben
            assertEquals(mapInt.getByKey(i), Integer.valueOf(i), "MovieMapInt getByKey mismatch for key " + i);
        }
    }

    /**
     * Hilfsmethode: Holt das keys-Feld per Reflection.
     */
    private Object getKeysField(Object map) {
        try {
            java.lang.reflect.Field keysField = map.getClass().getDeclaredField("keys");
            keysField.setAccessible(true);
            return keysField.get(map);
        } catch (Exception e) {
            return null;
        }
    }

}
