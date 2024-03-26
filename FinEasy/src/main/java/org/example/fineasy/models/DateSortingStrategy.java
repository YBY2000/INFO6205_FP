package org.example.fineasy.models;

import java.util.Comparator;
import java.util.List;

public class DateSortingStrategy implements SortingStrategy{
    @Override
    public void sort(List<Transaction> transactions) {
        transactions.sort(Comparator.comparing(Transaction::getDate));
    }
}
