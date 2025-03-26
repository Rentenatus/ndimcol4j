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

/**
 *
 * @author jRent
 * @param <T>
 */
public class ArrayTape<T> implements ArrayMovie<T> {

    public static final int DEFAULT_CAPACITY = 32;
    public static final int DEFAULT_PAGE = 512;
    public static final int DEFAULT_COUNTDOWN = DEFAULT_PAGE << 2;

    public static <T> boolean equals(T a, T b) {
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
    private int trimCounDown;
    private IterTapeWalker<T> softWalker;

    /**
     * Constructs an empty ArrayTape with an initial capacity of ten and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     */
    public ArrayTape() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.page = DEFAULT_PAGE; // Standardwert für das Attribut 'page'
        this.updateCounter = 0;
        this.trimCounDown = DEFAULT_COUNTDOWN;
    }

    /**
     * Constructs an empty ArrayTape with the specified initial capacity and a default page size of thirty. The update
     * counter and trim countdown are also initialized.
     *
     * @param initialCapacity the initial capacity of the ArrayTape
     */
    public ArrayTape(int initialCapacity) {
        this.elementData = new Object[initialCapacity];
        this.size = 0;
        this.page = DEFAULT_PAGE; // Standardwert für das Attribut 'page'
        this.updateCounter = 0;
        this.trimCounDown = DEFAULT_COUNTDOWN;
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
        this.trimCounDown = original.trimCounDown;
    }

    /**
     * Constructs a new ArrayTape from the specified List. This constructor copies the elements from the given List and
     * initializes the page size, update counter, and trim countdown.
     *
     * @param list the List from which the ArrayTape is created
     */
    public ArrayTape(List<T> list) {
        this.elementData = new Object[list.size() + DEFAULT_PAGE]; // Initialisieren mit list size + default page
        for (int i = 0; i < list.size(); i++) {
            this.elementData[i] = list.get(i);
        }
        this.size = list.size();
        this.page = DEFAULT_PAGE; // Default value for the 'page' attribute
        this.updateCounter = 0;
        this.trimCounDown = DEFAULT_COUNTDOWN; // Initialize trim countdown
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
        this.trimCounDown = DEFAULT_COUNTDOWN; // Initialize trim countdown
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
        // ensureCapacity():fast:
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
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    @Override
    public boolean add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size >= elementData.length) {
            int newCapacity = elementData.length + page + (size >> 2);
            Object[] newArray = new Object[newCapacity];
            // Kopiere Elemente bis zum Index
            System.arraycopy(elementData, 0, newArray, 0, index);
            // Kopiere verbleibende Elemente nach dem Index
            System.arraycopy(elementData, index, newArray, index + 1, size - index);
            elementData = newArray;
        } else {
            // Nur Elemente nach dem Index verschieben
            System.arraycopy(elementData, index, elementData, index + 1, size - index);
        }
        elementData[index] = element;
        size++;
        updateCounter++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        if (col.isEmpty()) {
            return false;
        }

