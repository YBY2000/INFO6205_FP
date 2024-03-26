package org.example.fineasy.models;

import java.util.List;

public interface TransactionList {
    void addTransaction(Transaction transaction);
    void deleteTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
}

