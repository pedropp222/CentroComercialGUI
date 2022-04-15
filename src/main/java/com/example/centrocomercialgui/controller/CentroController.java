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
                openCentroComercial(FileManager.loadFile(selected.getAbsolutePath()), selected);
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
        if (currentCentro != null)
        {
            lojaList.getItems().addAll(currentCentro.getLojas());
        }
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
        if (dirtyFile)
        {
            Optional<ButtonType> result = confirmationMessage("Novo Centro Comercial", "Quer guardar o ficheiro atual?");
            if (result.get() == ButtonType.YES)
            {
                saveAction(null);
            }
        }

        closeCentroComercial();

        //create new windows using fxml loader, file novoCentro.fxml
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/centrocomercialgui/novoCentro.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Novo Centro Comercial");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            loader.<NovoCentroController>getController().setCentroController(this);

            stage.showAndWait();
        } catch (IOException e)
        {
            GUIUtils.errorMessage("Erro ao abrir novo centro comercial", e.getMessage());
        }
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

    public File saveAsAction(ActionEvent actionEvent)
    {
        //create a file chooser to save the file
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Centro Comercial Como...");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheiro de texto", "*.txt"));
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

        return selected;
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

    public void closeCentroComercial()
    {
        currentCentro = null;
        currentFile = null;

        buttonBox.setDisable(true);
        saveItem.setDisable(true);
        saveAsItem.setDisable(true);
        tabPane.setDisable(true);

        updateList();
        updateWindowTitle();
    }

    private void openCentroComercial(CentroComercial centro, File file)
    {
        this.currentCentro = centro;
        this.currentFile = file;
        txtCentroName.setText("Centro Comercial: " + currentCentro.getNome() + " | " + currentCentro.getMorada());

        updateList();

        buttonBox.setDisable(false);

        saveItem.setDisable(false);
        saveAsItem.setDisable(false);
        tabPane.setDisable(false);

        updateWindowTitle();
    }

    public void criarCentroComercial(CentroComercial centro)
    {
        currentCentro = centro;
        File f = saveAsAction(null);
        if (f != null)
        {
            openCentroComercial(centro, f);
        }
    }

    public Optional<ButtonType> confirmationMessage(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO,ButtonType.CANCEL);
        alert.setHeaderText(title);

        return alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //update the closing window event to show a confirmation dialog
        CentroApplication.getMainStage().setOnCloseRequest(event ->
        {
            if (dirtyFile)
            {
                Optional<ButtonType> result = confirmationMessage("Fechar", "Quer guardar o ficheiro antes de sair?");

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