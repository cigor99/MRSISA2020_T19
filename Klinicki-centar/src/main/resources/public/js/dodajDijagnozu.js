$(document).ready(function() {
    $("#dodaj").click(function() {
        var regex = /^[a-zA-Z0-9 ]{1,20}$/;
        $("#sifraERROR").css('visibility', 'hidden')
        $("#nazivERROR").css('visibility', 'hidden')

        if ($("#naziv").val() == "") {
            $("#nazivERROR").html("Naziv je obavezno polje").css('visibility', 'visible').css('color', 'red');
            return;
        }
        // if ($("#opis").val() == "") {
        //     $("#opisERROR").html("Opis je obavezno polje").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }
        if ($("#sifra").val() == '') {
            $("#sifraERROR").text("Šifra je obavezno polje").css('visibility', 'visible').css('color', 'red');
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
            type: "post",
            url: "/klinicki-centar/sifarnikDijagnoza/addDijagnoza",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: 1,
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val(),
                opis: $("#opis").val()
            }),
            success: function(data) {
                // alert("USPESNO")
                window.location.replace("dijagnoze.html")
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    });


});