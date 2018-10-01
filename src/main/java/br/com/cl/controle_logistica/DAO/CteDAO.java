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
import java.sql.Statement;
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
                
                cte.setNotasFiscais(buscarNotasFiscais(cte.getNumeroCte()));
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
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBigDecimal(1, cte.getValor());
            preparedStatement.setDate(2, java.sql.Date.valueOf(cte.getDataEmissao()));
            preparedStatement.setString(3, cte.getChaveAcesso());
            preparedStatement.setString(4, cte.getProduto());
            preparedStatement.setDouble(5, cte.getPesoBruto());
            preparedStatement.setDouble(6, cte.getPesoLiquido());
            preparedStatement.setDouble(7, cte.getVolume());
            preparedStatement.setString(8, cte.getEspecie());
            preparedStatement.setString(9, cte.getObservacao());
            preparedStatement.setInt(10, 1);
            preparedStatement.setInt(11, 1);
            
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            int idCte = 0;
            if(rs.next()){
                idCte = (rs.getInt(1));
            }
            int i = 0;
            preparedStatement.close();
            while(cte.getNotasFiscais().size() >= i){
                salvarNF(cte.getNotasFiscais().get(i), idCte);
                i++;
            }
            
           
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
        String sql = "UPDATE cte SET valor = ?, dataEmissao = ?, chaveAcesso = ?, produto = ?, pesoBruto = ?,"
                + " pesoLiquido = ?, volume = ?, especie = ?,"
                + " observacao = ?, clienteRemetente_fk = ?, clienteDestinatario_fk = ? WHERE numeroCte = " + cte.getNumeroCte();
        
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
            preparedStatement.setInt(10, 1);
            preparedStatement.setInt(11, 1);
            
            preparedStatement.executeUpdate();
           
            deletarNf(cte);
            
            int i = 0;
            preparedStatement.close();
            while(cte.getNotasFiscais().size() >= i){
                salvarNF(cte.getNotasFiscais().get(i), cte.getNumeroCte());
                i++;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Método que realiza a exclusão de um frete no banco de dados.
     * @param frete
     * @return 
     */
    public boolean deletarCTe(Cte cte){
        String sql = "DELETE FROM cte WHERE numeroCte = " + cte.getNumeroCte();
        deletarNf(cte);
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
    
    //================= NOTAS FISCAIS ==========================================
    
    /**
     * Método que deleta do banco de dados a(s) nota(s) conforme numero do cte.
     */
   private void deletarNf(Cte cte){
       String sql = "DELETE FROM notafiscal WHERE cte_fk = " + cte.getNumeroCte();
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
          
        }
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
    
    
    /**
     * Metodo que busca todas as Notas fiscais referentes ao frete selecionado.
     * @param idCte
     * @return 
     */
    private ArrayList<Nf> buscarNotasFiscais(int idCte){
        String sql = "SELECT * FROM notaFiscal WHERE cte_fk = " + idCte + " ";
        
        ResultSet resultSet;
        ArrayList<Nf> notasFiscais = new ArrayList<Nf>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Nf nf = new Nf();
                nf.setChaveAcesso(resultSet.getString("chaveAcesso"));
                nf.setIdNotaFiscal(resultSet.getInt("idnotaFiscal"));
                nf.setNumeroNF(resultSet.getInt("numero"));
                nf.setValorNf(resultSet.getBigDecimal("valorNf"));
                
                notasFiscais.add(nf);
            }
            
            return notasFiscais;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
