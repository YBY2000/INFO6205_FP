package org.example.fineasy.models;

class BSTNode {
    Transaction data;
    BSTNode left, right;

    public BSTNode(Transaction data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

