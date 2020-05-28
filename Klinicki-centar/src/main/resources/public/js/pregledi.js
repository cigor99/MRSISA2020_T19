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
                //let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${pregled.id}')">Ukloni</button></td>`)
                tr.append(id);
                tr.append(datum);
                tr.append(vreme);
                tr.append(sala);
                tr.append(trajanje);
                tr.append(lekar);
                tr.append(tip);               
                tr.append(cena);
                //tr.append(ukloni);
                table.append(tr);
            }

        }
    });
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
            	$("#dodajPregled").css('visibility', 'hidden');
            }

        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
        async: false,

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
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu('${sala.id}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);               
                tr.append(izaberi);
                table.append(tr);
            }

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

		},
        
    });
}