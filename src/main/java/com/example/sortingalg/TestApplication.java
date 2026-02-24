package com.example.sortingalg;

import javafx.application.Application;
import javafx.stage.Stage;
import sortingAlgorithms.*;

public class TestApplication extends Application {

    @Override
    public void start(Stage stage) {

        int[] arr1 = {5, 2, 8, 1, 4};
        int[] arr2 = {5, 2, 8, 1, 4};
        int[] arr3 = {5, 2, 8, 1, 4};
        int[] arr4 = {5, 2, 8, 1, 4};
        int[] arr5 = {5, 2, 8, 1, 4};
        int[] arr6 = {5, 2, 8, 1, 4};

        SortingAlg algSelect = new SelectionSort();
        SortingAlg algBubble = new BubbleSort();
        SortingAlg algInsert = new InsertionSort();
        SortingAlg algMerge = new MergeSort();
        SortingAlg algQuick = new QuickSort();
        SortingAlg algHeap = new HeapSort();



        int[] result1 = algSelect.sort(arr1); //test Selection
        int[] result2 = algBubble.sort(arr2); //test Bubble
        int[] result3 = algInsert.sort(arr3); //test Insertion
        int[] result4 = algMerge.sort(arr4); //test merge
        int[] result5 = algQuick.sort(arr5); //test Quick
        int[] result6 = algHeap.sort(arr6); //test heap

        System.out.println("Selection: ");
        for (int num : result1) {
            System.out.print(num + " ");
        }
        System.out.println("\nBubble: ");
        for (int num : result2) {
            System.out.print(num + " ");
        }
        System.out.println("\nInsertion: ");
        for (int num : result3) {
            System.out.print(num + " ");
        }
        System.out.println("\nMerge: ");
        for (int num : result4) {
            System.out.print(num + " ");
        }
        System.out.println("\nQuick: ");
        for (int num : result5) {
            System.out.print(num + " ");
        }
        System.out.println("\nHeap: ");
        for (int num : result6) {
            System.out.print(num + " ");
        }


        System.exit(0);

    }
}
