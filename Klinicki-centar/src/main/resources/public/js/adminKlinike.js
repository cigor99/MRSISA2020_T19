function profilKlinike(){
	$.ajax({
        type: "get",
        url: "/klinicki-centar/klinika/getCurrent",
        success: function (id) {        	        	
        	window.location.replace("/klinicki-centar/profilKlinike.html?id=" + id);        		
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}