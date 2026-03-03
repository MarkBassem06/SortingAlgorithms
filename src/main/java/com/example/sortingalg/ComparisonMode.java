package com.example.sortingalg;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sortingAlgorithms.SortingAlg;
import sortingAlgorithms.SortingCompCalculate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComparisonMode {

    void show(Stage stage) {
        //Choosing Algorithms
        CheckBox selectionCB = new CheckBox("Selection Sort");
        CheckBox insertionCB = new CheckBox("Insertion Sort");
        CheckBox bubbleCB = new CheckBox("Bubble Sort");
        CheckBox mergeCB = new CheckBox("Merge Sort");
        CheckBox heapCB = new CheckBox("Heap Sort");
        CheckBox quickCB = new CheckBox("Quick Sort");

        HBox algorithmBox = new HBox(5,
                selectionCB, insertionCB, bubbleCB,
                mergeCB, heapCB, quickCB
        );

        //Choose Input type (file/generated)
        RadioButton fileIn = new RadioButton("Load From File");
        RadioButton generatedIn = new RadioButton("Generated Array");

        ToggleGroup inputGroup = new ToggleGroup();
        generatedIn.setToggleGroup(inputGroup);
        fileIn.setToggleGroup(inputGroup);
        generatedIn.setSelected(true);

        //Insert File path
        TextField filePathField = new TextField();
        filePathField.setPromptText("Enter file path");
        filePathField.setDisable(true);

        //Select generated type
        ComboBox<String> generatedType = new ComboBox<>();
        generatedType.getItems().addAll("Random", "Sorted", "Reverse");
        generatedType.setValue("Random");
        generatedType.setDisable(false);

        //Select size and no of runs
        TextField sizeField = new TextField("1000");
        TextField runsField = new TextField("5");
        sizeField.setDisable(false);

        fileIn.setOnAction(e -> {
            filePathField.setDisable(false);
            generatedType.setDisable(true);
            sizeField.setDisable(true);
        });
        generatedIn.setOnAction(e -> {
            filePathField.setDisable(true);
            generatedType.setDisable(false);
            sizeField.setDisable(false);
        });




        Button runBtn = new Button("Run Comparison");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        //Run btn
        runBtn.setOnAction(e -> {
            int size = Integer.parseInt(sizeField.getText());
            int runs = Integer.parseInt(runsField.getText());

            //Algorithms selection for running
            List<String> selectedAlgorithms = new ArrayList<>();
            if (selectionCB.isSelected()) selectedAlgorithms.add("Selection Sort");
            if (insertionCB.isSelected()) selectedAlgorithms.add("Insertion Sort");
            if (bubbleCB.isSelected()) selectedAlgorithms.add("Bubble Sort");
            if (mergeCB.isSelected()) selectedAlgorithms.add("Merge Sort");
            if (heapCB.isSelected()) selectedAlgorithms.add("Heap Sort");
            if (quickCB.isSelected()) selectedAlgorithms.add("Quick Sort");

            if (selectedAlgorithms.isEmpty()) {
                outputArea.setText("Please select at least one algorithm.");
                return;
            }

            //Original input Array
            int[] inputArr;
            if (fileIn.isSelected()) {
                inputArr = FileInput.loadFromFile(filePathField.getText());
            } else {
                switch (generatedType.getValue()) {
                    case "Sorted":
                        inputArr = ArrayGenerator.generateSorted(size);
                        break;
                    case "Reverse":
                        inputArr = ArrayGenerator.generateReverse(size);
                        break;
                    default:
                        inputArr = ArrayGenerator.generateRandom(size);
                }
            }

            //CSV file
            StringBuilder csvBuilder = new StringBuilder();

            //Algorithms loop
            for (String algName : selectedAlgorithms) {
                long totalTime = 0;
                long minTime = Long.MAX_VALUE;
                long maxTime = Long.MIN_VALUE;
                long totalComparisons = 0;
                long totalSwaps = 0;

                //Sorting array loop
                for (int i = 0; i < runs; i++) {
                    int[] arrCopy = inputArr.clone();
                    SortingAlg algorithm = getAlgorithm(algName);

                    long start = System.nanoTime()/10^6;
                    algorithm.sort(arrCopy);
                    long end = System.nanoTime()/10^6;
                    long time = end - start;

                    totalTime += time;
                    minTime = Math.min(minTime, time);
                    maxTime = Math.max(maxTime, time);

                    SortingCompCalculate calc = algorithm.getCalculation();
                    totalComparisons += calc.getComparisons();
                    totalSwaps += calc.getSwaps();
                }
                long average = totalTime / runs;

                //Add results to CSV file
                csvBuilder.append(algName).append(",")
                        .append(fileIn.isSelected() ? "File" : generatedType.getValue()).append(",")
                        .append(inputArr.length).append(",")
                        .append(runs).append(",")
                        .append(average).append(",")
                        .append(minTime).append(",")
                        .append(maxTime).append(",")
                        .append(totalComparisons).append(",")
                        .append(totalSwaps)
                        .append("\n");
            }

            //Save CSV file
            try {
                File file = new File("sorting_results.csv");
                boolean fileExists = file.exists();
                FileWriter writer = new FileWriter(file, true);
                if (!fileExists) {
                    writer.write("Algorithm,ArrayType,Size,Runs,AverageTime,MinTime,MaxTime,Comparisons,Swaps\n");
                }
                writer.append(csvBuilder.toString());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            outputArea.setText("CSV file generated successfully.");
        });
        VBox layout = new VBox(10,
                algorithmBox,
                generatedIn,
                fileIn,
                filePathField,
                generatedType,
                sizeField,
                runsField,
                runBtn,
                outputArea
        );

        Scene scene = new Scene(layout, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Comparison Mode");
        stage.show();
    }

    private SortingAlg getAlgorithm(String name) {
        switch (name) {
            case "Insertion Sort":
                return new sortingAlgorithms.InsertionSort();
            case "Bubble Sort":
                return new sortingAlgorithms.BubbleSort();
            case "Merge Sort":
                return new sortingAlgorithms.MergeSort();
            case "Heap Sort":
                return new sortingAlgorithms.HeapSort();
            case "Quick Sort":
                return new sortingAlgorithms.QuickSort();
            default:
                return new sortingAlgorithms.SelectionSort();
        }
    }
}