        if (col instanceof ArrayTape) {
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
     * Ensures that the ArrayTape has enough capacity to accommodate the specified minimum capacity. If the current
     * capacity is insufficient, it increases the capacity by the value of the 'page' attribute.
     *
     * @param minCapacity the minimum capacity that the ArrayTape must accommodate
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    public void ensureCapacity(int minCapacity) {
        if (elementData.length < minCapacity) {
            int newCapacity = minCapacity + page; // Nutzung des Attributs 'page'
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
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elementData[index];
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Tape is empty.");
        }
        return (T) elementData[0];
    }

    @Override
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
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
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
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public T removeAt(int index) {
        if (trimCounDown > 0) {
            return removeFast(index);
        }
        this.trimCounDown = page << 2;
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
        trimCounDown -= removed;
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
        if (trimCounDown < 0) {
            trimCapacity();
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> col) {
        if (col == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        boolean modified = false;
        IterTapeWalker<T> walker = softWalker();
        while (walker.hasNext()) {
            T element = walker.next();
            if (!col.contains(element)) {
                walker.removePrev();
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
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    protected T removeFast(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        @SuppressWarnings("unchecked")
        T ret = (T) elementData[index];
        int numElementsToMove = size - index - 1;
        if (numElementsToMove > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numElementsToMove);
        }
        elementData[--size] = null; // clear to let GC do its work
        updateCounter++;
        trimCounDown--;
        return ret;
    }

    /**
     * Removes the element at the specified position in the ArrayTape and resizes the internal array to size + page.
     * Copies the left and right sides (if present) into the new array and updates the size and update counter.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the ArrayTape
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T removeTrim(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        @SuppressWarnings("unchecked")
        T ret = (T) elementData[index];

        // Neues Array mit der Größe size + page
        Object[] newElementData = new Object[size + page - 1];

        // Elemente von der linken Seite (falls vorhanden) kopieren
        if (index > 0) {
            System.arraycopy(elementData, 0, newElementData, 0, index);
        }

        // Elemente von der rechten Seite (falls vorhanden) kopieren
        if (index < size - 1) {
            System.arraycopy(elementData, index + 1, newElementData, index, size - index - 1);
        }

        // Das alte Array aktualisieren
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
     * Returns true if this ArrayTape contains all of the elements in the specified collection.
     *
     * @param col the collection to be checked for containment in this ArrayTape
     * @return true if this ArrayTape contains all of the elements in the specified collection
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
     *
     * The method creates a new array with a correct capacity, copies the elements from the current array into the new
     * array, and then assigns the new array back to the elementData attribute.
     *
     * @throws OutOfMemoryError if there is not enough memory to create a new array with the increased capacity
     */
    protected void trimCapacity() {
        int newCapacity = elementData.length + page; // Nutzung des Attributs 'page'
        elementData = Arrays.copyOf(elementData, newCapacity);
        this.trimCounDown = DEFAULT_COUNTDOWN;
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
    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elementData, size, elementData.getClass());
    }

    /**
     * Returns an array containing all the elements in the ArrayTape.The runtime type of the returned array is that of
     * the specified array. If the ArrayTape fits in the specified array, it is returned therein. Otherwise, a new array
     * is allocated with the runtime type of the specified array and the size of the ArrayTape.
     *
     * @param <U>
     * @param a the array into which the elements of the ArrayTape are to be stored, if it is big enough; otherwise, a
     * new array of the same runtime type is allocated for this purpose.
     * @return an array containing all the elements in the ArrayTape
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of
     * every element in this ArrayTape
     */
    @SuppressWarnings("unchecked")
    @Override
    public <U> U[] toArray(U[] a) {
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (U[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null; // Null-terminate if a is larger than size
        }
        return a;
    }

    /**
     * Returns a List containing all the elements in the ArrayTape. The elements are in the same order as in the
     * ArrayTape.
     *
     * @return a List containing all the elements in the ArrayTape
     */
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
    public Iterator<T> iterator() {
        return new Iterator<T>() {
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

    @Override
    public void splitOrGlue() {
        // NoOp
    }

    @Override
    public ArrayTape<T> splitInHalf() {
        if (size <= 8) {
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

    public void copyToArray(Object[] array, int offset) {
        if (array == null) {
            throw new NullPointerException("Target array cannot be null");
        }
        if (offset < 0 || offset + size > array.length) {
            throw new IndexOutOfBoundsException("Offset out of bounds: " + offset);
        }

        System.arraycopy(elementData, 0, array, offset, size);
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
    public int debbug(PrintStream out, String prefix, int offset) {
        for (int i = 0; i < size(); i++) {
            final int index = offset + i;
            out.println(prefix + "t[" + i + "] =[" + index + "]= '" + elementData[i] + "'");
        }
        return offset + size();
    }

}
