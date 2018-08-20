/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;


import br.com.cl.controle_logistica.classes.Cargo;
import br.com.cl.controle_logistica.classes.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe que faz a comunicação entre a tabela colaborador e a aplicação.
 * É resonsável pela inserção, remoção, alteração e busca de colaboradores.
 * 
 * @author Artur
 */
public class FuncionarioDAO {
    
     private Connection connection = null;
     
    public ArrayList<Funcionario> consultarColaboradorPorNome(String nomeInformado){
        String sql = "SELECT * FROM funcionario WHERE nome LIKE " + "'%" + nomeInformado + "%' order by nome";
        
        ResultSet resultSet;
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(resultSet.getInt("idFuncionario"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setCpf(resultSet.getString("CPF"));
                funcionario.setRg(resultSet.getString("RG"));
                funcionario.setDataNascimento(resultSet.getDate("dataNascimento").toLocalDate());
                funcionario.setSalario(resultSet.getBigDecimal("salario"));
                funcionario.setPorcentagem(resultSet.getFloat("porcentagem"));
                funcionario.setDataAdmissao(resultSet.getDate("dataAdmissao").toLocalDate());
                
                if(resultSet.getDate("dataRescisao") != null){
                    funcionario.setDataRescisao(resultSet.getDate("dataRescisao").toLocalDate());
                }else{
                    funcionario.setDataRescisao(null);
                }
                
                funcionario.setContato(resultSet.getString("contato"));
                funcionario.setContato2(resultSet.getString("contato2"));
                funcionario.setEndereco(resultSet.getString("endereco"));
                funcionario.setBairro(resultSet.getString("bairro"));
                funcionario.setCidade(resultSet.getString("cidade"));
                funcionario.setEstado(resultSet.getString("estado"));
                funcionario.setCep(resultSet.getString("cep"));
                funcionario.setEmail(resultSet.getString("email"));
                
                //Busca e seta o cargo do funcionario
                CargoDAO cargoDAO = new CargoDAO();
                Cargo cargo = cargoDAO.buscarCargoPeloId(resultSet.getInt("cargo_fk"));
                funcionario.setCargo(cargo);
                funcionarios.add(funcionario);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return funcionarios;
    }
    
      /**
     * Método que salva o funcionario no banco de dados.
     * @param funcionario
     * @return 
     */
    public boolean salvarFuncionario(Funcionario funcionario){
        String sql = "INSERT INTO funcionario(nome, CPF, RG, dataNascimento, salario, porcentagem, dataAdmissao,"
                + " dataRescisao, contato, contato2, endereco, bairro, cidade, estado, cep, cargo_fk, email)" 
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        funcionario = transformarCamposVazioEmNulos(funcionario);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setString(2, funcionario.getCpf());
            preparedStatement.setString(3, funcionario.getRg());
            preparedStatement.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            
            preparedStatement.setBigDecimal(5, funcionario.getSalario());
            preparedStatement.setFloat(6, funcionario.getPorcentagem());
            preparedStatement.setDate(7, java.sql.Date.valueOf(funcionario.getDataAdmissao()));
            
            if(funcionario.getDataRescisao() == null){
                preparedStatement.setDate(8, null);
            }else{
                preparedStatement.setDate(8, java.sql.Date.valueOf(funcionario.getDataRescisao()));
            }
            
            preparedStatement.setString(9, funcionario.getContato());
            preparedStatement.setString(10, funcionario.getContato2());
            preparedStatement.setString(11, funcionario.getEndereco());
            preparedStatement.setString(12, funcionario.getBairro());
            preparedStatement.setString(13, funcionario.getCidade());
            preparedStatement.setString(14, funcionario.getEstado());
            preparedStatement.setString(15, funcionario.getCep());
            preparedStatement.setInt(16, funcionario.getCargo().getIdCargo());
            preparedStatement.setString(17, funcionario.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
     /**
     * Método que atualiza um funcionário já salvo no banco.
     * @param funcionário
     * @return 
     */
    public boolean atualizarFuncionario(Funcionario funcionario){
        String sql = "UPDATE funcionario SET nome = ?, CPF = ?, RG = ?, dataNascimento = ?, salario = ?, porcentagem = ?,"
                + " dataAdmissao = ?, dataRescisao = ?, contato = ?, contato2 = ?, endereco = ?, bairro = ?, cidade = ?, "
                + "estado = ?, cep = ?, cargo_fk = ?, email = ? WHERE idFuncionario = " + funcionario.getIdFuncionario();
        
          funcionario = transformarCamposVazioEmNulos(funcionario);
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setString(2, funcionario.getCpf());
            preparedStatement.setString(3, funcionario.getRg());
            preparedStatement.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            
            preparedStatement.setBigDecimal(5, funcionario.getSalario());
            preparedStatement.setFloat(6, funcionario.getPorcentagem());
            preparedStatement.setDate(7, java.sql.Date.valueOf(funcionario.getDataAdmissao()));
            
             if(funcionario.getDataRescisao() == null){
                preparedStatement.setDate(8, null);
            }else{
                preparedStatement.setDate(8, java.sql.Date.valueOf(funcionario.getDataRescisao()));
            }
            
            preparedStatement.setString(9, funcionario.getContato());
            preparedStatement.setString(10, funcionario.getContato2());
            preparedStatement.setString(11, funcionario.getEndereco());
            preparedStatement.setString(12, funcionario.getBairro());
            preparedStatement.setString(13, funcionario.getCidade());
            preparedStatement.setString(14, funcionario.getEstado());
            preparedStatement.setString(15, funcionario.getCep());
            preparedStatement.setInt(16, funcionario.getCargo().getIdCargo());
            preparedStatement.setString(17, funcionario.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    private Funcionario transformarCamposVazioEmNulos(Funcionario funcionario){
        if(funcionario.getContato2().isEmpty()){
            funcionario.setContato2(null);
        }
        
        return funcionario;
    }
    
     /**
     * Método que realiza a exclusão de um funcionário no banco de dados.
     * @param funcionario
     * @return 
     */
    public boolean deletarFuncionario(Funcionario funcionario){
        String sql = "DELETE FROM funcionario WHERE idFuncionario = " + funcionario.getIdFuncionario();
        
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
     * Método que busca um funcionário pelo iD e retorna o funcionario com todos os atributos.
     */
    public Funcionario buscaFuncionarioPeloId(int id){
        String sql = "SELECT * FROM funcionario WHERE idFuncionario = " + id;
        
        ResultSet resultSet;
        Funcionario funcionario = new Funcionario();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            if(resultSet.next()){
                
                funcionario.setIdFuncionario(resultSet.getInt("idFuncionario"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setCpf(resultSet.getString("CPF"));
                funcionario.setRg(resultSet.getString("RG"));
                funcionario.setDataNascimento(resultSet.getDate("dataNascimento").toLocalDate());
                funcionario.setSalario(resultSet.getBigDecimal("salario"));
                funcionario.setPorcentagem(resultSet.getFloat("porcentagem"));
                funcionario.setDataAdmissao(resultSet.getDate("dataAdmissao").toLocalDate());
                
                if(resultSet.getDate("dataRescisao") != null){
                    funcionario.setDataRescisao(resultSet.getDate("dataRescisao").toLocalDate());
                }else{
                    funcionario.setDataRescisao(null);
                }
                
                funcionario.setContato(resultSet.getString("contato"));
                funcionario.setContato2(resultSet.getString("contato2"));
                funcionario.setEndereco(resultSet.getString("endereco"));
                funcionario.setBairro(resultSet.getString("bairro"));
                funcionario.setCidade(resultSet.getString("cidade"));
                funcionario.setEstado(resultSet.getString("estado"));
                funcionario.setCep(resultSet.getString("cep"));
                funcionario.setEmail(resultSet.getString("email"));
                
                //Busca e seta o cargo do funcionario
                CargoDAO cargoDAO = new CargoDAO();
                Cargo cargo = cargoDAO.buscarCargoPeloId(resultSet.getInt("cargo_fk"));
                funcionario.setCargo(cargo);
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return funcionario;
    
    }
}
