function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekari/page",
        success: function (data) {
        	var table = $("#lekari")
            for (var lekar of data) {               
                let tr = $("<tr id=\"tr" + lekar.id + "\"></tr>");
                let id = $("<td>" + lekar.id + "</td>")
                let email = $("<td>" + lekar.email + "</td>")
                let ime = $("<td>" + lekar.ime + "</td>")
                let prezime = $("<td>" + lekar.prezime + "</td>")
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniLekara('${lekar.id}')">Ukloni</button></td>`)
                tr.append(id);
                tr.append(naziv);
                tr.append(tip);               
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}


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


function ukloniLekara(id) {
	
    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/getUpdate/" + id,
        success: function (data) {
            $("#email").val(data.naziv);
            $("#ime").val(data.naziv);
            $("#prezime").val(data.naziv);
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/sala/delete/" + id,
        success: function () {
            $("#tr" + id).remove();
            alert("USPESNO BRISANJE LEKARA");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
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