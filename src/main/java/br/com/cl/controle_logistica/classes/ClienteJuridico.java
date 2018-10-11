/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class ClienteJuridico {

    private int idClienteJuridico;
    private SimpleStringProperty nomeFantasia;
    private SimpleStringProperty razaoSocial;
    private SimpleStringProperty cnpj;
    private SimpleStringProperty ie;
    
    public ClienteJuridico(){
        super();
        this.nomeFantasia  = new SimpleStringProperty();
        this.razaoSocial = new SimpleStringProperty();
        this.cnpj = new SimpleStringProperty();
        this.ie = new SimpleStringProperty();
    }

    public ClienteJuridico(int idClienteJuridico, String nomeFantasia, String razaoSocial, String cnpj, String ie, String endereco,
            String cidade, String estado, String cep, String bairro, LocalDate dataCadastro, String email, 
            String contato, String contato1) {
        this.idClienteJuridico = idClienteJuridico;
        this.nomeFantasia = new SimpleStringProperty(nomeFantasia);
        this.razaoSocial  = new SimpleStringProperty(razaoSocial);
        this.cnpj  = new SimpleStringProperty(cnpj);
        this.ie = new SimpleStringProperty(ie);
    }

    public int getIdClienteJuridico() {
        return idClienteJuridico;
    }

    public void setIdClienteJuridico(int idClienteJuridico) {
        this.idClienteJuridico = idClienteJuridico;
    }

    public String getNomeFantasia() {
        return nomeFantasia.get();
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia.set(nomeFantasia);
    }

    public String getRazaoSocial() {
        return razaoSocial.get();
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial.set(razaoSocial);
    }

    public String getCnpj() {
        return cnpj.get();
    }

    public void setCnpj(String cnpj) {
        this.cnpj.set(cnpj);
    }

    public String getIe() {
        return ie.get();
    }

    public void setIe(String ie) {
        this.ie.set(ie);
    }
    
    
}
