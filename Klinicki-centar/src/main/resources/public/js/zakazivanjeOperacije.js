function zakazi(){
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var IDpacijenta = imeJednako.split("=")[1];
    var pregled = imeCoded.split("&")[1];
    var pregledID = pregled.split("=")[1];
    var dt = $("#date-time").val();
    var trajanje = $("#trajanje").val();
    console.log(dt);
    console.log(pregledID);
    console.log(IDpacijenta);
    if(dt == ""){
        $("#datumError").text("Unesite datum.").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if ($("#trajanje").val() == "") {
        $("#trajanjeError").text("Trajanje je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if (isNaN($("#trajanje").val()) ){
    	$("#trajanjeError").text("Trajanje je celobrojna vrednost!").css('visibility', 'visible').css('color', 'red');
    	return;
    }
    $("#trajanjeError").css('visibility', 'hidden');
	$("#datumError").css('visibility', 'hidden');
	$.ajax({
        type: "post",
        url: "/klinicki-centar/Operacija/posaljiZahtev/" + dt +"/" + trajanje + "/" + pregledID,
        success: function(data) {
            alert("Poslat je zahtev za rezervaciju sale.");
            close();
        },
        error: function() {
            alert("Error");
        }

    });
    
    
}