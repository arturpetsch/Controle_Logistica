/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import java.math.BigDecimal;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Nf {
    
    private int idNotaFiscal;
    private BigDecimal valorNf;
    private SimpleStringProperty chaveAcesso;
    private int numeroNF;
    private Cte cte;
    
    public Nf(){
        this.chaveAcesso = new SimpleStringProperty();
    }

    public int getIdNotaFiscal() {
        return idNotaFiscal;
    }

    public void setIdNotaFiscal(int idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
    }

    public BigDecimal getValorNf() {
        return valorNf;
    }

    public void setValorNf(BigDecimal valorNf) {
        this.valorNf = valorNf;
    }

    public String getChaveAcesso() {
        return chaveAcesso.get();
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso.set(chaveAcesso);
    }

    public int getNumeroNF() {
        return numeroNF;
    }

    public void setNumeroNF(int numeroNF) {
        this.numeroNF = numeroNF;
    }

    public Cte getCte() {
        return cte;
    }

    public void setCte(Cte cte) {
        this.cte = cte;
    }
    
    
}
