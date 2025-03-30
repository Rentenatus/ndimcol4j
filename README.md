# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.


---

** Comparison: **

Vector        Batch 80   Duration: 67 ms  100.00%  reference point.
ArrayList     Batch 80   Duration: 60 ms  89.55%
ArrayTape     Batch 80   Duration: 62 ms  92.53%
ArraySeason   Batch 80   Duration: 61 ms  91.04%
SortedSeason  Batch 80   Duration: 76 ms  113.43%

Vector        Batch 4000   Duration: 5811 ms  100.00%  reference point.
ArrayList     Batch 4000   Duration: 5843 ms  100.55%
ArrayTape     Batch 4000   Duration: 5897 ms  101.47%
ArraySeason   Batch 4000   Duration: 2462 ms  42.36%
SortedSeason  Batch 4000   Duration: 3960 ms  68.14%

Vector        Batch 8000   Duration: 21735 ms  100.00%  reference point.
ArrayList     Batch 8000   Duration: 21855 ms  100.55%
ArrayTape     Batch 8000   Duration: 21869 ms  100.61%
ArraySeason   Batch 8000   Duration: 4887 ms  22.48%
SortedSeason  Batch 8000   Duration: 8250 ms  37.95%

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


