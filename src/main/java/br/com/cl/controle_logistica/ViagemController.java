/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;



import br.com.cl.controle_logistica.DAO.CteDAO;
import br.com.cl.controle_logistica.DAO.VeiculoDAO;
import br.com.cl.controle_logistica.DAO.ViagemDAO;
import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.Veiculo;
import br.com.cl.controle_logistica.classes.Viagem;
import br.com.cl.controle_logistica.classes.ViagemDespesa;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class ViagemController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback{

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtBuscaVeiculo;
    
    @FXML
    private Button btnBuscarVeiculo;
    
    @FXML
    private Button btnIncluirCustoPrevisto;
    
    @FXML
    private Button btnIncluirCustoRealizado;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnDeletar;
    
    @FXML
    private Button btnSalvar;
    
    protected DirectionsService directionsService;
    
    protected DirectionsPane directionsPane;

    protected StringProperty from = new SimpleStringProperty();
    
    protected StringProperty to = new SimpleStringProperty();

    @FXML
    protected GoogleMapView mapView;

    @FXML
    protected TextField fromTextField;

    @FXML
    protected TextField toTextField;
    
    @FXML
    protected TextField placaVeiculo;
    
    @FXML
    protected TextField numeroCTE;
    
    @FXML
    protected Button btnIncluirCte;
    
    @FXML
    private TextField totalKmPrevisto;
    
    @FXML
    private TextField valorViagemPrevisto;
    
    @FXML
    private TextField totalKmRealizado;
    
    @FXML
    private TextField valorGanhoPrevisto;
    
    @FXML
    private TextField valorGanhoRealizado;
    
    @FXML
    private TextField valorViagemRealizado;
    
    Viagem viagem = new Viagem();
    
    Veiculo veiculo = new Veiculo();
    
    ArrayList<ViagemDespesa> despesasPrevistas = new ArrayList<>();
    
    ArrayList<ViagemDespesa> despesasRealizadas = new ArrayList<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image cancel = new Image(getClass().getResourceAsStream("/Imagens/cancel.png"));
        Image trash = new Image(getClass().getResourceAsStream("/Imagens/trash.png"));
        Image save = new Image(getClass().getResourceAsStream("/Imagens/save.png"));
        Image search = new Image(getClass().getResourceAsStream("/Imagens/search.png"));
        Image plus = new Image(getClass().getResourceAsStream("/Imagens/plus.png"));
        
        btnCancelar.setGraphic(new ImageView(cancel));
        btnDeletar.setGraphic(new ImageView(trash));
        btnSalvar.setGraphic(new ImageView(save));
        btnBuscarVeiculo.setGraphic(new ImageView(search));
        btnIncluirCustoPrevisto.setGraphic(new ImageView(plus));
        btnIncluirCustoRealizado.setGraphic(new ImageView(plus));
        btnIncluirCte.setGraphic(new ImageView(search));
        
        String key = "AIzaSyBwMONSVg9lwK1HVCFNugI3ESG1OhPKkSc";
        mapView.setKey(key);
       mapView.addMapInializedListener(this);
        to.bindBidirectional(toTextField.textProperty());
        from.bindBidirectional(fromTextField.textProperty());
    }    

    @FXML
    private void toTextFieldAction(ActionEvent event) {
        DirectionsRequest request = new DirectionsRequest(from.get(), to.get(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
        directionsService.processResponse(request, this);
       
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        DirectionsResult directionsResult = results;
       
        try {
            String km = directionsResult.getRoutes().get(0).getLegs().get(0).getDistance().getText().replace("km", "");
            totalKmPrevisto.setText(km.toString());
        } catch (Exception e) {
            
        }
    }
    
    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(-25.2417, -53.97649))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        
    }
    
    /**
     * Metodo que coleta os dados da viagem para depois disso salvar.
     */
    @FXML
    private boolean coletarDadosViagem(){
         if (verificarCamposVazios()) {
            viagem.setQtdeKmPrevisto(Double.parseDouble(totalKmPrevisto.getText()));
            
            String valorInformado = valorViagemPrevisto.getText();
            BigDecimal valor = new BigDecimal(valorInformado.replace(",", "."));
            viagem.setValorTotalGastoPrevisto(valor);
            
            valorInformado = valorGanhoPrevisto.getText();
            BigDecimal valorGanhoP = new BigDecimal(valorInformado.replace(",", "."));
            viagem.setValorTotalGanhoPrevisto(valorGanhoP);
            
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Metodo que através da placa busca e lista o veiculo cadastrado ou oferece
     * ao usuário a opção de inserção de um novo veículo.
     *
     * @param action
     */
    @FXML
    protected void buscarVeiculoPorPlaca(ActionEvent action) throws IOException {
        String placaInformada = txtBuscaVeiculo.getText();

        
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        veiculos = veiculoDAO.consultarVeiculoPorPlaca(placaInformada);

        mostrarVeiculos(veiculos);
    }
    
    /**
     * Método que chama o Stage onde está a tabela que lista todos os
     * veiculos com a placa informada pelo usuário.
     *
     * @param veiculos
     * @throws IOException
     */
    private void mostrarVeiculos(ArrayList<Veiculo> veiculos) throws IOException {
        if (!veiculos.isEmpty()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaVeiculos.fxml"));
            Parent root = (Parent) loader.load();
            TabelaVeiculosController tabelaVeiculosController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaVeiculosController.setVeiculo(veiculos);
            stage.showAndWait();
            this.veiculo = tabelaVeiculosController.getVeiculoSelecionado();
            if (this.veiculo != null) {
                popularCamposDadosVeiculo();
                mostrarViagens();
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Veiculo");
            confirmacao.setHeaderText("Nenhum veiculo encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo veiculo!");
            confirmacao.showAndWait();
        }
    }
    
    /**
     * Metodo que busca e mostra as viagens do veiculo selecionado.
     * @throws IOException 
     */
    private void mostrarViagens() throws IOException{
        ViagemDAO viagemDAO = new ViagemDAO();
        
        ArrayList<Viagem> viagens = new ArrayList<Viagem>();
        viagens = viagemDAO.consultarViagemPorIdVeiculo(this.veiculo.getIdVeiculo());
        
        if(!viagens.isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabelaViagens.fxml"));
            Parent root = (Parent) loader.load();
            TabelaViagensController tabelaViagensController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            tabelaViagensController.setViagens(viagens);
            stage.showAndWait();
            this.viagem = tabelaViagensController.getViagemSelecionada();
            if (this.viagem != null) {
                popularCamposDadosViagem();
            }
        }else{
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Viagens");
            confirmacao.setHeaderText("Nenhuma viagem encontrada.\nPor favor, refaça sua pesquisa ou cadastre uma nova viagem!");
            confirmacao.showAndWait();
        }
    }
    
    /**
     * Metodo que popula os campos da tela com os dados da viagem selecionada.
     */
    @FXML
    private void popularCamposDadosViagem(){
        numeroCTE.setText(String.valueOf(this.viagem.getCte().getNumeroCte()));
        totalKmPrevisto.setText(this.viagem.getQtdeKmPrevisto().toString());
        valorViagemPrevisto.setText(this.viagem.getValorTotalGastoPrevisto().toString());
        valorGanhoPrevisto.setText(this.viagem.getValorTotalGanhoPrevisto().toString());
        totalKmRealizado.setText(this.viagem.getQtdeKmRealizado().toString());
        valorViagemRealizado.setText(this.viagem.getValorTotalGastoRealizado().toString());
        valorGanhoRealizado.setText(this.viagem.getValorTotalGanhoRealizado().toString());
        
        int i = 0;
        while(viagem.getDespesas().size() > i){
            if(viagem.getDespesas().get(i).getTipoDespesa().equals("Previsto")){
                despesasPrevistas.add(viagem.getDespesas().get(i));
            }else{
                despesasRealizadas.add(viagem.getDespesas().get(i));
            }
            i++;
        }
    }
    
    /**
     * Método que popula o campo com a placa do veiculo.
     */
    @FXML
    private void popularCamposDadosVeiculo(){
        placaVeiculo.setText(veiculo.getPlaca());
    }
    
    /**
     * Metodo que abre a tela para inclusão de novas despesas previstas;
     * @param action
     * @throws IOException 
     */
    @FXML
    private void incluirDespesa(ActionEvent action) throws IOException{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/despesaViagem.fxml"));
            Parent root = (Parent) loader.load();
            DespesaViagemController despesaViagemController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            despesaViagemController.setDespesas(despesasPrevistas);
            
            stage.showAndWait();
            despesasPrevistas = despesaViagemController.getDespesas();
            BigDecimal valorTotal = calcularDespesas(despesasPrevistas);
            valorViagemPrevisto.setText(valorTotal.toString().replace(".", ","));
            
            
    }
    
    /**
     * Metodo que recebe um arraylist de despesas, calcula e retorna o valor total da despesa daquela viagem.
     * 
     * @param despesas
     * @return 
     */
    private BigDecimal calcularDespesas(ArrayList<ViagemDespesa> despesas){
        BigDecimal valorTotal = new BigDecimal(0);
        
       valorTotal = despesas.stream()
               .map(ViagemDespesa::getValor)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
       
               return valorTotal;
    }
    
   /**
     * Metodo que abre a tela para inclusão de novas despesas realizadas;
     * @param action
     * @throws IOException 
     */
    @FXML
    private void incluirDespesaRealizadas(ActionEvent action) throws IOException{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/despesaViagemRealizada.fxml"));
            Parent root = (Parent) loader.load();
            DespesaViagemRealizadaController despesaViagemRealizadaController = loader.getController();
            Scene alert = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(alert);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            despesaViagemRealizadaController.setDespesas(despesasRealizadas);
            
            stage.showAndWait();
            despesasRealizadas = despesaViagemRealizadaController.getDespesas();
            BigDecimal valorTotal = calcularDespesas(despesasRealizadas);
            valorViagemRealizado.setText(valorTotal.toString().replace(".", ","));
            
    }
    
    
    
    /**
     * Metodo que cancela todas as operações e limpa todos os campos, arrays e objetos.
     * @param action 
     */
    @FXML
    public void cancelarOperacao(ActionEvent action){
        limparCampos();
    }
    
     /**
     * Metodo que através do número busca e lista os fretes (Cte's) cadastrados ou
     * .
     *
     * @param action
     */
    @FXML
    protected void buscarCte(ActionEvent action) throws IOException, ClassNotFoundException {
        ArrayList<Cte> fretes = new ArrayList<Cte>();

        if(!numeroCTE.getText().isEmpty()){
            int numeroInformado = Integer.parseInt(numeroCTE.getText());

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
            this.viagem.setCte(tabelaConhecimentoController.getCteSelecionado()); 
            if (this.viagem.getCte() != null) {
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
     * Método que insere o numero do cte selecionado.
     */
    @FXML
    private void popularCamposCte(){
        numeroCTE.setText(String.valueOf(this.viagem.getCte().getNumeroCte()));
        
    }
    
    /**
     * Método que valida se os campos obrigatorios não estão vazios.
     */
    private boolean verificarCamposVazios() {
        boolean retorno = true;
        int contador = 0;
        if (placaVeiculo.getText().isEmpty()) {
            placaVeiculo.setStyle("-fx-border-color:red");
            contador++;
        } else {
            placaVeiculo.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (viagem.getCte() == null) {
            numeroCTE.setStyle("-fx-border-color:red");
            contador++;
        } else {
            numeroCTE.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

        if (totalKmPrevisto.getText().isEmpty()) {
            totalKmPrevisto.setStyle("-fx-border-color:red");
            contador++;
        } else {
            totalKmPrevisto.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }

         if (valorViagemPrevisto.getText().isEmpty()) {
            valorViagemPrevisto.setStyle("-fx-border-color:red");
            contador++;
        } else {
            valorViagemPrevisto.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
         
          if (valorGanhoPrevisto.getText().isEmpty()) {
            valorGanhoPrevisto.setStyle("-fx-border-color:red");
            contador++;
        } else {
            valorGanhoPrevisto.setStyle("-fx-border-color:#bbaFFF");
            contador--;
        }
          
        if(!totalKmRealizado.getText().isEmpty()){
            if(valorViagemRealizado.getText().isEmpty()){
                valorViagemRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                valorViagemRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador --;
            }
            
            if(valorGanhoRealizado.getText().isEmpty()){
                valorGanhoRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                valorGanhoRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador--;
            }
        }
        
        if(!valorViagemRealizado.getText().isEmpty()){
            if(totalKmRealizado.getText().isEmpty()){
                totalKmRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                totalKmRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador --;
            }
            
            if(valorGanhoRealizado.getText().isEmpty()){
                valorGanhoRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                valorGanhoRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador--;
            }
        }
        
        
        if(!valorGanhoRealizado.getText().isEmpty()){
            if(valorViagemRealizado.getText().isEmpty()){
                valorViagemRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                valorViagemRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador --;
            }
            
            if(totalKmRealizado.getText().isEmpty()){
                totalKmRealizado.setStyle("-fx-border-color:red");
                contador++;
            }else{
                totalKmRealizado.setStyle("-fx-border-color:#bbaFFF");
                contador--;
            }
        }
        
        if (contador <= -7) {
            retorno = true;
        } else {
            retorno = false;
        }

        if (retorno) {
            return retorno;
        } else {
            Alert informacao = new Alert(Alert.AlertType.INFORMATION);
            informacao.setTitle("Salvar Viagem");
            informacao.setHeaderText("Por favor, informe os campos obrigatórios.");
            informacao.show();
            return retorno;
        }
    }
    
    /**
     * Metodo utilizado pela botao de salvar.
     * 
     */
    @FXML
    public void salvarManutencao(ActionEvent action) {
        ViagemDAO viagemDAO = new ViagemDAO();

        if (this.viagem == null) {
            this.viagem = new Viagem();
        }

        if (this.viagem.getIdViagem() == 0 && coletarDadosViagem()) {

            viagemDAO.salvarViagem(viagem);
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Salvar Viagem");
            confirmacao.setHeaderText("Viagem Cadastrada com Sucesso!");
            confirmacao.showAndWait();
            limparCampos();
        } else if (this.viagem.getIdViagem() > 0 && coletarDadosViagem()) {
            if (viagemDAO.alterarViagem(viagem)) {
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setTitle("Salvar Viagem");
                confirmacao.setHeaderText("Viagem Atualizada com Sucesso!");
                confirmacao.showAndWait();
                limparCampos();
            }
        }
    }
    
    /**
     * Metodo que limpa todos os campos da tela.
     */
    @FXML
    private void limparCampos(){
        txtBuscaVeiculo.setText("");
        fromTextField.setText("");
        toTextField.setText("");
        placaVeiculo.setText("");
        numeroCTE.setText("");
        totalKmPrevisto.setText("");
        valorViagemPrevisto.setText("");
        valorGanhoPrevisto.setText("");
        totalKmRealizado.setText("");
        valorViagemRealizado.setText("");
        valorGanhoRealizado.setText("");
        despesasPrevistas = new ArrayList<ViagemDespesa>();
        despesasRealizadas = new ArrayList<ViagemDespesa>();
        veiculo = new Veiculo();
        viagem = new Viagem();
    }
}
