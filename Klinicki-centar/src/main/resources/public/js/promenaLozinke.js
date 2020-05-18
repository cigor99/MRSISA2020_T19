function provera(){
	$("#staraError").css('visibility', 'hidden');
	
	$.ajax({
		type: 'post',
        url: '/klinicki-centar/login/proveriStaruLozinku',
        contentType: 'application/json',
        data: $("#stara").val(),
        success: function(response) {
        	let obaveznoPolje = false;
            let uslov = false;
            let pogresnaStara = false;
        	if(!response){
        		$("#staraError").text("Pogresan unos lozinke").css('visibility', 'visible').css('color', 'red');
                pogresnaStara = true;
        	}       	
        }
	});
	
	$("#lozinkaError").css('visibility', 'hidden');
    $("#lozinka1Error").css('visibility', 'hidden');
    
    var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    
    if ($("#nova").val() == "") {
        $("#lozinkaError").text("Lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#ponovo").val() == "") {
        $("#lozinka1Error").text("Ponovljena lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    
    if (!regPass.test($("#nova").val())) {
        if ($("#password").val() != undefined) {
            $("#lozinkaError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }

    if ($("#ponovo").val() != $("#nova").val()) {
        if ($("#ponovo").val() != undefined) {
            $("#lozinkaError").text("Lozinke se ne poklapaju").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }
    
    if (obaveznoPolje || uslov || pogresnaStara) {
        return false;
    }

}


function promeniLozinku(){
	$.ajax({
		type: 'post',
        url: '/klinicki-centar/login/promeniLozinku',
        contentType: 'application/json',
        data: $("#nova").val(),
        success: function(response) {
        	
        }
	});

}