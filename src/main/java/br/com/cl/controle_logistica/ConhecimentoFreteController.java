/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.CteDAO;
import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.Nf;
import br.com.cl.controle_logistica.classes.Veiculo;
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
    
    private Cte cte = new Cte();
    
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
        limparCamposNF();
    }
    
    
    /**
     * 
     */
    private void limparCamposNF(){
        numeroNf.setText("");
        valorNf.setText("");
        chaveAcessoNf.setText("");
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
    
        String valor = valorNf.getText();
        BigDecimal zero = new BigDecimal(00);
        BigDecimal vl = new BigDecimal(valor.replace(",", "."));
         if (valorNf.getText().isEmpty()) {
            valorNf.setStyle("-fx-border-color:red");
            contador++;
        }else if(vl.intValue() <= 0){
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
        ArrayList<Cte> fretes = new ArrayList<Cte>();

        if(!txtPlaca.getText().isEmpty()){
            int numeroInformado = Integer.parseInt(txtPlaca.getText());

            CteDAO cteDAO = new CteDAO();
            fretes = cteDAO.buscarCtePeloNumero(numeroInformado);
        }
        
        mostrarCTes(fretes);
        
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * fretes (CTe) com o número informada pelo usuário.
     *
     * @param cte's
     * @throws IOException
     */
    private void mostrarCTes(ArrayList<Cte> ctes) throws IOException {
        if (!ctes.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaConhecimento.fxml"));
            Parent root = (Parent) loader.load();
            TabelaConhecimentoController tabelaConhecimentoController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaConhecimentoController.setCte(ctes);
            stage.showAndWait();
            this.cte = tabelaConhecimentoController.getCteSelecionado();
            if (this.cte != null) {
                popularCamposCte();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar CT-e");
            confirmacao.setHeaderText("Nenhum CT-e encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo CT-e!");
            confirmacao.showAndWait();
        }
    }
    
    /**
     * Método que limpa o(s) campo(s) na tela de Cte.
     */
    private void limparCampos() {
        txtNumeroCte.setText("");
        txtValorCte.setText("");
        dataEmissaoCte.setValue(null);
        chaveAcessoCte.setText("");
        produtoCte.setText("");
        pesoBrutoCte.setText("");
        pesoLiquidoCte.setText("");
        volumeCte.setText("");
        especieCte.setText("");
        observacaoCte.setText("");
        numeroNf.setText("");
        valorNf.setText("");
        chaveAcessoNf.setText("");
        
    }
    
    /**
     * Método que limpa todos os campos e cancela as operações.
     * @param action 
     */
    @FXML
    private void cancelarOperacao(ActionEvent action){
        limparCampos();
    }
    
     /**
     * Método que coleta os dados de todos os campos da tela e seta no objeto
     * cte.
     */
    private boolean getAtributosCte() {
        if (verificarCamposVazios()) {
            
            cte.setNumeroCte(Integer.valueOf(txtNumeroCte.getText()));
            String valor = txtValorCte.getText();
            BigDecimal valorInformado = new BigDecimal(valor.replace(",", "."));
            cte.setValor(valorInformado);
            cte.setDataEmissao(dataEmissaoCte.getValue());
            cte.setChaveAcesso(chaveAcessoCte.getText());
            cte.setProduto(produtoCte.getText().toUpperCase());
            cte.setPesoBruto(Double.parseDouble(pesoBrutoCte.getText()));
            cte.setPesoLiquido(Double.parseDouble(pesoLiquidoCte.getText()));
            cte.setVolume(Double.parseDouble(volumeCte.getText()));
            cte.setEspecie(especieCte.getText().toUpperCase());
            cte.setObservacao(observacaoCte.getText().toUpperCase());
            cte.setNotasFiscais(notasFiscais);
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Método que valida se os campos estão vazios.
     */
    private boolean verificarCamposVazios() {
        boolean retorno = true;
        int contador = 0;
        if (txtNumeroCte.getText().isEmpty()) {
            txtNumeroCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtNumeroCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
    
         if (txtValorCte.getText().isEmpty()) {
            txtValorCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            txtValorCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (dataEmissaoCte.getValue().equals(null)) {
            dataEmissaoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            dataEmissaoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (chaveAcessoCte.getText().isEmpty()) {
            chaveAcessoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            chaveAcessoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
          
          if (produtoCte.getText().isEmpty()) {
            produtoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            produtoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
           if (pesoBrutoCte.getText().isEmpty()) {
            pesoBrutoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            pesoBrutoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
          if (pesoLiquidoCte.getText().isEmpty()) {
            pesoLiquidoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            pesoLiquidoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }  
          
          if (volumeCte.getText().isEmpty()) {
            volumeCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            volumeCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
          if (especieCte.getText().isEmpty()) {
            especieCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            especieCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
          if (observacaoCte.getText().isEmpty()) {
            observacaoCte.setStyle("-fx-border-color:red");
            contador++;
        } else {
            observacaoCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        } 
          
         if(contador<=-10){
            retorno = true;
         }else{
             retorno = false;
         } 
         
         if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar CTe");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }
    }
    
     /**
     * Método que popula os campos da tela com as informações do cte
     * selecionado.
     */
    @FXML
    private void popularCamposCte() {
        
        txtNumeroCte.setText(String.valueOf(this.cte.getNumeroCte()));
        txtValorCte.setText(String.valueOf(cte.getValor()));
        dataEmissaoCte.setValue(cte.getDataEmissao());
        chaveAcessoCte.setText(cte.getChaveAcesso());
        produtoCte.setText(cte.getProduto());
        pesoBrutoCte.setText(String.valueOf(cte.getProduto()));
        pesoLiquidoCte.setText(String.valueOf(cte.getPesoLiquido()));
        volumeCte.setText(String.valueOf(cte.getVolume()));
        especieCte.setText(cte.getEspecie());
        observacaoCte.setText(cte.getObservacao());
        
        this.notasFiscais = cte.getNotasFiscais();
    }
    
    
    
    /**
     * Metodo utilizado para setar o cte selecionado na tabela no
     * cte dessa classe.
     *
     * @param cte
     */
    public void setCte(Cte cte) {
        this.cte = cte;

    }
    
    /**
     * Método utilizado pelo botao salvar. Salva um novo cte ou as
     * alterações feitas em um já existente.
     *
     * @param action
     */
    @FXML
    protected void salvarCTe(ActionEvent action) {
        CteDAO cteDAO = new CteDAO();

        if (this.cte == null) {
            this.cte = new Cte();
        }

        if (cte.getNumeroCte() == 0 && getAtributosCte()) {
            cteDAO.salvarCTe(cte);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar CT-e");
            confirmacao.setHeaderText("CT-e Cadastrado com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (getAtributosCte() && cte.getNumeroCte() > 0) {
            if (cteDAO.atualizarCTe(cte)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar CT-e");
                confirmacao.setHeaderText("CT-e Atualizado com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
    }
}
