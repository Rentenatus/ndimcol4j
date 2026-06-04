/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.primint.MovieMapInt;
import de.jare.ndimcol.ref.MovieMap;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Janusch Rentenatus
 */
public class MapSpeed {

    protected static final int NUMBER_BATCHES = 800;

    public void runHashtable(final Object[] arr, final int numberElems) {
        // Erstellen der ursprünglichen Hashtable und Hinzufügen von Elementen
        Hashtable<Integer, Integer> originalMap = new Hashtable<>();
        for (int i = 0; i < arr.length; i++) {
            originalMap.put((Integer) arr[i], i);
        }
        for (int i = 0; i < numberElems; i++) {
            originalMap.put(i, i);
        }

        // Erstellen der neuen Hashtable
        Hashtable<Integer, Integer> newMap = new Hashtable<>();

        // Kopieren aller Elemente
        for (Map.Entry<Integer, Integer> entry : originalMap.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }

        // Entfernen aller Elemente aus der ursprünglichen Map
        for (Integer key : newMap.keySet()) {
            originalMap.remove(key);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newMap.get(i).equals(i)) {
                throw new RuntimeException("Map failed");
            }
        }
    }

    public void runHashMap(final Object[] arr, final int numberElems) {
        // Erstellen der ursprünglichen HashMap und Hinzufügen von Elementen
        HashMap<Integer, Integer> originalMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            originalMap.put((Integer) arr[i], i);
        }
        for (int i = 0; i < numberElems; i++) {
            originalMap.put(i, i);
        }

        // Erstellen der neuen HashMap
        HashMap<Integer, Integer> newMap = new HashMap<>();

        // Kopieren aller Elemente
        for (Map.Entry<Integer, Integer> entry : originalMap.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }

        // Entfernen aller Elemente aus der ursprünglichen Map
        for (Integer key : newMap.keySet()) {
            originalMap.remove(key);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newMap.get(i).equals(i)) {
                throw new RuntimeException("Map failed");
            }
        }
    }

    public void runMovieMap(final Object[] arr, final int numberElems) {
        final Comparator<Integer> naturalOrder = Comparator.naturalOrder();
        // Erstellen der ursprünglichen MovieMap mit value-based equality und Hinzufügen von Elementen
        MovieMap<Integer, Integer> originalMap = new MovieMap<>(naturalOrder, true);
        for (int i = 0; i < arr.length; i++) {
            originalMap.put((Integer) arr[i], i);
        }
        for (int i = 0; i < numberElems; i++) {
            originalMap.put(i, i);
        }

        // Erstellen der neuen MovieMap
        MovieMap<Integer, Integer> newMap = new MovieMap<>(naturalOrder, true);

        // Kopieren aller Elemente
        for (int i = 0; i < originalMap.size(); i++) {
            Integer key = originalMap.getKey(i);
            Integer value = originalMap.getValue(i);
            newMap.put(key, value);
        }

        // Entfernen aller Elemente aus der ursprünglichen Map
        for (int i = 0; i < newMap.size(); i++) {
            Integer key = newMap.getKey(i);
            originalMap.removeByKey(key);
        }

        for (int i = 0; i < numberElems; i++) {
            Integer value = newMap.getByKey(i);
            if (value == null || !value.equals(i)) {
                throw new RuntimeException("Map failed");
            }
        }
    }

    public void runMovieMapInt(final int[] arr, final int numberElems) {
        // Erstellen der ursprünglichen MovieMapInt und Hinzufügen von Elementen
        MovieMapInt<Integer> originalMap = new MovieMapInt<>();
        for (int i = 0; i < arr.length; i++) {
            originalMap.put(arr[i], i);
        }
        for (int i = 0; i < numberElems; i++) {
            originalMap.put(i, i);
        }

        // Erstellen der neuen MovieMapInt
        MovieMapInt<Integer> newMap = new MovieMapInt<>();

        // Kopieren aller Elemente mit Walker
        de.jare.ndimcol.primint.IteratorWalkerInt iter = originalMap.getKeysWalker();
        while (iter.hasNext()) {
            int key = iter.next();
            Integer value = originalMap.getByKey(key);
            newMap.put(key, value);
        }

        // Entfernen aller Elemente aus der ursprünglichen Map
        iter = newMap.getKeysWalker();
        while (iter.hasNext()) {
            int key = iter.next();
            originalMap.removeByKey(key);
        }

        for (int i = 0; i < numberElems; i++) {
            Integer value = newMap.getByKey(i);
            if (value == null || !value.equals(i)) {
                throw new RuntimeException("Map failed");
            }
        }
    }

    private Object[] createTestData(int numberElems) {
        MovieMap<Integer, Integer> testCol = new MovieMap<>();
        Random random = new Random();
        for (int i = 0; i < numberElems; i++) {
            int randomNumber = random.nextInt(numberElems * 2);
            testCol.put(randomNumber, i);
        }
        return testCol.getKeysArray();
    }

    private int[] createTestDataInt(int numberElems) {
        MovieMapInt<Integer> testCol = new MovieMapInt<>();
        Random random = new Random();
        for (int i = 0; i < numberElems; i++) {
            int randomNumber = random.nextInt(numberElems * 2);
            testCol.put(randomNumber, i);
        }
        int[] result = new int[testCol.size()];
        de.jare.ndimcol.primint.IteratorWalkerInt iter = testCol.getKeysWalker();
        int i = 0;
        while (iter.hasNext()) {
            result[i++] = iter.next();
        }
        return result;
    }

    protected static void batch(MapSpeed test, int numberElems) {
        Object[] arr = test.createTestData(numberElems);
        int[] arrInt = test.createTestDataInt(numberElems);

        // Zeitmessung für Hashtable (Referenz)
        long startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runHashtable(arr, numberElems);
        }
        long endTime = System.nanoTime();
        long durationHashtable = (endTime - startTime) / 1_000_000;
        System.out.println("|Hashtable      |  " + numberElems + "    |  " + durationHashtable + " |  100.00%  |reference point.|");

        // Zeitmessung für HashMap
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runHashMap(arr, numberElems);
        }
        endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("|HashMap        |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationHashtable) / 100d + "%||");

        // Zeitmessung für MovieMap
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runMovieMap(arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000;
        System.out.println("|MovieMap       |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationHashtable) / 100d + "%|Object keys/values|");

        // Zeitmessung für MovieMapInt
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runMovieMapInt(arrInt, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000;
        System.out.println("|MovieMapInt    |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationHashtable) / 100d + "%|int keys without unboxing|");
    }

    final static int[] JOBS = new int[]{50_000, 24_000, 8_000, 4_000, 640, 240, 120, 80};

    public static void main(String[] args) {
        MapSpeed test = new MapSpeed();
        System.out.println("| Algorithm     | Batch Size | Duration (ms) | Percentage (%) | Notes                     |");
        System.out.println("|---------------|------------|---------------|----------------|---------------------------|");
        for (int i = 0; i < JOBS.length; i++) {
            batch(test, JOBS[i]);
        }
    }
}
