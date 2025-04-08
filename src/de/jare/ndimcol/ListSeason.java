package de.jare.ndimcol;


import java.util.*;

public class ListSeason <T> extends ArraySeason<T> implements List<T> {

    public ListSeason() {
        super();
    }

    public ListSeason(final Screenplay screenplay) {
        super( screenplay);
    }

    public ListSeason(ArrayTape<T> original) {
        super(original);
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their indices).
     *  @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, T element) {
        super.add(index, element);
    }

    /**
     * Sets the element at the specified position. Replaces an old element at the
     * specified position in this list with the specified element.
     *
     * @param element element to be appended to this list
     * @return the old element at the specified position
     */
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size+".");
        }
        IteratorWalker<T> walker = getLeafWalkerAtIndex(index);
       return walker.set(element);
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts
     * one from their indices). Returns the element that was removed from the list.
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt 0 || index &gt= size)
     */
    @Override
    public T remove(int index) {
        return super.removeAt(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     * @return a list iterator over the elements in this list.
     */
    @Override
    public ListIterator<T> listIterator() {
        return Collections.unmodifiableList(subList(0,size)).listIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence),
     * starting at the specified position in the list.
     * The specified index indicates the first element that would be returned by an initial call to next.
     * It must be a valid index (from 0 to size(), inclusive), and it is an error to pass an index outside the range.
     * @param index index of the first element to be returned from the list iterator (by a call to next)
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return Collections.unmodifiableList(subList(0,size)).listIterator(index);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a new ListSeason containing the elements in the specified range
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return (ListSeason<T>) subMovie(fromIndex, toIndex);
    }

    /**
     * Creates a new empty list with the same screenplay. The new movie is not a copy of this
     * list.
     * @param initialCapacityOrZero not used
     * @return a new empty list with the same screenplay
     */
    @Override
    public ListSeason<T> emptyMovie(int initialCapacityOrZero) {
        return new ListSeason<>(screenplay);
    }

}
