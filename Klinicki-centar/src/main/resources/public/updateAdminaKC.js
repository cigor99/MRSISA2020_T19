$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        url: "/klinicki-centar/adminKC/getupdate/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#id").val(data.id);
            $("#ime").val(data.ime);
            $("#prezime").val(data.prezime);
            $("#email").val(data.email);
            $("#jmbg").val(data.jmbg);
            $("#lozinka").val(data.lozinka)
        }, error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })

    $("#sacuvaj").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/adminKC/update",
            dataType: 'json',
            data: JSON.stringify({
                id: $("#id").val(),
                ime: $("#ime").val(),
                prezime: $("#prezime").val(),
                email: $("#email").val(),
                jmbg: $("#jmbg").val(),
                lozinka: $("#lozinka").val()
            }),
            success: function () {
                window.location.replace("adminiKC.html")
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    })


});