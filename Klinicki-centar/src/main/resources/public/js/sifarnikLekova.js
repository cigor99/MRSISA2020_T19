$(document).ready(function() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/KC/sifarnikLekova/getAll",
        success: function(data) {

            // alert(JSON.stringify(data));
            for (let lek of data) {

                var table = $("#lekovi")
                let tr = $("<tr id=\"tr" + lek.id + "\"></tr>");

                let idTD = $("<td>" + lek.id + "</td>")
                let nazivTd = $("<td>" + lek.naziv + "</td>")
                let sifraTD = $("<td>" + lek.sifra + "</td>")
                let izmeniTD = $("<td>" + "<a href=\"updateLeka.html?id=" + lek.id + "\">Izmeni</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(sifraTD);
                tr.append(izmeniTD);
                table.append(tr);
            }

        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }
    });

    $("#dodaj").click(function() {
        window.location.replace("dodajLek.html");
    });

    $("#obrisi").click(function() {
        $.ajax({
            url: "/klinicki-centar/sifarnikLekova/deleteLek/" + $("#IDBrisanja").val(),
            type: "DELETE",
            success: function() {
                $("#tr" + $("#IDBrisanja").val()).remove();
                $("#IDBrisanja").val("");
                alert("USPESNO BRISANJE LEKA");
            },
            error: function(jqXHR) {
                if (jqXHR.status == 400) {
                    alert("Nije moguće brisanje izabranog leka, jer se on već nalazi u receptu")
                } else {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                }
            },
        })
    });
});