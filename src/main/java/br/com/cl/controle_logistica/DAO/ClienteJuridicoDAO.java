/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;


import br.com.cl.controle_logistica.classes.ClienteJuridico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class ClienteJuridicoDAO {
    private Connection connection = null;
    
     public ArrayList<ClienteJuridico> consultarClientePorNome(String nomeInformado){
        String sql = "SELECT * FROM clientejuridico WHERE nomeFantasia LIKE " + "'%" + nomeInformado + "%' order by nomeFantasia";
        
        ResultSet resultSet;
        ArrayList<ClienteJuridico> clientes = new ArrayList<ClienteJuridico>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                ClienteJuridico clienteJuridico = new ClienteJuridico();
                clienteJuridico.setIdClienteJuridico(resultSet.getInt("idClienteJuridico"));
                clienteJuridico.setNomeFantasia(resultSet.getString("nomeFantasia"));
                clienteJuridico.setRazaoSocial(resultSet.getString("razaoSocial"));
                clienteJuridico.setCnpj(resultSet.getString("cnpj"));
                clienteJuridico.setEndereco(resultSet.getString("endereco"));
                clienteJuridico.setBairro(resultSet.getString("bairro"));
                clienteJuridico.setCidade(resultSet.getString("cidade"));
                clienteJuridico.setEstado(resultSet.getString("estado"));
                clienteJuridico.setCep(resultSet.getString("cep"));
                clienteJuridico.setEmail(resultSet.getString("email"));
                clienteJuridico.setIe(resultSet.getString("inscricaoEstadual"));
                clienteJuridico.setContato(resultSet.getString("contato"));
                clienteJuridico.setContato1(resultSet.getString("contato2"));
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
    public boolean salvarCliente(ClienteJuridico clienteJuridico){
        String sql = "INSERT INTO clientejuridico(nomeFantasia, razaoSocial, cnpj, endereco,"
                + " cidade, estado, cep, bairro,  dataCadastro, inscricaoEstadual, email,  contato, contato2)" 
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        clienteJuridico = transformarCamposVazioEmNulos(clienteJuridico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clienteJuridico.getNomeFantasia());
            preparedStatement.setString(2, clienteJuridico.getRazaoSocial());
            preparedStatement.setString(3, clienteJuridico.getCnpj());
            preparedStatement.setString(4, clienteJuridico.getEndereco());
            preparedStatement.setString(5, clienteJuridico.getCidade());
            preparedStatement.setString(6, clienteJuridico.getEstado());
            preparedStatement.setString(7, clienteJuridico.getCep());
            preparedStatement.setString(8, clienteJuridico.getBairro());
            preparedStatement.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(10, clienteJuridico.getIe());
            preparedStatement.setString(11, clienteJuridico.getEmail());
            preparedStatement.setString(12, clienteJuridico.getContato());
            preparedStatement.setString(13, clienteJuridico.getContato1());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
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
    public boolean atualizarCliente(ClienteJuridico clienteJuridico){
        String sql = "UPDATE clientejuridico SET nomeFantasia = ?, razaoSocial =?, cnpj = ?, endereco = ?,"
                + " cidade = ?, estado = ?, cep = ?, bairro = ?, inscricaoEstadual = ?, email = ?,"
                + " contato = ?, contato2 = ? WHERE idClienteJuridico = " + clienteJuridico.getIdClienteJuridico();
        
          clienteJuridico = transformarCamposVazioEmNulos(clienteJuridico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, clienteJuridico.getNomeFantasia());
           preparedStatement.setString(2, clienteJuridico.getRazaoSocial());
            preparedStatement.setString(3, clienteJuridico.getCnpj());
             preparedStatement.setString(4, clienteJuridico.getEndereco());
            preparedStatement.setString(5, clienteJuridico.getCidade());
            preparedStatement.setString(6, clienteJuridico.getEstado());
            preparedStatement.setString(7, clienteJuridico.getCep());
            preparedStatement.setString(8, clienteJuridico.getBairro());
            preparedStatement.setString(9, clienteJuridico.getIe());
            preparedStatement.setString(10, clienteJuridico.getEmail());
            preparedStatement.setString(11, clienteJuridico.getContato());
            preparedStatement.setString(12, clienteJuridico.getContato1());
           
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private ClienteJuridico transformarCamposVazioEmNulos(ClienteJuridico clienteJuridico){
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
    public boolean deletarCliente(ClienteJuridico clienteJuridico){
        String sql = "DELETE FROM clientejuridico WHERE idClienteJuridico = " + clienteJuridico.getIdClienteJuridico();
        
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
