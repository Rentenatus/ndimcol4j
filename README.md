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

---

**3D mesh‑glue system for jMonkeyEngine 3 (jME3)**

In jMonkeyEngine 3 (jME3), a 3D scene often contains many small mesh fragments—such as sprites, vegetation quads, modular geometry pieces, or procedurally generated elements. Rendering each fragment as a separate Geometry object is inefficient because every object produces its own draw call, state change, and buffer binding. This quickly becomes a performance bottleneck, especially when hundreds or thousands of small meshes are involved.

The mesh‑glue system solves this problem by combining many small mesh fragments (“atoms”) into one large, optimized mesh. Instead of letting jME3 batch the geometry at runtime, the system builds a single mesh manually, giving full control over all parameter.

Because the system performs all merging and updates directly inside the mesh data, it eliminates the need for jME3’s post‑processing batch optimizations and avoids repeated re‑batching by applying corrections directly within the combined mesh.
```
// 1) Create a GlueConfig for Position(3), TexCoord(3), Color(4)
GlueConfig<VertexBuffer.Type> config = new GlueConfig<>(
    new VertexBuffer.Type[]{
        VertexBuffer.Type.Position,
        VertexBuffer.Type.TexCoord,
        VertexBuffer.Type.Color
    },
    new int[]{3, 3, 4}
);

// Register which attributes represent Position and TexCoord
config.registerPosition(VertexBuffer.Type.Position);
config.registerTexture(VertexBuffer.Type.TexCoord);

// ---------------------------
// Create first mesh atom
// ---------------------------
GluableSingleMesh<VertexBuffer.Type> atom1 = new GluableSingleMesh<>(config);

// Local translation (optional)
atom1.setPos(0, 0, 0);

// Position buffer (4 vertices)
atom1.setContent(VertexBuffer.Type.Position, new float[]{
    0,0,0,
    1,0,0,
    1,1,0,
    0,1,0
});

// TexCoord buffer (u,v,layer)
atom1.setContent(VertexBuffer.Type.TexCoord, new float[]{
    0,0,2,
    1,0,2,
    1,1,2,
    0,1,2
});

// Color buffer
atom1.setContent(VertexBuffer.Type.Color, new float[]{
    1,0,0,1,
    1,0,0,1,
    1,0,0,1,
    1,0,0,1
});

// Index buffer
atom1.setIndexbuffer(new short[]{0,1,2, 0,2,3});

// ---------------------------
// Create second mesh atom
// ---------------------------
GluableSingleMesh<VertexBuffer.Type> atom2 = new GluableSingleMesh<>(config);

// Local translation (instead of hardcoding positions)
atom2.setPos(2, 0, 0);

// Base quad at origin (will be translated by setPos)
atom2.setContent(VertexBuffer.Type.Position, new float[]{
    0,0,0,
    1,0,0,
    1,1,0,
    0,1,0
});

// TexCoord buffer (u,v,layer)
atom2.setContent(VertexBuffer.Type.TexCoord, new float[]{
    0,0,5,
    1,0,5,
    1,1,5,
    0,1,5
});

// Color buffer
atom2.setContent(VertexBuffer.Type.Color, new float[]{
    0,1,0,1,
    0,1,0,1,
    0,1,0,1,
    0,1,0,1
});

// Index buffer
atom2.setIndexbuffer(new short[]{0,1,2, 0,2,3});

// ---------------------------
// Glue both atoms together
// ---------------------------
GluedMesh gluedMesh = new GluedMesh(config);

gluedMesh.add(atom1);
gluedMesh.add(atom2);

// Build the final merged mesh
gluedMesh.calculate();

// Retrieve the glued result
GluableSingleMesh<VertexBuffer.Type> result = gluedMesh.getGlued();
```

On‑the‑fly Manipulation

```
gluedMesh.setPos(atom2, 0, 2, 0); // move up by 2 units
gluedMesh.changeImageIndex(atom1, 7); // switch to texture layer 7
```

On‑the‑Fly Add Example
```
// Add third atom dynamically
gluedMesh.add(atom3);

// No need to call calculate() again
// enhance(atom3) is automatically invoked
```

---

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


---


**Speed comparison:**

