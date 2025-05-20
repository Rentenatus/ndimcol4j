/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package sandkasten;

import de.jare.ndimcol.primint.ArraySeasonInt;
import de.jare.ndimcol.primint.IteratorInt;
import de.jare.ndimcol.primint.Screenplay2dInt;
import de.jare.ndimcol.primint.ScreenplayInt;
import de.jare.ndimcol.primint.SortedSeasonSetInt;
import de.jare.ndimcol.ref.ArraySeason;
import de.jare.ndimcol.ref.ArrayTape;
import de.jare.ndimcol.ref.SortedSeasonSet;
import de.jare.ndimcol.ref.IterTapeWalker;
import de.jare.ndimcol.ref.Screenplay;
import de.jare.ndimcol.ref.Screenplay2d;
import de.jare.ndimcol.ref.Screenplay3d;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Janusch Rentenatus
 */
public class ListSpeed {

    protected static final int NUMBER_BATCHES = 800;

    public void runTape(final Object[] arr, final int numberElems) {
        // Erstellen des ursprünglichen Tapes und Hinzufügen von 100 Elementen
        ArrayTape<Integer> originalTape = new ArrayTape<>();
        for (int i = 0; i < arr.length; i++) {
            originalTape.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalTape.add(i, i);
        }

        // Erstellen des neuen Tapes
        ArrayTape<Integer> newTape = new ArrayTape<>();

        // Verwenden des TapeWalkers, um Elemente zu durchlaufen, hinzuzufügen  
        IterTapeWalker<Integer> tapeWalker = originalTape.softWalker();
        while (tapeWalker.hasNext()) {
            Integer element = tapeWalker.next();
            newTape.add(element);
        }

        // Verwenden des TapeWalkers, um Elemente  zu entfernen
        tapeWalker = newTape.softWalker();
        while (tapeWalker.hasNext()) {
            Integer element = tapeWalker.next();
            originalTape.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newTape.get(i).equals(i)) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runSeason(Screenplay sc, final Object[] arr, final int numberElems) {
        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        ArraySeason<Integer> originalSeason = new ArraySeason<>(sc);
        for (int i = 0; i < arr.length; i++) {
            originalSeason.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalSeason.add(i, i);
        }

        // Erstellen des neuen Tapes
        ArraySeason<Integer> newSeason = new ArraySeason<>(sc);

        // Verwenden des TapeWalkers, um Elemente zu durchlaufen, hinzuzufügen  
        Iterator<Integer> iter = originalSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            newSeason.add(element);
        }

        // Verwenden des TapeWalkers, um Elemente  zu entfernen
        iter = newSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            originalSeason.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newSeason.get(i).equals(i)) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runSeasonInt(ScreenplayInt sc, final Object[] arr, final int numberElems) {
        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        ArraySeasonInt originalSeason = new ArraySeasonInt(sc);
        for (int i = 0; i < arr.length; i++) {
            originalSeason.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalSeason.add(i, i);
        }

        // Erstellen des neuen Tapes
        ArraySeasonInt newSeason = new ArraySeasonInt(sc);

        // Verwenden des TapeWalkers, um Elemente zu durchlaufen, hinzuzufügen  
        IteratorInt iter = originalSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            newSeason.add(element);
        }

        // Verwenden des TapeWalkers, um Elemente  zu entfernen
        iter = newSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            originalSeason.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (newSeason.get(i) != i) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runSortedSeasonSet(final Object[] arr, final int numberElems) {
        Comparator<Integer> compT = Integer::compare;
        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        ArraySeason<Integer> originalSeason = new SortedSeasonSet<>(compT, false);
        for (int i = 0; i < arr.length; i++) {
            originalSeason.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalSeason.add(i);
        }

        // Erstellen des neuen Tapes
        ArraySeason<Integer> newTape = new SortedSeasonSet<>(compT, true);

        // Verwenden des TapeWalkers, um Elemente zu durchlaufen, hinzuzufügen  
        Iterator<Integer> iter = originalSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            newTape.add(element);
        }

