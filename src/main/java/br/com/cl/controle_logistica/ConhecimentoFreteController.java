/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import br.com.cl.controle_logistica.DAO.CteDAO;
import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.CteCliente;
import br.com.cl.controle_logistica.classes.Nf;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
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
    private Label lbNomeR;
    
    @FXML
    private Label lbCnpjCpfR;
    
    @FXML
    private Label lbEndR;
    
    @FXML
    private Label lbCidadeR;
    
    @FXML
    private Label lbEstadoR;
    
    @FXML
    private Label lbNomeD;
    
    @FXML
    private Label lbCnpjCpfD;
    
    @FXML
    private Label lbEndD;
    
    @FXML
    private Label lbCidadeD;
    
    @FXML
    private Label lbEstadoD;
    
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
    private DatePicker dataEmissaoCte = new DatePicker();

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
    
    private Cliente clienteRemetente = new Cliente();
    
    private Cliente clienteDestinatario = new Cliente();
   
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
     * Método simples que desmarca o remetente quando o destinatario é selecionado.
     * @param action 
     */
    @FXML
    private void desmarcarRemetente(ActionEvent action){
        tomadorServicoRemetente.setSelected(false);
    }
    
    /**
     *  Método simples que desmarca o destinatario quando o remetente é selecionado.
     * @param action 
     */
    @FXML
    private void desmarcarDestinatario(ActionEvent action){
        tomadorServicoDestinatario.setSelected(false);
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
    
         if (valorNf.getText().isEmpty()) {
            valorNf.setStyle("-fx-border-color:red");
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
        tomadorServicoDestinatario.setSelected(false);
        tomadorServicoRemetente.setSelected(true);
        lbNomeD.setText("");
        lbCnpjCpfD.setText("");
        lbEndD.setText("");
        lbCidadeD.setText("");
        lbEstadoD.setText("");
        lbNomeD.setText("");
        lbCnpjCpfD.setText("");
        lbEndD.setText("");
        lbCidadeD.setText("");
        lbEstadoD.setText("");
        txtPlaca.setText("");
        
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
            
            String valor = txtValorCte.getText();
            BigDecimal valorInformado = new BigDecimal(valor.replace(",", "."));
            cte.setValor(valorInformado);
            cte.setDataEmissao(dataEmissaoCte.getValue());
            cte.setChaveAcesso(chaveAcessoCte.getText());
            cte.setProduto(produtoCte.getText().toUpperCase());
            cte.setPesoBruto(Double.parseDouble(pesoBrutoCte.getText().replace(",", ".")));
            cte.setPesoLiquido(Double.parseDouble(pesoLiquidoCte.getText().replace(",", ".")));
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
        
        
         if (txtValorCte.getText().isEmpty()) {
            txtValorCte.setStyle("-fx-border-color:red");
            contador++;
         } else {
            txtValorCte.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
                  
          if (dataEmissaoCte.getValue() == null) {
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
          
        if(clienteDestinatario.getClienteFisico().getIdClienteFisico() <= 0 && clienteDestinatario.getClienteJuridico().getIdClienteJuridico() <= 0){
            contador++;
        }  else{
            contador--;
        }
        
        if(clienteRemetente.getClienteFisico().getIdClienteFisico() <= 0 && clienteRemetente.getClienteJuridico().getIdClienteJuridico() <= 0){
            contador++;
        }else{
            contador--;
        }
        
         if(contador<=-11){
            retorno = true;
         }else{
             retorno = false;
         } 
         
         if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar CTe");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios!.\nInforme os clientes!");
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
        txtValorCte.setText(String.valueOf(cte.getValor()).replace(".", ","));
        dataEmissaoCte.setValue(cte.getDataEmissao());
        chaveAcessoCte.setText(cte.getChaveAcesso());
        produtoCte.setText(cte.getProduto());
        pesoBrutoCte.setText(String.valueOf(cte.getPesoBruto()).replace(".", ","));
        pesoLiquidoCte.setText(String.valueOf(cte.getPesoLiquido()).replace(".", ","));
        volumeCte.setText(String.valueOf(cte.getVolume()));
        especieCte.setText(cte.getEspecie());
        observacaoCte.setText(cte.getObservacao());
        
        this.notasFiscais = cte.getNotasFiscais();
        
        if(cte.getClienteRemetente().getCliente().getClienteFisico() != null){
        
            popularCamposDadosRemetenteClienteFisico(cte.getClienteRemetente().getCliente());
            
        }else if(cte.getClienteRemetente().getCliente().getClienteJuridico() != null){
            popularCamposDadosRemetenteClienteJuridico(cte.getClienteRemetente().getCliente());
            
        }
        
        if(cte.getClienteDestinatario().getCliente().getClienteFisico() != null){
            popularCamposDadosDestinatarioClienteFisico(cte.getClienteDestinatario().getCliente());
            
        }else if(cte.getClienteDestinatario().getCliente().getClienteJuridico() != null){
            popularCamposDadosDestinatarioClienteJuridico(cte.getClienteDestinatario().getCliente());
            
        }
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
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * clientes consultados pelo usuário.
     *
     * @param action
     * @throws IOException
     */
    @FXML
    protected void mostrarTabelaClientesRemetente(ActionEvent action) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaClientesConhecimentoFrete.fxml"));
            Parent root = (Parent) loader.load();
            TabelaClientesConhecimentoFreteController tabelaClientesConhecimentoFreteController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
            
            this.clienteRemetente = tabelaClientesConhecimentoFreteController.getClienteSelecionadoFisico();
            
            if(clienteRemetente.getClienteFisico().getIdClienteFisico() > 0){
                popularCamposDadosRemetenteClienteFisico(clienteRemetente);
            }else if(clienteRemetente.getClienteJuridico().getIdClienteJuridico() > 0){
                popularCamposDadosRemetenteClienteJuridico(clienteRemetente);
            }
          
        }
    
    /**
     * Metodo que popula os campos referentes aos dados do cliente, caso seja fisico na area de remetente.
     * @param cliente 
     */
    private void popularCamposDadosRemetenteClienteFisico(Cliente cliente){
        lbNomeR.setText(cliente.getClienteFisico().getNomeCliente().toUpperCase());
        lbCnpjCpfR.setText(cliente.getClienteFisico().getCpf());
        lbEndR.setText(cliente.getEndereco().toUpperCase());
        lbCidadeR.setText(cliente.getCidade().toUpperCase());
        lbEstadoR.setText(cliente.getEstado().toUpperCase());
        
        Boolean tomador = tomadorServicoRemetente.isSelected();
        CteCliente clienteCte = new CteCliente(cte, cliente, tomador);
        cte.setClienteRemetente(clienteCte);
    }
   
     /**
     * Metodo que popula os campos referentes aos dados do cliente, caso seja juridico na area de remetente.
     * @param cliente 
     */
    private void popularCamposDadosRemetenteClienteJuridico(Cliente cliente){
        lbNomeR.setText(cliente.getClienteJuridico().getNomeFantasia().toUpperCase());
        lbCnpjCpfR.setText(cliente.getClienteJuridico().getCnpj());
        lbEndR.setText(cliente.getEndereco().toUpperCase());
        lbCidadeR.setText(cliente.getCidade().toUpperCase());
        lbEstadoR.setText(cliente.getEstado().toUpperCase());
        
        Boolean tomador = tomadorServicoRemetente.isSelected();
        
        CteCliente clienteCte = new CteCliente(cte, cliente, tomador);
        cte.setClienteRemetente(clienteCte);
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * clientes consultados pelo usuário.
     *
     * @param action
     * @throws IOException
     */
    @FXML
    protected void mostrarTabelaClientesDestinatario(ActionEvent action) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaClientesConhecimentoFrete.fxml"));
            Parent root = (Parent) loader.load();
            TabelaClientesConhecimentoFreteController tabelaClientesConhecimentoFreteController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
           
            this.clienteDestinatario = tabelaClientesConhecimentoFreteController.getClienteSelecionadoJuridico();
            
            if(clienteDestinatario.getClienteFisico().getIdClienteFisico() > 0){
                popularCamposDadosDestinatarioClienteFisico(clienteDestinatario);
            }else if(clienteDestinatario.getClienteJuridico().getIdClienteJuridico() > 0){
                popularCamposDadosDestinatarioClienteJuridico(clienteDestinatario);
            }
        }
    
    /**
     * Metodo que popula os campos referentes aos dados do cliente, caso seja fisico na area de destinatario.
     * @param cliente 
     */
    private void popularCamposDadosDestinatarioClienteFisico(Cliente cliente){
        lbNomeD.setText(cliente.getClienteFisico().getNomeCliente().toUpperCase());
        lbCnpjCpfD.setText(cliente.getClienteFisico().getCpf());
        lbEndD.setText(cliente.getEndereco().toUpperCase());
        lbCidadeD.setText(cliente.getCidade().toUpperCase());
        lbEstadoD.setText(cliente.getEstado().toUpperCase());
        
        Boolean tomador = tomadorServicoDestinatario.isSelected();
        
        CteCliente clienteCte = new CteCliente(cte, cliente, tomador);
        cte.setClienteDestinatario(clienteCte);
    }
   
     /**
     * Metodo que popula os campos referentes aos dados do cliente, caso seja juridico na area de destinatario.
     * @param cliente 
     */
    private void popularCamposDadosDestinatarioClienteJuridico(Cliente cliente){
        lbNomeD.setText(cliente.getClienteJuridico().getNomeFantasia().toUpperCase());
        lbCnpjCpfD.setText(cliente.getClienteJuridico().getCnpj());
        lbEndD.setText(cliente.getEndereco().toUpperCase());
        lbCidadeD.setText(cliente.getCidade().toUpperCase());
        lbEstadoD.setText(cliente.getEstado().toUpperCase());
        
        Boolean tomador = tomadorServicoDestinatario.isSelected();
        
        CteCliente clienteCte = new CteCliente(cte, cliente, tomador);
        cte.setClienteDestinatario(clienteCte);
    }
    
    /**
     * Metodo utilizado pelo botao Excluir que executa a exclusão do cte selecionado.
     * @param action 
     */
    @FXML
    private void deletarCTe(ActionEvent action) throws IOException{
        if(this.cte.getNumeroCte() > 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmacaoAcaoCte.fxml"));
            Parent root = (Parent) loader.load();
            ConfirmacaoAcaoCteController confirmacaoAcaoCteController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            confirmacaoAcaoCteController.setCte(cte);
            stage.showAndWait();
            limparCampos();
            }
        }
    }
    

