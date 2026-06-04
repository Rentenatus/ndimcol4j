/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol.ref;

import de.jare.ndimcol.utils.SortedSeasonSetAddResult;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArraySeason;
import java.util.Comparator;
import java.util.function.BiPredicate;
//prim:import java.util.function.Consumer; // forAll Method
import java.util.function.Consumer;

/**
 * MovieMap with sorted keys.
 *
 * <p>
 * Keys are stored in a {@link SortedSeasonSet} and values are stored in a parallel {@link ArrayMovie}. The key
 * insertion result is taken from {@link SortedSeasonSet#resAdd(Object)} so that values can be inserted or replaced at
 * the same logical position.
 * </p>
 *
 * <p>
 * This type is intentionally not a {@link java.util.Map}. It is an ordered, index-based structure in the style of
 * {@link ArrayMovie}.
 * </p>
 *
 * @author Janusch Rentenatus
 * @param <T> key type
 * @param <V> value type
 */
public class MovieMap<T, V> {

    private final SortedSeasonSet<T> keys;
    private final ArrayMovie<V> values;

//noprim.start  
    /**
     * Creates a new MovieMap .
     *
     *
     * @param compT a Comparator&lt;T&gt; to compare elements
     * @param forward true for ascending order, false for descending order
     */
    public MovieMap(final Comparator<T> compT, final boolean forward) {
        this.keys = new SortedSeasonSet<>(compT, forward);
        this.values = new ArraySeason<>();
    }

    /**
     * Creates a new MovieMap.
     *
     *
     * @param predicate a BiPredicate&lt;T, T&gt; to compare elements. If A is not smaller than B and A is not greater
     * than B, then A is equal to B.
     */
    public MovieMap(final BiPredicate<T, T> predicate) {
        this.keys = new SortedSeasonSet<>(predicate);
        this.values = new ArraySeason<>();
    }

//noprim.end
//prim:    
//prim:     /**
//prim:     * Constructor for MovieMap.
//prim:     *
//prim:     */
//prim:    public MovieMap_APPEND_() {
//prim:        this.keys = new SortedSeasonSet_APPEND_();
//prim:        this.values = new ArraySeason<>();
//prim:    }
//prim.ende
    /**
     * Creates a new MovieMap backed by the given key set and value movie.
     *
     * <p>
     * Both structures are used directly and therefore must already be in sync.
     * </p>
     *
     * @param keys sorted key set
     * @param values value movie
     */
    private MovieMap(final SortedSeasonSet<T> keys, final ArrayMovie<V> values) {
        this.keys = keys;
        this.values = values;
        ensureSameSize();
    }

    /**
     * Returns the number of entries.
     *
     * @return size
     */
    public int size() {
        ensureSameSize();
        return keys.size();
    }

    /**
     * Returns true if this map is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this map contains one or more entries.
     *
     * @return true if not empty
     */
    public boolean hasRecord() {
        return !isEmpty();
    }

    /**
     * Returns a structural clone of the keys.
     *
     * @return cloned keys
     */
    public ArrayMovie<T> getKeysMovie() {
        return keys.cloneMovie();
    }

    /**
     * Returns a structural walker over the keys.
     *
     * @return cloned keys
     */
    public IteratorWalker<T> getKeysWalker() {
        return new IterSeasonWalkerImmutable<>(keys);
    }

    /**
     * Returns a structural clone of the values.
     *
     * @return cloned values
     */
    public ArrayMovie<V> getValuesMovie() {
        return values.cloneMovie();
    }

    /**
     * Returns all keys as a new object array.
     *
     * @return keys as array
     */
    public Object[] getKeysArray() {
        return keys.toArray();
    }

    /**
     * Returns all values as a new object array.
     *
     * @return values as array
     */
//prim:     public Object[] getValuesArray() {
    public Object[] getValuesArray() {
        return values.toArray();
    }

    /**
     * Returns all keys in the given target array type.
     *
     * @param <U> array component type
     * @param arr target array
     * @return keys as typed array
     */
    public <U> U[] getKeysArray(final U[] arr) {
        return keys.toArray(arr);
    }

    /**
     * Returns all values in the given target array type.
     *
     * @param &lt;U&gt array component type
     * @param arr target array
     * @return values as typed array
     */
//prim:     public <U> U[] getValuesArray(final U[] arr) {
    public <U> U[] getValuesArray(final U[] arr) {
        return values.toArray(arr);
    }

    /**
     * Returns the first key.
     *
     * @return first key
     */
    public T firstKey() {
        return keys.first();
    }

    /**
     * Returns the first value.
     *
     * @return first value
     */
    public V firstValue() {
        return values.first();
    }

    /**
     * Returns the last key.
     *
     * @return last key
     */
    public T lastKey() {
        return keys.last();
    }

    /**
     * Returns the last value.
     *
     * @return last value
     */
    public V lastValue() {
        return values.last();
    }

