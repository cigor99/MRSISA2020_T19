$(document).ready(function() {
    window.podaci;
    $('#idemo').checked = true;
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pacijent/page/" + 0 + "/" + 6,
        success: function(data) {
            window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible')
            kartice(data);
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },

    });

    $.ajax({
        url: "/klinicki-centar/pacijent/all",
        type: "get",
        success: function(data) {
            let gradovi = []
            for (let pacijent of data) {
                if (jQuery.inArray(pacijent.grad, gradovi) === -1) {
                    gradovi.push(pacijent.grad);
                }
            }
            for (let grad of gradovi) {
                let input = $("#gradovi")
                let option = $("<option></option>")
                option.attr('value', grad)
                option.attr('id', grad)
                    // $("#")
                input.append(option);


            }
        }
    })
    $('#idemo').change(function() {
        if (window.search == false && window.filter == false) { //
            $("#stranice").css('visibility', 'visible')
            if ($("#prviBr").hasClass("strong")) {
                dobavi(parseInt($("#prviBr").text()) - 1, 6)
            } else if ($("#drugiBr").hasClass("strong")) {
                dobavi(parseInt($("#drugiBr").text()) - 1, 6)
            } else if ($("#treciBr").hasClass("strong")) {
                dobavi(parseInt($("#treciBr").text()) - 1, 6)
            }
        }
        if (window.search == true) { //if (window.search == true) 

            $.ajax({
                url: "/klinicki-centar/pacijent/search/" + $("#kriterijum option:selected").text() + "/" + $("#search").val(),
                type: "post",
                success: function(data) {
                    $("#sledeci").css('visibility', 'hidden');
                    $("#poslednja").css('visibility', 'hidden');
                    $("#treciBr").css('visibility', 'hidden');
                    if (window.podaci == undefined) {
                        window.podaci = data;
                    } else {
                        let stari = window.podaci;
                        window.podaci = []
                        for (let novi of data) {
                            for (let star of stari) {
                                if (star.id == novi.id) {
                                    window.podaci.push(star);
                                }
                            }
                        }
                    }

                    window.search = true;
                    // window.filter = false;
                    $("#stranice").css('visibility', 'hidden');
                    $("#tabela").css('visibility', 'hidden');
                    $("#ROWDIV").empty();
                    $("#tabela").empty();
                    if ($("#idemo").is(":checked") == true) {
                        tabela(window.podaci);

                    } else {
                        kartice(window.podaci);
                    }
                }
            });
        }
        // if (window.filter == false) {
        //     $("#stranice").css('visibility', 'visible')
        //     if ($("#prviBr").hasClass("strong")) {
        //         dobavi(parseInt($("#prviBr").text()) - 1, 6)
        //     } else if ($("#drugiBr").hasClass("strong")) {
        //         dobavi(parseInt($("#drugiBr").text()) - 1, 6)
        //     } else if ($("#treciBr").hasClass("strong")) {
        //         dobavi(parseInt($("#treciBr").text()) - 1, 6)
        //     }
        // }
        if (window.filter == true) {
            $.ajax({
                url: "/klinicki-centar/pacijent/filter/" + $("#grad").val(),
                type: "post",
                success: function(data) {
                    $("#sledeci").css('visibility', 'hidden');
                    $("#poslednja").css('visibility', 'hidden');
                    $("#treciBr").css('visibility', 'hidden');
                    if (window.podaci == undefined) {
                        window.podaci = data;
                    } else {
                        let stari = window.podaci;
                        window.podaci = []
                        for (let novi of data) {
                            for (let star of stari) {
                                if (star.id == novi.id) {
                                    window.podaci.push(star);
                                }
                            }
                        }
                    }
                    // window.search = false;
                    window.filter = true;
                    $("#stranice").css('visibility', 'hidden');
                    $("#tabela").css('visibility', 'hidden');
                    $("#ROWDIV").empty();
                    $("#tabela").empty();
                    if ($("#idemo").is(":checked") == true) {
                        tabela(window.podaci);

                    } else {
                        kartice(window.podaci);
                    }
                }
            })
        }

    });

    $("#filtriraj").click(function() {
        $("#filterERROR").css('visibility', 'hidden')
        if ($("#grad").val() == "") {
            $("#filterERROR").text("Polje ne sme biti prazno!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        $.ajax({
            url: "/klinicki-centar/pacijent/filter/" + $("#grad").val(),
            type: "post",
            success: function(data) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
                $("#treciBr").css('visibility', 'hidden');
                if (window.podaci == undefined) {
                    window.podaci = data;
                } else {
                    let stari = window.podaci;
                    window.podaci = []
                    for (let novi of data) {
                        for (let star of stari) {
                            if (star.id == novi.id) {
                                window.podaci.push(star);
                            }
                        }
                    }
                }
                window.filter = true;
                // window.search = false;
                $("#stranice").css('visibility', 'hidden');
                $("#tabela").css('visibility', 'hidden');
                $("#ROWDIV").empty();
                $("#tabela").empty();
                if ($("#idemo").is(":checked") == true) {
                    tabela(window.podaci);

                } else {
                    kartice(window.podaci);
                }
            }
        })
    })

    function kartice(data) {
        if (!$('#tabela').is(':empty')) {
            $("#tabela").empty()
            $("#tabela").css("visibility", 'hidden')
        }
        if ($("#ROWDIV").is(':empty')) {
            $("#ROWDIV").css("visibility", 'visible')
            let counter = 0;

            for (let pacijent of data) {
                let row = $(".row");
                let col = $("<div></div>");
                col.attr("class", 'col-md-4');
                row.append(col);
                let well = $("<div></div>");
                well.attr("class", 'well');
                col.append(well);
                let img = $("<img></img>");
                img.attr("class", 'avatar')
                img.attr("src", 'pacijent.png');
                let h4 = $("<h4>Pacijent</h4>");
                let ime = $("<p></p>");
                ime.append("<strong>Ime: </strong>");
                ime.append(pacijent.ime);
                let prezime = $("<p></p>");
                prezime.append("<strong>Prezime: </strong>");
                prezime.append(pacijent.prezime);
                let broj = $("<p></p>");
                broj.append("<strong>Jedinstevni broj osiguranika: </strong>")
                broj.append(pacijent.jedinstveniBrOsig);

                let ul = $("<ul></ul>")
                ul.attr("class", 'bottom')
                let karton = $("<li></li>")
                karton.attr("class", 'del');
                let a = $("<a>Profil pacijenta</a>")
                a.attr("class", 'btn');
                a.attr("href", 'profilPacijenta.html?id=' + pacijent.id)
                    // let pregled = $("<li></li>")
                    // pregled.attr('class', 'del');
                    // let a2 = $("<a>Započni pregled</a>");
                    // a2.attr("class", 'btn');
                    // pregled.append(a2);
                    // ul.append(pregled);
                karton.append(a);
                ul.append(karton)

                well.append(img);
                well.append(h4);
                well.append(ime);
                well.append(prezime);
                well.append(broj);
                well.append(ul);
            }
            counter = counter + 1;
        }
    }

    function tabela(data) {
        if (!$('#ROWDIV').is(':empty')) {
            $("#ROWDIV").empty()
            $("#ROWDIV").css("visibility", 'hidden')
        }
        if ($('#tabela').is(':empty')) {
            var div = $("#tabela")
            div.css("visibility", 'visible')
            let table = $("<table></table>")
            table.attr("class", 'table')
            table.attr("id", "#pacijenti")
            let imeTd = $("<td>Ime</td>")
            let prezimeTd = $("<td>Prezime</td>")
            let brojTd = $("<td>Jedinstveni broj pacijenta</td>")
            let kartonTD = $("<td>Profil pacijenta</td>")
                // let pregledTD = $("<td>Započni pregled</td>")
            let head = $("<thead></thead>")
            let trHead = $("<tr></tr>")
            head.append(trHead)

            trHead.append(imeTd);
            trHead.append(prezimeTd);
            trHead.append(brojTd);
            trHead.append(kartonTD);
            // trHead.append(pregledTD);
            table.append(head);
            let tbody = $("<tbody></tbody>")
            table.append(tbody);
            div.append(table);
            for (let pacijent of data) {
                console.log(pacijent)

                let tr = $("<tr id=\"tr" + pacijent.id + "\"></tr>")

                // let idTD = $("<td>" + pacijent.id + "</td>")
                let imeTD = $("<td>" + pacijent.ime + "</td>")
                let prezimeTD = $("<td>" + pacijent.prezime + "</td>")
                    // let polTD = $("<td>" + pacijent.pol + "</td>")
                    // let emailTD = $("<td>" + pacijent.email + "</td>")
                    // let lozinkaTD = $("<td>" + pacijent.lozinka + "</td>")
                    // let telefonTD = $("<td>" + pacijent.brojTelefona + "</td>")
                    // let jmbgTD = $("<td>" + pacijent.jmbg + "</td>")
                let osigTD = $("<td>" + pacijent.jedinstveniBrOsig + "</td>")
                    // let adresaTD = $("<td>" + pacijent.adresa + "</td>")
                    // let gradTD = $("<td>" + pacijent.grad + "</td>")
                    // let drzavaTD = $("<td>" + pacijent.drzava + "</td>")

                // let TDkarton = $("<td>" + "<a href=\"pacijentProfil.html?id=" + pacijent.id + "\">Karton</a></td>")
                let TDkarton = $("<td></td>")
                let karton = $("<a>Profil pacijenta</a>")
                karton.attr("href", 'profilPacijenta.html?id=' + pacijent.id)
                TDkarton.append(karton);

                // tr.append(idTD)
                tr.append(imeTD)
                tr.append(prezimeTD)
                    // tr.append(polTD)
                    // tr.append(emailTD)
                    // tr.append(lozinkaTD)
                    // tr.append(telefonTD)
                    // tr.append(jmbgTD)
                tr.append(osigTD)
                    // tr.append(adresaTD)
                    // tr.append(gradTD)
                    // tr.append(drzavaTD)
                tr.append(TDkarton)
                tbody.append(tr)
            }
        }
    }

    $("#dodajBtn").click(function() {
        let uslov = validacija();

        // $("#imeError").css('visibility', 'hidden')
        // $("#prezimeError").css('visibility', 'hidden')
        // $("#jmbgError").css('visibility', 'hidden')
        // $("#emailError").css('visibility', 'hidden')
        // $("#lozinkaError").css('visibility', 'hidden')
        // $("#lozinka1Error").css('visibility', 'hidden')
        // $("#gradError").css('visibility', 'hidden')
        // $("#adresaError").css('visibility', 'hidden')
        // $("#drzavaError").css('visibility', 'hidden')
        // $("#telefonError").css('visibility', 'hidden')
        // $("#jedBrOsigError").css('visibility', 'hidden')

        // var regName = /^[A-Z]{1}[a-z]{1,20}$/;
        // var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        // var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        // var regPhone = /^[+]{1}[0-9]{1,12}$/;
        // //var regDrzava = /^[A-Z]{1}[a-z]+([ ][A-Z]{1}[a-z]+)*$/;
        // var regGrad = /^([A-Z]{1}[a-z]+[ ]*)+$/;
        // var regAdresa = /^([A-Z]{1}[a-z]+[ ]*)+[0-9]+$/;
        // var regJmbg = /^[0-9]{1,20}$/;

        // if ($("#ime").val().length > 20) {
        //     $("#imeError").text("Ime moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#prezime").val().length > 20) {
        //     $("#prezimeError").text("Prezime moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#jmbg").val().length > 20) {
        //     $("#jmbgError").text("JMBG moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#grad").val().length > 256) {
        //     $("#gradError").text("Grad moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#drzava").val().length > 256) {
        //     $("#drzavaError").text("Drzava moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#jedinstveniBrOsig").val().length > 20) {
        //     $("#jedBrOsigError").text("Broj osiguranika moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#adresa").val().length > 256) {
        //     $("#adresaError").text("Adresa moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#telefon").val().length > 20) {
        //     $("#telefonError").text("Broj telefona moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#email").val().length > 128) {
        //     $("#emailError").text("Email moze da sadrzi maksimalno 128 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka").val().length > 256) {
        //     $("#lozinkaError").text("Lozinka moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka_conf").val().length > 256) {
        //     $("#lozinka1Error").text("Lozinka moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#ime").val() == "") {
        //     $("#imeError").text("Ime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#prezime").val() == "") {
        //     $("#prezimeError").text("Prezime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#jmbg").val() == "") {
        //     $("#jmbgError").text("JMBG je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#email").val() == "") {
        //     $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka").val() == "") {
        //     $("#lozinkaError").text("Lozinka je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka_conf").val() == "") {
        //     $("#lozinka1Error").text("Morate potvrditi lozinku!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#grad").val() == "") {
        //     $("#gradError").text("Grad je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#drzava").val() == "") {
        //     $("#drzavaError").text("Drzava je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#jedinstveniBrOsig").val() == "") {
        //     $("#jedBrOsigError").text("Broj osiguranika je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#adresa").val() == "") {
        //     $("#adresaError").text("Adresa je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#telefon").val() == "") {
        //     $("#telefonError").text("Broj telefona je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regName.test($("#ime").val())) {
        //     $("#imeError").text("Ime  mora da pocinje velikim slovom i ne sme da sadrzi brojeve").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regName.test($("#prezime").val())) {
        //     $("#prezimeError").text("Prezime  mora da pocinje velikim slovom i ne sme da sadrzi brojeve").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regEmail.test($("#email").val())) {
        //     $("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regPhone.test($("#telefon").val())) {
        //     $("#telefonError").text("Neispravan unos broja telefona!").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regGrad.test($("#grad").val())) {
        //     $("#gradError").text("Neispravan unos grada").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regGrad.test($("#drzava").val())) {
        //     $("#drzavaError").text("Neispravan unos drzave").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regAdresa.test($("#adresa").val())) {
        //     $("#adresaError").text("Neispravan unos adrese").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regJmbg.test($("#jedinstveniBrOsig").val())) {
        //     $("#jedBrOsigError").text("Neispravan unos broja osiguranika").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regJmbg.test($("#jmbg").val())) {
        //     $("#jmbgError").text("Neispravan unos JMBG-a").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regPass.test($("#lozinka").val())) {
        //     $("#lozinkaError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if (!regPass.test($("#lozinka_conf").val())) {
        //     $("#lozinka1Error").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }

        // if ($("#lozinka").val() != $("#lozinka_conf").val()) {
        //     $("#lozinka1Error").text("Mora da se poklapa sa lozinkom").css('visibility', 'visible').css('color', 'red');
        //     return;
        // }
        if (uslov) {
            $.ajax({
                type: 'POST',
                url: "/klinicki-centar/pacijent/add",
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
                    alert("Uspešno ste dodati pacijenta")
                    window.location.replace("pacijenti.html")
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
                        alert("Error in call /pacijenti/add")
                    }

                }
            });
        }

    });




    $("#obrisiBtn").click(function() {
        $.ajax({
            type: "DELETE",

            url: "/klinicki-centar/pacijent/delete/" + $("#IDbrisanje").val(),
            success: function() {
                $("#tr" + $("#IDbrisanje").val()).remove();
                $("#IDbrisanje").val("");
                alert("USPESNO BRISANJE PACIJENTA");
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        });
    });

    //////////////////////////////////////////////////////////////////////
    //ZA ISPISIVANJE BROJA STRANICA
    //////////////////////////////////////////////////////////////////////
    let brojPacijenata;
    let brStr;
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pacijent/allAkcive",
        success: function(data) {
            $("#stranice").css('visibility', 'visible')
            let div = $("#stranice")
            $("#prviBr").attr("class", 'strong')
            brojPacijenata = data.length;
            brStr = parseInt(brojPacijenata / 6);
            if (brojPacijenata % 6 > 0 && brojPacijenata > 6) {
                brStr = brStr + 1;
            }
            if (brStr == 0) {
                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);

            }
            if (brStr == 2) {
                let drugiBr = $("<a>2</a>");
                drugiBr.attr("id", 'drugiBr');
                div.append(drugiBr);

                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);

            } else if (brStr >= 3) {
                let drugiBr = $("<a>2</a>");
                drugiBr.attr("id", 'drugiBr');
                div.append(drugiBr);
                let treciBr = $("<a>3</a>");
                treciBr.attr("id", 'treciBr');
                div.append(treciBr);
                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);
            }
        },
        error: function(response) {
            alert("Error when pacijent/page called")
        },
        async: false,

    });



    $("#sledeci").click(function() {
        $("#treciBr").css('visibility', 'visible');
        if ($("#prviBr").hasClass("strong") && brStr == 0) {
            return;
        }
        if ($("#prviBr").hasClass("strong")) {
            $("#prviBr").removeClass('strong');
            $("#drugiBr").attr('class', 'strong');
            $("#ROWDIV").empty();
            $("#tabela").empty()
            dobavi(parseInt($("#drugiBr").text()) - 1, 6)

            if (parseInt($("#drugiBr").text()) == brStr) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
            }
            return;
        }
        let counter = 0;
        for (let str of $('div#stranice').children()) {
            if (counter == 2) {
                if (parseInt(str.text) + 3 <= brStr) {
                    str.text = parseInt(str.text) + 1
                }
            } else if (counter == 3) {
                if (parseInt(str.text) + 2 <= brStr) {
                    str.text = parseInt(str.text) + 1
                    if ($("#drugiBr").hasClass("strong")) {
                        $("#ROWDIV").empty();
                        $("#tabela").empty()
                        dobavi(parseInt(str.text) - 1, 6);
                    }
                }
            } else if (counter == 4) {
                if (parseInt(str.text) + 1 <= brStr) {
                    str.text = parseInt(str.text) + 1
                } else {
                    $("#prviBr").text($("#drugiBr").text());
                    $("#drugiBr").removeClass('strong');
                    $("#drugiBr").text($("#treciBr").text());
                    $("#drugiBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty();
                    dobavi(parseInt($("#drugiBr").text()) - 1, 6)
                    $("#treciBr").css('visibility', 'hidden');
                    $("#sledeci").css('visibility', 'hidden');
                    $("#poslednja").css('visibility', 'hidden');
                }
            }

            counter = counter + 1;
        }
    });

    $("#prethodni").click(function() {
        if (brStr == 2) {
            if ($("#prviBr").hasClass("strong")) {
                return;
            } else if ($("#drugiBr").hasClass("strong")) {
                $("#drugiBr").removeClass('strong');
                $("#prviBr").attr('class', 'strong');
                $("#ROWDIV").empty();
                $("#tabela").empty()
                dobavi(parseInt($("#prviBr").text()) - 1, 6)
                $("#sledeci").css('visibility', 'visible');
                $("#poslednja").css('visibility', 'visible');
            }
        }
        if ($("#treciBr").css("visibility") == "hidden") {
            if (brStr >= 3) {
                $("#treciBr").text(parseInt($("#drugiBr").text()) + 1);
                $("#treciBr").attr('class', 'strong');
                // $("#drugiBr").text($("#prviBr").text());
                $("#drugiBr").removeClass('strong');
                // $("#prviBr").text(parseInt($("#prviBr").text()) - 1);

            }
            $("#treciBr").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#sledeci").css('visibility', 'visible');
        }

        let counter = 0;
        for (let str of $('div#stranice').children()) {
            // if (str.text != "0" && counter == 0) {
            if (counter == 2) {
                if (parseInt(str.text) - 1 > 0) {
                    str.text = parseInt(str.text) - 1
                } else if ($("#drugiBr").hasClass("strong")) {
                    $("#drugiBr").removeClass('strong');
                    $("#prviBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty()
                    dobavi(parseInt($("#prviBr").text()) - 1, 6)

                }
            } else if (counter == 3) {
                if (parseInt(str.text) - 1 > 1) {
                    str.text = parseInt(str.text) - 1
                    if ($("#drugiBr").hasClass("strong")) {
                        $("#ROWDIV").empty();
                        $("#tabela").empty()
                        dobavi(parseInt(str.text) - 1, 6);
                    }
                }
            } else if (counter == 4) {
                if (parseInt(str.text) - 1 > 2) {
                    str.text = parseInt(str.text) - 1
                }
                if ($("#treciBr").hasClass("strong")) {
                    $("#treciBr").removeClass('strong');
                    $("#drugiBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty()
                    dobavi(parseInt($("#drugiBr").text()) - 1, 6)
                }
            }
            // }

            counter = counter + 1;
        }
    });

    // $("#prva").click(function() {
    //     // for (let str of $('div#stranice').children()) {
    //     $("#prviBr").text("1");
    //     $("#prviBr").attr("class", 'strong');
    //     $("#drugiBr").text("2");
    //     $("#drugiBr").removeClass("strong")
    //     $("#treciBr").text("3");
    //     $("#treciBr").removeClass("strong")
    //     $("#sledeci").css('visibility', 'visible');
    //     $("#poslednja").css('visibility', 'visible');
    //     $("#treciBr").css('visibility', 'visible');

    // });

    $("#poslednja").click(function() {
        if ($("#prviBr").hasClass("strong") && brStr == 0) {
            return;
        }

        $("#prviBr").removeClass('strong');
        $("#treciBr").removeClass("strong");
        $("#prviBr").text(brStr - 1);
        $("#drugiBr").text(brStr);
        $("#drugiBr").attr("class", 'strong');
        $("#treciBr").css('visibility', 'hidden');
        $("#sledeci").css('visibility', 'hidden');
        $("#poslednja").css('visibility', 'hidden');
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#drugiBr").text()) - 1, 6)

    });


    //////////////////////////////////////////////////////////////////////
    function dobavi(od, dokle) {
        if ($("#idemo").is(":checked") == true) {
            $.ajax({
                type: "get",
                url: "/klinicki-centar/pacijent/page/" + od + "/" + dokle,
                success: function(data) {
                    tabela(data);
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                }
            });
            return;
        }
        $.ajax({
            type: "get",
            url: "/klinicki-centar/pacijent/page/" + od + "/" + dokle,
            success: function(data) {
                kartice(data);
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
            }
        });
        return;
    }

    //////////////////DUGMAD///////////////////////////////////////

    $("#prviBr").click(function() {
        $("#prviBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#prviBr").text()) - 1, 6)
        $("#sledeci").css('visibility', 'visible');
        $("#poslednja").css('visibility', 'visible');
        $("#treciBr").css('visibility', 'visible');
    });

    $("#drugiBr").click(function() {
        $("#drugiBr").attr('class', 'strong');
        $("#prviBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#drugiBr").text()) - 1, 6)
        if (brStr == 2) {
            $("#sledeci").css('visibility', 'hidden');
            $("#poslednja").css('visibility', 'hidden');
            $("#treciBr").css('visibility', 'hidden');
        } else if (brStr > 2) {
            $("#sledeci").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#treciBr").css('visibility', 'visible');
        }

    });

    $("#treciBr").click(function() {
        $("#treciBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#prviBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#treciBr").text()) - 1, 6)
        if (brStr == 3) {
            $("#sledeci").css('visibility', 'hidden');
            $("#poslednja").css('visibility', 'hidden');
            $("#treciBr").css('visibility', 'hidden');
        } else if (brStr > 3) {
            $("#sledeci").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#treciBr").css('visibility', 'visible');
        }
    });

    $("#prva").click(function() {
        $("#prviBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#prviBr").text()) - 1, 6)
        $("#sledeci").css('visibility', 'visible');
        $("#poslednja").css('visibility', 'visible');
        $("#treciBr").css('visibility', 'visible');
    });

    // $("#poslednja").click(function() {
    //     $("#prviBr").attr('class', 'strong');
    //     $("#drugiBr").removeClass("strong");
    //     $("#treciBr").removeClass("strong");
    //     $("#ROWDIV").empty();
    //     $("#tabela").empty()
    //     dobavi(brStr - 1, 6)
    // });

    function search() {
        $("#error").css('visibility', 'hidden')
        if ($("#search").val() == "") {
            $("#error").text("Polje pretrage ne sme biti prazno.").css("visibility", 'visible').css('color', 'red');
            return;
        }

        $.ajax({
            url: "/klinicki-centar/pacijent/search/" + $("#kriterijum option:selected").text() + "/" + $("#search").val(),
            type: "post",
            success: function(data) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
                $("#treciBr").css('visibility', 'hidden');
                if (window.podaci == undefined) {
                    window.podaci = data;
                } else {
                    let stari = window.podaci;
                    window.podaci = []
                    for (let novi of data) {
                        for (let star of stari) {
                            if (star.id == novi.id) {
                                window.podaci.push(star);
                            }
                        }
                    }
                }
                window.search = true;
                // window.filter = false;
                $("#stranice").css('visibility', 'hidden')
                $("#tabela").css('visibility', 'hidden');
                $("#ROWDIV").empty();
                $("#tabela").empty()

                if ($("#idemo").is(":checked") == true) {
                    tabela(window.podaci);

                } else {
                    kartice(window.podaci);
                }
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
            }

        })
    }
    $("#dugme").click(function() {
        search();
    });

    // $("#search").keyup(function(event) {
    //     if (event.keyCode == 13) {
    //         $("#dugme").click();
    //     }
    // });

});