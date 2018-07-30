/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Artur
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnEsqueceuSenha;

    @FXML
    private PasswordField txtSenhaUsuario;

    @FXML
    private TextField txtNomeUsuario;

    @FXML
    private ImageView imagemTelaLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image icone = new Image(getClass().getResourceAsStream("/Imagens/usuario_imagem.png"));
        imagemTelaLogin.setImage(icone);
    }

    @FXML
    protected void entrarNoSistema(ActionEvent action) throws IOException {
        Parent telaPrincipal = FXMLLoader.load(getClass().getResource("/fxml/telaPrincipal.fxml"));
                
                Scene scene = new Scene(telaPrincipal);
                Stage stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
               
                stage.setTitle("Logistica");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.show();
    }
}
