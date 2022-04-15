package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.CentroApplication;
import com.example.centrocomercialgui.model.loja.CentroComercial;
import com.example.centrocomercialgui.model.loja.FileManager;
import com.example.centrocomercialgui.model.loja.Loja;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CentroController implements Initializable
{

    public StackPane treeStack;
    public Label txtCentroName;
    public VBox lojaBox;
    public Label txtLojaPropriedades;
    public MenuItem saveItem;
    public MenuItem saveAsItem;
    public HBox buttonBox;
    public ListView<Loja> lojaList;
    public TabPane tabPane;

    private File currentFile;
    private CentroComercial currentCentro;


    private boolean dirtyFile;

    public void abrirCentroAction(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Abrir Centro Comercial");
        File selected = fc.showOpenDialog(CentroApplication.getMainStage());

        if (selected != null)
        {
            try
            {
                currentCentro = FileManager.loadFile(selected.getAbsolutePath());
                currentFile = selected;

                txtCentroName.setText("Centro Comercial: " + currentCentro.getNome() + " | " + currentCentro.getMorada());

                updateList();

                buttonBox.setDisable(false);

                saveItem.setDisable(false);
                saveAsItem.setDisable(false);
                tabPane.setDisable(false);

                updateWindowTitle();
            } catch (FileNotFoundException e)
            {
                System.out.println("Erro a abrir loja?: " + e.getMessage());
            }
        }
    }

    public void sairAction(ActionEvent actionEvent)
    {
        //call onCloseRequest on this window
        CentroApplication.getMainStage().fireEvent(new WindowEvent(CentroApplication.getMainStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void updateList()
    {
        lojaList.getItems().clear();
        lojaList.getItems().addAll(currentCentro.getLojas());
        selectLojaList(null);
    }

    private void selectLojaList(Loja loja)
    {
        if (loja != null)
        {
            lojaBox.setDisable(false);
            txtLojaPropriedades.setText("Propriedades de: " + loja.getNome());
        }
        else
        {
            lojaBox.setDisable(true);
            txtLojaPropriedades.setText("Propriedades");
        }
    }

    public void novoCentroAction(ActionEvent actionEvent)
    {
    }

    public void saveAction(ActionEvent actionEvent)
    {
        try
        {
            FileManager.saveFile(currentFile.getAbsolutePath(), currentCentro);
            dirtyFile = false;
            updateWindowTitle();
        } catch (IOException e)
        {
            //alert for error message
            GUIUtils.errorMessage("Erro a guardar ficheiro", e.getMessage());

            e.printStackTrace();
        }
    }

    public void saveAsAction(ActionEvent actionEvent)
    {
        //create a file chooser to save the file
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Centro Comercial Como...");
        File selected = fc.showSaveDialog(CentroApplication.getMainStage());

        if (selected != null)
        {
            try
            {
                FileManager.saveFile(selected.getAbsolutePath(), currentCentro);
                currentFile = selected;
                dirtyFile = false;
                updateWindowTitle();
            } catch (IOException e)
            {
                GUIUtils.errorMessage("Erro ao guardar ficheiro", "Erro: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    public void novaLojaAction(ActionEvent actionEvent)
    {
        //create new window using the novaLoja.fxml file
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/centrocomercialgui/novaLoja.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
            stage.setTitle("Nova Loja");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            loader.<NovaLojaController>getController().setCentroControllerWindow(this);

            stage.show();
        } catch (Exception e)
        {
            //alert for error message
            GUIUtils.errorMessage("Erro ao abrir janela", "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateWindowTitle()
    {
        if (currentFile != null)
        {
            String title = "Centro Comercial - " + currentFile.getName();
            if (dirtyFile)
            {
                title += "*";
            }
            CentroApplication.getMainStage().setTitle(title);
        }
        else
        {
            CentroApplication.getMainStage().setTitle("Centro Comercial");
        }
    }

    public void newLoja(Loja loja)
    {
        currentCentro.adicionarLoja(loja);
        updateList();
        dirtyFile = true;
        updateWindowTitle();
    }

    public void apagarLojaAction(ActionEvent actionEvent)
    {
        Loja selected = lojaList.getSelectionModel().getSelectedItem();
        if (selected != null)
        {
            currentCentro.removerLoja(selected);
            updateList();
            dirtyFile = true;
            updateWindowTitle();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //update the closing window event to show a confirmation dialog
        CentroApplication.getMainStage().setOnCloseRequest(event ->
        {
            if (dirtyFile)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Quer guardar o ficheiro antes de sair?", ButtonType.YES, ButtonType.NO,ButtonType.CANCEL);
                alert.setHeaderText("Fechar aplicação");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES)
                {
                    saveAction(null);
                }
                else if (result.get() == ButtonType.CANCEL)
                {
                    event.consume();
                }
            }
        });

        tabPane.setDisable(true);

        //event handler for selected item change on the loja list
        lojaList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectLojaList(newValue));
    }
}