$(document).ready(function() {
    $.ajax({
        url: "/klinicki-centar/login/getLoggedUser",
        type: "get",
        success: function(data) {
            window.ulogovani = data;

        },
        async: false,
    });

    if (window.tipKorisnika == "sestra") {
        dodajPolja(window.ulogovani);

    } else if (window.tipKorisnika == "adminKC") {
        dodajPolja(window.ulogovani);

    } else if (window.tipKorisnika == "lekar") {
        dodajPolja(window.ulogovani);

    } else if (window.tipKorisnika == "adminKlinike") {
        dodajPolja(window.ulogovani);

    } else if (window.tipKorisnika == "pacijent") {
        dodajPolja(window.ulogovani);

        $.ajax({
            url: "/klinicki-centar/pacijent/getOnePacijent/" + window.ulogovani.id,
            type: "get",
            success: function(pacijent) {
                let forma = $("#forma");

                let p1 = $("<p></p>");
                p1.attr('id', 'polError');
                p1.css('visibility', 'hidden');

                let p2 = $("<p></p>");
                p2.attr('id', 'telefonError');
                p2.css('visibility', 'hidden');

                let p3 = $("<p></p>");
                p3.attr('id', 'jedBrOsigError');
                p3.css('visibility', 'hidden');

                let p4 = $("<p></p>");
                p4.attr('id', 'AdresaError');
                p4.css('visibility', 'hidden');

                let p5 = $("<p></p>");
                p5.attr('id', 'GradError');
                p5.css('visibility', 'hidden');

                let p6 = $("<p></p>");
                p6.attr('id', 'DrzavaError');
                p6.css('visibility', 'hidden');

                let polDIV = $("<div></div>");
                polDIV.attr("class", 'form-group');
                let pollabel = $("<label></label>")
                pollabel.append("Pol");
                let polInput = $("<input></input>");
                polInput.attr("type", 'text');
                polInput.attr("class", "form-control");
                polInput.attr('value', pacijent.pol);
                polInput.attr("id", 'pol');
                polDIV.append(pollabel);
                polDIV.append(polInput);
                polDIV.append(p1);

                let telefonDIV = $("<div></div>");
                telefonDIV.attr("class", 'form-group');
                let telefonlabel = $("<label></label>")
                telefonlabel.append("Broj telefona");
                let telefonInput = $("<input></input>");
                telefonInput.attr("type", 'text');
                telefonInput.attr("class", "form-control");
                telefonInput.attr('value', pacijent.brojTelefona);
                telefonInput.attr("id", 'telefon');
                telefonDIV.append(telefonlabel);
                telefonDIV.append(telefonInput);
                telefonDIV.append(p2);

                let osigDIV = $("<div></div>");
                osigDIV.attr("class", 'form-group');
                let osiglabel = $("<label></label>")
                osiglabel.append("Jedinstveni broj osiguranja");
                let osigInput = $("<input></input>");
                osigInput.attr("type", 'text');
                osigInput.attr("readonly", true);
                osigInput.attr("class", "form-control");
                osigInput.attr('value', pacijent.jedinstveniBrOsig);
                osigInput.attr("id", 'jedBrOsig');
                osigDIV.append(osiglabel);
                osigDIV.append(osigInput);
                osigDIV.append(p3);

                let AdresaDIV = $("<div></div>");
                AdresaDIV.attr("class", 'form-group');
                let Adresalabel = $("<label></label>")
                Adresalabel.append("Adresa");
                let AdresaInput = $("<input></input>");
                AdresaInput.attr("type", 'text');
                AdresaInput.attr("class", "form-control");
                AdresaInput.attr('value', pacijent.adresa);
                AdresaInput.attr("id", 'adresa');
                AdresaDIV.append(Adresalabel);
                AdresaDIV.append(AdresaInput);
                AdresaDIV.append(p4);

                let GradDIV = $("<div></div>");
                GradDIV.attr("class", 'form-group');
                let Gradlabel = $("<label></label>")
                Gradlabel.append("Grad");
                let GradInput = $("<input></input>");
                GradInput.attr("type", 'text');
                GradInput.attr("class", "form-control");
                GradInput.attr('value', pacijent.grad);
                GradInput.attr("id", 'grad');
                GradDIV.append(Gradlabel);
                GradDIV.append(GradInput);
                GradDIV.append(p5);

                let DrzavaDIV = $("<div></div>");
                DrzavaDIV.attr("class", 'form-group');
                let Drzavalabel = $("<label></label>")
                Drzavalabel.append("Država");
                let DrzavaInput = $("<input></input>");
                DrzavaInput.attr("type", 'text');
                DrzavaInput.attr("class", "form-control");
                DrzavaInput.attr('value', pacijent.drzava);
                DrzavaInput.attr("id", 'drzava');
                DrzavaDIV.append(Drzavalabel);
                DrzavaDIV.append(DrzavaInput);
                DrzavaDIV.append(p6);




                forma.append(polDIV);
                forma.append(telefonDIV);
                forma.append(osigDIV);
                forma.append(AdresaDIV);
                forma.append(GradDIV);
                forma.append(DrzavaDIV);
            }
        });

    }

    $("#sacuvaj").click(function() {
        $("#lozinkaError").css('visibility', 'hidden');
        $("#lozinka1Error").css('visibility', 'hidden');
        $("#imeError").css('visibility', 'hidden');
        $("#prezimeError").css('visibility', 'hidden');
        $("#DrzavaError").css('visibility', 'hidden');
        $("#GradError").css('visibility', 'hidden');
        $("#AdresaError").css('visibility', 'hidden');
        $("#jedBrOsigError").css('visibility', 'hidden');
        $("#telefonError").css('visibility', 'hidden');
        $("#polError").css('visibility', 'hidden');
        // $("#emailError").css('visibility', 'hidden');
        // $("#jmbgError").css('visibility', 'hidden');



        var regName = /^([a-zA-Z]{3,30}\s*)+$/;
        var regAdress = /^([a-zA-Z0-9 .,'-]{3,30}\s*)+$/;
        // var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        // var regJmbg = /^[0-9]{13}$/;
        var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        var regPhone = /^[+]{1}[0-9]{11,12}$/;
        let obaveznoPolje = false;
        let uslov = false;
        if ($("#drzava").val() == "") {
            $("#DrzavaError").text("Država je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#grad").val() == "") {
            $("#GradError").text("Grad je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#adresa").val() == "") {
            $("#AdresaError").text("Adresa je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#telefon").val() == "") {
            $("#telefonError").text("Telefon je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        // if ($("#jedBrOsig").val() == "") {
        //     $("#jedBrOsigError").text("Jedinstveni broj osiguranja je obavezno polje").css('visibility', 'visible').css('color', 'red');
        //     obaveznoPolje = true;
        // }
        if ($("#pol").val() == "") {
            $("#polError").text("Pol je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#ime").val() == "") {
            $("#imeError").text("Ime je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#prezime").val() == "") {
            $("#prezimeError").text("Prezime je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#password").val() == "") {
            $("#lozinkaError").text("Lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if ($("#password1").val() == "") {
            $("#lozinka1Error").text("Ponovljena lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
            obaveznoPolje = true;
        }
        if (obaveznoPolje) {
            return;
        }




        if (!regName.test($("#ime").val())) {
            $("#imeError").text("Ime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regName.test($("#prezime").val())) {
            $("#prezimeError").text("Prezime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regName.test($("#drzava").val())) {
            $("#DrzavaError").text("Država  sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regName.test($("#grad").val())) {
            $("#GradError").text("Grad sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
        if (!regAdress.test($("#adresa").val())) {
            $("#AdresaError").text("Adresa sme da sadrži samo mala slova, velika slova, brojeve ,-.' minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
        if ($("#pol").val() != "MUSKI" && $("#pol").val() != "ZENSKI" &&
            $("#pol").val() != "muski" && $("#pol").val() != "zenski") {
            if ($("#pol").val() != undefined) {
                $("#polError").text("Pol može biti MUSKI/ZENSKI").css('visibility', 'visible').css('color', 'red');
                uslov = true;
            }
        }
        // if (!regJmbg.test($("#jedBrOsig").val())) {
        //     $("#jedBrOsigError").text("Neispravan jedinstevni broj osiguranika").css('visibility', 'visible').css('color', 'red');
        //     uslov = true;
        // }
        if (!regPhone.test($("#telefon").val())) {
            if ($("#telefon").val() != undefined) {
                $("#telefonError").text("Neispravan broj telefona, broj mora da počne sa + i sme da sadrži 11 ili 12 cifara").css('visibility', 'visible').css('color', 'red');
                uslov = true;
            }
        }



        // if (!regEmail.test($("#email").val())) {
        //     $("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
        //     uslov = true;
        // }

        // if (!regJmbg.test($("#jmbg").val())) {
        //     $("#jmbgError").text("Neispravan JMBG").css('visibility', 'visible').css('color', 'red');
        // }

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
            return;
        }

        if (window.tipKorisnika == "sestra") {
            $.ajax({
                url: "/klinicki-centar/medicinskaSestra/update",
                type: 'put',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    email: $("#email").val(),
                    id: $("#id").val(),
                    lozinka: $("#password").val(),
                    jmbg: $("#jmbg").val()
                }),
                success: function(data) {
                    alert("USPESNO STE SAČUVALI IZMENE")
                }

            })

        } else if (window.tipKorisnika == "adminKC") {
            $.ajax({
                url: "/klinicki-centar/adminKC/update",
                type: 'put',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    email: $("#email").val(),
                    id: $("#id").val(),
                    lozinka: $("#password").val(),
                    jmbg: $("#jmbg").val()
                }),
                success: function(data) {
                    alert("USPESNO STE SAČUVALI IZMENE")
                }

            })


        } else if (window.tipKorisnika == "lekar") {
            $.ajax({
                url: "/klinicki-centar/lekar/update",
                type: 'put',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    email: $("#email").val(),
                    id: $("#id").val(),
                    lozinka: $("#password").val(),
                    jmbg: $("#jmbg").val()
                }),
                success: function(data) {
                    alert("USPESNO STE SAČUVALI IZMENE")
                }

            })

        } else if (window.tipKorisnika == "adminKlinike") {
            $.ajax({
                url: "/klinicki-centar/adminK/update",
                type: 'put',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    ime: $("#ime").val(),
                    prezime: $("#prezime").val(),
                    email: $("#email").val(),
                    id: $("#id").val(),
                    lozinka: $("#password").val(),
                    jmbg: $("#jmbg").val()
                }),
                success: function(data) {
                    alert("USPESNO STE SAČUVALI IZMENE")
                }

            })

        } else if (window.tipKorisnika == "pacijent") {

        }
    });

});

function dodajPolja(data) {
    let p1 = $("<p></p>");
    p1.attr('id', 'imeError');
    p1.css('visibility', 'hidden');
    let p2 = $("<p></p>");
    p2.attr('id', 'prezimeError');
    p2.css('visibility', 'hidden');
    // let p3 = $("<p></p>");
    // p3.attr('id', 'jmbgError');
    // p3.css('visibility', 'hidden');
    // let p4 = $("<p></p>");
    // p4.attr('id', 'emailError');
    // p4.css('visibility', 'hidden');
    let p5 = $("<p></p>");
    p5.attr('id', 'lozinkaError');
    p5.css('visibility', 'hidden');
    let p6 = $("<p></p>");
    p6.attr('id', 'lozinka1Error');
    p6.css('visibility', 'hidden');

    let div = $("#podaci");
    let form = $("<form></form>");
    form.attr("id", 'forma')

    let divID = $("<div></div>");
    divID.attr("class", 'form-group');

    let IDlabel = $("<label></label>")
    IDlabel.append("ID");

    let IDInput = $("<input></input>");
    IDInput.attr("type", 'text');
    IDInput.attr("readonly", true);
    IDInput.attr("class", "form-control");
    IDInput.attr('value', data.id);
    IDInput.attr("id", 'id');

    divID.append(IDlabel);
    divID.append(IDInput);

    let imeDIV = $("<div></div>");
    imeDIV.attr("class", 'form-group');
    let Imelabel = $("<label></label>")
    Imelabel.append("Ime");
    let ImeInput = $("<input></input>");
    ImeInput.attr("type", 'text');
    ImeInput.attr("class", "form-control");
    ImeInput.attr('value', data.ime);
    ImeInput.attr("id", 'ime');

    imeDIV.append(Imelabel);
    imeDIV.append(ImeInput);
    imeDIV.append(p1);

    let prezimeDIV = $("<div></div>");
    prezimeDIV.attr("class", 'form-group');
    let prezimelabel = $("<label></label>")
    prezimelabel.append("Prezime");
    let prezimeInput = $("<input></input>");
    prezimeInput.attr("type", 'text');
    prezimeInput.attr("class", "form-control");
    prezimeInput.attr('value', data.prezime);
    prezimeInput.attr("id", 'prezime');

    prezimeDIV.append(prezimelabel);
    prezimeDIV.append(prezimeInput);
    prezimeDIV.append(p2);

    let jmbgDIV = $("<div></div>");
    jmbgDIV.attr("class", 'form-group');
    let jmbglabel = $("<label></label>")
    jmbglabel.append("JMBG");
    let jmbgInput = $("<input></input>");
    jmbgInput.attr("type", 'number');
    jmbgInput.attr("readonly", true);
    jmbgInput.attr("class", "form-control");
    jmbgInput.attr('value', data.jmbg);
    jmbgInput.attr("id", 'jmbg');

    jmbgDIV.append(jmbglabel);
    jmbgDIV.append(jmbgInput);
    // jmbgDIV.append(p3);

    let emailDIV = $("<div></div>");
    emailDIV.attr("class", 'form-group');
    let emaillabel = $("<label></label>")
    emaillabel.append("Email");
    let emaiInput = $("<input></input>");
    emaiInput.attr("type", 'text')
    emaiInput.attr("readonly", true);;
    emaiInput.attr("class", "form-control");
    emaiInput.attr('value', data.email);
    emaiInput.attr("id", 'email');

    emailDIV.append(emaillabel);
    emailDIV.append(emaiInput);
    // emailDIV.append(p4);

    let passDIv = $("<div></div>");
    passDIv.attr("class", 'form-group');
    let passLabel = $("<label></label>")
    passLabel.append("Lozinka");
    let passInput = $("<input></input>");
    passInput.attr("type", 'password');
    passInput.attr("class", "form-control");
    passInput.attr('value', data.lozinka);
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




    form.append(divID);
    form.append(imeDIV);
    form.append(prezimeDIV);
    form.append(jmbgDIV);
    form.append(emailDIV);
    form.append(passDIv);
    form.append(passDIv1);
    div.append(form);
}