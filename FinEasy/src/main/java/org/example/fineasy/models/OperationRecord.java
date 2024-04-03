package org.example.fineasy.models;

import org.example.fineasy.entity.OperationType;

public class OperationRecord {

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

