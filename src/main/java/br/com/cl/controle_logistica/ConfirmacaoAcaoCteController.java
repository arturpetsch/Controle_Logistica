/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.CteDAO;
import br.com.cl.controle_logistica.classes.Cte;
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
public class ConfirmacaoAcaoCteController implements Initializable {

 @FXML
 private Label labelNumeroCte;
 
@FXML
private Label labelValorCte;

@FXML
private Button botaoSim;

@FXML
private Button botaoNao;

Cte cte = new Cte();

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
        CteDAO cteDAO = new CteDAO();
        if(this.cte != null){
            if (cteDAO.deletarCTe(cte)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Cte");
                confirmacao.setHeaderText("Cte Excluído com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de CTe.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto cte da tela Fretes.
     * @param cte
     */
    public void setCte(Cte cte){
        this.cte = cte;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do cte nos labels valor e numero.
     */
    private void popularCampos(){
        labelNumeroCte.setText(String.valueOf(cte.getNumeroCte()));
        labelValorCte.setText(cte.getValor().toString());
    }
}


