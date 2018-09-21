/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.Nf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class CteDAO {
    
    private Connection connection = null;
    
    /**
     * 
     * @param numeroInformado
     * @return 
     */
    public ArrayList<Cte> buscarCtePeloNumero(int numeroInformado){
        String sql = "SELECT * FROM cte WHERE numeroCte LIKE '" + numeroInformado + "%'";
        
        ResultSet resultSet;
        ArrayList<Cte> fretes = new ArrayList<Cte>();
        
         try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Cte cte = new Cte();
                cte.setValor(resultSet.getBigDecimal("valor"));
                cte.setDataEmissao(resultSet.getDate("dataEmissao").toLocalDate());
                cte.setNumeroCte(resultSet.getInt("numeroCte"));
                cte.setChaveAcesso(resultSet.getString("chaveAcesso"));
                cte.setProduto(resultSet.getString("produto"));
                cte.setPesoBruto(resultSet.getDouble("pesoBruto"));
                cte.setPesoLiquido(resultSet.getDouble("pesoLiquido"));
                cte.setVolume(resultSet.getDouble("volume"));
                cte.setEspecie(resultSet.getString("especie"));
                cte.setObservacao(resultSet.getString("observacao"));
                
                
                fretes.add(cte);
            }
            return fretes;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
    
    /**
     * Método que salva o cte no banco de dados.
     * @param cte
     * @return 
     */
    public boolean salvarCTe(Cte cte){
        String sql = "INSERT INTO cte(valor, dataEmissao, chaveAcesso, produto, pesoBruto, pesoLiquido, volume, especie,"
                + " observacao, clienteRemetente_fk, clienteDestinatario_fk)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, cte.getValor());
            preparedStatement.setDate(2, java.sql.Date.valueOf(cte.getDataEmissao()));
            preparedStatement.setString(3, cte.getChaveAcesso());
            preparedStatement.setString(4, cte.getProduto());
            preparedStatement.setDouble(5, cte.getPesoBruto());
            preparedStatement.setDouble(6, cte.getPesoLiquido());
            preparedStatement.setDouble(7, cte.getVolume());
            preparedStatement.setString(8, cte.getEspecie());
            preparedStatement.setString(9, cte.getObservacao());
             
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            int idCte = 0;
            if(rs.next()){
                idCte = (rs.getInt(1));
            }
            int i = 0;
            while(cte.getNotasFiscais().size() >= 0){
                salvarNF(cte.getNotasFiscais().get(i), idCte);
            }
            
            preparedStatement.close();
        
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Método que atualiza o cte que já está salvo no banco de dados
     * @param cte
     * @return 
     */
    public boolean atualizarCTe(Cte cte){
        return false;
    }
    
    /**
     * Método que salva todas as nfs no banco de dados.
     * @param nf
     * @param idCte 
     */
    private void salvarNF(Nf nf, int idCte){
        String sql = "INSERT INTO notafiscal(valorNf, chaveAcesso, numero, cte_fk)"
                + "VALUES(?,?,?,?)";
        
        try{
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, nf.getValorNf());
            preparedStatement.setString(2, nf.getChaveAcesso());
            preparedStatement.setInt(3, nf.getNumeroNF());
            preparedStatement.setInt(4, idCte);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
