$(document).ready(function(){
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/sifarnikDijagnoza/getUpdate/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#id").val(data.id);
            $("#naziv").val(data.naziv);
            $("#sifra").val(data.sifra);
            $("#opis").val(data.sifra);
        }, error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    $("#sacuvaj").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/klinicki-centar/sifarnikDijagnoza/Update",
            dataType: 'json',
            data: JSON.stringify({
                id: $("#id").val(),
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val(),
                opis: $("#opis").val()
            }),
            success: function () {
                window.location.replace("dijagnoze.html")
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    })
});
