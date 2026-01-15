# ndimcol4j
Two and perhaps three-dimensional array collection.

My ArrayTape is a collection similar to ArrayList, but ArraySeason maintains an ArrayTape of ArrayTape. This makes my ArraySeason up to 5 times faster than a classic ArrayList when adding elements right between existing ones to the collection... because the partitions are much smaller than the entire set, and thus the shuffling is shorter.

ArraySeason is the basis for SortedSeasonSet, which, despite binary search, can still be faster than a simple ArrayList without sorting.

---

**Coding lambda**

Lambdas can now also be used for the primitive types; the LongPredicate, IntPredicate, and LongConsumer, IntConsumer from java.util.function 
plus the own FloatPredicate, FloatConsumer also operate on primitives.

Example from TestNG

```
    public void testFloat() {
        ArraySeasonFloat data = new ArraySeasonFloat();
        float f = 0.92345f;
        for (int i = 0; i < 77420; i++) {
            data.add(i * f);
            f = -f;
        }
        final Integer[] counter = {0};
        final Integer[] expected = {77420};
        data.forEach(
                value -> value > 0, // Predicate: checks whether the value is positive
                value -> assertTrue(value > 0),
                value -> assertFalse(value > 0)
        );
        data.forEach(
                value -> value > 0, // Predicate: checks whether the value is positive
                value -> assertTrue(value > 0)
        );
        data.forEach(value -> counter[0]++);
        assertEquals(counter, expected);
    }
```

---

**Coding hash comparison**

This collection *ArraySeasonHashable* offers a special way of calculating HASH values. This is best explained with an example: Let list1, list2, and list3 be arbitrary and list123 be an empty list, then:
```
list123.addAll(list1);
list123.addAll(list2);
list123.addAll(list3);
hash = list123.hashCode();
```
can be calculated directly from the hashes of the three lists:
```
hash = list1.hashCode();
hash = list2.combineListHash(hash);
hash = list3.combineListHash(hash);
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

**HashSet without HashSet**

*SortedSeasonSet* provides a memory‑efficient alternative to Java’s HashSet by eliminating the need for bucket structures, wrapper nodes, or tree‑based collision handling. Instead of storing elements in hash buckets, the collection keeps all elements sorted by their hash code inside an *ArraySeason<T>* (a fragmented, two‑dimensional array structure).

This design offers several advantages:

- No bucket or tree nodes:  
Elements are stored directly in compact array segments, avoiding the additional objects normally required by HashMap/HashSet internals.

- Efficient lookup via interval nesting:  
Because the hash codes are sorted, the set locates the correct hash interval using binary search. Only elements with the same hash code need to be checked for equality, keeping collision handling lightweight.

- Low memory footprint:  
The absence of wrapper nodes and linked structures reduces object count and improves cache locality, which is especially beneficial for large datasets.

- Fast modifications through fragmentation:  
Since ArraySeason<T> splits the data into smaller partitions, insertions and shifts affect only a fraction of the total elements. This makes updates significantly faster than in monolithic arrays.

SortedSeasonSet therefore behaves like a HashSet—unique elements, fast lookup—but achieves this without relying on Java’s hash table machinery. The result is a compact, cache‑friendly, and high‑performance set implementation suitable for large collections and performance‑critical applications.

Example:
```
// Ambiguity predicate: two objects are considered identical only if they are the same instance (o1 == o2)
final BiPredicateAmbiguityIdentity<T> ambiguity = new BiPredicateAmbiguityIdentity<>();

// Hash ordering predicate: elements are sorted by their hash code;
// if two elements share the same hash code, the ambiguity predicate decides whether they are identical
final BiPredicateHashGr<T> predicate = new BiPredicateHashGr<>();

// Create a SortedSeasonSet that behaves like a HashSet without using Java's HashSet infrastructure
SortedSeasonSet<T> setHash = new SortedSeasonSet<>(predicate, ambiguity);
```


**Speed comparison:**

| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|--------------|------------|---------------|----------------|------------------|
Vector         |  50000    |  314212 |  100.00%  |reference point.|
ArrayList      |  50000    |  317340 |  100.99%||
ArrayTape      |  50000    |  323296 |  102.89%||
ArraySeason    |  50000    |  12672 |  4.03%||
ArraySeasonHashable|  50000    |  13417 |  4.27%|with hash cache|
ArraySeasonHashableInt |  50000    |  4424 |  1.4%|int without unboxing; hash cache|
ArraySeasonInt |  50000    |  4559 |  1.45%|int without unboxing|
ArraySeason3d  |  50000    |  28229 |  8.98%||
SortedSeason   |  50000    |  25013 |  7.96%||
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


