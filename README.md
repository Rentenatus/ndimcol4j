# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.


---

**Coding hash comparison**

This collection offers a special way of calculating HASH values. This is best explained with an example: Let list1, list2, and list3 be arbitrary and list123 be an empty list, then:
```
list123.addAll(list1);
list123.addAll(list2);
list123.addAll(list3);
hash = list123.hashCode();
```
can be calculated directly from the hashes of the three lists:
```
hash = list1.hashCode();
hash = list2.combine(hash,list2.size(),list2.hashCode());
hash = list3.combine(hash,list3.size(),list3.hashCode());
```
And because ArraySeason manages an ArrayTape of ArrayTape, the hash value can be quickly determined from the individual sublists. Manipulations don't have to affect all sublists, so repeated calculations are faster.


Replacing a single element in the list can also calculate the new hash value directly:
```
    public T set(int index, T element) {
        int power = size() - 1 - index;
        hashCode = replace(hashCode,
                    power,
                    strategie.hashCode(get(index)),
                    strategie.hashCode(element));
        return super.set(index, element);
    }
```


**Speed comparison:**

| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|--------------|------------|---------------|----------------|------------------|
Vector         |  24000    |  74475 |  100.00%  |reference point.|
ArrayList      |  24000    |  75888 |  101.89%||
ArrayTape      |  24000    |  77140 |  103.57%||
ArraySeason    |  24000    |  6144 |  8.24%||
ArraySeasonHashable|  24000    |  6460 |  8.67%|with hash cache|
ArraySeasonHashableInt |  24000    |  2183 |  2.93%|int without unboxing; hash cache|
ArraySeasonInt |  24000    |  2126 |  2.85%|int without unboxing|
ArraySeason3d  |  24000    |  8096 |  10.87%||
SortedSeason   |  24000    |  10495 |  14.09%||
SortedSeasonInt|  24000    |  4038 |  5.42%|int without unboxing|
Vector         |  8000    |  8849 |  100.00%  |reference point.|
ArrayList      |  8000    |  8906 |  100.64%||
ArrayTape      |  8000    |  8959 |  101.24%||
ArraySeason    |  8000    |  2153 |  24.33%||
ArraySeasonHashable|  8000    |  2180 |  24.63%|with hash cache|
ArraySeasonHashableInt |  8000    |  659 |  7.44%|int without unboxing; hash cache|
ArraySeasonInt |  8000    |  734 |  8.29%|int without unboxing|
ArraySeason3d  |  8000    |  2569 |  29.03%||
SortedSeason   |  8000    |  3135 |  35.42%||
SortedSeasonInt|  8000    |  1197 |  13.52%|int without unboxing|
Vector         |  4000    |  2321 |  100.00%  |reference point.|
ArrayList      |  4000    |  2338 |  100.73%||
ArrayTape      |  4000    |  2389 |  102.92%||
ArraySeason    |  4000    |  1096 |  47.22%||
ArraySeasonHashable|  4000    |  1088 |  46.87%|with hash cache|
ArraySeasonHashableInt |  4000    |  340 |  14.64%|int without unboxing; hash cache|
ArraySeasonInt |  4000    |  349 |  15.03%|int without unboxing|
ArraySeason3d  |  4000    |  1289 |  55.53%||
SortedSeason   |  4000    |  1521 |  65.53%||
SortedSeasonInt|  4000    |  586 |  25.24%|int without unboxing|
Vector         |  240    |  51 |  100.00%  |reference point.|
ArrayList      |  240    |  50 |  98.03%||
ArrayTape      |  240    |  51 |  100.0%||
ArraySeason    |  240    |  63 |  123.52%||
ArraySeasonHashable|  240    |  62 |  121.56%|with hash cache|
ArraySeasonHashableInt |  240    |  18 |  35.29%|int without unboxing; hash cache|
ArraySeasonInt |  240    |  18 |  35.29%|int without unboxing|
ArraySeason3d  |  240    |  63 |  123.52%||
SortedSeason   |  240    |  58 |  113.72%||
SortedSeasonInt|  240    |  18 |  35.29%|int without unboxing|
Vector         |  80    |  15 |  100.00%  |reference point.|
ArrayList      |  80    |  15 |  100.0%||
ArrayTape      |  80    |  15 |  100.0%||
ArraySeason    |  80    |  19 |  126.66%||
ArraySeasonHashable|  80    |  19 |  126.66%|with hash cache|
ArraySeasonHashableInt |  80    |  4 |  26.66%|int without unboxing; hash cache|
ArraySeasonInt |  80    |  5 |  33.33%|int without unboxing|
ArraySeason3d  |  80    |  19 |  126.66%||
SortedSeason   |  80    |  16 |  106.66%||
SortedSeasonInt|  80    |  5 |  33.33%|int without unboxing|

<sub>(This is an arbitrary measurement and not representative)</sub>

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


