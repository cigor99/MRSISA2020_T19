var ulogovan = null;
$(document).ready(function() {

    $("#submit").click(function() {
        $("#emailError").css('visibility', 'hidden')
        $("#lozinkaError").css('visibility', 'hidden')
        var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        var email = $('#email').val();
        var lozinka = $("#lozinka").val();

        if ($("#email").val() == "") {
            $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#lozinka").val() == "") {
            $("#lozinkaError").text("Lozinka je obavezno polje!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#email").val().length > 128) {
            $("#emailError").text("Email moze da sadrzi maksimalno 128 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if ($("#lozinka").val().length > 256) {
            $("#lozinkaError").text("Lozinka moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
            return;
        }

        if (!regEmail.test($("#email").val())) {
            $("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
            return;
        }


        $.ajax({
            type: 'post',
            url: '/klinicki-centar/login/prijava',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                email: $("#email").val(),
                lozinka: $("#lozinka").val()
            }),
            success: function(response) {
                alert("Uspesno ste se prijavili")
                ulogovan = response;
                console.log(ulogovan.ime);
                window.location.replace("/klinicki-centar");

            },
            error: function(jqXHR) {
                if (jqXHR.status == 400) {
                    alert("Pogresan unos email-a ili lozinke")
                }
            }
        });

    });


});


$.ajax({
type: 'post',
url: '/klinicki-centar/login/prijava',
dataType: 'json',
contentType: 'application/json',
data: JSON.stringify({
email: $("#email").val(),
lozinka: $("#lozinka").val()
}),
success: function(response) {
alert("Uspesno ste se prijavili")
ulogovan = response;
console.log(ulogovan.ime)
window.location.replace('index.html')
},
error: function(jqXHR) {
if (jqXHR.status == 400) {
    alert("Pogresan unos email-a ili lozinke")
}
}
});

});


});