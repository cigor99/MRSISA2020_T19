var myWindow = null;

function ucitajTabelu() {
    //var selected = document.getElementById("terminiSelect");
    //filter = selected.options[selected.selectedIndex].value;
    console.log(window.tipKorisnika);
    if (window.tipKorisnika == "adminKlinike") {       
        $("#dodajPregled").css('visibility', 'visible');
        //$("#zakaziPregled").css('visibility', 'hidden');
    } else if (window.tipKorisnika == "lekar") {
    	$("#dodajPregled").css('visibility', 'hidden');
    	//$("#zakaziPregled").css('visibility', 'visible');   
    	document.getElementById("pacijent").innerHTML = "Pacijent";
    }

    $.ajax({
        type: "get",
        url: "/klinicki-centar/pregled/all",
        success: function(data) {
            console.log("success");
            var table = $("#pregledi")
            for (var pregled of data) {
                let tr = $("<tr id=\"tr" + pregled.id + "\"></tr>");
                let id = $("<td>" + pregled.id + "</td>");
                let datum = $("<td>" + pregled.datum + "</td>");
                let vreme = $("<td>" + pregled.vreme + "</td>");
                let sala = $("<td>" + pregled.sala + "</td>");
                let trajanje = $("<td>" + pregled.trajanje + "</td>");
                let lekar = $("<td>" + pregled.lekar + "</td>");
                let tip = $("<td>" + pregled.tipPregleda + "</td>");
                let cena = $("<td>" + pregled.cena + "</td>")
                tr.append(id);
                tr.append(datum);
                tr.append(vreme);
                tr.append(sala);
                tr.append(trajanje);
                tr.append(lekar);
                tr.append(tip);
                tr.append(cena);
                if (window.tipKorisnika == "adminKlinike") {
                    let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniPregled('${pregled.id}')">Ukloni</button></td>`)
                    tr.append(ukloni);                   
                } else if (window.tipKorisnika == "lekar") {               	
                    
                    let pacijent = $("<td>" + pregled.pacijent + "</td>");
                    tr.append(pacijent);
                    let zapocni = $("<td>" + "<a href=\"izvestajPregleda.html?id=" + pregled.pacijent + "&prID=" + pregled.id + "\">Zapocni pregled</a></td>")
                    tr.append(zapocni);
                }
                table.append(tr);
            }

        }
    });
}

function ukloniPregled(id) {

    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/pregled/delete/" + id,
        success: function() {
            $("#tr" + id).remove();
            alert("USPESNO BRISANJE PREGLEDA");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}



function proveriKorisnika() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/login/tipKorisnika",
        success: function(data) {
            console.log(data);
            tipKorisnika = data;
            //window.tipKorisnika = data;
            if (tipKorisnika == "lekar") {
                $("#dodajPregled").css('visibility', 'hidden');
            }

        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
        async: false,

    });
}

function ucitajSale() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/page",
        success: function(data) {
            var table = $("#sale")
            for (var sala of data) {
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>")
                let naziv = $("<td>" + sala.naziv + "</td>")
                let tip = $("<td>" + sala.tip + "</td>")
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);
                tr.append(izaberi);
                table.append(tr);
            }

        }
    });
}



function datumVreme() {
    var dt = $("#date-time").val();
    console.log(dt);
    var trajanje = $("#trajanje").val();
    console.log(trajanje);

    if (trajanje != "" && dt != "") {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/sala/filterTime",
            dataType: 'json',
            data: dt + ";" + trajanje,
            success: function(sale) {
                console.log(sale);
                $("#table_body").empty();
                var table = $("#sale")
                for (var sala of sale) {
                    let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                    let id = $("<td>" + sala.id + "</td>")
                    let naziv = $("<td>" + sala.naziv + "</td>")
                    let tip = $("<td>" + sala.tip + "</td>")
                    let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
                    tr.append(id);
                    tr.append(naziv);
                    tr.append(tip);
                    tr.append(izaberi);
                    table.append(tr);
                }

            },

        });
    }

}

function ucitajListe() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/page",
        success: function(data) {
            for (var sala of data) {
                console.log(sala.naziv);
                $("#salaSelect").append($('<option>').val(sala.id).text(sala.naziv));
            }
        }
    });

    $.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/page",
        success: function(data) {
            for (var tip of data) {
                console.log(tip.naziv);
                $("#tipPregledaSelect").append($('<option>').val(tip.id).text(tip.naziv));
            }
        }
    });

    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
        success: function(data) {
            for (var lekar of data) {
                $("#lekarSelect").append($('<option>').val(lekar.id).text(lekar.ime + " " + lekar.prezime));
            }
        }
    });

    $("#tipPregledaSelect").change(function() {
        var tipPregleda = $('option:selected', $("#tipPregledaSelect")).val();
        console.log("TIP PREGLEDA");
        $("#lekarSelect").empty();
        $.ajax({
            type: "get",
            url: "/klinicki-centar/tipPregleda/getUpdate/" + tipPregleda,
            success: function(data) {
                console.log(data);
                $("#cena").val(data.cena);
                $("#trajanje").val(data.trajanje);
            }
        });
        $.ajax({
            type: "get",
            url: "/klinicki-centar/tipPregleda/getLekari/" + tipPregleda,
            success: function(data) {
                for (var lekar of data) {
                    $("#lekarSelect").append($('<option>').val(lekar.id).text(lekar.ime + " " + lekar.prezime));
                }
            }
        });
    });
}



