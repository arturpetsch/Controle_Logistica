/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.ClienteJuridicoDAO;
import br.com.cl.controle_logistica.classes.ClienteJuridico;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ClienteJuridicoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnBuscarNomeClienteJuridico;
    
    @FXML
    private Button btnSalvarClienteJuridico;
    
    @FXML
    private Button btnDeletarClienteJuridico;
    
    @FXML
    private Button btnCancelarClienteJuridico;
    
    @FXML
    private TextField txtnome;
    
    @FXML
    private TextField txtNomeFantasiaClienteJuridico;
    
    @FXML
    private TextField txtRazaoSocialClienteJuridico;
    
    @FXML
    private TextField txtCnpjClienteJuridico;
    
    @FXML
    private TextField txtIEClienteJuridico;
    
    @FXML
    private TextField txtEnderecoClienteJuridico;

    @FXML
    private TextField txtBairroClienteJuridico;

    @FXML
    private TextField txtCEPClienteJuridico;

    @FXML
    private TextField txtCidadeClienteJuridico;

    @FXML
    private ComboBox comboBoxEstadoClienteJuridico;

    @FXML
    private TextField txtContatoClienteJuridico;

    @FXML
    private TextField txtContato1ClienteJuridico;

    @FXML
    private TextField txtEmailClienteJuridico;
    
    ClienteJuridico clienteJuridico = new ClienteJuridico();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        
        btnCancelarClienteJuridico.setGraphic(new ImageView(cancel));
        btnDeletarClienteJuridico.setGraphic(new ImageView(trash));
        btnSalvarClienteJuridico.setGraphic(new ImageView(save));
        btnBuscarNomeClienteJuridico.setGraphic(new ImageView(search));
        
        maskCnpj();
        maskTel9DigContato();
        maskTel9DigContato2();
        popularComboBoxEstados();
    }    
    
        /**
     * Metodo que através do nome busca e lista o cliente cadastrado ou
     * oferece ao usuário a opção de inserção de um novo cliente.
     *
     * @param action
     */
    @FXML
    protected void buscarPeloNome(ActionEvent action) throws IOException {
        String nomeInformado = txtnome.getText();
        
            ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
            ArrayList<ClienteJuridico> clientes = new ArrayList<ClienteJuridico>();
            clientes = clienteJuridicoDAO.consultarClientePorNome(nomeInformado);

            mostrarClientes(clientes);

       
    }
    
    /**
     * Método que limpa o(s) campo(s) na tela de Cliente Juridico.
     */
    private void limparCampos() {
        txtNomeFantasiaClienteJuridico.setText("");
        txtnome.setText("");
        txtCnpjClienteJuridico.setText("");
        txtIEClienteJuridico.setText("");
        txtEnderecoClienteJuridico.setText("");
        txtBairroClienteJuridico.setText("");
        txtCEPClienteJuridico.setText("");
        txtCidadeClienteJuridico.setText("");
        txtContatoClienteJuridico.setText("");
        txtContato1ClienteJuridico.setText("");
        txtEmailClienteJuridico.setText("");
        txtRazaoSocialClienteJuridico.setText("");
    }
    
       /**
     * Método utilizado pelo botao salvar. Salva um novo cliente ou as
     * alterações feitas em um já existente.
     *
     * @param action
     */
    @FXML
    protected void salvarCliente(ActionEvent action) {
         ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();

        if (this.clienteJuridico == null) {
            this.clienteJuridico = new ClienteJuridico();
        }

        if (clienteJuridico.getIdClienteJuridico()== 0 && getAtributosClienteJuridico()) {
            clienteJuridicoDAO.salvarCliente(clienteJuridico);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Cliente");
            confirmacao.setHeaderText("Cliente Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (getAtributosClienteJuridico()&& clienteJuridico.getIdClienteJuridico()> 0) {
            if (clienteJuridicoDAO.atualizarCliente(clienteJuridico)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Cliente");
                confirmacao.setHeaderText("Cliente Atualizado com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
        
    }
    
        /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão do cliente selecionado.
     * @param action 
     */
    @FXML
    private void deletarCliente(ActionEvent action) throws IOException{
        if(this.clienteJuridico.getIdClienteJuridico()> 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoClienteJuridico.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoClienteJuridicoController confirmacaoAcaoClienteJuridicoController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoClienteJuridicoController.setCliente(clienteJuridico);
            stage.showAndWait();
            limparCampos();
            clienteJuridico = new ClienteJuridico();
        }
    }
    
       /**
     * Método que limpa todos os campos e cancela as operações.
     * @param action 
     */
    @FXML
    private void cancelarOperacao(ActionEvent action){
        limparCampos();
        clienteJuridico = new ClienteJuridico();
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * clientes com o nome informado pelo usuário.
     *
     * @param clientes
     * @throws IOException
     */
    private void mostrarClientes(ArrayList<ClienteJuridico> clientesJuridicos) throws IOException {
        if (!clientesJuridicos.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaClientesJuridicos.fxml"));
            Parent root = (Parent) loader.load();
            TabelaClientesJuridicosController tabelaClientesJuridicosController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaClientesJuridicosController.setCliente(clientesJuridicos);
            stage.showAndWait();
            this.clienteJuridico = tabelaClientesJuridicosController.getClienteSelecionado();
            if (this.clienteJuridico != null) {
                popularCamposClienteJuridico();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Cliente");
            confirmacao.setHeaderText("Nenhum cliente encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo cliente!");
            confirmacao.showAndWait();
        }
    }
    
      /**
     * Método que coleta os dados de todos os campos da tela e seta no objeto
     * cliente juridico.
     */
    private boolean getAtributosClienteJuridico() {
        if (verificarCamposVazios()) {
            //Coletar todos os atributos e setar no cliente para poder salvar;
            clienteJuridico.setNomeFantasia(txtNomeFantasiaClienteJuridico.getText().toUpperCase());
            clienteJuridico.setRazaoSocial(txtRazaoSocialClienteJuridico.getText().toUpperCase());
            clienteJuridico.setCnpj(txtCnpjClienteJuridico.getText().toUpperCase());
            clienteJuridico.setIe(txtIEClienteJuridico.getText().toUpperCase());
            clienteJuridico.setEndereco(txtEnderecoClienteJuridico.getText().toUpperCase());
            clienteJuridico.setBairro(txtBairroClienteJuridico.getText().toUpperCase());
            clienteJuridico.setCep(txtCEPClienteJuridico.getText().toUpperCase());
            clienteJuridico.setCidade(txtCidadeClienteJuridico.getText().toUpperCase());
            clienteJuridico.setEstado(comboBoxEstadoClienteJuridico.getValue().toString());
            clienteJuridico.setContato(txtContatoClienteJuridico.getText());
            clienteJuridico.setContato1(txtContato1ClienteJuridico.getText());
            clienteJuridico.setEmail(txtEmailClienteJuridico.getText().toUpperCase());

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
        if (txtNomeFantasiaClienteJuridico.getText().isEmpty()) {
            txtNomeFantasiaClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtNomeFantasiaClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
           contador--;
        }

         if (txtRazaoSocialClienteJuridico.getText().isEmpty()) {
            txtRazaoSocialClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtRazaoSocialClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
        
        if (txtCnpjClienteJuridico.getText().isEmpty()) {
            txtCnpjClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCnpjClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtIEClienteJuridico.getText().isEmpty()) {
            txtIEClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtIEClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtEnderecoClienteJuridico.getText().isEmpty()) {
            txtEnderecoClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtEnderecoClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtBairroClienteJuridico.getText().isEmpty()) {
            txtBairroClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtBairroClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

         if (txtCEPClienteJuridico.getText().isEmpty()) {
            txtCEPClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCEPClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
        
        if (txtCidadeClienteJuridico.getText().isEmpty()) {
            txtCidadeClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCidadeClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
        
         if (txtContatoClienteJuridico.getText().isEmpty()) {
            txtContatoClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtContatoClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (txtEmailClienteJuridico.getText().isEmpty()) {
            txtEmailClienteJuridico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtEmailClienteJuridico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if(contador<=-10){
            retorno = true;
        }else{
            retorno = false;
        }
        
        if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Cliente Juridico");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }

    }
    
    /**
     * Método que popula os campos da tela com as informações do cliente juridico
     * selecionado.
     */
    @FXML
    private void popularCamposClienteJuridico() {
       
        txtNomeFantasiaClienteJuridico.setText(clienteJuridico.getNomeFantasia().toUpperCase());
        txtRazaoSocialClienteJuridico.setText(clienteJuridico.getRazaoSocial().toUpperCase());
        txtCnpjClienteJuridico.setText(clienteJuridico.getCnpj().toUpperCase());
        txtIEClienteJuridico.setText(clienteJuridico.getIe().toUpperCase());
        txtEnderecoClienteJuridico.setText(clienteJuridico.getEndereco().toUpperCase());
        txtBairroClienteJuridico.setText(clienteJuridico.getBairro().toUpperCase());
        txtCEPClienteJuridico.setText(clienteJuridico.getCep().toUpperCase());
        txtCidadeClienteJuridico.setText(clienteJuridico.getCidade().toUpperCase());
        comboBoxEstadoClienteJuridico.getSelectionModel().select(clienteJuridico.getEstado());
        txtContatoClienteJuridico.setText(clienteJuridico.getContato());
        
        if(clienteJuridico.getContato1() == null){
            txtContato1ClienteJuridico.setText("");
        }else{
            txtContato1ClienteJuridico.setText(clienteJuridico.getContato1());
        }
        
        txtEmailClienteJuridico.setText(clienteJuridico.getEmail().toUpperCase());

    }
    
     /**
     * Metodo utilizado para setar o cliente juridico selecionado na tabela no
     * cliente juridico dessa classe.
     *
     * @param clienteJuridico 
     */
    public void setClienteJuridico(ClienteJuridico clienteJuridico) {
        this.clienteJuridico = clienteJuridico;

    }
    
    
    /**
     * Método que fornece uma mascará no formato de CNPJ.
     */
    private void maskCnpj() {

        txtCnpjClienteJuridico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtCnpjClienteJuridico.getText().length()) {
                    case 15:
                        txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText().substring(0, 8));
                        txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                        break;
                    case 10:
                        txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText().substring(0, 7));
                        txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                        break;    
                    case 6:
                        txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText().substring(0, 6));
                        txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                        break;
                    case 2:
                        txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText().substring(0, 2));
                        txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                        break;
                }

            } else if (txtCnpjClienteJuridico.getText().length() == 18) {
                evento.consume();
            }
            switch (txtCnpjClienteJuridico.getText().length()) {
                case 2:
                    txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText() + ".");
                    txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                    break;
                case 6:
                    txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText() + ".");
                    txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                    break;
                case 10:
                    txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText() + "/");
                    txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                    break;    
                case 15:
                    txtCnpjClienteJuridico.setText(txtCnpjClienteJuridico.getText() + "-");
                    txtCnpjClienteJuridico.positionCaret(txtCnpjClienteJuridico.getText().length());
                    break;
            }

        });
    }
    
     /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato a máscara.
     */
    private void maskTel9DigContato() {
        txtContatoClienteJuridico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContatoClienteJuridico.getText().length()) {
                    case 1:
                        txtContatoClienteJuridico.setText("");
                        txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                        break;
                    case 3:
                        txtContatoClienteJuridico.setText(txtContatoClienteJuridico.getText().substring(0, 2));
                        txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                        break;
                    case 10:
                        txtContatoClienteJuridico.setText(txtContatoClienteJuridico.getText().substring(0, 9));
                        txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                        break;
                }
            } else if (txtContatoClienteJuridico.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContatoClienteJuridico.getText().length()) {
                case 1:
                    txtContatoClienteJuridico.setText("(" + txtContatoClienteJuridico.getText());
                    txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                    break;
                case 3:
                    txtContatoClienteJuridico.setText(txtContatoClienteJuridico.getText() + ") ");
                    txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                    break;
                case 10:
                    txtContatoClienteJuridico.setText(txtContatoClienteJuridico.getText() + "-");
                    txtContatoClienteJuridico.positionCaret(txtContatoClienteJuridico.getText().length());
                    break;
            }

        });
    }
    
    /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato 2 a máscara.
     */
    private void maskTel9DigContato2() {
        txtContato1ClienteJuridico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContato1ClienteJuridico.getText().length()) {
                    case 1:
                        txtContato1ClienteJuridico.setText("");
                        txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                        break;
                    case 3:
                        txtContato1ClienteJuridico.setText(txtContato1ClienteJuridico.getText().substring(0, 2));
                        txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                        break;
                    case 10:
                        txtContato1ClienteJuridico.setText(txtContato1ClienteJuridico.getText().substring(0, 9));
                        txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                        break;
                }
            } else if (txtContato1ClienteJuridico.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContato1ClienteJuridico.getText().length()) {
                case 1:
                    txtContato1ClienteJuridico.setText("(" + txtContato1ClienteJuridico.getText());
                    txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                    break;
                case 3:
                    txtContato1ClienteJuridico.setText(txtContato1ClienteJuridico.getText() + ") ");
                    txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                    break;
                case 10:
                    txtContato1ClienteJuridico.setText(txtContato1ClienteJuridico.getText() + "-");
                    txtContato1ClienteJuridico.positionCaret(txtContato1ClienteJuridico.getText().length());
                    break;
            }

        });
    }
    
     /**
     * Método que popula o combobox de estados.
     */
    @FXML
    private void popularComboBoxEstados() {
        ObservableList<String> estados = FXCollections.observableArrayList();
        estados.add("Acre");
        estados.add("Alagoas");
        estados.add("Amapá");
        estados.add("Amazonas");
        estados.add("Bahia");
        estados.add("Ceará");
        estados.add("Distrito Federal");
        estados.add("Espirito Santo");
        estados.add("Goias");
        estados.add("Maranhão");
        estados.add("Mato Grosso");
        estados.add("Mato Grosso do Sul");
        estados.add("Minas Gerais");
        estados.add("Pará");
        estados.add("Paraiba");
        estados.add("Paraná");
        estados.add("Pernambuco");
        estados.add("Piaui");
        estados.add("Rio de Janeiro");
        estados.add("Rio Grande do Norte");
        estados.add("Rio Grande do Sul");
        estados.add("Rondonia");
        estados.add("Roraima");
        estados.add("Santa Catarina");
        estados.add("Sao Paulo");
        estados.add("Sergipe");
        estados.add("Tocantins");

        comboBoxEstadoClienteJuridico.setItems(estados);
        comboBoxEstadoClienteJuridico.getSelectionModel().select(15);

    }
}
