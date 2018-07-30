/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.ClienteFisicoDAO;
import br.com.cl.controle_logistica.classes.ClienteFisico;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.DatePicker;
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
public class ClienteFisicoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnBuscarCPFClienteFisico;
    
    @FXML
    private Button btnSalvarClienteFisico;
    
    @FXML
    private Button btnDeletarClienteFisico;
    
    @FXML
    private Button btnCancelarClienteFisico;
    
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNomeClienteFisico;

    @FXML
    private TextField txtCpfClienteFisico;

    @FXML
    private TextField txtRGClienteFisico;

    @FXML
    private DatePicker dataNascimentoClienteFisico;

    @FXML
    private TextField txtEnderecoClienteFisico;

    @FXML
    private TextField txtBairroClienteFisico;

    @FXML
    private TextField txtCEPClienteFisico;

    @FXML
    private TextField txtCidadeClienteFisico;

    @FXML
    private ComboBox comboBoxEstadoClienteFisico;

    @FXML
    private TextField txtContatoClienteFisico;

    @FXML
    private TextField txtContato1ClienteFisico;

    @FXML
    private TextField txtEmailClienteFisico;
    
    ClienteFisico clienteFisico = new ClienteFisico();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        
        btnCancelarClienteFisico.setGraphic(new ImageView(cancel));
        btnDeletarClienteFisico.setGraphic(new ImageView(trash));
        btnSalvarClienteFisico.setGraphic(new ImageView(save));
        btnBuscarCPFClienteFisico.setGraphic(new ImageView(search));
        
        maskCpf();
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
        String nomeInformado = txtNome.getText();
        
            ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
            ArrayList<ClienteFisico> clientes = new ArrayList<ClienteFisico>();
            clientes = clienteFisicoDAO.consultarClientePorNome(nomeInformado);

            mostrarClientes(clientes);

       
    }
    
    /**
     * Método que limpa o(s) campo(s) na tela de Cliente Fisico.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtNomeClienteFisico.setText("");
        txtCpfClienteFisico.setText("");
        txtRGClienteFisico.setText("");
        dataNascimentoClienteFisico.setValue(null);
        txtEnderecoClienteFisico.setText("");
        txtBairroClienteFisico.setText("");
        txtCEPClienteFisico.setText("");
        txtCidadeClienteFisico.setText("");
        txtContatoClienteFisico.setText("");
        txtContato1ClienteFisico.setText("");
        txtEmailClienteFisico.setText("");

    }
    
    /**
     * Método utilizado pelo botao salvar. Salva um novo cliente ou as
     * alterações feitas em um já existente.
     *
     * @param action
     */
    @FXML
    protected void salvarCliente(ActionEvent action) {
        ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();

        if (this.clienteFisico == null) {
            this.clienteFisico = new ClienteFisico();
        }

        if (clienteFisico.getIdClienteFisico()== 0 && getAtributosClienteFisico()) {
            clienteFisicoDAO.salvarCliente(clienteFisico);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Cliente");
            confirmacao.setHeaderText("Cliente Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (getAtributosClienteFisico()&& clienteFisico.getIdClienteFisico()> 0) {
            if (clienteFisicoDAO.atualizarCliente(clienteFisico)) {
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
        if(this.clienteFisico.getIdClienteFisico() > 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoCliente.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoClienteController confirmacaoAcaoClienteController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoClienteController.setCliente(clienteFisico);
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
     * Método que chama o Stage onde está a tabela que lista todos os
     * clientes com o nome informado pelo usuário.
     *
     * @param clientes
     * @throws IOException
     */
    private void mostrarClientes(ArrayList<ClienteFisico> clientesFisicos) throws IOException {
        if (!clientesFisicos.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaClientes.fxml"));
            Parent root = (Parent) loader.load();
            TabelaClientesController tabelaClientesController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaClientesController.setCliente(clientesFisicos);
            stage.showAndWait();
            this.clienteFisico = tabelaClientesController.getClienteSelecionado();
            if (this.clienteFisico != null) {
                popularCamposClienteFisico();
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
     * cliente fisico.
     */
    private boolean getAtributosClienteFisico() {
        if (verificarCamposVazios()) {
            //Coletar todos os atributos e setar no cliente para poder salvar;
            clienteFisico.setNomeCliente(txtNomeClienteFisico.getText().toUpperCase());
            clienteFisico.setCpf(txtCpfClienteFisico.getText().toUpperCase());
            clienteFisico.setRg(txtRGClienteFisico.getText().toUpperCase());
            clienteFisico.setDataNascimento(dataNascimentoClienteFisico.getValue());
            clienteFisico.setEndereco(txtEnderecoClienteFisico.getText().toUpperCase());
            clienteFisico.setBairro(txtBairroClienteFisico.getText().toUpperCase());
            clienteFisico.setCep(txtCEPClienteFisico.getText().toUpperCase());
            clienteFisico.setCidade(txtCidadeClienteFisico.getText().toUpperCase());
            clienteFisico.setEstado(comboBoxEstadoClienteFisico.getValue().toString());
            clienteFisico.setContato(txtContatoClienteFisico.getText());
            clienteFisico.setContato1(txtContato1ClienteFisico.getText());
            clienteFisico.setEmail(txtEmailClienteFisico.getText().toUpperCase());

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
        
        if (txtNomeClienteFisico.getText().isEmpty()) {
            txtNomeClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtNomeClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtCpfClienteFisico.getText().isEmpty()) {
            txtCpfClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCpfClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtRGClienteFisico.getText().isEmpty()) {
            txtRGClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtRGClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (dataNascimentoClienteFisico.getValue() == null) {
            dataNascimentoClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            dataNascimentoClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtEnderecoClienteFisico.getText().isEmpty()) {
            txtEnderecoClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtEnderecoClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtBairroClienteFisico.getText().isEmpty()) {
            txtBairroClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtBairroClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

         if (txtCEPClienteFisico.getText().isEmpty()) {
            txtCEPClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCEPClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
        
        if (txtCidadeClienteFisico.getText().isEmpty()) {
            txtCidadeClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtCidadeClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
        
         if (txtContatoClienteFisico.getText().isEmpty()) {
            txtContatoClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtContatoClienteFisico.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (txtEmailClienteFisico.getText().isEmpty()) {
            txtEmailClienteFisico.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtEmailClienteFisico.setStyle("-fx-border-color:#bbaFFF");
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
            informacao.setTitle("Salvar Cliente Fisico");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }

    }
    
     /**
     * Método que popula os campos da tela com as informações do cliente fisico
     * selecionado.
     */
    @FXML
    private void popularCamposClienteFisico() {
       
        txtNomeClienteFisico.setText(clienteFisico.getNomeCliente().toUpperCase());
        txtCpfClienteFisico.setText(clienteFisico.getCpf().toUpperCase());
        txtRGClienteFisico.setText(clienteFisico.getRg().toUpperCase());
        dataNascimentoClienteFisico.setValue(clienteFisico.getDataNascimento());
        txtEnderecoClienteFisico.setText(clienteFisico.getEndereco().toUpperCase());
        txtBairroClienteFisico.setText(clienteFisico.getBairro().toUpperCase());
        txtCEPClienteFisico.setText(clienteFisico.getCep().toUpperCase());
        txtCidadeClienteFisico.setText(clienteFisico.getCidade().toUpperCase());
        comboBoxEstadoClienteFisico.getSelectionModel().select(clienteFisico.getEstado());
        txtContatoClienteFisico.setText(clienteFisico.getContato());
        
        if(clienteFisico.getContato1() == null){
            txtContato1ClienteFisico.setText("");
        }else{
            txtContato1ClienteFisico.setText(clienteFisico.getContato1());
        }
        
        txtEmailClienteFisico.setText(clienteFisico.getEmail().toUpperCase());

    }
    
     /**
     * Metodo utilizado para setar o cliente fisico selecionado na tabela no
     * cliente fisico dessa classe.
     *
     * @param clienteFisico
     */
    public void setClienteFisico(ClienteFisico clienteFisico) {
        this.clienteFisico = clienteFisico;

    }
    
     /**
     * Método que fornece uma mascará no formato de CPF.
     */
    private void maskCpf() {

        txtCpfClienteFisico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtCpfClienteFisico.getText().length()) {
                    case 11:
                        txtCpfClienteFisico.setText(txtCpfClienteFisico.getText().substring(0, 9));
                        txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                        break;
                    case 7:
                        txtCpfClienteFisico.setText(txtCpfClienteFisico.getText().substring(0, 6));
                        txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                        break;
                    case 3:
                        txtCpfClienteFisico.setText(txtCpfClienteFisico.getText().substring(0, 2));
                        txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                        break;
                }

            } else if (txtCpfClienteFisico.getText().length() == 14) {
                evento.consume();
            }
            switch (txtCpfClienteFisico.getText().length()) {
                case 3:
                    txtCpfClienteFisico.setText(txtCpfClienteFisico.getText() + ".");
                    txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                    break;
                case 7:
                    txtCpfClienteFisico.setText(txtCpfClienteFisico.getText() + ".");
                    txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                    break;
                case 11:
                    txtCpfClienteFisico.setText(txtCpfClienteFisico.getText() + "-");
                    txtCpfClienteFisico.positionCaret(txtCpfClienteFisico.getText().length());
                    break;
            }

        });
    }

    /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato a máscara.
     */
    private void maskTel9DigContato() {
        txtContatoClienteFisico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContatoClienteFisico.getText().length()) {
                    case 1:
                        txtContatoClienteFisico.setText("");
                        txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                        break;
                    case 3:
                        txtContatoClienteFisico.setText(txtContatoClienteFisico.getText().substring(0, 2));
                        txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                        break;
                    case 10:
                        txtContatoClienteFisico.setText(txtContatoClienteFisico.getText().substring(0, 9));
                        txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                        break;
                }
            } else if (txtContatoClienteFisico.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContatoClienteFisico.getText().length()) {
                case 1:
                    txtContatoClienteFisico.setText("(" + txtContatoClienteFisico.getText());
                    txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                    break;
                case 3:
                    txtContatoClienteFisico.setText(txtContatoClienteFisico.getText() + ") ");
                    txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                    break;
                case 10:
                    txtContatoClienteFisico.setText(txtContatoClienteFisico.getText() + "-");
                    txtContatoClienteFisico.positionCaret(txtContatoClienteFisico.getText().length());
                    break;
            }

        });
    }

    /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato 2 a máscara.
     */
    private void maskTel9DigContato2() {
        txtContato1ClienteFisico.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContato1ClienteFisico.getText().length()) {
                    case 1:
                        txtContato1ClienteFisico.setText("");
                        txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
                        break;
                    case 3:
                        txtContato1ClienteFisico.setText(txtContato1ClienteFisico.getText().substring(0, 2));
                        txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
                        break;
                    case 10:
                        txtContato1ClienteFisico.setText(txtContato1ClienteFisico.getText().substring(0, 9));
                        txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
                        break;
                }
            } else if (txtContato1ClienteFisico.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContato1ClienteFisico.getText().length()) {
                case 1:
                    txtContato1ClienteFisico.setText("(" + txtContato1ClienteFisico.getText());
                    txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
                    break;
                case 3:
                    txtContato1ClienteFisico.setText(txtContato1ClienteFisico.getText() + ") ");
                    txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
                    break;
                case 10:
                    txtContato1ClienteFisico.setText(txtContato1ClienteFisico.getText() + "-");
                    txtContato1ClienteFisico.positionCaret(txtContato1ClienteFisico.getText().length());
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

        comboBoxEstadoClienteFisico.setItems(estados);
        comboBoxEstadoClienteFisico.getSelectionModel().select(15);

    }
}
