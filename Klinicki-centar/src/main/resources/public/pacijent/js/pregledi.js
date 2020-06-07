$(document).ready( function() {	
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];if(imeParam == undefined){
    	alert("Morate prvo izabrati kliniku")
    	window.location.replace("/klinicki-centar/pretragaKlinika.html");
    }else{
    	$.ajax({
    		type: "get",
    		url: "/klinicki-centar/klinika/getUpdate/" + imeParam,
    		success: function(data){
    			window.klinika = data;
    		},
    		error: function(data){
    			alert("error in get klinika");
    		},
    		async:false
        });
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
            ucitajTabelu();
        }
    }
    //ucitajTabelu();
});

function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pregled/definisaniPregledi/" + window.klinika.id,
        success: function (data) {
        	console.log("success");
        	var table = $("#pregledi")
            for (var pregled of data) {               
                let tr = $("<tr id=\"tr" + pregled.id + "\"></tr>");
                let id = $("<td>" + pregled.id + "</td>");
                let datum = $("<td>" + pregled.datum + "</td>");
                let vreme = $("<td>" + pregled.vreme + "</td>");                
                let sala = $("<td>" + pregled.sala + "</td>");
                let trajanje = $("<td>" + pregled.trajanje + "</td>");
                let lekar = $("<td>" + pregled.lekar + "</td>");
                let tip = $("<td>" + pregled.tipPregleda + "</td>");   
                let cena = $("<td>" + pregled.cena + "</td>")                
                let zakazi = $(`<td><button  type="button" id="zakaziBtn" onclick="zakazi('${pregled.id}')">Zakazi</button></td>`)
                //let zakazi = $(`<td><button  type="button" id="zakaziBtn">Zakazi</button></td>`)
                tr.append(id);
                tr.append(datum);
                tr.append(vreme);
                tr.append(sala);
                tr.append(trajanje);
                tr.append(lekar);
                tr.append(tip);               
                tr.append(cena);
                tr.append(zakazi);
                table.append(tr);
            }

        },error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        }
    });
}

function zakazi(idPregleda){
    $.ajax({
        type: "POST",
        contentType: "text",
        url: "/klinicki-centar/pregled/brzoZakazivanje/" + idPregleda,
        success : function(data){
            console.log("uspeo");
            alert("Uspesno poslat zahtev")
            window.location.replace("/klinicki-centar/pacijent/pacijentHomePage.html");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        }
    });
}