
function ucitajTabelu() {
	var p = window.location.search.substr(1);
    console.log(p);
    if(p != ""){
    	document.getElementById('dodajSalu').style.visibility = 'hidden';
    	//document.getElementById('div_podaci').style.display = 'block';
    	document.getElementById('filter').style.display = 'none';
    	filtriranje();
    }
    else {
    	document.getElementById('dodajSalu').style.visibility = 'visible';
    	//document.getElementById('div_podaci').style.display = 'none';
    	document.getElementById('filter').style.display = 'block';
    
	    $.ajax({
	        type: "get",
	        url: "/klinicki-centar/sala/page",
	        success: function (data) {
	        	var table = $("#sale")
	            for (var sala of data) {
	                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
	                let id = $("<td>" + sala.id + "</td>");
	                let naziv = $("<td>" + sala.naziv + "</td>");
	                let tip = $("<td>" + sala.tip + "</td>");
	                let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
	                let izmeni = $("<td>" + "<a href=\"izmeniSalu.html?id=" + sala.id + "\">Izmeni</a></td>");
	                let kalendar = $("<td>" + "<a href=\"kalendarZauzecaSale.html?id=" + sala.id + "\">Kalendar</a></td>");
	                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${sala.id}')">Ukloni</button></td>`)
	                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
	                tr.append(id);
	                tr.append(naziv);
	                tr.append(tip);
	                tr.append(slobodna);
	                tr.append(kalendar);
	                if(p == ""){
	                	tr.append(izmeni);
	                    tr.append(ukloni);
	                }
	                else {
	                	tr.append(izaberi);
	                }                
	                table.append(tr);
	            }
	
	        }
	    });
    }
    
}

function proveriKorisnika(){
	$.ajax({
        type: "get",
        url: "/klinicki-centar/login/tipKorisnika",
        success: function(data) {       
            console.log(data);
            tipKorisnika = data;
            //window.tipKorisnika = data;
            if(tipKorisnika == "lekar"){
            	$("#dodajSalu").css('visibility', 'hidden');
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
        async: false,

    });
}

function pretraga(){
	var p = window.location.search.substr(1);
	var pp = p.split("=");
	var w = "nista";
	if(pp[1] == "pregled"){
		w = "ZA_PREGLED";
		console.log(w);
	}
	else if(pp[1] == "operacija"){
		w = "OPERACIONA"
	}
	
	var trazi = $('#trazi').val();
	console.log(trazi);
	if(trazi == ""){
		return;
	}
	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/sala/search/"+w,
        dataType: 'json',
        data: trazi,
        success : function (sale) {
        	console.log(sale);
        	$("#table_body").empty();
        	var table = $("#sale")
            for (var sala of sale) {
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>");
                let naziv = $("<td>" + sala.naziv + "</td>");
                let tip = $("<td>" + sala.tip + "</td>");
                let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
                let izmeni = $("<td>" + "<a href=\"izmeniSalu.html?id=" + sala.id + "\">Izmeni</a></td>")
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${sala.id}')">Ukloni</button></td>`)
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);
                tr.append(slobodna);
                if(p == ""){
                	tr.append(izmeni);
                    tr.append(ukloni);
                }
                else {
                	tr.append(izaberi);
                }                
                table.append(tr);
            }

		},
        
    });
} 


function filtriranje(){
	var p = window.location.search.substr(1);
	var filter;
	if(p == ""){
		var tipSelected = document.getElementById("tipSaleSelect");
		filter = tipSelected.options[tipSelected.selectedIndex].value;
	}
	else {
		var pp = p.split("=");
		var w = "";
		if(pp[1] == "pregled"){
			filter = "ZA_PREGLED";
			console.log(w);
		}
		else if(pp[1] == "operacija"){
			filter = "OPERACIONA"
		}
		
	}
	
	console.log(filter);
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/sala/filter",
        dataType: 'json',
        data: filter,
        success : function (sale) {
        	console.log(sale);
        	$("#table_body").empty();
        	var table = $("#sale")
            for (var sala of sale) {
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>");
                let naziv = $("<td>" + sala.naziv + "</td>");
                let tip = $("<td>" + sala.tip + "</td>");
                let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
                let izmeni = $("<td>" + "<a href=\"izmeniSalu.html?id=" + sala.id + "\">Izmeni</a></td>")
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${sala.id}')">Ukloni</button></td>`)
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);
                tr.append(slobodna);
                if(p == ""){
                	tr.append(izmeni);
                    tr.append(ukloni);
                }
                else {
                	tr.append(izaberi);
                }                
                table.append(tr);
            }

		},
        
    });
}



