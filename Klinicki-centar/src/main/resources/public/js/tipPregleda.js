
function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/page",
        success: function (data) {
        	var table = $("#tipoviPregleda")
            for (var tip of data) {               
                let tr = $("<tr id=\"tr" + tip.id + "\"></tr>");
                let id = $("<td>" + tip.id + "</td>");
                let naziv = $("<td>" + tip.naziv + "</td>");
                let trajanje = $("<td>" + tip.trajanje + "</td>")  ;
                let cena = $("<td>" + tip.cena + "</td>");
                let izmeni = $("<td>" + "<a href=\"izmeniTipPregleda.html?id=" + tip.id + "\">Izmeni</a></td>");
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniTipPregleda('${tip.id}')">Ukloni</button></td>`);
                tr.append(id);
                tr.append(naziv);
                tr.append(trajanje);
                tr.append(cena);
                tr.append(izmeni);
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}


function pretraga(){
	var trazi = $('#trazi').val();
	console.log(trazi);
	if(trazi == ""){
		return;
	}
	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/tipPregleda/search",
        dataType: 'json',
        data: trazi,
        success : function (tipovi) {
        	console.log(tipovi);
        	$("#table_body").empty();
        	var table = $("#tipoviPregleda")
            for (var tip of tipovi) {               
                let tr = $("<tr id=\"tr" + tip.id + "\"></tr>");
                let id = $("<td>" + tip.id + "</td>");
                let naziv = $("<td>" + tip.naziv + "</td>");
                let trajanje = $("<td>" + tip.trajanje + "</td>")  ;
                let cena = $("<td>" + tip.cena + "</td>");
                let izmeni = $("<td>" + "<a href=\"izmeniTipPregleda.html?id=" + tip.id + "\">Izmeni</a></td>");
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniTipPregleda('${tip.id}')">Ukloni</button></td>`);
                tr.append(id);
                tr.append(naziv);
                tr.append(trajanje);
                tr.append(cena);
                tr.append(izmeni);
                tr.append(ukloni);
                table.append(tr);
            }
		},
        
    });
} 


function provera(){
	$("#nazivError").css('visibility', 'hidden');
    $("#trajanjeError").css('visibility', 'hidden');
    $("#cenaError").css('visibility', 'hidden');
   

    var regex = /^[a-zA-Z0-9]{1,20}$/;
    //alert($("#naziv").val().length)
    if ($("#naziv").val().length > 20) {
        $("#nazivError").text("Naziv moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#nazivError").css('visibility','hidden');
    }
    
    if ($("#naziv").val() == "") {
        $("#nazivError").text("Naziv je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#nazivError").css('visibility','hidden');
    }
    
    if ($("#trajanje").val() == "") {
        $("#trajanjeError").text("Trajanje je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#trajanjeError").css('visibility','hidden');
    }
    
    if ($("#cena").val() == "") {
        $("#cenaError").text("Cena je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#cenaError").css('visibility','hidden');
    }
    
    if (isNaN($("#trajanje").val()) ){
    	$("#trajanjeError").text("Trajanje je celobrojna vrednost!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
	else {
	   var vr = parseFloat($("#trajanje").val());
	   if (!Number.isInteger(vr)){
	       $("#trajanjeError").text("Trajanje je celobrojna vrednost!").css('visibility', 'visible').css('color', 'red');
		   return false;
	   }
	   else{
	    	$("#trajanjeError").css('visibility','hidden');
	    }
	 }
    
    if (isNaN($("#cena").val()) ){
    	$("#cenaError").text("Cena je brojna vrednost!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#cenaError").css('visibility','hidden');
    }
    if (!regex.test($("#naziv").val())) {
        $("#nazivError").text("Naziv moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#nazivError").css('visibility','hidden');
    }
    return true;
}


function dodajTipPregleda() {
	console.log("MRS");
	console.log(provera())
	if(provera()){
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/klinicki-centar/tipPregleda/add",
	        dataType: 'json',
	        data: JSON.stringify({
	            naziv: $('#naziv').val(),
	            trajanje: $('#trajanje').val(),
	            cena: $('#cena').val(),
	        }),
	        success: function () {
	            window.location.replace("tipoviPregleda.html")
	        }
	    })
	}

}


