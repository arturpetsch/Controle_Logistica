/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Funcionario {

    private int idFuncionario;
    private SimpleStringProperty nome;
    private SimpleStringProperty cpf;
    private SimpleStringProperty rg;
    private LocalDate dataNascimento;
    private BigDecimal salario;
    private SimpleFloatProperty porcentagem;
    private LocalDate dataAdmissao;
    private LocalDate dataRescisao;
    private SimpleStringProperty contato;
    private SimpleStringProperty contato2;
    private SimpleStringProperty endereco;
    private SimpleStringProperty bairro;
    private SimpleStringProperty cidade;
    private SimpleStringProperty estado;
    private SimpleStringProperty cep;
    private Cargo cargo;
    private SimpleStringProperty email;

    public Funcionario() {
        this.nome = new SimpleStringProperty();
        this.cpf = new SimpleStringProperty();
        this.rg = new SimpleStringProperty();
        this.contato = new SimpleStringProperty();
        this.contato2= new SimpleStringProperty();
        this.endereco = new SimpleStringProperty();
        this.bairro = new SimpleStringProperty();
        this.cidade = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.cep = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.porcentagem = new SimpleFloatProperty();
        this.dataRescisao = null;
    }

    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCpf() {
        return cpf.get();
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public String getRg() {
        return rg.get();
    }

    public void setRg(String rg) {
        this.rg.set(rg);
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public float getPorcentagem() {
        return porcentagem.get();
    }

    public void setPorcentagem(Float porcentagem) {
        this.porcentagem.set(porcentagem);
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataRescisao() {
        return dataRescisao;
    }

    public void setDataRescisao(LocalDate dataRescisao) {
        this.dataRescisao = dataRescisao;
    }

    public String getContato() {
        return contato.get();
    }

    public void setContato(String contato) {
        this.contato.set(contato);
    }

    public String getContato2() {
        return contato2.get();
    }

    public void setContato2(String contato2) {
        this.contato2.set(contato2);
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public String getBairro() {
        return bairro.get();
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    
    
}