    /**
     * Returns the key at the given index.
     *
     * @param index index
     * @return key
     */
    public T getKey(final int index) {
        return keys.get(index);
    }

    /**
     * Returns the value at the given index.
     *
     * @param index index
     * @return value
     */
    public V getValue(final int index) {
        return values.get(index);
    }

    /**
     * Returns the entry at the given index.
     *
     * @param index index
     * @return entry
     */
    public MovieEntry<T, V> getEntry(final int index) {
        return new MovieEntry<>(keys.get(index), values.get(index));
    }

    /**
     * Returns the index of the given key, or -1 if not found.
     *
     * @param key key to search
     * @return index or -1
     */
    public int indexOfKey(final Object key) {
        return keys.indexOf(key);
    }

    /**
     * Returns true if the key exists.
     *
     * @param key key to test
     * @return true if present
     */
    public boolean containsKey(final Object key) {
        return keys.contains(key);
    }

    /**
     * Returns true if the value exists.
     *
     * @param value value to test
     * @return true if present
     */
    public boolean containsValue(final Object value) {
        return values.contains(value);
    }

    /**
     * Adds or replaces a key-value pair using SortedSeasonSet.resAdd(...).
     *
     * <p>
     * If the key is inserted, the value is inserted at the same index. If the key is replaced, the value is replaced at
     * the same index.
     * </p>
     *
     * @param key key to add or replace
     * @param value value to add or replace
     * @return result of the key add/replace operation
     */
    public final SortedSeasonSetAddResult put(final T key, final V value) {
        final SortedSeasonSetAddResult result = keys.resAdd(key);

        final int index = result.foundIndex;
        if (index < 0) {
            return result;
        }

        if (result.changed) {
            values.addAt(index, value);
        } else {
            values.set(index, value);
        }

        ensureSameSize();
        return result;
    }

    /**
     * Returns the first value for the given key, or null if not found.
     *
     * <p>
     * This method cannot distinguish between "not found" and a stored null value.
     * </p>
     *
     * @param key key to search
     * @return matching value or null
     */
    public V getByKey(final Object key) {
        final int index = keys.indexOf(key);
        return index >= 0 ? values.get(index) : null;
    }

    /**
     * Removes the entry at the given index.
     *
     * @param index index to remove
     * @return removed entry
     */
    public MovieEntry<T, V> removeAt(final int index) {
        final T removedKey = keys.removeAt(index);
        final V removedValue = values.removeAt(index);
        ensureSameSize();
        return new MovieEntry<>(removedKey, removedValue);
    }

    /**
     * Removes the first entry for the given key.
     *
     * @param key key to remove
     * @return removed value or null if absent
     */
    public V removeByKey(final Object key) {
        final int index = keys.indexOf(key);
        if (index < 0) {
            return null;
        }
        return removeAt(index).value();
    }

    /**
     * Removes all entries.
     */
    public void clear() {
        keys.clear();
        values.clear();
        ensureSameSize();
    }

    /**
     * Returns a cloned sub map.
     *
     * @param fromIndex low endpoint inclusive
     * @param toIndex high endpoint exclusive
     * @return new sub map
     */
    public MovieMap<T, V> subMovieMap(final int fromIndex, final int toIndex) {
        final ArrayMovie<T> subKeysMovie = keys.subMovie(fromIndex, toIndex);
        final SortedSeasonSet<T> subKeys = keys.union(subKeysMovie.emptyMovie(0));
        subKeys.addMovie(subKeysMovie);

        return new MovieMap<>(
                subKeys,
                values.subMovie(fromIndex, toIndex)
        );
    }

    /**
     * Returns a structural clone of this map.
     *
     * @return cloned map
     */
    public MovieMap<T, V> cloneMovieMap() {
        final ArrayMovie<T> clonedKeysMovie = keys.cloneMovie();
        final SortedSeasonSet<T> clonedKeys = keys.union(clonedKeysMovie.emptyMovie(0));
        clonedKeys.addMovie(clonedKeysMovie);

        return new MovieMap<>(
                clonedKeys,
                values.cloneMovie()
        );
    }

    /**
     * Applies the given action to all entries in encounter order.
     *
     * @param action action to perform
     */
    public void forAll(final Consumer<MovieEntry<T, V>> action) {
        final int size = size();
        for (int i = 0; i < size; i++) {
            action.accept(new MovieEntry<>(keys.get(i), values.get(i)));
        }
    }

    private void ensureSameSize() {
        if (keys.size() != values.size()) {
            throw new IllegalStateException(
                    "MovieMap invariant violated: keys.size()=" + keys.size()
                    + ", values.size()=" + values.size());
        }
    }

    /**
     * Immutable entry of MovieMap.
     *
     * @param <K> key type
     * @param <V> value type
     */
//prim:    public static record MovieEntry_APPEND_<V>(_PRIM_ key, V value) {
    public static record MovieEntry<K, V>(K key, V value) {

    }
}
