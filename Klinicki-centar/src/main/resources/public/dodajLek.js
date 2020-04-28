$(document).ready(function () {
    $("#dodaj").click(function () {
        $.ajax({
            url: "/klinicki-centar/sifarnikLekova/addLek",
            type: "post",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                id: "1",
                naziv: $("#naziv").val(),
                sifra: $("#sifra").val()
            }),
            success: function (data) {
                // $.ajax({
                //     url:"",
                //     type:"",
                
                // });

                window.location.replace("sifarnikLekova.html")
            },
            error: function (jqXHR) {
                alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
            },

        });
    });
});