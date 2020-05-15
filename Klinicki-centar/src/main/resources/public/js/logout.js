function odjava() {
    console.log("logout");
    $.ajax({
        type: "get",
        url: "/klinicki-centar/login/logout",
        success: function(data) {
            window.location.replace("/klinicki-centar/login.html");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}