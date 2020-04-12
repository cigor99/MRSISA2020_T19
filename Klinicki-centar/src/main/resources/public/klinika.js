$(document).ready(function(){
    $('#dodajBtn').click(function(){
        // alert("Uspesno");\
        var naziv = $('#naziv').val()
        var adresa = $('#adresa').val()
        var opis = $("#opis").val()
        var data = JSON.stringify({
            naziv: $('#naziv').val(),
            adresa: $('#adresa').val(),
            opis: $("#opis").val()
        });
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/klinika",
            dataType: 'json',
            data: JSON.stringify({
                naziv: $('#naziv').val(),
                adresa: $('#adresa').val(),
                opis: $("#opis").val()
            }),
            success: function(){
                alert("uspesno!")
            }
        })
    });
});