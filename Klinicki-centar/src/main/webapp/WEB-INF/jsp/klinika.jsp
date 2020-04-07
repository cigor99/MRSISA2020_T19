<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        
        <meta charset="utf-8">
        <title>Dodavanje klinike</title>
        <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
        <script src="klinika.js"></script>
    </head>
    <body>
        <h1>Dodavanje klinike</h1>

        <form action="">
            <table>
                <tr>
                    <td>Naziv klinike:</td>
                    <td><input type="text" name="naziv" id="naziv"></td>
                </tr>

                <tr>
                    <td>Adresa:</td>
                    <td><input type="text" name="adresa" id="adresa"></td>
                </tr>

                <tr>    
                    <td>Opis:</td>
                    <td><input type="text" name="opis" id="opis"></td>
                </tr>    
                <tr>
                    <td><input type="button" value="Dodaj kliniku" id="dodajBtn"></td>

                </tr>            
            </table>
        </form>
    </body>
</html>