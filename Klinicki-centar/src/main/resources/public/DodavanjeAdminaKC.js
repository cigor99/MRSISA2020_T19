$(document).ready(function () {

    $("#dodaj").click(function () {
        $.ajax({
            url: "/klinicki-centar/adminKC/add",
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: 1,
                ime: $("#ime").val(),
                prezime: $("#prezime").val(),
                jmbg: $("#JMBG").val(),
                lozinka: $("#sifra").val(),
                email: $("#email").val()
            }),
            success: function () {
                // alert("Uspesno ste dodali novog admina");
                window.location.replace("adminiKC.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        })
    });
});