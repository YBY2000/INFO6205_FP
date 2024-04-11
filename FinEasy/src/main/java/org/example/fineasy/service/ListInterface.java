package org.example.fineasy.service;

import java.util.Collection;
import java.util.List;

/**
 An interface that describes the operations of a bag of objects.
 */
public interface ListInterface<T>
{
    /**
     * Initializes the data fields of the linked list to their default values.
     */
    public void initializeDataField();


    /**
     * Adds a new entry to the end of the list.
     * @param newEntry The element to be added to the list.
     * @return true (as specified by {@link Collection#add}).
     */
    public boolean add(T newEntry);

    /**
     * Inserts the specified element at the specified position in this list.
     * @param newPosition The 1-based position to insert the new entry.
     * @param newEntry The element to be inserted.
     * @return true (as specified by {@link List#add(int, Object)}).
     * @throws IndexOutOfBoundsException if the newPosition is out of range (newPosition < 1 || newPosition > size() + 1)
     */
    public boolean add(int newPosition, T newEntry);
    /**
     * Removes the element at the specified position in this list.
     * @param givenPosition The 1-based index of the element to be removed.
     * @return The element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T remove(int givenPosition);

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param givenPosition The 1-based index of the element to replace.
     * @param newEntry The element to be stored at the specified position.
     * @return The element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T replace(int givenPosition, T newEntry);

    /**
     * Returns the element at the specified position in this list.
     * @param givenPosition The 1-based index of the element to return.
     * @return The element at the specified position in this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T get(int givenPosition);


    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the 1-based position of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     * @param entry The element to search for.
     * @return The 1-based position of the first occurrence of the specified element in this list, or -1 if not found.
     */
    public int getPosition(T entry);


    /**
     * Removes all of the elements from this list. The list will be empty after this call returns.
     */
    public void clear();

    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
     * @return An array containing all of the elements in this list in proper sequence.
     */
    public T[] toArray();
} // end ListInterface
