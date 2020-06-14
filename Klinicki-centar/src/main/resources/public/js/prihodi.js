function izracunaj(){
	var date1 = $("#date1").val();
	console.log(date1);
	var date2 = $("#date2").val();
	console.log(date2);
	if(date1 == "" || date2 == ""){
        $("#error").text("Unesite oba datuma.").css('visibility', 'visible').css('color', 'red');
	}
	else{
		$("#error").text("Unesite oba datuma.").css('visibility', 'hidden');
		$.ajax({
			type: "get",
	        url: "/klinicki-centar/klinika/prihodi/"+date1+"/"+date2,        
	        success: function(data) {
	        	console.log(data);
	        	document.getElementById("ukupno").value = data;
	        	$("#ukupno_div").css('visibility', 'visible');
	        }
		});
	}
	
	
}