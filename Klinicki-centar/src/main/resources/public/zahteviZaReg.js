$(document).ready(function () {

    $.ajax({
        url: "/klinicki-centar/KC/ZZR/getAll",
        type: "get",
        success: function (data) {
            for (let zahtev of data) {
                let table = $("#zahtevi");
                let tr = $("<tr id=\"tr" + zahtev.id + "\"></tr>");

                let idTD = $("<td>" + zahtev.id + "</td>");
                let stanjeTd = $("<td>" + zahtev.stanje + "</td>");
                let emailTD = $("<td>" + zahtev.email + "</td>");
                let prihvatiTD = $("<td>" + "<a onclick=\"prihvati(zahtev.id)\">Prihvati</a></td>")
                let odbijTD = $("<td>" + "<a href=\"odbij.html?id=" + zahtev.id + "\">Odbij</a></td>")
                tr.append(idTD);
                tr.append(stanjeTd);
                tr.append(emailTD);
                tr.append(opisTD);
                tr.append(prihvatiTD);
                tr.append(odbijTD);
                table.append(tr);
            }
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    });
    function prihvati(IDZahteva){

    }


});