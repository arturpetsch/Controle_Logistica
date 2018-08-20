/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.classes.Nf;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class TabelaNotasFiscaisController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView tabelaNotasFiscais = new TableView();

    @FXML
    private TableColumn<Nf, Integer> numeroNfTabelaNotasFiscais = new TableColumn();

    @FXML
    private TableColumn<Nf, String> chaveAcessoTabelaNotasFiscais = new TableColumn();

    @FXML
    private TableColumn<Nf, BigDecimal> valorTabelaNotasFiscais = new TableColumn();
    
    ArrayList<Nf> notasFiscais = new ArrayList<Nf>();

    ObservableList<Nf> notasFiscaisBusca = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tabelaNotasFiscais.setOnKeyPressed((KeyEvent e) -> {

            if (!tabelaNotasFiscais.getSelectionModel().isEmpty()
                    && e.getCode() == KeyCode.DELETE) {
                Nf nf = (Nf) tabelaNotasFiscais.getSelectionModel().getSelectedItem();
                notasFiscaisBusca.remove(nf);
                notasFiscais.remove(nf);
                e.consume();
            }
        });
    }    
    
     /**
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabela() {
        if (notasFiscais != null) {
            numeroNfTabelaNotasFiscais.setCellValueFactory(new PropertyValueFactory("numeroNF"));
            chaveAcessoTabelaNotasFiscais.setCellValueFactory(new PropertyValueFactory("chaveAcesso"));
            valorTabelaNotasFiscais.setCellValueFactory(new PropertyValueFactory("valorNf"));
            
            tabelaNotasFiscais.setItems(notasFiscaisBusca);
            tabelaNotasFiscais.getItems().setAll(notasFiscais);

        }
    }
    
    /**
     * Metodo que recebe um arraylist de notas fiscais oriundas da tela Conhecimento de Transporte.
     *
     * @param notasFiscais
     */
    public void setNotasFiscais(ArrayList<Nf> notasFiscais) {
        this.notasFiscais = notasFiscais;
        popularTabela();
    }
    
    /**
     * Método que possibilita a outras classes coletar o arraylist de notas fiscais da tabela.
     */
    public ArrayList<Nf> getNotasFiscais(){
        return this.notasFiscais;
    }
}
