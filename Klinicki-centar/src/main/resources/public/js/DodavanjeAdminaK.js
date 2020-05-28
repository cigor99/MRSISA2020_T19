$(document).ready(function() {
    $("#dodaj").click(function() {
        validacija();


        $.ajax({
            url: '/klinicki-centar/adminK/add',
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: 1,
                ime: $("#ime").val(),
                prezime: $("#prezime").val(),
                jmbg: $("#jmbg").val(),
                lozinka: $("#password").val(),
                email: $("#email").val()
            }),
            success: function(data) {
                alert("Uspešno ste dodali admina klinike, poslat je email sa kodom za registracija na servis.")
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }
        })
    });
});