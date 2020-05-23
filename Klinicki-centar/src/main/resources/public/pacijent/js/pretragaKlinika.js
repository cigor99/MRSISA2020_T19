$(document).ready(function () {
	$.ajax({
		url: "/klinicki-centar/login/tipKorisnika",
        type: "get",
        success: function(data) {
        	window.tipKorisnika = data
        	console.log(data)
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
		    		naslov.append('<h1>'+window.ulogovani.ime+" " + window.ulogovani.prezime+'</h1>');
		    		naslov.append('<h1>Klinike</h1>');
		    		//prikaziKlinike();
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

	var tipID = null;
	let tipNaz = $("#tip").val()
	$.ajax({
		type: 'get',
		url: "/klinicki-centar/tipPregleda/all",
		success: function(data){
			console.log(data)
			for(t of data){
				console.log(t)
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

	var dateString = $("#datepicker").val();
	var dateTokens=  dateString.split("/")
	if(dateTokens[1].split()[0] == "0"){
		dateTokens[1] = dateTokens[1].split()[1]
	}

	if(dateTokens[2].split()[0] == "0"){
		dateTokens[2] = dateTokens[2].split()[1]
	}
	
	var date = new Date(dateTokens[1], dateTokens[2], dateTokens[0]);
	console.log(date)

	var ocenaTokens = $("#ocena").val().split("+")
	var parametri = JSON.stringify({
		datum: date,
		tip : tipID,
		ocena: ocenaTokens[0]
	});
	console.log(parametri)

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
                let ocenaTD = $("<td>" + klinika.prosencaOcena + "</td>")
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
                let izaberiTD = $("<td><a href='pretragaLekara.html?id="+klinika.id+"'>Izaberi</a></td>")
                tr.append(idTD);
				tr.append(nazivTd);
				tr.append(ocenaTD);
                tr.append(adresaTD);
                tr.append(cenaTD);
				tr.append(izaberiTD);
                table.append(tr);
                holder.append(table);
            }

		},
        
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
    var adresaTH = $('<th id="adresa">Adresa</th>');
    var opisTH = $('<th id="opis">Opis</th>');
    var izaberiTH = $('<th>Izaberi</th>');
	trHead.append(idTH);
	trHead.append(nazivTH);
	trHead.append(adresaTH);
	trHead.append(opisTH);
	trHead.append(izaberiTH);
	thead.append(trHead);
	table.append(thead);
	
	
	$.ajax({
        type: "get",
        url: "/klinicki-centar/klinika/page",
        success: function (data) {

            // alert(JSON.stringify(data));
            for (let klinika of data) {

                
                let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

                let idTD = $("<td>" + klinika.id + "</td>")
                let nazivTd = $("<td>" + klinika.naziv + "</td>")
                let adresaTD = $("<td>" + klinika.adresa + "</td>")
                let opisTD = $("<td>" + klinika.opis + "</td>")
                // let izaberiTD = $("<td>" + "<a href=\"#izabrana?id=" +
				// klinika.id + ">Izaberi</a></td>")
                let izaberiTD = $("<td><a href='pretragaLekara.html?id="+klinika.id+"'>Izaberi</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(adresaTD);
                tr.append(opisTD);
                tr.append(izaberiTD);
                table.append(tr);
                holder.append(table);  
            }
        }
    });
 
};
