$(document).ready(function() {
    iscrtaj();
    var d = new Date();
    pronalazenjeDatuma(d);

    godisnji(d);


    $("#primeni").click(function() {
        let d = new Date($("#datum").val());
        iscrtaj();
        pronalazenjeDatuma(d);
        godisnji(d);
    });
    $("#godisnji").click(function() {
        window.location.replace("radniKalendarG.html");
    });

    $("#mesecni").click(function() {
        window.location.replace("radniKalendarM.html");
    });


});

function godisnji(d) {
    $.ajax({
        url: "/klinicki-centar/ZZG/getZahtev",
        type: "get",
        success: function(data) {
            let danPoc;
            let danKraj;
            let mesecPoc;
            let mesecKraj;
            let godPocetka;
            let godKraj;
            for (let zahtev of data) {
                godPocetka = zahtev.pocetniDatum.substring(0, 4);
                mesecPoc = zahtev.pocetniDatum.substring(5, 7);
                danPoc = zahtev.pocetniDatum.substring(8, 10);
                godKraj = zahtev.krajnjiDatum.substring(0, 4);
                mesecKraj = zahtev.krajnjiDatum.substring(5, 7);
                danKraj = zahtev.krajnjiDatum.substring(8, 10);


                let nedelja = getNedelja(d);

                let pocetak = getDatum(danPoc + " " + mesecPoc + " " + godPocetka);
                let kraj = getDatum(danKraj + " " + mesecKraj + " " + godKraj);
                let razlika = []
                let counter = 0;
                let dat1 = parseInt(danPoc) + counter;
                for (let index = pocetak; index <= kraj; index++) {

                    dat1 = daniUMesecu(mesecPoc, dat1);

                    if (jQuery.inArray(dat1, razlika) == -1) {
                        razlika.push(dat1);
                    }

                    dat1++;
                }

                dat1 = parseInt(danPoc) + counter;
                if (pocetak > kraj) {
                    for (let index = pocetak; index <= 6; index++) {

                        dat1 = daniUMesecu(mesecPoc, dat1);

                        if (jQuery.inArray(dat1, razlika) == -1) {
                            razlika.push(dat1);
                        }
                        dat1++;
                    }
                    dat1 = parseInt(danPoc) + counter;
                    for (let index = 0; index <= kraj; index++) {

                        dat1 = daniUMesecu(mesecPoc, dat1);

                        if (jQuery.inArray(dat1, razlika) == -1) {
                            razlika.push(dat1);
                        }
                        dat1++;
                    }
                }
                // counter = 0;

                // alert(razlika)
                for (let dat of razlika) {
                    for (const [key, value] of Object.entries(nedelja)) {

                        if (key == dat.toString() && (d.getMonth() + 1 == parseInt(mesecPoc) || d.getMonth() == parseInt(mesecKraj))) {
                            let polje = $("[id^=" + "td" + nedelja[key] + "]");
                            polje.css("background-color", "red");
                        }
                    }
                }




            }
        }
    })
}

function daniUMesecu(mesecPoc, dat1) {
    if (mesecPoc == 1 || mesecPoc == 3 || mesecPoc == 5 || mesecPoc == 7 || mesecPoc == 8 || mesecPoc == 10 || mesecPoc == 12) {
        if (dat1 > 31) {
            dat1 = 1;
        }
    } else if (mesecPoc == 2) {
        if (dat1 > 29) {
            dat1 = 1;
        }
    } else {
        if (dat1 > 30) {
            dat1 = 1;
        }
    }
    return dat1;
}

//za izabrani datum pronalazi preostale datume iz iste nedelje
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
    for (var key in dict) {
        dobavi(key, dict, d);
    }
}


//FUNKCIJA VRACA REČNIK, KLJUČ JE DATUM, VREDNOST JE POLJE U TABELI (DAN U NEDELJI)
function getNedelja(d) {

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
    let dat = parseInt(d.getDate());

    for (let index = danas; index <= 6; index++) {
        if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
            if (dat > 31) {
                dat = 1;
            }
        } else if (mesec == 2) {
            if (dat > 29) {
                dat = 1;
            }
        } else {
            if (dat > 30) {
                dat = 1;
            }
        }
        dict[dat] = index;
        dat++;
    }

    let brojac = 0;
    for (let index = danas; index > 0; index--) {
        if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
            if (dat > 31) {
                dat = 1;
            }
        } else if (mesec == 2) {
            if (dat > 29) {
                dat = 1;
            }
        } else {
            if (dat > 30) {
                dat = 1;
            }
        }
        dict[d.getDate() - index] = brojac;
        brojac++;
    }

    return dict;
}

//DOBAVLJA PODATKE PREGLEDA
function dobavi(key, dict, d) {
    $.ajax({
        url: "/klinicki-centar/pregled/getDnevniPregled/" + key + "/" + parseInt(d.getMonth() + 1),
        type: 'get',
        success: function(data) {
            if (window.tipKorisnika == "lekar") {
                for (let pregled of data) {
                    if (window.ulogovani.id == pregled.lekarID) {
                        let td = document.getElementById("td" + dict[key] + pregled.vreme);
                        td.style.backgroundColor = boja();
                        td.innerHTML = "<div><label> Datum:" + pregled.datum + " </label><br><label>Vreme: </label>" + pregled.vreme + "<br><label>Trajanje: </label>" + pregled.trajanje + " min<br><label>Pacijent: </label>" + pregled.pacijent + "<br><label>Tip pregleda: </label>" + pregled.tipPregleda + " </div > ";
                    }
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

//ODREĐUJE RANDOM BOJU ZA POLJE U TABELI
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
            boja = "darksalmon";
            break;
        case 3:
            boja = "bisque";
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

//ISCRTAVA POLJA POLJA TABELE
function iscrtaj() {
    let counter = 8;
    let cetiri = 8;
    let uslov = true;
    let brojac = 0;
    let table = $("#tbody");
    let counterIspis;
    for (let vreme = 0; vreme < 33; vreme++) {

        let tr = $("<tr></tr>");
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
                td.style.backgroundColor = "#e6ffe6";
            }

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

//VRACE VREME U VREMENSKIM INTERVALIMA OD 15 MINUTA
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