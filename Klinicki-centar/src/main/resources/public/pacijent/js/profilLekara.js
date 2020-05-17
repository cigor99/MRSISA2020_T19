var klinika = null;
var lekar = null;
$(document).ready(function () {	
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    var klinikaParam = imeCoded.split("&")[1].split("=")[1];
    if(imeParam == undefined){
    	alert("Morate prvo izabrati lekara")
    	window.location.replace("/klinicki-centar/pretragaLekara.html");
    }else if(klinikaParam == undefined){
    	alert("Morate prvo izabrati kliniku")
    	window.location.replace("/klinicki-centar/pretragaKlinika.html");
    }else{
    	$.ajax({
    		type: "get",
    		url: "/klinicki-centar/lekar/getOneLekar/" + imeParam,
    		success: function(data){
    			lekar = data
    		},
    		error: function(data){
    			alert("error in get lekar");
    		},
    		async:false
    	});
    	
    	$.ajax({
    		type: "get",
    		url: "/klinicki-centar/klinika/getUpdate/" + klinikaParam,
    		success: function(data){
    			klinika = data
    		},
    		error: function(data){
    			alert("error in get klinika");
    		},
    		async:false
    	})
    }
    
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
		    		naslov.append('<h1>'+window.ulogovani.ime+" " + window.ulogovani.prezime+'</h1>');
		    		naslov.append('<h1>Lekar</h1>');
		    		printLekar()
		        }
		 });
	}
	
});

function printLekar(){
	if(lekar == null){
		return;
	}
	var imeDiv = $("#ime");
	imeDiv.append("<label>"+lekar.ime+"</label>");
    var prezimeDiv = $("#prezime");
    prezimeDiv.append("<label>"+lekar.prezime+"</label>");
    var emailDiv = $("#email");
    emailDiv.append("<label>"+lekar.email+"</label>");
    var ocenaDiv = $("#ocena");
    ocenaDiv.append("<label>"+lekar.prosecnaOcena+"</label>");
    var nazad = $("#nazad");
    nazad.attr('href', 'pretragaLekara.html?id='+klinika.id);
	
}