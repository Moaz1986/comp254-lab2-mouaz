/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */


/**
 * Code for end-of-chapter exercises on asymptotics.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
class Exercises {

  /**
   * Time Complexity: O(n)
   * Explanation: The loop iterates through the entire array once.
   */
  public static int example1(int[] arr) {
    int n = arr.length, total = 0;
    for (int j = 0; j < n; j++)       // loop from 0 to n-1
      total += arr[j];
    return total;
  }

  /**
   * Time Complexity: O(n)
   * Explanation: The loop increments by 2, but the number of operations
   * is still proportional to the size of the array n.
   */
  public static int example2(int[] arr) {
    int n = arr.length, total = 0;
    for (int j = 0; j < n; j += 2)    // note the increment of 2
      total += arr[j];
    return total;
  }

  /**
   * Time Complexity: O(n^2)
   * Explanation: The outer loop runs n times, and for each iteration,
   * the inner loop runs up to j times, resulting in quadratic time.
   */
  public static int example3(int[] arr) {
    int n = arr.length, total = 0;
    for (int j = 0; j < n; j++)       // loop from 0 to n-1
      for (int k = 0; k <= j; k++)    // loop from 0 to j
        total += arr[j];
    return total;
  }

  /**
   * Time Complexity: O(n)
   * Explanation: A single loop is used, and all operations inside
   * the loop take constant time.
   */
  public static int example4(int[] arr) {
    int n = arr.length, prefix = 0, total = 0;
    for (int j = 0; j < n; j++) {     // loop from 0 to n-1
      prefix += arr[j];
      total += prefix;
    }
    return total;
  }

  /**
   * Time Complexity: O(n^3)
   * Explanation: The outer loop runs n times. Inside it, there is a nested
   * loop structure where j runs n times and k runs up to j times,
   * resulting in cubic time complexity.
   */
  public static int example5(int[] first, int[] second) { // assume equal-length arrays
    int n = first.length, count = 0;
    for (int i = 0; i < n; i++) {     // loop from 0 to n-1
      int total = 0;
      for (int j = 0; j < n; j++)     // loop from 0 to n-1
        for (int k = 0; k <= j; k++)  // loop from 0 to j
          total += first[k];
      if (second[i] == total) count++;
    }
    return count;
  }

}
