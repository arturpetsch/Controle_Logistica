/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;


import br.com.cl.controle_logistica.classes.Funcionario;
import br.com.cl.controle_logistica.classes.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class VeiculoDAO {
    
     private Connection connection = null;
     
    public ArrayList<Veiculo> consultarVeiculoPorPlaca(String placaInformada){
        String sql = "SELECT * FROM veiculo WHERE placa LIKE " + "'%" + placaInformada + "%' order by placa";
        
        ResultSet resultSet;
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Veiculo veiculo = new Veiculo();
                veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
                veiculo.setPlaca(resultSet.getString("placa"));
                veiculo.setModeloVeiculo(resultSet.getString("modeloVeiculo"));
                veiculo.setPesoTara(resultSet.getDouble("pesoTara"));
                veiculo.setPlacaCarreta(resultSet.getString("placaCarreta"));
                veiculo.setAnoVeiculo(resultSet.getString("anoVeiculo"));
                veiculo.setKm(resultSet.getDouble("km"));
                veiculo.setMediaKmLitro(resultSet.getDouble("mediaKmLitro"));
                
                //Busca Funcionário responsável pelo Veiculo
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                Funcionario funcionario = funcionarioDAO.buscaFuncionarioPeloId(resultSet.getInt("funcionario_fk"));
                veiculo.setFuncionario(funcionario);
                
                veiculos.add(veiculo);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }   
        return veiculos;
    }
    
    public Veiculo consultarVeiculoPorId(int idVeiculo){
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = " + idVeiculo;
        
        ResultSet resultSet;
        Veiculo veiculo = new Veiculo();
                
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            if(resultSet.next()){
                veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
                veiculo.setPlaca(resultSet.getString("placa"));
                veiculo.setModeloVeiculo(resultSet.getString("modeloVeiculo"));
                veiculo.setPesoTara(resultSet.getDouble("pesoTara"));
                veiculo.setPlacaCarreta(resultSet.getString("placaCarreta"));
                veiculo.setAnoVeiculo(resultSet.getString("anoVeiculo"));
                veiculo.setKm(resultSet.getDouble("km"));
                veiculo.setMediaKmLitro(resultSet.getDouble("mediaKmLitro"));
                
                //Busca Funcionário responsável pelo Veiculo
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                Funcionario funcionario = funcionarioDAO.buscaFuncionarioPeloId(resultSet.getInt("funcionario_fk"));
                veiculo.setFuncionario(funcionario);
             }
            return veiculo;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }   
               
    }
    
    /**
     * Método que salva o veículo no banco de dados.
     * @param veiculo
     * @return 
     */
    public boolean salvarVeiculo(Veiculo veiculo){
        String sql = "INSERT INTO veiculo(placa, modeloVeiculo, pesoTara, placaCarreta, anoVeiculo, km, funcionario_fk, mediaKmLitro)"
                + "VALUES(?,?,?,?,?,?,?,?)";
        veiculo = transformarCamposVazioEmNulos(veiculo);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, veiculo.getPlaca());
            preparedStatement.setString(2, veiculo.getModeloVeiculo());
            preparedStatement.setDouble(3, veiculo.getPesoTara());
            preparedStatement.setString(4, veiculo.getPlacaCarreta());
            preparedStatement.setString(5, veiculo.getAnoVeiculo());
            preparedStatement.setDouble(6, veiculo.getKm());
            preparedStatement.setInt(7, veiculo.getFuncionario().getIdFuncionario());
            preparedStatement.setDouble(8, veiculo.getMediaKmLitro());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
     /**
     * Método que atualiza um veiculo já salvo no banco.
     * @param veiculo
     * @return 
     */
    public boolean atualizarVeiculo(Veiculo veiculo){
        String sql = "UPDATE veiculo SET placa = ?, modeloVeiculo = ?, pesoTara = ?, placaCarreta = ?, anoVeiculo = ?, km = ?,"
                + " funcionario_fk = ?, mediaKmLitro = ?  WHERE idVeiculo = " + veiculo.getIdVeiculo();
        
          veiculo = transformarCamposVazioEmNulos(veiculo);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, veiculo.getPlaca());
            preparedStatement.setString(2, veiculo.getModeloVeiculo());
            preparedStatement.setDouble(3, veiculo.getPesoTara());
            preparedStatement.setString(4, veiculo.getPlacaCarreta());
            preparedStatement.setString(5, veiculo.getAnoVeiculo());
            preparedStatement.setDouble(6, veiculo.getKm());
            preparedStatement.setInt(7, veiculo.getFuncionario().getIdFuncionario());
            preparedStatement.setDouble(8, veiculo.getMediaKmLitro());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    private Veiculo transformarCamposVazioEmNulos(Veiculo veiculo){
        if(veiculo.getPlacaCarreta().isEmpty()){
            veiculo.setPlacaCarreta(null);
        }
        
        return veiculo;
    }
    
     /**
     * Método que realiza a exclusão de um veiculo no banco de dados.
     * @param veiculo
     * @return 
     */
    public boolean deletarVeiculo(Veiculo veiculo){
        String sql = "DELETE FROM veiculo WHERE idVeiculo = " + veiculo.getIdVeiculo();
        
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
}
