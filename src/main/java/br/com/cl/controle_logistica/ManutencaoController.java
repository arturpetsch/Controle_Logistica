/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.ManutencaoDAO;
import br.com.cl.controle_logistica.DAO.VeiculoDAO;
import br.com.cl.controle_logistica.classes.Manutencao;
import br.com.cl.controle_logistica.classes.Veiculo;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ManutencaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtPlaca;

    @FXML
    private Button btnBuscarVeiculo;

    @FXML
    private Label modeloManutencao;

    @FXML
    private Label anoManutencao;

    @FXML
    private Label placaCarretaManutencao;

    @FXML
    private Label kmManutencao;

    @FXML
    private ListView dataUltimasManutencoes = new ListView();

    @FXML
    private ComboBox comboBoxTipoManutencao;

    @FXML
    private TextField valorManutencao;

    @FXML
    private DatePicker dataManutencao;

    @FXML
    private TextArea observacaoManutencao;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnDeletarManutencao;

    @FXML
    private Button btnSalvar;

    @FXML
    private ListCell<LocalDate> datas;

    Veiculo veiculo = new Veiculo();

    ArrayList<LocalDate> dataManutencoes = new ArrayList<LocalDate>();

    ObservableList<LocalDate> manutencaoBusca = FXCollections.observableArrayList();

    Manutencao manutencao = new Manutencao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));

        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletarManutencao.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarVeiculo.setGraphic(new ImageView(search));

        popularTipoManutencao();
        
        dataManutencao.setValue(null);
        
    }

    /**
     * Metodo que através da placa busca e lista o veiculo cadastrado.
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
     * Método que chama o Stage onde está a tabela que lista todos os veiculos
     * com a placa informada pelo usuário.
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
                buscarManutencao();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Veiculo");
            confirmacao.setHeaderText("Nenhum veiculo encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo veiculo!");
            confirmacao.showAndWait();
        }
    }

    /**
     * Método que popula os campos com os dados do veiculo selecionado.
     */
    private void popularCamposVeiculo() {
        modeloManutencao.setText(veiculo.getModeloVeiculo());
        anoManutencao.setText(veiculo.getAnoVeiculo());
        placaCarretaManutencao.setText(veiculo.getPlaca());
        kmManutencao.setText(veiculo.getKm().toString());
    }

    /**
     * Método que busca manutenção referente ao veiculo selecionado e lista na
     * tela.
     */
    private void buscarManutencao() {
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
        dataManutencoes = manutencaoDAO.buscarDataManutencoes(veiculo.getIdVeiculo());

        if (!dataManutencoes.isEmpty()) {
            popularManutencao(dataManutencoes);
        
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Manutenção de Veículo");
            alerta.setHeaderText("Nenhuma manutenção cadastrada para este veículo.");
            alerta.showAndWait();
        }
    }

    /**
     * Método que preenche a lista com as manutenções localizadas.
     */
    private void popularManutencao(ArrayList<LocalDate> dataManutencoes) {

         
        dataUltimasManutencoes.setItems(manutencaoBusca);
        dataUltimasManutencoes.getItems().setAll(dataManutencoes);
        
        formatarDataManutencao();
        
    }

    /**
     * Método que formata a data em modelo dd/MM/aaaa.
     */
    private void formatarDataManutencao() {
        dataUltimasManutencoes.setCellFactory(column -> {
            return new ListCell<LocalDate>() {

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {

                    } else {
                        setText(formatarData(item));
                    }
                }
            };
        });
    }

    /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão da manutenção selecionada.
     * @param action 
     */
    @FXML
    private void deletarManutencao(ActionEvent action) throws IOException{
        if(this.manutencao.getIdManutencao()> 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoManutencao.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoManutencaoController confirmacaoAcaoManutencaoController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoManutencaoController.setManutencao(manutencao);
            stage.showAndWait();
            limparCampos();
            dataManutencao.setValue(null);
        }
    }
    
    /**
     * Método que limpa todos os campos e cancela as operações.
     * @param action 
     */
    @FXML
    private void cancelarOperacao(ActionEvent action){
        limparCampos();
        dataManutencao.setValue(null);
    }
    
    private String formatarData(LocalDate data) {
        final String DATE_PATTERN = "dd/MM/yyyy";
        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return DATE_FORMATTER.format(data);
    }

    @FXML
    private void inserindoDataManutencao(ActionEvent action) {
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
        if (dataManutencao != null) {

            manutencao = manutencaoDAO.buscarManutencaoPorData(dataManutencao.getValue());
            if (manutencao != null) {
                popularCamposManutencao();
            } else {
                limparCamposManutencao();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Buscar Manutenção");
                alerta.setHeaderText("Nenhuma manutenção cadastrada nessa data.");
                alerta.showAndWait();
            }
        }
    }

    /**
     * Método que coleta os dados informados pelo usuário referente a
     * manutenção.
     */
    @FXML
    private boolean coletarCamposManutencao() {
        if (verificarCamposVazios()) {
            manutencao.setDataManutencao(dataManutencao.getValue());
            manutencao.setTipoManutencao(comboBoxTipoManutencao.getValue().toString());
            String valorInformado = valorManutencao.getText();
            BigDecimal valor = new BigDecimal(valorInformado.replace(",", "."));
            manutencao.setValor(valor);
            manutencao.setObservacao(observacaoManutencao.getText());
            manutencao.setVeiculo(veiculo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que popula os campos de manutenção conforme objeto retornado do
     * banco de dados.
     */
    @FXML
    private void popularCamposManutencao() {
        this.valorManutencao.setText(String.valueOf(manutencao.getValor()).replace(".", ","));
        this.comboBoxTipoManutencao.getSelectionModel().select(manutencao.getTipoManutencao());
        this.observacaoManutencao.setText(manutencao.getObservacao());
    }

    /**
     * Método que popula o combobox de tipo de manutenção.
     */
    @FXML
    private void popularTipoManutencao() {
        ObservableList<String> tipoManutencao = FXCollections.observableArrayList();
        tipoManutencao.add("Corretiva");
        tipoManutencao.add("Preventiva");
        comboBoxTipoManutencao.setItems(tipoManutencao);
        comboBoxTipoManutencao.getSelectionModel().select(0);
    }

    /**
     * Método que valida se os campos estão vazios.
     */
    private boolean verificarCamposVazios() {
        boolean retorno = true;
        int contador = 0;
        if (observacaoManutencao.getText().isEmpty()) {
            observacaoManutencao.setStyle("-fx-border-color:red");
            contador++;
        } else {
            observacaoManutencao.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (dataManutencao.getValue() == null) {
            dataManutencao.setStyle("-fx-border-color:red");
            contador++;
        } else {
            dataManutencao.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (valorManutencao.getText().isEmpty()) {
            valorManutencao.setStyle("-fx-border-color:red");
            contador++;
        } else if (Double.parseDouble(valorManutencao.getText().replace(",", ".")) <= 0) {
            valorManutencao.setStyle("-fx-border-color:red");
            contador++;
        } else {
            valorManutencao.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (contador <= -3) {
            retorno = true;
        } else {
            retorno = false;
        }

        if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Manutenção");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }
    }

    @FXML
    public void salvarManutencao() {
        ManutencaoDAO manutencaoDAO = new ManutencaoDAO();

        if (this.manutencao == null) {
            this.manutencao = new Manutencao();
        }

        if (this.manutencao.getIdManutencao() == 0 && coletarCamposManutencao()) {

            manutencaoDAO.salvarManutencao(manutencao);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Manutenção");
            confirmacao.setHeaderText("Manutenção Cadastrada com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (this.manutencao.getIdManutencao() > 0 && coletarCamposManutencao()) {
            if (manutencaoDAO.alterarManutencao(manutencao)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Manutencao");
                confirmacao.setHeaderText("Manutencao Atualizada com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
    }

    /**
     * Método que limpa os campos da tela.
     */
    private void limparCampos() {
        modeloManutencao.setText("");
        anoManutencao.setText("");
        placaCarretaManutencao.setText("");
        kmManutencao.setText("");
        dataUltimasManutencoes.setItems(null);
        valorManutencao.setText("");
        observacaoManutencao.setText("");
        
    }
    
    private void limparCamposManutencao(){
        valorManutencao.setText("");
        observacaoManutencao.setText("");
        dataManutencao.setValue(null);
    }
}
