$(document).ready(function() {
    var tokenCoded = window.location.href.split("?")[1];
    var tokenJednako = tokenCoded.split("&")[0];
    var token = tokenJednako.split("=")[1];

    $("#potvrdi").click(function() {
        $.ajax({
            url: "/klinicki-centar/pregled/potvrdiPregled/" + token,
            type: "get",
            success: function(data) {
                let rez = $("#rezultat")
                rez.empty();
                rez.append(data);
                var element=document.getElementById('odbij');

                if ( element.style.display!='none' ) {
                    element.style.display='none';
                }
    
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }
    
        })
    });

    $("#odbij").click(function() {
        $.ajax({
            url: "/klinicki-centar/pregled/odbijPregled/" + token,
            type: "get",
            success: function(data) {
                let rez = $("#rezultat")
                rez.empty();
                rez.append(data);
                
                var element=document.getElementById('potvrdi');

                if ( element.style.display!='none' ) {
                    element.style.display='none';
                }
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }
    
        })
    });


    $("#login").click(function() {
        window.location.replace("login.html");
    });
});