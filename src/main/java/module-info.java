module com.example.centrocomercialgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.centrocomercialgui to javafx.fxml;
    exports com.example.centrocomercialgui;
}