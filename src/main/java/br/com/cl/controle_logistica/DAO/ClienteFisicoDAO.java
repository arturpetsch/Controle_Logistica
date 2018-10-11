/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.ClienteFisico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class ClienteFisicoDAO {
    
    private Connection connection = null;
    
     public ArrayList<Cliente> consultarClientePorNome(String nomeInformado){
        String sql = "SELECT * FROM clientefisico WHERE nomeCliente LIKE " + "'%" + nomeInformado + "%' order by nomeCliente";
        
        ResultSet resultSet;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Cliente clienteFisico = new Cliente();
                clienteFisico.getClienteFisico().setIdClienteFisico(resultSet.getInt("idCliente"));
                clienteFisico.getClienteFisico().setNomeCliente(resultSet.getString("nomeCliente"));
                clienteFisico.getClienteFisico().setCpf(resultSet.getString("CPF"));
                clienteFisico.getClienteFisico().setDataNascimento(resultSet.getDate("dataNascimento").toLocalDate());    
                clienteFisico.getClienteFisico().setRg(resultSet.getString("rg"));
                clienteFisico = clienteDAO.buscarClienteFisico(clienteFisico);
                clientes.add(clienteFisico);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return clientes;
    }
     
     /**
     * Método que salva o cliente no banco de dados.
     * @param clienteFisico
     * @return 
     */
    public boolean salvarCliente(Cliente clienteFisico){
        String sql = "INSERT INTO clientefisico(nomeCliente, CPF, dataNascimento, RG)" 
                + "VALUES(?,?,?,?)";
        
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, clienteFisico.getClienteFisico().getNomeCliente());
            preparedStatement.setString(2, clienteFisico.getClienteFisico().getCpf());
            preparedStatement.setDate(3, java.sql.Date.valueOf(clienteFisico.getClienteFisico().getDataNascimento()));
            preparedStatement.setString(4, clienteFisico.getClienteFisico().getRg());
            
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                clienteFisico.getClienteFisico().setIdClienteFisico(rs.getInt(1));
                clienteDAO.salvarClienteFisico(clienteFisico);
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
     /**
     * Método que atualiza um cliente fisico já salvo no banco.
     * @param cliente
     * @return 
     */
    public boolean atualizarCliente(Cliente clienteFisico){
        String sql = "UPDATE clientefisico SET nomeCliente = ?, CPF = ?, dataNascimento = ?, RG = ? WHERE idCliente = " 
                + clienteFisico.getClienteFisico().getIdClienteFisico();
        ClienteDAO clienteDAO = new ClienteDAO();
          clienteFisico = transformarCamposVazioEmNulos(clienteFisico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, clienteFisico.getClienteFisico().getNomeCliente());
            preparedStatement.setString(2, clienteFisico.getClienteFisico().getCpf());
             preparedStatement.setDate(3, java.sql.Date.valueOf(clienteFisico.getClienteFisico().getDataNascimento()));
            preparedStatement.setString(4, clienteFisico.getClienteFisico().getRg());
            preparedStatement.executeUpdate();
            
            clienteDAO.atualizarClienteFisico(clienteFisico);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private Cliente transformarCamposVazioEmNulos(Cliente clienteFisico){
        if(clienteFisico.getContato1().isEmpty()){
            clienteFisico.setContato1("");
        }
        
         return clienteFisico;
    }
    
     /**
     * Método que realiza a exclusão de um cliente no banco de dados.
     * @param clienteFisico
     * @return 
     */
    public boolean deletarCliente(Cliente clienteFisico){
        String sql = "DELETE FROM clientefisico WHERE idCliente = " + clienteFisico.getClienteFisico().getIdClienteFisico();
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           
            clienteDAO.deletarClienteFisico(clienteFisico);
            preparedStatement.executeUpdate(sql);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
