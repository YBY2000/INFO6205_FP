package org.example.fineasy.models;

/**
 * Represents a node in a binary search tree.
 * This class is designed to hold data of type T, where T extends Transaction.
 *
 * @param <T> The type of the data stored in the node, specified to be a subclass of Transaction.
 */
public class BSTNode<T extends Transaction> {
    T data;
    BSTNode<T> left, right;

    /**
     * Constructs a BSTNode with the specified data.
     *
     * @param data The transaction data this node will hold.
     */
    public BSTNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

}

