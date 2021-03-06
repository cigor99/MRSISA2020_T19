$(document).ready(function() {
    $("#filtriraj").click(function() {
        console.log("filter");
        $("#ROWDIV").empty();
        var selected = document.getElementById("osobljeSelect");
        var filter = selected.options[selected.selectedIndex].value;
        if (filter == "lekar") {
            $.ajax({
                type: "get",
                url: "/klinicki-centar/lekar/page",
                success: function(data) {
                    window.search = false;
                    window.filter = false;
                    $("#stranice").css('visibility', 'visible');
                    kartice(data, "lekar");
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                },
            });
        } else if (filter == "sestra") {
            $.ajax({
                type: "get",
                url: "/klinicki-centar/medicinskaSestra/page",
                success: function(data) {
                    window.search = false;
                    window.filter = false;
                    $("#stranice").css('visibility', 'visible');
                    kartice(data, "sestra");
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                },
            });
        }
    });

    $("#dugme").click(function() {
        pretraga();
    });
});

function ucitajOsoblje() {
    window.podaci;
    $('#idemo').checked = true;

    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
        success: function(data) {
            window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible');
            kartice(data, "lekar");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
    $.ajax({
        type: "get",
        url: "/klinicki-centar/medicinskaSestra/page",
        success: function(data) {
            window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible');
            kartice(data, "sestra");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
}

function pretraga() {
    $("#error").css('visibility', 'hidden')
    if ($("#search").val() == "") {
        $("#error").text("Polje pretrage ne sme biti prazno.").css("visibility", 'visible').css('color', 'red');
        return;
    }
    console.log($("#search").val())
    $("#ROWDIV").empty();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/lekar/search",
        dataType: 'json',
        data: $("#search").val(),
        success: function(lekari) {
            kartice(lekari, "lekar");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/medicinskaSestra/search",
        dataType: 'json',
        data: $("#search").val(),
        success: function(sestre) {
            kartice(sestre, "sestra");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
}

function kartice(data, x) {

    $("#ROWDIV").css("visibility", 'visible')
    let counter = 0;

    for (var lekar of data) {
        let row = $(".row");
        let col = $("<div></div>");
        col.attr("class", 'col-md-4');
        row.append(col);
        let well = $("<div></div>");
        well.attr("class", 'well');
        well.attr("id", '${lekar.id}');
        col.append(well);
        let img = $("<img></img>");
        img.attr("class", 'avatar');
        if (x == "lekar") {
            img.attr("src", 'lekar.png');
        } else if (x == "sestra") {
            img.attr("src", 'medSestra.png');
        }

        if (x == "lekar") {
            var h4 = $("<h4>Lekar</h4>");
        } else {
            var h4 = $("<h4>Medicinska Sestra</h4>");
        }
        let ime = $("<p></p>");
        ime.append("<strong>Ime: </strong>");
        ime.append(lekar.ime);
        let prezime = $("<p></p>");
        prezime.append("<strong>Prezime: </strong>");
        prezime.append(lekar.prezime);
        let email = $("<p></p>");
        email.append("<strong>Email: </strong>");
        email.append(lekar.email);
        let ocena = $("<div></div>");
        ocena.attr("class", 'ocena');
        ocena.append(lekar.prosecnaOcena);
        let zvezda = $("<img></img>");
        zvezda.attr("src", 'zvezda.png');
        ocena.append(zvezda);
        //<img src="zvezda.png" alt="">

        let ul = $("<ul></ul>")
        ul.attr("class", 'bottom')
        let karton = $("<li></li>")
        karton.attr("class", 'del');
        let a = $("<a>Profil</a>")
        a.attr("class", 'btn');
        if (x == "lekar") {
            a.attr("href", 'profilLekara.html?id=' + lekar.id);
        } else if (x == "sestra") {
            a.attr("href", 'profilMedicinskeSestre.html?id=' + lekar.id)
        }

        // let pregled = $("<li></li>")
        // pregled.attr('class', 'del');
        // let a2 = $("<a>Započni pregled</a>");
        // a2.attr("class", 'btn');
        // pregled.append(a2);
        // ul.append(pregled);
        let del = $("<button>Obriši</button>"); //type="button" id="ukloniBtn" onclick="ukloniLekara('${lekar.id}')">Ukloni</button>")
        //del.attr("class", 'obrisi'); 
        del.attr("id", "ukloniBtn");
        if (x == "lekar") {
            del.attr("onclick", 'ukloniLekara(' + lekar.id + ')');
        } else {
            del.attr("onclick", 'ukloniSestru(' + lekar.id + ')');
        }

        karton.append(del);
        let karton2 = $("<li></li>")
        karton2.attr("class", 'fb');
        karton2.append(a);
        //karton.append(a);
        ul.append(karton2);
        ul.append(karton);
        //ul.append(a);
        /*<ul class="bottom">
                       <li class="fb"><a href="#" class="btn">Izmeni profil</a></li>
                        <li class="getPosts"><a href="#" class="btn">Izmeni kliniku</a></li>
                        <li class="del"><a href="#" class="btn">Obrisi</a></li>
                    </ul>*/

        well.append(img);
        well.append(h4);
        well.append(ime);
        well.append(prezime);
        well.append(email);
        well.append(ocena);
        well.append(ul);
    }
    counter = counter + 1;
}

function ukloniLekara(id) {

    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/lekar/delete/" + id,
        success: function() {
            $("#div" + id).remove();
            alert("USPESNO BRISANJE LEKARA");
            location.reload();
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function ukloniSestru(id) {

    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/medicinskaSestra/delete/" + id,
        success: function() {
            $("#div" + id).remove();
            alert("USPESNO BRISANJE MEDICINSKE SESTRE");
            location.reload();
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}

function ucitajLekara() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/lekar/getOneLekar/" + imeParam,
        type: 'get',
        success: function(lekar) {
            $("#ID").val(lekar.id)
            $("#ime").val(lekar.ime)
            $("#prezime").val(lekar.prezime)
            $("#jmbg").val(lekar.jmbg)
            $("#email").val(lekar.email)
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    $("#karton").click(function() {
        window.location.replace("zdravsteniKarton.html?id=" + $("#ID").val());
    });
}

function ucitajSestru() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/medicinskaSestra/getOneMS/" + imeParam,
        type: 'get',
        success: function(lekar) {
            $("#ID").val(lekar.id)
            $("#ime").val(lekar.ime)
            $("#prezime").val(lekar.prezime)
            $("#jmbg").val(lekar.jmbg)
            $("#email").val(lekar.email)
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    $("#karton").click(function() {
        window.location.replace("zdravsteniKarton.html?id=" + $("#ID").val());
    });
}

function dodajSestru() {
    let uslov = validacija();

    // if ($("#ime").val() == "") {
    //     $("#imeError").text("Ime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
    //     return;
    // } else {
    //     $("#imeError").css('visibility', 'hidden');
    // }
    // if ($("#prezime").val() == "") {
    //     $("#prezimeError").text("Prezime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
    //     return;
    // } else {
    //     $("#prezimeError").css('visibility', 'hidden');
    // }
    // if ($("#email").val() == "") {
    //     $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
    //     return;
    // } else {
    //     $("#emailError").css('visibility', 'hidden');
    // }
    // if ($("#jmbg").val() == "") {
    //     $("#jmbgError").text("JMBG je obavezno polje!").css('visibility', 'visible').css('color', 'red');
    //     return;
    // } else {
    //     $("#jmbgError").css('visibility', 'hidden');
    // }
    // if ($("#password").val() != $("#password1").val()) {
    //     $("#lozinkaError").text("Lozinke se ne poklapaju!").css('visibility', 'visible').css('color', 'red');
    //     return;
    // } else {
    //     $("#lozinkaError").css('visibility', 'hidden');
    // }
    console.log("dodavanje lekara");
    var email = $('#email').val()
    var lozinka = $('#password').val()
    var jmbg = $('#jmbg').val()
    var ime = $('#ime').val()
    var prezime = $('#prezime').val()
        //var klinika = document.getElementById("klinikaSelect");
        //var k = klinika.options[klinika.selectedIndex].value;
        //console.log(k);
    var data = JSON.stringify({
        email: $('#email').val(),
        lozinka: $('#password').val(),
        jmbg: $('#jmbg').val(),
        ime: $('#ime').val(),
        prezime: $('#prezime').val(),
        //klinika: k
    });
    if (uslov) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/medicinskaSestra/add",
            dataType: 'json',
            data: JSON.stringify({
                email: $('#email').val(),
                lozinka: $('#password').val(),
                jmbg: $('#jmbg').val(),
                ime: $('#ime').val(),
                prezime: $('#prezime').val(),
                //klinika: k
            }),
            success: function() {
                alert("Uspesno ste dodali medicinsku sestru.")
                window.location.replace("/klinicki-centar/medicinskoOsoblje.html");
            },
            error: function(jqXHR) {
                if (jqXHR.status == 406) {
                    $("#emailError").text("Email koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                    alert("Email koji ste uneli vec postoji")
                } else if (jqXHR.status == 409) {
                    $("#jmbgError").text("JMBG koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                    alert("JMBG koji ste uneli vec postoji")
                } else {
                    alert("Error in call /medicinskaSestra/add")
                }
            },

        });
    }
}


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

        /*$.ajax({
            url: "/klinicki-centar/lekar/search/" + $("#kriterijum option:selected").text() + "/" + $("#search").val(),
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
    }*/
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
            $("#ROWDIV").empty();
            var selected = document.getElementById("osobljeSelect");
            var filter = tipSelected.options[selected.selectedIndex].value;
            if (filter == "lekar") {
                $.ajax({
                    type: "get",
                    url: "/klinicki-centar/lekar/page",
                    success: function(data) {
                        window.search = false;
                        window.filter = false;
                        $("#stranice").css('visibility', 'visible');
                        kartice(data, "lekar");
                    },
                    error: function(jqXHR) {
                        alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                    },
                });
            } else if (filter == "sestra") {
                $.ajax({
                    type: "get",
                    url: "/klinicki-centar/medicinskaSestra/page",
                    success: function(data) {
                        window.search = false;
                        window.filter = false;
                        $("#stranice").css('visibility', 'visible');
                        kartice(data, "sestra");
                    },
                    error: function(jqXHR) {
                        alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                    },
                });
            }
            /*$.ajax({
                url: "/klinicki-centar/lekari/filter/" + filter,
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
                }*/
        }
    }
});