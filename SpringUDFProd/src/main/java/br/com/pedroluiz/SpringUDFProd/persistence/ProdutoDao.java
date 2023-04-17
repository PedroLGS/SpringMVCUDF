package br.com.pedroluiz.SpringUDFProd.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pedroluiz.SpringUDFProd.model.Produto;

@Repository
public class ProdutoDao implements IProdutoDao {
	
	@Autowired
	GenericDao gDao;

	private String callProcedureProduto(String opc, Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_produto(?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, opc);
		cs.setInt(2, p.getCodigo());
		cs.setString(3, p.getNome());
		cs.setFloat(4, p.getValor_unitario());
		cs.setInt(5, p.getQtd_estoque());
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		
		return saida;
	}
	
	@Override
	public String insert(Produto p) throws SQLException, ClassNotFoundException {
		String saida = callProcedureProduto("I", p);
		return saida;
	}

	@Override
	public String update(Produto p) throws SQLException, ClassNotFoundException {
		String saida = callProcedureProduto("U", p);
		return saida;
	}

	@Override
	public String delete(int codigo) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_produto(?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setInt(2, codigo);
		cs.setNull(3, Types.VARCHAR);
		cs.setNull(4, Types.VARCHAR);
		cs.setNull(5, Types.VARCHAR);
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		
		return saida;
	}

	@Override
	public Produto consultaProduto(Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, valor_unitario, qtd_estoque FROM produto WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			p.setValor_unitario(rs.getFloat("valor_unitario"));
			p.setQtd_estoque(rs.getInt("qtd_estoque"));
		}
		rs.close();
		ps.close();
		c.close();
		return p;
	}

	@Override
	public List<Produto> consultaProdutos() throws SQLException, ClassNotFoundException {
		List<Produto> produtos = new ArrayList<Produto>();
		
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, valor_unitario, qtd_estoque FROM produto";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			p.setValor_unitario(rs.getFloat("valor_unitario"));
			p.setQtd_estoque(rs.getInt("qtd_estoque"));
			produtos.add(p);
		}
		rs.close();
		ps.close();
		c.close();
		return produtos;
	}

	@Override
	public int consultaQtdForaEstoque(int val_min) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		int qtd = 0;
		String sql = "SELECT dbo.fn_valent(?) AS menor";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, val_min);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			qtd = rs.getInt("menor");
		}
		return qtd;
	}

	@Override
	public List<Produto> consultaListaProdutosForaEstoque(int val_min) throws SQLException, ClassNotFoundException {
		List<Produto> produtos = new ArrayList<Produto>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, valor_unitario, qtd_estoque FROM fn_abaixo_valor(?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, val_min);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			p.setValor_unitario(rs.getFloat("valor_unitario"));
			p.setQtd_estoque(rs.getInt("qtd_estoque"));
			produtos.add(p);
		}
		return produtos;
	}
}