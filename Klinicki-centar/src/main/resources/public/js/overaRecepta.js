$(document).ready(function() {

    $.ajax({
        url: "/klinicki-centar/recepti/getNeoverene",
        type: "get",
        success: function(data) {
            let table = $("#recepti");
            let tbody = $("<tbody></tbody>")
            table.append(tbody);

            for (let recept of data) {
                let tr = $("<tr></tr>");

                let IDTD = $("<td>" + recept.id + "</td>")
                let stanjeTD = $("<td>" + recept.stanjeRecepta + "</td>")
                stanjeTD.attr("id", 'td' + recept.id)
                let datumTD = $("<td>" + recept.datumIzdavanja + "</td>")
                let lekarTD = $("<td>" + recept.lekarID + "</td>")
                let pregledTD = $("<td>" + recept.pregledID + "</td>")
                let overiTD = $("<td></td>");
                overiTD.attr('id', 'overiTD' + recept.id);
                let a = $("<a>Overi</a>")
                a.attr("onclick", "overi(" + recept.id + ")")
                a.attr("href", '#')
                overiTD.append(a);

                tr.append(IDTD);
                tr.append(stanjeTD);
                tr.append(datumTD);
                tr.append(lekarTD);
                tr.append(pregledTD);
                tr.append(overiTD);
                tbody.append(tr);
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }
    })


});

function overi(receptID) {
    let conf = confirm("Da li ste sigurni da želite da overite recept sa ID:" + receptID);
    if (conf == true) {
        $.ajax({
            url: "/klinicki-centar/recepti/overi/" + receptID + "/" + window.ulogovani.id,
            contentType: "application/json",
            dataType: 'json',
            type: "put",
            success: function() {
                $("#td" + receptID).html("OVEREN");
                $("#overiTD" + receptID).remove();
            }
        });
    } else {
        return;
    }

}