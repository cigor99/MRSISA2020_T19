$(document).ready(function() {

    $.ajax({
        url: "/klinicki-centar/pregled/all",
        type: "get",
        success: function(data) {
            iscrtaj();
            for (let pregled of data) {
                let danPregled = pregled.datum.substring(0, 2);
                let mesecPregled = pregled.datum.substring(3, 5)
                if (mesecPregled.substring(0, 1) == "0") {
                    mesecPregled = mesecPregled.substring(1, 2);
                }
                let tbody = $("#tbody");

                var dan;
                let mesec;

                for (dan = 1; dan < 32; dan++) {
                    for (mesec = 1; mesec < 13; mesec++) {
                        if (mesec == mesecPregled && dan == danPregled && $("#td" + mesec.toString() + dan.toString()).html() === "") {
                            let polje = $("#td" + mesec.toString() + dan.toString());
                            let color = Math.floor((Math.random() * 6) + 1);
                            let boja;
                            switch (color % 6) {
                                case 0:
                                    boja = "#f54251";
                                    break;
                                case 1:
                                    boja = "#2a6df4";
                                    break;
                                case 2:
                                    boja = "chocolate";
                                    break;
                                case 3:
                                    boja = "darkmagenta";
                                    break;
                                case 4:
                                    boja = "mediumspringgreen";
                                    break;
                                case 5:
                                    boja = "turquoise";
                                    break;
                            }
                            polje.css('background-color', boja);
                            if (mesec.toString().length == 1) {
                                mesec = "0" + mesec.toString();
                            }
                            polje.append("Pregled " + dan.toString() + "." + mesec.toString())
                            polje.attr("onclick", "dobavi(" + dan.toString() + "," + mesec.toString() + ")");
                            // tr.append(polje);
                            // tbody.append(tr);
                        }

                    }

                }
            }

        }

    });




    $("#pregled4").click(function() {
        window.open("recept.html", 'newwindow', 'width=1200,height=650');
    });
});

function dobavi(dan, mesec) {
    // if (mesec.toString().length == 1) {
    //     mesec = "0" + mesec.toString();
    // }
    window.open("proba.html?d=" + dan.toString() + "&m=" + mesec.toString(),
        'newwindow',
        'width=700,height=500');
}


function iscrtaj() {
    let tbody = $("#tbody");

    var dan;
    let mesec;

    for (dan = 1; dan < 32; dan++) {
        let tr = $("<tr></tr>");

        // td.attr("id", mesec.toString() + dan.toString());
        for (mesec = 1; mesec < 13; mesec++) {
            let td;

            let uslov = true;
            if (mesec == 2) {
                if (dan > 29) {
                    uslov = false;
                    td = $("<td></td>");
                    td.css("background-color", 'gray');
                    td.attr("id", "td" + mesec.toString() + dan.toString());
                    tr.append(td);
                }
            }
            if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
                if (dan == 31) {
                    uslov = false;
                    td = $("<td></td>");
                    td.css("background-color", 'gray');
                    td.attr("id", "td" + mesec.toString() + dan.toString());
                    tr.append(td);
                }
            }

            if (uslov) {
                td = $("<td></td>");
                td.attr("id", "td" + mesec.toString() + dan.toString());
                tr.append(td);
            }

        }
        tbody.append(tr);
    }
};