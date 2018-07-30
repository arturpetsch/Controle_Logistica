/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Artur
 */
public class Veiculo {

    private int idVeiculo;
    private SimpleStringProperty placa;
    private SimpleStringProperty modeloVeiculo;
    private SimpleDoubleProperty pesoTara;
    private SimpleStringProperty placaCarreta;
    private SimpleStringProperty anoVeiculo;
    private SimpleDoubleProperty km;
    private Funcionario funcionario;
    private SimpleDoubleProperty mediaKmLitro;

    public Veiculo() {
        this.placa = new SimpleStringProperty();
        this.modeloVeiculo = new SimpleStringProperty();
        this.pesoTara = new SimpleDoubleProperty();
        this.placaCarreta = new SimpleStringProperty();
        this.anoVeiculo = new SimpleStringProperty();
        this.mediaKmLitro = new SimpleDoubleProperty();
        this.km = new SimpleDoubleProperty();
    }

    public Veiculo(int idVeiculo, String placa, String modeloVeiculo, Double pesoTara, String placaCarreta, String anoVeiculo, 
            Double km, Funcionario funcionario, Double mediaKmLitro) {
        this.idVeiculo = idVeiculo;
        this.placa = new SimpleStringProperty(placa);
        this.modeloVeiculo = new SimpleStringProperty(modeloVeiculo);
        this.pesoTara = new SimpleDoubleProperty(pesoTara);
        this.placaCarreta = new SimpleStringProperty(placaCarreta);
        this.anoVeiculo = new SimpleStringProperty(anoVeiculo);
        this.km = new SimpleDoubleProperty(km);
        this.funcionario = funcionario;
        this.mediaKmLitro = new SimpleDoubleProperty(mediaKmLitro);
    }

    
    
    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa.get();
    }

    public void setPlaca(String placa) {
        this.placa.set(placa);
    }

    public String getModeloVeiculo() {
        return modeloVeiculo.get();
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo.set(modeloVeiculo);
    }

    public Double getPesoTara() {
        return pesoTara.get();
    }

    public void setPesoTara(Double pesoTara) {
        this.pesoTara.set(pesoTara);
    }

    public String getPlacaCarreta() {
        return placaCarreta.get();
    }

    public void setPlacaCarreta(String placaCarreta) {
        this.placaCarreta.set(placaCarreta);
    }

    public String getAnoVeiculo() {
        return anoVeiculo.get();
    }

    public void setAnoVeiculo(String anoVeiculo) {
        this.anoVeiculo.set(anoVeiculo);
    }

    public Double getKm() {
        return km.get();
    }

    public void setKm(Double km) {
        this.km.set(km);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Double getMediaKmLitro() {
        return mediaKmLitro.get();
    }

    public void setMediaKmLitro(Double mediaKmLitro) {
        this.mediaKmLitro.set(mediaKmLitro);
    }

   
}

