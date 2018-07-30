/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.classes.Veiculo;
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
public class TabelaVeiculosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView tabelaVeiculos = new TableView();

    @FXML
    private TableColumn<Veiculo, Integer> placaTabelaVeiculos = new TableColumn();

    @FXML
    private TableColumn<Veiculo, String> modeloTabelaVeiculos = new TableColumn();

    ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();

    ObservableList<Veiculo> veiculosBusca = FXCollections.observableArrayList();
    
    Veiculo veiculo = new Veiculo();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabelaVeiculos.setRowFactory(tv -> {
            TableRow<Veiculo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        selecionaVeiculo(row.getItem());
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
        if (veiculos != null) {
            placaTabelaVeiculos.setCellValueFactory(new PropertyValueFactory("placa"));
            modeloTabelaVeiculos.setCellValueFactory(new PropertyValueFactory("modeloVeiculo"));

            tabelaVeiculos.setItems(veiculosBusca);
            tabelaVeiculos.getItems().setAll(veiculos);

        }
    }
    
     /**
     * Metodo que recebe um arraylist de veiculos oriundos da tela Veiculos.
     *
     * @param veiculos
     */
    public void setVeiculo(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
        popularTabela();
    }
    
     /**
     * Método que recebe o veiculo selecionado da tabela e retorna a tela de
     * veiculo.
     */
    private void selecionaVeiculo(Veiculo veiculo) throws IOException {
        this.veiculo = veiculo;
         Stage stage = (Stage) tabelaVeiculos.getScene().getWindow();
        stage.close();
        getVeiculoSelecionado();
    }
    
    public Veiculo getVeiculoSelecionado(){
        return this.veiculo;
    }
    }    
    

