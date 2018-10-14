/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.classes.Viagem;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
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
public class TabelaViagensController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView tabelaViagens = new TableView();

    @FXML
    private TableColumn<Viagem, LocalDate> dataTabelaViagens = new TableColumn();

    @FXML
    private TableColumn<Viagem, BigDecimal> valorGastoPrevistoTabelaViagens = new TableColumn();

    @FXML
    private TableColumn<Viagem, BigDecimal> valorGastoRealizadoTabelaViagens = new TableColumn();
    
    ArrayList<Viagem> viagens = new ArrayList<Viagem>();

    ObservableList<Viagem> viagensBusca = FXCollections.observableArrayList();
    
    Viagem viagem = new Viagem();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tabelaViagens.setRowFactory(tv -> {
            TableRow<Viagem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        selecionaViagem(row.getItem());
                    } catch (IOException ex) {
                        Logger.getLogger(TabelaCargosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });

            return row;
        });
       
       formatarDataViagem();
    }    
    
     /**
     * Método que insere os dados do arraylist na tabela.
     */
    @FXML
    protected void popularTabela() {
        if (viagens != null) {
            
            dataTabelaViagens.setCellValueFactory(new PropertyValueFactory("dataViagem"));
            valorGastoPrevistoTabelaViagens.setCellValueFactory(new PropertyValueFactory("valorTotalGastoPrevisto"));
            valorGastoRealizadoTabelaViagens.setCellValueFactory(new PropertyValueFactory("valorTotalGastoRealizado"));
            
            tabelaViagens.setItems(viagensBusca);
            tabelaViagens.getItems().setAll(viagens);

        }
    }
    
     /**
     * Metodo que recebe um arraylist de viagens oriundos da tela Viagens.
     *
     * @param viagens
     */
    public void setViagens(ArrayList<Viagem> viagens) {
        this.viagens = viagens;
        popularTabela();
    }
    
     /**
     * Método que recebe a viagem selecionada da tabela e retorna a tela de
     * viagem.
     */
    private void selecionaViagem(Viagem viagem) throws IOException {
        this.viagem = viagem;
         Stage stage = (Stage) tabelaViagens.getScene().getWindow();
        stage.close();
        getViagemSelecionada();
    }
    
    public Viagem getViagemSelecionada(){
        return this.viagem;
    }
      
    
    @FXML
    private void formatarDataViagem() {
        dataTabelaViagens.setCellFactory(column -> {
            return new TableCell<Viagem, LocalDate>() {

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
    
    private String formatarData(LocalDate data) {
        final String DATE_PATTERN = "dd/MM/yyyy";
        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return DATE_FORMATTER.format(data);
    }
}