function ukloniTipPregleda(id) {
	
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/tipPregleda/delete/" + id,
        success: function () {
            $("#tr" + id).remove();
            alert("USPESNO BRISANJE TIPA PREGLEDA");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function izmenaTipaPregleda() {
	
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/getUpdate/" + imeParam,
        success: function (data) {
            $("#naziv").val(data.naziv);
            $("#trajanje").val(data.trajanje);
            $("#cena").val(data.cena);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    
    $('#izmeniBtn').click(function () {
    	if(provera()){
    		$.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "/klinicki-centar/tipPregleda/update",
                dataType: 'json',
                data: JSON.stringify({
                    id: imeParam,
                    naziv: $('#naziv').val(),
                    trajanje: $('#trajanje').val(),
                    cena: $('#cena').val(),
                }),
                success: function () {
                    window.location.replace("tipoviPregleda.html")
                }
            });
    	}
    	
        
    });
}



function ucitajTabelu1() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/page",
        success: function (data) {
        	var table = $("#tipoviPregleda")
            for (var tip of data) {               
                let tr = $("<tr id=\"tr" + tip.id + "\"></tr>");
                let id = $("<td>" + tip.id + "</td>");
                let naziv = $("<td>" + tip.naziv + "</td>");
                let trajanje = $("<td>" + tip.trajanje + "</td>")  ;
                let cena = $("<td>" + tip.cena + "</td>");
                let izmeni = $("<td>" + "<a href=\"izmeniTipPregleda.html?id=" + tip.id + "\">Izmeni</a></td>");
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniTipPregleda('${tip.id}')">Ukloni</button></td>`);
                tr.append(id);
                tr.append(naziv);
                tr.append(trajanje);
                tr.append(cena);
                tr.append(izmeni);
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}

function provera1(){
	$("#nazivError").css('visibility', 'hidden');
    $("#trajanjeError").css('visibility', 'hidden');
    $("#cenaError").css('visibility', 'hidden');
   

    var regex = /^[a-zA-Z0-9]{1,20}$/;
    alert($("#naziv").val().length)
    if ($("#naziv").val().length > 20) {
        $("#nazivError").text("Naziv moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        return;
    }        

    if ($("#naziv").val() == "") {
        $("#nazivError").text("Naziv je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if ($("#trajanje").val() == "") {
        $("#trajanjeError").text("Trajanje je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if ($("#cena").val() == "") {
        $("#cenaError").text("Cena je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    
    if (isNaN($("#trajanje").val()) ){
    	$("#trajanjeError").text("Trajanje je celobrojna vrednost!").css('visibility', 'visible').css('color', 'red');
        return;
    }
	else {
	   var vr = parseFloat($("#trajanje").val());
	   if (!Number.isInteger(vr)){
	       $("#trajanjeError").text("Trajanje je celobrojna vrednost!").css('visibility', 'visible').css('color', 'red');
		   return;
	   }
	 }
    
    if (isNaN($("#cena").val()) ){
    	$("#cenaError").text("Cena je brojna vrednost!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    
    if (!regex.test($("#naziv").val())) {
        $("#nazivError").text("Naziv moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
        return;
    }
}

function dodajTipPregleda1() {
    provera();
    

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/tipPregleda/add",
        dataType: 'json',
        data: JSON.stringify({
            naziv: $('#naziv').val(),
            trajanje: $('#trajanje').val(),
            cena: $('#cena').val(),
        }),
        success: function () {
            window.location.replace("tipoviPregleda.html")
            // alert("Uspesno dodavanje klinike!")
        }
    })
}


function ukloniTipPregleda1(id) {

    $.ajax({
        type: "DELETE",
        url: "/klinicki-centar/tipPregleda/delete/" + id,
        success: function () {
            $("#tr" + id).remove();
            alert("USPESNO BRISANJE TIPA PREGLEDA");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function izmenaTipaPregleda1() {
	
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        type: "get",
        url: "/klinicki-centar/tipPregleda/getUpdate/" + imeParam,
        success: function (data) {
            $("#naziv").val(data.naziv);
            $("#trajanje").val(data.trajanje);
            $("#cena").val(data.cena);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
    
    
    $('#izmeniBtn').click(function () {
    	provera();
    	
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/tipPregleda/update",
            dataType: 'json',
            data: JSON.stringify({
                id: imeParam,
                naziv: $('#naziv').val(),
                trajanje: $('#trajanje').val(),
                cena: $('#cena').val(),
            }),
            success: function () {
                window.location.replace("tipoviPregleda.html")
            }
        });
    });
}




