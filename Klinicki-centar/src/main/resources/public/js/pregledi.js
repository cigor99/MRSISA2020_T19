function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/pregled/page",
        success: function (data) {
        	var table = $("#pregledi")
            for (var pregled of data) {               
                let tr = $("<tr id=\"tr" + pregled.id + "\"></tr>");
                let id = $("<td>" + pregled.id + "</td>");
                let datum = $("<tr id=\"tr" + pregled.datum + "\"></tr>");
                let vreme = $("<tr id=\"tr" + pregled.vreme + "\"></tr>");                
                let sala = $("<td>" + pregled.sala + "</td>");
                let trajanje = $("<td>" + pregled.trajanje + "</td>");
                let lekar = $("<td>" + pregled.lekar + "</td>");
                let tip = $("<td>" + pregled.tip + "</td>");   
                let cena = $("<td>" + pregled.cena + "</td>")                
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniSalu('${pregled.id}')">Ukloni</button></td>`)
                tr.append(id);
                tr.append(datum);
                tr.append(vreme);
                tr.append(sala);
                tr.append(trajanje);
                tr.append(lekar);
                tr.append(tip);               
                tr.append(cena);
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}