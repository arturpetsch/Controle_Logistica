/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Manutencao {
    
    private int idManutencao;
    private LocalDate dataManutencao;
    private BigDecimal valor;
    private SimpleStringProperty tipoManutencao;
    private SimpleStringProperty observacao;
    private Veiculo veiculo;

    public Manutencao() {
        this.tipoManutencao = new SimpleStringProperty();
        this.observacao = new SimpleStringProperty();        
    }

    public int getIdManutencao() {
        return idManutencao;
    }

    public void setIdManutencao(int idManutencao) {
        this.idManutencao = idManutencao;
    }

    public LocalDate getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(LocalDate dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoManutencao() {
        return tipoManutencao.get();
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao.set(tipoManutencao);
    }

    public String getObservacao() {
        return observacao.get();
    }

    public void setObservacao(String observacao) {
        this.observacao.set(observacao);
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    
    
}
