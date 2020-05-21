$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];
    $.ajax({
        url: '/klinicki-centar/sifarnikLekova/getall',
        type: "get",
        success: function(data) {
            for (let lek of data) {
                let input = $("#lekovi")
                let option = $("<option></option>")
                option.attr('value', lek.id)
                option.attr('id', lek.sifra)
                input.append(option);
            }
        }

    })
    window.lekovi = []
    $("#dodaj").click(function() {
        $("#dodajError").css("visibility", 'hidden');
        if ($("#select").val() != "") {
            window.lekovi.push($("#select").val());
        } else {
            $("#dodajError").text("Niste izabrali nijedan lek!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        $.ajax({
            url: "/klinicki-centar/sifarnikLekova/getOne/" + $("#select").val(),
            type: "get",
            success: function(data) {
                $("#dodajError").css("visibility", 'hidden');
                if ($("#tr" + data.id).val() == undefined) {
                    let div = $("#tabela");
                    let tr = $("<tr></tr>");
                    tr.attr("id", "tr" + data.id);

                    let sifraTD = $("<td></td>");
                    sifraTD.append(data.sifra);
                    tr.append(sifraTD);

                    let nazivTD = $("<td></td>");
                    nazivTD.append(data.naziv);
                    tr.append(nazivTD);

                    let obrisiTD = $("<td></td>");
                    let a = $("<a>Ukloni lek</a>");
                    a.attr("id", "a" + data.id);
                    a.attr("href", '#');
                    a.attr("onClick", "obrisi(" + data.id + ")");

                    obrisiTD.append(a);
                    tr.append(obrisiTD);
                    div.append(tr);
                } else {
                    $("#dodajError").text("Lek je već dodat").css('visibility', 'visible').css('color', 'red');
                    return;
                }
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                window.lekovi.pop();
            }
        })

    });

    $("#sacuvaj").click(function() {
        $("#dodajError").css("visibility", 'hidden');
        if (window.lekovi.length == 0) {
            $("#dodajError").text("Niste dodali nijedan lek u recept").css('visibility', 'visible').css('color', 'red');
            return;
        }
        $.ajax({
            url: "/klinicki-centar/recepti/add",
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: "1",
                // datumIzdavanja: "01/01/2020",
                // stanjeRecepta: 'NIJE_OVEREN',
                // pregledID: 0,
                lekoviID: window.lekovi,
                lekarID: window.ulogovani.id
            }),
            success: function(data) {
                alert("Uspešno ste sačuvali recept!")
                window.location.replace("izvestajPregleda.html?" + window.location.href.split("?")[1] + "&rID=" + data.id);
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    });
});


function obrisi(id) {
    let temp = window.lekovi;
    window.lekovi = []
    for (let lek of temp) {
        alert(typeof(id))
        alert(typeof(lek))
        if (parseInt(lek) !== id) {
            window.lekovi.push(lek);
        }
    }
    $("#tr" + id).remove();
}