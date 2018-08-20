/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.CteDAO;
import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.Nf;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class ConhecimentoFreteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnBuscarNumeroFrete;

    @FXML
    private Button btnSalvarFrete;

    @FXML
    private Button btnDeletaFrete;

    @FXML
    private Button btnCancelarFrete;

    @FXML
    private Button btnInserirNf;

    @FXML
    private Button btnBuscarDestinatario;

    @FXML
    private Button btnBuscarRemetente;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtNumeroCte;

    @FXML
    private TextField txtValorCte;

    @FXML
    private DatePicker dataEmissaoCte;

    @FXML
    private TextField chaveAcessoCte;

    @FXML
    private TextField produtoCte;

    @FXML
    private TextField pesoBrutoCte;

    @FXML
    private TextField pesoLiquidoCte;

    @FXML
    private TextField volumeCte;

    @FXML
    private TextField especieCte;

    @FXML
    private TextField observacaoCte;

    @FXML
    private TextField numeroNf;

    @FXML
    private TextField valorNf;

    @FXML
    private TextField chaveAcessoNf;

    @FXML
    private Button btnVerNfs;
    
    @FXML
    private CheckBox tomadorServicoRemetente;

    @FXML
    private CheckBox tomadorServicoDestinatario;

    ArrayList<Nf> notasFiscais = new ArrayList<Nf>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        Image plus = new Image(getClass().getResourceAsStream("/Imagens/plus.png"));

        btnCancelarFrete.setGraphic(new ImageView(cancel));
        btnDeletaFrete.setGraphic(new ImageView(trash));
        btnSalvarFrete.setGraphic(new ImageView(save));
        btnBuscarDestinatario.setGraphic(new ImageView(search));
        btnInserirNf.setGraphic(new ImageView(plus));
        btnBuscarNumeroFrete.setGraphic(new ImageView(search));
        btnBuscarRemetente.setGraphic(new ImageView(search));
        btnVerNfs.setGraphic(new ImageView(search));
    }

    /**
     * Método que coleta os dados da NF e insere um objeto NF e posterior em uma
     * ArrayList.
     *
     * @param action
     */
    @FXML
    protected void coletarDadosNf(ActionEvent action) throws IOException {
        Nf notaFiscal = new Nf();
        
        if(verificarCamposNf()){
            notaFiscal.setNumeroNF(Integer.parseInt(numeroNf.getText()));
            String valor = valorNf.getText();
            BigDecimal vlNF = new BigDecimal(valor.replace(",", "."));
            notaFiscal.setValorNf(vlNF);

            notaFiscal.setChaveAcesso(chaveAcessoNf.getText());

            if (notasFiscais.isEmpty()) {
                notasFiscais.add(notaFiscal);
                mostrarTabelaNotasFiscais();
            } else {
                for (Nf nf : notasFiscais) {
                    if (nf.getChaveAcesso().equals(notaFiscal.getChaveAcesso())) {
                        Alert erro = new Alert(Alert.AlertType.ERROR);
                        erro.setTitle("Inserir Nota Fiscal");
                        erro.setHeaderText("Já existe outra nota fiscal cadastrada com essa chave de acesso!");
                        erro.showAndWait();
                    } else {
                        notasFiscais.add(notaFiscal);
                        mostrarTabelaNotasFiscais();
                    }
                }
            }
        }
    }
    
    /**
     * Método que verifica se os campos de nota fiscal estão vazios.
     * @return 
     */
    private boolean verificarCamposNf(){
        
        boolean retorno = true;
        int contador = 0;
        if (numeroNf.getText().isEmpty()) {
            numeroNf.setStyle("-fx-border-color:red");
            contador++;
        }else if(Integer.parseInt(numeroNf.getText()) <= 0){
            numeroNf.setStyle("-fx-border-color:red");
            contador++;
        } else {
            numeroNf.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
    
         if (valorNf.getText().isEmpty()) {
            valorNf.setStyle("-fx-border-color:red");
            contador++;
        }else if(Double.parseDouble(valorNf.getText()) <= 0){
            numeroNf.setStyle("-fx-border-color:red");
            contador++;    
        } else {
            valorNf.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (chaveAcessoNf.getText().isEmpty()) {
            chaveAcessoNf.setStyle("-fx-border-color:red");
            contador++;
        } else {
            chaveAcessoNf.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
           if(contador<=-3){
            retorno = true;
         }else{
             retorno = false;
         } 
         
         if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Nota Fiscal");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos as notas fiscais. 
     *
     * @param 
     * @throws IOException
     */
    protected void mostrarTabelaNotasFiscais() throws IOException {
        if (!notasFiscais.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaNotasFiscais.fxml"));
            Parent root = (Parent) loader.load();
            TabelaNotasFiscaisController tabelaNotasFiscaisController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaNotasFiscaisController.setNotasFiscais(notasFiscais);
            stage.showAndWait();
            this.notasFiscais = tabelaNotasFiscaisController.getNotasFiscais();

        }else{
             Alert erro = new Alert(Alert.AlertType.INFORMATION);
                    erro.setTitle("Ver Notas Fiscais");
                    erro.setHeaderText("Nenhuma NF foi cadastrada até o momento!");
                    erro.showAndWait();
        }
    }
    
    /**
     * Método utilizado pelo botão ver notas fiscais. Mostra a tabela com as nfs já cadastradas.
     */
    @FXML
    private void verNotasFiscaisNaTabela(ActionEvent action) throws IOException{
        mostrarTabelaNotasFiscais();
    }
    
     /**
     * Metodo que através do número busca e lista os fretes (Cte's) cadastrados ou
     * oferece ao usuário a opção de inserção de um novo frete.
     *
     * @param action
     */
    @FXML
    protected void buscarCte(ActionEvent action) throws IOException, ClassNotFoundException {
        int numeroInformado = Integer.parseInt(txtPlaca.getText());

        CteDAO cteDAO = new CteDAO();
         ArrayList<Cte> fretes = new ArrayList<Cte>();
        // fretes = cteDAO.buscarCtePeloNumero(numeroInformado);

       // mostrarCte(fretes);
        
    }
}
