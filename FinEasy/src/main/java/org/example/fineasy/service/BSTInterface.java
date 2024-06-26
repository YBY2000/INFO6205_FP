package org.example.fineasy.service;

/**
 * Interface to define the operations for a binary search tree.
 * @param <T> The type of elements held in the binary search tree.
 */
public interface BSTInterface<T> {

    /**
     * Inserts a new element into the binary search tree.
     * @param data The element to be inserted.
     */
    void insert(T data);

    /**
     * Deletes an element from the binary search tree.
     * @param id The ID of the element to be deleted.
     * @return {@code true} if the element was successfully deleted, {@code false} otherwise.
     */
    boolean delete(int id);

    /**
     * Searches for an element in the binary search tree.
     * @param id The identifier of the element to search for.
     * @return The found element, or {@code null} if not found.
     */
    T search(int id);
}
