/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.CargoDAO;
import br.com.cl.controle_logistica.classes.Cargo;
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
public class ConfirmacaoAcaoCargoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //=====================Atributos====================
    @FXML
    private Label labelIdCargo;

    @FXML
    private Label labelDescricaoCargo;

    @FXML
    private Button botaoSim;

    @FXML
    private Button botaoNao;

    Cargo cargo = new Cargo();

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
    private void confirmarAcao(ActionEvent action) {
        CargoDAO cargoDAO = new CargoDAO();
        if (cargo != null) {
            if (cargoDAO.deletarCargo(cargo)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Deletar Cargo");
                confirmacao.setHeaderText("Cargo Excluído com Sucesso!");
                confirmacao.showAndWait();
                Stage stage = (Stage) botaoSim.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Método que cancela a ação e volta a tela de Cargos
     *
     * @param action
     */
    @FXML
    private void cancelarAcao(ActionEvent action) {
        Stage stage = (Stage) botaoNao.getScene().getWindow();
        stage.close();
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
        popularCampos();
    }

    /**
     * Método que popula os labels informando a conta que será deletada.
     */
    private void popularCampos() {
        labelIdCargo.setText(String.valueOf(cargo.getIdCargo()));
        labelDescricaoCargo.setText(cargo.getDescricao());
    }
}
