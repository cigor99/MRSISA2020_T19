$(document).ready(function() {
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var id = imeJednako.split("=")[1];
    
    let table = $("#tabela");
    let mesec = prviPut();
    iscrtaj();
    popuni();


    //POPUNJAVA TABELU SA PREGLEDIMA
    function popuni() {
        $.ajax({
            url: "/klinicki-centar/sala/getDnevniRaspored/" + id + "/" + getMesec(),
            type: 'get',
            success: function(data) {
                
                let polje;
                let prviDan = datum();
                let nedelje;


                let danPoc;
                let danKraj;
                let mesecPoc;
                let mesecKraj;
                let dan;
                let danPOLJE;
                let trenutniMesec = getMesec();
                


                for (let pregled of data) {

                    let dan = (parseInt(pregled.datum.substring(0, 2)) + parseInt(prviDan))
                    let danPOLJE = (parseInt(pregled.datum.substring(0, 2)) + parseInt(prviDan)) % 7;
                    if (danPOLJE == 0) {
                        danPOLJE = 7;
                    }
                    nedelje = Math.ceil(dan / 7);
                    if ($("#td" + nedelje.toString() + danPOLJE.toString()).html().length < 5 &&
                        window.ulogovani.id == pregled.lekarID && $("#td" + nedelje.toString() + danPOLJE.toString()).html() != "") {
                        polje = $("#td" + nedelje.toString() + danPOLJE.toString())
                        polje.empty();

                        //let color = Math.floor((Math.random() * 6) + 1);
                        //let boja;
                        /*switch (color % 6) {
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
                        }*/
                        polje.css('background-color', "darksalmon");
                        if (mesec.toString().length == 1) {
                            mesec = "0" + mesec.toString();
                        }
                        polje.append("Termin " + pregled.datum.substring(0, 2) + "." + getMesec())
                        polje.attr("onclick", "dobavi("+ id + "," + pregled.datum.substring(0, 2) + ")");
                        uslov = false;

                    }
                }
            },
            error: function(jqXHR) {
                alert(jqXHR.status);
            }
        })
    }

    //VRACA NA PRETHODNI MESEC
    $("#sledeci").click(function() {
        let polje = $("#mesec");
        let mesec;
        switch (polje.html()) {
            case "Januar":
                mesec = "Februar";
                break;
            case "Februar":
                mesec = "Mart";
                break;
            case "Mart":
                mesec = "April";
                break;
            case "April":
                mesec = "Maj";
                break;
            case "Maj":
                mesec = "Jun";
                break;
            case "Jun":
                mesec = "Jul";
                break;
            case "Jul":
                mesec = "Avgust";
                break;
            case "Avgust":
                mesec = "Septembar";
                break;
            case "Septembar":
                mesec = "Oktobar";
                break;
            case "Oktobar":
                mesec = "Novembar";
                break;
            case "Novembar":
                mesec = "Decembar";
                break;
            case "Decembar":
                mesec = "Januar";
                break;
        }
        polje.text(mesec);
        // window.location.reload();
        iscrtaj();
        popuni();

    });

    //MENJA NA SLEDECI MESEC
    $("#prethodni").click(function() {
        let polje = $("#mesec");
        let mesec;
        switch (polje.html()) {
            case "Januar":
                mesec = "Decembar";
                break;
            case "Februar":
                mesec = "Januar";
                break;
            case "Mart":
                mesec = "Februar";
                break;
            case "April":
                mesec = "Mart";
                break;
            case "Maj":
                mesec = "April";
                break;
            case "Jun":
                mesec = "Maj";
                break;
            case "Jul":
                mesec = "Jun";
                break;
            case "Avgust":
                mesec = "Jul";
                break;
            case "Septembar":
                mesec = "Avgust";
                break;
            case "Oktobar":
                mesec = "Septembar";
                break;
            case "Novembar":
                mesec = "Oktobar";
                break;
            case "Decembar":
                mesec = "Novembar";
                break;
        }
        polje.text(mesec);
        iscrtaj();
        popuni();

    });



});




