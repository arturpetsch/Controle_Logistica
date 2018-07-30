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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ConhecimentoFreteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button btnBuscarNumeroFrete;
    
    @FXML
    private Button btnSalvarFrete;
    
    @FXML
    private Button btnDeletaFrete;
    
    @FXML
    private Button btnCancelarFrete;
    
    @FXML
    private Button btnInserirNf;
    
    @FXML
    private Button btnBuscarDestinatario;
    
    @FXML
    private Button btnBuscarRemetente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        Image plus = new Image(getClass().getResourceAsStream("/Imagens/plus.png"));
        
        btnCancelarFrete.setGraphic(new ImageView(cancel));
        btnDeletaFrete.setGraphic(new ImageView(trash));
        btnSalvarFrete.setGraphic(new ImageView(save));
        btnBuscarDestinatario.setGraphic(new ImageView(search));
        btnInserirNf.setGraphic(new ImageView(plus));
        btnBuscarNumeroFrete.setGraphic(new ImageView(search));
        btnBuscarRemetente.setGraphic(new ImageView(search));
    }    
    
}
