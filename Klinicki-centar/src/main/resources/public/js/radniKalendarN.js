$(document).ready(function() {
    // alert("RADI")

    iscrtaj();
    var d = new Date();
    pronalazenjeDatuma(d);



    $("#primeni").click(function() {
        let d = new Date($("#datum").val());
        iscrtaj();
        pronalazenjeDatuma(d);
    });
});

function pronalazenjeDatuma(d) {

    let mesec = d.getMonth() + 1;
    if (mesec.toString().length == 1) {
        mesec = "0" + mesec.toString();
    }
    let dan = d.getDate();
    if (dan.toString().length == 1) {
        dan = "0" + dan.toString();
    }
    let danas = getDatum(dan + " " + mesec + " " + d.getFullYear());
    var dict = {}; //key = datum, value = pozicija u tabeli
    for (let index = danas; index <= 6; index++) {
        dict[d.getDate() + 6 - index] = index;
    }

    for (let index = danas; index > 0; index--) {

        dict[d.getDate() - index] = 7 - index - 1;
    }
    // let counter = 0;
    for (var key in dict) {
        dobavi(key, dict, d);
    }
}


function dobavi(key, dict, d) {
    $.ajax({
        url: "/klinicki-centar/pregled/getDnevniPregled/" + key + "/" + parseInt(d.getMonth() + 1),
        type: 'get',
        success: function(data) {
            for (let pregled of data) {
                alert(window.ulogovani)
                alert(pregled.lekar)
                if (window.ulogovani == pregled.lekar) {
                    alert("td" + dict[key] + pregled.vreme);
                    // let td = $("#td" + dict[key] + pregled.vreme);
                    let td = document.getElementById("td" + dict[key] + pregled.vreme);
                    td.style.backgroundColor = boja();
                    // for (let pregled of data) {
                    td.innerHTML = "<div><label> Datum:" + pregled.datum + " </label><br><label>Vreme: </label>" + pregled.vreme + "<br><label>Trajanje: </label>" + pregled.trajanje + " min<br><label>Pacijent: </label>" + pregled.pacijent + "<br><label>Tip pregleda: </label>" + pregled.tipPregleda + " </div > ";
                    // let div = $("<div></div>")

                    // div.css("background-color", boja())
                    //     // let tr = $("<tr></tr>");
                    // let datumLabel = $("<label>Datum: </label>");

                    // div.append(datumLabel);
                    // div.append(pregled.datum);
                    // let vremeLabel = $("<br><label>Vreme: </label>");
                    // div.append(vremeLabel.toString());
                    // div.append(pregled.vreme);
                    // let TrajanjeLabel = $("<br><label>Trajanje: </label>");
                    // div.append(TrajanjeLabel);
                    // div.append(pregled.trajanje + " min");

                    // // tr.append(datumTD);
                    // // tr.append(vremeTD);
                    // // tr.append(TrajanjeTD);
                    // // table.append(tr);

                    // let pacijentLabel = $("<br><label>Pacijent: </label>");
                    // div.append(pacijentLabel);
                    // div.append(pregled.pacijent);

                    // let tipPregledaLabel = $("<br><label>Tip pregleda: </label>");
                    // div.append(tipPregledaLabel);
                    // div.append(pregled.tipPregleda);

                    // tr.append(imeTD);
                    // tr.append(tipPregledaTd);
                    // table.append(tr);
                    // td.append("div");

                    // alert(pregled.datum)
                    // alert(getDatum(pregled.datum));
                    // counter++;
                }
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
        async: false,
    })
}

//ODREĐUJE DAN U NEDELJI NA OSNOVU DATUMA
function getDatum(datum) {
    let dan = datum.substring(0, 2);;
    let mesec = datum.substring(3, 5);
    let godina = datum.substring(6, 10);
    datum = godina + " " + mesec + " " + dan;

    var d = new Date(datum);
    var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    let prviDan = weekday[d.getDay()];
    let DO;

    switch (prviDan) {
        case "Monday":
            DO = 0;
            break;
        case "Tuesday":
            DO = 1;
            break;
        case "Wednesday":
            DO = 2;
            break;
        case "Thursday":
            DO = 3;
            break;
        case "Friday":
            DO = 4;
            break;
        case "Saturday":
            DO = 5;
            break;
        case "Sunday":
            DO = 6;
            break;
    }
    return DO;
}

function boja() {
    let color = Math.floor((Math.random() * 6) + 1);
    let boja;
    switch (color % 6) { //RANDOM BOJA POLJA
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
    return boja;
}

function iscrtaj() {
    let counter = 8;
    let cetiri = 8;
    let uslov = true;
    let brojac = 0;
    let table = $("#tbody");
    let counterIspis;
    for (let vreme = 0; vreme < 33; vreme++) {

        let tr = $("<tr></tr>");
        // uslov = true;
        // cetiri = vreme;
        for (let dan = 0; dan < 7; dan++) {
            let td;

            if (counter < 10) {
                counterIspis = "0" + counter;
            } else {
                counterIspis = counter;
            }
            let ovo = counterIspis.toString() + ":" + getVreme(cetiri);
            if (document.getElementById("td" + dan.toString() + ovo) == null) {
                td = $("<td></td>");
                td.attr("id", "td" + dan.toString() + ovo);
            } else {
                td = (document.getElementById("td" + dan.toString() + ovo));
                td.innerHTML = "";
                td.style.backgroundColor = "white";
            }





            // alert(counter.toString() + getVreme(vreme))

            if (document.getElementById("td" + dan.toString() + ovo) == null) {
                tr.append(td);
            }
        }
        if (brojac == 3 && uslov) {
            counter++;
            brojac = 0;
            uslov = false;
        }
        if ((brojac == 4 && !uslov)) {
            counter++;
            brojac = 0;
        }
        brojac++;
        cetiri++;

        table.append(tr);
    }
}

function getVreme(vreme) {
    let minuti;
    switch (vreme % 4) {
        case 0:
            minuti = "00";
            break;
        case 1:
            minuti = "15";
            break;
        case 2:
            minuti = "30";
            break;
        case 3:
            minuti = "45";
            break;
    }
    return minuti;
}