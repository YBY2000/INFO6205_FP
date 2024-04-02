package org.example.fineasy.models;

public class OperationRecord {

    private int operationType;
    private Transaction transaction;

    OperationRecord(int operation_type, Transaction transaction){
        this.operationType = operation_type;
        this.transaction = transaction;
    }
    public int getOperationType() {
        return operationType;
    }


    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


}
