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

    } else if (window.tipKorisnika == "superAdmin") {
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
                p4.attr('id', 'adresaError');
                p4.css('visibility', 'hidden');

                let p5 = $("<p></p>");
                p5.attr('id', 'gradError');
                p5.css('visibility', 'hidden');

                let p6 = $("<p></p>");
                p6.attr('id', 'drzavaError');
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
    
    $("#promeniLozinku").click(function() {
    	window.location.replace("/klinicki-centar/promeniLozinku.html");
    });

    $("#sacuvaj").click(function() {
        let uslov = validacija();
        if (uslov) {
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
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
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
                        //lozinka: $("#password").val(),
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
                    }

                })


            } else if (window.tipKorisnika == "superAdmin") {
                $.ajax({
                    url: "/klinicki-centar/superadmin/update",
                    type: 'put',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify({
                        ime: $("#ime").val(),
                        prezime: $("#prezime").val(),
                        email: $("#email").val(),
                        id: $("#id").val(),
                        //lozinka: $("#password").val(),
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
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
                        //lozinka: $("#password").val(),
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
                    }

                })

            } else if (window.tipKorisnika == "adminKlinike") {
            	console.log(window.ulogovani.lozinka);
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
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
                    }

                })

            } else if (window.tipKorisnika == "pacijent") {

                $.ajax({
                    url: "/klinicki-centar/pacijent/update",
                    type: 'put',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify({
                        ime: $("#ime").val(),
                        prezime: $("#prezime").val(),
                        email: $("#email").val(),
                        id: $("#id").val(),
                        //lozinka: $("#password").val(),
                        lozinka: window.ulogovani.lozinka,
                        jmbg: $("#jmbg").val(),
                        grad: $("#grad").val(),
                        adresa: $("#adresa").val(),
                        drzava: $("#drzava").val(),
                        brojTelefona: $("#telefon").val(),
                        jedinstveniBrOsig: $("#jedBrOsig").val(),
                        pol: $("#pol").val()
                    }),
                    success: function(data) {
                        alert("USPESNO STE SAČUVALI IZMENE");
                        window.location.replace("/klinicki-centar/");
                    }

                })
            }
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
    //ImeInput.attr("readonly", true);

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
    emaiInput.attr("readonly", true);
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

    if (window.tipKorisnika == "superAdmin") {
        ImeInput.attr("readonly", true);
        prezimeInput.attr("readonly", true);
    }


    form.append(divID);
    form.append(imeDIV);
    form.append(prezimeDIV);
    form.append(jmbgDIV);
    form.append(emailDIV);
    //form.append(passDIv);
    //form.append(passDIv1);
    div.append(form);
}