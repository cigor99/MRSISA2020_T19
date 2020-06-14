$(document).ready(function() {

    $("#potvrdi").click(function() {
        let uslov = validacija();
        

        console.log("pre pokusaja")
        if (uslov) {
            $.ajax({
                type: 'POST',
                url: "/klinicki-centar/pacijent/register",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    jmbg: $("#jmbg").val(),
                    email: $("#email").val(),
                    lozinka: $("#password").val(),
                    pol: $("#pol").val(),
                    grad: $("#grad").val(),
                    brojTelefona: $("#telefon").val(),
                    drzava: $("#drzava").val(),
                    adresa: $("#adresa").val(),
                    jedinstveniBrOsig: $("#jedBrOsig").val()
                }),
                success: function() {
                    console.log("Prosao");
                    //window.location.replace("pacijenti.html")
                    $("#sve").remove();
                    let div = $("#rez");
                    div.css("visibility", 'visible');
                    div.append("Uspesno poslat zahtev.");
                    div.append("<br>");
                    div.append("Nakon što administrator pogleda vaš zahtev za registraciju dobićete email sa linkom za potvrdu naloga!");


                    // alert("Uspesno poslat zahtev")
                },
                error: function(jqXHR) {
                    if (jqXHR.status == 406) {
                        $("#emailError").text("Email koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("Email koji ste uneli vec postoji")
                    } else if (jqXHR.status == 409) {
                        $("#jmbgError").text("JMBG koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("JMBG koji ste uneli vec postoji")
                    } else if (jqXHR.status == 423) {
                        $("#jedBrOsigError").text("Broj osiguranika koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("Broj osiguranika koji ste uneli vec postoji")
                    } else {
                        alert("Error in call /pacijenti/register")
                    }

                }
            });
        }

    });
});