/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;



import br.com.cl.controle_logistica.DAO.VeiculoDAO;
import br.com.cl.controle_logistica.classes.Veiculo;
import br.com.cl.controle_logistica.classes.Viagem;
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
    protected GoogleMapView mapView;// = new GoogleMapView("pt-BR", "My-Google-Map-API-Key");

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
            //Double km = directionsResult.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
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
    
    @FXML
    private void coletarDadosViagem(){
        
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
            }
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
            confirmacao.setTitle("Buscar Veiculo");
            confirmacao.setHeaderText("Nenhum veiculo encontrado.\nPor favor, refaça sua pesquisa ou cadastre um novo veiculo!");
            confirmacao.showAndWait();
        }
    }
    
    @FXML
    private void popularCamposDadosVeiculo(){
        placaVeiculo.setText(veiculo.getPlaca());
    }
    
    
}
