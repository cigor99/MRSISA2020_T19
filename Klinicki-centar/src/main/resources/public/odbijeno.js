$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/pacijent/getOnePacijent/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#email").val(data.email);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }, async: false,
    })


    $("#posalji").click(function () {

        $.ajax({
            dataType: 'json',
            url: "/klinicki-centar/ZZR/sendEmail",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({
                email: $("#email").val(),
                message: $("#razlog").val(),
                subject: "Razglo odbijanja zahteva za registraciju na klinički centar"
            }),
            success: function () {
                alert("USPESNO STE POSLALI EMAIL")
                window.close()
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        })
    });


});