$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        type: "get",

        url: "/klinicki-centar/klinika/getUpdate/" + imeParam,
        success: function (data) {
            $("#naziv").val(data.naziv);
            $("#opis").val(data.opis);
            $("#adresa").val(data.adresa);

        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })


    $('#izmeniBtn').click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/klinika/update",
            dataType: 'json',
            data: JSON.stringify({
                id: imeParam,
                naziv: $('#naziv').val(),
                adresa: $('#adresa').val(),
                opis: $("#opis").val()
            }),
            success: function () {
                window.location.replace("/klinicki-centar/profilKlinike.html?id="+imeParam);
            }
        })

    });
});