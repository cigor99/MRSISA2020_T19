function ucitajListe(){
	$.ajax({
        type: "get",
        url: "/klinicki-centar/sala/page",
        success: function(data) {            
            for (var sala of data) {
            	console.log(sala.naziv);
            	$("#salaSelect").append($('<option>').val(sala.id).text(sala.naziv));                
            }
        }
    });
	
	$.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/page",
        success: function(data) {            
            for (var tip of data) {
            	console.log(tip.naziv);
            	$("#tipPregledaSelect").append($('<option>').val(tip.id).text(tip.naziv));                
            }
        }
    });
	
	$.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
        success: function(data) {            
            for (var lekar of data) {
            	$("#lekarSelect").append($('<option>').val(lekar.id).text(lekar.ime + " " + lekar.prezime));                
            }
        }
    });
}

function dodajPregled(){
	//var salaSelect = document.getElementById("salaSelect");
	//var sala = salaSelect.options[salaSelect.selectedIndex].text();
	var sala = $('option:selected', $("#salaSelect"));
	
	//var tipSelect = document.getElementById("tipPregledaSelect");
	var tipPregleda = $('option:selected', $("#tipPregledaSelect"));
	
	//var lekarSelect = document.getElementById("lekarSelect");
	var lekar = $('option:selected', $("#lekarSelect"));
	
	var datum = $("#date-time").val();
	
	//var cena = $("#cena").val();
	
	console.log(datum);
	console.log(sala.text());
	console.log(tipPregleda.text());
	console.log(lekar.text());
	//console.log(cena);
	
	if(provera()){
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/klinicki-centar/pregled/add",
	        dataType: 'json',
	        data: JSON.stringify({
	            datumivreme: datum,
	            sala: sala.val(),
	            tipPregleda: tipPregleda.val(),
	            lekar: lekar.val(),
	            //cena: cena
	        }),
	        success: function () {
	            window.location.replace("pregledi.html")
	        }
	    });
	}	

}

function provera(){
	$("#datumError").css('visibility', 'hidden');
    $("#salaError").css('visibility', 'hidden');
    $("#tipError").css('visibility', 'hidden');
    $("#lekarError").css('visibility', 'hidden');
    
    var sala = $('option:selected', $("#salaSelect"));	
	var tipPregleda = $('option:selected', $("#tipPregledaSelect"));	
	var lekar = $('option:selected', $("#lekarSelect"));	
	var datum = $("#date-time").val();
	
	var retVal = true;
	
	if (sala.val() == "") {
        $("#salaError").text("Sala je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    }
    else{
    	$("#salaError").css('visibility', 'hidden')
    }
	if (tipPregleda.val() == "") {
        $("#tipError").text("Tip pregleda je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    }
    else{
    	$("#tipError").css('visibility', 'hidden')
    }
	if (lekar.val() == "") {
        $("#lekarError").text("Lekar je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    }
    else{
    	$("#lekarError").css('visibility', 'hidden')
    }
	if (datum == "") {
        $("#datumError").text("Datum je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    }
    else{
    	$("#datumError").css('visibility', 'hidden')
    }
    
	return retVal;    
}