//ODREDJUJE DAN OD KOG DANA POCINJE POPUNJAVANJE KALENDARA, DA LI IMA PRAZNIH POLJA NA POCETKU
function datum() {
    let mesec = getMesec();
    var d = Date.parse("2020 " + mesec + " 01");
    var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    var date = new Date(d);
    let prviDan = weekday[date.getDay()];
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

//ISCRTAVA POCETNI KALENDAR, DODAJE SIVA POLJA ZA NEPOSTOJECE DANE U TOM MESECU
function iscrtaj() {
    let tbody = $("#tbody");
    let counter = 1;
    let DO = datum();
    let mesec = getMesec();
    let tr;
    let td;
    let uslov;
    let brojac = 1;
    for (let nedelje = 1; nedelje < 6; nedelje++) {
        let tr = $("<tr></tr>");
        uslov = true;
        for (let dan = 1; dan < 8; dan++) {
            if (document.getElementById("td" + nedelje.toString() + dan.toString()) == null) {
                td = $("<td></td>");

            } else {
                uslov = false;
                td = $("#td" + nedelje.toString() + dan.toString());
                td.html("");
            }
            if (counter <= DO) {
                td.css('background-color', 'gray');
            } else {
                td.css('background-color', '#e6ffe6');

                if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
                    if (brojac > 31) {
                        brojac = -100;
                    }
                } else if (mesec == 2) {
                    if (brojac > 29) {
                        brojac = -100;
                    }
                } else {
                    if (brojac > 30) {
                        brojac = -100;
                    }
                }
                if (brojac > 0) {
                    td.append(brojac + "." + mesec);
                } else {
                    td.css('background-color', 'gray');
                }
                brojac++;
            }

            if (document.getElementById("td" + nedelje.toString() + dan.toString()) == null) {
                td.attr("id", "td" + nedelje.toString() + dan.toString());

                tr.append(td);
            }
            counter++;
        }
        if (uslov) {
            tbody.append(tr);
        }
    }
}

function brojaDanaUMesecu(mesec) {
    if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
        brojac = 31;
    } else if (mesec == 2) {
        brojac = 29;
    } else {
        brojac = 30;
    }
    return brojac;
}

//FUNKCIJA KOJA OTVARA NOVI PROZOR ZA ZADATI DATUM
function dobavi(id, dan) {
    let mesec = getMesec();
    window.open("termini.html?id=" + id.toString() + "&d=" + dan.toString() + "&m=" + mesec,
        'newwindow',
        'width=700,height=500');
}

//ZA MESEC NAPISAN RECIMA VRACA REDNI BROJ MESECA
function getMesec() {
    let mesec = $("#mesec").html();
    switch (mesec) {
        case "Januar":
            mesec = '1';
            break;
        case "Februar":
            mesec = '2';
            break;
        case "Mart":
            mesec = '3';
            break;
        case "April":
            mesec = '4';
            break;
        case "Maj":
            mesec = '5';
            break;
        case "Jun":
            mesec = '6';
            break;
        case "Jul":
            mesec = '7';
            break;
        case "Avgust":
            mesec = '8';
            break;
        case "Septembar":
            mesec = '9';
            break;
        case "Oktobar":
            mesec = '10';
            break;
        case "Novembar":
            mesec = '11';
            break;
        case "Decembar":
            mesec = '12';
            break;
    }
    return mesec;
}

function prviPut() {
    let dat = new Date();
    // alert(dat.getMonth() + 1)
    let mesec = $("#mesec").empty();
    let month = dat.getMonth() + 1
    switch (month.toString()) {
        case "1":
            mesec.append("Januar");
            break;
        case "2":
            mesec.append("Februar");
            break;
        case "3":
            mesec.append("Mart");
            break;
        case "4":
            mesec.append("April");
            break;
        case "5":
            mesec.append("Maj");
            break;
        case "6":
            mesec.append("Jun");
            break;
        case "7":
            mesec.append("Jul");
            break;
        case "8":
            mesec.append("Avgust");
            break;
        case "9":
            mesec.append("Septembar");
            break;
        case "10":
            mesec.append("Oktobar");
            break;
        case "11":
            mesec.append("Novembar");
            break;
        case "12":
            mesec.append("Decembar");
            break;
    }
    return dat.getMonth() + 1;
}