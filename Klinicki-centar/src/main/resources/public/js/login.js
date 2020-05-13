var ulogovan = null;
$(document).ready(function() {
	
	$("#submit").click(function(){
		var email = $('#email').val();
		var lozinka = $("#lozinka").val();
		
		$.ajax({
			type:'post',
			url:'/klinicki-centar/login/prijava',
			dataType : 'json',
			contentType: 'application/json',
			data : JSON.stringify({
				email: $("#email").val(),
				lozinka: $("#lozinka").val()
			}),
			success: function(response){
				alert("Uspesno registrovan")
				ulogovan = response;
				console.log(ulogovan.ime)
			},
			error: function(response){
				alert("Greska login")
			}
		});
		
	});
	
	
});