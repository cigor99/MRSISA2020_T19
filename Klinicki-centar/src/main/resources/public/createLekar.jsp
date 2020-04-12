<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Dodaj lekara</title>
</head>
<body>
<h1>Dodavanje novog lekara</h1>

<div class="container">
<c:url var="action" value="/lekari/create" />
<form id="formLekar" action="${action}" method="post" modelAttribute="lekar">
	<label for="fname">E-mail:</label>
	<input type="text" id="email" name="email"><br><br>
	<label for="lname">Lozinka:</label>
	<input type="text" id="lozinka" name="lozinka"><br><br>
	<label for="lname">Ime:</label>
	<input type="text" id="ime" name="ime"><br><br>
	<label for="lname">Prezime:</label>
	<input type="text" id="prezime" name="prezime"><br><br>
	<input type="submit" value="Submit">
</form>
</div>

</body>

</html>