package com.example.centrocomercialgui;

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
        FXMLLoader fxmlLoader = new FXMLLoader(CentroApplication.class.getResource("centro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Centro Comercial Manager");
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
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