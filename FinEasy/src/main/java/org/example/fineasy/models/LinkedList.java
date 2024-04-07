package org.example.fineasy.models;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a generic linked list implementation where nodes are linked together to form a sequence.
 * @param <T> The type of elements stored in the linked list.
 */
public class LinkedList<T> implements Iterable<T> {
    private Node firstNode;       // Reference to first node
    private int numberOfEntries;


    /**
     * Initializes the data fields of the linked list to their default values.
     */
    public void initializeDataField(){
        firstNode = null;
        numberOfEntries = 0;
    }

    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        initializeDataField();
    }


    /**
     * Adds a new entry to the end of the list.
     * @param newEntry The element to be added to the list.
     * @return true (as specified by {@link Collection#add}).
     */
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);
        if (firstNode == null) {
            firstNode = newNode;
        } else {
            Node lastNode = firstNode;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            lastNode.next = newNode;
        }
        numberOfEntries++;
        return true;
    }


    /**
     * Inserts the specified element at the specified position in this list.
     * @param newPosition The 1-based position to insert the new entry.
     * @param newEntry The element to be inserted.
     * @return true (as specified by {@link List#add(int, Object)}).
     * @throws IndexOutOfBoundsException if the newPosition is out of range (newPosition < 1 || newPosition > size() + 1)
     */
    public boolean add(int newPosition, T newEntry) {
        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            Node newNode = new Node(newEntry);
            if (newPosition == 1) {
                newNode.next = firstNode;
                firstNode = newNode;
            } else {
                Node nodeBefore = getNodeAt(newPosition - 1);
                newNode.next = nodeBefore.next;
                nodeBefore.next = newNode;
            }
            numberOfEntries++;
            return true;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
        }
    }


    /**
     * Removes and returns the last element from this list.
     * @return The element that was removed from the list.
     */
    public T remove(){
        return remove(numberOfEntries-1);
    }

    /**
     * Removes the element at the specified position in this list.
     * @param givenPosition The 1-based index of the element to be removed.
     * @return The element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T remove(int givenPosition) {
        T result = null;
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            if (givenPosition == 1) {
                result = firstNode.data;
                firstNode = firstNode.next;
            } else {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                result = nodeBefore.next.data;
                nodeBefore.next = nodeBefore.next.next;
            }
            numberOfEntries--;
            return result;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
        }
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param givenPosition The 1-based index of the element to replace.
     * @param newEntry The element to be stored at the specified position.
     * @return The element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T replace(int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            Node desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.data;
            desiredNode.data = newEntry;
            return originalEntry;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
        }
    }


    /**
     * Returns the element at the specified position in this list.
     * @param givenPosition The 1-based index of the element to return.
     * @return The element at the specified position in this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 1 || index > size())
     */
    public T get(int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            return getNodeAt(givenPosition).data;
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to get operation.");
        }
    }


    /**
     * Returns a node at a given 1-based position.
     * @param givenPosition The position of the node.
     * @return The node at the specified position.
     */
    private Node getNodeAt(int givenPosition) {
        Node currentNode = firstNode;
        for (int counter = 1; counter < givenPosition; counter++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }


    /**
     * Removes all of the elements from this list. The list will be empty after this call returns.
     */
    public void clear() {
        initializeDataField();
    }


    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
     * @return An array containing all of the elements in this list in proper sequence.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        // The cast is safe because the new array contains null entries @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries];
        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null))
        {
            result[index] = currentNode.getData();
            currentNode = currentNode.getNextNode();
            index++;
        } // end while
        return result; } // end toArray

    /**
     * Returns the 1-based position of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     * @param entry The element to search for.
     * @return The 1-based position of the first occurrence of the specified element in this list, or -1 if not found.
     */
    public int getPosition(T entry) {
        Node currentNode = firstNode;
        int position = 1; // Positions are 1-based in this method
        while (currentNode != null) {
            if (currentNode.data.equals(entry)) {
                return position;
            }
            position++;
            currentNode = currentNode.next;
        }
        return -1; // Return -1 if the entry is not found
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return An iterator over the elements in this list in proper sequence.
     */
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

    private class Node {
        private T data; // Entry in bag
        private Node next; // Link to next node

        /**
         * Default constructs a new node with specified data part
         * @param dataPortion The data to store in this node.
         */
        private Node(T dataPortion) {
            this(dataPortion, null);
        }

        /**
         * Constructs a new node with specified data part and next node reference.
         * @param dataPortion The data to store in this node.
         * @param nextNode The next node in the linked list.
         */
        private Node(T dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNextNode() {
            return next;
        }

        public void setNextNode(Node next) {
            this.next = next;
        }

    }
}
