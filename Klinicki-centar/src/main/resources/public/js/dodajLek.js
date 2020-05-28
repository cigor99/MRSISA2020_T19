$(document).ready(function() {
    $("#dodaj").click(function() {
        var regex = /^[a-zA-Z0-9 ]{1,20}$/;
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
            url: "/klinicki-centar/sifarnikLekova/addLek",
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: "1",
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val()
            }),
            success: function(data) {
                window.location.replace("sifarnikLekova.html")
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    });
});