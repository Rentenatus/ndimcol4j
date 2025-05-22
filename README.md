# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.


---

**Comparison:**

| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|--------------|------------|---------------|----------------|------------------|
Vector         |  80    |  32 |  100.00%  |reference point.|
ArrayList      |  80    |  27 |  84.37%||
ArrayTape      |  80    |  27 |  84.37%||
ArraySeason    |  80    |  36 |  112.5%||
ArraySeasonInt |  80    |  29 |  90.62%|int without unboxing|
ArraySeason3d  |  80    |  31 |  96.87%||
SortedSeason   |  80    |  43 |  134.37%||
SortedSeasonInt|  80    |  25 |  78.12%|int without unboxing|
Vector         |  240    |  95 |  100.00%  |reference point.|
ArrayList      |  240    |  75 |  78.94%||
ArrayTape      |  240    |  66 |  69.47%||
ArraySeason    |  240    |  111 |  116.84%||
ArraySeasonInt |  240    |  91 |  95.78%|int without unboxing|
ArraySeason3d  |  240    |  91 |  95.78%||
SortedSeason   |  240    |  86 |  90.52%||
SortedSeasonInt|  240    |  47 |  49.47%|int without unboxing|
Vector         |  4000    |  2342 |  100.00%  |reference point.|
ArrayList      |  4000    |  2356 |  100.59%||
ArrayTape      |  4000    |  2441 |  104.22%||
ArraySeason    |  4000    |  1047 |  44.7%||
ArraySeasonInt |  4000    |  407 |  17.37%|int without unboxing|
ArraySeason3d  |  4000    |  1288 |  54.99%||
SortedSeason   |  4000    |  1654 |  70.62%||
SortedSeasonInt|  4000    |  748 |  31.93%|int without unboxing|
Vector         |  8000    |  8671 |  100.00%  |reference point.|
ArrayList      |  8000    |  8818 |  101.69%||
ArrayTape      |  8000    |  9012 |  103.93%||
ArraySeason    |  8000    |  2057 |  23.72%||
ArraySeasonInt |  8000    |  634 |  7.31%|int without unboxing|
ArraySeason3d  |  8000    |  2465 |  28.42%||
SortedSeason   |  8000    |  3353 |  38.66%||
SortedSeasonInt|  8000    |  1515 |  17.47%|int without unboxing|
Vector         |  24000    |  75448 |  100.00%  |reference point.|
ArrayList      |  24000    |  72793 |  96.48%||
ArrayTape      |  24000    |  79419 |  105.26%||
ArraySeason    |  24000    |  6424 |  8.51%||
ArraySeasonInt |  24000    |  2021 |  2.67%|int without unboxing|
ArraySeason3d  |  24000    |  7525 |  9.97%||
SortedSeason   |  24000    |  11172 |  14.8%||
SortedSeasonInt|  24000    |  5095 |  6.75%|int without unboxing|

<sub>(This is an arbitrary measurement and not representative)</sub>

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


