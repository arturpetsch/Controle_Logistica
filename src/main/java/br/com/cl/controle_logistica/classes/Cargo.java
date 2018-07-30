/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Cargo {
    private int idCargo;
    private SimpleStringProperty descricao;

    public Cargo() {
         this.idCargo = idCargo;
        this.descricao = new SimpleStringProperty();
    }

    
    public Cargo(int idCargo, String descricao) {
        this.idCargo = idCargo;
        this.descricao = new SimpleStringProperty(descricao);
    }

   
    
    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }
    
    
}
