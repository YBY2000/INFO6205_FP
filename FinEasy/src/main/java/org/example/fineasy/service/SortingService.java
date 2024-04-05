package org.example.fineasy.service;

import javafx.collections.ObservableList;
import org.example.fineasy.models.Transaction;

import java.util.Comparator;
import java.util.List;

// TODO: sorting method interface
public interface SortingService<T extends Comparable<? super T>> {
    /**
     * Sorts a given list based on a specified criterion.
     * @param list The list to be sorted.
     * @param comparator The criterion on which the sort is to be based.
     */

    void sort(List<T> list, Comparator<T> comparator);
}

