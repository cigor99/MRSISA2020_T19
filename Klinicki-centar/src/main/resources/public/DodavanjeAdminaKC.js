$(document).ready(function () {

    $("#dodaj").click(function () {
        var regex;
        regex = /^[a-zA-Z]{1,20}$/;
        $("#imeERROR").css('visibility', 'hidden')
        $("#prezimeERROR").css('visibility', 'hidden')
        $("#emailERROR").css('visibility', 'hidden')
        $("#jmbgERROR").css('visibility', 'hidden')
        $("#lozinkaERROR").css('visibility', 'hidden')

        if (!regex.test($("#ime").val())) {
            $("#imeERROR").html("Ime može da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if (!regex.test($("#prezime").val())) {
            $("#prezimeERROR").html("Prezime može da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
            return;
        }

        regex = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/

        if (!regex.test($("#email").val())) {
            $("#emailERROR").html("Neispravan email").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#jmbg").val().length != 13) {
            $("#jmbgERROR").html("JMBG mora da sadži 13 cifara").css('visibility', 'visible').css('color', 'red');
            return;
        }

        regex = /^(?=.*?[A-Z])(?=.*?[a-z]).{8,}$/

        if (!regex.test($("#lozinka").val())) {
            $("#lozinkaERROR").html("Lozinka mora da sadži mala, velika slova i jedan broj.").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#lozinka").val().length < 8) {
            $("#lozinkaERROR").html("Lozinka mora da sadži najmanje 8 karaktera").css('visibility', 'visible').css('color', 'red');
            return;
        }
        $.ajax({
            url: "/klinicki-centar/adminKC/add",
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: 1,
                ime: $("#ime").val(),
                prezime: $("#prezime").val(),
                jmbg: $("#JMBG").val(),
                lozinka: $("#sifra").val(),
                email: $("#email").val()
            }),
            success: function () {
                // alert("Uspesno ste dodali novog admina");
                window.location.replace("adminiKC.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    });
});