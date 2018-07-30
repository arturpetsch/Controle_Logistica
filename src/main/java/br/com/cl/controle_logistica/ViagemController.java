/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import com.sun.prism.PhongMaterial.MapType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ViagemController implements Initializable{

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtBuscaVeiculo;
    
    @FXML
    private Button btnBuscarVeiculo;
    
    @FXML
    private Button btnIncluirCustoPrevisto;
    
    @FXML
    private Button btnIncluirCustoRealizado;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnDeletar;
    
    @FXML
    private Button btnSalvar;
    
    @FXML
    private WebView webViewMapa  = new WebView();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        Image plus = new Image(getClass().getResourceAsStream("/Imagens/plus.png"));
        
        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletar.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarVeiculo.setGraphic(new ImageView(search));
        btnIncluirCustoPrevisto.setGraphic(new ImageView(plus));
        btnIncluirCustoRealizado.setGraphic(new ImageView(plus));
        
       
    }    

    
}
