package com.example.centrocomercialgui.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CentroApplication extends Application
{
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException
    {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/com/example/centrocomercialgui/centro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Centro Comercial Manager");
        stage.setScene(scene);
        stage.show();


    }

    public static Stage getMainStage()
    {
        return mainStage;
    }

    public static void main(String[] args)
    {
        launch();
    }
}