package org.example.fineasy.models;

import org.example.fineasy.service.BSTInterface;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO: re-create a BinarySearchTree that can implement a quick search for particular record using keyword!!!
 * TODO: there need a interface called BSTInterface<T> in service package
 * TODO: the BinarySearchTree<T> need to implement the BSTInterface<T>
 * TODO: should write generic for both interface<T> and implementation of BinarySearchTree<T>
 * TODO: write proper javadoc for BinarySearchTree<T>
 */

/**
 * A generic Binary Search Tree (BST) implementation that supports operations
 * such as insert, delete, and search for managing transaction records.
 * This class is designed to work with any type of Transaction or its subclasses,
 * enabling efficient data retrieval and manipulation based on transaction attributes.
 *
 * @param <T> The type of elements managed by the BST, extending Transaction.
 */
public class BinarySearchTree<T extends Transaction> implements BSTInterface<T> {
    private BSTNode<T> root;

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts a new element into the binary search tree.
     * @param data The element to be inserted.
     */
    @Override
    public void insert(T data) {
        root = insertRec(root, data);
    }

    /**
     * Recursively inserts a new node into the binary search tree.
     * @param root The current root of the subtree.
     * @param data The data to insert into the BST.
     * @return The new or updated root of the subtree.
     */
    private BSTNode<T> insertRec(BSTNode<T> root, T data) {
        if (root == null) {
            return new BSTNode<>(data);
        }

        int compareResult = data.compareTo(root.data);

        if (compareResult < 0) {
            root.left = insertRec(root.left, data);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    /**
     * Deletes a specific element from the binary search tree.
     * @param data The element to be deleted.
     */
    @Override
    public void delete(T data) {
        root = deleteRec(root, data.getId());
    }

    /**
     * Recursively deletes a node from the binary search tree.
     * @param root The current root of the subtree.
     * @param id The ID of the element to delete.
     * @return The new or updated root of the subtree.
     */
    private BSTNode<T> deleteRec(BSTNode<T> root, String id) {
        if (root == null) return null;

        if (id.compareTo(root.data.getId()) < 0) {
            root.left = deleteRec(root.left, id);
        } else if (id.compareTo(root.data.getId()) > 0) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getId());
        }
        return root;
    }

    /**
     * Finds the node with the minimum value in the subtree.
     * @param root The root of the subtree.
     * @return The minimum value node within the subtree.
     */
    private T minValue(BSTNode<T> root) {
        T minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    /**
     * Searches for an element in the binary search tree by its ID.
     * @param id The ID of the element to search for.
     * @return The found element, or null if not found.
     */
    @Override
    public T search(String id) {
        return searchRec(root, id);
    }

    /**
     * Recursively searches for an element in the binary search tree by its ID.
     * @param root The current root of the subtree.
     * @param id The ID of the element to search for.
     * @return The found element, or null if not found.
     */
    private T searchRec(BSTNode<T> root, String id) {
        if (root == null || root.data.getId().equals(id)) {
            return (root != null) ? root.data : null;
        }

        if (id.compareTo(root.data.getId()) < 0) {
            return searchRec(root.left, id);
        } else {
            return searchRec(root.right, id);
        }
    }

    /**
     * Searches for elements in the binary search tree containing a specific keyword in their attributes.
     * @param keyword The keyword to search for.
     * @return A list of elements containing the keyword.
     */
    public List<Transaction> searchByKeyword(String keyword) {
        List<Transaction> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        searchByKeywordRec(root, keyword.toLowerCase(), result);
        return result;
    }

    /**
     * Recursively searches for elements containing a specific keyword.
     * @param root The current root of the subtree.
     * @param keyword The keyword to search for.
     * @param result The list to store found elements.
     */
    private void searchByKeywordRec(BSTNode<T> root, String keyword, List<Transaction> result) {
        if (root == null) return;

        searchByKeywordRec(root.left, keyword, result);

        Transaction currentTransaction = root.data;
        boolean matches = (currentTransaction.getType() != null && currentTransaction.getType().toLowerCase().contains(keyword)) ||
                (currentTransaction.getCategory() != null && currentTransaction.getCategory().toLowerCase().contains(keyword)) ||
                (currentTransaction.getComment() != null && currentTransaction.getComment().toLowerCase().contains(keyword));

        if (matches) {
            result.add(currentTransaction);
        }

        searchByKeywordRec(root.right, keyword, result);
    }
}

