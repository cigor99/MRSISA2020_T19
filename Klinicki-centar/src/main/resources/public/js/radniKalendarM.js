$(document).ready(function() {

    let table = $("#tabela");
    let mesec = getMesec();
    iscrtaj();

    $.ajax({
        url: "/klinicki-centar/pregled/getDnevniPregled/" + mesec,
        type: 'get',
        success: function(data) {
            let polje;
            let prviDan = datum();
            let nedelje;
            let uslov;
            for (let pregled of data) {
                alert(pregled.datum);

                let dan = (parseInt(pregled.datum.substring(0, 2)) + parseInt(prviDan))
                let danPOLJE = (parseInt(pregled.datum.substring(0, 2)) + parseInt(prviDan)) % 7;
                if (danPOLJE == 0) {
                    danPOLJE = 7;
                }
                nedelje = Math.ceil(dan / 7);
                // alert(parseInt(parseInt((prviDan++) % 7)));
                if ($("#td" + nedelje.toString() + danPOLJE.toString()).html() === "") {
                    polje = $("#td" + nedelje.toString() + danPOLJE.toString())

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
                    polje.append("Pregled " + pregled.datum.substring(0, 2) + "." + mesec.toString())
                    polje.attr("onclick", "dobavi(" + pregled.datum.substring(0, 2) + ")");
                    uslov = false;
                }
            }




        },
        error: function(jqXHR) {
            alert(jqXHR.status);
        }

    })


});

function datum() {
    let mesec = getMesec();
    var d = Date.parse("2020 " + mesec + " 01");
    var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    var date = new Date(d);
    let prviDan = weekday[date.getDay()];
    alert(prviDan);
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

function iscrtaj() {
    let tbody = $("#tbody");
    // var dan;
    // let mesec;
    let counter = 1;
    // if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
    //     dan = 30;
    // } else if (mesec == 2) {
    //     dan = 29;
    // } else {
    //     dan = 31;
    // }
    let DO = datum();

    let tr;
    for (let nedelje = 1; nedelje < 6; nedelje++) {
        let tr = $("<tr></tr>");
        for (let dan = 1; dan < 8; dan++) {
            let td = $("<td></td>");
            if (counter <= DO) {
                td.css('background-color', 'gray');
            }
            // } else {
            //     td.append(counter);
            // }
            td.attr("id", "td" + nedelje.toString() + dan.toString());



            tr.append(td);
            counter++;
        }
        tbody.append(tr);
    }
}

function dobavi(dan) {
    let mesec = getMesec();
    // if (mesec.toString().length == 1) {
    //     mesec = "0" + mesec.toString();
    // }
    window.open("proba.html?d=" + dan.toString() + "&m=" + mesec,
        'newwindow',
        'width=700,height=500');
}

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