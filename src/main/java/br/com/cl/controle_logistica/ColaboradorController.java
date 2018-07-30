/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.DAO.CargoDAO;
import br.com.cl.controle_logistica.DAO.FuncionarioDAO;
import br.com.cl.controle_logistica.classes.Cargo;
import br.com.cl.controle_logistica.classes.Funcionario;
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
public class ColaboradorController implements Initializable {

    /**
     * ===========================Atributos=========================
     */
    @FXML
    private Button btnSalvarColaborador;

    @FXML
    private Button btnDeletarColaborador;

    @FXML
    private Button btnCancelarColaborador;

    @FXML
    private Button btnBuscarCargo;

    @FXML
    private Button btnBuscarNomeColaborador;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNomeColaborador;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtRGColaborador;

    @FXML
    private DatePicker dataNascimentoColaborador;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtBairroColaborador;

    @FXML
    private TextField txtCEPColaborador;

    @FXML
    private TextField txtCidadeColaborador;

    @FXML
    private ComboBox comboBoxEstadoColaborador;

    @FXML
    private TextField txtContato;

    @FXML
    private TextField txtContato1;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCargo;

    @FXML
    private DatePicker dataAdmissao;

    @FXML
    private DatePicker dataRescisao;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtPorcentagem;

    Funcionario funcionario = new Funcionario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));

        btnCancelarColaborador.setGraphic(new ImageView(cancel));
        btnDeletarColaborador.setGraphic(new ImageView(trash));
        btnSalvarColaborador.setGraphic(new ImageView(save));
        btnBuscarCargo.setGraphic(new ImageView(search));
        btnBuscarNomeColaborador.setGraphic(new ImageView(search));

        maskCpf();
        maskTel9DigContato();
        maskTel9DigContato2();
        popularComboBoxEstados();
    }

    /**
     * Metodo que através do nome busca e lista o funcionário cadastrado ou
     * oferece ao usuário a opção de inserção de um novo funcionário.
     *
     * @param action
     */
    @FXML
    protected void buscarPeloNome(ActionEvent action) throws IOException {
        String nomeInformado = txtNome.getText();
        
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
            this.funcionario = tabelaColaboradorController.getFuncionarioSelecionado();
            if (this.funcionario != null) {
                popularCamposFuncionario();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Funcionário");
            confirmacao.setHeaderText("Nenhum funcionário encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo funcionário!");
            confirmacao.showAndWait();
        }
    }

    /**
     * Método que limpa o(s) campo(s) na tela de Colaborador.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtNomeColaborador.setText("");
        txtCPF.setText("");
        txtRGColaborador.setText("");
        dataNascimentoColaborador.setValue(null);
        txtEndereco.setText("");
        txtBairroColaborador.setText("");
        txtCEPColaborador.setText("");
        txtCidadeColaborador.setText("");
        txtContato.setText("");
        txtContato1.setText("");
        txtEmail.setText("");
        txtCargo.setText("");
        dataAdmissao.setValue(null);
        dataRescisao.setValue(null);
        txtSalario.setText("");
        txtPorcentagem.setText("");
    }

    /**
     * Método utilizado pelo botao salvar. Salva um novo funcionário ou as
     * alterações feitas em um já existente.
     *
     * @param action
     */
    @FXML
    protected void salvarFuncionario(ActionEvent action) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        if (this.funcionario == null) {
            this.funcionario = new Funcionario();
        }

        if (funcionario.getIdFuncionario() == 0 && getAtributosFuncionario()) {
            funcionarioDAO.salvarFuncionario(funcionario);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Funcionário");
            confirmacao.setHeaderText("Funcionário Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (getAtributosFuncionario() && funcionario.getIdFuncionario() > 0) {
            if (funcionarioDAO.atualizarFuncionario(funcionario)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Funcionário");
                confirmacao.setHeaderText("Funcionário Atualizado com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
        
    }
    
     /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão do funcionário selecionado.
     * @param action 
     */
    @FXML
    private void deletarFuncionario(ActionEvent action) throws IOException{
        if(this.funcionario.getIdFuncionario() > 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoColaborador.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoColaboradorController confirmacaoAcaoColaboradorController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoColaboradorController.setFuncionario(funcionario);
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
     * Método que coleta a descrição do cargo informada e busca no banco todos
     * os cargos com aquela descrição, após isso envia para a controller do
     * cargo para mostrar na tabela.
     *
     * @param action
     * @throws IOException
     */
    @FXML
    private void buscarCargoPeloNome(ActionEvent action) throws ClassNotFoundException, IOException {
        String descricaoInformada = txtCargo.getText();

        CargoDAO cargoDAO = new CargoDAO();
        ArrayList<Cargo> cargos = new ArrayList<Cargo>();
        cargos = cargoDAO.buscarCargoPelaDescricao(descricaoInformada);

        mostrarCargos(cargos);

    }

    /**
     * Método que mostra a tabela de cargos.
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
            this.funcionario.setCargo(tabelaCargosController.getCargoSelecionado());
            if (this.funcionario.getCargo() != null) {
                txtCargo.setText(funcionario.getCargo().getDescricao());
            }

        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Cargo");
            confirmacao.setHeaderText("Nenhum cargo encontrado.");
            confirmacao.showAndWait();
        }
    }

    /**
     * Método que coleta os dados de todos os campos da tela e seta no objeto
     * funcionário.
     */
    private boolean getAtributosFuncionario() {
        if (verificarCamposVazios()) {
            //Coletar todos os atributos e setar no funcionario para poder salvar;
            funcionario.setNome(txtNomeColaborador.getText().toUpperCase());
            funcionario.setCpf(txtCPF.getText().toUpperCase());
            funcionario.setRg(txtRGColaborador.getText().toUpperCase());
            funcionario.setDataNascimento(dataNascimentoColaborador.getValue());
            funcionario.setEndereco(txtEndereco.getText().toUpperCase());
            funcionario.setBairro(txtBairroColaborador.getText().toUpperCase());
            funcionario.setCep(txtCEPColaborador.getText().toUpperCase());
            funcionario.setCidade(txtCidadeColaborador.getText().toUpperCase());
            funcionario.setEstado(comboBoxEstadoColaborador.getValue().toString());
            funcionario.setContato(txtContato.getText());
            funcionario.setContato2(txtContato1.getText());
            funcionario.setEmail(txtEmail.getText().toUpperCase());

            funcionario.setDataAdmissao(dataAdmissao.getValue());
            funcionario.setDataRescisao(dataRescisao.getValue());
            String salarioInformado = txtSalario.getText();
            BigDecimal salario = new BigDecimal(salarioInformado.replace(",", "."));
            funcionario.setSalario(salario);

            if (!txtPorcentagem.getText().isEmpty()) {
                funcionario.setPorcentagem(Float.parseFloat(txtPorcentagem.getText()));
            }else{
                funcionario.setPorcentagem(new Float(0));
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
        if (txtNomeColaborador.getText().isEmpty() && txtNomeColaborador.getText().length() == 0) {
            txtNomeColaborador.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtNomeColaborador.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtCPF.getText().isEmpty()) {
            txtCPF.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtCPF.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (dataNascimentoColaborador.getValue() == null) {
            dataNascimentoColaborador.setStyle("-fx-border-color:red");
             contador++;
        } else {
            dataNascimentoColaborador.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (txtSalario.getText().isEmpty()) {
            txtSalario.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtSalario.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (dataAdmissao.getValue() == null) {
            dataAdmissao.setStyle("-fx-border-color:red");
             contador++;
        } else {
            dataAdmissao.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (txtEndereco.getText().isEmpty()) {
            txtEndereco.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtEndereco.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (txtBairroColaborador.getText().isEmpty()) {
            txtBairroColaborador.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtBairroColaborador.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (txtCidadeColaborador.getText().isEmpty()) {
            txtCidadeColaborador.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtCidadeColaborador.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

        if (txtRGColaborador.getText().isEmpty()) {
            txtRGColaborador.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtRGColaborador.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
        
         if (txtContato.getText().isEmpty()) {
            txtContato.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtContato.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
         
          if (txtEmail.getText().isEmpty()) {
            txtEmail.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtEmail.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
         
               
        if (txtCargo.getText().isEmpty()) {
            txtCargo.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtCargo.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }

         if (txtCEPColaborador.getText().isEmpty()) {
            txtCEPColaborador.setStyle("-fx-border-color:red");
             contador++;
        } else {
            txtCEPColaborador.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
        
        if(contador<=-13){
            retorno = true;
        }else{
            retorno = false;
        }
        
        if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Funcionário");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }

    }

    /**
     * Método que popula os campos da tela com as informações do funcionário
     * selecionado.
     */
    @FXML
    private void popularCamposFuncionario() {
        //Dados Pessoais do Funcionário
        txtNomeColaborador.setText(funcionario.getNome().toUpperCase());
        txtCPF.setText(funcionario.getCpf().toUpperCase());
        txtRGColaborador.setText(funcionario.getRg().toUpperCase());
        dataNascimentoColaborador.setValue(funcionario.getDataNascimento());
        txtEndereco.setText(funcionario.getEndereco().toUpperCase());
        txtBairroColaborador.setText(funcionario.getBairro().toUpperCase());
        txtCEPColaborador.setText(funcionario.getCep().toUpperCase());
        txtCidadeColaborador.setText(funcionario.getCidade().toUpperCase());
        comboBoxEstadoColaborador.getSelectionModel().select(funcionario.getEstado());
        txtContato.setText(funcionario.getContato());
        
        if(funcionario.getContato2() == null){
            txtContato1.setText("");
        }else{
            txtContato1.setText(funcionario.getContato2());
        }
        
        txtEmail.setText(funcionario.getEmail().toUpperCase());

        //Dados Financeiros do Funcionário
        txtCargo.setText(funcionario.getCargo().getDescricao().toUpperCase());
        dataAdmissao.setValue(funcionario.getDataAdmissao());
        
        if(funcionario.getDataRescisao() == null){
            dataRescisao.setValue(null);
        }else{
            dataRescisao.setValue(funcionario.getDataRescisao());
        }
        
        txtSalario.setText(String.valueOf(funcionario.getSalario()));
        
        if(!String.valueOf(funcionario.getPorcentagem()).isEmpty()){
            txtPorcentagem.setText(String.valueOf(funcionario.getPorcentagem()));
        }else{
            txtPorcentagem.setText("");
        }
    }

    /**
     * Metodo utilizado para setar o funcionario selecionado na tabela no
     * funcionario dessa classe.
     *
     * @param funcionario
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;

    }

    /**
     * Método que fornece uma mascará no formato de CPF.
     */
    private void maskCpf() {

        txtCPF.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtCPF.getText().length()) {
                    case 11:
                        txtCPF.setText(txtCPF.getText().substring(0, 9));
                        txtCPF.positionCaret(txtCPF.getText().length());
                        break;
                    case 7:
                        txtCPF.setText(txtCPF.getText().substring(0, 6));
                        txtCPF.positionCaret(txtCPF.getText().length());
                        break;
                    case 3:
                        txtCPF.setText(txtCPF.getText().substring(0, 2));
                        txtCPF.positionCaret(txtCPF.getText().length());
                        break;
                }

            } else if (txtCPF.getText().length() == 14) {
                evento.consume();
            }
            switch (txtCPF.getText().length()) {
                case 3:
                    txtCPF.setText(txtCPF.getText() + ".");
                    txtCPF.positionCaret(txtCPF.getText().length());
                    break;
                case 7:
                    txtCPF.setText(txtCPF.getText() + ".");
                    txtCPF.positionCaret(txtCPF.getText().length());
                    break;
                case 11:
                    txtCPF.setText(txtCPF.getText() + "-");
                    txtCPF.positionCaret(txtCPF.getText().length());
                    break;
            }

        });
    }

    /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato a máscara.
     */
    private void maskTel9DigContato() {
        txtContato.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContato.getText().length()) {
                    case 1:
                        txtContato.setText("");
                        txtContato.positionCaret(txtContato.getText().length());
                        break;
                    case 3:
                        txtContato.setText(txtContato.getText().substring(0, 2));
                        txtContato.positionCaret(txtContato.getText().length());
                        break;
                    case 10:
                        txtContato.setText(txtContato.getText().substring(0, 9));
                        txtContato.positionCaret(txtContato.getText().length());
                        break;
                }
            } else if (txtContato.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContato.getText().length()) {
                case 1:
                    txtContato.setText("(" + txtContato.getText());
                    txtContato.positionCaret(txtContato.getText().length());
                    break;
                case 3:
                    txtContato.setText(txtContato.getText() + ") ");
                    txtContato.positionCaret(txtContato.getText().length());
                    break;
                case 10:
                    txtContato.setText(txtContato.getText() + "-");
                    txtContato.positionCaret(txtContato.getText().length());
                    break;
            }

        });
    }

    /**
     * Método que fornece uma máscara para telefone com 9 digítos. Seta no campo
     * contato 2 a máscara.
     */
    private void maskTel9DigContato2() {
        txtContato1.setOnKeyTyped((KeyEvent evento) -> {
            if (!"0123456789".contains(evento.getCharacter())) {
                evento.consume();
            }

            if (evento.getCharacter().trim().length() == 0) {

                switch (txtContato1.getText().length()) {
                    case 1:
                        txtContato1.setText("");
                        txtContato1.positionCaret(txtContato1.getText().length());
                        break;
                    case 3:
                        txtContato1.setText(txtContato1.getText().substring(0, 2));
                        txtContato1.positionCaret(txtContato1.getText().length());
                        break;
                    case 10:
                        txtContato1.setText(txtContato1.getText().substring(0, 9));
                        txtContato1.positionCaret(txtContato1.getText().length());
                        break;
                }
            } else if (txtContato1.getText().length() == 15) {
                evento.consume();
            }
            switch (txtContato1.getText().length()) {
                case 1:
                    txtContato1.setText("(" + txtContato1.getText());
                    txtContato1.positionCaret(txtContato1.getText().length());
                    break;
                case 3:
                    txtContato1.setText(txtContato1.getText() + ") ");
                    txtContato1.positionCaret(txtContato1.getText().length());
                    break;
                case 10:
                    txtContato1.setText(txtContato1.getText() + "-");
                    txtContato1.positionCaret(txtContato1.getText().length());
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

        comboBoxEstadoColaborador.setItems(estados);
        comboBoxEstadoColaborador.getSelectionModel().select(15);

    }
}
