function ucitajZaglavlje() {
    //kad se napravi login treba dodati ajax zahtev koji dobavlja tip korisnika
    //var tipKorisnika = "adminKlinike";
    var tipKorisnika = "";
    window.tipKorisnika = "";

    $.ajax({
        type: "get",
        url: "/klinicki-centar/login/tipKorisnika",
        success: function(data) {
            if (data == "") {
                window.location.replace("/klinicki-centar/login.html");
                return;
            } else {
                console.log(data);
                tipKorisnika = data;
                window.tipKorisnika = data;
                ucitaj(tipKorisnika);
            }
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
        async: false,

    });


}

function ucitaj(tipKorisnika) {
    var header = "header.html";
    if (tipKorisnika == "adminKlinike") {
        header = "adminKlinikeHeader.html";
        console.log(tipKorisnika);
        $("#naslov").append("<h1>Admin klinike</h1>");
    } else if (tipKorisnika == "adminKC") {
        $("#naslov").append("<h1>Admin KC</h1>");
    } else if (tipKorisnika == "lekar") {
        $("#naslov").append("<h1>Lekar</h1>");
    } else if (tipKorisnika == "sestra") {
        header = "medicinskaSestraHeader.html";
        $("#naslov").append("<h1>Medicinska sestra</h1>");
    } else if (tipKorisnika == "pacijent") {
        $("#naslov").append("<h1>Pacijent</h1>");
        window.location.replace("/klinicki-centar/pacijent/pacijentHomePage.html");
    }
    $.ajax({
        url: header,
        method: "GET",
        complete: function(data) {
            $("#navbardiv").append(data.responseText);
        }
    });


}