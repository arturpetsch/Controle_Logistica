/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;


import br.com.cl.controle_logistica.classes.Cargo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class CargoDAO {

    private Connection connection = null;

    public Cargo buscarCargoPeloId(int id)  {
        String sql = "SELECT * FROM Cargo WHERE idCargo = " + id;

        ResultSet resultSet;
        Cargo cargo = new Cargo();

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            if (resultSet.next()) {
                cargo.setIdCargo(resultSet.getInt("idCargo"));
                cargo.setDescricao(resultSet.getString("descricao"));
            }
            return cargo;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return null;
    }

    /**
     * Método que consulta no banco um cargo pela sua descrição e retorno o
     * objeto inteiro.
     */
    public ArrayList<Cargo> buscarCargoPelaDescricao(String descricaoInformada) throws ClassNotFoundException {
        String sql = "SELECT * FROM cargo WHERE descricao LIKE " + "'%" + descricaoInformada + "%' order by descricao";

        ResultSet resultSet;
        ArrayList<Cargo> cargos = new ArrayList<Cargo>();

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                int id =(resultSet.getInt("idCargo"));
                String descricao = (resultSet.getString("descricao"));
                Cargo cargo = new Cargo(id, descricao);
                cargos.add(cargo);
            }
            return cargos;
        } catch (SQLException e) {
            return null;
        }
        
    }

    /**
     * Método que salva o cargo no banco de dados.
     * @param cargo
     * @return 
     */
    public boolean salvarCargo(Cargo cargo){
        String sql = "INSERT INTO cargo(descricao)" + "VALUES(?)";
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cargo.getDescricao());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Método que atualiza um cargo já salvo no banco.
     * @param cargo
     * @return 
     */
    public boolean atualizarCargo(Cargo cargo){
        String sql = "UPDATE cargo SET descricao = ? WHERE idCargo = " + cargo.getIdCargo();
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cargo.getDescricao());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Método que realiza a exclusão de um cargo no banco de dados.
     * @param cargo
     * @return 
     */
    public boolean deletarCargo(Cargo cargo){
        String sql = "DELETE FROM cargo WHERE idCargo = " + cargo.getIdCargo();
        
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
