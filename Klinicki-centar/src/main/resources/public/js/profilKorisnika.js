$(document).ready(function() {

    if (tipKorisnika == "sestra") {
        $.ajax({
                url: "/klinicki-centar/medicinskaSestra/getOneMS/" + 1,
                type: 'GET',
                success: function(data) {
                    let p1 = $("<p></p>");
                    p1.attr('id', 'imeError');
                    p1.css('visibility', 'hidden');
                    let p2 = $("<p></p>");
                    p2.attr('id', 'prezimeError');
                    p2.css('visibility', 'hidden');
                    let p3 = $("<p></p>");
                    p3.attr('id', 'jmbgError');
                    p3.css('visibility', 'hidden');
                    let p4 = $("<p></p>");
                    p4.attr('id', 'emailError');
                    p4.css('visibility', 'hidden');
                    let p5 = $("<p></p>");
                    p5.attr('id', 'lozinkaError');
                    p5.css('visibility', 'hidden');
                    let p6 = $("<p></p>");
                    p6.attr('id', 'lozinka1Error');
                    p6.css('visibility', 'hidden');

                    let div = $("#podaci");
                    let form = $("<form></form>");

                    let divID = $("<div></div>");
                    divID.attr("class", 'form-group');

                    let IDlabel = $("<label></label>")
                    IDlabel.append("ID");

                    let IDInput = $("<input></input>");
                    IDInput.attr("type", 'text');
                    IDInput.attr("readonly", true);
                    IDInput.attr("class", "form-control");
                    IDInput.attr('value', data.id);

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
                    jmbgInput.attr("class", "form-control");
                    jmbgInput.attr('value', data.jmbg);
                    jmbgInput.attr("id", 'jmbg');

                    jmbgDIV.append(jmbglabel);
                    jmbgDIV.append(jmbgInput);
                    jmbgDIV.append(p3);

                    let emailDIV = $("<div></div>");
                    emailDIV.attr("class", 'form-group');
                    let emaillabel = $("<label></label>")
                    emaillabel.append("Email");
                    let emaiInput = $("<input></input>");
                    emaiInput.attr("type", 'text');
                    emaiInput.attr("class", "form-control");
                    emaiInput.attr('value', data.email);
                    emaiInput.attr("id", 'email');

                    emailDIV.append(emaillabel);
                    emailDIV.append(emaiInput);
                    emailDIV.append(p4);

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
            }


        })

    $("#sacuvaj").click(function() {
        $("#lozinkaError").css('visibility', 'hidden');
        $("#lozinka1Error").css('visibility', 'hidden');
        $("#imeError").css('visibility', 'hidden');
        $("#prezimeError").css('visibility', 'hidden');
        $("#emailError").css('visibility', 'hidden');
        $("#jmbgError").css('visibility', 'hidden');

        var regName = /^[A-Z]{1}[a-z]{1,20}$/;
        var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        var regJmbg = /^[0-9]{13}$/;
        var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        let uslov = false;

        if (!regName.test($("#ime").val())) {
            $("#imeError").text("Ime  mora da pocinje velikim slovom i sme da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regName.test($("#prezime").val())) {
            $("#prezimeError").text("Prezime  mora da pocinje velikim slovom i sme da sadrži samo slova").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regEmail.test($("#email").val())) {
            $("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }

        if (!regJmbg.test($("#jmbg").val())) {
            $("#jmbgError").text("Neispravan JMBG").css('visibility', 'visible').css('color', 'red');
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
            return;
        }

    });

});