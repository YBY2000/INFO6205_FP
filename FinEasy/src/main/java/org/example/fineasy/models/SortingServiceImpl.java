package org.example.fineasy.models;

import org.example.fineasy.service.SortingService;
import java.util.Comparator;
import java.util.List;

public class SortingServiceImpl<T extends Comparable<? super T>> implements SortingService<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        if (comparator != null) {
            quickSort(list, 0, list.size() - 1, comparator);
        }
    }

    //QuickSort
    private void quickSort(List<T> list, int begin, int end, Comparator<T> comparator) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end, comparator);
            quickSort(list, begin, partitionIndex - 1, comparator);
            quickSort(list, partitionIndex + 1, end, comparator);
        }
    }

    private int partition(List<T> list, int begin, int end, Comparator<T> comparator) {
        T pivot = list.get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;

                T swapTemp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, swapTemp);
            }
        }

        T swapTemp = list.get(i + 1);
        list.set(i + 1, list.get(end));
        list.set(end, swapTemp);

        return i + 1;
    }
}
