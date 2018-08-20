/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.CargoDAO;
import br.com.cl.controle_logistica.classes.Cargo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class CargoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnBuscarCargo;

    @FXML
    private TextField txtBuscaDescricao;

    @FXML
    private TextField txtDescricaoCargo;

    Cargo cargo = new Cargo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));

        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletar.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarCargo.setGraphic(new ImageView(search));

    }

    /**
     * Metodo que através da descrição busca e lista os cargos cadastrados ou
     * oferece ao usuário a opção de inserção de um novo cargo.
     *
     * @param action
     */
    @FXML
    protected void buscarCargo(ActionEvent action) throws IOException, ClassNotFoundException {
        String descricaoInformada = txtBuscaDescricao.getText();

            CargoDAO cargoDAO = new CargoDAO();
            ArrayList<Cargo> cargos = new ArrayList<Cargo>();
            cargos = cargoDAO.buscarCargoPelaDescricao(descricaoInformada);

            mostrarCargos(cargos);
        
    }

    /**
     * Método que chama o Stage onde está a tabela que lista todos os cargos com
     * a descrição informada pelo usuário.
     *
     * @param cargos
     * @throws IOException
     */
    protected void mostrarCargos(ArrayList<Cargo> cargos) throws IOException {
        if (!cargos.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaCargos.fxml"));
            Parent root = (Parent) loader.load();
            TabelaCargosController tabelaCargosController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaCargosController.setCargos(cargos);
            stage.showAndWait();
            this.cargo = tabelaCargosController.getCargoSelecionado();
            if (this.cargo != null) {
                popularCampos();
            }

        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Cargo");
            confirmacao.setHeaderText("Nenhum cargo com essa descrição.\nPor favor, refaça sua pesquisa!");
            confirmacao.showAndWait();
        }
    }

    /**
     * Método que limpa o(s) campo(s) na tela de Cargos
     */
    private void limparCampos() {
        txtBuscaDescricao.setText("");
        txtDescricaoCargo.setText("");
    }

    /**
     * Método que coleta todos os dados no campos da tela Cargo e seta no objeto
     * cargo.
     */
    private void coletarDadosCamposCargo() {
        cargo.setDescricao(txtDescricaoCargo.getText().toUpperCase());

    }

    /**
     * Método utilizado pelo botão salvar. Salva um novo ou altera um cargo no banco de dados.
     *
     * @param action
     */
    @FXML
    protected void salvarCargo(ActionEvent action) {
        CargoDAO cargoDAO = new CargoDAO();

        if (this.cargo == null) {
            this.cargo = new Cargo();
        }

        if (cargo.getIdCargo() == 0) {
            coletarDadosCamposCargo();
            cargoDAO.salvarCargo(cargo);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Cargo");
            confirmacao.setHeaderText("Cargo Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else {
            coletarDadosCamposCargo();
            if (cargoDAO.atualizarCargo(cargo)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Cargo");
                confirmacao.setHeaderText("Cargo Atualizado com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
    }

    /**
     * Metodo utilizado para setar o cargo selecionado na tabela no cargo dessa classe.
     * @param cargo 
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;

    }

    /**
     * 
     * Método que seta nos campos da tela Cargo o cargo selecionado na tabela.
     */
    private void popularCampos() {
        txtDescricaoCargo.setText(cargo.getDescricao());

    }
    
    /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão do cargo selecionado.
     * @param action 
     */
    @FXML
    private void deletarCargo(ActionEvent action) throws IOException{
        if(cargo.getIdCargo() > 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoCargo.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoCargoController confirmacaoAcaoCargoController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoCargoController.setCargo(cargo);
            stage.showAndWait();
            limparCampos();
        }
    }
    
    /**
     * Método utilizado pelo botão cancelar. Limpa todos os campos e retorna a tela inicial.
     */
    @FXML
    private void cancelarOperacao(ActionEvent action){
        limparCampos();
    }
}
