/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ManutencaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtPlaca;
    
    @FXML
    private Button btnBuscarVeiculo;
    
    @FXML
    private Label modeloManutencao;
    
    @FXML
    private Label anoManutencao;
    
    @FXML
    private Label placaCarretaManutencao;
    
    @FXML
    private Label kmManutencao;
    
    @FXML
    private Label dataUltimaManutencao;
    
    @FXML
    private ComboBox comboBoxTipoManutencao;
    
    @FXML
    private TextField valorManutencao;
    
    @FXML
    private DatePicker dataManutencao;
    
    @FXML
    private TextArea observacaoManutencao;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnDeletarManutencao;
    
    @FXML
    private Button btnSalvar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        
        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletarManutencao.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarVeiculo.setGraphic(new ImageView(search));
    }    
    
}
