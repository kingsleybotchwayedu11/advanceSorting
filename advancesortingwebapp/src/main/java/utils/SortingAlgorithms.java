package utils;
import java.util.ArrayList;
import java.util.List;

public class SortingAlgorithms {
    

    public static List<Integer> heapSort(List<Integer> list) {
        int n = list.size();

        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
          //  heapify(list, n, i);
        }

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap the root (largest element) with the last element
            int temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);

            // Call heapify on the reduced heap
           // heapify(list, i, 0);
        }

        return list;  // The list is now sorted
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        // Merge the two lists while there are elements in both
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // If there are remaining elements in the left list
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        // If there are remaining elements in the right list
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }



}
