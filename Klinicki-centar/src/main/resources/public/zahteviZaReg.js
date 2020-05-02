$(document).ready(function () {

    $.ajax({
        url: "/klinicki-centar/KC/ZZR/getAll",
        type: "get",
        success: function (data) {
            for (let zahtev of data) {
                let table = $("#zahtevi");
                let tr = $("<tr id=\"tr" + zahtev.id + "\"></tr>");

                let idTD = $("<td>" + zahtev.id + "</td>");
                let stanjeTd = $("<td id=\"td" + zahtev.id + "\">" + zahtev.stanje + "</td>");
                let emailTD;
                $.ajax({
                    url: "/klinicki-centar/pacijent/getOnePacijent/" + zahtev.pacijent,
                    type: 'get',
                    success: function (data) {
                        emailTD = $("<td >" + data.email + "</td>");
                    },
                    error: function (jqXHR) {
                        alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                    }, async: false,
                })

                let prihvatiTD = $("<td id=\"prihvatiTD" + zahtev.id + "\"></td>");
                let a = $("<a>Prihvati</a>");
                a.attr("onclick", "prihvati(" + zahtev.id + ");");
                a.attr("href", '#')
                // a.click(prihvati(zahtev.id))
                // a.attr('href',"prihvati(zahtev.id);");
                prihvatiTD.append(a);

                let odbijTD = $("<td  id=\"odbij" + zahtev.id + "\"></td>")
                let a2 = $("<a>Odbij</a>");
                alert(zahtev.pacijent)
                a2.attr("onclick", "odbij(" + zahtev.id + "," + zahtev.pacijent + ");");
                a2.attr("href", '#')
                // a2.attr("target", '_blank')
                odbijTD.append(a2)
                tr.append(idTD);
                tr.append(stanjeTd);
                tr.append(emailTD);
                if (zahtev.stanje == "NA_CEKANJU") {
                    tr.append(prihvatiTD);
                    tr.append(odbijTD);
                }
                table.append(tr);
            }
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    });




});
function prihvati(IDZahteva) {
    $.confirm({
        title: 'Zahtev za registraciju',
        content: 'Da li ste sigurni da želite da potrvdite zahtev za registraciju br.' + IDZahteva +"?",
        buttons: {
            potvrdi: function () {
                $.ajax({
                    url: "/klinicki-centar/KC/ZZR/Prihvati/" + IDZahteva,
                    contentType: "application/json",
                    dataType: 'json',
                    type: "put",
                    success: function () {
                        $("#td" + IDZahteva).html("PRIHVACEN")
            
                        // $("#prihvatiTD" + IDZahteva).remove()
                        $("#odbij" + IDZahteva).remove()
                        $("#prihvatiTD" + IDZahteva).remove()
                    }
                });
            },
            odustani: function () {
                return;
            },
        }
    });

   
    // $("#td" + IDZahteva).html("PRIHVACEN")

}


function odbij(IDZahteva, idPacijenta) {
    $.confirm({
        title: 'Zahtev za registraciju',
        content: 'Da li ste sigurni da želite da odbijete zahtev za registraciju br.' + IDZahteva +"?",
        buttons: {
            potvrdi: function () {
                $("#odbij" + IDZahteva).remove()
                $("#prihvatiTD" + IDZahteva).remove()
                $("#td" + IDZahteva).html("ODBIJEN")
                $.ajax({
                    url: "/klinicki-centar/KC/ZZR/Odbij/" + IDZahteva,
                    contentType: "application/json",
                    dataType: 'json',
                    type: "put",
                    success: function () {
                        $("#td" + IDZahteva).html("ODBIJEN")
                        $("#odbij" + IDZahteva).remove()
                        $("#prihvatiTD" + IDZahteva).remove()
                    }
                });
                $.ajax({
                    url: "klinicki-centar/pacijent/delete/" + idPacijenta,
                    type: "DELETE",
                })

                window.open("odbijeno.html?id=" + idPacijenta,
                    'newwindow',
                    'width=700,height=400');
            
               
                
            },
            odustani: function () {
                return;
            },
        }
    });

    
}

