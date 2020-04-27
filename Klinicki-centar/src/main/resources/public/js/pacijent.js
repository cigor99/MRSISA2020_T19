$(document).ready(function(){
	$.ajax({
		type: "get",
		url: "/klinicki-centar/pacijent/page",
		success: function(data){
			for (let pacijent of data){
				var table = $("#pacijenti")
				let tr = $("<tr id=\"tr" + pacijent.id +"\"></tr>")
				
				let idTD = $("<td>" + pacijent.id + "</td>")
				let imeTD = $("<td>" + pacijent.ime + "</td>")
				let prezimeTD = $("<td>" + pacijent.prezime + "</td>")
				let jmbgTD = $("<td>" + pacijent.jmbg + "</td>")
				let emailTD = $("<td>" + pacijent.email + "</td>")
				let lozinkaTD = $("<td>" + pacijent.lozinka + "</td>")
				tr.append(idTD)
				tr.append(imeTD)
				tr.append(prezimeTD)
				tr.append(jmbgTD)
				tr.append(emailTD)
				tr.append(lozinkaTD)
				table.append(tr)
			}
		},
		error: function(response){
			alert("Error when pacijent/page called")
		}
	});
	
	
	$("#dodajBtn").click(function(){
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
				lozinka: $("#lozinka").val()
			}),
			success: function(){
				window.location.replace("Pacijenti.html")
			},
			error: function(){
				alert("Error in call /pacijenti/add")
			}
		});
		
	});
	
	
	
	
	$("#obrisiBtn").click(function () {
        $.ajax({
            type: "DELETE",

            url: "/klinicki-centar/pacijent/delete/" + $("#IDbrisanje").val(),
            success: function () {
                $("#tr" + $("#IDbrisanje").val()).remove();
                $("#IDbrisanje").val("");
                alert("USPESNO BRISANJE PACIJENTA");
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },
        });
    });
	
	
});