/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cl.controle_logistica.DAO;

import br.com.cl.controle_logistica.classes.Cte;
import br.com.cl.controle_logistica.classes.CteCliente;
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
    public ArrayList<Cte> buscarCtePeloNumero(int numeroInformado) {
        String sql = "SELECT * FROM cte WHERE numeroCte LIKE '" + numeroInformado + "%'";

        ResultSet resultSet;
        ArrayList<Cte> fretes = new ArrayList<Cte>();

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo que consulta o cte pelo numero e retorna um cte.
     *
     * @param numero
     * @return
     */
    public Cte consultarCtePorNumero(int numero) {
        String sql = "SELECT * FROM cte WHERE numeroCte = " + numero;

        ResultSet resultSet;
        Cte cte = new Cte();

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            if (resultSet.next()) {

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
            }
            return cte;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo utilizada para buscar o CTECliente de um frete.
     *
     * @param idCte
     * @return
     */
    private ArrayList<CteCliente> buscarCteCliente(int idCte) {
        String sqlCteCliente = "SELECT * FROM cte_cliente WHERE cte_fk = " + idCte;
        
        ArrayList<CteCliente> cteClientes = new ArrayList<>();
        ResultSet resultSet;
        ClienteDAO clienteDAO = new ClienteDAO();
        CteDAO cteDAO = new CteDAO();
        
        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCteCliente);
            resultSet = preparedStatement.executeQuery(sqlCteCliente);

            while (resultSet.next()) {
                CteCliente cteCliente = new CteCliente();
                //cteCliente.setCliente(clienteDAO); verificar esse caso
                cteCliente.setCte(cteDAO.consultarCtePorNumero(resultSet.getInt("cte_fk")));
                cteCliente.setTomador(resultSet.getBoolean("tomadorServico"));
                cteClientes.add(cteCliente);
            }
            return cteClientes;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método que salva o cte no banco de dados.
     *
     * @param cte
     * @return
     */
    public boolean salvarCTe(Cte cte) {
        String sqlCte = "INSERT INTO cte(valor, dataEmissao, chaveAcesso, produto, pesoBruto, pesoLiquido, volume, especie,"
                + " observacao)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        String sqlUpdateCte = "UPDATE cte SET clienteRemetente_fk = ?, clienteDestinatario_fk = ?";

        String sqlClienteCte = "INSERT INTO cte_cliente(cte_fk, cliente_fk, tomadorServico) VALUES(?,?,?)";

        try {
            connection = Conexao.conexao();
            connection.setAutoCommit(false);

            // insere a cte (sem os clientes destinatário e remetente)
            PreparedStatement preparedStatementCte = connection.prepareStatement(sqlCte, Statement.RETURN_GENERATED_KEYS);

            preparedStatementCte.setBigDecimal(1, cte.getValor());
            preparedStatementCte.setDate(2, java.sql.Date.valueOf(cte.getDataEmissao()));
            preparedStatementCte.setString(3, cte.getChaveAcesso());
            preparedStatementCte.setString(4, cte.getProduto());
            preparedStatementCte.setDouble(5, cte.getPesoBruto());
            preparedStatementCte.setDouble(6, cte.getPesoLiquido());
            preparedStatementCte.setDouble(7, cte.getVolume());
            preparedStatementCte.setString(8, cte.getEspecie());
            preparedStatementCte.setString(9, cte.getObservacao());

            preparedStatementCte.executeUpdate();

            // recupera o id da cte inserida
            final ResultSet rsCte = preparedStatementCte.getGeneratedKeys();
            int idCte = 0;
            if (rsCte.next()) {
                idCte = (rsCte.getInt(1));
            }

            // insere cliente destinatário
            PreparedStatement preparedStatementClienteDestinatario = connection.prepareStatement(sqlClienteCte, Statement.RETURN_GENERATED_KEYS);

            preparedStatementClienteDestinatario.setInt(1, idCte);
            preparedStatementClienteDestinatario.setInt(2, cte.getClienteDestinatario().getCliente().getIdCliente());
            preparedStatementClienteDestinatario.setBoolean(3, cte.getClienteDestinatario().getTomador());

            preparedStatementClienteDestinatario.executeUpdate();

            // recupera o id do cliente destinatário da cte inserido
            final ResultSet rsClienteDestinatarioCte = preparedStatementClienteDestinatario.getGeneratedKeys();
            int idClienteDestinatarioCte = 0;
            if (rsClienteDestinatarioCte.next()) {
                idClienteDestinatarioCte = (rsClienteDestinatarioCte.getInt(1));
            }

            // insere cliente remetente
            PreparedStatement preparedStatementClienteRemetente = connection.prepareStatement(sqlClienteCte, Statement.RETURN_GENERATED_KEYS);

            preparedStatementClienteRemetente.setInt(1, idCte);
            preparedStatementClienteRemetente.setInt(2, cte.getClienteRemetente().getCliente().getIdCliente());
            preparedStatementClienteRemetente.setBoolean(3, cte.getClienteRemetente().getTomador());

            preparedStatementClienteRemetente.executeUpdate();

            // recupera o id do cliente remetente da cte inserido
            final ResultSet rsClienteRemetenteCte = preparedStatementClienteRemetente.getGeneratedKeys();
            int idClienteRemetenteCte = 0;
            if (rsClienteRemetenteCte.next()) {
                idClienteRemetenteCte = (rsClienteRemetenteCte.getInt(1));
            }
            // atualiza cte inserindo os ids dos clientes cte (destinatário e remetente)
            PreparedStatement preparedStatementUpdateCte = connection.prepareStatement(sqlUpdateCte, Statement.RETURN_GENERATED_KEYS);
            preparedStatementUpdateCte.setInt(1, idClienteRemetenteCte);
            preparedStatementUpdateCte.setInt(2, idClienteDestinatarioCte);
            preparedStatementUpdateCte.executeUpdate();

            // commita a fecha os PreparedStatement
            connection.commit();
            preparedStatementClienteDestinatario.close();
            preparedStatementClienteRemetente.close();
            preparedStatementUpdateCte.close();
            preparedStatementCte.close();

            // salva as notas fiscais
            int i = 0;
            while (cte.getNotasFiscais().size() > i) {
                salvarNF(cte.getNotasFiscais().get(i), idCte);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Método que atualiza o cte que já está salvo no banco de dados
     *
     * @param cte
     * @return
     */
    public boolean atualizarCTe(Cte cte) {
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
            while (cte.getNotasFiscais().size() >= i) {
                salvarNF(cte.getNotasFiscais().get(i), cte.getNumeroCte());
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Método que realiza a exclusão de um frete no banco de dados.
     *
     * @param frete
     * @return
     */
    public boolean deletarCTe(Cte cte) {
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
    private void deletarNf(Cte cte) {
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
     *
     * @param nf
     * @param idCte
     */
    private void salvarNF(Nf nf, int idCte) {
        String sql = "INSERT INTO notafiscal(valorNf, chaveAcesso, numero, cte_fk)"
                + "VALUES(?,?,?,?)";

        try {
            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, nf.getValorNf());
            preparedStatement.setString(2, nf.getChaveAcesso());
            preparedStatement.setInt(3, nf.getNumeroNF());
            preparedStatement.setInt(4, idCte);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que busca todas as Notas fiscais referentes ao frete selecionado.
     *
     * @param idCte
     * @return
     */
    private ArrayList<Nf> buscarNotasFiscais(int idCte) {
        String sql = "SELECT * FROM notaFiscal WHERE cte_fk = " + idCte + " ";

        ResultSet resultSet;
        ArrayList<Nf> notasFiscais = new ArrayList<Nf>();

        try {

            connection = Conexao.conexao();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                Nf nf = new Nf();
                nf.setChaveAcesso(resultSet.getString("chaveAcesso"));
                nf.setIdNotaFiscal(resultSet.getInt("idnotaFiscal"));
                nf.setNumeroNF(resultSet.getInt("numero"));
                nf.setValorNf(resultSet.getBigDecimal("valorNf"));

                notasFiscais.add(nf);
            }

            return notasFiscais;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
