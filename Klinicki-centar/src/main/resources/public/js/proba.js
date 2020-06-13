$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var mesecJednako = imeCoded.split("&")[1];
    var dan = imeJednako.split("=")[1];
    var mesec = mesecJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/pregled/getDnevniPregled/" + dan + "/" + mesec,
        type: 'get',
        success: function(data) {
            let table = $("#rez")
            for (let pregled of data) {
                let tr = $("<tr></tr>");
                let datumTD = $("<td></td>");
                datumTD.append(pregled.datum);
                let vremeTD = $("<td></td>");
                vremeTD.append(pregled.vreme);
                let TrajanjeTD = $("<td></td>");
                TrajanjeTD.append(pregled.trajanje + " min");
                let pregledTD = $("<td></td>");
                let a = $("<a>Započni pregled</a>");
                a.attr("href", 'izvestajPregleda.html?pID=' + pregled.pacijent + "&prID=" + pregled.id);
                pregledTD.append(a);

                tr.append(datumTD);
                tr.append(vremeTD);
                tr.append(TrajanjeTD);

                // table.append(tr);

                let imeTD = $("<td></td>");
                imeTD.append(pregled.pacijent);

                let tipPregledaTd = $("<td></td>");
                tipPregledaTd.append(pregled.tipPregleda);

                tr.append(imeTD);
                tr.append(tipPregledaTd);
                tr.append(pregledTD);
                table.append(tr);

            }

            // alert(data);
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })

    $.ajax({
        url: '/klinicki-centar/getDnevneOperacije/' + dan + "/" + mesec,
        type: 'get',
        success: function(data) {
            let table = $("#operacije");
            for (let op of data) {
                let tr = $("<tr></tr>");

                let datumTD = $("<td></td>");
                datumTD.append(op.datum);
                let vremeTD = $("<td></td>");
                vremeTD.append(op.vreme);
                let TrajanjeTD = $("<td></td>");
                TrajanjeTD.append(op.trajanje + " min");

                let pacijentTD = $("<td></td>")
                tr.append(datumTD);
                tr.append(vremeTD);
                tr.append(TrajanjeTD);

                $.ajax({
                    url: '/klinicki-centar/pacijent/getOnePacijent/' + op.pacijent,
                    type: 'get',
                    success: function(data) {
                        pacijentTD.append(data.ime + " " + data.prezime);
                        tr.append(pacijentTD);
                    },
                    async: false,
                })



                table.append(tr);

            }

        }
    })
});