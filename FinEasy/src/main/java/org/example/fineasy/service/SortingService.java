package org.example.fineasy.service;

import javafx.collections.ObservableList;
import org.example.fineasy.models.Transaction;

// TODO: sorting method interface
public interface SortingService<T> {
    /** Sorts a given list based on a specified criterion.
     * @param bag The list to be sorted.
     * @param criterion The criterion on which the sort is to be based.
     */
    void sort(ObservableList<Transaction> bag, String criterion);

}

