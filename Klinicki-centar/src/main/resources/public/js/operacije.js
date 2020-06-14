function ucitajTabelu() {

    $.ajax({
        type: "get",
        url: "/klinicki-centar/Operacija/all",
        success: function(data) {
            console.log("success");
            var table = $("#operacije")
            for (var operacija of data) {
                let tr = $("<tr id=\"tr" + operacija.id + "\"></tr>");
                let id = $("<td>" + operacija.id + "</td>");
                let datum = $("<td>" + operacija.datum + "</td>");
                let vreme = $("<td>" + operacija.vreme + "</td>");
                let sala = $("<td>" + operacija.sala + "</td>");
                let trajanje = $("<td>" + operacija.trajanje + "</td>");
                let lekari = $("<td>" + operacija.lekari + "</td>");
                tr.append(id);
                tr.append(datum);
                tr.append(vreme);
                tr.append(sala);
                tr.append(trajanje);
                tr.append(lekari);
                table.append(tr);
            }

        }
    });
}