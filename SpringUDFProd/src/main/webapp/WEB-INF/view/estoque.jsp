<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css" />'>
<title>Estoque</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<br>
	<div align="center">
		<form action="" method="post">
			<table>
			<tr>
				<td colspan="2"><input type="number" name="minval" id="minval" placeholder="Minimo" step="1"
				required min="0">
				</td>
			</tr>
			<tr>
				<td colspan="1"><input type="submit" name="botao" id="botao" value="Quantidade"></td>
				<td colspan="1"><input type="submit" name="botao" id="botao" value="Listar"></td>
			</tr>
			</table>
		</form>
	</div>
	<br>
	<div align="center">
		<c:if test="${not empty val}">
		<c:out value="${val}"></c:out>
		</c:if>
	</div>
	<br>
	<div align="center">
	<c:if test="${not empty list}">
	<table>
		<thead>
		<tr>
			<th>Codigo</th>
			<th>Nome</th>
			<th>Valor_Unitario</th>
			<th>Qtd_Estoque</th>
		</tr>
		</thead>
	<tbody>
			<c:forEach items="${list}" var="p">
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
