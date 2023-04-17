package br.com.pedroluiz.SpringUDFProd.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.pedroluiz.SpringUDFProd.model.Produto;

public interface IProdutoDao {
	
	public String insert(Produto p) throws SQLException,ClassNotFoundException;
	public String update(Produto p) throws SQLException,ClassNotFoundException;
	public String delete(int codigo) throws SQLException,ClassNotFoundException;
	public Produto consultaProduto(Produto p) throws SQLException, ClassNotFoundException;
	public List<Produto> consultaProdutos() throws SQLException, ClassNotFoundException;
	public int consultaQtdForaEstoque(int val_min) throws SQLException, ClassNotFoundException;
	public List<Produto> consultaListaProdutosForaEstoque(int val_min) throws SQLException, ClassNotFoundException;


}
