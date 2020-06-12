let listaID = [];
var operacija;
var klinikaID;
$(document).ready(function() {
    // alert("radi");
    var p = window.location.search.substr(1);
    var pp = p.split("=");


    $.ajax({
        url: '/klinicki-centar/Operacija/getOne/' + pp[1],
        type: 'get',
        success: function(data) {
            operacija = data;
            $.ajax({
                url: "/klinicki-centar/sala/getOne/" + operacija.salaID,
                type: "get",
                success: function(data) {
                    klinikaID = data.klinikaID;
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                },
                async: false
            })
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
        async: false
    })
    ucitaj();


    $("#dugme").click(function() {
        $("#error").css("visibility", 'hidden')
        var regName = /^([a-zA-Z]{3,30}\s*)+$/;
        var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if ($("#search").val() == "") {
            ucitaj();
            return;
        }
        if ($("#kriterijum option:selected").text() == "Email") {
            if (!regEmail.test($("#search").val())) {
                $("#error").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
                return;
            }
        }
        if ($("#kriterijum option:selected").text() == "Prezime") {
            if (!regName.test($("#search").val())) {
                $("#error").text("Prezime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
                return;
            }
        }
        if ($("#kriterijum option:selected").text() == "Ime") {
            if (!regName.test($("#search").val())) {
                $("#error").text("Ime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
                return;
            }
        }

        $.ajax({
            url: '/klinicki-centar/lekar/searchOperacija/' + $("#search").val(),
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: '1',
                datumivreme: operacija.datum + " " + operacija.vreme,
                sala: operacija.sala,
                trajanje: operacija.trajanje,
                pacijent: operacija.pacijent
            }),
            success: function(data) {

                let tbody = $("#table_body");
                tbody.empty();
                for (let lekar of data) {
                    if (lekar.klinikaID == klinikaID) {
                        let uslov = true;
                        let tr = $("<tr></tr>");

                        let idtd = $("<td></td>");
                        idtd.append(lekar.id);

                        let imeTD = $("<td></td>");
                        imeTD.append(lekar.ime);
                        let prezimeTD = $("<td></td>");
                        prezimeTD.append(lekar.prezime);
                        let emailTD = $("<td></td>");
                        emailTD.append(lekar.email);
                        let dodajTD;

                        for (let id of listaID) {
                            if (lekar.id == id) {
                                uslov = false;
                            }
                        }
                        if (uslov) {
                            dodajTD = $(`<td><button  type="button"  onclick="dodajLekara('${lekar.id}')">Dodaj</button></td>`)
                            dodajTD.attr('id', "btn" + lekar.id);
                        }
                        tr.append(idtd);
                        tr.append(imeTD);
                        tr.append(prezimeTD);
                        tr.append(emailTD);
                        tr.append(dodajTD);
                        tbody.append(tr);
                    }
                }
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },


        })

    });
});

function dodajLekara(id) {

    let uslov = true;
    for (let lekarID of listaID) {
        if (id == lekarID) {
            uslov = false;
        }
    }
    if (uslov) {
        listaID.push(id);
    }
    $("#btn" + id).remove();

    // alert("Uspesno");


    $.ajax({
        url: "/klinicki-centar/ZZO/AddLekar",
        type: 'post',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
            id: id, //ID LEKARA
            operacija: operacija.id,
            sala: operacija.salaID
        }),
        success: function(data) {
            alert("Upešno ste dodali lekara na operaciju");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })

}

function ucitaj() {
    $.ajax({
        url: "/klinicki-centar/lekar/allOperacija",
        type: 'post',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
            id: '1',
            datumivreme: operacija.datum + " " + operacija.vreme,
            sala: operacija.sala,
            trajanje: operacija.trajanje,
            pacijent: operacija.pacijent
        }),
        success: function(data) {
            let tbody = $("#table_body");
            tbody.empty();
            for (let lekar of data) {
                if (lekar.klinikaID == klinikaID) {
                    let uslov = true;
                    let tr = $("<tr></tr>");

                    let idtd = $("<td></td>");
                    idtd.append(lekar.id);

                    let imeTD = $("<td></td>");
                    imeTD.append(lekar.ime);
                    let prezimeTD = $("<td></td>");
                    prezimeTD.append(lekar.prezime);
                    let emailTD = $("<td></td>");
                    emailTD.append(lekar.email);
                    let dodajTD;

                    for (let id of listaID) {
                        if (lekar.id == id) {
                            uslov = false;
                        }
                    }
                    if (uslov) {
                        dodajTD = $(`<td><button  type="button"  onclick="dodajLekara('${lekar.id}')">Dodaj</button></td>`)
                        dodajTD.attr('id', "btn" + lekar.id);
                    }
                    tr.append(idtd);
                    tr.append(imeTD);
                    tr.append(prezimeTD);
                    tr.append(emailTD);
                    tr.append(dodajTD);
                    tbody.append(tr);
                }
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })
}