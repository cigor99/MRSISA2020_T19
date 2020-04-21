$(document).ready(function () {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/klinika/page",
        success: function (data) {

            // alert(JSON.stringify(data));
            for (let klinika of data) {

                var table = $("#klinike")
                let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

                let idTD = $("<td>" + klinika.id + "</td>")
                let nazivTd = $("<td>" + klinika.naziv + "</td>")
                let adresaTD = $("<td>" + klinika.adresa + "</td>")
                let opisTD = $("<td>" + klinika.opis + "</td>")
                let izmeniTD = $("<td>" + "<a href=\"updateKlinike.html?id=" + klinika.id + "\">Izmeni</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(adresaTD);
                tr.append(opisTD);
                tr.append(izmeniTD);
                table.append(tr);
            }

        }
    });



    $('#dodajBtn').click(function () {
        $("#nazivError").css('visibility', 'hidden');
        $("#adresaError").css('visibility', 'hidden');
        $("#opisError").css('visibility', 'hidden');

        var regex = /^[a-zA-Z0-9]{1,20}$/;
        alert($("#naziv").val().length)
        if ($("#naziv").val().length > 20) {
            $("#nazivError").text("Naziv moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if ($("#adresa").val().length > 20) {
            $("#adresaError").text("Adresa moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if ($("#opis").val().length > 50) {
            $("#opisError").text("Opis moze da sadrzi maksimalno 50 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#naziv").val() == "") {
            $("#nazivError").text("Naziv je obavezno polje!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if ($("#adresa").val() == "") {
            $("#adresaError").text("Adresa je obavezno polje!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if ($("#opis").val() == "") {
            $("#opisError").text("Opis je obavezno polje!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if (!regex.test($("#naziv").val())) {
            $("#nazivError").text("Naziv moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if (!regex.test($("#adresa").val())) {
            $("#adresaError").text("Adres moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
            return;
        }

        var regex = /^[a-zA-Z0-9]{1,50}$/;
        if (!regex.test($("#opis").val())) {
            $("#opisError").text("Opis moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
            return;
        }


        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/klinika/add",
            dataType: 'json',
            data: JSON.stringify({
                naziv: $('#naziv').val(),
                adresa: $('#adresa').val(),
                opis: $("#opis").val()
            }),
            success: function () {
                window.location.replace("Klinike.html")
                // alert("Uspesno dodavanje klinike!")
            }
        })
    });
    $("#obrisiBtn").click(function () {
        $.ajax({
            type: "DELETE",

            url: "/klinicki-centar/klinika/delete/" + $("#IDbrisanje").val(),
            success: function () {
                $("#tr" + $("#IDbrisanje").val()).remove();
                $("#IDbrisanje").val("");
                alert("USPESNO BRISANJE KLINIKE");
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    });

});