$(document).ready(function(){
	$("#imeError").css('visibility', 'hidden')
	$("#prezimeError").css('visibility', 'hidden')
	$("#jmbgError").css('visibility', 'hidden')
	$("#emailError").css('visibility', 'hidden')
	$("#lozinkaError").css('visibility', 'hidden')
	
	var regex = /^[a-zA-Z]{1,20}$/;
	
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
	
	if($("#email").val().length >20){
		$("#emailError").text("Email moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
		return;
	}
	
	if($("#lozinka").val().length >20){
		$("#lozinkaError").text("Lozinka moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
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
	
	if (!regex.test($("#ime").val())) {
        $("#imeError").text("Ime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
        return;
    }
	
	if (!regex.test($("#prezime").val())) {
        $("#prezimeError").text("Prezime moze da sadrzi samo mala ili velika slova").css('visibility', 'visible').css('color', 'red');
        return;
    }
	
	
	$("#potvrdi").click(function(){
		$.ajax({
			type : 'POST',
			url : "/klinicki-centar/pacijent/register",
			dataType : "json",
			contentType:"application/json",
			data : JSON.stringify({
				id: imeParam,
				ime : $("#ime").val(),
				prezime : $("#prezime").val(),
				jmbg: $("#jmbg").val(),
				email : $("#email").val(),
				lozinka : $("#lozinka").val()
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
}