        // Verwenden des TapeWalkers, um Elemente  zu entfernen
        iter = newTape.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            originalSeason.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newTape.get(i).equals(i)) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runSortedSeasonSetInt(final Object[] arr, final int numberElems) {

        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        ArraySeasonInt originalSeason = new SortedSeasonSetInt();
        for (int i = 0; i < arr.length; i++) {
            originalSeason.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalSeason.add(i);
        }

        // Erstellen des neuen Tapes
        ArraySeasonInt newTape = new SortedSeasonSetInt();

        // Verwenden des TapeWalkers, um Elemente zu durchlaufen, hinzuzufügen  
        IteratorInt iter = originalSeason.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            newTape.add(element);
        }

        // Verwenden des TapeWalkers, um Elemente  zu entfernen
        iter = newTape.iterator();
        while (iter.hasNext()) {
            Integer element = iter.next();
            originalSeason.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (newTape.get(i) != i) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runVector(final Object[] arr, final int numberElems) {
        // Erstellen des ursprünglichen Tapes und Hinzufügen von numberElems Elementen
        Vector<Integer> originalVector = new Vector<>();
        for (int i = 0; i < arr.length; i++) {
            originalVector.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalVector.add(i, i);
        }

        // Erstellen des neuen Tapes
        Vector<Integer> newVactor = new Vector<>();

        // Verwenden der Enumeration, um Elemente zu durchlaufen und hinzuzufügen
        Enumeration<Integer> enumeration = originalVector.elements();
        while (enumeration.hasMoreElements()) {
            Integer element = enumeration.nextElement();
            newVactor.add(element);
        }

        // Verwenden der Enumeration, um Elemente zu entfernen
        enumeration = newVactor.elements();
        while (enumeration.hasMoreElements()) {
            Integer element = enumeration.nextElement();
            originalVector.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newVactor.get(i).equals(i)) {
                throw new RuntimeException("List failed");
            }
        }
    }

    public void runList(final Object[] arr, final int numberElems) {
        // Erstellen des ursprünglichen ArrayLists und Hinzufügen von numberElems Elementen
        List<Integer> originalList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            originalList.add((Integer) arr[i]);
        }
        for (int i = 0; i < numberElems; i++) {
            originalList.add(i, i);
        }

        // Erstellen des neuen ArrayLists
        List<Integer> newList = new ArrayList<>();

        // Verwenden des Iterators, um Elemente zu durchlaufen und hinzuzufügen
        Iterator<Integer> iterator = originalList.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            newList.add(element);
        }

        // Verwenden des Iterators, um Elemente zu entfernen
        iterator = newList.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            originalList.remove(element);
        }

        for (int i = 0; i < numberElems; i++) {
            if (!newList.get(i).equals(i)) {
                throw new RuntimeException("List failed");
            }
        }
    }

    protected static void batch(ListSpeed test, int numberElems) {
        ArraySeason<Integer> testCol = new ArraySeason<>();
        Random random = new Random();

        for (int i = 0; i < numberElems; i++) {
            int randomNumber = random.nextInt(numberElems * 2); // Zufälliger Integer  
            boolean ok2 = testCol.add(randomNumber);
        }
        Object[] arr = testCol.toArray();

        // Zeitmessung für runVector
        long startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runVector(arr, numberElems);
        }
        long endTime = System.nanoTime();
        long durationVector = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("Vector         |  " + numberElems + "    |  " + durationVector + " |  100.00%  |reference point.|");

        // Zeitmessung für runList
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runList(arr, numberElems);
        }
        endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("ArrayList      |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%||");
        // Zeitmessung für runTape
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runTape(arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("ArrayTape      |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%||");

        // Zeitmessung für runSeason
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runSeason(Screenplay2d.INSTANCE, arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("ArraySeason    |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%||");

        // Zeitmessung für runSeasonInt
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runSeasonInt(Screenplay2dInt.INSTANCE, arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("ArraySeasonInt |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%|int without unboxing|");

        // Zeitmessung für runSeason
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runSeason(Screenplay3d.INSTANCE, arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("ArraySeason3d  |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%||");

        // Zeitmessung für runSortedSeasonSet
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runSortedSeasonSet(arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("SortedSeason   |  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%||");

        // Zeitmessung für runSortedSeasonSet
        startTime = System.nanoTime();
        for (int batch = 0; batch < NUMBER_BATCHES; batch++) {
            test.runSortedSeasonSetInt(arr, numberElems);
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000; // Zeit in Millisekunden
        System.out.println("SortedSeasonInt|  " + numberElems + "    |  " + duration + " |  " + (duration * 10000 / durationVector) / 100d + "%|int without unboxing|");

    }

    final static int[] JOBS = new int[]{80, 240, 4_000, 8_000, 24_000};

    public static void main(String[] args) {
        ListSpeed test = new ListSpeed();
        System.out.println("| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |");
        System.out.println("|--------------|------------|---------------|----------------|------------------|");
        for (int i = 0; i < JOBS.length; i++) {
            batch(test, JOBS[i]);
        }
    }
}
