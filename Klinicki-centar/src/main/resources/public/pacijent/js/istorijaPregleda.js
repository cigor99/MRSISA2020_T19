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
		    		naslov.append('<h1>Istorija pregleda</h1>');
		        }
		 });
    }

    $.ajax({
        type: "get",
        url: "/klinicki-centar/pacijent/istorijaPregleda",
        success: function(data){
            var holder = $("#table-holder");
	        holder.empty();
	        var table = $('<table id="pregledi" name="pregledi" class="table"></table>')
	        var thead = $("<thead></thead>");
	        var trHead= $("<tr></tr>");
	        var idTH = $('<th id="ID">ID</th>');
	        var datumTH = $('<th id="datum">Datum</th>');
	        var vremeTH = $('<th id="vreme">Vreme</th>');
            var tipTH = $('<th id="tip">Tip</th>');
            var salaTH = $('<th id="sala">Sala</th>');
            var lekarTH = $('<th id="lekar">Lekar</th>');
            var trajanjeTH = $('<th id="trajanje">Trajanje</th>')
            var cenaTH = $('<th id="cena">Cena Pregleda</th>');
            
            var izaberiTH = $('<th>Detalji</th>');
	        trHead.append(idTH);
	        trHead.append(datumTH);
	        trHead.append(vremeTH);
	        trHead.append(tipTH);
	        trHead.append(salaTH);
            trHead.append(lekarTH);
            trHead.append(trajanjeTH);
            trHead.append(cenaTH);
            trHead.append(izaberiTH);
	        thead.append(trHead);
            table.append(thead);
            
            for(var pregled of data){
                let tr = $("<tr id=\"tr" + pregled.id + "\"></tr>");
                let idTD = $("<td>" + pregled.id + "</td>")
                let datumTD = $("<td>" + pregled.datum + "</td>")
                let vremeTD = $("<td>" + pregled.vreme + "</td>")
				let tipTD = $("<td>" + pregled.tipPregleda + "</td>")
                let salaTD = $("<td>" + pregled.sala + "</td>")
                let lekarTD = $("<td>" + pregled.lekar + "</td>")
                let trajanjeTD = $("<td>" + pregled.trajanje + "</td>")
                let cenaTD = $("<td>" + pregled.cena + "</td>")
                let izaberiTD = $("<td><a href='detaljiPregleda.html?id=" + pregled.id + "'>Oceni</a></td>")

                tr.append(idTD);
                tr.append(datumTD);
                tr.append(vremeTD)
                tr.append(tipTD)
                tr.append(salaTD)
                tr.append(lekarTD)
                tr.append(trajanjeTD)
                tr.append(cenaTD)
                tr.append(izaberiTD)
                table.append(tr);
                holder.append(table);
            }
        },
        error: function(jqXHR){
            if(jqXHR.status==403){
                alert("Samo pacijent moze da pristupi istoriji pregleda");
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
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            }
        }

    });
    
});