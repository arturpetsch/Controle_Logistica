/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.VeiculoDAO;
import br.com.cl.controle_logistica.classes.Veiculo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ConfirmacaoAcaoVeiculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    //=================Atributos=======================
    
    @FXML
    private Label labelPlacaVeiculo;

    @FXML
    private Label labelModeloVeiculo;

    @FXML
    private Button botaoSim;

    @FXML
    private Button botaoNao;
    
    Veiculo veiculo = new Veiculo();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
     /**
     * Método que confirma a ação selecionada.
     *
     * @param action
     */
    @FXML
    private void confirmarAcao(ActionEvent action){
       VeiculoDAO veiculoDAO = new VeiculoDAO();
        if(this.veiculo != null){
            if (veiculoDAO.deletarVeiculo(veiculo)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Veículo");
                confirmacao.setHeaderText("Veículo Excluído com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de Veículo.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto veiculo da tela Veículo.
     * @param veiculo
     */
    public void setVeiculo(Veiculo veiculo){
        this.veiculo = veiculo;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do veiculo nos labels modelo e placa.
     */
    private void popularCampos(){
        labelPlacaVeiculo.setText((veiculo.getPlaca()));
        labelModeloVeiculo.setText(veiculo.getModeloVeiculo());
    }
}

