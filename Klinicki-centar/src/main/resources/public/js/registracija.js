$(document).ready(function(){
	$("#imeError").css('visibility', 'hidden')
	$("#prezimeError").css('visibility', 'hidden')
	$("#jmbgError").css('visibility', 'hidden')
	$("#emailError").css('visibility', 'hidden')
	$("#lozinkaError").css('visibility', 'hidden')
	$("#lozinkaConfError").css('visibility', 'hidden')
	
	var regex = /^[a-zA-Z]{1,20}$/;
	var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
	var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
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
	
	if (!regex.test($("#ime").val())) {
        $("#imeError").text("Ime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
        return;
    }
	
	if (!regex.test($("#prezime").val())) {
        $("#prezimeError").text("Prezime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
        return;
    }
	
	if(!regEmail.test($("#email").val())){
		$("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
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
		type: "POST",
		contentType: "application/json",
		url: "/klinicki-centar/pacijent/add",
		dataType: 'json',
		data: JSON.stringify({
			ime: $("#ime").val(),
			prezime: $("#prezime").val(),
			jmbg: $("#jmbg").val(),
			email: $("#email").val(),
			lozinka: $("#lozinka").val(),
			pol: $("#pol").val()
		}),
		success: function(){
			window.location.replace("pacijenti.html")
		},
		error: function(){
			alert("Error in call /pacijenti/add")
		}
	});
}