$(document).ready(function() {
	
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    
    $.ajax({
        type: "get",

        url: "/klinicki-centar/pacijent/getUpdate/" + imeParam,
        success: function (data) {
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
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })
	
	
	$("#potvrdi").click(function(){
		
		$("#imeError").css('visibility', 'hidden')
		$("#prezimeError").css('visibility', 'hidden')
		$("#jmbgError").css('visibility', 'hidden')
		$("#emailError").css('visibility', 'hidden')
		$("#lozinkaError").css('visibility', 'hidden')
		$("#lozinkaConfError").css('visibility', 'hidden')
		$("#gradError").css('visibility', 'hidden')
		$("#adresaError").css('visibility', 'hidden')
		$("#drzavaError").css('visibility', 'hidden')
		$("#telefonError").css('visibility', 'hidden')
		$("#jedinstveniBrOsigError").css('visibility', 'hidden')
		
		var regName = /^[A-Z]{1}[a-z]{1,20}$/;
		var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
		var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var regPhone = /^[+]{1}[0-9]{1,12}$/;
		//var regDrzava = /^[A-Z]{1}[a-z]+([ ][A-Z]{1}[a-z]+)*$/;
		var regGrad = /^([A-Z]{1}[a-z]+[ ]*)+$/;
		var regAdresa = /^([A-Z]{1}[a-z]+[ ]*)+[0-9]+$/;
		var regJmbg = /^[0-9]{1,20}$/;
		
		if($("#ime").val().length >20){
			$("#imeError").text("Ime moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#prezime").val().length >20){
			$("#prezimeError").text("Prezime moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#jmbg").val().length >20){
			$("#jmbgError").text("JMBG moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#grad").val().length >256){
			$("#gradError").text("Grad moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#drzava").val().length >256){
			$("#drzavaError").text("Drzava moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#jedinstveniBrOsig").val().length >20){
			$("#jedinstveniBrOsigError").text("Broj osiguranika moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#adresa").val().length >256){
			$("#adresaError").text("Adresa moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#telefon").val().length >20){
			$("#telefonError").text("Broj telefona moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#email").val().length >128){
			$("#emailError").text("Email moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#lozinka").val().length >256){
			$("#lozinkaError").text("Lozinka moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#lozinka_conf").val().length >256){
			$("#lozinkaConfError").text("Lozinka moze da sadrzi maksimalno 256 karaktera!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if ($("#ime").val() == "") {
	        $("#imeError").text("Ime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if ($("#prezime").val() == "") {
	        $("#prezimeError").text("Prezime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if ($("#jmbg").val() == "") {
	        $("#jmbgError").text("JMBG je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if ($("#email").val() == "") {
	        $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if ($("#lozinka").val() == "") {
	        $("#lozinkaError").text("Lozinka je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if ($("#lozinka_conf").val() == "") {
	        $("#lozinkaConfError").text("Morate potvrditi lozinku!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if($("#grad").val() == ""){
			$("#gradError").text("Grad je obavezno polje!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#drzava").val() == ""){
			$("#drzavaError").text("Drzava je obavezno polje!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#jedinstveniBrOsig").val() == ""){
			$("#jedinstveniBrOsigError").text("Broj osiguranika je obavezno polje!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#adresa").val() == ""){
			$("#adresaError").text("Adresa je obavezno polje!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#telefon").val() == ""){
			$("#telefonError").text("Broj telefona je obavezno polje!").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if (!regName.test($("#ime").val())) {
	        $("#imeError").text("Ime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if (!regName.test($("#prezime").val())) {
	        $("#prezimeError").text("Prezime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
		
		if(!regEmail.test($("#email").val())){
			$("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if(!regPhone.test($("#telefon").val())){
			$("#telefonError").text("Neispravan unos broja telefona!").css('visibility', 'visible').css('color', 'red');
			return;
		}
	
		if(!regGrad.test($("#grad").val())){
			$("#gradError").text("Neispravan unos grada").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if(!regGrad.test($("#drzava").val())){
			$("#drzavaError").text("Neispravan unos drzave").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if(!regAdresa.test($("#adresa").val())){
			$("#adresaError").text("Neispravan unos adrese").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if(!regJmbg.test($("#jedinstveniBrOsig").val())){
			$("#jedinstveniBrOsigError").text("Neispravan unos broja osiguranika").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if(!regJmbg.test($("#jmbg").val())){
			$("#jmbgError").text("Neispravan unos JMBG-a").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if (!regPass.test($("#lozinka").val())){
			$("#lozinkaError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if (!regPass.test($("#lozinka_conf").val())){
			$("#lozinkaConfError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		if($("#lozinka").val() != $("#lozinka_conf").val()){
			$("#lozinkaConfError").text("Mora da se poklapa sa lozinkom").css('visibility', 'visible').css('color', 'red');
			return;
		}
		
		$.ajax({
			type : 'PUT',
			url : "/klinicki-centar/pacijent/update",
			dataType : "json",
			contentType:"application/json",
			data : JSON.stringify({
				id: imeParam,
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
				printPacijent(response);
			},
			error : function(response) {
				alert("Greska pri kliku potvrdi")
			}
		});
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