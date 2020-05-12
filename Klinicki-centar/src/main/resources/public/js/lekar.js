function ucitajTabelu() {
    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
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
                tr.append(email);
                tr.append(ime);
                tr.append(prezime);               
                tr.append(ukloni);
                table.append(tr);
            }

        }
    });
}

function pretraga(){
	var trazi = $('#trazi').val();
	console.log(trazi);
	if(trazi==""){
		return;
	}
	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/lekar/search",
        dataType: 'json',
        data: trazi,
        success : function (lekari) {
        	console.log(lekari);
        	var table = $("#lekari");
        	$("#table_body").empty();
        	//table.empty();
            for (var lekar of lekari) {               
                let tr = $("<tr id=\"tr" + lekar.id + "\"></tr>");
                let id = $("<td>" + lekar.id + "</td>")
                let email = $("<td>" + lekar.email + "</td>")
                let ime = $("<td>" + lekar.ime + "</td>")
                let prezime = $("<td>" + lekar.prezime + "</td>")
                let ukloni = $(`<td><button  type="button" id="ukloniBtn" onclick="ukloniLekara('${lekar.id}')">Ukloni</button></td>`)
                tr.append(id);
                tr.append(email);
                tr.append(ime);
                tr.append(prezime);               
                tr.append(ukloni);
                table.append(tr);
            }

		},
        
    });
} 

function validacija(){
	//$("#nazivError").css('visibility', 'hidden');
    //$("#tipError").css('visibility', 'hidden');        

    if ($("#ime").val() == "") {
        $("#imeError").text("Ime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if ($("#prezime").val() == "") {
        $("#prezimeError").text("Prezime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }
    if ($("#email").val() == "") {
        $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
        return;
    }

    
}

function dodajLekara() {

	    if ($("#ime").val() == "") {
	        $("#imeError").text("Ime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
	    else{
	    	$("#imeError").css('visibility', 'hidden');
	    }
	    if ($("#prezime").val() == "") {
	        $("#prezimeError").text("Prezime je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
	    else{
	    	$("#prezimeError").css('visibility', 'hidden');
	    }
	    if ($("#email").val() == "") {
	        $("#emailError").text("Email je obavezno polje!").css('visibility', 'visible').css('color', 'red');
	        return;
	    }
	    else{
	    	$("#emailError").css('visibility', 'hidden');
	    }
	    if($("#lozinka").val() != $("#lozinkaPonovo").val()){
	        $("#lozinkaError").text("Lozinke se ne poklapaju!").css('visibility', 'visible').css('color', 'red');	
	        return;
	    }
	    else{
	    	$("#lozinkaError").css('visibility', 'hidden');
	    }
    	console.log("dodavanje lekara");
        var email = $('#email').val()
        var lozinka = $('#lozinka').val()
        var jmbg = $('#jmbg').val()
        var ime = $('#ime').val()
        var prezime = $('#prezime').val()
        //var klinika = document.getElementById("klinikaSelect");
        //var k = klinika.options[klinika.selectedIndex].value;
        //console.log(k);
        var data = JSON.stringify({
            email: $('#email').val(),
            lozinka: $('#lozinka').val(),
            jmbg: $('#jmbg').val(),
            ime: $('#ime').val(),
            prezime: $('#prezime').val(),
            //klinika: k
        });
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/klinicki-centar/lekar/add",
            dataType: 'json',
            data: JSON.stringify({
                email: $('#email').val(),
                lozinka: $('#lozinka').val(),
                jmbg: $('#jmbg').val(),
                ime: $('#ime').val(),
                prezime: $('#prezime').val(),
                //klinika: k
            }),
            success : function () {
    			alert("Uspesno ste dodali lekara.")
    			window.location.replace("/klinicki-centar/medicinskoOsoblje.html");
    		},
    		error: function() {
    			alert("Email adresa nije dostupna!");
    		},
            
        });
}


function ukloniLekara(id) {
	
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/lekar/delete/" + id,
        success: function () {
            $("#tr" + id).remove();
            alert("USPESNO BRISANJE LEKARA");
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


/*function dodavanjeNovogLekara(){
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
}*/