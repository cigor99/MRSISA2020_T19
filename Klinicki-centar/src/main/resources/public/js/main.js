function ucitajZaglavlje(){
	//kad se napravi login treba dodati ajax zahtev koji dobavlja tip korisnika
	var tipKorisnika = "adminKlinike";
	//var tipKorisnika = "";
	var header = "header.html";
	if(tipKorisnika == "adminKlinike"){
		header = "adminKlinikeHeader.html"
		$("#naslov").append("<h1>Klinika</h1>");
	}
	$.ajax({
	        url: header,
	        method: "GET",	    
	        complete: function(data){
	            $("#navbardiv").append(data.responseText);
	        }
	});
	
}