$(document).ready(function() {

    $("#dodaj").click(function() {
        let uslov = validacija();
        
        if (uslov) {
            $.ajax({
                url: "/klinicki-centar/adminKC/add",
                type: "POST",
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
                success: function() {
                    // alert("Uspesno ste dodali novog admina");
                    window.location.replace("adminiKC.html")
                },
                error: function(jqXHR) {
                    if (jqXHR.status == 406) {
                        $("#emailError").text("Email koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("Email koji ste uneli vec postoji")
                    } else if (jqXHR.status == 409) {
                        $("#jmbgError").text("JMBG koji ste uneli vec postoji").css('visibility', 'visible').css('color', 'red');
                        alert("JMBG koji ste uneli vec postoji")
                    } else {
                        alert("Error in call /adminKC/add")
                    }
                },
            })
        }
    });

});