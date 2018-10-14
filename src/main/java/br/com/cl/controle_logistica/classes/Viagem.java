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
public class Viagem {
   
    private int idViagem;
    private Veiculo veiculo;
    private SimpleDoubleProperty qtdeKmPrevisto;
    private SimpleDoubleProperty qtdeKmRealizado;
    private Cte cte;
    private ArrayList<ViagemDespesa> despesas;
    private BigDecimal valorTotalGastoPrevisto;
    private BigDecimal valorTotalGastoRealizado;
    private BigDecimal valorTotalGanhoPrevisto;
    private BigDecimal valorTotalGanhoRealizado;
    private LocalDate dataViagem;
    
    public Viagem(){
        this.qtdeKmPrevisto = new SimpleDoubleProperty();
        this.qtdeKmRealizado = new SimpleDoubleProperty(0.00);
        valorTotalGastoRealizado = null;
        valorTotalGanhoRealizado = null;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Double getQtdeKmPrevisto() {
        return qtdeKmPrevisto.get();
    }

    public void setQtdeKmPrevisto(Double qtdeKmPrevisto) {
        this.qtdeKmPrevisto.set(qtdeKmPrevisto);
    }

    public Double getQtdeKmRealizado() {
        return qtdeKmRealizado.get();
    }

    public void setQtdeKmRealizado(Double qtdeKmRealizado) {
        this.qtdeKmRealizado.set(qtdeKmRealizado);
    }

    public Cte getCte() {
        return cte;
    }

    public void setCte(Cte cte) {
        this.cte = cte;
    }

    public ArrayList<ViagemDespesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(ArrayList<ViagemDespesa> despesas) {
        this.despesas = despesas;
    }

    public BigDecimal getValorTotalGastoPrevisto() {
        return valorTotalGastoPrevisto;
    }

    public void setValorTotalGastoPrevisto(BigDecimal valorTotalGastoPrevisto) {
        this.valorTotalGastoPrevisto = valorTotalGastoPrevisto;
    }

    public BigDecimal getValorTotalGastoRealizado() {
        return valorTotalGastoRealizado;
    }

    public void setValorTotalGastoRealizado(BigDecimal valorTotalGastoRealizado) {
        this.valorTotalGastoRealizado = valorTotalGastoRealizado;
    }

    public BigDecimal getValorTotalGanhoPrevisto() {
        return valorTotalGanhoPrevisto;
    }

    public void setValorTotalGanhoPrevisto(BigDecimal valorTotalGanhoPrevisto) {
        this.valorTotalGanhoPrevisto = valorTotalGanhoPrevisto;
    }

    public BigDecimal getValorTotalGanhoRealizado() {
        return valorTotalGanhoRealizado;
    }

    public void setValorTotalGanhoRealizado(BigDecimal valorTotalGanhoRealizado) {
        this.valorTotalGanhoRealizado = valorTotalGanhoRealizado;
    }

    public LocalDate getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(LocalDate dataViagem) {
        this.dataViagem = dataViagem;
    }
    
    
}
