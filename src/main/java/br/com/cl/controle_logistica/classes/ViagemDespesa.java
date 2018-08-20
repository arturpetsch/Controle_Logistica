/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.math.BigDecimal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Artur
 */
public class ViagemDespesa {
    private int idViagemDespesa;
    private SimpleStringProperty tipoDespesa;
    private SimpleStringProperty descricao;
    private BigDecimal valor;
    private Viagem viagem;
    
    public ViagemDespesa() {
        this.tipoDespesa = new SimpleStringProperty();
        this.descricao = new SimpleStringProperty();
    }

    
    
    public int getIdViagemDespesa() {
        return idViagemDespesa;
    }

    public void setIdViagemDespesa(int idViagemDespesa) {
        this.idViagemDespesa = idViagemDespesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoDespesa() {
        return tipoDespesa.get();
    }

    public void setTipoDespesa(String tipoDespesa) {
        this.tipoDespesa.set(tipoDespesa);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    
}
