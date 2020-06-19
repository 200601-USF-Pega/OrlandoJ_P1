<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/js/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Characters</title>
</head>
<body>
	<h1>List of Characters:</h1>
	<table>
		<thead>
			<th>Character Name</th>
			<th>ID</th>
			<th>Attack Stat</th>
		</thead>	
		<tbody>
			<c:forEach var="character" items="${param.characters}">
				<td><c:out value="${character.name}"/></td>
				<td><c:out value="${character.id}"/></td>
				<td><c:out value="${character.attackStat}"/></td>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>