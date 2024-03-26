package org.example.fineasy.models;

import java.util.List;

public interface SortingStrategy {
    void sort(List<Transaction> transactions);
}

