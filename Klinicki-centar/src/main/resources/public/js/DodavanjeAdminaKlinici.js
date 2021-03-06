$(document).ready(function() {
    $.ajax({
        url: "/klinicki-centar/klinika/all",
        type: "get",
        success: function(data) {
            let input = $("#klinike")
            window.klinike = data;
            for (let klinika of data) {
                let option = $("<option></option>")
                    // option.attr("value", klinika.id);
                option.attr('value', klinika.id + ", " + klinika.naziv + ", " + klinika.adresa)
                input.append(option);
            }
        }
    })


    $.ajax({
        url: "/klinicki-centar/adminK/getAll",
        type: "get",
        success: function(data) {
            let table = $("#tabela")
            let input = $("#admini")
            window.admini = data;
            for (let admin of data) {
                let option = $("<option></option>")
                let lab = admin.ime + " " + admin.prezime + ", " + admin.id
                option.attr('value', lab)
                input.append(option);

                let tr = $("<tr ></tr>")
                tr.attr("id", "td" + admin.id)
                let IDTD = $("<td></td>")
                IDTD.append(admin.id)
                let imeTD = $("<td></td>")
                imeTD.append(admin.ime)
                let prezimeTD = $("<td></td>")
                prezimeTD.append(admin.prezime)
                let emailTd = $("<td></td>")
                emailTd.append(admin.email)
                let jmbgTD = $("<td></td>")
                jmbgTD.append(admin.jmbg)
                let klinikaTd = $("<td> </td>")
                klinikaTd.attr("id", "td" + admin.id)
                klinikaTd.append(admin.klinikaID)

                tr.append(IDTD);
                tr.append(imeTD);
                tr.append(prezimeTD);
                tr.append(emailTd);
                tr.append(jmbgTD);
                tr.append(klinikaTd);

                table.append(tr);
            }
        }
    })


    $("#dodaj").click(function() {
        $("#adminERROR").css('visibility', 'hidden')
        $("#klinikaERROR").css('visibility', 'hidden')
        if ($("#inputAdmin").val() == "") {
            $("#adminERROR").text("Neispravan unos! Izaberite nešto od ponuđenih!").css("visibility", 'visible').css('color', 'red')
            return;
        }
        if ($("#inputKlinika").val() == "") {
            $("#klinikaERROR").text("Neispravan unos! Izaberite nešto od ponuđenih!").css("visibility", 'visible').css('color', 'red')
            return;
        }
        let uslovKlinike = false;
        let uslovAdmini = false;
        for (let klinika of window.klinike) {
            if ((klinika.id + ", " + klinika.naziv + ", " + klinika.adresa) == $("#inputKlinika").val()) {
                uslovKlinike = true;
            }
        }
        for (let admin of window.admini) {
            if ((admin.ime + " " + admin.prezime + ", " + admin.id) == $("#inputAdmin").val()) {
                uslovAdmini = true;
            }
        }
        if (!uslovAdmini) {
            $("#adminERROR").text("Neispravan unos! Izaberite nešto od ponuđenih!").css("visibility", 'visible').css('color', 'red')
            return;
        }
        if (!uslovKlinike) {
            $("#klinikaERROR").text("Neispravan unos! Izaberite nešto od ponuđenih!").css("visibility", 'visible').css('color', 'red')
            return;
        }

        let klinikaSTR = $("#inputKlinika").val();
        let reci = klinikaSTR.split(",")
        let klinikaID = reci[0];
        let adminSTR = $("#inputAdmin").val();
        let reci1 = adminSTR.split(",")
        let adminID = reci1[1];
        $.ajax({
            url: "/klinicki-centar/klinika/addAdmin",
            type: "put",
            data: JSON.stringify({
                adminID: adminID,
                klinikaID: klinikaID,
            }),
            dataType: 'json',
            contentType: "application/json",
            success: function() {
                alert("Uspesno!")
                window.location.reload();
                // let nesto = $(this).find("#td" + adminID)
                // nesto.append(klinikaID)
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + JSON.parse(jqXHR.responseText).error);
            }

        })

    });

});