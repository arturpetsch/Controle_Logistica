/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Veiculo;
import br.com.cl.controle_logistica.classes.Viagem;
import br.com.cl.controle_logistica.classes.ViagemDespesa;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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
public class ViagemDAO {

     private Connection connection = null;
    
     /**
      * Método que consulta viagem atraves do id do veiculo e retorna um arraylist de viagem.
      * @param idVeiculo
      * @return 
      */
     public ArrayList<Viagem> consultarViagemPorIdVeiculo(int idVeiculo){
        String sql = "SELECT * FROM viagem WHERE veiculo_fk = " + idVeiculo + " order by dataViagem";
        
        ResultSet resultSet;
        ArrayList<Viagem> viagens = new ArrayList<Viagem>();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        CteDAO cteDAO = new CteDAO();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            
            while(resultSet.next()){
                Viagem viagem = new Viagem();
                
                viagem.setIdViagem(resultSet.getInt("idViagem"));
                viagem.setVeiculo(veiculoDAO.consultarVeiculoPorId(resultSet.getInt("veiculo_fk")));
                viagem.setCte(cteDAO.consultarCtePorNumero(resultSet.getInt("cte_fk")));
                viagem.setQtdeKmPrevisto(resultSet.getDouble("totalKmPrevisto"));
                viagem.setQtdeKmRealizado(resultSet.getDouble("totalKmRealizado"));
                viagem.setValorTotalGastoPrevisto(resultSet.getBigDecimal("valorTotalGastoPrevisto"));
                viagem.setValorTotalGastoRealizado(resultSet.getBigDecimal("valorTotalGastoRealizado"));
                viagem.setValorTotalGanhoPrevisto(resultSet.getBigDecimal("valorTotalGanhoPrevisto"));
                viagem.setValorTotalGanhoRealizado(resultSet.getBigDecimal("valorTotalGanhoRealizado"));
                viagem.setDataViagem(resultSet.getDate("dataViagem").toLocalDate());
                viagem.setDespesas(buscarDespesas(viagem.getIdViagem()));
                viagens.add(viagem);
            }
            return viagens;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
     }
     
