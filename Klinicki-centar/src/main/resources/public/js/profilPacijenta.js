$(document).ready(function() {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    $.ajax({
        url: "/klinicki-centar/pacijent/getOnePacijent/" + imeParam,
        type: 'get',
        success: function(pacijent) {
            $("#ID").val(pacijent.id)
            $("#ime").val(pacijent.ime)
            $("#prezime").val(pacijent.prezime)
            $("#jmbg").val(pacijent.jmbg)
            $("#jedinstveniBr").val(pacijent.jedinstveniBrOsig)
            $("#pol").val(pacijent.pol)
            $("#email").val(pacijent.email)
            $("#grad").val(pacijent.grad)
            $("#adresa").val(pacijent.adresa)
            $("#drzava").val(pacijent.drzava)



        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });

    $("#karton").click(function() {
        window.location.replace("zdravsteniKarton.html?id=" + $("#ID").val());
    });
});