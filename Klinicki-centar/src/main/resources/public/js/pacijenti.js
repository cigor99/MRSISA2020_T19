$(document).ready(function() {

	pokretanje();
	
	$("#potvrdi").click(function(){
		$.ajax({
			type : 'POST',
			url : "pacijent/izmeniPacijenta",
			dataType : "json",
			contentType:"application/json",
			data : JSON.stringify({
				ime : $("#ime").val(),
				prezime : $("#prezime").val(),
				email : $("#email").val(),
				lozinka : $("#lozinka").val(),
			}),

			success : function(response) {
				alert("Uspesno promenjeno")
				printPacijent(response);
			},
			error : function(response) {
				alert("error")
			}
		});
	});

});

function pokretanje(){
	var ime = $("#ime").val();
	var prezime = $("#prezime").val();
	var email = $("#email").val();
	var lozinka = $("#lozinka").val();
	$.ajax({
		type : 'POST',
		url : "pacijent/dodaj",
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify({
			ime : "Privremeni",
			prezime : "Privremeni",
			email : "Privremeni",
			lozinka : "Privremeni",
		}),

		success : function(response) {
			console.log(response.ime)
		},
		error : function(response) {
			alert("error")
		}
	});
	
	$.ajax({
		type : 'POST',
		url : "pacijent/pronadjiPacijenta",
		dataType : "json",
		contentType:"text/plain",
		data: "Privremeni",
		success : function(response) {
			printPacijent(response);
		},
		error : function(response) {
			alert("error")
		}
	});
}


function printPacijent(pacijent) {
	var ime = $("#ime").val(pacijent.ime);
	var prezime = $("#prezime").val(pacijent.prezime);
	var email = $("#email").val(pacijent.email);
	var lozinka = $("#lozinka").val(pacijent.lozinka);

}