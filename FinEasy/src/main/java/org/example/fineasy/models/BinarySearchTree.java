package org.example.fineasy.models;

import org.example.fineasy.service.BSTInterface;
import java.util.List;
import java.util.ArrayList;

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
     *
     * @param data The element to be inserted.
     */
    @Override
    public void insert(T data) {
        root = insertRec(root, data);
    }

    /**
     * Recursively inserts a new node into the binary search tree.
     *
     * @param root The current root of the subtree.
     * @param data The data to insert into the BST.
     * @return The new or updated root of the subtree.
     */
    private BSTNode<T> insertRec(BSTNode<T> root, T data) {
        if (root == null) {
            return new BSTNode<>(data);
        }

        int rootId = root.data.getId();
        int dataId = data.getId();

        if (dataId < rootId) {
            root.left = insertRec(root.left, data);
        } else if (dataId > rootId) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    /**
     * Deletes a specific element from the binary search tree.
     *
     * @param id The ID of the element to be deleted.
     * @return True if the element is successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        boolean[] isDeleted = new boolean[1];
        root = deleteRec(root, id, isDeleted);
        return isDeleted[0];
    }

    /**
     * Recursively deletes a node with a specified ID from the binary search tree.
     * This method works by finding the node to delete and then adjusting the tree
     * to maintain the binary search tree properties after removal.
     *
     * @param root      The current root of the subtree in which to perform the deletion.
     * @param id        The ID of the element to be deleted.
     * @param isDeleted An array to capture whether the deletion was successful.
     *                  This is particularly useful for signaling when the node has been found and deleted.
     * @return The new or updated root of the subtree after the deletion. If the node to delete is not found,
     *         the method returns the unchanged subtree root.
     */
    private BSTNode<T> deleteRec(BSTNode<T> root, int id, boolean[] isDeleted) {
        if (root == null) {
            return null; // Return null if the root is empty, indicating no node to delete.
        }
        int rootId = root.data.getId();

        // Search for the node to delete
        if (id < rootId) {
            root.left = deleteRec(root.left, id, isDeleted); // Recurse into the left subtree
        } else if (id > rootId) {
            root.right = deleteRec(root.right, id, isDeleted); // Recurse into the right subtree
        } else {
            // Node found, set deletion flag
            isDeleted[0] = true;

            // Handle the case where the node has two children
            if (root.left != null && root.right != null) {
                // Find the smallest node in the right subtree, which is the leftmost node
                T minData = minValue(root.right);
                // Replace the current node's data with the minimum node's data
                root.data = minData;
                // Delete the node that had its data copied
                root.right = deleteRec(root.right, minData.getId(), new boolean[1]);
            } else {
                // Handle the case where the node has one or no children
                root = (root.left != null) ? root.left : root.right;
            }
        }
        return root; // Return the possibly updated root of the subtree
    }

    /**
     * Finds the node with the minimum value in the subtree, which is always the leftmost node.
     * This method is typically used during the deletion of a node with two children to find a successor value.
     *
     * @param node The root of the subtree from which to find the minimum value.
     * @return The minimum value found in the subtree, specifically used when replacing a deleted node's value
     *         with the minimum value from its right subtree.
     */
    private T minValue(BSTNode<T> node) {
        T minv = node.data;
        while (node.left != null) {
            node = node.left;
            minv = node.data;
        }
        return minv;
    }

    /**
     * Searches for an element in the binary search tree by its ID.
     *
     * @param id The ID of the element to search for.
     * @return The found element, or null if not found.
     */
    @Override
    public T search(int id) {
        return searchRec(root, id);
    }

    /**
     * Recursively searches for an element in the binary search tree by its ID.
     *
     * @param root The current root of the subtree.
     * @param id   The ID of the element to search for.
     * @return The found element, or null if not found.
     */
    private T searchRec(BSTNode<T> root, int id) {
        if (root == null) return null;

        int rootId = root.data.getId();
        if (id == rootId) {
            return root.data;
        } else if (id < rootId) {
            return searchRec(root.left, id);
        } else {
            return searchRec(root.right, id);
        }
    }

    /**
     * Searches for elements in the binary search tree containing a specific keyword in their attributes.
     *
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
     *
     * @param root    The current root of the subtree.
     * @param keyword The keyword to search for.
     * @param result  The list to store found elements.
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

