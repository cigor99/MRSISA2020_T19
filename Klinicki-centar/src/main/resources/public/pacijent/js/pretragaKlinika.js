$(document).ready(function () {
	window.tipID = null;
	window.datum = null;

	$.ajax({
		url: "/klinicki-centar/login/tipKorisnika",
        type: "get",
        success: function(data) {
        	window.tipKorisnika = data
        },
        error: function(data){
        	alert("error getTipKorisnika")
        },
        async: false
	});
	
	if(window.tipKorisnika != "pacijent"){
		alert("Ovo je samo za pacijenta")
		$.ajax({
	        type: "get",
	        url: "/klinicki-centar/login/logout",
	        success: function(data) {
	            window.location.replace("/klinicki-centar/login.html");
	        },
	        error: function(jqXHR) {
	            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
	        },
	    });
	}else{ 
	
		$.ajax({
		        url: "/klinicki-centar/login/getLoggedUser",
		        type: "get",
		        success: function(data) {
		            window.ulogovani = data;
		            var naslov = $("#naslov");
		        	naslov.empty();
		    		naslov.append('<h1>Klinike</h1>');
		        }
		 });
	}
	
	var tip = $("#tip");
	$.ajax({
		url:"/klinicki-centar/tipPregleda/all",
		type: "get",
		success: function(data){
			for(t of data){
				tip.append("<option>" + t.naziv + "</option>")
			}
		}
		
	});

	prikaziKlinike()

	$("#reset").click(function(){
		$("#table-holder").empty()
	});
});

function pretraga(){
	var trazi = $('#datepicker').val();
	if(trazi==""){
		alert("Morate uneti parametar pretrage")
		return;
	}

	var regex = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;

	if(!regex.test(trazi)){
		alert("Neispravan unos datuma");
		return;
	}

	var today = new Date();
	var tdd = String(today.getDate()).padStart(2, '0');
	var tmm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var tyyyy = today.getFullYear();
	
	var dateString = $("#datepicker").val();
	var dateTokens=  dateString.split("/")
	if(dateTokens[1].split()[0] == "0"){
		dateTokens[1] = dateTokens[1].split()[1]
	}
	if(dateTokens[2].split()[0] == "0"){
		dateTokens[2] = dateTokens[2].split()[1]
	}
	var date = new Date(dateTokens[2], dateTokens[0], dateTokens[1]);
	var tdint = parseInt(tdd);
	var tmint = parseInt(tmm);
	var tyint = parseInt(tyyyy);
	var ddint = parseInt(dateTokens[1]);
	var dmint = parseInt(dateTokens[0]);
	var dyint = parseInt(dateTokens[2]);

	if(dyint < tyint){
		alert("Ne smete izabrati datum koji je vec prosao")
		return;
	}
	if(dyint == tyint){
		if(dmint < tmint){
			alert("Ne smete izabrati datum koji je vec prosao")
			return;
		}
		if(dmint == tmint){
			if(ddint < tdint){
				alert("Ne smete izabrati datum koji je vec prosao")
				return;
			}
		}
	}

	window.datum = dateTokens[2]+";"+dateTokens[0]+";"+dateTokens[1];

	var tipID = null;
	let tipNaz = $("#tip").val();
	$.ajax({
		type: 'get',
		url: "/klinicki-centar/tipPregleda/all",
		success: function(data){
			for(t of data){
				if(t.naziv == tipNaz){
					tipID = t.id;
					return;
				}
			}
		},
		error: function(data){
			alert("Greska u tipPregleda/all");
		},
		async:false
	});

	if (tipID == null){
		alert("Tip nije pronadjen")
		return;
	}
	let oc = $("#ocenaSel").val()
	var ocenaTokens = oc.split("+");
	
	window.tipID = tipID;
	var parametri = JSON.stringify({
		datum: date,
		tip : tipID,
		ocena: ocenaTokens[0]
	});

	var holder = $("#table-holder");
	holder.empty();
	var table = $('<table id="klinike" name="klinike" class="table"></table>')
	var thead = $("<thead></thead>");
	var trHead= $("<tr></tr>");
	var idTH = $('<th id="ID">ID</th>');
	var nazivTH = $('<th id="naziv">Naziv</th>');
	var ocenaTH = $('<th id="ocena">Ocena</th>');
	var adresaTH = $('<th id="adresa">Adresa</th>');
    var cenaTH = $('<th id="cena">Cena Pregleda</th>');
    var izaberiTH = $('<th>Izaberi</th>');
	trHead.append(idTH);
	trHead.append(nazivTH);
	trHead.append(ocenaTH);
	trHead.append(adresaTH);
	trHead.append(cenaTH);
	trHead.append(izaberiTH);
	thead.append(trHead);
	table.append(thead);
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/klinika/searchPacijentoviParametri",
		dataType: 'json',
		contentType : 'application/json',
        data: parametri,
        success : function (data) {
            for (var klinika of data) {               
            	let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

                let idTD = $("<td>" + klinika.id + "</td>")
                let nazivTd = $("<td>" + klinika.naziv + "</td>")
                let ocenaTD = $("<td>" + klinika.prosecnaOcena + "</td>")
				let adresaTD = $("<td>" + klinika.adresa + "</td>")
				let cenaTD = null;
				$.ajax({
					type: "get",
					url: "/klinicki-centar/klinika/getCenaTipaPoID/" + klinika.id + "/" +tipID,
					success: function(data){
						cenaTD = $("<td>" + data + "</td>")
					},
					error: function(data){
						alert("Error tipPregleda/getUpdate")
					},
					async: false
				});
				
                // let izaberiTD = $("<td>" + "<a href=\"#izabrana?id=" +
				// klinika.id + ">Izaberi</a></td>")
                let izaberiTD = $("<td><a href='pretragaLekara.html?id="+klinika.id+"&nacin="+ 1 +"&datum=" + window.datum + "&tip=" +window.tipID + "'>Izaberi</a></td>")
                tr.append(idTD);
				tr.append(nazivTd);
				tr.append(ocenaTD);
                tr.append(adresaTD);
                tr.append(cenaTD);
				tr.append(izaberiTD);
                table.append(tr);
                holder.append(table);
            }

		},error: function(jqXHR) {
			alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
		}
        
	});
}

