$(document).ready(function() {
    $("#posalji").click(function() {
        $("#pocetniERROR").css('visibility', 'hidden');
        var regex = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/;
        let uslov = false;
        if (!regex.test($("#pocetni").val())) {
            $("#pocetniERROR").text("Neispravan format. (31.01.2020.)").css("color", 'red').css('visibility', 'visible');
            uslov = true;
        };

        if (!regex.test($("#krajnji").val())) {
            $("#KrajnjiERROR").text("Neispravan format. (31.01.2020.)").css("color", 'red').css('visibility', 'visible');
            uslov = true;
        };
        if (new Date($("#krajnji").val()) < new Date($("#pocetni").val())) {
            $("#pocetniERROR").text("Početni datum mora biti pre ili isti kao i krajnji datum").css("color", 'red').css('visibility', 'visible');
            uslov = true;
        }

        if (uslov) {
            return;
        }
        let podaci;
        if (window.tipKorisnika == "lekar") {
            podaci = JSON.stringify({
                id: 1,
                krajnjiDatum: $("#krajnji").val(),
                pocetniDatum: $("#pocetni").val(),
                lekar: window.ulogovani.id,
                medicinskaSestra: 0
            })
        } else if (window.tipKorisnika == "sestra") {
            podaci = JSON.stringify({
                id: 1,
                krajnjiDatum: $("#krajnji").val(),
                pocetniDatum: $("#pocetni").val(),
                lekar: 0,
                medicinskaSestra: window.ulogovani.id
            })
        }


        $.ajax({
            url: "/klinicki-centar/ZZG/add",
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: podaci,
            success: function(data) {
                alert("Uspešno ste poslali zahtev");
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }
        })
    });
});