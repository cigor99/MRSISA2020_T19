$(document).ready(function () {	
	var urlParametri = window.location.href.split("?")[1];
    var urlTok = urlParametri.split("&");
	var imeParam = urlTok[0].split("=")[1];
	window.lekarID = imeParam
	var klinikaParam = urlTok[1].split("=")[1];
    var datum = urlTok[2].split("=")[1]
    window.datum = datum;
    var tip = urlTok[3].split("=")[1]
	window.tipID = tip;
	console.log(window.tipID)
    console.log(window.lekarID)
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
					naslov.append('<h1>Zakazi pregled</h1>');
					
					popuni()
		    		
		        }
		 });
	}
	
});

function popuni(){
	var datumTokens = window.datum.split(";")
	var datString = datumTokens[2] + "/"+datumTokens[1]+"/"+datumTokens[0]
	var pathString = datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0]
	console.log(datString)
	$("#datum").val(datString)

	$.ajax({
		type: 'get',
		url: "/klinicki-centar/tipPregleda/all",
		success: function(data){
			for(tipPregleda of data){
				if (tipPregleda.id == window.tipID){
					var tipNaziv = tipPregleda.naziv
					var tipTrajanje = tipPregleda.trajanje
					var tipCena = tipPregleda.cena
					$("#tip").val(tipNaziv)
					$("#trajanje").val(tipTrajanje)
					$("#cena").val(tipCena)
				}
			}
		}
	});

	$.ajax({
		type: 'get',
		url: "/klinicki-centar/pregled/slobodniTermini/" + window.lekarID + "/" + pathString,
		success: function(data){
			var sel = $("#vreme");
			for(let item of data){
				sel.append("<option>"+item+"</option")
			}
		},error: function(jqXHR) {
			alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
		}
	});

	$("#potvrdi").click(function() {
		console.log(datumTokens[2])
		console.log(datumTokens[1])
		console.log(datumTokens[0])
		var date = new Date(datumTokens[0], datumTokens[1], datumTokens[2]);
		var vreme = $("#vreme").val()
		var tip = window.tipID;
		var parametri = JSON.stringify({
			datum:date,
			vreme:vreme,
			tipID:window.tipID,
			lekarID: window.lekarID,

		});
		console.log(parametri)
		$.ajax({
			type: 'post',
			url: "/klinicki-centar/pregled/zakaziSvoj",
			dataType: 'json',
			contentType : 'application/json',
			data: parametri,
			success : function (data) {
				alert("Uspesno poslat zahtev")
			},error: function(jqXHR) {
				alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
			}
		});
    });

}