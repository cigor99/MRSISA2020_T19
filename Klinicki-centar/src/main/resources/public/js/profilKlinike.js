$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/klinika/getKlinika/" + imeParam,
        type: 'get',
        success: function(klinika) {
            $("#ID").val(klinika.id)
            $("#naziv").val(klinika.naziv)
            $("#adresa").val(klinika.adresa)
            $("#opis").val(klinika.opis)
            $("#ocena").val(klinika.prosecnaOcena)
            window.nazivKlinike = klinika.naziv;
            window.adresaKlinike = klinika.adresa;
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    $("#izmenaKlinike").click(function() {
        window.location.replace("/klinicki-centar/updateKlinike.html?id=" + $("#ID").val());
    });
});