function prikaziKlinike(){
	var holder = $("#table-holder");
holder.empty();
var table = $('<table id="klinike" name="klinike" class="table"></table>')
var thead = $("<thead></thead>");
var trHead= $("<tr></tr>");
var idTH = $('<th id="ID">ID</th>');
var nazivTH = $('<th id="naziv">Naziv</th>');
var ocenaTH = $('<th id="ocena">Ocena</th>');
var adresaTH = $('<th id="adresa">Adresa</th>');
var izaberiTH = $('<th>Profil</th>');
trHead.append(idTH);
trHead.append(nazivTH);
trHead.append(ocenaTH);
trHead.append(adresaTH);
trHead.append(izaberiTH);
thead.append(trHead);
table.append(thead);
$.ajax({
	type: "GET",
	url: "/klinicki-centar/klinika/page",
	success : function (data) {
		for (var klinika of data) {               
			let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

			let idTD = $("<td>" + klinika.id + "</td>")
			let nazivTd = $("<td>" + klinika.naziv + "</td>")
			let ocenaTD = $("<td>" + klinika.prosecnaOcena + "</td>")
			let adresaTD = $("<td>" + klinika.adresa + "</td>")
			
			// let izaberiTD = $("<td>" + "<a href=\"#izabrana?id=" +
			// klinika.id + ">Izaberi</a></td>")
			let izaberiTD = $("<td><a href='pretragaLekara.html?id="+klinika.id+"&nacin="+ 2 +"&datum=" + window.datum + "&tip=" +window.tipID + "'>Izaberi</a></td>")
			tr.append(idTD);
			tr.append(nazivTd);
			tr.append(ocenaTD);
			tr.append(adresaTD);
			tr.append(izaberiTD);
			table.append(tr);
			holder.append(table);
		}

	},error: function(jqXHR) {
		alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
	}
	
});
}


