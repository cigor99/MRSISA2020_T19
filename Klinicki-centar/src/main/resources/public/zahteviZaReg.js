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
                let prihvatiTD = $("<td></td>");
                let a = $("<a>Prihvati</a>");
                a.attr("onclick", "prihvati("+zahtev.id+");");
                // a.click(prihvati(zahtev.id))
                // a.attr('href',"prihvati(zahtev.id);");
                prihvatiTD.append(a);    
            
                let odbijTD = $("<td>" + "<a href=\"odbij.html?id=" + zahtev.id + "\">Odbij</a></td>")
                tr.append(idTD);
                tr.append(stanjeTd);
                tr.append(emailTD);

                tr.append(prihvatiTD);
                tr.append(odbijTD);
                table.append(tr);
            }
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    });
    


});
function prihvati(IDZahteva){
    alert("RADI")
}