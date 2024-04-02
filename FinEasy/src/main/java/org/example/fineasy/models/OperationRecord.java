package org.example.fineasy.models;

public class OperationRecord {
    public enum OperationType {
        ADD, DELETE, EDIT
    }

    private final OperationType operationType;
    private final Transaction transaction;

    public OperationRecord(OperationType operationType, Transaction transaction) {
        this.operationType = operationType;
        this.transaction = transaction;
    }

    // Getters
    public OperationType getOperationType() {
        return operationType;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}

