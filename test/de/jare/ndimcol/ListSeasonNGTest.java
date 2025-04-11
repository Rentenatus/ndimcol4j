/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.util.List;
import java.util.Random;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author jRent
 */
public class ListSeasonNGTest {

    public ListSeasonNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("===============================================");
        System.out.println("## Start ListSeasonNGTest.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("## End ListSeasonNGTest.");
        System.out.println("===============================================");
    }

    public static ArrayTape<Integer> generateRandomList() {
        ArrayTape<Integer> randomList = new ArrayTape<>();
        Random random = new Random();

        for (int i = 0; i < 9000; i++) {
            int randomNumber = random.nextInt(3333);
            randomList.add(randomNumber);
        }
        return randomList;
    }

    @Test
    public void testSplitInHalf() {
        System.out.println("-------------- testSplitInHalf");

        ListSeason<Integer> list = new ListSeason<>();
        list.addAll(generateRandomList());
        System.out.println("list.size    " + list.size());
        List<Integer> copyList = list.subList(0, list.size());
        System.out.println("copy.size    " + list.size());
        ListSeason<Integer> half = list.splitInHalf();
        System.out.println("-- split");
        System.out.println("list.size    " + list.size());
        System.out.println("half.size    " + half.size());
        list.addAll(half);
        System.out.println("-- addAll");
        System.out.println("list.size    " + list.size());

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), copyList.get(i));

        }
    }

    @Test
    public void testFilter() {
        System.out.println("-------------- testFilterFirstLast");

        ListSeason<Integer> list = new ListSeason<>();
        list.addAll(generateRandomList());

        System.out.println("list.size    " + list.size());

        final Integer probe = list.get(list.size() / 2);
        int firstIndex = list.indexOf(probe);
        int lastIndex = list.lastIndexOf(probe);
        IteratorWalker<Integer> firstFind = list.filterFirst(x -> probe.equals(x));
        IteratorWalker<Integer> lastFind = list.filterLast(x -> probe.equals(x));
        System.out.println("-- first");
        System.out.println("index     " + firstIndex);
        System.out.println("filter    " + firstFind.getCurrentIndex());
        System.out.println("-- last");
        System.out.println("index     " + lastIndex);
        System.out.println("filter    " + lastFind.getCurrentIndex());

        assertEquals(firstIndex, firstFind.getCurrentIndex());
        assertEquals(lastIndex, lastFind.getCurrentIndex());

        ArrayMovie<Integer> allFind = list.filterAll(x -> probe.equals(x));
        System.out.println("-- all");
        System.out.println("found     " + allFind.size());

        IteratorWalker<Integer> walker = allFind.softWalker();
        while (walker.hasNext()) {
            assertEquals(walker.next(), probe);
        }

    }

}
