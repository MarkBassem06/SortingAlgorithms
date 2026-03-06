package com.example.sortingalg;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sortingAlgorithms.SortingAlg;


public class VisualizationMode {
    //For coloring indices (comp -> red / swap -> green)
    private int compIndex1 = -1;
    private int compIndex2 = -1;
    private int swapIndex1 = -1;
    private int swapIndex2 = -1;

    Pane getLayout() {
        //Choose Input type (file/generated)
        RadioButton generatedIn = new RadioButton("Generated Array");
        RadioButton fileIn = new RadioButton("Load From File");
        ToggleGroup inputGroup = new ToggleGroup();
        generatedIn.setToggleGroup(inputGroup);
        fileIn.setToggleGroup(inputGroup);
        generatedIn.setSelected(true);

        //Select size and delay
        Label sizeLabel = new Label("Array Size:");
        TextField sizeField = new TextField("50"); // max 100 for visualization
        Label speedLabel = new Label("Delay(ms):");
        TextField speedField = new TextField("20"); // milliseconds delay

        //Select Algorithm
        ComboBox<String> algorithmBox = new ComboBox<>();
        algorithmBox.getItems().addAll("Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Heap Sort", "Quick Sort");
        algorithmBox.setValue("Selection Sort");

        //Select generated type
        ComboBox<String> generatedType = new ComboBox<>();
        generatedType.getItems().addAll("Random", "Sorted", "Reverse");
        generatedType.setValue("Random");
        generatedType.setDisable(false);

        //Insert File path
        TextField filePathField = new TextField();
        filePathField.setPromptText("Enter file path");
        filePathField.setDisable(true);

        fileIn.setOnAction(e -> {
            filePathField.setDisable(false);
            generatedType.setDisable(true);
            sizeField.setDisable(true);

        });
        generatedIn.setOnAction(e ->{
            filePathField.setDisable(true);
            generatedType.setDisable(false);
            sizeField.setDisable(false);
        });

        //Labels for Comparisons and Swaps after running
        Label comparisonsLabel = new Label("Comparisons: 0");
        Label swapsLabel = new Label("Swaps: 0");


        Button visButton = new Button("Visualize");

        //For Bars representation
        Pane barPane = new Pane();
        barPane.setPrefSize(800, 400);
        barPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: black;");

        HBox sizeBox = new HBox(5,sizeLabel, sizeField);
        HBox speedBox = new HBox(5,speedLabel, speedField);
        HBox selection = new HBox(50, algorithmBox, generatedIn, fileIn);
        HBox labelBox = new HBox(50, comparisonsLabel, swapsLabel);

        VBox layout = new VBox(10, selection, filePathField,generatedType, sizeBox, speedBox, visButton, barPane, labelBox);
        layout.setStyle("-fx-padding: 20;");

        //Visualize btn action
        visButton.setOnAction(e -> {
            visButton.setDisable(true);
            comparisonsLabel.setText("Comparisons: 0");
            swapsLabel.setText("Swaps: 0");
            int size = Integer.parseInt(sizeField.getText());
            int speed = Integer.parseInt(speedField.getText());

            if (size > 100) {
                showAlert("Max size for visualization is 100.");
                return;
            }

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

            drawBars(barPane, inputArr);
            SortingAlg algorithm = getAlgorithm(algorithmBox.getValue());
            algorithm.setListener(new sortingAlgorithms.SortingListener() {
                @Override
                public void onCompare(int[] array, int i, int j) {
                    compIndex1 = i;
                    compIndex2 = j;

                    pause(speed);
                    javafx.application.Platform.runLater(() -> drawBars(barPane, array));

                    compIndex1 = -1;
                    compIndex2 = -1;
                }

                @Override
                public void onSwap(int[] array, int i, int j) {
                    swapIndex1 = i;
                    swapIndex2 = j;

                    pause(speed);
                    javafx.application.Platform.runLater(() -> drawBars(barPane, array));

                    swapIndex1 = -1;
                    swapIndex2 = -1;
                }

                private void pause(int time) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException ignored) {
                    }
                }
            });

            new Thread(() -> {
                algorithm.sort(inputArr);
                javafx.application.Platform.runLater(() -> {
                    int comparisons = algorithm.getCalculation().getComparisons();
                    int swaps = algorithm.getCalculation().getSwaps();

                    comparisonsLabel.setText("Comparisons: " + comparisons);
                    swapsLabel.setText("Swaps: " + swaps);

                    visButton.setDisable(false);
                });
            }).start();
        });
        return layout;
    }
    private void drawBars(Pane pane, int[] arr) {
        pane.getChildren().clear();  //clear bars everytime

        double width = pane.getWidth() / arr.length;
        int max = getMax(arr);

        //Draw bars (after update)
        for (int i = 0; i < arr.length; i++) {
            double height = ((double) arr[i] / max) * pane.getHeight();
            Rectangle rect = new Rectangle(
                    i * width,
                    pane.getHeight() - height,
                    width - 2,
                    height
            );
            if (i == swapIndex1 || i == swapIndex2) {
                rect.setFill(Color.GREEN);
            }
            else if (i == compIndex1 || i == compIndex2) {
                rect.setFill(Color.RED);
            }
            else {
                rect.setFill(Color.BLUE);
            }
            pane.getChildren().add(rect);
        }
    }
    private int getMax(int[] arr) {
        int max = arr[0];
        for (int val : arr) {
            if (val > max){
                max = val;
            }
        }
        return max;
    }

    //Alert if size>100
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
