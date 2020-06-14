$(document).ready(function () {
    var urlParametri = window.location.href.split("?")[1];
    var urlTok = urlParametri.split("&");
    var pregledID = urlTok[0].split("=")[1];
    window.pregledID = pregledID;
    $.ajax({
		url: "/klinicki-centar/login/tipKorisnika",
        type: "get",
        success: function(data) {
        	window.tipKorisnika = data
        	console.log(data)
        },
        error: function(data){
        	alert("error getTipKorisnika")
        },
        async: false
	});
	
	if(window.tipKorisnika != "pacijent"){
		alert("Ovo je samo za pacijenta")
		$.ajax({
	        type: "get",
	        url: "/klinicki-centar/login/logout",
	        success: function(data) {
	            window.location.replace("/klinicki-centar/login.html");
	        },
	        error: function(jqXHR) {
	            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
	        },
	    });
	}else{ 
	
		$.ajax({
		        url: "/klinicki-centar/login/getLoggedUser",
		        type: "get",
		        success: function(data) {
		            window.ulogovani = data;
		            var naslov = $("#naslov");
		        	naslov.empty();
                    naslov.append('<h1>Detalji Pregleda</h1>');
                    ucitajPodatke()
		        }
		 });
    }



    $("#oceni").click(function(){
        var ocenaLekar = $("#ocenaLekar").val()
        var ocenaKlinika = $("#ocenaKlinika").val()
        var idLekar = $("#idLekar").val()
        var idKlinika = $("#idKlinika").val()
        var parametri = JSON.stringify({
            ocenaLekar: ocenaLekar,
            ocenaKlinika: ocenaKlinika,
            idLekar:idLekar,
            idKlinika: idKlinika
        })
        $.ajax({
            type: "post",
            url: "/klinicki-centar/pacijent/ocenjivanjePregleda",
            contentType: "application/json",
            data: parametri,
            success : function (data) {
                alert("Uspesno ste uneli ocenu")
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
            }
        });
    });
    
    
});

function ucitajPodatke(){
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pacijent/getPregledByID/" + window.pregledID,
        success : function (pregled) {
            $("#ime").val(pregled.lekarIme)
            $("#idLekar").val(pregled.lekarId)
            $("#naziv").val(pregled.klinikaNaziv)
            $("#idKlinika").val(pregled.klinikaId)
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        }
    });
}