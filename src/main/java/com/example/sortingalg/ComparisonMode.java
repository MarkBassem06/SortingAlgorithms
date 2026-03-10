package com.example.sortingalg;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sortingAlgorithms.SortingAlg;
import sortingAlgorithms.SortingCompCalculate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComparisonMode {

    Pane getLayout(){
        //Choosing Algorithms
        CheckBox selectionCB = new CheckBox("Selection Sort");
        CheckBox insertionCB = new CheckBox("Insertion Sort");
        CheckBox bubbleCB = new CheckBox("Bubble Sort");
        CheckBox mergeCB = new CheckBox("Merge Sort");
        CheckBox heapCB = new CheckBox("Heap Sort");
        CheckBox quickCB = new CheckBox("Quick Sort");

        HBox algorithmBox = new HBox(20 ,
                selectionCB, insertionCB, bubbleCB,
                mergeCB, heapCB, quickCB
        );

        //Choose Input type (file/generated)
        RadioButton fileIn = new RadioButton("Load From File");
        RadioButton generatedIn = new RadioButton("Generated Array");
        HBox typeIn = new HBox(20,generatedIn, fileIn);

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
        Label sizeLabel = new Label("Array Size:");
        TextField sizeField = new TextField("1000");
        Label runsLabel = new Label("No of Runs:");
        TextField runsField = new TextField("5");
        sizeField.setDisable(false);
        HBox sizeBox = new HBox(5,sizeLabel, sizeField);
        HBox runsBox = new HBox(5,runsLabel, runsField);

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

            if (size > 10000) {
                showAlert("Max size for comparison is 10000.");
                return;
            }

            //Algorithms selection for running
            List<String> selectedAlgorithms = new ArrayList<>();
            if (selectionCB.isSelected()) selectedAlgorithms.add("Selection Sort");
            if (insertionCB.isSelected()) selectedAlgorithms.add("Insertion Sort");
            if (bubbleCB.isSelected()) selectedAlgorithms.add("Bubble Sort");
            if (mergeCB.isSelected()) selectedAlgorithms.add("Merge Sort");
            if (heapCB.isSelected()) selectedAlgorithms.add("Heap Sort");
            if (quickCB.isSelected()) selectedAlgorithms.add("Quick Sort");

            if (selectedAlgorithms.isEmpty()) {
                showAlert("Select at least 1 Algorithm!");
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
                Double totalTime = 0.0;
                Double minTime = Double.MAX_VALUE;
                Double maxTime = Double.MIN_VALUE;
                long totalComparisons = 0;
                long totalSwaps = 0;
                Long minComp = Long.MAX_VALUE;
                Long maxComp = Long.MIN_VALUE;
                Long minSwaps = Long.MAX_VALUE;
                Long maxSwaps = Long.MIN_VALUE;

                //Sorting array loop
                for (int i = 0; i < runs; i++) {
                    int[] arrCopy = inputArr.clone();
                    SortingAlg algorithm = getAlgorithm(algName);

                    double start = System.nanoTime()/1000000.0;
                    algorithm.sort(arrCopy);
                    double end = System.nanoTime()/1000000.0;
                    double time = end - start;

                    totalTime += time;
                    minTime = Math.min(minTime, time);
                    maxTime = Math.max(maxTime, time);

                    SortingCompCalculate calc = algorithm.getCalculation();
                    totalComparisons += calc.getComparisons();
                    totalSwaps += calc.getSwaps();
                }
                Double averageTime = totalTime / runs;
                Long averageComp = totalComparisons / runs;
                Long averageSwaps = totalSwaps / runs;

                //Add results to CSV file
                csvBuilder.append(algName).append(",")
                        .append(fileIn.isSelected() ? "File: " +filePathField.getText() : generatedType.getValue()).append(",")
                        .append(inputArr.length).append(",")
                        .append(runs).append(",")
                        .append(averageTime).append(",")
                        .append(minTime).append(",")
                        .append(maxTime).append(",")
                        .append(averageComp).append(",")
                        .append(averageSwaps)
                        .append("\n");
            }

            //Save CSV file
            try {
                File file = new File("sorting_results3.csv");
                boolean fileExists = file.exists();
                FileWriter writer = new FileWriter(file, true);
                if (!fileExists) {
                    writer.write("Algorithm,ArrayType,Size,Runs,AverageTime(ms),MinTime(ms),MaxTime(ms),AverageComparisons,AverageSwaps\n");
                }
                writer.append(csvBuilder.toString());
                writer.close();
                if (!fileExists) {
                    outputArea.setText("CSV file created successfully.");
                }
                else{
                    outputArea.setText("CSV file updated successfully.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        VBox layout = new VBox(10,
                algorithmBox,
                typeIn,
                filePathField,
                generatedType,
                sizeBox,
                runsBox,
                runBtn,
                outputArea
        );
        layout.setStyle("-fx-padding: 20;");
        return layout;
    }
    //Alert if size>10000 or if no algorithm is selected
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
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
