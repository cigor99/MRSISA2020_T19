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

        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    })
	
	
	$("#potvrdi").click(function(){
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

});


function printPacijent(pacijent) {
	var ime = $("#ime").val(pacijent.ime);
	var prezime = $("#prezime").val(pacijent.prezime);
	var jmbg = $("#jmbg").val(pacijent.jmbg);
	var email = $("#email").val(pacijent.email);
	var lozinka = $("#lozinka").val(pacijent.lozinka);

}