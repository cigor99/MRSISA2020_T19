$(document).ready(function(){
    $.ajax({
        url:"/klinicki-centar/KC/sifarnikDijagnoza/getAll",
        type: "GET",
        success:function(data){
            for(let dijagnoza of data){
                let table = $("#dijagnoze");
                let tr = $("<tr id=\"tr" + dijagnoza.id + "\"></tr>");

                let idTD = $("<td>" + dijagnoza.id + "</td>");
                let nazivTd = $("<td>" + dijagnoza.naziv + "</td>");
                let sifraTD = $("<td>" + dijagnoza.sifra + "</td>");
                let opisTD = $("<td>" + dijagnoza.opis + "</td>");
                let izmeniTD = $("<td>" + "<a href=\"updateDijagnoze.html?id=" + dijagnoza.id + "\">Izmeni</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(sifraTD);
                tr.append(opisTD);
                tr.append(izmeniTD);
                table.append(tr);
            }
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    });

    $("#dodaj").click(function(){
        window.location.replace("dodajDijagnozu.html");
    })


    $("#obrisi").click(function(){
        $.ajax({
            url: "/klinicki-centar/sifarnikDijagnoza/delete/"+ $("#IDBrisanja").val(),
            type: "DELETE",
            success:function(){
                $("#tr" + $("#IDBrisanja").val()).remove();
                $("#IDBrisanja").val("");
                alert("USPESNO BRISANJE DIJAGNOZE");
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    })
});