| Algorithm    | Batch Size | Duration (ms) | Percentage (%) | Notes            |
|--------------|------------|---------------|----------------|------------------|
|Vector         |  50000    |  318529 |  100.00%  |reference point.|
|ArrayList      |  50000    |  312194 |  98.01%||
|ArrayTape      |  50000    |  317553 |  99.69%||
|ArraySeason    |  50000    |  16529 |  5.18%||
|ArraySeasonHashable|  50000    |  28183 |  8.84%|with hash cache|
|ArraySeasonHashableInt |  50000    |  8402 |  2.63%|int without unboxing; hash cache|
|ArraySeasonInt |  50000    |  18343 |  5.75%|int without unboxing|
|ArraySeason3d  |  50000    |  24023 |  7.54%||
|SortedSeason   |  50000    |  23860 |  7.49%||
|SortedSeasonInt|  50000    |  9572 |  3.0%|int without unboxing|
|Vector         |  24000    |  72325 |  100.00%  |reference point.|
|ArrayList      |  24000    |  73375 |  101.45%||
|ArrayTape      |  24000    |  75073 |  103.79%||
|ArraySeason    |  24000    |  10522 |  14.54%||
|ArraySeasonHashable|  24000    |  13593 |  18.79%|with hash cache|
|ArraySeasonHashableInt |  24000    |  3145 |  4.34%|int without unboxing; hash cache|
|ArraySeasonInt |  24000    |  6349 |  8.77%|int without unboxing|
|ArraySeason3d  |  24000    |  9207 |  12.73%||
|SortedSeason   |  24000    |  10526 |  14.55%||
|SortedSeasonInt|  24000    |  4278 |  5.91%|int without unboxing|
|Vector         |  8000    |  8485 |  100.00%  |reference point.|
|ArrayList      |  8000    |  8615 |  101.53%||
|ArrayTape      |  8000    |  8867 |  104.5%||
|ArraySeason    |  8000    |  2649 |  31.21%||
|ArraySeasonHashable|  8000    |  3106 |  36.6%|with hash cache|
|ArraySeasonHashableInt |  8000    |  761 |  8.96%|int without unboxing; hash cache|
|ArraySeasonInt |  8000    |  1256 |  14.8%|int without unboxing|
|ArraySeason3d  |  8000    |  2853 |  33.62%||
|SortedSeason   |  8000    |  3187 |  37.56%||
|SortedSeasonInt|  8000    |  1557 |  18.35%|int without unboxing|
|Vector         |  4000    |  2282 |  100.00%  |reference point.|
|ArrayList      |  4000    |  2355 |  103.19%||
|ArrayTape      |  4000    |  2370 |  103.85%||
|ArraySeason    |  4000    |  1170 |  51.27%||
|ArraySeasonHashable|  4000    |  1274 |  55.82%|with hash cache|
|ArraySeasonHashableInt |  4000    |  334 |  14.63%|int without unboxing; hash cache|
|ArraySeasonInt |  4000    |  501 |  21.95%|int without unboxing|
|ArraySeason3d  |  4000    |  1455 |  63.75%||
|SortedSeason   |  4000    |  1520 |  66.6%||
|SortedSeasonInt|  4000    |  736 |  32.25%|int without unboxing|
|Vector         |  640    |  157 |  100.00%  |reference point.|
|ArrayList      |  640    |  160 |  101.91%||
|ArrayTape      |  640    |  162 |  103.18%||
|ArraySeason    |  640    |  160 |  101.91%||
|ArraySeasonHashable|  640    |  160 |  101.91%|with hash cache|
|ArraySeasonHashableInt |  640    |  41 |  26.11%|int without unboxing; hash cache|
|ArraySeasonInt |  640    |  48 |  30.57%|int without unboxing|
|ArraySeason3d  |  640    |  187 |  119.1%||
|SortedSeason   |  640    |  200 |  127.38%||
|SortedSeasonInt|  640    |  76 |  48.4%|int without unboxing|
|Vector         |  240    |  50 |  100.00%  |reference point.|
|ArrayList      |  240    |  49 |  98.0%||
|ArrayTape      |  240    |  51 |  102.0%||
|ArraySeason    |  240    |  61 |  122.0%||
|ArraySeasonHashable|  240    |  59 |  118.0%|with hash cache|
|ArraySeasonHashableInt |  240    |  16 |  32.0%|int without unboxing; hash cache|
|ArraySeasonInt |  240    |  17 |  34.0%|int without unboxing|
|ArraySeason3d  |  240    |  61 |  122.0%||
|SortedSeason   |  240    |  58 |  116.0%||
|SortedSeasonInt|  240    |  21 |  42.0%|int without unboxing|
|Vector         |  120    |  23 |  100.00%  |reference point.|
|ArrayList      |  120    |  23 |  100.0%||
|ArrayTape      |  120    |  24 |  104.34%||
|ArraySeason    |  120    |  28 |  121.73%||
|ArraySeasonHashable|  120    |  28 |  121.73%|with hash cache|
|ArraySeasonHashableInt |  120    |  6 |  26.08%|int without unboxing; hash cache|
|ArraySeasonInt |  120    |  7 |  30.43%|int without unboxing|
|ArraySeason3d  |  120    |  29 |  126.08%||
|SortedSeason   |  120    |  26 |  113.04%||
|SortedSeasonInt|  120    |  9 |  39.13%|int without unboxing|
|Vector         |  80    |  15 |  100.00%  |reference point.|
|ArrayList      |  80    |  15 |  100.0%||
|ArrayTape      |  80    |  16 |  106.66%||
|ArraySeason    |  80    |  18 |  120.0%||
|ArraySeasonHashable|  80    |  18 |  120.0%|with hash cache|
|ArraySeasonHashableInt |  80    |  4 |  26.66%|int without unboxing; hash cache|
|ArraySeasonInt |  80    |  4 |  26.66%|int without unboxing|
|ArraySeason3d  |  80    |  18 |  120.0%||
|SortedSeason   |  80    |  18 |  120.0%||
|SortedSeasonInt|  80    |  6 |  40.0%|int without unboxing| 


<sub>(This is an arbitrary measurement and not representative)</sub>

---

**License Notice:**

This description is subject to the **Eclipse Public License 2.0**. For more information about the license, please visit [here](https://www.eclipse.org/legal/epl-2.0/).


