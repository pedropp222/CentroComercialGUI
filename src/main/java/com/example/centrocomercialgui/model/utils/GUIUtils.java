package com.example.centrocomercialgui.model.utils;

import javafx.scene.control.Alert;

public class GUIUtils
{
    public static void errorMessage(String header, String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}