package com.example.sortingalg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sortingAlgorithms.*;

public class TestApplication extends Application {
    @Override
    public void start(Stage stage) {
        ComparisonMode comparisonMode = new ComparisonMode();
        VisualizationMode visualizationMode = new VisualizationMode();

        Tab comparisonTab = new Tab("Comparison Mode");
        comparisonTab.setContent(comparisonMode.getLayout());
        comparisonTab.setClosable(false);

        Tab visualizationTab = new Tab("Visualization Mode");
        visualizationTab.setContent(visualizationMode.getLayout());
        visualizationTab.setClosable(false);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(comparisonTab, visualizationTab);

        Scene scene = new Scene(tabPane, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Sorting Algorithms Project");
        stage.show();
    }
}

