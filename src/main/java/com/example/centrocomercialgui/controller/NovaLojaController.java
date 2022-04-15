package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.loja.*;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NovaLojaController implements Initializable
{
    public Label txtLoja;
    public ComboBox tipoLojaCombo;
    public GridPane mainGrid;

    private CentroController centroControllerWindow;

    private List<TextField> textFields;

    public void setCentroControllerWindow(CentroController centroControllerWindow)
    {
        this.centroControllerWindow = centroControllerWindow;
    }

    public void selectTipoLoja(ActionEvent actionEvent)
    {
        TipoLoja tipoLoja = (TipoLoja) tipoLojaCombo.getSelectionModel().getSelectedItem();

        switch (tipoLoja)
        {
            case ANCORA_EXTERNA -> {
                populatePane(AncoraExterna.class);
            }
            case ANCORA_PROPRIA -> {
                populatePane(AncoraPropria.class);
            }
            case QUIOSQUE -> {
                populatePane(LojaQuiosque.class);
            }
            case RESTAURANTE -> {
                populatePane(LojaRestauracao.class);
            }
            case OUTRO -> {
            }

        }
    }

    private void populatePane(Class<? extends Loja> loja)
    {
        mainGrid.getChildren().clear();

        if (textFields == null)
        {
            textFields = new ArrayList<>();
        }
        else
        {
            textFields.clear();
        }

        mainGrid.setAlignment(javafx.geometry.Pos.CENTER);

        Class res = loja;

        List<Field> vars = new ArrayList<>();

        System.out.println("Variáveis da classe: " + res.getName());

        for (Field f : res.getDeclaredFields())
        {
            if (f.isAnnotationPresent(Loja.UILojaElement.class))
                vars.add(f);
        }

        while(res.getSuperclass() != null)
        {
            res = res.getSuperclass();
            boolean first = true;
            for (Field f : res.getDeclaredFields())
            {

                if (f.isAnnotationPresent(Loja.UILojaElement.class))
                {
                    if (first)
                    {
                        vars.add(0, f);
                        first = false;
                    } else
                    {
                        vars.add(1,f);
                    }
                }
            }
        }

        for (int i = 0; i < vars.size(); i++)
        {
            System.out.println("Variável: " + vars.get(i).getName()+ " - " + vars.get(i).getType());
            Field f = vars.get(i);

            Label label = new Label(f.getName());
            GridPane.setMargin(label, new Insets(0, 20, 0, 0));

            mainGrid.add(label, 0, i);

            TextField textField = new TextField();

            mainGrid.add(textField, 1, i);
            GridPane.setMargin(textField, new Insets(0, 20, 0, 20));

            textFields.add(textField);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tipoLojaCombo.getItems().addAll(TipoLoja.values());
    }

    public void criarLojaAction(ActionEvent actionEvent)
    {
        Loja loja = null;

        for (int i = 0; i < textFields.size(); i++)
        {
            if (textFields.get(i).getText().isEmpty())
            {
                GUIUtils.errorMessage("Erro!","Campo " + textFields.get(i).getId() + " não pode ser vazio");
                return;
            }
        }

        switch ((TipoLoja)tipoLojaCombo.getSelectionModel().getSelectedItem())
        {
            case ANCORA_EXTERNA -> {
                loja = new AncoraExterna(textFields.get(0).getText(),
                        Integer.parseInt(textFields.get(2).getText()),
                        Float.parseFloat(textFields.get(1).getText()),
                        Integer.parseInt(textFields.get(3).getText()),
                        Float.parseFloat(textFields.get(4).getText()));
            }
            case ANCORA_PROPRIA -> {
                loja = new AncoraPropria(textFields.get(0).getText(),
                        Integer.parseInt(textFields.get(2).getText()),
                        Float.parseFloat(textFields.get(1).getText()),
                        Float.parseFloat(textFields.get(3).getText()));
            }
            case QUIOSQUE -> {
                loja = new LojaQuiosque(textFields.get(0).getText(),
                        Integer.parseInt(textFields.get(2).getText()),
                        Float.parseFloat(textFields.get(1).getText()),
                        Integer.parseInt(textFields.get(3).getText()));

            }
            case RESTAURANTE -> {
                loja = new LojaRestauracao(textFields.get(0).getText(),
                        Integer.parseInt(textFields.get(2).getText()),
                        Float.parseFloat(textFields.get(1).getText()),
                        Integer.parseInt(textFields.get(3).getText()),
                        Integer.parseInt(textFields.get(4).getText()),
                        Float.parseFloat(textFields.get(5).getText()));
            }
            case OUTRO -> {
            }

        }

        centroControllerWindow.newLoja(loja);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso!");
        alert.setHeaderText("Loja criada com sucesso!");
        alert.setContentText("A loja " + loja.getNome() + " foi criada com sucesso!");
        alert.showAndWait();
    }

    public void cancelAction(ActionEvent actionEvent)
    {
        Stage stage = (Stage) txtLoja.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}