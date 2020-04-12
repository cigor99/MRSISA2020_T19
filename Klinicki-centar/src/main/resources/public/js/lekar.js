function dodajLekara() {
    
        var email = $('#email').val()
        var lozinka = $('#lozinka').val()
        var ime = $("#ime").val()
        var prezime = $("#prezime").val()
        var data = JSON.stringify({
            email: $('#email').val(),
            lozinka: $('#lozinka').val(),
            ime: $("#ime").val(),
            prezime: $("#prezime").val() 
        });
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/dodajNovogLekara",
            dataType: 'json',
            data: JSON.stringify({
                email: $('#email').val(),
                lozinka: $('#lozinka').val(),
                ime: $("#ime").val(),
                prezime: $("#prezime").val()
            }),
            complete : function () {
    			alert("Uspesno ste dodali lekara.")
    			window.location.replace("/klinicki-centar");
    		}
            
        });
}