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
Vector         |  24000    |  75726 |  100.00%  |reference point.|
ArrayList      |  24000    |  72210 |  95.35%||
ArrayTape      |  24000    |  76983 |  101.65%||
ArraySeason    |  24000    |  6048 |  7.98%||
ArraySeasonHash|  24000    |  6431 |  8.49%||
ArraySeasonInt |  24000    |  2042 |  2.69%|int without unboxing|
ArraySeason3d  |  24000    |  8087 |  10.67%||
SortedSeason   |  24000    |  10520 |  13.89%||
SortedSeasonInt|  24000    |  3877 |  5.11%|int without unboxing|
Vector         |  8000    |  8707 |  100.00%  |reference point.|
ArrayList      |  8000    |  8824 |  101.34%||
ArrayTape      |  8000    |  9038 |  103.8%||
ArraySeason    |  8000    |  2185 |  25.09%||
ArraySeasonHash|  8000    |  2215 |  25.43%||
ArraySeasonInt |  8000    |  692 |  7.94%|int without unboxing|
ArraySeason3d  |  8000    |  2603 |  29.89%||
SortedSeason   |  8000    |  3187 |  36.6%||
SortedSeasonInt|  8000    |  1182 |  13.57%|int without unboxing|
Vector         |  4000    |  2341 |  100.00%  |reference point.|
ArrayList      |  4000    |  2352 |  100.46%||
ArrayTape      |  4000    |  2405 |  102.73%||
ArraySeason    |  4000    |  1115 |  47.62%||
ArraySeasonHash|  4000    |  1107 |  47.28%||
ArraySeasonInt |  4000    |  329 |  14.05%|int without unboxing|
ArraySeason3d  |  4000    |  1307 |  55.83%||
SortedSeason   |  4000    |  1524 |  65.1%||
SortedSeasonInt|  4000    |  565 |  24.13%|int without unboxing|
Vector         |  240    |  50 |  100.00%  |reference point.|
ArrayList      |  240    |  51 |  102.0%||
ArrayTape      |  240    |  51 |  102.0%||
ArraySeason    |  240    |  63 |  126.0%||
ArraySeasonHash|  240    |  63 |  126.0%||
ArraySeasonInt |  240    |  17 |  34.0%|int without unboxing|
ArraySeason3d  |  240    |  64 |  128.0%||
SortedSeason   |  240    |  56 |  112.0%||
SortedSeasonInt|  240    |  18 |  36.0%|int without unboxing|
Vector         |  80    |  16 |  100.00%  |reference point.|
ArrayList      |  80    |  15 |  93.75%||
ArrayTape      |  80    |  15 |  93.75%||
ArraySeason    |  80    |  19 |  118.75%||
ArraySeasonHash|  80    |  19 |  118.75%||
ArraySeasonInt |  80    |  4 |  25.0%|int without unboxing|
ArraySeason3d  |  80    |  19 |  118.75%||
SortedSeason   |  80    |  16 |  100.0%||
SortedSeasonInt|  80    |  5 |  31.25%|int without unboxing|

<sub>(This is an arbitrary measurement and not representative)</sub>

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


