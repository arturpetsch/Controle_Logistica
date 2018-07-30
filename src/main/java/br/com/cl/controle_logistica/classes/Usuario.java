/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Usuario {
    private int idUsuario;
    private SimpleBooleanProperty tipoUsuario;
    private SimpleStringProperty senha;
    private Funcionario funcionario;
    
}
