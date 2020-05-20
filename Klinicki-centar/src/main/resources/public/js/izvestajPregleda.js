$(document).ready(function() {
    $.ajax({
        url: "/klinicki-centar/sifarnikDijagnoza/getAll",
        type: "get",
        success: function(data) {
            for (let dijagnoza of data) {
                let input = $("#dijagnoze")
                let option = $("<option></option>")
                option.attr('value', dijagnoza.sifra)
                option.attr('id', dijagnoza.sifra)
                input.append(option);
            }
        }
    })

    $("#recept").click(function() {
        window.location.replace("recept.html")
    });
});