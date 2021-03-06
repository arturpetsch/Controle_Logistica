/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.ManutencaoDAO;
import br.com.cl.controle_logistica.DAO.ViagemDAO;
import br.com.cl.controle_logistica.classes.Manutencao;
import br.com.cl.controle_logistica.classes.Viagem;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Pane paneHome;
    
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
    
    @FXML
    private Button botaoHome;
    
    @FXML
    private Label lblValorGanhoRealizado;
    
    @FXML
    private Label lblValorGastoRealizado;
    
    @FXML
    private Label lblValorTotalRealizado;
    
    @FXML
    private Label lblValorGanhoPrevisto;
    
    @FXML
    private Label lblValorGastoPrevisto;
    
    @FXML
    private Label lblValorTotalPrevisto;
    
    @FXML
    private Label lblPlacaVeiculoCorretiva;
    
    @FXML
    private Label lblDataCorretiva;
    
    @FXML
    private Label lblValorCorretiva;
    
    @FXML
    private Label lblPlacaVeiculo;
    
    @FXML
    private Label lblData;
    
    @FXML
    private Label lblValor;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         Image icone = new Image(getClass().getResourceAsStream("/Imagens/truck.png"));
         botaoHome.setGraphic(new ImageView(icone));
         
         popularDashBoardValorViagemRealizado();
         popularDashBoardValorViagemPrevisto();
         popularDashBoardUltimaManutencaoCorretiva();
         popularDashBoardUltimaManutencaoPreventiva();
    }    

 
    /**
     * Metodo que popula os labels referentes ao dashboard de viagem realizado.
     */
    private void popularDashBoardValorViagemRealizado(){
        ViagemDAO viagemDAO = new ViagemDAO();
        BigDecimal valorTotalGasto;
        BigDecimal valorTotalGanho = new BigDecimal(0);
        BigDecimal valorTotal = new BigDecimal(0);
        
        ArrayList<Viagem> viagens = viagemDAO.buscarValorTotalRealizadosUltimoAno();
       
        valorTotalGasto = viagens.stream()
               .map(Viagem::getValorTotalGastoRealizado)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
       
        valorTotalGanho = viagens.stream()
               .map(Viagem::getValorTotalGanhoRealizado)
              .reduce(BigDecimal.ZERO, BigDecimal::add);       
      
       lblValorGanhoRealizado.setText("R$" + valorTotalGanho.toString());
      lblValorGastoRealizado.setText("R$" + valorTotalGasto.toString());
        
        valorTotal = valorTotalGanho.subtract(valorTotalGasto);
        
        lblValorTotalRealizado.setText("R$" + valorTotal.toString());
    }
    
    /**
     * Metodo que popula os labels referentes ao dashboard de viagens previstas.
     */
    private void popularDashBoardValorViagemPrevisto(){
        ViagemDAO viagemDAO = new ViagemDAO();
        BigDecimal valorTotalGasto;
        BigDecimal valorTotalGanho = new BigDecimal(0);
        BigDecimal valorTotal = new BigDecimal(0);
        
        ArrayList<Viagem> viagens = viagemDAO.buscarValorTotalRealizadosUltimoAno();
       
        valorTotalGasto = viagens.stream()
               .map(Viagem::getValorTotalGastoPrevisto)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
       
        valorTotalGanho = viagens.stream()
               .map(Viagem::getValorTotalGanhoPrevisto)
              .reduce(BigDecimal.ZERO, BigDecimal::add);       
      
       lblValorGanhoPrevisto.setText("R$" + valorTotalGanho.toString());
      lblValorGastoPrevisto.setText("R$" + valorTotalGasto.toString());
        
        valorTotal = valorTotalGanho.subtract(valorTotalGasto);
        
        lblValorTotalPrevisto.setText("R$" + valorTotal.toString());
    }
    
    
    private void popularDashBoardUltimaManutencaoCorretiva(){
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
        String tipoManutencao = "Corretiva";
        Manutencao manutencao = manutencaoDAO.buscarUltimaManutencao(tipoManutencao);
        
        lblPlacaVeiculoCorretiva.setText(manutencao.getVeiculo().getPlaca().toUpperCase());
        lblDataCorretiva.setText(manutencao.getDataManutencao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
        lblValorCorretiva.setText("R$" + manutencao.getValor().toString());
    }
    
    private void popularDashBoardUltimaManutencaoPreventiva(){
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
        String tipoManutencao = "Preventiva";
        Manutencao manutencao = manutencaoDAO.buscarUltimaManutencao(tipoManutencao);
        
        lblPlacaVeiculo.setText(manutencao.getVeiculo().getPlaca().toUpperCase());
        lblData.setText(manutencao.getDataManutencao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
        lblValor.setText("R$" + manutencao.getValor().toString());
    }
    
     /**
     * Seta no painel direito o FXML referente a tela home do software.
     * @param action
     * @throws IOException 
     */
    @FXML
    public void telaPrincipal(ActionEvent action) throws IOException{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/telaPrincipal.fxml"));
        AnchorPane pane = (AnchorPane) fXMLLoader.load();
        paneHome.getChildren().setAll(pane);
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

