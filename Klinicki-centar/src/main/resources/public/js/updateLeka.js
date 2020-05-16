$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        url: "/klinicki-centar/sifarnikLekova/getUpdate/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#ID").val(data.id);
            $("#naziv").val(data.naziv);
            $("#sifra").val(data.sifra)
        }, error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })

    $("#izmeniBtn").click(function () {
        var regex;
        regex = /^[a-zA-Z0-9]{1,20}$/;
        $("#nazivERROR").css('visibility', 'hidden')
        $("#sifraERROR").css('visibility', 'hidden')

        if ($("#naziv").val() == "") {
            $("#nazivERROR").html("Naziv je obavezno polje").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if ($("#sifra").val() == "") {
            $("#sifraERROR").html("Šifra je obavezno polje").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if (!regex.test($("#naziv").val())) {
            $("#nazivERROR").html("Naziv može da sadrži samo slova i brojeve").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if (!regex.test($("#sifra").val())) {
            $("#sifraERROR").html("Šifra može da sadrži samo slova i brojeve").css('visibility', 'visible').css('color', 'red');
            return;
        }

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/sifarnikLekova/Update",
            dataType: 'json',
            data: JSON.stringify({
                id: $("#ID").val(),
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val()
            }),
            success: function () {
                window.location.replace("sifarnikLekova.html")
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    })

});