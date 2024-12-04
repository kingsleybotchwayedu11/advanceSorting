package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SortingAlgorithms {

    public static List<Integer> mergeSort(List<Integer> list) {
        // Base case: a list with 0 or 1 elements is already sorted
        if (list.size() <= 1) {
            return new ArrayList<>(list); // Return a copy of the original list
        }

        // Split the list into two halves
        int mid = list.size() / 2;
        List<Integer> left = list.subList(0, mid);
        List<Integer> right = list.subList(mid, list.size());

        // Recursively sort both halves
        List<Integer> sortedLeft = mergeSort(left);
        List<Integer> sortedRight = mergeSort(right);

        // Merge the two sorted halves
        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;

        // Merge elements from both lists in sorted order
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements from the left list
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        // Add remaining elements from the right list
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }



    //heap sort
    private static void heapify(List<Integer> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && list.get(left) > list.get(largest)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && list.get(right) > list.get(largest)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            // Swap list[i] and list[largest]
            int temp = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, temp);

            // Recursively heapify the affected sub-tree
            heapify(list, n, largest);
        }
    }

    // Method to perform heap sort
    public static List<Integer> heapSort(List<Integer> originalList) {
        // Copy the original list to avoid modifying it
        List<Integer> list = new ArrayList<>(originalList);
        int n = list.size();

        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        // One by one extract elements from the heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to the end
            int temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);

            // Call heapify on the reduced heap
            heapify(list, i, 0);
        }

        // Return the sorted list
        return list;
    }

    //quick sort
    private static int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high); // pivot element
        int i = low - 1; // Index of smaller element

        // Rearranging the list so that elements smaller than pivot are on the left
        // and greater elements are on the right
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                // Swap list[i] and list[j]
                int temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Swap the pivot element with the element at i+1
        int temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1; // Return the pivot index
    }

    // Helper method to apply QuickSort recursively
    private static void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            // Partition the list and get the pivot index
            int pivotIndex = partition(list, low, high);

            // Recursively sort the sub-lists
            quickSort(list, low, pivotIndex - 1); // Left sublist
            quickSort(list, pivotIndex + 1, high); // Right sublist
        }
    }

    // Main method to return a sorted list using QuickSort
    public static List<Integer> quickSort(List<Integer> originalList) {
        // Copy the original list to avoid modifying it
        List<Integer> list = new ArrayList<>(originalList);

        // Apply QuickSort on the copied list
        quickSort(list, 0, list.size() - 1);

        // Return the sorted list
        return list;
    }

    private static void countingSort(List<Integer> list, int place) {
        int n = list.size();
        int[] output = new int[n];
        int[] count = new int[10];  // As digits range from 0 to 9

        // Calculate the count of digits (occurrences) based on current place
        for (int i = 0; i < n; i++) {
            count[(list.get(i) / place) % 10]++;
        }

        // Update the count[] so that it now contains actual positions of digits
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (list.get(i) / place) % 10;
            output[count[digit] - 1] = list.get(i);
            count[digit]--;
        }

        // Copy the output array back to list[]
        for (int i = 0; i < n; i++) {
            list.set(i, output[i]);
        }
    }

    // Main radix sort function
    public static List<Integer> radixSort(List<Integer> originalList) {
        // Copy the original list to avoid modifying it
        List<Integer> list = new ArrayList<>(originalList);

        // Find the maximum number to determine the number of digits
        int max = list.get(0);
        for (int num : list) {
            if (num > max) {
                max = num;
            }
        }

        // Perform counting sort for every digit (from LSD to MSD)
        // The place (1, 10, 100, etc.) will determine which digit we're sorting by
        for (int place = 1; max / place > 0; place *= 10) {
            countingSort(list, place);
        }

        // Return the sorted list
        return list;
    }

    // Helper method to perform Insertion Sort on individual buckets
    private static void insertionSort(List<Integer> bucket) {
        int n = bucket.size();
        for (int i = 1; i < n; i++) {
            int key = bucket.get(i);
            int j = i - 1;

            // Move elements of bucket[0..i-1] that are greater than key to one position ahead
            while (j >= 0 && bucket.get(j) > key) {
                bucket.set(j + 1, bucket.get(j));
                j = j - 1;
            }
            bucket.set(j + 1, key);
        }
    }

    // Main Bucket Sort function
    public static List<Integer> bucketSort(List<Integer> originalList) {
        // Copy the original list to avoid modifying it
        List<Integer> list = new ArrayList<>(originalList);

        if (list.isEmpty()) {
            return list; // Return the list as it is if empty
        }

        // Find the maximum and minimum values in the list
        int minValue = Collections.min(list);
        int maxValue = Collections.max(list);

        // Calculate the number of buckets based on the range of values
        int bucketCount = (maxValue - minValue) / list.size() + 1;

        // Create buckets and distribute the numbers into appropriate buckets
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute the elements into the appropriate bucket
        for (int num : list) {
            int bucketIndex = (num - minValue) * (bucketCount - 1) / (maxValue - minValue);
            buckets.get(bucketIndex).add(num);
        }

        // Sort each bucket and concatenate the result
        List<Integer> sortedList = new ArrayList<>();
        for (List<Integer> bucket : buckets) {
            insertionSort(bucket);
            sortedList.addAll(bucket);
        }

        // Return the sorted list
        return sortedList;
    }


    public static Function<List<Integer>, List<Integer>> GetFunciton(String name) {
       if(name.equals("mergesort"))
            return SortingAlgorithms::mergeSort;
        else if(name.equals("heapSort"))
            return SortingAlgorithms::heapSort;
        else  if(name.equals("quicksort"))
            return SortingAlgorithms::quickSort;
        else if(name.equals("radixsort"))
            return SortingAlgorithms::radixSort;
        else if(name.equals("bucketsort"))
            return SortingAlgorithms::bucketSort;
        else
            return null;
    }

}
