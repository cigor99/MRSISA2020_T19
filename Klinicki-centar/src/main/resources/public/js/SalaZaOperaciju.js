$(document).ready(function() {

    $.ajax({
        url: '/klinicki-centar/ZZO/getAll',
        type: 'get',
        success: function(data) {
            let tbody = $("#tbody")
            for (let zahtev of data) {
                let tr = $("<tr></tr>");

                let idTD = $("<td></td>");
                idTD.append(zahtev.id)
                let datumTD = $("<td></td>");
                let vremeTD = $("<td></td>")
                let trajanjeTD = $("<td></td>");
                let salaTD = $("<td></td>");
                let rezervisiTD = $("<td></td>");
                let a = $("<a>Rezerviši</a>");


                $.ajax({
                    url: '/klinicki-centar/Operacija/getOne/' + zahtev.operacija,
                    type: "get",
                    success: function(data) {
                        datumTD.append(data.datum);
                        trajanjeTD.append(data.trajanje);
                        salaTD.append(data.sala);
                        vremeTD.append(data.vreme);
                    },
                    error: function(jqXHR) {
                        alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                    },
                    async: false,
                })
                a.attr("href", 'sale.html?w=' + 'operacija' + '&dat=' + datumTD.html() + "&vr=" + vremeTD.html() + "&tr=" + trajanjeTD.html() + "&sa=" + salaTD.html() + "&op=" + zahtev.operacija + "&zz=" + zahtev.id);
                rezervisiTD.append(a);

                tr.append(idTD);
                tr.append(datumTD);
                tr.append(vremeTD);
                tr.append(trajanjeTD);
                tr.append(salaTD);
                tr.append(rezervisiTD);

                tbody.append(tr);
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    })
});