function dodajPregled() {
    //var salaSelect = document.getElementById("salaSelect");
    //var sala = salaSelect.options[salaSelect.selectedIndex].text();
    var sala = $('option:selected', $("#salaSelect"));

    //var tipSelect = document.getElementById("tipPregledaSelect");
    var tipPregleda = $('option:selected', $("#tipPregledaSelect"));

    //var lekarSelect = document.getElementById("lekarSelect");
    var lekar = $('option:selected', $("#lekarSelect"));

    var datum = $("#date-time").val();

    //var cena = $("#cena").val();

    console.log(datum);
    console.log(sala.text());
    console.log(tipPregleda.val());
    console.log(lekar.text());
    //console.log(cena);

    if (provera()) {
        //var d = proveriDatum(sala.val(), datum, tipPregleda.val());
        //alert(d);
        $.ajax({
            type: "get",
            url: "/klinicki-centar/sala/proveriDatum/" + sala.val() + "/" + datum + "/" + tipPregleda.val(),
            success: function(data) {
                alert(data);
                if (data == "slobodna") {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/klinicki-centar/pregled/add",
                        dataType: 'json',
                        data: JSON.stringify({
                            datumivreme: datum,
                            sala: sala.val(),
                            tipPregleda: tipPregleda.val(),
                            lekar: lekar.val(),
                            //cena: cena
                        }),
                        success: function() {
                            window.location.replace("pregledi.html")
                        }
                    });
                } else {
                    //alert("NE MOZE");
                    $("#datumError").text("Sala je zauzeta!").css('visibility', 'visible').css('color', 'red');
                }
            }
        });

    }

}

function provera() {
    $("#datumError").css('visibility', 'hidden');
    $("#salaError").css('visibility', 'hidden');
    $("#tipError").css('visibility', 'hidden');
    $("#lekarError").css('visibility', 'hidden');

    var sala = $('option:selected', $("#salaSelect"));
    var tipPregleda = $('option:selected', $("#tipPregledaSelect"));
    var lekar = $('option:selected', $("#lekarSelect"));
    var datum = $("#date-time").val();

    var retVal = true;

    if (sala.val() == "") {
        $("#salaError").text("Sala je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#salaError").css('visibility', 'hidden')
    }
    if (tipPregleda.val() == "") {
        $("#tipError").text("Tip pregleda je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#tipError").css('visibility', 'hidden')
    }
    if (lekar.val() == "") {
        $("#lekarError").text("Lekar je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#lekarError").css('visibility', 'hidden')
    }
    if (datum == "") {
        $("#datumError").text("Datum je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#datumError").css('visibility', 'hidden')
    }


    return retVal;
}

function proveriFilter() {
    $("#datumError").css('visibility', 'hidden');
    $("#trajanjeError").css('visibility', 'hidden');

    var trajanje = $("#trajanje").val();
    var datum = $("#date-time").val();

    var retVal = true;


    if (trajanje.val() == "") {
        $("#trajanjeError").text("Unesite trajanje").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#trajanjeError").css('visibility', 'hidden')
    }
    if (datum == "") {
        $("#datumError").text("Unesite datum i vreme").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#datumError").css('visibility', 'hidden')
    }


    return retVal;
}

function proveriDatum(salaId, datum, tipPregledaId) {
    console.log(tipPregledaId);
    var retVal = false;
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/proveriDatum/" + salaId + "/" + datum + "/" + tipPregledaId,
        success: function(data) {
            console.log(data);
            alert(data);
            if (data == "slobodna") {
                retVal = true;
                return retVal;
            } else if (data == "zauzeta") {
                retVal = false;
                return retVal;
            }
        },
        error: function() {
            alert("Error");
            console.log("error");
            retVal = false;
            return retVal;
        }

    });


}

function pretraziSale() {
    //window.sale = "izbor";
    //myWindow = window.open("http://localhost:8080/klinicki-centar/izborSaleZaPregled.html", "", "toolbar=yes,scrollbars=yes,resizable=yes,top=200,left=100,width=800,height=500");
    myWindow = window.open("http://localhost:8080/klinicki-centar/sale.html?w=pregled", "", "toolbar=yes,scrollbars=yes,resizable=yes,top=200,left=100,width=800,height=500");
    //console.log(myWindow);
    console.log(myWindow.parent);
}

function izaberiSalu(idSale) {
    var dt = $("#date-time").val();
    //console.log(opener)
    close();
    var el = opener.document.getElementById("salaSelect");
    opener.console.log(el);

    var sala = $("#salaSelect option[value='" + idSale + "']", opener.document).prop('selected', true);
    $("#date-time", opener.document).val(dt);
    opener.console.log(sala);
}


function ucitajPacijenta(){
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];
    
    console.log(IDpacijenta);
    
    
}

function zakaziPregled(){
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];
    var pregled = imeCoded.split("&")[1];
    var pregledID = pregled.split("=")[1];
    var dt = $("#date-time").val();
    console.log(dt);
    console.log(pregledID);
    console.log(IDpacijenta);
    $.ajax({
        type: "post",
        url: "/klinicki-centar/pregled/posaljiZahtev/" + IDpacijenta + "/" + dt + "/" + pregledID,
        success: function(data) {
            alert("Poslat je zahtev za rezervaciju sale.");
            close();
        },
        error: function() {
            alert("Error");
        }

    });
}