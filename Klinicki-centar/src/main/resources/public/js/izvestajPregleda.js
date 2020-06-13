$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];
    var pregledID;

    if (imeCoded.split("&")[4] != undefined) {
        let receptJednako = imeCoded.split("&")[4];
        if (receptJednako.split("=")[0] == "rID") {
            let receptID = receptJednako.split("=")[1];
            $("#recepti").text("Dodali ste recept").css("visibility", 'visible').css('color', 'blue').css("font-style", 'italic').css("font-weight", 'bold');
        }

    } else {
        $("#recepti").text("Nema nijedan recept").css("visibility", 'visible');
    }
    if (imeCoded.split("&")[1] != undefined) {
        var opisJednako = imeCoded.split("&")[1];
        if (opisJednako.split("=")[0] == "opis") {
            $("#opis").text(opisJednako.split("=")[1].split("%20").join(" "));
        } else {
            pregledID = opisJednako.split("=")[1];
            // alert(pregledID);
        }
    }

    if (imeCoded.split("&")[3] != undefined) {
        let pregled = imeCoded.split("&")[3];
        if (pregled.split("=")[0] == "prID") {
            pregledID = pregled.split("=")[1];
            // alert(pregledID);
        }
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
            $("#select").val() + "&prID=" + pregledID);

        // } else {
        //     window.location.replace("recept.html?pID=" + IDpacijenta + "&opis=" + $("#opis").val() + "&dgnz=" +
        //         $("#select").val() + "&prID=" + );

        // }
    });
    
    $("#pregled").click(function() {
        myWindow = window.open("http://localhost:8080/klinicki-centar/zakazivanjePregleda.html?pID=" + IDpacijenta + "&pregledID=" + pregledID, "", "toolbar=yes,scrollbars=yes,resizable=yes,top=200,left=100,width=800,height=500");

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
        if (pregledID == undefined) {
            let danas = new Date();
            // alert("OVDE")
            $.ajax({
                url: '/klinicki-centar/pregled/getDnevniPregled/' + danas.getDate() + '/' + parseInt(danas.getMonth() + 1),
                type: "get",
                success: function(data) {
                    for (let pregled of data) {
                        if (pregled.pacijentID == IDpacijenta) {
                            pregledID = pregled.id;
                        }
                    }
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
                },
                async: false,
            })
        }
        let receptID;
        let receptJednako;
        let podaci
        if (imeCoded.split("&")[3] != undefined) {
            let receptJednako = imeCoded.split("&")[4];
            let receptID = receptJednako.split("=")[1];
            // alert(receptID);

            podaci = JSON.stringify({
                id: 1,
                opis: $("#opis").val(),
                dijagnoza: $("#select").val(),
                lekar: window.ulogovani.id,
                recept: parseInt(receptID),
                pregled: pregledID,
            })
        } else {
            podaci = JSON.stringify({
                id: 1,
                opis: $("#opis").val(),
                dijagnoza: $("#select").val(),
                lekar: window.ulogovani.id,
                pregled: pregledID,
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