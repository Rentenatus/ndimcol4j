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
ArrayTape      |  80    |  28 |  90.32%||
ArraySeason    |  80    |  42 |  135.48%||
ArraySeasonInt |  80    |  32 |  103.22%|int without unboxing|
ArraySeason3d  |  80    |  41 |  132.25%||
SortedSeason   |  80    |  51 |  164.51%||
SortedSeasonInt|  80    |  29 |  93.54%|int without unboxing|
Vector         |  240    |  90 |  100.00%  |reference point.|
ArrayList      |  240    |  84 |  93.33%||
ArrayTape      |  240    |  78 |  86.66%||
ArraySeason    |  240    |  114 |  126.66%||
ArraySeasonInt |  240    |  93 |  103.33%|int without unboxing|
ArraySeason3d  |  240    |  106 |  117.77%||
SortedSeason   |  240    |  97 |  107.77%||
SortedSeasonInt|  240    |  63 |  70.0%|int without unboxing|
Vector         |  4000    |  2360 |  100.00%  |reference point.|
ArrayList      |  4000    |  2388 |  101.18%||
ArrayTape      |  4000    |  2437 |  103.26%||
ArraySeason    |  4000    |  1064 |  45.08%||
ArraySeasonInt |  4000    |  445 |  18.85%|int without unboxing|
ArraySeason3d  |  4000    |  1284 |  54.4%||
SortedSeason   |  4000    |  1713 |  72.58%||
SortedSeasonInt|  4000    |  769 |  32.58%|int without unboxing|
Vector         |  8000    |  8886 |  100.00%  |reference point.|
ArrayList      |  8000    |  8955 |  100.77%||
ArrayTape      |  8000    |  9167 |  103.16%||
ArraySeason    |  8000    |  2096 |  23.58%||
ArraySeasonInt |  8000    |  687 |  7.73%|int without unboxing|
ArraySeason3d  |  8000    |  2525 |  28.41%||
SortedSeason   |  8000    |  3097 |  34.85%||
SortedSeasonInt|  8000    |  1541 |  17.34%|int without unboxing|
Vector         |  24000    |  75941 |  100.00%  |reference point.|
ArrayList      |  24000    |  75286 |  99.13%||
ArrayTape      |  24000    |  78870 |  103.85%||
ArraySeason    |  24000    |  6619 |  8.71%||
ArraySeasonInt |  24000    |  2133 |  2.8%|int without unboxing|
ArraySeason3d  |  24000    |  7860 |  10.35%||
SortedSeason   |  24000    |  9977 |  13.13%||
SortedSeasonInt|  24000    |  5164 |  6.8%|int without unboxing|

<sub>(This is an arbitrary measurement and not representative)</sub>

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


