$(document).ready(function(){
    $.ajax({
        url:"/klinicki-centar/adminKC/getAll",
        type: "GET",
        success:function(data){
            for(let admin of data){
                let table = $("#admini");
                let tr = $("<tr id=\"tr" + admin.id + "\"></tr>");

                let idTD = $("<td>" + admin.id + "</td>");
                let imeTd = $("<td>" + admin.ime + "</td>");
                let prezimeTD = $("<td>" + admin.prezime + "</td>");
                let emailTD = $("<td>" + admin.email + "</td>");
                let jmbgTD = $("<td>" + admin.jmbg + "</td>");
                let izmeniTD = $("<td>" + "<a href=\"updateAdminaKC.html?id=" + admin.id + "\">Izmeni</a></td>")
                tr.append(idTD);
                tr.append(imeTd);
                tr.append(prezimeTD);
                tr.append(emailTD);
                tr.append(jmbgTD);
                tr.append(izmeniTD);
                table.append(tr);
            }
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    });

    $("#dodaj").click(function(){
        window.location.replace("DodavanjeAdminaKC.html");
    })


    $("#obrisi").click(function(){
        $.ajax({
            url: "/klinicki-centar/adminKC/delete/"+ $("#IDBrisanja").val(),
            type: "DELETE",
            success:function(){
                $("#tr" + $("#IDBrisanja").val()).remove();
                $("#IDBrisanja").val("");
                alert("USPESNO BRISANJE ADMINA");
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    })
});