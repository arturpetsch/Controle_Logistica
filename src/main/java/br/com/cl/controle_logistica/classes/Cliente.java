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


public class Cliente {
    

    protected SimpleStringProperty endereco;
    protected SimpleStringProperty cidade;
    protected SimpleStringProperty estado;
    protected SimpleStringProperty cep;
    protected SimpleStringProperty bairro;
    protected LocalDate dataCadastro;
    protected SimpleStringProperty email;
    protected SimpleStringProperty contato;
    protected SimpleStringProperty contato1;
    
    public Cliente(String endereco, String cidade, String estado, String cep, String bairro, LocalDate dataCadastro, 
            String email, String contato, String contato1){
        this.endereco = new SimpleStringProperty(endereco);
        this.cidade = new SimpleStringProperty(cidade);
        this.estado = new SimpleStringProperty(estado);
        this.cep = new SimpleStringProperty(cep);
        this.bairro = new SimpleStringProperty(bairro);
        this.dataCadastro = dataCadastro;
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.contato1 = new SimpleStringProperty(contato1);
    }

    public Cliente(){
        this.endereco = new SimpleStringProperty();
        this.cidade = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.cep = new SimpleStringProperty();
        this.bairro = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.contato = new SimpleStringProperty();
        this.contato1 = new SimpleStringProperty();
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public String getCidade() {
        return cidade.get();
    }

    public void setCidade(String cidade) {
        this.cidade.set(cidade);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getCep() {
        return cep.get();
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public String getBairro() {
        return bairro.get();
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getContato() {
        return contato.get();
    }

    public void setContato(String contato) {
        this.contato.set(contato);
    }

    public String getContato1() {
        return contato1.get();
    }

    public void setContato1(String contato1) {
        this.contato1.set(contato1);
    }

    
    
    
}
