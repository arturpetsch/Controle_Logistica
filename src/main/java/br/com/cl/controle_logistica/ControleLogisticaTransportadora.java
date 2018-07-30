/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Artur
 */
public class ControleLogisticaTransportadora extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Entrar no Sistema");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
      *@param args the command line arguments
     **/
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
