function ucitajTabelu() {
    var p = window.location.search.substr(1);
    console.log(p);
    
    var pregled = p.split("&")[0];
    var idPregleda = pregled.split("=")[1];
    var datum = p.split("&")[1];
    var dt = datum.split("=")[1];
    dt = dt.split(".")[0];
    console.log(idPregleda);
    console.log(window.datum);
    $("#date-time").val(dt);
    
    $.ajax({
        type: "get",
        url: "/klinicki-centar/sala/rezervacijaPregleda/"+idPregleda,
        success: function(data) {
            var table = $("#sale");
            if(data.length==0){
            	$("#label1").css('visibility', 'visible');
            	$("#button1").css('visibility', 'visible');
            }
            for (var sala of data) {
            	console.log(sala);
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>");
                let naziv = $("<td>" + sala.naziv + "</td>");
                let tip = $("<td>" + sala.tip + "</td>");
                //let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu1('${sala.id}', '${idPregleda}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);
                //tr.append(slobodna);                
                tr.append(izaberi);                
                table.append(tr);
            }

        }
    });
}


function prviSlobodanTermin(){
	var p = window.location.search.substr(1);
    console.log(p);
    
    var pregled = p.split("&")[0];
    var idPregleda = pregled.split("=")[1];
	$.ajax({
        type: "get",
        url: "/klinicki-centar/sala/saleZaPregled",
        success: function(data) {
            var table = $("#sale");
            for (var sala of data) {
            	console.log(sala);
                let tr = $("<tr id=\"tr" + sala.id + "\"></tr>");
                let id = $("<td>" + sala.id + "</td>");
                let naziv = $("<td>" + sala.naziv + "</td>");
                let tip = $("<td>" + sala.tip + "</td>");
                let slobodna = $("<td>" + sala.prviSlobodanTermin + "</td>");
                let izaberi = $(`<td><button  type="button" id="izaberiBtn" onclick="izaberiSalu2('${sala.id}', '${idPregleda}', '${sala.prviSlobodanTermin}')">Izaberi</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);
                tr.append(slobodna);                
                tr.append(izaberi);                
                table.append(tr);
            }

        }
    });
}

function izaberiSalu1(idSale, idPregleda){
	$.ajax({
        type: "get",
        url: "/klinicki-centar/sala/rezervisiSaluZaPregled/"+idSale+"/"+idPregleda,
        success: function(data) {
            alert("Sala je uspesno rezervisana");
            window.location.replace("/klinicki-centar");
        }
    });
}

function izaberiSalu2(idSale, idPregleda, prviSlobodanTermin){
	console.log(idSale);
	console.log(idPregleda);
	console.log(prviSlobodanTermin);
	$.ajax({
        type: "get",
        url: "/klinicki-centar/sala/rezervisiSaluZaPregled/"+idSale+"/"+idPregleda+"/"+prviSlobodanTermin,
        success: function(data) {
            alert("Sala je uspesno rezervisana");
            window.location.replace("/klinicki-centar");
        }
    });
}
