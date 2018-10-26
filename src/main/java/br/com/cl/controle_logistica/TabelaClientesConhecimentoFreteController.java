/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.ClienteDAO;
import br.com.cl.controle_logistica.DAO.ClienteFisicoDAO;
import br.com.cl.controle_logistica.DAO.ClienteJuridicoDAO;
import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.ClienteFisico;
import br.com.cl.controle_logistica.classes.ClienteJuridico;
import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.CteCliente;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class TabelaClientesConhecimentoFreteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
   @FXML
    private TableView tabelaClientes = new TableView();

   @FXML
    private TableView tabelaClientesJuridicos = new TableView();

    @FXML
    private TableColumn<Cliente, ClienteFisico> cpfCnpjTabelaClientes = new TableColumn();

    @FXML
    private TableColumn<Cliente, ClienteFisico> nomeTabelaClientes = new TableColumn();
    
    @FXML
    private TableColumn<Cliente, ClienteJuridico> cpfCnpjTabelaClientesJuridicos = new TableColumn();

    @FXML
    private TableColumn<Cliente, ClienteJuridico> nomeTabelaClientesJuridicos = new TableColumn();
    
    @FXML
    private TextField txtNomeCliente;
    
    @FXML
    private Button botaoPesquisar;
    
    ObservableList<Cliente> clienteBusca = FXCollections.observableArrayList();
    
    //ObservableList<ClienteJuridico> clienteJBusca = FXCollections.observableArrayList();
        
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        
    @FXML
    private ComboBox comboBoxTipoCliente;
    
    Cliente cliente = new Cliente();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        
        botaoPesquisar.setGraphic(new ImageView(search));
        
        popularComboBoxTipoCliente();
        
        
    }    
    
    /**
     * Método que coleta o nome informado e o tipo do cliente e busca no banco de dados.
     * @param action 
     */
    @FXML
    protected void buscarCliente(ActionEvent action){
       String nomeClienteInformado = txtNomeCliente.getText();
               
       if(comboBoxTipoCliente.getValue().equals("Fisico")){
           ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
           
           clientes = clienteFisicoDAO.consultarClientePorNome(nomeClienteInformado);
           
           popularTabelaClientesFisicos(clientes);
       }else{
           ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
           
           clientes = clienteJuridicoDAO.consultarClientePorNome(nomeClienteInformado);
           
           popularTabelaClientesJuridicos(clientes);
       }
       
    }
    
      /**
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabelaClientesJuridicos(ArrayList<Cliente> clientesJuridicos){
        if(clientesJuridicos != null){
            tabelaClientes.setVisible(false);
            tabelaClientesJuridicos.setVisible(true);
            cpfCnpjTabelaClientesJuridicos.setCellValueFactory(new PropertyValueFactory("clienteJuridico"));
            nomeTabelaClientesJuridicos.setCellValueFactory(new PropertyValueFactory("clienteJuridico"));
            
            formatarNomeClienteJuridico();
            formatarCpfCnpjClienteJuridico();
            tabelaClientesJuridicos.setItems(clienteBusca);
            tabelaClientesJuridicos.getItems().setAll(clientesJuridicos);

            tabelaClientesJuridicos.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selecionaClienteJuridico(row.getItem());
                    
                }
            });

            return row;
        });
        }
    }
    
        /**
     * Método que recebe o cliente selecionado da tabela e retorna a tela de
     * fretes.
     */
    private void selecionaClienteJuridico(Cliente clienteJuridico){
        this.cliente = clienteJuridico;
         Stage stage = (Stage) tabelaClientesJuridicos.getScene().getWindow();
        stage.close();
        getClienteSelecionadoJuridico();
    }
        
    
    public Cliente getClienteSelecionadoJuridico(){
        return this.cliente;
    }
    
        /**
     * Método que recebe o cliente selecionado da tabela e retorna a tela de
     * fretes.
     */
    private void selecionaClienteFisico(Cliente clienteFisico){
        this.cliente = clienteFisico;
         Stage stage = (Stage) tabelaClientes.getScene().getWindow();
        stage.close();
        getClienteSelecionadoFisico();
    }
        
    
    public Cliente getClienteSelecionadoFisico(){
        return this.cliente;
    }
    
      /**
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabelaClientesFisicos(ArrayList<Cliente> clientesFisicos){
        if(clientesFisicos != null){
            tabelaClientesJuridicos.setVisible(false);
            tabelaClientes.setVisible(true);
            cpfCnpjTabelaClientes.setCellValueFactory(new PropertyValueFactory("clienteFisico"));
            nomeTabelaClientes.setCellValueFactory(new PropertyValueFactory("clienteFisico"));
            
            formatarNomeCliente();
            formatarCpfCnpjCliente();
            tabelaClientes.setItems(clienteBusca);
            tabelaClientes.getItems().setAll(clientesFisicos);

            tabelaClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selecionaClienteFisico(row.getItem());
                    
                }
            });

            return row;
        });
        }
        }
    
  @FXML
    private void formatarNomeCliente() {
        nomeTabelaClientes.setCellFactory(column -> {
            return new TableCell<Cliente, ClienteFisico>() {

                @Override
                protected void updateItem(ClienteFisico item,  boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        
                    } else if(item != null && item.getIdClienteFisico() > 0){
                        setText(item.getNomeCliente());
                    }
                }
            };
        });
    }
    
  @FXML
    private void formatarCpfCnpjCliente() {
        cpfCnpjTabelaClientes.setCellFactory(column -> {
            return new TableCell<Cliente, ClienteFisico>() {

                @Override
                protected void updateItem(ClienteFisico item,  boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        
                    } else if(item != null && item.getIdClienteFisico() > 0){
                        setText(item.getCpf());
                    }
                }
            };
        });
    }
    
  @FXML
    private void formatarNomeClienteJuridico() {
        nomeTabelaClientesJuridicos.setCellFactory(column -> {
            return new TableCell<Cliente, ClienteJuridico>() {

                @Override
                protected void updateItem(ClienteJuridico item,  boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        
                    } else if(item != null && item.getIdClienteJuridico()> 0){
                        setText(item.getNomeFantasia());
                    }
                }
            };
        });
    }
    
          @FXML
    private void formatarCpfCnpjClienteJuridico() {
        cpfCnpjTabelaClientesJuridicos.setCellFactory(column -> {
            return new TableCell<Cliente, ClienteJuridico>() {

                @Override
                protected void updateItem(ClienteJuridico item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        
                    } else if(item != null){
                        setText(item.getCnpj());
                    }
                }
            };
        });
        }
   
    
    /**
     * Metodo que popula o tipo do cliente no combobox.
     */
    @FXML
    protected void popularComboBoxTipoCliente(){
         ObservableList<String> tipo = FXCollections.observableArrayList();
         tipo.add("Fisico");
         tipo.add("Juridico");
         
         comboBoxTipoCliente.setItems(tipo);
         comboBoxTipoCliente.getSelectionModel().select(0);
    }
}
