<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Lista</title>
</head>
<body>
<h1>Lekari</h1>
</body>
<div class="container">
		<table>
			<thead>
				<tr>
					<th>Email</th>
					<th>Password</th>
					<th>Ime</th>
					<th>Prezime</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lekari}" var="lekar">
					<tr>
						<td><c:out value="${lekar.email}" /></td>
						<td><c:out value="${lekar.lozinka}" /></td>
						<td><c:out value="${lekar.ime}" /></td>
						<td><c:out value="${lekar.prezime}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<form class="form-style-4" action="/klinicki-centar/lekari/new" method="get">
			<div style="text-align: center;">
				<input type="submit" value="Dodaj novog lekara">
			</div>
		</form>
	</div>

</html>
