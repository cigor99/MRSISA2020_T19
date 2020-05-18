$(document).ready(function() {
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
	            console.log(data)
	            $("#ime").val(data.ime);
	            $("#prezime").val(data.prezime);
	            $("#jmbg").val(data.jmbg);
	            $("#email").val(data.email);
	            $("#lozinka").val(data.lozinka);
	            $("#pol").val(data.pol);
	            $("#grad").val(data.grad);
	            $("#adresa").val(data.adresa);
	            $("#drzava").val(data.drzava);
	            $("#telefon").val(data.brojTelefona);
	            $("#jedinstveniBrOsig").val(data.jedinstveniBrOsig)
	        },
	        error: function(data){
	        	alert("error getLoggedUser")
	        }
	    });
	}
	
	$("#potvrdi").click(function(){
		
		let uslov = validacija();
		if(uslov){
		
			$.ajax({
				type : 'PUT',
				url : "/klinicki-centar/pacijent/update",
				dataType : "json",
				contentType:"application/json",
				data : JSON.stringify({
					id: window.ulogovani.id,
					ime : $("#ime").val(),
					prezime : $("#prezime").val(),
					jmbg: $("#jmbg").val(),
					email : $("#email").val(),
					lozinka : $("#lozinka").val(),
					pol: $("#pol").val(),
					grad: $("#grad").val(),
					brojTelefona: $("#telefon").val(),
					drzava: $("#drzava").val(),
					adresa: $("#adresa").val(),
					jedinstveniBrOsig: $("#jedinstveniBrOsig").val()
				}),
	
				success : function(response) {
					alert("Uspesno promenjeno")
					//printPacijent(response);
				},
				error : function(response) {
					alert("Greska pri kliku potvrdi")
				}
			});
		}
	});

});


function printPacijent(pacijent) {
	var ime = $("#ime").val(pacijent.ime);
	var prezime = $("#prezime").val(pacijent.prezime);
	var jmbg = $("#jmbg").val(pacijent.jmbg);
	var pol = $("#pol").val(pacijent.pol);
	var email = $("#email").val(pacijent.email);
	var lozinka = $("#lozinka").val(pacijent.lozinka);

}