package org.example.fineasy.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.fineasy.models.Transaction;
import org.example.fineasy.service.SortingService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingServiceImpl implements SortingService<Transaction> {

    @Override
    public void sort(List<Transaction> list, String criterion) {
        //method 1
        Comparator<Transaction> comparator = getComparator(criterion);
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
        //method 2
//        quickSort(list, 0, list.size() - 1, getComparator(criterion));
    }

    //method 1
    private Comparator<Transaction> getComparator(String criterion) {
        switch (criterion) {
            case "Amount":
                return Comparator.comparing(Transaction::getAmount);
            case "Date":
                return Comparator.comparing(Transaction::getDate);
            case "Type":
                return Comparator.comparing(Transaction::getType);
            case "Category":
                return Comparator.comparing(Transaction::getCategory);
            default:
                return null;
        }
    }
    //method 2
    private void quickSort(List<Transaction> list, int begin, int end, Comparator<Transaction> comparator) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end, comparator);

            quickSort(list, begin, partitionIndex-1, comparator);
            quickSort(list, partitionIndex+1, end, comparator);
        }
    }

    private int partition(List<Transaction> list, int begin, int end, Comparator<Transaction> comparator) {
        Transaction pivot = list.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;

                Transaction swapTemp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, swapTemp);
            }
        }

        Transaction swapTemp = list.get(i+1);
        list.set(i+1, list.get(end));
        list.set(end, swapTemp);

        return i+1;
    }

    //method1

}

