/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Cliente;
import br.com.cl.controle_logistica.classes.ClienteFisico;
import br.com.cl.controle_logistica.classes.ClienteJuridico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 *
 * @author Artur
 */
public class ClienteDAO {

    private Connection connection = null;

    public Cliente buscarClienteFisico(Cliente clienteFisico) {
        String sql = "SELECT * FROM cliente WHERE idClienteFisico = " + clienteFisico.getClienteFisico().getIdClienteFisico();

        ResultSet resultSet;

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            if (resultSet.next()) {
                clienteFisico.setEndereco(resultSet.getString("endereco"));
                clienteFisico.setBairro(resultSet.getString("bairro"));
                clienteFisico.setCidade(resultSet.getString("cidade"));
                clienteFisico.setEstado(resultSet.getString("estado"));
                clienteFisico.setCep(resultSet.getString("cep"));
                clienteFisico.setEmail(resultSet.getString("email"));
                clienteFisico.setContato(resultSet.getString("contato"));
                clienteFisico.setContato1(resultSet.getString("contato2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return clienteFisico;
    }

    public boolean salvarClienteFisico(Cliente clienteFisico) {
        String sql = "INSERT INTO cliente(idClienteFisico, endereco, bairro, cidade, estado, cep, email, contato, contato2, dataCadastro)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        clienteFisico = transformarCamposVazioEmNulos(clienteFisico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clienteFisico.getClienteFisico().getIdClienteFisico());
            preparedStatement.setString(2, clienteFisico.getEndereco());
            preparedStatement.setString(3, clienteFisico.getBairro());
            preparedStatement.setString(4, clienteFisico.getCidade());
            preparedStatement.setString(5, clienteFisico.getEstado());
            preparedStatement.setString(6, clienteFisico.getCep());
            preparedStatement.setString(7, clienteFisico.getEmail());
            preparedStatement.setString(8, clienteFisico.getContato());
            preparedStatement.setString(9, clienteFisico.getContato1());
            preparedStatement.setDate(10, java.sql.Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean atualizarClienteFisico(Cliente clienteFisico) {
        String sql = "UPDATE cliente SET endereco = ?,"
                + " bairro = ?, cidade = ?, estado = ?, cep = ?, email = ?,"
                + " contato = ?, contato2 = ? WHERE idClienteFisico = " + clienteFisico.getClienteFisico().getIdClienteFisico();

        clienteFisico = transformarCamposVazioEmNulos(clienteFisico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clienteFisico.getEndereco());
            preparedStatement.setString(2, clienteFisico.getBairro());
            preparedStatement.setString(3, clienteFisico.getCidade());
            preparedStatement.setString(4, clienteFisico.getEstado());
            preparedStatement.setString(5, clienteFisico.getCep());
            preparedStatement.setString(6, clienteFisico.getEmail());
            preparedStatement.setString(7, clienteFisico.getContato());
            preparedStatement.setString(8, clienteFisico.getContato1());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deletarClienteFisico(Cliente clienteFisico) {
        String sql = "DELETE FROM cliente WHERE idClienteFisico = " + clienteFisico.getClienteFisico().getIdClienteFisico();

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

    /**
     * Metodo que transforme em nulo os campos vazios do cliente fisico.
     *
     * @param clienteFisico
     * @return
     */
    private Cliente transformarCamposVazioEmNulos(Cliente clienteFisico) {
        if (clienteFisico.getContato1().isEmpty()) {
            clienteFisico.setContato1("");
        }

        return clienteFisico;
    }

    //=====================Métodos Cliente Juridico===========================
    public Cliente buscarClienteJuridico(Cliente clienteJuridico) {
        String sql = "SELECT * FROM cliente WHERE idClienteJuridico = " + clienteJuridico.getClienteJuridico().getIdClienteJuridico();

        ResultSet resultSet;

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            if (resultSet.next()) {
                clienteJuridico.setEndereco(resultSet.getString("endereco"));
                clienteJuridico.setBairro(resultSet.getString("bairro"));
                clienteJuridico.setCidade(resultSet.getString("cidade"));
                clienteJuridico.setEstado(resultSet.getString("estado"));
                clienteJuridico.setCep(resultSet.getString("cep"));
                clienteJuridico.setEmail(resultSet.getString("email"));
                clienteJuridico.setContato(resultSet.getString("contato"));
                clienteJuridico.setContato1(resultSet.getString("contato2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return clienteJuridico;
    }
    
    public boolean salvarClienteJuridico(Cliente clienteJuridico){
        String sql = "INSERT INTO cliente(idClienteJuridico, endereco, bairro, cidade, estado, cep, email, contato, contato2, dataCadastro)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        clienteJuridico = transformarCamposVazioEmNulos(clienteJuridico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clienteJuridico.getClienteJuridico().getIdClienteJuridico());
            preparedStatement.setString(2, clienteJuridico.getEndereco());
            preparedStatement.setString(3, clienteJuridico.getBairro());
            preparedStatement.setString(4, clienteJuridico.getCidade());
            preparedStatement.setString(5, clienteJuridico.getEstado());
            preparedStatement.setString(6, clienteJuridico.getCep());
            preparedStatement.setString(7, clienteJuridico.getEmail());
            preparedStatement.setString(8, clienteJuridico.getContato());
            preparedStatement.setString(9, clienteJuridico.getContato1());
            preparedStatement.setDate(10, java.sql.Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean atualizarClienteJuridico(Cliente clienteJuridico) {
        String sql = "UPDATE cliente SET endereco = ?,"
                + " bairro = ?, cidade = ?, estado = ?, cep = ?, email = ?,"
                + " contato = ?, contato2 = ? WHERE idClienteJuridico = " + clienteJuridico.getClienteJuridico().getIdClienteJuridico();

        clienteJuridico = transformarCamposVazioEmNulos(clienteJuridico);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clienteJuridico.getEndereco());
            preparedStatement.setString(2, clienteJuridico.getBairro());
            preparedStatement.setString(3, clienteJuridico.getCidade());
            preparedStatement.setString(4, clienteJuridico.getEstado());
            preparedStatement.setString(5, clienteJuridico.getCep());
            preparedStatement.setString(6, clienteJuridico.getEmail());
            preparedStatement.setString(7, clienteJuridico.getContato());
            preparedStatement.setString(8, clienteJuridico.getContato1());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
     public boolean deletarClienteJuridico(Cliente clienteJuridico) {
        String sql = "DELETE FROM cliente WHERE idClienteJuridico = " + clienteJuridico.getClienteJuridico().getIdClienteJuridico();

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
