$(document).ready(function () {
    $('#dodajBtn').click(function () {
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
                alert("Uspesno dodavanje klinike!")
            }
        })
    });
    $("#obrisiBtn").click(function () {
        $.ajax({
            type: "DELETE",
           
            url: "/klinicki-centar/klinika/delete/" + $("#ID").val() ,
            success: function(){
                alert("USPESNO BRISANJE KLINIKE");
            }
        })
    });
    $('#izmeniBtn').click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/klinika/update",
            dataType: 'json',
            data: JSON.stringify({
                naziv: $('#naziv').val(),
                adresa: $('#adresa').val(),
                opis: $("#opis").val()
            }),
            success: function () {
                alert("Uspesna izmena klinike!")
            }
        })
    });
});