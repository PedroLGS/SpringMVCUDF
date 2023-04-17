<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css" />'>
<title>Produtos</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"/>
	</div>
	<br />
	<div align="center">
		<form action="produto" method="post">
			<table>
			<tr>
				<td colspan="3"><input type="number" name="botaocodigo" id="botaocodigo" placeholder="Codigo"
				step="1" min="1" value="<c:out value="${produto.codigo}"></c:out>"></td>
				<td colspan="1"><input type="submit" name="botao" id="botao" value="Buscar"></td>
			</tr>
			<tr>
				<td colspan="4"><input type="text" name="botaonome" id="botaonome" placeholder="Nome"
				value="<c:out value="${produto.nome}"></c:out>"></td>
			</tr>
			<tr>
				<td colspan="4"><input type="number" name="botaovu" id="botaovu" placeholder="Valor Unitario"
				step="0.01" value="<c:out value="${produto.valor_unitario}"></c:out>"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="number" name="botaoqtd" id="botaoqtd" placeholder="Quantidade"
				step="1" value="<c:out value="${produto.qtd_estoque}"></c:out>"></td>
			</tr>
			<tr>
				<td colspan="1"><input type="submit" name="botao" 
                                       id="botao" value="Inserir"></td>
				<td colspan="1"><input type="submit" name="botao" 
                                       id="botao" value="Atualizar"></td>
				<td colspan="1"><input type="submit" name="botao" 
                                       id="botao" value="Deletar"></td>
				<td colspan="1"><input type="submit" name="botao" 
                                       id="botao" value="Listar"></td>
			</tr>
			</table>
		</form>
	</div>
		<br>
	<div align="center">
		<c:if test="${not empty erro}">
		<c:out value="${erro}"></c:out>
		</c:if>
	</div>
		<br>
	<div align="center">
	<c:if test="${not empty listaproduto }">
		<table>
		<thead>
			<tr>
				<th>Codigo</th>
				<th>Nome</th>
				<th>Valor Unitario</th>
				<th>Quantidade</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaproduto}" var="p">
		<tr>
			<td><c:out value="${p.codigo}"></c:out></td>
			<td><c:out value="${p.nome}"></c:out></td>
			<td><c:out value="${p.valor_unitario}"></c:out></td>
			<td><c:out value="${p.qtd_estoque}"></c:out></td>
		</tr>
			</c:forEach>
		</tbody>
		</table>
	</c:if>
	</div>
</body>
</html>