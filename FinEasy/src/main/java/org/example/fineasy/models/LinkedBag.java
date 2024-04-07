package org.example.fineasy.models;

import org.example.fineasy.service.BagInterface;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 A class of bags whose entries are stored in a chain of linked nodes.
 The bag is never full.
 */
public final class LinkedBag<T> implements BagInterface<T>, Iterable<T>
{
    private Node firstNode;       // Reference to first node
    private int numberOfEntries;

    private class Node
    {
        private T    data; // Entry in bag
        private Node next; // Link to next node


        private Node(T dataPortion)
        {
            this(dataPortion, null);
        } // end constructor


        private Node(T dataPortion, Node nextNode)
        {
            data = dataPortion;
            next = nextNode;
        } // end constructor


        private T getData()
        {
            return data;
        } // end getData


        private void setData(T newData)
        {
            data = newData;
        } // end setData
    } // end Node


    public LinkedBag()
    {
        firstNode = null;
        numberOfEntries = 0;
    } // end default constructor


    /** Adds a new entry to this bag.
     @param newEntry  The object to be added as a new entry.
     @return  True. */
    public boolean add(T newEntry) // OutOfMemoryError possible
    {
        // Add to beginning of chain:
        Node newNode = new Node(newEntry);
        newNode.next = firstNode;  // Make new node reference rest of chain

        // (firstNode is null if chain is empty)
        firstNode = newNode;       // New node is at beginning of chain
        numberOfEntries++;

        return true;
    } // end add

    /** Retrieves all entries that are in this bag.
     @return  A newly allocated array of all the entries in this bag. */
    public T[] toArray()
    {
        // The cast is safe because the new array contains null entries.
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast

        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null))
        {
            result[index] = currentNode.data;
            index++;
            currentNode = currentNode.next;
        } // end while

        return result;
        // Note: The body of this method could consist of one return statement,
        // if you call Arrays.copyOf
    } // end toArray


    /** Sees whether this bag is empty.
     @return  True if the bag is empty, or false if not. */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    } // end isEmpty


    /** Gets the number of entries currently in this bag.
     @return  The integer number of entries currently in the bag. */
    public int getCurrentSize()
    {
        return numberOfEntries;
    } // end getCurrentSize


    /** Removes one unspecified entry from this bag, if possible.
     @return  Either the removed entry, if the removal
     was successful, or null. */
    public T remove() {
        T result = null;

        // MODIFY THIS METHOD TO REMOVE A RANDOM ITEM FROM THE BAG

        if (firstNode != null) {
            result = firstNode.data;
            firstNode = firstNode.next; // Remove first node from chain
            numberOfEntries--;
        } // end if

        return result;
    } // end remove


    /** Removes one occurrence of a given entry from this bag.
     @param anEntry  The entry to be removed.
     @return  True if the removal was successful, or false otherwise. */
    public T remove(T anEntry) {
        T result = null;
        Node nodeN = getReferenceTo(anEntry);
        if (nodeN != null) {
            result = nodeN.data;
            nodeN.data = firstNode.data; // Replace located entry with entry in first node
            firstNode = firstNode.next;  // Remove first node
            numberOfEntries--;
        } // end if

        return result;
    } // end remove


    /**
     * Locates a given entry within this bag.
     *
     * @param anEntry The entry to be searched
     * @return a reference to the node containing the entry, if located, or null otherwise.
     */
    private Node getReferenceTo(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        } // end while
        return currentNode;
    } // end getReferenceTo


    /** Removes all entries from this bag. */
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    } // end clear


    /** Counts the number of times a given entry appears in this bag.
     @param anEntry  The entry to be counted.
     @return  The number of times anEntry appears in the bag. */
    public int getFrequencyOf(T anEntry) {
        int frequency = 0;
        int loopCounter = 0;
        Node currentNode = firstNode;
        while ((loopCounter < numberOfEntries) && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                frequency++;
            }
            loopCounter++;
            currentNode = currentNode.next;
        } // end while
        return frequency;
    } // end getFrequencyOf


    /** Tests whether this bag contains a given entry.
     @param anEntry  The entry to locate.
     @return  True if the bag contains anEntry, or false otherwise. */
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                found = true;
            else
                currentNode = currentNode.next;
        } // end while
        return found;
    } // end contains


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = firstNode;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T item = current.data;
                current = current.next;
                return item;
            }
        };
    }

    /**
     * Public sort method that sorts the LinkedBag using the specified comparator.
     * @param comparator The comparator to determine the order of the bag elements. Can be null if T implements Comparable.
     */
    public void sort(Comparator<T> comparator) {
        firstNode = mergeSort(firstNode, comparator);
    }

    /**
     * Private recursive method to perform merge sort on the linked list.
     * @param head The head of the linked list to sort.
     * @param comparator The comparator to determine the order of the elements.
     * @return The new head of the sorted linked list.
     */
    private Node mergeSort(Node head, Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return head;
        }

        // Split list into halves
        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;

        // Recursive sort for each half
        Node left = mergeSort(head, comparator);
        Node right = mergeSort(nextOfMiddle, comparator);

        // Merge the sorted halves
        return sortedMerge(left, right, comparator);
    }

    /**
     * Finds the middle node of the linked list.
     * @param head The head of the linked list.
     * @return The middle node.
     */
    private Node getMiddle(Node head) {
        if (head == null) {
            return head;
        }

        Node slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Merges two sorted linked lists into one.
     * @param a The head of the first sorted list.
     * @param b The head of the second sorted list.
     * @param comparator The comparator to determine the order of the elements.
     * @return The head of the merged sorted list.
     */
    private Node sortedMerge(Node a, Node b, Comparator<T> comparator) {
        Node result;
        if (a == null) return b;
        if (b == null) return a;

        // Choose either a or b, and recur
        if (comparator == null) {
            // Assume T implements Comparable
            if (((Comparable<T>)a.data).compareTo(b.data) <= 0) {
                result = a;
                result.next = sortedMerge(a.next, b, comparator);
            } else {
                result = b;
                result.next = sortedMerge(a, b.next, comparator);
            }
        } else {
            if (comparator.compare(a.data, b.data) <= 0) {
                result = a;
                result.next = sortedMerge(a.next, b, comparator);
            } else {
                result = b;
                result.next = sortedMerge(a, b.next, comparator);
            }
        }
        return result;
    }
} // end LinkedBag

