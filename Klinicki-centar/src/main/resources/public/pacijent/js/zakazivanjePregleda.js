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
    			window.lekar = data
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
    			window.klinika = data
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
		    		naslov.append('<h1>Zakazi pregled</h1>');
		    		
		        }
		 });
	}
	
});