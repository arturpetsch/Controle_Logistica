/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

/**
 *
 * @author Artur
 */
public class CteCliente {
    private Cte cte;
    private Cliente cliente;
    private Boolean tomador;

    public CteCliente(Cte cte, Cliente cliente, Boolean tomador) {
        this.cte = cte;
        this.cliente = cliente;
        this.tomador = tomador;
    }

    public CteCliente() {
    }

    
    
    public Cte getCte() {
        return cte;
    }

    public void setCte(Cte cte) {
        this.cte = cte;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getTomador() {
        return tomador;
    }

    public void setTomador(Boolean tomador) {
        this.tomador = tomador;
    }
    
    
}
