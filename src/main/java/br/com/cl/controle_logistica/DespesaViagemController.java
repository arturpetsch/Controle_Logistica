package br.com.cl.controle_logistica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.cl.controle_logistica.classes.Nf;
import br.com.cl.controle_logistica.classes.ViagemDespesa;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.naming.Binding;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class DespesaViagemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField descricao;
    
    @FXML
    private ComboBox comboBoxTipo;
    
    @FXML
    private TextField valor;
    
    @FXML
    private Button botaoIncluir;
    
    @FXML
    private TableView tabelaDespesas;
    
    @FXML
    private TableColumn<ViagemDespesa, String> descricaoDespesa;
    
    @FXML
    private TableColumn<ViagemDespesa, String> tipoDespesa = new TableColumn<>();
    
    @FXML
    private TableColumn<ViagemDespesa, BigDecimal> valorDespesa = new TableColumn<>();
    
    ObservableList<ViagemDespesa> viagemDespesaBusca = FXCollections.observableArrayList();
    
    ArrayList<ViagemDespesa> despesas = new ArrayList<ViagemDespesa>();
    
    ViagemDespesa viagemDespesa = new ViagemDespesa();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image plus = new Image(getClass().getResourceAsStream("/Imagens/plus.png"));
        
        botaoIncluir.setGraphic(new ImageView(plus));
        
        popularComboBoxTipoDespesa();
        
        tabelaDespesas.setOnKeyPressed((KeyEvent e) -> {

            if (!tabelaDespesas.getSelectionModel().isEmpty()
                    && e.getCode() == KeyCode.DELETE) {
                ViagemDespesa vg = (ViagemDespesa) tabelaDespesas.getSelectionModel().getSelectedItem();
                viagemDespesaBusca.remove(vg);
                despesas.remove(vg);
                e.consume();
            }
        });
    }    
    
    /**
     * Método que insere a despesa na tabela e no arraylist.
     * @param action 
     */
    @FXML
    public void inserirDespesa(ActionEvent action){
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
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabelaViagem(){
        if(despesas != null){
            descricaoDespesa.setCellValueFactory(new PropertyValueFactory("descricao"));
            tipoDespesa.setCellValueFactory(new PropertyValueFactory("tipoDespesa"));
            valorDespesa.setCellValueFactory(new PropertyValueFactory("valor"));
            
            tabelaDespesas.setItems(viagemDespesaBusca);
            tabelaDespesas.getItems().setAll(despesas);

        }
    }
    
    
    
    /**
     * Método que coleta os dados informados pelo usuário e seta no objeto viagemDespesa.
     */
    private boolean coletarDadosDespesas(){
        if(verificarCamposVazios()){
            viagemDespesa.setDescricao((descricao.getText().toUpperCase()));
            String valorInformado = valor.getText();
            BigDecimal valor = new BigDecimal(valorInformado.replace(",", "."));   
            viagemDespesa.setValor(valor);
            viagemDespesa.setTipoDespesa(comboBoxTipo.getValue().toString().toUpperCase());
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método que valida se os campos estão vazios.
     * @return 
     */
    private boolean verificarCamposVazios(){
        boolean retorno = true;
        int contador = 0;
  
          if (descricao.getText().isEmpty()) {
            descricao.setStyle("-fx-border-color:red");
            contador++;
        } else {
            descricao.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
         
               
        if (valor.getText().isEmpty()) {
            valor.setStyle("-fx-border-color:red");
             contador++;
        } else {
            valor.setStyle("-fx-border-color:#bbaFFF");
             contador--;
        }
        
        if(contador<=-2){
            retorno = true;
        }else{
            retorno = false;
        }
        
        if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Despesa");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }

    }
    /**
     * Método que insere os dois tipos de despesas no comboBox.
     */
    @FXML
    private void popularComboBoxTipoDespesa(){
        ObservableList<String> tipo = FXCollections.observableArrayList();
         tipo.add("Previsto");
         tipo.add("Realizado");
         
         comboBoxTipo.setItems(tipo);
         comboBoxTipo.getSelectionModel().select(0);
    }
}
