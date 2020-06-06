$(document).ready(function() {

    $.ajax({
        url: "/klinicki-centar/ZZG/page",
        type: "get",
        success: function(data) {
            for (let zahtev of data) {
                let table = $("#zahtevi");
                let tr = $("<tr id=\"tr" + zahtev.id + "\"></tr>");
                let id = $("<td>" + zahtev.id + "</td>");
                let stanje = $("<td id=\"td" + zahtev.id + "\">" + zahtev.stanje + "</td>");
                let email = $("<td>"+zahtev.email+"</td>");
                /*$.ajax({
                    url: "/klinicki-centar/pacijent/getOnePacijent/" + zahtev.pacijent,
                    type: 'get',
                    success: function(data) {
                        emailTD = $("<td >" + data.email + "</td>");
                    },
                    error: function(jqXHR) {
                        alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                    },
                    async: false,
                })*/
                let od = $("<td>"+zahtev.pocetniDatum+"</td>");
                let do_ = $("<td>"+zahtev.krajnjiDatum+"</td>");
                let prihvati = $("<td id=\"prihvatiTD" + zahtev.id + "\"></td>");
                let a = $("<a>Prihvati</a>");
                a.attr("onclick", "prihvati(" + zahtev.id + ");");
                a.attr("href", '#')
                    // a.click(prihvati(zahtev.id))
                    // a.attr('href',"prihvati(zahtev.id);");
                prihvati.append(a);

                let odbij = $("<td  id=\"odbij" + zahtev.id + "\"></td>")
                let a2 = $("<a>Odbij</a>");
                // alert(zahtev.pacijent)
                a2.attr("onclick", "odbij(" + zahtev.id + ");");
                a2.attr("href", '#')
                    // a2.attr("target", '_blank')
                odbij.append(a2)
                tr.append(id);
                tr.append(stanje);
                tr.append(email);
                tr.append(od);
                tr.append(do_);
                if (zahtev.stanje == "NA_CEKANJU") {
                    tr.append(prihvati);
                    tr.append(odbij);
                }
                table.append(tr);
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + JSON.parse(jqXHR.responseText).error);
        }
    });

});


function prihvati(IDZahteva) {
    let conf = confirm('Da li ste sigurni da želite da potrvdite zahtev za odsustvom br.' + IDZahteva + "?")
    if (conf == true) {
        $.ajax({
            url: "/klinicki-centar/ZZG/Prihvati/" + IDZahteva,
            contentType: "application/json",
            dataType: 'json',
            type: "put",
            success: function() {
                $("#td" + IDZahteva).html("PRIHVACEN")
                $("#odbij" + IDZahteva).remove()
                $("#prihvatiTD" + IDZahteva).remove()
            }
        });
    } else {
        return;
    }

}


function odbij(IDZahteva) {
	var razlog = prompt("Razlog odbijanja zahteva: ", "");
	console.log(razlog);
    let conf = confirm('Da li ste sigurni da želite da odbijete zahtev za odsustvom br.' + IDZahteva + "?");
    if (conf == true) {
        $("#odbij" + IDZahteva).remove()
        $("#prihvatiTD" + IDZahteva).remove()
        $("#td" + IDZahteva).html("ODBIJEN")

        $.ajax({
            url: "/klinicki-centar/ZZG/Odbij/" + IDZahteva + "/" + razlog,
            contentType: "application/json",
            dataType: 'json',
            type: "put",
            success: function() {
                $("#td" + IDZahteva).html("ODBIJEN")
                $("#odbij" + IDZahteva).remove()
                $("#prihvatiTD" + IDZahteva).remove()
            },
            //async: false
        });

    } else {
        return;
    }



}
