$(document).ready(function(){
    $("#dodaj").click(function(){

        $.ajax({
            type:"post",
            url: "/klinicki-centar/sifarnikDijagnoza/addDijagnoza",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: 1,
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val(),
                opis: $("#opis").val()
            }),
            success: function(data){
                // alert("USPESNO")
                window.location.replace("dijagnoze.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    });


});