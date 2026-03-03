package com.example.sortingalg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sortingAlgorithms.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestApplication extends Application {
    @Override
    public void start(Stage stage) {

        Button comparisonBtn = new Button("Comparison Mode");
        Button visualizationBtn = new Button("Visualization Mode");

        comparisonBtn.setPrefWidth(200);
        visualizationBtn.setPrefWidth(200);

        VBox root = new VBox(20, comparisonBtn, visualizationBtn);
        root.setStyle("-fx-alignment: center; -fx-padding: 40;");

        ComparisonMode compare = new ComparisonMode();
        VisualizationMode visualize = new VisualizationMode();

        comparisonBtn.setOnAction(e -> compare.show(stage));
        visualizationBtn.setOnAction(e -> visualize.show(stage));

        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.setTitle("Sorting Algorithms Project");
        stage.show();
    }
}
