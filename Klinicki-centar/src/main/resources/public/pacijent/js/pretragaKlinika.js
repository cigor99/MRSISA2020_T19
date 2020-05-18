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
		    		prikaziKlinike();
		        }
		 });
	}
	
	
	$("#reset").click(function(){
		prikaziKlinike();
	});
});

function pretraga(){
	var trazi = $('#trazi').val();
	console.log(trazi);
	if(trazi==""){
		alert("Morate uneti parametar pretrage")
		return;
	}
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
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/klinika/search",
        dataType: 'json',
        data: trazi,
        success : function (data) {
            for (var klinika of data) {               
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

		},
        
    });
}

function reset(){
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
