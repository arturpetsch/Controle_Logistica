/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.classes.Cte;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class TabelaConhecimentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView tabelaConhecimentos = new TableView();

    @FXML
    private TableColumn<Cte, Integer> numeroTabelaCte = new TableColumn();

    @FXML
    private TableColumn<Cte, String> remetenteTabelaCte = new TableColumn();

    @FXML
    private TableColumn<Cte, Integer> destinatarioTabelaCte = new TableColumn();

    @FXML
    private TableColumn<Cte, Integer> valorTabelaCte = new TableColumn();

    ArrayList<Cte> fretes = new ArrayList<Cte>();

    ObservableList<Cte> fretesBusca = FXCollections.observableArrayList();
    
    Cte cte = new Cte();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabelaConhecimentos.setRowFactory(tv -> {
            TableRow<Cte> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        selecionaCte(row.getItem());
                    } catch (IOException ex) {
                        Logger.getLogger(TabelaCargosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });

            return row;
        });
    }    
    
    /**
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabela() {
        if (fretes != null) {
            numeroTabelaCte.setCellValueFactory(new PropertyValueFactory("numeroCte"));
            remetenteTabelaCte.setCellValueFactory(new PropertyValueFactory("nomeCliente"));
            destinatarioTabelaCte.setCellValueFactory(new PropertyValueFactory("nomeCliente"));
            valorTabelaCte.setCellValueFactory(new PropertyValueFactory("valor"));

            tabelaConhecimentos.setItems(fretesBusca);
            tabelaConhecimentos.getItems().setAll(fretes);

        }
    }
    
     /**
     * Metodo que recebe um arraylist de cte's oriundos da tela Conhecimentos.
     *
     * @param ctes
     */
    public void setCte(ArrayList<Cte> ctes) {
        this.fretes = ctes;
        popularTabela();
    }
    
     /**
     * Método que recebe o cte selecionado da tabela e retorna a tela de
     * cte's.
     */
    private void selecionaCte(Cte cte) throws IOException {
        this.cte = cte;
         Stage stage = (Stage) tabelaConhecimentos.getScene().getWindow();
        stage.close();
        getCteSelecionado();
    }
    
    public Cte getCteSelecionado(){
        return this.cte;
    }
    }    
    


