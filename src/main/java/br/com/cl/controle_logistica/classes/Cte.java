/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Cte {
    
    private BigDecimal valor;
    private LocalDate dataEmissao;
    private int numeroCte;
    private SimpleStringProperty chaveAcesso;
    private SimpleStringProperty produto;
    private SimpleDoubleProperty pesoBruto;
    private SimpleDoubleProperty pesoLiquido;
    private SimpleDoubleProperty volume;
    
    private CteCliente clienteDestinatario;
    private CteCliente clienteRemetente;
    
    
    private ArrayList<Nf> notasFiscais;
    private SimpleStringProperty especie;
    private SimpleStringProperty observacao;

    
    public Cte() {
        chaveAcesso = new SimpleStringProperty();
        produto= new SimpleStringProperty();
        pesoBruto = new SimpleDoubleProperty();
        pesoLiquido = new SimpleDoubleProperty();
        volume = new SimpleDoubleProperty();
        especie = new SimpleStringProperty();
        observacao = new SimpleStringProperty();
    }

    public Cte(BigDecimal valor, LocalDate dataEmissao, int numeroCte, String chaveAcesso, String produto, Double pesoBruto, 
            Double pesoLiquido, Double volume, CteCliente clienteDestinatario, CteCliente clienteRemetente,
            ArrayList<Nf> notasFiscais, String especie, String observacao) {
        
        this.valor = valor;
        this.dataEmissao = dataEmissao;
        this.numeroCte = numeroCte;
        this.chaveAcesso = new SimpleStringProperty(chaveAcesso);
        this.produto = new SimpleStringProperty(produto);
        this.pesoBruto = new SimpleDoubleProperty(pesoBruto);
        this.pesoLiquido = new SimpleDoubleProperty(pesoLiquido);
        this.volume = new SimpleDoubleProperty(volume);
        this.clienteDestinatario = clienteDestinatario;
        this.clienteRemetente = clienteRemetente;
        this.notasFiscais = notasFiscais;
        this.especie = new SimpleStringProperty(especie);
        this.observacao = new SimpleStringProperty(observacao);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getNumeroCte() {
        return numeroCte;
    }

    public void setNumeroCte(int numeroCte) {
        this.numeroCte = numeroCte;
    }

    public String getChaveAcesso() {
        return chaveAcesso.get();
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso.set(chaveAcesso);
    }

    public String getProduto() {
        return produto.get();
    }

    public void setProduto(String produto) {
        this.produto.set(produto);
    }

    public Double getPesoBruto() {
        return pesoBruto.get();
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto.set(pesoBruto);
    }

    public Double getPesoLiquido() {
        return pesoLiquido.get();
    }

    public void setPesoLiquido(Double pesoLiquido) {
        this.pesoLiquido.set(pesoLiquido);
    }

    public Double getVolume() {
        return volume.get();
    }

    public void setVolume(Double volume) {
        this.volume.set(volume);
    }

    public CteCliente getClienteDestinatario() {
        return clienteDestinatario;
    }

    public void setClienteDestinatario(CteCliente clienteDestinatario) {
        this.clienteDestinatario = clienteDestinatario;
    }

    public CteCliente getClienteRemetente() {
        return clienteRemetente;
    }

    public void setClienteRemetente(CteCliente clienteRemetente) {
        this.clienteRemetente = clienteRemetente;
    }

    public ArrayList<Nf> getNotasFiscais() {
        return notasFiscais;
    }

    public void setNotasFiscais(ArrayList<Nf> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    public String getEspecie() {
        return especie.get();
    }

    public void setEspecie(String especie) {
        this.especie.set(especie);
    }

    public String getObservacao() {
        return observacao.get();
    }

    public void setObservacao(String observacao) {
        this.observacao.set(observacao);
    }

    
    
    
}
