package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.loja.*;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

public class LojaPropriedadesController
{
    @javafx.fxml.FXML
    private TextField txtRestauracaoRenda;
    @javafx.fxml.FXML
    private TextField txtAreaGrande;
    @javafx.fxml.FXML
    private TextField txtQuiosqueRenda;
    @javafx.fxml.FXML
    private TextField txtAncoraExternaRenda;
    @javafx.fxml.FXML
    private TextField txtRestauracaoMesa;
    @javafx.fxml.FXML
    private TextField txtAreaPequeno;

    private CentroController centro;

    @javafx.fxml.FXML
    public void okAction(ActionEvent actionEvent)
    {
        cancelAction(null);
    }

    @javafx.fxml.FXML
    public void applyAction(ActionEvent actionEvent)
    {
        if (validateValues())
        {
            Loja.setAreaPequeno(Integer.parseInt(txtAreaPequeno.getText()));
            Loja.setAreaGrande(Integer.parseInt(txtAreaGrande.getText()));
            AncoraExterna.setRendaFixa(Float.parseFloat(txtAncoraExternaRenda.getText()));
            LojaQuiosque.setRenda(Float.parseFloat(txtQuiosqueRenda.getText()));
            LojaRestauracao.setRendaFixa(Float.parseFloat(txtRestauracaoRenda.getText()));
            LojaRestauracao.setValorPorMesa(Float.parseFloat(txtRestauracaoMesa.getText()));

            GUIUtils.informationMessage("Operacao concluida.","Valores foram aplicados com sucesso.");
        }
    }

    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent)
    {
        centro.editPropriedadesFinished();
        txtAreaPequeno.getScene().getWindow().fireEvent(new WindowEvent(txtAreaPequeno.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void initialize(CentroController centro)
    {
        this.centro = centro;
        txtAreaPequeno.setText(String.valueOf(Loja.getAreaPequeno()));
        txtAreaGrande.setText(String.valueOf(Loja.getAreaGrande()));

        txtAncoraExternaRenda.setText(String.valueOf(AncoraExterna.getRendaFixa()));
        txtQuiosqueRenda.setText(String.valueOf(LojaQuiosque.getRenda()));
        txtRestauracaoRenda.setText(String.valueOf(LojaRestauracao.getRendaFixa()));
        txtRestauracaoMesa.setText(String.valueOf(LojaRestauracao.getValorPorMesa()));
    }

    public boolean validateValues()
    {
        try
        {
            Integer.parseInt(txtAreaPequeno.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Area Loja Pequena!");
            return false;
        }

        try
        {
            Integer.parseInt(txtAreaGrande.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Area Loja Grande!");
            return false;
        }

        try
        {
            Float.parseFloat(txtAncoraExternaRenda.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Renda Ancora Externa!");
            return false;
        }

        try
        {
            Float.parseFloat(txtQuiosqueRenda.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Renda Quiosque!");
            return false;
        }

        try
        {
            Float.parseFloat(txtRestauracaoRenda.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Renda Restauracao!");
            return false;
        }

        try
        {
            Float.parseFloat(txtRestauracaoMesa.getText());
        }
        catch (NumberFormatException e)
        {
            GUIUtils.errorMessage("Erro a validar campo.","Valor numerico invalido no campo Restauracao Custo Seguranca Por Mesa!");
            return false;
        }

        return true;
    }

}
