/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
package de.jare.ndimcol.primshort;

import de.jare.ndimcol.utils.SortedSeasonSetAddResult;
import de.jare.ndimcol.ref.ArrayMovie;
import de.jare.ndimcol.ref.ArraySeason;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer; // forAll Method

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
 * @param <V> value type
 */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
public class MovieMapShort<V> {

    private final SortedSeasonSetShort keys;
    private final ArrayMovie<V> values;

    
     /**
     * Constructor for MovieMap.
     *
     */
    public MovieMapShort() {
        this.keys = new SortedSeasonSetShort();
        this.values = new ArraySeason<>();
    }
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    private MovieMapShort(final SortedSeasonSetShort keys, final ArrayMovie<V> values) {
        this.keys = keys;
        this.values = values;
        ensureSameSize();
    }

    /**
     * Returns the number of entries.
     *
     * @return size
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int size() {
        ensureSameSize();
        return keys.size();
    }

    /**
     * Returns true if this map is empty.
     *
     * @return true if empty
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this map contains one or more entries.
     *
     * @return true if not empty
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean hasRecord() {
        return !isEmpty();
    }

    /**
     * Returns a structural clone of the keys.
     *
     * @return cloned keys
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayMovieShort getKeysMovie() {
        return keys.cloneMovie();
    }

    /**
     * Returns a structural clone of the values.
     *
     * @return cloned values
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public ArrayMovie<V> getValuesMovie() {
        return values.cloneMovie();
    }

    /**
     * Returns all keys as a new object array.
     *
     * @return keys as array
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public short[] getKeysArray() {
        return keys.toArray();
    }

    /**
     * Returns all values as a new object array.
     *
     * @return values as array
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
     public Object[] getValuesArray() {
        return values.toArray();
    }

    /**
     * Returns all keys in the given target array type.
     *
     * @param arr target array
     * @return keys as typed array
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public short[] getKeysArray(final short[] arr) {
        return keys.toArray(arr);
    }

    /**
     * Returns all values in the given target array type.
     *
     * @param &lt;U&gt array component type
     * @param arr target array
     * @return values as typed array
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
     public <U> U[] getValuesArray(final U[] arr) {
        return values.toArray(arr);
    }

    /**
     * Returns the first key.
     *
     * @return first key
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public short firstKey() {
        return keys.first();
    }

    /**
     * Returns the first value.
     *
     * @return first value
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public V firstValue() {
        return values.first();
    }

    /**
     * Returns the last key.
     *
     * @return last key
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public short lastKey() {
        return keys.last();
    }

    /**
     * Returns the last value.
     *
     * @return last value
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public V lastValue() {
        return values.last();
    }

    /**
     * Returns the key at the given index.
     *
     * @param index index
     * @return key
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public short getKey(final int index) {
        return keys.get(index);
    }

    /**
     * Returns the value at the given index.
     *
     * @param index index
     * @return value
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public V getValue(final int index) {
        return values.get(index);
    }

    /**
     * Returns the entry at the given index.
     *
     * @param index index
     * @return entry
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public MovieEntryShort<V> getEntry(final int index) {
        return new MovieEntryShort(keys.get(index), values.get(index));
    }

    /**
     * Returns the index of the given key, or -1 if not found.
     *
     * @param key key to search
     * @return index or -1
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public int indexOfKey(final short key) {
        return keys.indexOf(key);
    }

    /**
     * Returns true if the key exists.
     *
     * @param key key to test
     * @return true if present
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean containsKey(final short key) {
        return keys.contains(key);
    }

    /**
     * Returns true if the value exists.
     *
     * @param value value to test
     * @return true if present
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public boolean containsValue(final short value) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public final SortedSeasonSetAddResult put(final short key, final V value) {
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public V getByKey(final short key) {
        final int index = keys.indexOf(key);
        return index >= 0 ? values.get(index) : null;
    }

    /**
     * Removes the entry at the given index.
     *
     * @param index index to remove
     * @return removed entry
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public MovieEntryShort<V> removeAt(final int index) {
        final short removedKey = keys.removeAt(index);
        final V removedValue = values.removeAt(index);
        ensureSameSize();
        return new MovieEntryShort(removedKey, removedValue);
    }

    /**
     * Removes the first entry for the given key.
     *
     * @param key key to remove
     * @return removed value or null if absent
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public V removeByKey(final short key) {
        final int index = keys.indexOf(key);
        if (index < 0) {
            return null;
        }
        return removeAt(index).value();
    }

    /**
     * Removes all entries.
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public MovieMapShort<V> subMovieMap(final int fromIndex, final int toIndex) {
        final ArrayMovieShort subKeysMovie = keys.subMovie(fromIndex, toIndex);
        final SortedSeasonSetShort subKeys = keys.union(subKeysMovie.emptyMovie(0));
        subKeys.addMovie(subKeysMovie);

        return new MovieMapShort(
                subKeys,
                values.subMovie(fromIndex, toIndex)
        );
    }

    /**
     * Returns a structural clone of this map.
     *
     * @return cloned map
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public MovieMapShort<V> cloneMovieMap() {
        final ArrayMovieShort clonedKeysMovie = keys.cloneMovie();
        final SortedSeasonSetShort clonedKeys = keys.union(clonedKeysMovie.emptyMovie(0));
        clonedKeys.addMovie(clonedKeysMovie);

        return new MovieMapShort(
                clonedKeys,
                values.cloneMovie()
        );
    }

    /**
     * Applies the given action to all entries in encounter order.
     *
     * @param action action to perform
     */
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public void forAll(final Consumer<MovieEntryShort<V>> action) {
        final int size = size();
        for (int i = 0; i < size; i++) {
            action.accept(new MovieEntryShort(keys.get(i), values.get(i)));
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
    // #### This code has been generated. Please do not make any changes here.
    // #### Modify package 'de.jare.ndimcol.ref' and use 'GeneratePrimitiveJavaFiles'
    public static record MovieEntryShort<V>(short key, V value) {

    }
}
