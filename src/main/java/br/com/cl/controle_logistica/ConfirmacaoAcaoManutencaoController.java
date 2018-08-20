/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.ManutencaoDAO;
import br.com.cl.controle_logistica.classes.Manutencao;
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
public class ConfirmacaoAcaoManutencaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label labelData;
    
    @FXML
    private Label labelValor;
    
    @FXML
    private Button botaoNao;
    
    @FXML
    private Button botaoSim;
    
    Manutencao manutencao = new Manutencao();
    
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
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
        if(this.manutencao != null){
            if (manutencaoDAO.deletarManutencao(manutencao)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Manutenção");
                confirmacao.setHeaderText("Manutenção Excluída com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de Manutenção.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto manutencao da tela Manutencao.
     * @param manutencao
     */
    public void setManutencao(Manutencao manutencao){
        this.manutencao = manutencao;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do funcionário nos labels nome e id.
     */
    private void popularCampos(){
        labelData.setText(manutencao.getDataManutencao().toString());
        labelValor.setText(manutencao.getValor().toEngineeringString());
    }
}
