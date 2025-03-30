# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.


---

**Comparison:**

| Algorithm     | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|---------------|------------|---------------|----------------|------------------|
| Vector        | 80         | 67            | 100.00         | Reference point  |
| ArrayList     | 80         | 60            | 89.55          |                  |
| ArrayTape     | 80         | 62            | 92.53          |                  |
| ArraySeason   | 80         | 61            | 91.04          |                  |
| SortedSeason  | 80         | 76            | 113.43         |                  |
| Vector        | 4000       | 5811          | 100.00         | Reference point  |
| ArrayList     | 4000       | 5843          | 100.55         |                  |
| ArrayTape     | 4000       | 5897          | 101.47         |                  |
| ArraySeason   | 4000       | 2462          | 42.36          |                  |
| SortedSeason  | 4000       | 3960          | 68.14          |                  |
| Vector        | 8000       | 21735         | 100.00         | Reference point  |
| ArrayList     | 8000       | 21855         | 100.55         |                  |
| ArrayTape     | 8000       | 21869         | 100.61         |                  |
| ArraySeason   | 8000       | 4887          | 22.48          |                  |
| SortedSeason  | 8000       | 8250          | 37.95          |                  |


---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


