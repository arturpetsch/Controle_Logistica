/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.classes.Cargo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class TabelaCargosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    /**
     * *********************ATRIBUTOS*******************************
     *
     */
    @FXML
    private TableView tabelaCargos = new TableView();

    @FXML
    private TableColumn<Cargo, Integer> idTabelaCargos = new TableColumn();

    @FXML
    private TableColumn<Cargo, String> descricaoTabelaCargos = new TableColumn();

    ArrayList<Cargo> cargos = new ArrayList<Cargo>();

    ObservableList<Cargo> cargoBusca = FXCollections.observableArrayList();

    Cargo cargo = new Cargo();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabelaCargos.setRowFactory(tv -> {
            TableRow<Cargo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        //Cargo rowData = row.getItem();
                        selecionaCargo(row.getItem());
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
        if (cargos != null) {
            idTabelaCargos.setCellValueFactory(new PropertyValueFactory("idCargo"));
            descricaoTabelaCargos.setCellValueFactory(new PropertyValueFactory("descricao"));

            tabelaCargos.setItems(cargoBusca);
            tabelaCargos.getItems().setAll(cargos);

        }
    }

    /**
     * Metodo que recebe um arraylist de cargos oriundos da tela Cargos.
     *
     * @param cargos
     */
    public void setCargos(ArrayList<Cargo> cargos) {
        this.cargos = cargos;
        popularTabela();
    }

    /**
     * Método que recebe o cargo selecionado da tabela e retorna a tela de
     * cargos.
     */
    private void selecionaCargo(Cargo cargo) throws IOException {
        this.cargo = cargo;
         Stage stage = (Stage) tabelaCargos.getScene().getWindow();
        stage.close();
        getCargoSelecionado();
    }
    
    public Cargo getCargoSelecionado(){
        return this.cargo;
    }
}
