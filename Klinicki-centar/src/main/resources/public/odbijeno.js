$(document).ready(function () {
    var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];

    var counter = 0;
    $.ajax({
        url: "/klinicki-centar/pacijent/getOnePacijent/" + imeParam,
        type: 'get',
        success: function (data) {
            $("#email").val(data.email);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        }, async: false,
    })
    // $.ajax({
    //     url: "/klinicki-centar/pacijent/delete/" + imeParam,
    //     type: "DELETE",
    //     success: function () {
    //         // alert("OBRISAO")
    //     }
    // })


    $("#posalji").click(function () {
        counter = counter + 1;
        posalji()
    });
    
    window.onbeforeunload = function() {
        posalji("Za više informacija pozovite 021/800-100")
     };

     function posalji(poruka){
         var mes = $("#razlog").val();
         if(mes == ""){
             mes = poruka;
         }
        $.ajax({
            dataType: 'json',
            url: "/klinicki-centar/ZZR/sendEmail",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({
                email: $("#email").val(),
                message: mes,
                subject: "Razlog odbijanja zahteva za registraciju na klinički centar"
            }),
            success: function () {
                alert("USPESNO STE POSLALI EMAIL")
                window.close()
            }, error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        })
     }

});