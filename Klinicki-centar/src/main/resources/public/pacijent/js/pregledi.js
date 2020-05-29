function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pregled/page",
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

function ucitajSale() {
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
                
                let izaberi = $("<td>" + "<a href=\"dodavanjePregleda.html?id=" + sala.id + "\">Izaberi</a></td>")
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);               
                tr.append(izaberi);
                table.append(tr);
            }

        },error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        }
    });
}

function datumVreme(){
	var dt = $("#date-time").val();
	console.log(dt);
	var trajanje = $("#trajanje").val();
	console.log(trajanje);
	/*var data = JSON.stringify({
        datumVreme: dt,
        trajanje: trajanje,
    });*/
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/sala/filterTime",
        dataType: 'json',
        data: dt+";"+trajanje,
        success : function (sale) {
        	console.log(sale);
        	$("#table_body").empty();
        	var table = $("#sale")
            for (var sala of sale) {               
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>")
                let naziv = $("<td>" + sala.naziv + "</td>")
                let tip = $("<td>" + sala.tip + "</td>")                
                let izaberi = $("<td>" + "<a href=\"dodavanjePregleda.html?id=" + sala.id + "\">Izaberi</a></td>")
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);               
                tr.append(izaberi);
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