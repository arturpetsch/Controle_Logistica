/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Veiculo;
import br.com.cl.controle_logistica.classes.Viagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class ViagemDAO {

     private Connection connection = null;
    
     /**
      * Método que consulta viagem atraves do id do veiculo e retorna um arraylist de viagem.
      * @param idVeiculo
      * @return 
      */
     public ArrayList<Viagem> consultarViagemPorIdVeiculo(int idVeiculo){
        String sql = "SELECT * FROM viagem WHERE veiculo_fk = " + idVeiculo + " order by dataViagem";
        
        ResultSet resultSet;
        ArrayList<Viagem> viagens = new ArrayList<Viagem>();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        CteDAO cteDAO = new CteDAO();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Viagem viagem = new Viagem();
                
                viagem.setIdViagem(resultSet.getInt("idViagem"));
                viagem.setVeiculo(veiculoDAO.consultarVeiculoPorId(resultSet.getInt("veiculo_fk")));
                viagem.setCte(cteDAO.consultarCtePorNumero(resultSet.getInt("cte_fk")));
                viagem.setQtdeKmPrevisto(resultSet.getDouble("totalKmPrevisto"));
                viagem.setQtdeKmRealizado(resultSet.getDouble("totalKmRealizado"));
                viagem.setValorTotalGastoPrevisto(resultSet.getBigDecimal("valorTotalGastoPrevisto"));
                viagem.setValorTotalGastoRealizado(resultSet.getBigDecimal("valorTotalGastoRealizado"));
                viagem.setValorTotalGanhoPrevisto(resultSet.getBigDecimal("valorTotalGanhoPrevisto"));
                viagem.setValorTotalGanhoRealizado(resultSet.getBigDecimal("valorTotalGanhoRealizado"));
                viagem.setDataViagem(resultSet.getDate("dataViagem").toLocalDate());
                viagens.add(viagem);
            }
            return viagens;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
     }
     
     /**
     * Método que salva o veículo no banco de dados.
     * @param veiculo
     * @return 
     */
    public boolean salvarViagem(Viagem viagem){
        String sql = "INSERT INTO viagem(veiculo_fk, cte_fk, totalKmPrevisto, totalKmRealizado, valorTotalGastoPrevisto,"
                + " valorTotalGastoRealizado, valorTotalGanhoPrevisto, valorTotalGanhoRealizado, dataViagem)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, viagem.getVeiculo().getIdVeiculo());
            preparedStatement.setInt(2, viagem.getCte().getNumeroCte());
            preparedStatement.setDouble(3, viagem.getQtdeKmPrevisto());
            preparedStatement.setDouble(4, viagem.getQtdeKmRealizado());
            preparedStatement.setBigDecimal(5, viagem.getValorTotalGastoPrevisto());
            preparedStatement.setBigDecimal(6, viagem.getValorTotalGastoRealizado());
            preparedStatement.setBigDecimal(7, viagem.getValorTotalGanhoPrevisto());
            preparedStatement.setBigDecimal(8, viagem.getValorTotalGanhoRealizado());
            preparedStatement.setDate(9, java.sql.Date.valueOf(viagem.getDataViagem()));
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
