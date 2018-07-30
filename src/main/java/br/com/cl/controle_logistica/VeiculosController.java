/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.FuncionarioDAO;
import br.com.cl.controle_logistica.DAO.VeiculoDAO;
import br.com.cl.controle_logistica.classes.Funcionario;
import br.com.cl.controle_logistica.classes.Veiculo;
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
public class VeiculosController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnDeletarVeiculo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnBuscarPlaca;

    @FXML
    private Button btnBuscarFuncionario;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPesoTara;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtPlacaCarreta;

    @FXML
    private TextField txtKM;

    @FXML
    private TextField txtMediaKmLitro;

    @FXML
    private TextField txtPlacaVeiculo;
    
    @FXML
    private TextField txtFuncionario;

    Veiculo veiculo = new Veiculo();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));

        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletarVeiculo.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarPlaca.setGraphic(new ImageView(search));
        btnBuscarFuncionario.setGraphic(new ImageView(search));
        
    }

    /**
     * Metodo que através da placa busca e lista o veiculo cadastrado ou oferece
     * ao usuário a opção de inserção de um novo veículo.
     *
     * @param action
     */
    @FXML
    protected void buscarPlaca(ActionEvent action) throws IOException {
        String placaInformada = txtPlaca.getText();

        
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        veiculos = veiculoDAO.consultarVeiculoPorPlaca(placaInformada);

        mostrarVeiculos(veiculos);
    }
    
     /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * veiculos com a placa informada pelo usuário.
     *
     * @param veiculos
     * @throws IOException
     */
    private void mostrarVeiculos(ArrayList<Veiculo> veiculos) throws IOException {
        if (!veiculos.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaVeiculos.fxml"));
            Parent root = (Parent) loader.load();
            TabelaVeiculosController tabelaVeiculosController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaVeiculosController.setVeiculo(veiculos);
            stage.showAndWait();
            this.veiculo = tabelaVeiculosController.getVeiculoSelecionado();
            if (this.veiculo != null) {
                popularCamposVeiculo();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Veiculo");
            confirmacao.setHeaderText("Nenhum veiculo encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo veiculo!");
            confirmacao.showAndWait();
        }
    }
    
     /**
     * Método que limpa o(s) campo(s) na tela de Veiculo.
     */
    private void limparCampos() {
        txtModelo.setText("");
        txtAno.setText("");
        txtFuncionario.setText("");
        txtKM.setText("");
        txtMediaKmLitro.setText("");
        txtPesoTara.setText("");
        txtPlaca.setText("");
        txtPlacaCarreta.setText("");
        txtPlacaVeiculo.setText("");
    }
    
     /**
     * Método utilizado pelo botao salvar. Salva um novo veiculo ou as
     * alterações feitas em um já existente.
     *
     * @param action
     */
    @FXML
    protected void salvarVeiculo(ActionEvent action) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();

        if (this.veiculo == null) {
            this.veiculo = new Veiculo();
        }

        if (veiculo.getIdVeiculo()== 0 && getAtributosVeiculo()) {
            veiculoDAO.salvarVeiculo(veiculo);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Veiculo");
            confirmacao.setHeaderText("Veiculo Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (getAtributosVeiculo()&& veiculo.getIdVeiculo()> 0) {
            if (veiculoDAO.atualizarVeiculo(veiculo)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Veiculo");
                confirmacao.setHeaderText("Veiculo Atualizado com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
        
    }
    
    /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão do veiculo selecionado.
     * @param action 
     */
    @FXML
    private void deletarVeiculo(ActionEvent action) throws IOException{
        if(this.veiculo.getIdVeiculo() > 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoVeiculo.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoVeiculoController confirmacaoAcaoVeiculoController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoVeiculoController.setVeiculo(veiculo);
            stage.showAndWait();
            limparCampos();
        }
    }
    
    /**
     * Método que limpa todos os campos e cancela as operações.
     * @param action 
     */
    @FXML
    private void cancelarOperacao(ActionEvent action){
        limparCampos();
    }
    
     /**
     * Metodo que através do nome busca e lista os funcionários cadastrados. 
     *
     * @param action
     */
    @FXML
    protected void buscarPeloNome(ActionEvent action) throws IOException {
        String nomeInformado = txtFuncionario.getText();
        
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
            funcionarios = funcionarioDAO.consultarColaboradorPorNome(nomeInformado);

            mostrarFuncionarios(funcionarios);
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * funcionários com o nome informado pelo usuário.
     *
     * @param funcionarios
     * @throws IOException
     */
    private void mostrarFuncionarios(ArrayList<Funcionario> funcionarios) throws IOException {
        if (!funcionarios.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaColaborador.fxml"));
            Parent root = (Parent) loader.load();
            TabelaColaboradorController tabelaColaboradorController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaColaboradorController.setFuncionarios(funcionarios);
            stage.showAndWait();
            this.veiculo.setFuncionario(tabelaColaboradorController.getFuncionarioSelecionado());
            if (this.veiculo.getFuncionario() != null) {
                txtFuncionario.setText(veiculo.getFuncionario().getNome().toUpperCase());
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Funcionário");
            confirmacao.setHeaderText("Nenhum funcionário encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo funcionário!");
            confirmacao.showAndWait();
        }
    }
    
      /**
     * Método que coleta os dados de todos os campos da tela e seta no objeto
     * veiculo.
     */
    private boolean getAtributosVeiculo() {
        if (verificarCamposVazios()) {
            
            veiculo.setModeloVeiculo(txtModelo.getText().toUpperCase());
            veiculo.setPesoTara(Double.parseDouble(txtPesoTara.getText().toUpperCase()));
            veiculo.setAnoVeiculo(txtAno.getText().toUpperCase());
            veiculo.setKm(Double.parseDouble(txtKM.getText()));
            veiculo.setMediaKmLitro(Double.parseDouble(txtMediaKmLitro.getText().toUpperCase()));
            veiculo.setPlaca(txtPlacaVeiculo.getText().toUpperCase());
            if (!txtPlacaCarreta.getText().isEmpty()) {
                veiculo.setPlacaCarreta((txtPlacaCarreta.getText().toUpperCase()));
            }else{
                veiculo.setPlacaCarreta("");
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Método que valida se os campos estão vazios.
     */
    private boolean verificarCamposVazios() {
        boolean retorno = true;
        int contador = 0;
        if (txtModelo.getText().isEmpty()) {
            txtModelo.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtModelo.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
    
         if (txtPesoTara.getText().isEmpty()) {
            txtPesoTara.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtPesoTara.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (txtPlacaVeiculo.getText().isEmpty()) {
            txtPlacaVeiculo.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtPlacaVeiculo.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (txtAno.getText().isEmpty()) {
            txtAno.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtAno.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
          
          if (txtKM.getText().isEmpty()) {
            txtKM.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtKM.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
           if (txtMediaKmLitro.getText().isEmpty()) {
            txtMediaKmLitro.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtMediaKmLitro.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
          if (txtFuncionario.getText().isEmpty()) {
            txtFuncionario.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtFuncionario.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }  
           
         if(contador<=-7){
            retorno = true;
         }else{
             retorno = false;
         } 
         
         if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Veiculo");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }
    }
     /**
     * Método que popula os campos da tela com as informações do veiculo
     * selecionado.
     */
    @FXML
    private void popularCamposVeiculo() {
        
        txtModelo.setText(veiculo.getModeloVeiculo().toUpperCase());
        txtPesoTara.setText(veiculo.getPesoTara().toString());
        txtAno.setText(veiculo.getAnoVeiculo());
        txtPlacaVeiculo.setText(veiculo.getPlaca());
        
        if(veiculo.getPlacaCarreta() == null){
            txtPlacaCarreta.setText("");
        }else{
            txtPlacaCarreta.setText(veiculo.getPlacaCarreta());
        }
        txtKM.setText(String.valueOf(veiculo.getKm()));
        txtMediaKmLitro.setText(veiculo.getMediaKmLitro().toString());
        txtFuncionario.setText(veiculo.getFuncionario().getNome().toUpperCase());
    }
    
    /**
     * Metodo utilizado para setar o veiculo selecionado na tabela no
     * veiculo dessa classe.
     *
     * @param veiculo
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;

    }
}
