/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.classes.Funcionario;
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

public class TabelaColaboradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    //================Atributos================================
    @FXML
    private TableView tabelaFuncionarios = new TableView();

    @FXML
    private TableColumn<Funcionario, Integer> idTabelaFuncionarios = new TableColumn();

    @FXML
    private TableColumn<Funcionario, String> nomeTabelaFuncionarios = new TableColumn();

    ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

    ObservableList<Funcionario> funcionarioBusca = FXCollections.observableArrayList();

    Funcionario funcionario = new Funcionario();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         tabelaFuncionarios.setRowFactory(tv -> {
            TableRow<Funcionario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        selecionaFuncionario(row.getItem());
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
        if (funcionarios != null) {
            idTabelaFuncionarios.setCellValueFactory(new PropertyValueFactory("idFuncionario"));
            nomeTabelaFuncionarios.setCellValueFactory(new PropertyValueFactory("nome"));

            tabelaFuncionarios.setItems(funcionarioBusca);
            tabelaFuncionarios.getItems().setAll(funcionarios);

        }
    }
    
     /**
     * Metodo que recebe um arraylist de funcionários oriundos da tela Colaborador.
     *
     * @param funcionarios
     */
    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        popularTabela();
    }
    
     /**
     * Método que recebe o funcionario selecionado da tabela e retorna a tela de
     * cargos.
     */
    private void selecionaFuncionario(Funcionario funcionario) throws IOException {
        this.funcionario = funcionario;
         Stage stage = (Stage) tabelaFuncionarios.getScene().getWindow();
        stage.close();
        getFuncionarioSelecionado();
    }
    
    public Funcionario getFuncionarioSelecionado(){
        return this.funcionario;
    }
    
    }    
    

