package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.loja.*;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
    public Button btnCreate;

    private CentroController centroControllerWindow;

    private List<TextField> textFields;

    private Loja workingLoja;

    public void setCentroControllerWindow(CentroController centroControllerWindow)
    {
        this.centroControllerWindow = centroControllerWindow;
    }

    public void setWorkingLoja(Loja workingLoja)
    {
        this.workingLoja = workingLoja;
        btnCreate.setText("Editar");
        txtLoja.setText("Editar Loja");
        populatePane(workingLoja);
    }

    public void selectTipoLoja(ActionEvent actionEvent)
    {
        TipoLoja tipoLoja = (TipoLoja) tipoLojaCombo.getSelectionModel().getSelectedItem();

        if (workingLoja != null)
        {
            workingLoja.setTipoLoja(tipoLoja);
            populatePane(workingLoja);
        }

        switch (tipoLoja)
        {
            case ANCORA_EXTERNA -> {
                if (workingLoja == null)
                {
                    populatePane(AncoraExterna.class);
                }
                else if (workingLoja instanceof AncoraExterna)
                {
                    populatePane(workingLoja);
                }
            }
            case ANCORA_PROPRIA -> {
                if (workingLoja == null)
                {
                    populatePane(AncoraPropria.class);
                }
                else if (workingLoja instanceof AncoraPropria)
                {
                    populatePane(workingLoja);
                }
            }
            case QUIOSQUE -> {
                if (workingLoja == null)
                {
                    populatePane(LojaQuiosque.class);
                }
                else if (workingLoja instanceof LojaQuiosque)
                {
                    populatePane(workingLoja);
                }
            }
            case RESTAURANTE -> {
                if (workingLoja == null)
                {
                    populatePane(LojaRestauracao.class);
                }
                else if (workingLoja instanceof LojaRestauracao)
                {
                    populatePane(workingLoja);
                }
            }
            case OUTRO -> {
            }

        }
    }

    private List<Field> populateVars(Class<? extends Loja> loja)
    {
        Class res = loja;

        List<Field> vars = new ArrayList<>();


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

        return vars;
    }

    private void populateFields(List<Field> vars, Loja loja)
    {
        for (int i = 0; i < vars.size(); i++)
        {
            System.out.println("Variável: " + vars.get(i).getName()+ " - " + vars.get(i).getType());
            Field f = vars.get(i);

            Label label = new Label(f.getName());
            GridPane.setMargin(label, new Insets(0, 20, 0, 0));

            mainGrid.add(label, 0, i);

            TextField textField = new TextField();

            if (loja != null)
            {
                try
                {
                    Field fl = getFieldObject(f.getName(), loja.getClass());
                    if (fl != null)
                    {
                        fl.setAccessible(true);
                        textField.setText(fl.get(loja).toString());
                    }
                    else
                    {
                        System.out.println("Não encontrou o campo " + f.getName());
                    }
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }

            mainGrid.add(textField, 1, i);
            GridPane.setMargin(textField, new Insets(0, 20, 0, 20));

            textFields.add(textField);
        }
    }

    private Field getFieldObject(String name, Class<? extends Loja> loja)
    {
        Field fl = null;

        try
        {
            fl = loja.getDeclaredField(name);
        }
        catch (NoSuchFieldException e)
        {
            if (loja.getSuperclass() != null)
            {
                return getFieldObject(name, (Class<? extends Loja>) loja.getSuperclass());
            }
            else
            {
                return null;
            }
        }

        return fl;
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

        List<Field> vars = populateVars(loja);

        populateFields(vars, null);
    }

    private void populatePane(Loja loja)
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

        List<Field> vars = populateVars(loja.getClass());

        populateFields(vars, loja);
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

        if (workingLoja != null)
        {
            editLoja();
            return;
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

    private void editLoja()
    {
        switch (workingLoja.getTipoLoja())
        {
            case ANCORA_EXTERNA -> {
                workingLoja.setNome(textFields.get(0).getText());
                workingLoja.setReceitas(Float.parseFloat(textFields.get(1).getText()));
                workingLoja.setArea(Integer.parseInt(textFields.get(2).getText()));
                ((AncoraExterna)workingLoja).setNumeroFuncionarios(Integer.parseInt(textFields.get(3).getText()));
                ((AncoraExterna)workingLoja).setCustoSeguranca(Float.parseFloat(textFields.get(4).getText()));
            }
            case ANCORA_PROPRIA -> {
                workingLoja.setNome(textFields.get(0).getText());
                workingLoja.setReceitas(Float.parseFloat(textFields.get(1).getText()));
                workingLoja.setArea(Integer.parseInt(textFields.get(2).getText()));
                ((AncoraPropria)workingLoja).setCustoSeguranca(Float.parseFloat(textFields.get(3).getText()));
            }
            case QUIOSQUE -> {
                workingLoja.setNome(textFields.get(0).getText());
                workingLoja.setReceitas(Float.parseFloat(textFields.get(1).getText()));
                workingLoja.setArea(Integer.parseInt(textFields.get(2).getText()));
                ((LojaQuiosque)workingLoja).setNumeroFuncionarios(Integer.parseInt(textFields.get(3).getText()));
            }
            case RESTAURANTE -> {
                workingLoja.setNome(textFields.get(0).getText());
                workingLoja.setReceitas(Float.parseFloat(textFields.get(1).getText()));
                workingLoja.setArea(Integer.parseInt(textFields.get(2).getText()));
                ((LojaRestauracao)workingLoja).setNumeroFuncionarios(Integer.parseInt(textFields.get(3).getText()));
                ((LojaRestauracao)workingLoja).setNumMesas(Integer.parseInt(textFields.get(4).getText()));
                ((LojaRestauracao)workingLoja).setCustoManutencao(Float.parseFloat(textFields.get(5).getText()));
            }
        }

        centroControllerWindow.editLoja(workingLoja);

        cancelAction(null);
    }

    public void cancelAction(ActionEvent actionEvent)
    {
        Stage stage = (Stage) txtLoja.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}