module com.example.sortingalg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sortingalg to javafx.fxml;
    exports com.example.sortingalg;
}