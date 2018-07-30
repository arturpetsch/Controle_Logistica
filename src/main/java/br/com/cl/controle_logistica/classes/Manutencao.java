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
}