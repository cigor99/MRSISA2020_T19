function ucitajTabelu(){
	$.ajax({
        type: "get",
        url: "/klinicki-centar/ZZP/getAll",
        success: function(data) {
            var table = $("#zahtevi")
            for (var zahtev of data) {
                let tr = $("<tr id=\"tr" + zahtev.id + "\"></tr>");
                let datum = $("<td>" + zahtev.datum + "</td>");
                let vreme = $("<td>" + zahtev.vreme + "</td>");
                let lekar = $("<td>" + zahtev.lekar + "</td>");
                let pacijent = $("<td>" + zahtev.pacijent + "</td>");
                console.log(zahtev.datetime);
                //let rezervisi = $("<td>" + "<a href=\"rezervisiSalu.html?idPregleda=" + zahtev.pregled + "\">Rezervisi salu</a></td>")
                let rezervisi = $(`<td><button  type="button" id="rezervisiBtn" onclick="rezervisiSalu('${zahtev.pregled}', '${zahtev.datetime}')">Rezervisi salu</button></td>`)

                tr.append(datum);
                tr.append(vreme);
                tr.append(lekar);
                tr.append(pacijent);
                tr.append(rezervisi);
                table.append(tr);
            }

        }
    });
}

function rezervisiSalu(pregledID, datum){
	
	console.log(datum);
	window.location.replace("/klinicki-centar/rezervisiSalu.html?idPregleda=" + pregledID + "&dt=" + datum);
	window.datum = datum;
}