function dodajSalu() {
	var tipSelected = document.getElementById("tipSaleSelect");
	var tipSale = tipSelected.options[tipSelected.selectedIndex].value;

	if(provera()){
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/klinicki-centar/sala/add",
	        dataType: 'json',
	        data: JSON.stringify({
	            naziv: $('#naziv').val(),
	            tip: tipSale, 
	        }),
	        success: function () {
	            window.location.replace("sale.html")
	        }
	    })
	}
    

}

function provera(){
	$("#nazivError").css('visibility', 'hidden');
    $("#tipError").css('visibility', 'hidden');
    
    var tipSelected = document.getElementById("tipSaleSelect");
	var tipSale = tipSelected.options[tipSelected.selectedIndex].value;

    var regex = /^[a-zA-Z0-9]{1,20}$/;
    //alert($("#naziv").val().length)
    if ($("#naziv").val().length > 20) {
        $("#nazivError").text("Naziv moze da sadrzi maksimalno 20 karaktera!").css('visibility', 'visible').css('color', 'red');
        return false;
    }  
    else{
    	$("#nazivError").css('visibility', 'hidden')
    }

    if ($("#naziv").val() == "") {
        $("#nazivError").text("Naziv je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#nazivError").css('visibility', 'hidden')
    }

    if (tipSale == "") {
        $("#tipError").text("Tip je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#tipError").css('visibility', 'hidden')
    }

    
    if (!regex.test($("#naziv").val())) {
        $("#nazivError").text("Naziv moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
        return false;
    }
    else{
    	$("#nazivError").css('visibility', 'hidden')
    }

    
    return true;

}

function ukloniSalu(imeParam) {
	
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/sala/delete/" + imeParam,
        success: function () {
            $("#tr" + imeParam).remove();
            alert("USPESNO BRISANJE SALE");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function izmenaSale1() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/getUpdate/" + imeParam,
        success: function (data) {
            $("#naziv").val(data.naziv);
            var tipSelected = document.getElementById("tipSaleSelect");
        	tipSelected.options[tipSelected.selectedIndex].value = data.tip;
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
    
    
    $('#izmeniBtn').click(function () {
    	var tipSelected1 = document.getElementById("tipSaleSelect");
    	var tipSale1 = tipSelected1.options[tipSelected1.selectedIndex].value;
    	
    	if(provera()){
    		$.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "/klinicki-centar/sala/update",
                dataType: 'json',
                data: JSON.stringify({
                    id: imeParam,
                    naziv: $('#naziv').val(),
                    tip: tipSale1,
                }),
                success: function () {
                    window.location.replace("sale.html")
                }
            });
    	}
        
    });
}


function ucitajTabelu1() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/page",
        success: function (data) {
        	var table = $("#sale")
            for (var sala of data) {               
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>")
                let naziv = $("<td>" + sala.naziv + "</td>")
                let tip = $("<td>" + sala.tip + "</td>")                
                let izmeni = $("<td>" + "<a href=\"izmeniSalu.html?id=" + sala.id + "\">Izmeni</a></td>")
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${sala.id}')">Ukloni</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);               
                tr.append(izmeni);
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}



function dodajSalu1() {
    $("#nazivError").css('visibility', 'hidden');
    $("#tipError").css('visibility', 'hidden');
    
    var tipSelected = document.getElementById("tipSaleSelect");
	var tipSale = tipSelected.options[tipSelected.selectedIndex].value;

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
    if (tipSale == "") {
        $("#tipError").text("Tip je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    
    if (!regex.test($("#naziv").val())) {
        $("#nazivError").text("Naziv moze da sadrzi samo mala, velika slova i brojeve.").css('visibility', 'visible').css('color', 'red');
        return;
    }
    

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/sala/add",
        dataType: 'json',
        data: JSON.stringify({
            naziv: $('#naziv').val(),
            tip: tipSale, 
        }),
        success: function () {
            window.location.replace("sale.html")
            // alert("Uspesno dodavanje klinike!")
        }
    })
}


function ukloniSalu1(imeParam) {
	
    
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/sala/delete/" + imeParam,
        success: function () {
            $("#tr" + imeParam).remove();
            alert("USPESNO BRISANJE SALE");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function izmenaSale() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/getUpdate/" + imeParam,
        success: function (data) {
            $("#naziv").val(data.naziv);
            //var tipSelected = document.getElementById("tipSaleSelect");
        	//tipSelected.options[tipSelected.selectedIndex].value = data.tip;
            var $selekt = document.getElementById("tipSaleSelect");			
			
		    var option1 = document.createElement("option");
		    var option2 = document.createElement("option");
		    option1.value = data.tip;
		    if(data.tip == "OPERACIONA"){
		    	option1.text = "operaciona sala";
			    $selekt.appendChild(option1);
			    option2.value = "ZA_PREGLED";
			    option2.text = "sala za pregled";
			    $selekt.appendChild(option2);
		    }
		    else{
		    	option1.text = "sala za pregled";
			    $selekt.appendChild(option1);
			    option2.value = "OPERACIONA";
			    option2.text = "operaciona sala";
			    $selekt.appendChild(option2);
		    }
		    
				
        	//$("#tipSaleSelect").val(data.tip);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
    
    
    $('#izmeniBtn').click(function () {
    	var tipSelected = document.getElementById("tipSaleSelect");
    	var tipSale = tipSelected.options[tipSelected.selectedIndex].value;
    	if(provera()){
    		$.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "/klinicki-centar/sala/update",
                dataType: 'json',
                data: JSON.stringify({
                    id: imeParam,
                    naziv: $('#naziv').val(),
                    tip: tipSale,
                }),
                success: function () {
                    window.location.replace("sale.html")
                }
            });
    	}
        
    });
}

function datumVreme(){
	var dt = $("#date-time").val();
	console.log(dt);
	var trajanje = $("#trajanje").val();
	console.log(trajanje);
	
	var p = window.location.search.substr(1);
	var pp = p.split("=");
	var w = "nista";	
	if(pp[1] == "pregled"){
		w = "ZA_PREGLED";
	}
	else if(pp[1] == "operacija"){
		w = "OPERACIONA"
	}
	console.log(w);
	
	if(trajanje != "" && dt != ""){
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/klinicki-centar/sala/filterTime/"+w,
	        dataType: 'json',
	        data: dt+";"+trajanje,
	        success : function (sale) {
	        	console.log(sale);
	        	$("#table_body").empty();
	        	var table = $("#sale")
	            for (var sala of sale) {
	            	let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
	                let id = $("<td>" + sala.id + "</td>");
	                let naziv = $("<td>" + sala.naziv + "</td>");
	                let tip = $("<td>" + sala.tip + "</td>");
	                let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
	                let izmeni = $("<td>" + "<a href=\"izmeniSalu.html?id=" + sala.id + "\">Izmeni</a></td>")
	                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${sala.id}')">Ukloni</button></td>`)
	                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
	                tr.append(id);
	                tr.append(naziv);
	                tr.append(tip);
	                tr.append(slobodna);
	                if(p == ""){
	                	tr.append(izmeni);
	                    tr.append(ukloni);
	                }
	                else {
	                	tr.append(izaberi);
	                }                
	                table.append(tr);
	            }

			},
	        
	    });
	}
	
}

function proveriFilter() {
    $("#datumError").css('visibility', 'hidden');
    $("#trajanjeError").css('visibility', 'hidden');

    var trajanje = $("#trajanje").val();
    var datum = $("#date-time").val();

    var retVal = true;

   
    if (trajanje.val() == "") {
        $("#trajanjeError").text("Unesite trajanje").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#trajanjeError").css('visibility', 'hidden')
    }
    if (datum == "") {
        $("#datumError").text("Unesite datum i vreme").css('visibility', 'visible').css('color', 'red');
        retVal = false;
    } else {
        $("#datumError").css('visibility', 'hidden')
    }


    return retVal;
}

function izaberiSalu(idSale){	
	var dt = $("#date-time").val();
	//console.log(opener)
	close();
	var el = opener.document.getElementById("salaSelect");
	opener.console.log(el);

	var sala = $("#salaSelect option[value='"+idSale+"']", opener.document).prop('selected', true);
	$("#date-time", opener.document).val(dt);
	opener.console.log(sala);
}




