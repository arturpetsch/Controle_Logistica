/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.ViagemDAO;
import br.com.cl.controle_logistica.classes.Viagem;
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
public class ConfirmacaoAcaoViagemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label labelIdViagem;

    @FXML
    private Label labelPlacaVeiculo;

    @FXML
    private Button botaoSim;

    @FXML
    private Button botaoNao;
    
    Viagem viagem = new Viagem();
    
    
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
       ViagemDAO viagemDAO = new ViagemDAO();
        if(this.viagem != null){
            if (viagemDAO.deletarViagem(viagem)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Viagem");
                confirmacao.setHeaderText("Viagem Excluída com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de Viagem.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto viagem da tela Viagem.
     * @param viagem
     */
    public void setViagem(Viagem viagem){
        this.viagem = viagem;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do veiculo nos labels modelo e placa.
     */
    private void popularCampos(){
        labelPlacaVeiculo.setText((viagem.getVeiculo().getPlaca()));
        labelIdViagem.setText(String.valueOf(viagem.getIdViagem()));
    }
}


