package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.loja.FileManager;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class ExportLojasController
{
    public ToggleButton toggleAncoraPropria;
    public ToggleButton toggleAncoraExterna;
    public ToggleButton toggleQuiosque;
    public ToggleButton toggleRestauracao;
    public Label txtCurrentFolder;
    public TextField txtFilename;
    public ToggleGroup formatoGroup;

    private File currentFolder;

    private CentroController currentCentro;

    public void saveFiles(ActionEvent actionEvent)
    {
        try
        {

            RadioButton sel = (RadioButton) formatoGroup.getSelectedToggle();

            if (sel.getText().contains(FileManager.TXT_FORMAT))
            {

                FileManager.saveLojas(currentFolder, txtFilename.getText(),
                        currentCentro.getCurrentCentro(),
                        toggleAncoraExterna.isSelected(),
                        toggleAncoraPropria.isSelected(),
                        toggleQuiosque.isSelected(),
                        toggleRestauracao.isSelected());
            }
            else
            {
                FileManager.saveLojasBinary(currentFolder, txtFilename.getText(),
                        currentCentro.getCurrentCentro(),
                        toggleAncoraExterna.isSelected(),
                        toggleAncoraPropria.isSelected(),
                        toggleQuiosque.isSelected(),
                        toggleRestauracao.isSelected());
            }

            GUIUtils.informationMessage("Sucesso","Operacao concluida com sucesso!");
        }
        catch (IOException e)
        {
            GUIUtils.errorMessage("Erro ao gravar","Ocorreu um problema ao gravar. "+e.getMessage());
        }
    }

    public void closeWindow(ActionEvent actionEvent)
    {
        Stage stage = (Stage) toggleQuiosque.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void defineSaveFolder(ActionEvent actionEvent)
    {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Definir pasta para gravacao dos ficheiros...");

        currentFolder = dc.showDialog(toggleQuiosque.getScene().getWindow());

        if (currentFolder == null)
        {
            txtCurrentFolder.setText("Nenhuma pasta definida...");
        }
        else
        {
            txtCurrentFolder.setText(currentFolder.getAbsolutePath());
        }
    }

    public void initialize(CentroController centroController)
    {
        this.currentCentro = centroController;
    }
}
