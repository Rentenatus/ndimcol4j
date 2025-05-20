# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.


---

**Comparison:**

| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|--------------|------------|---------------|----------------|------------------|
Vector         |  80    |  31 |  100.00%  |reference point.|
ArrayList      |  80    |  27 |  87.09%||
ArrayTape      |  80    |  26 |  83.87%||
ArraySeason    |  80    |  42 |  135.48%||
ArraySeasonInt |  80    |  44 |  141.93%|int without unboxing|
ArraySeason3d  |  80    |  40 |  129.03%||
SortedSeason   |  80    |  53 |  170.96%||
SortedSeasonInt|  80    |  32 |  103.22%|int without unboxing|
Vector         |  240    |  86 |  100.00%  |reference point.|
ArrayList      |  240    |  71 |  82.55%||
ArrayTape      |  240    |  75 |  87.2%||
ArraySeason    |  240    |  122 |  141.86%||
ArraySeasonInt |  240    |  96 |  111.62%|int without unboxing|
ArraySeason3d  |  240    |  129 |  150.0%||
SortedSeason   |  240    |  113 |  131.39%||
SortedSeasonInt|  240    |  52 |  60.46%|int without unboxing|
Vector         |  4000    |  2365 |  100.00%  |reference point.|
ArrayList      |  4000    |  2381 |  100.67%||
ArrayTape      |  4000    |  2630 |  111.2%||
ArraySeason    |  4000    |  1137 |  48.07%||
ArraySeasonInt |  4000    |  490 |  20.71%|int without unboxing|
ArraySeason3d  |  4000    |  1347 |  56.95%||
SortedSeason   |  4000    |  1859 |  78.6%||
SortedSeasonInt|  4000    |  817 |  34.54%|int without unboxing|
Vector         |  8000    |  9382 |  100.00%  |reference point.|
ArrayList      |  8000    |  8989 |  95.81%||
ArrayTape      |  8000    |  9150 |  97.52%||
ArraySeason    |  8000    |  2105 |  22.43%||
ArraySeasonInt |  8000    |  691 |  7.36%|int without unboxing|
ArraySeason3d  |  8000    |  2485 |  26.48%||
SortedSeason   |  8000    |  3128 |  33.34%||
SortedSeasonInt|  8000    |  1541 |  16.42%|int without unboxing|
Vector         |  24000    |  76180 |  100.00%  |reference point.|
ArrayList      |  24000    |  72406 |  95.04%||
ArrayTape      |  24000    |  80583 |  105.77%||
ArraySeason    |  24000    |  7535 |  9.89%||
ArraySeasonInt |  24000    |  2339 |  3.07%|int without unboxing|
ArraySeason3d  |  24000    |  8431 |  11.06%||
SortedSeason   |  24000    |  10721 |  14.07%||
SortedSeasonInt|  24000    |  5625 |  7.38%|int without unboxing|


---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


