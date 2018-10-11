/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;


import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.ClienteFisico;
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
public class TabelaClientesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TableView tabelaClientes = new TableView();

    @FXML
    private TableColumn<ClienteFisico, Integer> idTabelaClientes = new TableColumn();

    @FXML
    private TableColumn<ClienteFisico, String> nomeTabelaClientes = new TableColumn();

    ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    ObservableList<Cliente> clienteBusca = FXCollections.observableArrayList();
    
    Cliente clienteFisico = new Cliente();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         tabelaClientes.setRowFactory(tv -> {
            TableRow<ClienteFisico> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        selecionaCliente(row.getItem());
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
        ArrayList<ClienteFisico> clientesFisico = new ArrayList<ClienteFisico>();
        int i = 0;
        while(clientes.size() > i){
            clientesFisico.add(clientes.get(i).getClienteFisico());
            i++;
        }
        
        if (clientesFisico != null) {
            idTabelaClientes.setCellValueFactory(new PropertyValueFactory("idClienteFisico"));
            nomeTabelaClientes.setCellValueFactory(new PropertyValueFactory("nomeCliente"));

            tabelaClientes.setItems(clienteBusca);
            tabelaClientes.getItems().setAll(clientesFisico);

        }
    }
    
     /**
     * Metodo que recebe um arraylist de clientes oriundos da tela Clientes.
     *
     * @param clientes
     */
    public void setCliente(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
        popularTabela();
    }
    
     /**
     * Método que recebe o cliente selecionado da tabela e retorna a tela de
     * cliente.
     */
    private void selecionaCliente(ClienteFisico clienteFisico) throws IOException {
        int i = 0;
        while(clientes.size() > i){
            if(clientes.get(i).getClienteFisico().equals(clienteFisico)){
                this.clienteFisico = clientes.get(i);
            }
            i++;
        }
        
         Stage stage = (Stage) tabelaClientes.getScene().getWindow();
        stage.close();
        getClienteSelecionado();
    }
    
    public Cliente getClienteSelecionado(){
        return this.clienteFisico;
    }
    
}
