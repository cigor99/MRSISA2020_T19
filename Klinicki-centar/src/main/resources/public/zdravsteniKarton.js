$(document).ready(function () {
    $("#traziJMBG").click(function () {

        $.ajax({
            url: "/klinicki-centar/karton/get/" + $("#JBMG").val(),
            type: 'GET',
            success: function (data) {
                $("#tabela").remove();
                let div = $("#div")
                let table = $("<table></table>")

                let p1 = $("<p></p>")
                p1.css('visibility', 'hidden')
                p1.attr('id', 'tezinaERROR')
                let p1TD = $("<td></td>")
                p1TD.append(p1);

                let p2 = $("<p></p>")
                p2.css('visibility', 'hidden')
                p2.attr('id', 'visinaERROR')
                let p2TD = $("<td></td>")
                p2TD.append(p2);

                let p3 = $("<p></p>")
                p3.css('visibility', 'hidden')
                p3.attr('id', 'krvERROR')
                let p3TD = $("<td></td>")
                p3TD.append(p3);

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
                tr2.append(p1TD)

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
                tr3.append(p2TD)

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
                tr4.append(p3TD)


                let tr5 = $("<tr></tr>")
                let dugmeTD = $("<td></td>")
                let dugme = $("<input></input>")
                dugme.attr("type", 'button')
                dugme.attr("id", 'izmeni')
                dugme.attr('value', 'Sacuvaj')
                dugme.attr('class', 'button')
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
        $("#visinaERROR").css('visibility', 'hidden');
        $("#tezinaERROR").css('visibility', 'hidden');
        $("#krvERROR").css('visibility', 'hidden');
        var regex = /^[0-9]{1,5}$/;

        if ($("#visina").val().length > 5) {
            $("#visinaERROR").text("Visina moze da sadrzi maksimalno 5 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#tezina").val().length > 5) {
            $("#tezinaERROR").text("Tezina moze da sadrzi maksimalno 5 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if (!regex.test($("#visina").val())) {
            $("#visinaERROR").text("Visina moze da sadrzi samo cifre!").css('visibility', 'visible').css('color', 'red');
            return;
        }
        if (!regex.test($("#tezina").val())) {
            $("#tezinaERROR").text("Tezina moze da sadrzi samo cifre!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#krv").val().toUpperCase() != "NULTAPOZITIVNA" && $("#krv").val().toUpperCase() != "NULTANEGATIVNA"
            && $("#krv").val().toUpperCase() != "APOZITIVNA" && $("#krv").val().toUpperCase() != "ANEGATIVNA"
            && $("#krv").val().toUpperCase() != "BPOZITIVNA" && $("#krv").val().toUpperCase() != "BNEGATIVNA"
            && $("#krv").val().toUpperCase() != "ABPOZITIVNA" && $("#krv").val().toUpperCase() != "ABNEGATIVNA") {
            $("#krvERROR").text("Neispravan unos!(ispravno NULTAPOZITIVNA) ").css('visibility', 'visible').css('color', 'red');
            return;
        }


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
                window.location.replace("index.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    })
});