function dodajLekara() {
    	console.log("dodavanje lekara");
        var email = $('#email').val()
        var lozinka = $('#lozinka').val()
        var ime = $('#ime').val()
        var prezime = $('#prezime').val()
        //var klinika = document.getElementById("klinikaSelect");
        //var k = klinika.options[klinika.selectedIndex].value;
        //console.log(k);
        var data = JSON.stringify({
            email: $('#email').val(),
            lozinka: $('#lozinka').val(),
            ime: $('#ime').val(),
            prezime: $('#prezime').val(),
            //klinika: k
        });
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/dodajNovogLekara",
            dataType: 'json',
            data: JSON.stringify({
                email: $('#email').val(),
                lozinka: $('#lozinka').val(),
                ime: $('#ime').val(),
                prezime: $('#prezime').val(),
                //klinika: k
            }),
            complete : function () {
    			alert("Uspesno ste dodali lekara.")
    			window.location.replace("/klinicki-centar");
    		}
            
        });
}


function dodavanjeNovogLekara(){
	//postaviKlinike();
}

function postaviKlinike(){
	$.ajax({
		url: "/klinicki-centar/dobaviKlinike",
		type: "GET",
		processData: false,
		complete: function(data) {
			array = JSON.parse(data.responseText);
			var $selekt = document.getElementById("klinikaSelect");			
			for (var i = 0; i < array.length; i++) {
				console.log(array[i]);
			    var option = document.createElement("option");
			    option.value = array[i].id;
			    option.text = array[i].naziv;
			    $selekt.appendChild(option);
			}	
		}
	});	
}