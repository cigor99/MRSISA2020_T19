$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        url: "/klinicki-centar/sifarnikLekova/getUpdate/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#ID").val(data.id);
            $("#naziv").val(data.naziv);
            $("#sifra").val(data.sifra)
        }, error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })

    $("#izmeniBtn").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/sifarnikLekova/Update",
            dataType: 'json',
            data: JSON.stringify({
                id: $("#ID").val(),
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val()
            }),
            success: function () {
                window.location.replace("sifarnikLekova.html")
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    })

});