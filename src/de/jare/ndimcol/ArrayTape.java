/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * An ArrayTape is a dynamic array implementation that allows for efficient insertion, deletion, and iteration of
 * elements. It is designed to be used in a variety of applications where a flexible and resizable array is needed.
 * <p>
 * This class implements the ArrayMovie interface and provides methods for adding, removing, and accessing elements, as
 * well as for iterating over the elements in the array.
 * <p>
 * ArrayMovie&lt;T&gt; extends Collection&lt;T&gt;.
 *
 * @author Janusch Rentenatus
 * @param <T> the type of elements in this tape
 */
public class ArrayTape<T> implements ArrayMovie<T> {

    public static final int DEFAULT_CAPACITY = 32;
    public static final int DEFAULT_PAGE = 64;
    public static final int DEFAULT_COUNTDOWN = DEFAULT_PAGE << 2;

    /**
     * Compares two objects for equality.If both objects are null, they are considered equal. If one is null and the
     * other is not, they are not equal. Otherwise, it uses the equals method of the first object to compare them.
     *
     * @param <U> the type
     * @param a the first object to compare
     * @param b the second object to compare
     * @return true if the objects are equal, false otherwise
     */
    public static <U> boolean equals(U a, U b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private Object[] elementData;
    private int size;
    private int page;
    private int updateCounter;
    private int trimCountDown;
    private IterTapeWalker<T> softWalker;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArrayTape() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.page = DEFAULT_PAGE;
        this.updateCounter = 0;
        this.trimCountDown = DEFAULT_COUNTDOWN;
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param initialCapacityOrZero the initial capacity of the ArrayTape
     */
    public ArrayTape(int initialCapacityOrZero) {
        this.elementData = new Object[initialCapacityOrZero > 0 ? initialCapacityOrZero : DEFAULT_CAPACITY];
        this.size = 0;
        this.page = DEFAULT_PAGE;
        this.updateCounter = 0;
        this.trimCountDown = DEFAULT_COUNTDOWN;
    }

    /**
     * Constructs a new ArrayTape that is a duplicate of the specified ArrayTape. This constructor copies the elements
     * from the given ArrayTape and initializes the page size, update counter, and trim countdown based on the original.
     *
     * @param original the ArrayTape to be duplicated
     */
    public ArrayTape(ArrayTape<T> original) {
        this.elementData = Arrays.copyOf(original.elementData, original.size + original.page);
        this.size = original.size;
        this.page = original.page;
        this.updateCounter = original.updateCounter;
        this.trimCountDown = original.trimCountDown;
        this.softWalker = null;
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
    public ArrayTape(List<T> list) {
        this.elementData = new Object[list.size() + (DEFAULT_PAGE >> 4)];
        for (int i = 0; i < list.size(); i++) {
            this.elementData[i] = list.get(i);
        }
        this.size = list.size();
        this.page = DEFAULT_PAGE; // Default value for the 'page' attribute
        this.updateCounter = 0;
        this.trimCountDown = DEFAULT_COUNTDOWN; // Initialize trim countdown
    }

    /**
     * Clears the ArrayTape by resetting the elementData array to its default capacity, setting the size to zero,
     * incrementing the update counter, and initializing the trim countdown. This effectively removes all elements from
     * the ArrayTape.
     */
    @Override
    public void clear() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.updateCounter++;
        this.trimCountDown = DEFAULT_COUNTDOWN; // Initialize trim countdown
    }

    /**
     * Returns the number of elements in the ArrayTape.
     *
     * @return the current size of the ArrayTape
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns true if the ArrayTape contains no elements.
     *
     * @return true if the ArrayTape is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns true if the ArrayTape contains one or more elements.
     *
     * @return true if the ArrayTape has elements, false otherwise
     */
    @Override
    public boolean hasRecord() {
        return this.size > 0;
    }

    /**
     * Adds the specified element to the end of the ArrayTape, ensuring capacity as needed.Increments the update counter
     * after adding the element.
     *
     * @param element the element to be added to the ArrayTape
     * @return true
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    @Override
    public boolean add(T element) {
        // ensureCapacity():fast:Sorry for redundant code.
        if (size >= elementData.length) {
            int newCapacity = elementData.length + page + (size >> 2);
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
        elementData[size++] = element;
        updateCounter++;
        return true;
    }

    /**
     * Adds the specified element at the specified position in the ArrayTape. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right. Increments the update counter after adding the
     * element.
     *
     * @param index the position at which the specified element is to be inserted
     * @param element the element to be added to the ArrayTape
     * @return true
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    @Override
    public boolean addAt(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size >= elementData.length) {
            int newCapacity = elementData.length + page + (size >> 2);
            Object[] newArray = new Object[newCapacity];
            // Copy elements up to the index
            System.arraycopy(elementData, 0, newArray, 0, index);
            // Copy remaining elements after the index
            System.arraycopy(elementData, index, newArray, index + 1, size - index);
            elementData = newArray;
        } else {
            // Only move elements by index
            System.arraycopy(elementData, index, elementData, index + 1, size - index);
        }
        elementData[index] = element;
        size++;
        updateCounter++;
        return true;
    }

    /**
     * Adds all elements in the specified collection to the end of this ArrayTape. Increments the update counter after
     * adding the elements.
     *
     * @param col collection containing elements to be added to this ArrayTape
     * @return true if this ArrayTape changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(Collection<? extends T> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        if (col.isEmpty()) {
            return false;
        }
        if (col instanceof ArrayTape<? extends T>) {
            ArrayTape<T> tape = (ArrayTape<T>) col;
            int newSize = size + tape.size;

            // Ensure capacity
            ensureCapacity(newSize);

            // Copy elements from tape
            System.arraycopy(tape.elementData, 0, elementData, size, tape.size);
            size = newSize;
        } else {
            for (T element : col) {
                add(element);
            }
        }
        updateCounter++;
        return true;
    }

    /**
     * Inserts all elements in the specified collection into this tape, starting at the specified position.
     *
     * @param index index at which to insert the first element from the specified collection
     * @param col collection containing elements to be added to this tape
     * @return true if this tape changed as a result of the call
     */
    public boolean addAll(int index, Collection<? extends T> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        if (col.isEmpty()) {
            return false;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        int colSize = col.size();
        int ns = size + colSize - 1;
        if (ns >= elementData.length || size <= col.size()) {
            int newCapacity = elementData.length + page + (ns >> 2);
            Object[] newArray = new Object[newCapacity];
            // Copy elements up to the index
            if (index > 0) {
                System.arraycopy(elementData, 0, newArray, 0, index);
            }
            // Copy remaining elements after the index
            if (size > index) {
                System.arraycopy(elementData, index, newArray, index + colSize, size - index);
            }
            // Copy collection
            // size = col.size() detect x.addAll(n,x) if x == col
            // size = col.size() detect x.addAll(n,Collections.unmodifiableList(x)) too!
            int i = index;
            for (T element : col) {
                newArray[i++] = element;
            }
            elementData = newArray;
        } else {
            // Only move elements by index
            System.arraycopy(elementData, index, elementData, index + colSize, size - index);
            // Copy collection
            int i = index;
            for (T element : col) {
                elementData[i++] = element;
            }
        }
        size += col.size();
        updateCounter++;
        return true;
    }

    /**
     * Ensures that the ArrayTape has enough capacity to accommodate the specified minimum capacity. If the current
     * capacity is insufficient, it increases the capacity by the value of the 'page' attribute.
     *
     * @param minCapacity the minimum capacity that the ArrayTape must accommodate
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    public void ensureCapacity(int minCapacity) {
        if (elementData.length < minCapacity) {
            int newCapacity = minCapacity + page + (size >> 2);
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /**
     * Returns the element at the specified position in the ArrayTape. Throws an IndexOutOfBoundsException if the index
     * is out of range.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in the ArrayTape
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        return (T) elementData[index];
    }

    /**
     * Returns the first element in the ArrayTape. Throws an IndexOutOfBoundsException if the ArrayTape is empty.
     *
     * @return the first element in the ArrayTape
     * @throws IndexOutOfBoundsException if the ArrayTape is empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public T first() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Tape is empty.");
        }
        return (T) elementData[0];
    }

    /**
     * Returns the last element in the ArrayTape. Throws an IndexOutOfBoundsException if the ArrayTape is empty.
     *
     * @return the last element in the ArrayTape
     * @throws IndexOutOfBoundsException if the ArrayTape is empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public T last() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Tape is empty.");
        }
        return (T) elementData[size - 1];
    }

    /**
     * Replaces the element at the specified position in the ArrayTape with the specified element. Returns the element
     * previously at the specified position. Increments the update counter if the element at the specified position is
     * not equal to the new element. Throws an IndexOutOfBoundsException if the index is out of range.
     *
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        @SuppressWarnings("unchecked")
        T oldElement = (T) elementData[index];
        elementData[index] = element;
        if (!equals(oldElement, element)) {
            updateCounter++;
        }
        return oldElement;
    }

    /**
     * Removes the element at the specified position in the ArrayTape. If the trim countdown is greater than zero, it
     * uses the fast removal method. Otherwise, it uses the trim removal method and resets the trim countdown.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    @Override
    public T removeAt(int index) {
        if (trimCountDown > 0) {
            return removeFast(index);
        }
        this.trimCountDown = page << 2;
        return removeTrim(index);
    }

    /**
     * Removes all occurrences of the specified element in the ArrayTape. If the specified element is null, it removes
     * all null elements.
     *
     * @param element the element to be removed from the ArrayTape
     * @return true if any elements were removed, false otherwise
     */
    public boolean removeAll(Object element) {
        int removed = 0;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    int numMoved = size - i - 1;
                    if (numMoved > 0) {
                        System.arraycopy(elementData, i + 1, elementData, i, numMoved);
                    }
                    elementData[--size] = null; // clear to let GC do its work
                    i--; // stay at the same index to check for more null elements
                    removed++;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elementData[i])) {
                    int numMoved = size - i - 1;
                    if (numMoved > 0) {
                        System.arraycopy(elementData, i + 1, elementData, i, numMoved);
                    }
                    elementData[--size] = null; // clear to let GC do its work
                    i--; // stay at the same index to check for more matching elements
                    removed++;
                }
            }
        }
        updateCounter++;
        trimCountDown -= removed;
        return removed > 0;
    }

    /**
     * Removes from this ArrayTape all of its elements that are contained in the specified collection.
     *
     * @param c the collection containing elements to be removed from this ArrayTape
     * @return true if this ArrayTape changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            if (removeAll(element)) {
                modified = true;
            }
        }
        if (trimCountDown < 0) {
            trimCapacity();
        }
        return modified;
    }

    /**
     * Retains only the elements in this ArrayTape that are contained in the specified collection. In other words,
     * removes from this ArrayTape all of its elements that are not contained in the specified collection.
     *
     * @param col collection containing elements to be retained in this collection
     * @return true if this ArrayTape changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean retainAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        if (col.isEmpty()) {
            clear();
            return false;
        }
        boolean modified = false;
        IterTapeWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            T element = walker.next();
            if (!col.contains(element)) {
                walker.removeBackward();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Protected method that removes the element at the specified position in the ArrayTape quickly, without resizing
     * the internal array. It decreases the size, updates the update counter, and decreases the trim countdown.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    protected T removeFast(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        @SuppressWarnings("unchecked")
        T ret = (T) elementData[index];
        int numElementsToMove = size - index - 1;
        if (numElementsToMove > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numElementsToMove);
        }
        elementData[--size] = null; // clear to let GC do its work
        updateCounter++;
        trimCountDown--;
        return ret;
    }

    /**
     * Removes the element at the specified position in the ArrayTape and resizes the internal array to size + page.
     * Copies the left and right sides (if present) into the new array and updates the size and update counter.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size)
     */
    public T removeTrim(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + ".");
        }
        @SuppressWarnings("unchecked")
        T ret = (T) elementData[index];
        Object[] newElementData = new Object[size + page - 1];

        // Copy elements from the left side (if any)
        if (index > 0) {
            System.arraycopy(elementData, 0, newElementData, 0, index);
        }

        // Copy elements from the right side (if any)
        if (index < size - 1) {
            System.arraycopy(elementData, index + 1, newElementData, index, size - index - 1);
        }

        // Update the old array
        elementData = newElementData;
        size--;
        updateCounter++;
        return ret;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for in the ArrayTape
     * @return the index of the first occurrence of the specified element, or -1 if the element is not found
     */
    @Override
    public int indexOf(Object element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in the ArrayTape, or -1 if the element is not
     * found. If the specified element is null, it checks for null elements in the ArrayTape.
     *
     * @param element the element to search for in the ArrayTape
     * @return the index of the last occurrence of the specified element, or -1 if the element is not found
     */
    @Override
    public int lastIndexOf(Object element) {
        if (element == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (element.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns true if this ArrayTape contains the specified element.
     *
     * @param element element whose presence in this ArrayTape is to be tested
     * @return true if this ArrayTape contains the specified element
     */
    @Override
    public boolean contains(Object element) {
        return indexOf(element) > -1;
    }

    /**
     * Returns true if this ArrayTape contains all elements in the specified collection.
     *
     * @param col the collection to be checked for containment in this ArrayTape
     * @return true if this ArrayTape contains all elements in the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean containsAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        for (Object element : col) {
            if (indexOf(element) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from the ArrayTape, if it is present. Returns the index of
     * the removed element, or -1 if the element was not found.
     *
     * @param element the element to be removed from the ArrayTape
     * @return the index of the removed element, or -1 if the element was not found
     */
    public int indexRemove(Object element) {
        int index = indexOf(element);
        if (index < 0) {
            return -1;
        }
        removeAt(index);
        return index;
    }

    /**
     * Removes the first occurrence of the specified element from the ArrayTape, if it is present. Returns the index of
     * the removed element, or -1 if the element was not found.
     *
     * @param element the element to be removed from the ArrayTape
     * @return the index of the removed element, or -1 if the element was not found
     */
    @Override
    public boolean remove(Object element) {
        int index = indexOf(element);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * Trim or increases the capacity of the ArrayTape by a fixed amount specified by the attribute 'page'. It also
     * resets the trim countdown to its default value.
     * <p>
     * The method creates a new array with a correct capacity, copies the elements from the current array into the new
     * array, and then assigns the new array back to the elementData attribute.
     *
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    protected void trimCapacity() {
        int newCapacity = elementData.length + page;
        elementData = Arrays.copyOf(elementData, newCapacity);
        this.trimCountDown = DEFAULT_COUNTDOWN;
    }

    /**
     * Returns the current page size of the ArrayTape.
     *
     * @return the current page size
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page size of the ArrayTape to the specified value. Increments the update counter after setting the new
     * page size.
     *
     * @param page the new page size to be set
     */
    public void setPage(int page) {
        this.page = page;
        updateCounter++;
    }

    /**
     * Returns an array containing all the elements in the ArrayTape. The runtime type of the returned array is that of
     * the specified array.
     *
     * @return an array containing all the elements in the ArrayTape
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elementData, size, elementData.getClass());
    }

    /**
     * Returns an array containing all the elements in the ArrayTape.The runtime type of the returned array is that of
     * the specified array. If the ArrayTape fits in the specified array, it is returned therein. Otherwise, a new array
     * is allocated with the runtime type of the specified array and the size of the ArrayTape.
     *
     * @param <U> the runtime type of the array to contain the elements of the ArrayTape
     * @param a the array into which the elements of the ArrayTape are to be stored, if it is big enough; otherwise, a
     * new array of the same runtime type is allocated for this purpose.
     * @return an array containing all the elements in the ArrayTape
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of
     * every element in this ArrayTape
     */
    @Override
    @SuppressWarnings("unchecked")
    public <U> U[] toArray(U[] a) {
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (U[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null; // Null-terminate if Param 'a' is larger than size
        }
        return a;
    }

    /**
     * Returns a List containing all the elements in the ArrayTape. The elements are in the same order as in the
     * ArrayTape.
     *
     * @return a List containing all the elements in the ArrayTape
     */
    @SuppressWarnings("unchecked")
    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((T) elementData[i]);
        }
        return list;
    }

    /**
     * Returns a IterTapeWalker that allows for soft (non-freezing) iteration over the elements in the ArrayTape.
     *
     * @return a IterTapeWalker for soft iteration over the elements in the ArrayTape
     */
    @Override
    public IterTapeWalker<T> softWalker() {
        if (softWalker != null) {
            return softWalker.goFirst();
        }
        softWalker = new IterTapeWalker<>(this);
        return softWalker;
    }

    /**
     * Returns a IterTapeWalker that allows for soft (non-freezing) iteration over the elements in the ArrayTape.
     *
     * @param atIndex start index for walk.
     * @return a IterTapeWalker for soft iteration over the elements in the ArrayTape
     */
    @Override
    public IterTapeWalker<T> leafWalker(int atIndex) {
        if (softWalker != null) {
            return softWalker.goLeafIndex(atIndex);
        }
        softWalker = new IterTapeWalker<>(this, atIndex);
        return softWalker;
    }

    /**
     * Returns a IterTapeWalkerFreeze that allows for freezing iteration over the elements in the ArrayTape.
     *
     * @return a IterTapeWalkerFreeze for freezing iteration over the elements in the ArrayTape
     */
    public IterTapeWalker<T> walker() {
        return new IterTapeWalkerFreeze<>(this);
    }

    /**
     * Returns an iterator over elements of type T.
     *
     * @return an Iterator.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            private int initialUpdateCounter = Integer.MIN_VALUE;

            @Override
            public boolean hasNext() {
                return currentIndex < size && elementData[currentIndex] != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("No more elements in the tape.");
                }
                if (initialUpdateCounter == Integer.MIN_VALUE) {
                    initialUpdateCounter = updateCounter;
                } else if (initialUpdateCounter != updateCounter) {
                    throw new ConcurrentModificationException("ArrayTape was modified during iteration.");
                }
                return (T) elementData[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * ArrayTape does not support splitting or gluing. This method is a no-op.
     */
    @Override
    public void splitOrGlue() {
        // NoOp
    }

    /**
     * Splits the ArrayTape in half and returns a new ArrayTape containing the second half. The original ArrayTape is
     * modified to contain the first half. If the size is less than or equal to 1, it returns null.
     *
     * @return a new ArrayTape containing the second half, or null if the size is less than or equal to 1
     */
    @Override
    public ArrayTape<T> splitInHalf() {
        if (size <= 1) {
            return null;
        }
        int halfSize = size / 2;
        ArrayTape<T> newTape = new ArrayTape<>();
        newTape.size = size - halfSize;
        newTape.elementData = new Object[newTape.size];
        System.arraycopy(elementData, halfSize, newTape.elementData, 0, newTape.size);

        // Create new elementData array for the current instance with only the first half
        Object[] newElementData = new Object[halfSize];
        System.arraycopy(elementData, 0, newElementData, 0, halfSize);
        elementData = newElementData;
        size = halfSize;

        // Adjust the leafWalker if necessary
        if (softWalker != null) {
            softWalker = softWalker.goLeafIndex(0); // Reset to the beginning of the remaining elements
        }

        return newTape;
    }

    @Override
    public void copyToArray(Object[] array, int offset) {
        if (array == null) {
            throw new NullPointerException("Target array cannot be null.");
        }
        if (offset < 0 || offset + size > array.length) {
            throw new IndexOutOfBoundsException("Offset out of bounds: " + offset + ".");
        }

        System.arraycopy(elementData, 0, array, offset, size);
    }

    /**
     * Returns a new ArrayMovie that is a sub-movie of the current ArrayTape, starting from the specified index to the
     * end index. The new ArrayMovie will have the same page size as the original.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ArrayMovie that is a sub-movie of the current ArrayTape
     */
    @Override
    public ArrayMovie<T> subMovie(int fromIndex, int toIndex) {
        if (fromIndex >= size || fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", Size: " + size + ".");
        }
        if (toIndex > size || toIndex <= 0) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", Size: " + size + ".");
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ".");
        }
        int newSize = toIndex - fromIndex;
        ArrayTape<T> subMovie = new ArrayTape<>(newSize);
        if (fromIndex < toIndex) {
            System.arraycopy(elementData, fromIndex, subMovie.elementData, 0, newSize);
        }
        subMovie.size = newSize;
        return subMovie;
    }

    /**
     * Creates a new empty ArrayTape with the specified initial capacity. The new ArrayTape will have the same page size
     * as the original.
     *
     * @param initialCapacityOrZero the initial capacity of the new movie or zero if no initial capacity is needed
     * @return a new empty ArrayTape with the specified initial capacity
     */
    @Override
    public ArrayTape<T> emptyMovie(int initialCapacityOrZero) {
        ArrayTape<T> ret = new ArrayTape<>(initialCapacityOrZero);
        ret.page = this.page;
        return ret;
    }

    /**
     * Returns the number of times the ArrayTape has been updated.
     *
     * @return the update counter value
     */
    public int getUpdateCounter() {
        return updateCounter;
    }

    @Override
    public int debug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < size(); i++) {
            final int index = offset + i;
            out.println(prefix + "t[" + i + "] =[" + index + "]= '" + elementData[i] + "'");
        }
        return offset + size();
    }

    /**
     * Returns the number of elements that can be added to the ArrayTape before it needs to be resized.
     *
     * @return the number of elements that can be added
     */
    @Override
    public int pageSpaceLeft() {
        return this.elementData == null ? 0 : (this.elementData.length - size);
    }

    /**
     * Returns a walker for the first occurrence of a hit considering the given predicate.
     *
     * @param predicate the predicate to be used for the search
     * @return a leaf walker for the first occurrence of a hit
     */
    @SuppressWarnings("unchecked")
    @Override
    public IteratorWalker<T> filterFirst(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (predicate.test((T) elementData[i])) {
                return new IterTapeWalker<>(this, i);
            }
        }
        return null;
    }

    /**
     * Returns a walker for the last occurrence of a hit considering the given predicate.
     *
     * @param predicate the predicate to be used for the search
     * @return a leaf walker for the last occurrence of a hit
     */
    @SuppressWarnings("unchecked")
    @Override
    public IteratorWalker<T> filterLast(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate cannot be null.");
        }
        for (int i = size - 1; i >= 0; i--) {
            if (predicate.test((T) elementData[i])) {
                return new IterTapeWalker<>(this, i);
            }
        }
        return null;
    }

    /**
     * Return a new movie containing all elements that match the given predicate.
     *
     * @param predicate the predicate to be used for the filter
     * @return a new movie containing all elements that match the given predicate
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayMovie<T> filterAll(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate cannot be null.");
        }
        ArrayMovie<T> ret = emptyMovie(size >> 4);
        T element;
        for (int i = size - 1; i >= 0; i--) {
            element = (T) elementData[i];
            if (predicate.test(element)) {
                ret.add(element);
            }
        }
        return ret;
    }
}
