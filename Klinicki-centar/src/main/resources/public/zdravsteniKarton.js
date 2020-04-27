$(document).ready(function () {
    $("#traziJMBG").click(function () {
        // alert($("#JBMG").val());


        $.ajax({
            url: "/klinicki-centar/karton/get/" + $("#JBMG").val(),
            type: 'GET',
            success: function (data) {
                $("#tabela").remove();
                let div = $("#div")
                let table = $("<table></table>")

                let tr1 = $("<tr></tr>")
                let idLabelTD = $("<td></td>")
                idLabelTD.append("ID Zdravstvenog kartona")
                let idTD = $("<td></td>")
                let inputID = $("<input ></input>");
                idTD.append(inputID)
                inputID.attr('readonly', true)
                inputID.attr("type", 'text');
                inputID.attr('id', 'id')
                inputID.attr("value", data.id)
                tr1.append(idLabelTD)
                tr1.append(idTD)

                let tr2 = $("<tr></tr>")
                let tezinaLabelTD = $("<td></td>")
                let tezinaTD = $("<td></td>")
                tezinaLabelTD.append("Tezina")
                let inputTezina = $("<input ></input>");
                inputTezina.attr("id", 'tezina')
                inputTezina.attr('type', 'text')
                inputTezina.attr('value', data.tezina)
                tr2.append(tezinaLabelTD);
                tr2.append(tezinaTD)
                tezinaTD.append(inputTezina)

                let tr3 = $("<tr></tr>")
                let visinaLabelTD = $("<td></td>")
                let visinaTD = $("<td></td>")
                let visinaInput = $("<input></input>")
                visinaLabelTD.append("Visina")
                visinaInput.attr("id", 'visina')
                visinaInput.attr("type", 'text')
                visinaInput.attr('value', data.visina)
                tr3.append(visinaLabelTD)
                tr3.append(visinaTD)
                visinaTD.append(visinaInput)

                let tr4 = $("<tr></tr>")
                let krvLabelTD = $("<td></td>")
                let krvTD = $("<td></td>")
                let krvInput = $("<input></input>")
                krvLabelTD.append("Krvna grupa")
                krvInput.attr("id", 'krv')
                krvInput.attr("type", 'text')
                krvInput.attr('value', data.krvnaGrupa)
                tr4.append(krvLabelTD)
                tr4.append(krvTD)
                krvTD.append(krvInput);


                let tr5 = $("<tr></tr>")
                let dugmeTD = $("<td></td>")
                let dugme = $("<input></input>")
                dugme.attr("type", 'button')
                dugme.attr("id", 'izmeni')
                dugme.attr('value', 'Sacuvaj')
                dugmeTD.append(dugme)
                tr5.append(dugmeTD)

                table.append(tr1)
                table.append(tr2)
                table.append(tr3)
                table.append(tr4)
                table.append(tr5)
                div.append(table)
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }, async: false,
        })
    });


    $("#div").on("click", "#izmeni", function () {
        alert("RADi")
        $.ajax({
            type: 'put',
            contentType: "application/json",
            url: "/klinicki-centar/karton/update",
            dataType: 'json',
            data: JSON.stringify({
                id: $("#id").val(),
                visina: $("#visina").val(),
                tezina: $("#tezina").val(),
                krvnaGrupa: $("#krv").val()
            }),
            success: function () {
                alert("USPESNO STE SACUVALI IZMENE")
                // window.location.replace("index.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    })
});