     /**
     * Método que salva o veículo no banco de dados.
     * @param veiculo
     * @return 
     */
    public boolean salvarViagem(Viagem viagem){
        String sqlViagem = "INSERT INTO viagem(veiculo_fk, cte_fk, totalKmPrevisto, totalKmRealizado, valorTotalGastoPrevisto,"
                + " valorTotalGastoRealizado, valorTotalGanhoPrevisto, valorTotalGanhoRealizado, dataViagem)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatementViagem = connection.prepareStatement(sqlViagem, Statement.RETURN_GENERATED_KEYS);
            preparedStatementViagem.setInt(1, viagem.getVeiculo().getIdVeiculo());
            preparedStatementViagem.setInt(2, viagem.getCte().getNumeroCte());
            preparedStatementViagem.setDouble(3, viagem.getQtdeKmPrevisto());
            preparedStatementViagem.setDouble(4, viagem.getQtdeKmRealizado());
            preparedStatementViagem.setBigDecimal(5, viagem.getValorTotalGastoPrevisto());
            preparedStatementViagem.setBigDecimal(6, viagem.getValorTotalGastoRealizado());
            preparedStatementViagem.setBigDecimal(7, viagem.getValorTotalGanhoPrevisto());
            preparedStatementViagem.setBigDecimal(8, viagem.getValorTotalGanhoRealizado());
            preparedStatementViagem.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
            
            preparedStatementViagem.executeUpdate();
            
            final ResultSet rsViagem = preparedStatementViagem.getGeneratedKeys();
            int idViagem = 0;
            if(rsViagem.next()){
                idViagem = (rsViagem.getInt(1));
            }
            
            int i = 0;
            while(viagem.getDespesas().size() > i){
                salvarDespesas(viagem.getDespesas().get(i), idViagem);
                i++;
            }

            preparedStatementViagem.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Metodo que altera uma viagem já existente no banco de dados.
     * @param viagem
     * @return 
     */
    public boolean alterarViagem(Viagem viagem){
        String sqlViagem = "UPDATE viagem SET veiculo_fk = ?, cte_fk = ?, totalKmPrevisto = ?, totalKmRealizado = ?, valorTotalGastoPrevisto = ?,"
                + " valorTotalGastoRealizado = ?, valorTotalGanhoPrevisto = ?, valorTotalGanhoRealizado = ? WHERE idViagem = " + viagem.getIdViagem();
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatementViagem = connection.prepareStatement(sqlViagem, Statement.RETURN_GENERATED_KEYS);
            preparedStatementViagem.setInt(1, viagem.getVeiculo().getIdVeiculo());
            preparedStatementViagem.setInt(2, viagem.getCte().getNumeroCte());
            preparedStatementViagem.setDouble(3, viagem.getQtdeKmPrevisto());
            preparedStatementViagem.setDouble(4, viagem.getQtdeKmRealizado());
            preparedStatementViagem.setBigDecimal(5, viagem.getValorTotalGastoPrevisto());
            preparedStatementViagem.setBigDecimal(6, viagem.getValorTotalGastoRealizado());
            preparedStatementViagem.setBigDecimal(7, viagem.getValorTotalGanhoPrevisto());
            preparedStatementViagem.setBigDecimal(8, viagem.getValorTotalGanhoRealizado());
            preparedStatementViagem.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
            
            preparedStatementViagem.executeUpdate();
        
            deletarDespesa(viagem);
            
            int i = 0;
            while(viagem.getDespesas().size() > i){
                salvarDespesas(viagem.getDespesas().get(i), viagem.getIdViagem());
                i++;
            }

            preparedStatementViagem.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Metodo que deleta do banco a viagem selecionada.
     * @param viagem
     * @return 
     */
    public boolean deletarViagem(Viagem viagem){
        String sqlViagem = "DELETE FROM viagem WHERE idViagem = " + viagem.getIdViagem();
        deletarDespesa(viagem);
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlViagem);
            preparedStatement.executeUpdate(sqlViagem);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
  //=============================Despesas===================================
    
    /**
     * Metodo que deleta do banco todas as despesas de uma viagem.
     * @param viagem 
     */
    private void deletarDespesa(Viagem viagem){
        String sqlDespesa = "DELETE FROM viagemdespesa WHERE viagem_fk = " + viagem.getIdViagem();
        
        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDespesa);
            preparedStatement.executeUpdate(sqlDespesa);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
          
        }
    }
    
    
    /**
     * Metodo que salva as despesas da viagem.
     * @param viagemDespesa
     * @param idViagem 
     */
    private void salvarDespesas(ViagemDespesa viagemDespesa, int idViagem){
        String sqlDespesas = "INSERT INTO viagemdespesa(viagem_fk, tipoDespesa, descricao, valor, idViagemDespesa)"
                + "VALUES(?,?,?,?,?)";
        
        try{
            connection = Conexao.conexao();
            PreparedStatement preparedStatementDespesas = connection.prepareStatement(sqlDespesas);
            preparedStatementDespesas.setInt(1, idViagem);
            preparedStatementDespesas.setString(2, viagemDespesa.getTipoDespesa());
            preparedStatementDespesas.setString(3, viagemDespesa.getDescricao());
            preparedStatementDespesas.setBigDecimal(4, viagemDespesa.getValor());
            preparedStatementDespesas.setInt(5, viagemDespesa.getIdViagemDespesa());
            
            preparedStatementDespesas.executeUpdate();
            preparedStatementDespesas.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que busca e retorna todas as depesas de uma viagem atraves de seu id.
     * @param idViagem
     * @return 
     */
   private ArrayList<ViagemDespesa> buscarDespesas(int idViagem){
       String sqlDespesa = "SELECT * FROM viagemdespesa WHERE viagem_fk = " + idViagem;
       
       ResultSet resultSetDespesa;
       ArrayList<ViagemDespesa> despesas = new ArrayList<ViagemDespesa>();
        
        try {
            
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDespesa);
            resultSetDespesa = preparedStatement.executeQuery(sqlDespesa);
            
            while(resultSetDespesa.next()){
                ViagemDespesa viagemDespesa = new ViagemDespesa();
                viagemDespesa.setIdViagemDespesa(resultSetDespesa.getInt("idViagemDespesa"));
                viagemDespesa.setDescricao(resultSetDespesa.getString("descricao"));
                viagemDespesa.setTipoDespesa(resultSetDespesa.getString("tipoDespesa"));
                viagemDespesa.setValor(resultSetDespesa.getBigDecimal("valor"));
                despesas.add(viagemDespesa);
            }
            return despesas;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
   }
}
