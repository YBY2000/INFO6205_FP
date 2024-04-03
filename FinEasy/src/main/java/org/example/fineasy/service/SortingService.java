package org.example.fineasy.service;

import javafx.collections.ObservableList;
import org.example.fineasy.models.LinkedBag;
import org.example.fineasy.models.Transaction;

import java.util.List;

// TODO: sorting method interface
public interface SortingService<T> {
    /** Sorts a given list based on a specified criterion.
     * @param list The list to be sorted.
     * @param criterion The criterion on which the sort is to be based.
     */
    void sort(List<T> list, String criterion);

}

