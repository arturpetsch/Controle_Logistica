/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.ClienteFisico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class ClienteFisicoDAO {
    
    private Connection connection = null;
    
     public ArrayList<ClienteFisico> consultarClientePorNome(String nomeInformado){
        String sql = "SELECT * FROM clientefisico WHERE nomeCliente LIKE " + "'%" + nomeInformado + "%' order by nomeCliente";
        
        ResultSet resultSet;
        ArrayList<ClienteFisico> clientes = new ArrayList<ClienteFisico>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                ClienteFisico clienteFisico = new ClienteFisico();
                clienteFisico.setIdClienteFisico(resultSet.getInt("idCliente"));
                clienteFisico.setNomeCliente(resultSet.getString("nomeCliente"));
                clienteFisico.setCpf(resultSet.getString("CPF"));
                clienteFisico.setEndereco(resultSet.getString("endereco"));
                clienteFisico.setBairro(resultSet.getString("bairro"));
                clienteFisico.setCidade(resultSet.getString("cidade"));
                clienteFisico.setEstado(resultSet.getString("estado"));
                clienteFisico.setCep(resultSet.getString("cep"));
                clienteFisico.setDataNascimento(resultSet.getDate("dataNascimento").toLocalDate());
                clienteFisico.setEmail(resultSet.getString("email"));
                clienteFisico.setRg(resultSet.getString("rg"));
                clienteFisico.setContato(resultSet.getString("contato"));
                clienteFisico.setContato1(resultSet.getString("contato2"));
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
    public boolean salvarCliente(ClienteFisico clienteFisico){
        String sql = "INSERT INTO clientefisico(nomeCliente, CPF, endereco, bairro,"
                + " cidade, estado, cep, dataNascimento, dataCadastro, email, rg, contato, contato2)" 
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        clienteFisico = transformarCamposVazioEmNulos(clienteFisico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clienteFisico.getNomeCliente());
            preparedStatement.setString(2, clienteFisico.getCpf());
            preparedStatement.setString(3, clienteFisico.getEndereco());
            preparedStatement.setString(4, clienteFisico.getBairro());
            preparedStatement.setString(5, clienteFisico.getCidade());
            preparedStatement.setString(6, clienteFisico.getEstado());
            preparedStatement.setString(7, clienteFisico.getCep());
            preparedStatement.setDate(8, java.sql.Date.valueOf(clienteFisico.getDataNascimento()));
            preparedStatement.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(10, clienteFisico.getEmail());
            preparedStatement.setString(11, clienteFisico.getRg());
            preparedStatement.setString(12, clienteFisico.getContato());
            preparedStatement.setString(13, clienteFisico.getContato1());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
     /**
     * Método que atualiza um cliente fisico já salvo no banco.
     * @param clienteFisico
     * @return 
     */
    public boolean atualizarCliente(ClienteFisico clienteFisico){
        String sql = "UPDATE clientefisico SET nomeCliente = ?, cpf = ?, endereco = ?,"
                + " bairro = ?, cidade = ?, estado = ?, cep = ?, dataNascimento = ?, email = ?,"
                + " rg = ?, contato = ?, contato2 = ? WHERE idCliente = " + clienteFisico.getIdClienteFisico();
        
          clienteFisico = transformarCamposVazioEmNulos(clienteFisico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, clienteFisico.getNomeCliente());
            preparedStatement.setString(2, clienteFisico.getCpf());
             preparedStatement.setString(3, clienteFisico.getEndereco());
            preparedStatement.setString(4, clienteFisico.getBairro());
            preparedStatement.setString(5, clienteFisico.getCidade());
            preparedStatement.setString(6, clienteFisico.getEstado());
            preparedStatement.setString(7, clienteFisico.getCep());
            preparedStatement.setDate(8, java.sql.Date.valueOf(clienteFisico.getDataNascimento()));
            preparedStatement.setString(9, clienteFisico.getEmail());
            preparedStatement.setString(10, clienteFisico.getRg());
            preparedStatement.setString(11, clienteFisico.getContato());
            preparedStatement.setString(12, clienteFisico.getContato1());
           
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private ClienteFisico transformarCamposVazioEmNulos(ClienteFisico clienteFisico){
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
    public boolean deletarCliente(ClienteFisico clienteFisico){
        String sql = "DELETE FROM clientefisico WHERE idCliente = " + clienteFisico.getIdClienteFisico();
        
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
