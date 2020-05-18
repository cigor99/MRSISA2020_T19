$(document).ready(function() {
    var tokenCoded = window.location.href.split("?")[1];
    var tokenJednako = tokenCoded.split("&")[0];
    var token = tokenJednako.split("=")[1];
    if (token != "superAdmin") {
        $.ajax({
            url: "/klinicki-centar/KC/aktivacija/" + token,
            type: "get",
            success: function(data) {
                window.user = data;

            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }

        })
    } else {
        window.user = {};
        window.user.tipKorisnika = "ADMINISTRATOR_KLINICKOG_CENTRA";
        window.user.id = 1;
    }
    dodajPolja();

    $("#nazad").click(function() {
        window.location.replace("login.html");
    });

    $("#sacuvaj").click(function() {
        var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        let obaveznoPolje = false;
        let uslov = false;
        if ($("#password").val() == "") {
            $("#lozinkaError").text("Lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#password1").val() == "") {
            $("#lozinka1Error").text("Ponovljena lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if (obaveznoPolje) {
            return false;
        }


        if (!regPass.test($("#password").val())) {
            $("#lozinkaError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regPass.test($("#password1").val())) {
            $("#lozinka1Error").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if ($("#password1").val() != $("#password").val()) {
            $("#lozinkaError").text("Lozinka se ne poklapaju").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (uslov) {
            return false;
        }



        if (window.user.tipKorisnika == "MEDICINSKA_SESTRA") {
            $.ajax({
                url: "/klinicki-centar/medicinskaSestra/prvaSifra",
                type: "put",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    id: window.user.id,
                    sifra: $("#password").val()
                }),
                success: function(data) {
                    let rez = $("#rezultat").empty();
                    rez.append("Uspešno ste aktivirali nalog!");
                    $("#sifra").remove();
                    $("#sacuvaj").css('visibility', 'hidden');
                    $("#nazad").css('visibility', 'visible');
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                }
            })
        } else if (window.user.tipKorisnika == "LEKAR") {
            $.ajax({
                url: "/klinicki-centar/lekar/prvaSifra",
                type: "put",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    id: window.user.id,
                    sifra: $("#password").val()
                }),
                success: function(data) {
                    let rez = $("#rezultat").empty();
                    rez.append("Uspešno ste aktivirali nalog!");
                    $("#sifra").remove();
                    $("#sacuvaj").css('visibility', 'hidden');
                    $("#nazad").css('visibility', 'visible');
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                }
            })
        } else if (window.user.tipKorisnika == "ADMINISTRATOR_KLINICKOG_CENTRA") {

            $.ajax({
                url: "/klinicki-centar/adminKC/prvaSifra",
                type: "put",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    id: window.user.id,
                    sifra: $("#password").val()
                }),
                success: function(data) {
                    let rez = $("#rezultat").empty();
                    rez.append("Uspešno ste aktivirali nalog!");
                    $("#sifra").remove();
                    $("#sacuvaj").css('visibility', 'hidden');
                    $("#nazad").css('visibility', 'visible');
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                }
            })
        } else if (window.user.tipKorisnika == "ADMINISTRATOR_KLINIKE") {
            alert("U IZDRADI");
        }
    });
});

function dodajPolja() {
    let rez = $("#rezultat")
    rez.append("Na korak ste od aktivacije naloga. Molimo Vas unesite novu lozinku.");
    let div = $("#sifra");


    let p5 = $("<p></p>");
    p5.attr('id', 'lozinkaError');
    p5.css('visibility', 'hidden');
    let p6 = $("<p></p>");
    p6.attr('id', 'lozinka1Error');
    p6.css('visibility', 'hidden');

    let passDIv = $("<div></div>");
    passDIv.attr("class", 'form-group');
    let passLabel = $("<label></label>")
    passLabel.append("Lozinka");
    let passInput = $("<input></input>");
    passInput.attr("type", 'password');
    passInput.attr("class", "form-control");
    passInput.attr("id", 'password');

    passDIv.append(passLabel);
    passDIv.append(passInput);
    passDIv.append(p5);

    let passDIv1 = $("<div></div>");
    passDIv1.attr("class", 'form-group');
    let passLabel1 = $("<label></label>")
    passLabel1.append("Ponovite lozinku");
    let passInput1 = $("<input></input>");
    passInput1.attr("type", 'password');
    passInput1.attr("class", "form-control");
    passInput1.attr("id", 'password1');

    passDIv1.append(passLabel1);
    passDIv1.append(passInput1);
    passDIv1.append(p6);

    div.append(passDIv);
    div.append(passDIv1);
}