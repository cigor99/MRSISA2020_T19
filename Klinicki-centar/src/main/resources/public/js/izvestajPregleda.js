$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];

    if (imeCoded.split("&")[3] != undefined) {
        let receptJednako = imeCoded.split("&")[3];
        let receptID = receptJednako.split("=")[1];
        $("#recepti").text("Dodali ste recept").css("visibility", 'visible').css('color', 'blue').css("font-style", 'italic').css("font-weight", 'bold');

    } else {
        $("#recepti").text("Nema nijedan recept").css("visibility", 'visible');
    }
    if (imeCoded.split("&")[1] != undefined) {
        var opisJednako = imeCoded.split("&")[1];
        $("#opis").text(opisJednako.split("=")[1])
    }

    $.ajax({
        url: "/klinicki-centar/sifarnikDijagnoza/getAll",
        type: "get",
        success: function(data) {
            let dijagnoze = []
            for (let dijagnoza of data) {
                if (jQuery.inArray(dijagnoza, dijagnoze) === -1) {
                    dijagnoze.push(dijagnoza);
                }
            }
            for (let dijagnoza of dijagnoze) {
                let input = $("#dijagnoze")
                let option = $("<option></option>")
                option.attr('value', dijagnoza.sifra)
                option.attr('id', dijagnoza.sifra)
                input.append(option);

            }
        },
        async: false
    })

    if (imeCoded.split("&")[2] != undefined) {
        var dijagnozaJednako = imeCoded.split("&")[2];
        let trazenaDijagnoza = dijagnozaJednako.split("=")[1];
        var x = document.getElementById("dijagnoze").options;
        // alert(x);
        for (let option of x) {
            if (option.value == trazenaDijagnoza) {
                $("#select").val(option.value);
            }
        }
    }

    $("#recept").click(function() {
        // window.open("recept.html?pID=" + IDpacijenta,
        //     'newwindow',
        //     'width=1200,height=650');
        window.location.replace("recept.html?pID=" + IDpacijenta + "&opis=" + $("#opis").val() + "&dgnz=" +
            $("#select").val());
    });


    $("#sacuvaj").click(function() {
        $("#dijagnozaError").css('visibility', 'hidden');
        $("#opisError").css('visibility', 'hidden');
        let uslov = false;
        if ($("#select").val() == "") {
            $("#dijagnozaError").text("Niste izabrali nijednu dijagnozu").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
        if ($("#opis").val() == "") {
            $("#opisError").text("Opis je obavezno polje").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
        if (uslov) {
            return;
        }
        let receptID;
        let receptJednako;
        let podaci
        if (imeCoded.split("&")[3] != undefined) {
            receptJednako = imeCoded.split("&")[3];
            receptID = receptJednako.split("=")[1];
            alert(receptID);
            podaci = JSON.stringify({
                id: 1,
                opis: $("#opis").val(),
                dijagnoza: $("#select").val(),
                lekar: window.ulogovani.id,
                recept: parseInt(receptID),
            })
        } else {
            podaci = JSON.stringify({
                id: 1,
                opis: $("#opis").val(),
                dijagnoza: $("#select").val(),
                lekar: window.ulogovani.id,
            })
        }
        // alert(window.ulogovani.id)

        $.ajax({
            url: '/klinicki-centar/izvestajPregleda/add',
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: podaci,

            success: function(data) {
                alert("Uspešno ste sačuvali izveštaj pregleda");
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })

    });
});