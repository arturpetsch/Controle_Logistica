/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;


import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.ClienteJuridico;
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
public class ClienteJuridicoDAO {
    private Connection connection = null;
    
     public ArrayList<Cliente> consultarClientePorNome(String nomeInformado){
        String sql = "SELECT * FROM clientejuridico WHERE nomeFantasia LIKE " + "'%" + nomeInformado + "%' order by nomeFantasia";
        
        ResultSet resultSet;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Cliente clienteJuridico = new Cliente();
                clienteJuridico.getClienteJuridico().setIdClienteJuridico(resultSet.getInt("idClienteJuridico"));
                clienteJuridico.getClienteJuridico().setNomeFantasia(resultSet.getString("nomeFantasia"));
                clienteJuridico.getClienteJuridico().setRazaoSocial(resultSet.getString("razaoSocial"));
                clienteJuridico.getClienteJuridico().setCnpj(resultSet.getString("cnpj"));
                clienteJuridico.getClienteJuridico().setIe(resultSet.getString("inscricaoEstadual"));
                clienteDAO.buscarClienteJuridico(clienteJuridico);
                clientes.add(clienteJuridico);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return clientes;
    }
     
     /**
     * Método que salva o cliente no banco de dados.
     * @param clienteJuridico 
     * @return 
     */
    public boolean salvarCliente(Cliente clienteJuridico){
        String sql = "INSERT INTO clientejuridico(nomeFantasia, razaoSocial, cnpj, inscricaoEstadual)" 
                + "VALUES(?,?,?,?)";
         ClienteDAO clienteDAO = new ClienteDAO();
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, clienteJuridico.getClienteJuridico().getNomeFantasia());
            preparedStatement.setString(2, clienteJuridico.getClienteJuridico().getRazaoSocial());
            preparedStatement.setString(3, clienteJuridico.getClienteJuridico().getCnpj());
            preparedStatement.setString(4, clienteJuridico.getClienteJuridico().getIe());
           
            preparedStatement.executeUpdate();
           final ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                clienteJuridico.getClienteJuridico().setIdClienteJuridico(rs.getInt(1));
                clienteDAO.salvarClienteJuridico(clienteJuridico);
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
     /**
     * Método que atualiza um cliente juridico já salvo no banco.
     * @param clienteJuridico
     * @return 
     */
    public boolean atualizarCliente(Cliente clienteJuridico){
        String sql = "UPDATE clientejuridico SET nomeFantasia = ?, razaoSocial =?, cnpj = ?, inscricaoEstadual = ?"
                + " WHERE idClienteJuridico = " + clienteJuridico.getClienteJuridico().getIdClienteJuridico();
        
        ClienteDAO clienteDAO = new ClienteDAO();
          clienteJuridico = transformarCamposVazioEmNulos(clienteJuridico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, clienteJuridico.getClienteJuridico().getNomeFantasia());
           preparedStatement.setString(2, clienteJuridico.getClienteJuridico().getRazaoSocial());
            preparedStatement.setString(3, clienteJuridico.getClienteJuridico().getCnpj());
            preparedStatement.setString(4, clienteJuridico.getClienteJuridico().getIe());
           
            preparedStatement.executeUpdate();
            
            clienteDAO.atualizarClienteJuridico(clienteJuridico);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private Cliente transformarCamposVazioEmNulos(Cliente clienteJuridico){
        if(clienteJuridico.getContato1().isEmpty()){
            clienteJuridico.setContato1("");
        }
        
         return clienteJuridico;
    }
    
     /**
     * Método que realiza a exclusão de um cliente no banco de dados.
     * @param clienteJuridico
     * @return 
     */
    public boolean deletarCliente(Cliente clienteJuridico){
        String sql = "DELETE FROM clientejuridico WHERE idClienteJuridico = " + clienteJuridico.getClienteJuridico().getIdClienteJuridico();
        
         ClienteDAO clienteDAO = new ClienteDAO();
         
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            clienteDAO.deletarClienteJuridico(clienteJuridico);
            preparedStatement.executeUpdate(sql);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
