/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class TelaPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane paneDireitoPrincipal;
    
    @FXML
    private ImageView logoTelaPrincipal;
    
    @FXML
    private MenuButton colaboradores;
    
    @FXML
    private MenuItem btnColaborador;
    
    @FXML
    private MenuItem btnCargo;
    
    @FXML
    private MenuButton btnFrota;
    
    @FXML
    private MenuItem btnVeiculo;
    
    @FXML
    private MenuItem btnManutencao;
    
    @FXML
    private MenuButton btnClientes;
    
    @FXML
    private MenuItem btnClienteFisico;
    
    @FXML
    private MenuItem btnClienteJuridico;
    
    @FXML
    private MenuButton btnViagem;
    
    @FXML
    private MenuItem btnTelaViagem;
    
    @FXML
    private MenuItem btnFrete;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         Image icone = new Image(getClass().getResourceAsStream("/Imagens/truck.gif"));
         logoTelaPrincipal.setImage(icone);
        
    }    

    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de veículo.
     * @param action
     * @throws IOException 
     */
    @FXML
    public void telaVeiculo(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/veiculos.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /** 
     *  Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de colaborador.
     * @param action 
     */
    @FXML
    public void telaColaborador(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/colaborador.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de cliente fisico.
     */
    @FXML
    public void telaClienteFisico(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/clienteFisico.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de cliente juridico.
     */
    @FXML
    public void telaClienteJuridico(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/clienteJuridico.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de fretes.
     */
    @FXML
    public void telaFrete(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/conhecimentoFrete.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de manutenções.
     */
    @FXML
    public void telaManutencao(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/manutencao.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
    /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de cargos.
     */
    @FXML
    public void telaCargo(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/cargo.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
    
     /**
     * Seta no painel direito o FXML referente a tela de cadastro, consulta, alteração e remoção de viagem.
     */
    @FXML
    public void telaViagem(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/viagem.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneDireitoPrincipal.getChildren().setAll(pane);
    }
}

