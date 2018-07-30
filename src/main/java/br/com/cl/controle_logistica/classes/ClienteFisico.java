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
public class ClienteFisico extends Cliente{
    
    private int idClienteFisico;
    private SimpleStringProperty nomeCliente;
    private SimpleStringProperty cpf;
    private LocalDate dataNascimento;
    private SimpleStringProperty rg;
    
    public ClienteFisico() {
        super();
        this.nomeCliente = new SimpleStringProperty();
        this.cpf = new SimpleStringProperty();
        this.rg = new SimpleStringProperty();
    }

    public ClienteFisico(int idClienteFisico, String nomeCliente, String cpf, LocalDate dataNascimento, String endereco,
            String cidade, String estado, String cep, String bairro, LocalDate dataCadastro, String rg, String email, 
            String contato, String contato1) {
        
        super(endereco, cidade, estado, cep, bairro, dataCadastro, email, contato, contato1);
        this.idClienteFisico = idClienteFisico;
        this.nomeCliente  = new SimpleStringProperty(nomeCliente);
        this.cpf = new SimpleStringProperty(cpf);
        this.dataNascimento = dataNascimento;
        this.rg = new SimpleStringProperty(rg);
    }

    public int getIdClienteFisico() {
        return idClienteFisico;
    }

    public void setIdClienteFisico(int idClienteFisico) {
        this.idClienteFisico = idClienteFisico;
    }

    public String getNomeCliente() {
        return nomeCliente.get();
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente.set(nomeCliente);
    }

    public String getCpf() {
        return cpf.get();
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg.get();
    }

    public void setRg(String rg) {
        this.rg.set(rg);
    }
    
    
}
