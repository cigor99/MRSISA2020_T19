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
	
	$("#tipPregledaSelect").change(function(){
		var tipPregleda = $('option:selected', $("#tipPregledaSelect")).val();
		console.log("TIP PREGLEDA");
		$("#lekarSelect").empty();
		$.ajax({
	        type: "get",
	        url: "/klinicki-centar/tipPregleda/getUpdate/"+tipPregleda,
	        success: function(data) {    
	        	console.log(data);
	            $("#cena").val(data.cena);
	            $("#trajanje").val(data.trajanje);
	        }
	    });
		$.ajax({
	        type: "get",
	        url: "/klinicki-centar/tipPregleda/getLekari/"+tipPregleda,
	        success: function(data) {    
	        	for(var lekar of data){
	        		$("#lekarSelect").append($('<option>').val(lekar.id).text(lekar.ime + " " + lekar.prezime));
	        	}	        	
	        }
	    });
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
	console.log(tipPregleda.val());
	console.log(lekar.text());
	//console.log(cena);
	
	if(provera()){
		//var d = proveriDatum(sala.val(), datum, tipPregleda.val());
		//alert(d);
		$.ajax({
	        type: "get",
	        url: "/klinicki-centar/sala/proveriDatum/"+sala.val()+"/"+datum+"/"+tipPregleda.val(),
	        success: function(data) { 
	        	alert(data);
	        	if(data == "slobodna"){        		
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
	        	else {
	        		//alert("NE MOZE");
	    			$("#datumError").text("Sala je zauzeta!").css('visibility', 'visible').css('color', 'red');
	        	}        	
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

function proveriDatum(salaId, datum, tipPregledaId){
	console.log(tipPregledaId);
	var retVal = false;
	$.ajax({
        type: "get",
        url: "/klinicki-centar/sala/proveriDatum/"+salaId+"/"+datum+"/"+tipPregledaId,
        success: function(data) { 
        	console.log(data);
        	alert(data);
        	if(data == "slobodna"){        		
        		retVal = true;
        		return retVal;
        	}
        	else if(data == "zauzeta"){
        		retVal = false;
        		return retVal;
        	}        	
        },
        error: function() {
        	alert("Error");
        	console.log("error");
        	retVal = false;
        	return retVal;
        }
		
    });
	
	
}

function pretraziSale() {
  var myWindow = window.open("http://localhost:8080/klinicki-centar/izborSaleZaPregled.html", "", "toolbar=yes,scrollbars=yes,resizable=yes,top=200,left=100,width=800,height=500");
}