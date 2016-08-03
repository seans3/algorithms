package io.seansullivan.algorithms;

import java.lang.Comparable;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


/**
 * Implements several sorting algorithms as static methods for generic
 * lists, including:
 *
 * 1) Merge sort
 * 2) Quick sort
 * 3) Heap sort
 * 4) Shell sort
 * 5) Insertion sort
 * 6) Selection sort
 * 7) Bubble sort
 *
 * Sorts to implement in future:
 * 1) Iterative merge sort
 * 2) Radix sort (String, Integer)
 *
 * The generic type of the list must implement the Comparable interface.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class Sorts {

  /**
   * Sorts the passed list using O(n log n) merge sort. Modifies the
   * passed list. Sorts in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void mergeSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // Call merge sort on entire array.
    mergeSort(a, 0, a.length - 1);

    // Write the sorted array back into the list.
    arrayIntoList(a, list);
  }

  
  /**
   * Recursive method which calls itself on two halves of the
   * array "a". The base case is when "a" is a single element
   * array. After calling itself on the lower and upper halves
   * of the array, this method merges the two sorted sub-arrays.
   */
  private static void mergeSort(Object[] a, int low, int high) {
    assert(a != null);
    assert(high < a.length);

    // Base case: low == high means "a" is a one-element array.
    if (low < high) {
      // Calculate the index to split the array.
      int middle = ((int) ((high - low) / 2)) + low;
      // Recursive call to lower half of "a".
      mergeSort(a, low, middle);
      // Recursive call to upper half of "a".
      mergeSort(a, middle + 1, high);
      // Merge the sorted lower half sub-array with the upper half.
      merge(a, low, middle, high);
    }
  }


  /**
   * The indexes "low", "middle", and "high" are passed to denote two already
   * sorted sub-arrays within "a". This method merges these two sorted
   * sub-arrays by iteratively choosing the smallest element from each sub-array.
   *
   * NOTE: The "new" creation of two arrays could be optimized, by creating
   * a working array once and passing it into this method.
   */
  @SuppressWarnings("unchecked")
  private static void merge(Object[] a, int low, int middle, int high) {
    assert(low <= middle);
    assert(middle < high);
      
    // Copy two already-sorted sub-arrays from "a" into "lower" and "upper".
    // First (lower): array "a" from "low" to "middle".
    // Second (upper): array "a" from "middle + 1" to "high".
    // Invariant: arrays "lower" and "upper" are already sorted.
    //
    int lowerSize = middle - low + 1;
    Object[] lower = new Object[lowerSize];
    System.arraycopy(a, low, lower, 0, lowerSize);

    int upperSize = high - middle;
    Object[] upper = new Object[upperSize];
    System.arraycopy(a, middle + 1, upper, 0, upperSize);

    // Merge arrays "lower" and "upper". Choose the next smallest
    // value from either "lower" or "upper". Write the values back into "a"
    // from index "low" until index "high".
    //
    int lowerIdx = 0;
    int upperIdx = 0;
    for (int i = low; i <= high; i++) {
      // Need to check if we've exhausted members from "lower" or "upper".
      if ((upperIdx >= upper.length) ||
	  ((lowerIdx < lower.length) &&
	   ((Comparable) lower[lowerIdx]).compareTo(upper[upperIdx]) < 0)) {
	a[i] = lower[lowerIdx++];
      } else {
	a[i] = upper[upperIdx++];
      }
    }
  }

  /**
   * Sorts the passed list using O(n log n) quick sort (expected case), and
   * O(n^2) in the degenerate worst case. Modifies the passed list. Sorts
   * in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * NOTE: We can optimize this algorithm by coding a method to ensure
   * the pivot is close to the median value of the array each time.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void quickSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // Call quick sort on entire array.
    quickSort(a, 0, a.length - 1);

    // Write the sorted array back into the list.
    arrayIntoList(a, list);
  }

  /**
   * Recursive method which chooses a "pivot", then places all
   * all values less than the "pivot" at the left of the array
   * (not necessarily ordered). We finally place the "pivot"
   * in between the lesser values and the greater (or equal) values.
   * This places the "pivot" in it's final position. Finally, recurse
   * on the lesser values to the left of the "pivot", and the greater
   * or equal values to the right of the "pivot".
   *
   * NOTE: Choosing a "pivot" which is about the median value
   * would guarantee O(n log n) running time.
   */
  @SuppressWarnings("unchecked")
  private static void quickSort(Object[] a, int low, int high) {
    assert(a != null);
    assert(high < a.length);

    // Base case: low == high means a one element sized array.
    if (low < high) {

      int pivot = high;  // Choose pivot at the end of the array.
      int wall = low;

      // Move all items less than the pivot to the left of the
      // "wall", while all the items larger than the pivot remain
      // to the right of the "wall".
      //
      for (int current = low; current <= high; current++) {
	if (((Comparable) a[current]).compareTo(a[pivot]) < 0) {
	  swap(a, current, wall);
	  wall++;
	}
      }

      // Finally, place the pivot in between the lesser values
      // and the greater values at the "wall", which is it's
      // final position.
      //
      swap(a, pivot, wall);

      // Recurse on the values less than the pivot.
      quickSort(a, low, wall - 1);
      // Recurse on the values greater than the pivot.      
      quickSort(a, wall + 1, high);
    }
  }

  /**
   * Sorts the passed list using O(n log n) heap sort. The items are
   * sorted in ascending order. This algorithm uses a data structure
   * called a heap. A heap is a binary tree which maintains a partial
   * ordering where the parent "dominates" the two children.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void heapSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // First, create the heap.
    heapify(a);
    
    // Iteratively grab the max element from the top of the heap,
    // then ensure the heap property.
    //
    for (int i = a.length - 1; i > 0; i--) {
      swap(a, 0, i);
      bubbleDown(a, 0, (i - 1));
    }    
    
    // Write the sorted array back into the list.
    arrayIntoList(a, list);
  }

  /**
   * For all non-leaf nodes, fix heap property.
   */
  @SuppressWarnings("unchecked")
  private static void heapify(Object[] a) {
    assert(a != null);

    int parentIdx = 0;
    int lastIdx = a.length - 1;
    while (hasLeftChild(parentIdx, lastIdx)) {
      int largestChildIdx = largestChild(a, parentIdx, lastIdx);
      if (((Comparable) a[largestChildIdx]).compareTo(a[parentIdx]) > 0) {
	swap(a, parentIdx, largestChildIdx);
      }
      parentIdx++;
    }
  }

  /**
   * Continually swaps the parent element with the largest child
   * element until the heap property is restored or the parent element
   * ends up as a leaf.
   */
  @SuppressWarnings("unchecked")
  private static void bubbleDown(Object[] a, int parentIdx, int lastIdx) {
    assert(a != null);
    assert(parentIdx >= 0);
    assert(parentIdx <= lastIdx);

    boolean swapped = true;
    // hasLeftChild() determines if the parent has any children.
    while (swapped && hasLeftChild(parentIdx, lastIdx)) {
      // Swapping with the largest child is necessary to maintain heap property.
      int largestChildIdx = largestChild(a, parentIdx, lastIdx);
      if (((Comparable) a[largestChildIdx]).compareTo(a[parentIdx]) > 0) {
	swap(a, parentIdx, largestChildIdx);
	parentIdx = largestChildIdx;	
      } else {
	swapped = false;
      }
    }
  }

  /**
   * Returns the index of the larger of a parents two children. Assumes
   * at least the left child exists.
   */
  @SuppressWarnings("unchecked")
  private static int largestChild(Object[] a, int parentIdx, int lastIdx) {
    assert(hasLeftChild(parentIdx, lastIdx));

    int leftChildIdx = leftChild(parentIdx);
    int largestChildIdx = leftChildIdx;
    if (hasRightChild(parentIdx, lastIdx)) {
      int rightChildIdx = rightChild(parentIdx);
      if (((Comparable) a[rightChildIdx]).compareTo(a[leftChildIdx]) > 0) {
	largestChildIdx = rightChildIdx;
      }
    }
    return largestChildIdx;
  }

  // Returns index of left child assuming 0-based arrays.
  private static int leftChild(int parentIdx) {
    return ((2 * parentIdx) + 1);
  }

  // Returns index of right child assuming 0-based arrays.
  private static int rightChild(int parentIdx) {
    return ((2 * parentIdx) + 2);
  }

  private static boolean hasLeftChild(int parentIdx, int lastIdx) {
    return (leftChild(parentIdx) <= lastIdx);
  }

  private static boolean hasRightChild(int parentIdx, int lastIdx) {
    return (rightChild(parentIdx) <= lastIdx);
  }


  /**
   * Sorts the passed list using shell sort. Modifies the passed list.
   * Shell sort is a generalization of insertion sort. This sort starts
   * by sorting pairs of elements far away from each other, then progressively
   * reducing this "gap". Analysis of Shell sort is complicated, and depends
   * on the "gap". This implementation halves the "gap" at each iteration, and
   * the worst-case asymptotic runtime is O(n^2).
   *
   * Sorts elements in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void shellSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // Calculate the gap which defines the sublists. When the gap is
    // 1, then shell sort is the same as insertion sort.
    //
    int gap = (int) Math.floor(a.length / 2);
    while (gap >= 1) {
      for (int end = gap; end < a.length; end++) {
	int j = end;
	int i = j - gap;
	while ((i >= 0) && ((Comparable) a[i]).compareTo(a[j]) > 0) {
	  swap(a, i, j);
	  j = i;
	  i = j - gap;
	}
      }
      gap = (int) Math.floor(gap / 2);  // Reduce the gap, until it is 1.
    }

    // Write the sorted array back into the list.
    arrayIntoList(a, list);    
  }

  /**
   * Sorts the passed list using O(n^2) insertion sort. Modifies the
   * passed list.
   *
   * Sorts elements in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void insertionSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // For each element in the array, swap it into the lower position in the
    // array, until it is in the correct position.
    // 
    for (int i = 1; i < a.length; i++) {
      int j = i;
      while (j > 0 && ((Comparable) a[j - 1]).compareTo(a[j]) > 0) {
	swap(a, j, j - 1);
	j--;
      }
    }

    // Write the sorted array back into the list.
    arrayIntoList(a, list);    
  }


  /**
   * Sorts the passed list using O(n^2) selection sort. Modifies the
   * passed list.
   *
   * Sorts elements in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The list to be sorted.
   * @param T generic type of list element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void selectionSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // For each position in the array, find the minimum value in the
    // remaining sub-array and swap it into this position.
    //
    for (int i = 0; i < a.length; i++) {
      int min = i;
      for (int j = i + 1; j < a.length; j++) {
	if (((Comparable) a[min]).compareTo(a[j]) > 0) {
	  min = j;
	}
      }
      swap(a, i, min);
    }

    // Write the sorted array back into the list.
    arrayIntoList(a, list);
  }


  /**
   * Sorts the passed list using O(n^2) bubble sort. Modifies the
   * passed list.
   *
   * Sorts elements in ascending order.
   *
   * The list is first copied into an array, since we use indexes
   * for this algorithm. After the array is sorted, it is written
   * back into the passed list.
   *
   * @param list The List to be sorted.
   * @param T generic type of List element, which must implement Comparable.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> void bubbleSort(List<T> list) {
    assert(list != null);

    // First, copy the list into an array.
    Object[] a = list.toArray();

    // Swap adjacent members if they are out of order, "bubbling" the
    // largest value to the end of the array. At the end of each step of
    // the outer loop, the current largest value has been "bubbled" to
    // the end of the array.
    //
    for (int i = a.length; i > 0; i--) {
      for (int j = 1; j < i; j++) {
	if (((Comparable) a[j - 1]).compareTo(a[j]) > 0) {
	  swap(a, j, j - 1);
	}
      }
    }

    // Write the sorted array back into the list.
    arrayIntoList(a, list);
  }

  /**
   * Helper method which exchanges elements in array "a" at indexes
   * "index1" and "index2".
   */
  private static void swap(Object[] a, int index1, int index2) {
    assert(a.length > 0);
    assert(index1 < a.length);
    assert(index2 < a.length);

    if (index1 != index2) {
      Object temp = a[index1];
      a[index1] = a[index2];
      a[index2] = temp;
    }
  }

  /**
   * Helper method which writes the elements of sorted array "a"
   * into "list".
   */
  @SuppressWarnings("unchecked")
  private static <T> void arrayIntoList(Object[] a, List<T> list) {
    assert(a.length == list.size());

    ListIterator<T> iterator = list.listIterator();
    for (int i = 0; i < a.length; i++) {
      iterator.next();
      iterator.set((T) a[i]);
    }
  }


  public static List<Integer> createTestList(int size) {
    Random rand = new Random();
    List<Integer> list = new ArrayList<Integer>(size);
    for (int i = 0; i < size; i++) {
      int randomInt = rand.nextInt(size);
      list.add(randomInt);
    }
    return list;
  }

  public static <T extends Comparable<T>> boolean validateSorted(List<T> list) {
    boolean sorted = true;
    ListIterator<T> iterator = list.listIterator();
    if (iterator.hasNext()) {
      T prev = iterator.next();
      while (iterator.hasNext()) {
	T current = iterator.next();
	if ((prev).compareTo(current) > 0) {
	  sorted = false;
	  break;
	}
	prev = current;
      }
    }
    return sorted;
  }

  public static void main(String [] args) {
    System.out.println("STARTING Sort Test...");
    System.out.println();

    List<Integer> testListSizes = Arrays.asList(1, 2, 3, 10000);
    List<Integer> testList;
    
    // Test merge sort.
    System.out.println("Merge Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      mergeSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();

    // Test quick sort.
    System.out.println("Quick Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      quickSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();

    // Test heap sort.
    System.out.println("Heap Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      heapSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();

    // Test shell sort.
    System.out.println("Shell Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      insertionSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();
    
    // Test insertion sort.
    System.out.println("Insertion Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      insertionSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();

    // Test selection sort.
    System.out.println("Selection Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      selectionSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();
    
    // Test bubble sort.
    System.out.println("Bubble Sort");
    for (int listSize : testListSizes) {
      testList = createTestList(listSize);
      bubbleSort(testList);
      assert(validateSorted(testList));
    }
    System.out.println();

    System.out.println("FINISHED Sort Test");
  }
}
