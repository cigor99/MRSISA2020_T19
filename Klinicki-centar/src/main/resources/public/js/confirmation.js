$(document).ready(function() {
    var tokenCoded = window.location.href.split("?")[1];
    var tokenJednako = tokenCoded.split("&")[0];
    var token = tokenJednako.split("=")[1];
    $.ajax({
        url: "/klinicki-centar/ZZR/potvrda-registracije/" + token,
        type: "get",
        success: function(data) {
            let rez = $("#rezultat")
            rez.append(data);


        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }

    })


    $("#login").click(function() {
        window.location.replace("login.html");
    });
});