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
public class Cte {
    
    private BigDecimal valor;
    private LocalDate dataEmissao;
    private int numeroCte;
    private SimpleStringProperty chaveAcesso;
    private SimpleStringProperty produto;
    private SimpleDoubleProperty pesoBruto;
    private SimpleDoubleProperty pesoLiquido;
    private SimpleDoubleProperty volume;
    private ClienteFisico clienteFisico;
    private ClienteJuridico clienteJuridico;
    private ClienteJuridico clienteJuridicoDestinatario;
    private ClienteFisico clienteFisicoDestinatario;
    private Nf nf;
}
