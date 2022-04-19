module com.example.centrocomercialgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.centrocomercialgui to javafx.fxml;
    exports com.example.centrocomercialgui.model;
    opens com.example.centrocomercialgui.model to javafx.fxml;
    exports com.example.centrocomercialgui.controller;
    opens com.example.centrocomercialgui.controller to javafx.fxml;
}