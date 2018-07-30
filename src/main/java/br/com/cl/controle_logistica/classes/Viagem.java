/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Artur
 */
public class Viagem {
   
    private int idViagem;
    private Veiculo veiculo;
    private SimpleDoubleProperty kmInicial;
    private SimpleDoubleProperty kmFinal;
    private SimpleDoubleProperty qtdeKmPrevisto;
    private SimpleDoubleProperty qtdeKmRealizado;
    private Cte cte;
}
