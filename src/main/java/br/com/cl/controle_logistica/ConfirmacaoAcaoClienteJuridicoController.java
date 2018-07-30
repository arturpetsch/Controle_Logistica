/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.ClienteJuridicoDAO;
import br.com.cl.controle_logistica.classes.ClienteJuridico;
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
public class ConfirmacaoAcaoClienteJuridicoController implements Initializable {

   //=================Atributos=======================
    
    @FXML
    private Label labelIdCliente;

    @FXML
    private Label labelNomeCliente;

    @FXML
    private Button botaoSim;

    @FXML
    private Button botaoNao;

    ClienteJuridico clienteJuridico = new ClienteJuridico();
    
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
        ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
        if(this.clienteJuridico != null){
            if (clienteJuridicoDAO.deletarCliente(clienteJuridico)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Cliente");
                confirmacao.setHeaderText("Cliente Excluído com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    } 
    
     /**
     * Método que cancela a ação e volta a tela de Cliente.
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que recebe o objeto cliente da tela Cliente.
     * @param cliente 
     */
    public void setCliente(ClienteJuridico clienteJuridico){
        this.clienteJuridico = clienteJuridico;
        popularCampos();
    }
    
    /**
     * Método que insere os dados do cliente nos labels nome e id.
     */
    private void popularCampos(){
        labelIdCliente.setText(String.valueOf(clienteJuridico.getIdClienteJuridico()));
        labelNomeCliente.setText(clienteJuridico.getNomeFantasia());
    }
}

