package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.CentroApplication;
import com.example.centrocomercialgui.model.loja.CentroComercial;
import com.example.centrocomercialgui.model.loja.FileManager;
import com.example.centrocomercialgui.model.loja.Loja;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

public class CentroController
{

    public StackPane treeStack;
    public Label txtCentroName;
    public VBox lojaBox;
    public Label txtLojaPropriedades;
    private CentroComercial currentCentro;

    public void abrirCentroAction(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Abrir Centro Comercial");
        File selected = fc.showOpenDialog(CentroApplication.getMainStage());

        if(selected != null)
        {
            try
            {
                currentCentro = FileManager.loadFile(selected.getAbsolutePath());

                txtCentroName.setText("Centro Comercial: "+currentCentro.getNome()+" | "+currentCentro.getMorada());

                updateTree();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Erro a abrir loja?: "+e.getMessage());
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

        treeStack.getChildren().add(lojasTree);
    }

    //check if the user clicked on a loja item and if so, enable lojaBox
    private void selectLojaTree(TreeView<String> lojasTree)
    {
        TreeItem<String> selected = lojasTree.getSelectionModel().getSelectedItem();

        int index = lojasTree.getSelectionModel().getSelectedIndex();

        if(selected != null && selected.getValue() != null && index != 0)
        {
            lojaBox.setDisable(false);

            Loja j = currentCentro.obterLoja(index-1);

            txtLojaPropriedades.setText("Propriedades de: "+j.getNome());
        }
        else
        {
            lojaBox.setDisable(true);
        }
    }
}