package org.example.fineasy.models;

/**
 * TODO: re-create a BinarySearchTree according to the project aim
 * TODO: there need a interface called BSTInterface<T> in service package
 * TODO: the BinarySearchTree<T> need to implement the BSTInterface<T>
 * TODO: should write generic for both interface<T> and implementation of BinarySearchTree<T>
 * TODO: write proper javadoc for BinarySearchTree<T>
 */
class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(Transaction data) {
        root = insertRec(root, data);
    }

    private BSTNode insertRec(BSTNode root, Transaction data) {
        if (root == null) {
            root = new BSTNode(data);
            return root;
        }

        if (data.getId().compareTo(root.data.getId()) < 0)
            root.left = insertRec(root.left, data);
        else if (data.getId().compareTo(root.data.getId()) > 0)
            root.right = insertRec(root.right, data);

        return root;
    }

    // 按ID搜索
    public Transaction search(String id) {
        return searchRec(root, id);
    }

    private Transaction searchRec(BSTNode root, String id) {
        if (root == null || root.data.getId().equals(id))
            return (root != null) ? root.data : null;

        if (id.compareTo(root.data.getId()) < 0)
            return searchRec(root.left, id);

        return searchRec(root.right, id);
    }

    // 在BinarySearchTree.java中添加
    public void delete(Transaction data) {
        root = deleteRec(root, data.getId());
    }

    private BSTNode deleteRec(BSTNode root, String id) {
        if (root == null) return root;

        if (id.compareTo(root.data.getId()) < 0)
            root.left = deleteRec(root.left, id);
        else if (id.compareTo(root.data.getId()) > 0)
            root.right = deleteRec(root.right, id);
        else {
            // 节点只有一个子节点或无子节点
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // 有两个子节点的节点
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getId());
        }

        return root;
    }

    private Transaction minValue(BSTNode root) {
        Transaction minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

}

