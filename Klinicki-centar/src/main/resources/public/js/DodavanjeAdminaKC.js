$(document).ready(function() {

    $("#dodaj").click(function() {
        let uslov = validacija();
        // var regex;
        // regex = /^[a-zA-Z]{1,20}$/;
        // $("#imeERROR").css('visibility', 'hidden')
        // $("#prezimeERROR").css('visibility', 'hidden')
        // $("#emailERROR").css('visibility', 'hidden')
        // $("#jmbgERROR").css('visibility', 'hidden')
        // $("#lozinkaERROR").css('visibility', 'hidden')

        // if (!regex.test($("#ime").val())) {
        //     $("#imeERROR").html("Ime može da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }
        // if (!regex.test($("#prezime").val())) {
        //     $("#prezimeERROR").html("Prezime može da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // regex = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/

        // if (!regex.test($("#email").val())) {
        //     $("#emailERROR").html("Neispravan email").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#JMBG").val().length != 13) {
        //     $("#jmbgERROR").html("JMBG mora da sadži 13 cifara").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // regex = /^(?=.*?[A-Z])(?=.*?[a-z]).{8,}$/

        // if (!regex.test($("#lozinka").val())) {
        //     $("#lozinkaERROR").html("Lozinka mora da sadži mala, velika slova i jedan broj.").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka").val().length < 8) {
        //     $("#lozinkaERROR").html("Lozinka mora da sadži najmanje 8 karaktera").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }
        // if ($("#lozinka").val() != $("#sifraPonovo").val()) {
        //     $("#lozinkaERROR").html("Lozinke se ne podudaraju").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }
        if (uslov) {
            $.ajax({
                url: "/klinicki-centar/adminKC/add",
                type: "POST",
                contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify({
                    id: 1,
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    jmbg: $("#jmbg").val(),
                    lozinka: $("#password").val(),
                    email: $("#email").val()
                }),
                success: function() {
                    // alert("Uspesno ste dodali novog admina");
                    window.location.replace("adminiKC.html")
                },
                error: function(jqXHR) {
                    if (jqXHR.status == 406) {
                        $("#emailError").text("Email koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("Email koji ste uneli vec postoji")
                    } else if (jqXHR.status == 409) {
                        $("#jmbgError").text("JMBG koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("JMBG koji ste uneli vec postoji")
                    } else {
                        alert("Error in call /adminKC/add")
                    }
                },
            })
        }
    });

});