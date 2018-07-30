/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.FuncionarioDAO;
import br.com.cl.controle_logistica.classes.Funcionario;
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
public class ConfirmacaoAcaoColaboradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    //=================Atributos=======================
    
    @FXML
    private Label labelIdFuncionario;

    @FXML
    private Label labelNomeFuncionario;

    @FXML
    private Button botaoSim;

    @FXML
    private Button botaoNao;

    Funcionario funcionario = new Funcionario();
    
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
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if(this.funcionario != null){
            if (funcionarioDAO.deletarFuncionario(funcionario)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Funcionário");
                confirmacao.setHeaderText("Funcionário Excluído com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de Colaborador.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto funcionário da tela Colaborador.
     * @param funcionario 
     */
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do funcionário nos labels nome e id.
     */
    private void popularCampos(){
        labelIdFuncionario.setText(String.valueOf(funcionario.getIdFuncionario()));
        labelNomeFuncionario.setText(funcionario.getNome());
    }
}
