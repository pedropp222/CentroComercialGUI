package com.example.centrocomercialgui.controller;

import com.example.centrocomercialgui.model.loja.CentroComercial;
import com.example.centrocomercialgui.model.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

public class NovoCentroController
{
    public TextField txtNome;
    public TextField txtMorada;

    private CentroController centroController;

    public void setCentroController(CentroController centroController)
    {
        this.centroController = centroController;
    }

    public void criarAction(ActionEvent actionEvent)
    {
        if (txtNome.getText().isEmpty() || txtMorada.getText().isEmpty())
        {
            GUIUtils.errorMessage("Erro", "Preencha todos os campos");
        }
        else
        {
            CentroComercial c = new CentroComercial(txtNome.getText(), txtMorada.getText());
            centroController.criarCentroComercial(c);
            cancelarAction(null);
        }
    }

    public void cancelarAction(ActionEvent actionEvent)
    {
        txtNome.getScene().getWindow().fireEvent(new WindowEvent(txtNome.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
