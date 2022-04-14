package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.CentroApplication;
import com.example.centrocomercialgui.model.loja.CentroComercial;
import com.example.centrocomercialgui.model.loja.FileManager;
import com.example.centrocomercialgui.model.loja.Loja;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CentroController
{

    public StackPane treeStack;
    public Label txtCentroName;
    public VBox lojaBox;
    public Label txtLojaPropriedades;
    public MenuItem saveItem;
    public MenuItem saveAsItem;
    public HBox buttonBox;

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

                updateTree();

                buttonBox.setDisable(false);

                saveItem.setDisable(false);
                saveAsItem.setDisable(false);

                updateWindowTitle();
            } catch (FileNotFoundException e)
            {
                System.out.println("Erro a abrir loja?: " + e.getMessage());
            }
        }
    }

    public void sairAction(ActionEvent actionEvent)
    {
        //close this window and exit the application
        CentroApplication.getMainStage().close();

    }

    private void updateTree()
    {
        TreeItem<String> root = new TreeItem<>("Listagem de Lojas");
        root.setExpanded(true);
        TreeView<String> lojasTree = new TreeView(root);

        for (int i = 0; i < currentCentro.getTotalLojas(); i++)
        {
            root.getChildren().add(new TreeItem<String>(currentCentro.obterLoja(i).toString()));
        }


        //call method selectLojaTree when lojasTree selected item changes
        lojasTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectLojaTree(lojasTree));

        treeStack.getChildren().clear();
        treeStack.getChildren().add(lojasTree);
    }

    //check if the user clicked on a loja item and if so, enable lojaBox
    private void selectLojaTree(TreeView<String> lojasTree)
    {
        TreeItem<String> selected = lojasTree.getSelectionModel().getSelectedItem();

        int index = lojasTree.getSelectionModel().getSelectedIndex();

        if (selected != null && selected.getValue() != null && index != 0)
        {
            lojaBox.setDisable(false);

            Loja j = currentCentro.obterLoja(index - 1);

            txtLojaPropriedades.setText("Propriedades de: " + j.getNome());
        } else
        {
            lojaBox.setDisable(true);
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
            stage.setTitle("Nova Loja");
            stage.setScene(new Scene(root));
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
        updateTree();
        dirtyFile = true;
        updateWindowTitle();
    }
}