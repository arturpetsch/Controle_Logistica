/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Manutencao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class ManutencaoDAO {
    
     private Connection connection = null;
     
     public ArrayList<Manutencao> buscarManutencoes(int idVeiculo){
         String sql = "SELECT * FROM manutencao WHERE id_veiculo = " + idVeiculo;
         
         ResultSet resultSet;
         ArrayList<Manutencao> manutencoes = new ArrayList<Manutencao>();
         VeiculoDAO veiculoDAO = new VeiculoDAO();
         
         try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Manutencao manutencao = new Manutencao();
                manutencao.setIdManutencao(resultSet.getInt("idManutencao"));
                manutencao.setValor(resultSet.getBigDecimal("valor"));
                manutencao.setDataManutencao(resultSet.getDate("dataManutencao").toLocalDate());
                manutencao.setObservacao(resultSet.getString("observacao"));
                manutencao.setTipoManutencao(resultSet.getString("tipoManutencao"));
                 manutencao.setVeiculo(veiculoDAO.consultarVeiculoPorId(resultSet.getInt("id_veiculo")));
                manutencoes.add(manutencao);
            }
            return manutencoes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
     }
     
     public Manutencao buscarManutencaoPorData(LocalDate dataManutencao){
         String sql = "SELECT * FROM manutencao WHERE dataManutencao = '" + dataManutencao + "'";
         
         ResultSet resultSet;
         Manutencao manutencao = null;
         VeiculoDAO veiculoDAO = new VeiculoDAO();
         
         try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            if(resultSet.next()){
                manutencao = new Manutencao();
                manutencao.setIdManutencao(resultSet.getInt("idManutencao"));
                manutencao.setValor(resultSet.getBigDecimal("valor"));
                manutencao.setDataManutencao(resultSet.getDate("dataManutencao").toLocalDate());
                manutencao.setObservacao(resultSet.getString("observacao"));
                manutencao.setTipoManutencao(resultSet.getString("tipoManutencao"));
                
                manutencao.setVeiculo(veiculoDAO.consultarVeiculoPorId(resultSet.getInt("id_veiculo")));
            }
            return manutencao;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
     
     
     /**
      * Método que salva no banco uma manutenção.
      * 
      */
     public boolean salvarManutencao(Manutencao manutencao){
         String sql = "INSERT INTO manutencao(valor, dataManutencao, observacao, tipoManutencao, id_veiculo)" + 
                 "VALUES(?,?,?,?,?)";
          try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, manutencao.getValor());
            preparedStatement.setDate(2, java.sql.Date.valueOf(manutencao.getDataManutencao()));
            preparedStatement.setString(3, manutencao.getObservacao());
            preparedStatement.setString(4, manutencao.getTipoManutencao());
            preparedStatement.setInt(5, manutencao.getVeiculo().getIdVeiculo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            return false;
        }
        return true;
     }
     
     /**
      * Método que atualiza uma manutencao já salva no banco de dados.
      * @param manutencao
      * @return 
      */
     public boolean alterarManutencao(Manutencao manutencao){
         String sql = "UPDATE manutencao SET valor = ?, dataManutencao = ?, observacao = ?, tipoManutencao = ?, id_veiculo = ? "
                 + "WHERE idManutencao = " + manutencao.getIdManutencao();
         
     try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, manutencao.getValor());
            preparedStatement.setDate(2, java.sql.Date.valueOf(manutencao.getDataManutencao()));
            preparedStatement.setString(3, manutencao.getObservacao());
            preparedStatement.setString(4, manutencao.getTipoManutencao());
            preparedStatement.setInt(5, manutencao.getVeiculo().getIdVeiculo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            return false;
        }
        return true;
     }    
     
     /**
      * Método que realiza a exclusão de uma manutenção no banco de dados.
      * @param manutencao
      * @return 
      */
     public boolean deletarManutencao(Manutencao manutencao){
         String sql = "DELETE FROM manutencao WHERE idManutencao = " + manutencao.getIdManutencao();
         
          try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
     }
     
     
     public ArrayList<LocalDate> buscarDataManutencoes(int idVeiculo){
         String sql = "SELECT dataManutencao FROM manutencao WHERE id_veiculo = " + idVeiculo;
         
         ResultSet resultSet;
         ArrayList<LocalDate> dataManutencoes = new ArrayList<LocalDate>();
         VeiculoDAO veiculoDAO = new VeiculoDAO();
         
         try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                LocalDate data;
                data = (resultSet.getDate("dataManutencao").toLocalDate());
                dataManutencoes.add(data);
            }
            return dataManutencoes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
     }
     
     /**
      * Metodo que busca a ultima manutencao por id.
      * @param idManutencao
      * @return 
      */
     private Manutencao buscarUltimaManutencaoPorId(int idManutencao){
         String sql = "SELECT * FROM manutencao WHERE idManutencao = " + idManutencao;
         
         ResultSet resultSet;
         
         VeiculoDAO veiculoDAO = new VeiculoDAO();
         Manutencao manutencao = new Manutencao();
                
         try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            if(resultSet.next()){
                manutencao.setIdManutencao(resultSet.getInt("idManutencao"));
                manutencao.setValor(resultSet.getBigDecimal("valor"));
                manutencao.setDataManutencao(resultSet.getDate("dataManutencao").toLocalDate());
                manutencao.setObservacao(resultSet.getString("observacao"));
                manutencao.setTipoManutencao(resultSet.getString("tipoManutencao"));
                 manutencao.setVeiculo(veiculoDAO.consultarVeiculoPorId(resultSet.getInt("id_veiculo")));
           }
            return manutencao;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
     }
     
     public Manutencao buscarUltimaManutencao(String tipoManutencao){
         String sqlBusca = "SELECT MAX(idManutencao) as idManutencao FROM manutencao WHERE tipoManutencao = '" + tipoManutencao + "'";
         
         ResultSet resultSetManutencao;
         
         try {
             connection = Conexao.conexao();
             PreparedStatement preparedStatementManutencao = connection.prepareStatement(sqlBusca);
             resultSetManutencao = preparedStatementManutencao.executeQuery(sqlBusca);
             
             Manutencao manutencao = new Manutencao();
             
             int idManutencao = 0;
             if(resultSetManutencao.next()){
                 idManutencao = resultSetManutencao.getInt("idManutencao");
             }
             
             manutencao = buscarUltimaManutencaoPorId(idManutencao);
             return manutencao;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
}