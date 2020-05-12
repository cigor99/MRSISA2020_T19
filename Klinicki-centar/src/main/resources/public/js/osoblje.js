$(document).ready(function() {
	$("#filtriraj").click(function() {
    	console.log("filter");
    	$("#ROWDIV").empty();
    	var selected = document.getElementById("osobljeSelect");
    	var filter = selected.options[selected.selectedIndex].value;
    	if(filter == "lekar"){
    		$.ajax({
    	        type: "get",
    	        url: "/klinicki-centar/lekar/page",
    	        success: function (data) {
    	        	window.search = false;
    	            window.filter = false;
    	            $("#stranice").css('visibility', 'visible');
    	        	kartice(data, "lekar");
    	        },
    	        error: function(jqXHR) {
    	            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
    	        },	
    	    });
    	}
    	else if(filter == "sestra"){
    		$.ajax({
    	        type: "get",
    	        url: "/klinicki-centar/medicinskaSestra/page",
    	        success: function (data) {
    	        	window.search = false;
    	            window.filter = false;
    	            $("#stranice").css('visibility', 'visible');
    	        	kartice(data, "sestra");
    	        },
    	        error: function(jqXHR) {
    	            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
    	        },
    	    });
    	}
    });

    $("#dugme").click(function() {
        pretraga();
    });
});

function ucitajOsoblje(){
	window.podaci;
    $('#idemo').checked = true;
    
	$.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
        success: function (data) {
        	window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible');
        	kartice(data, "lekar");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },	
    });
	$.ajax({
        type: "get",
        url: "/klinicki-centar/medicinskaSestra/page",
        success: function (data) {
        	window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible');
        	kartice(data, "sestra");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
}

function pretraga() {
    $("#error").css('visibility', 'hidden')
    if ($("#search").val() == "") {
        $("#error").text("Polje pretrage ne sme biti prazno.").css("visibility", 'visible').css('color', 'red');
        return;
    }
    console.log($("#search").val())
    $("#ROWDIV").empty();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/lekar/search",
        dataType: 'json',
        data: $("#search").val(),
        success : function (lekari) {
        	 kartice(lekari, "lekar");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/medicinskaSestra/search",
        dataType: 'json',
        data: $("#search").val(),
        success : function (sestre) {
        	 kartice(sestre, "sestra");
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },
    });
}

function kartice(data, x){
	
    $("#ROWDIV").css("visibility", 'visible')
    let counter = 0;

    for (var lekar of data) {
        let row = $(".row");
        let col = $("<div></div>");
        col.attr("class", 'col-md-4');
        row.append(col);
        let well = $("<div></div>");
        well.attr("class", 'well');
        well.attr("id", '${lekar.id}');
        col.append(well);
        let img = $("<img></img>");
        img.attr("class", 'avatar')
        img.attr("src", 'lekar.png');
        if(x == "lekar"){
        	var h4 = $("<h4>Lekar</h4>");
        }
        else{
        	var h4 = $("<h4>Medicinska Sestra</h4>");
        }
        let ime = $("<p></p>");
        ime.append("<strong>Ime: </strong>");
        ime.append(lekar.ime);
        let prezime = $("<p></p>");
        prezime.append("<strong>Prezime: </strong>");
        prezime.append(lekar.prezime);
        let ocena = $("<div></div>");
        ocena.attr("class", 'ocena');
        ocena.append(4.7);
        let zvezda = $("<img></img>");
        zvezda.attr("src", 'zvezda.png');
        ocena.append(zvezda);
        //<img src="zvezda.png" alt="">

        let ul = $("<ul></ul>")
        ul.attr("class", 'bottom')
        let karton = $("<li></li>")
        karton.attr("class", 'del');
        let a = $("<a>Profil</a>")
        a.attr("class", 'btn');
        if(x == "lekar"){
        	a.attr("href", 'profilLekara.html?id=' + lekar.id);
        }
        else{
        	a.attr("href", 'profilMedSestre.html?id=' + lekar.id)
        }
        
            // let pregled = $("<li></li>")
            // pregled.attr('class', 'del');
            // let a2 = $("<a>Započni pregled</a>");
            // a2.attr("class", 'btn');
            // pregled.append(a2);
            // ul.append(pregled);
        let del = $("<button>Obriši</button>");  //type="button" id="ukloniBtn" onclick="ukloniLekara('${lekar.id}')">Ukloni</button>")
        //del.attr("class", 'obrisi'); 
        del.attr("id", "ukloniBtn");
        if(x == "lekar"){
        	del.attr("onclick", 'ukloniLekara('+lekar.id+')');
        }
        else{
        	del.attr("onclick", 'ukloniSestru('+lekar.id+')');
        }
        
        karton.append(del);
        let karton2 = $("<li></li>")
        karton2.attr("class", 'fb');
        karton2.append(a);
        //karton.append(a);
        ul.append(karton2);
        ul.append(karton);
        //ul.append(a);
        /*<ul class="bottom">
                       <li class="fb"><a href="#" class="btn">Izmeni profil</a></li>
                        <li class="getPosts"><a href="#" class="btn">Izmeni kliniku</a></li>
                        <li class="del"><a href="#" class="btn">Obrisi</a></li>
                    </ul>*/

        well.append(img);
        well.append(h4);
        well.append(ime);
        well.append(prezime);
        well.append(ocena);
        well.append(ul);
    }
    counter = counter + 1;
}

function ukloniLekara(id) {
	
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/lekar/delete/" + id,
        success: function () {
            $("#div" + id).remove();
            alert("USPESNO BRISANJE LEKARA");
            location.reload();
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


function ukloniSestru(id) {
	
    $.ajax({
        type: "DELETE",

        url: "/klinicki-centar/medicinskaSestra/delete/" + id,
        success: function () {
            $("#div" + id).remove();
            alert("USPESNO BRISANJE MEDICINSKE SESTRE");
            location.reload();
        },
        error: function (jqXHR) {
            alert("Error: " + jqXHR.status + " " + jqXHR.responseText);
        },
    });
}


$('#idemo').change(function() {
    if (window.search == false && window.filter == false) { //
        $("#stranice").css('visibility', 'visible')
        if ($("#prviBr").hasClass("strong")) {
            dobavi(parseInt($("#prviBr").text()) - 1, 6)
        } else if ($("#drugiBr").hasClass("strong")) {
            dobavi(parseInt($("#drugiBr").text()) - 1, 6)
        } else if ($("#treciBr").hasClass("strong")) {
            dobavi(parseInt($("#treciBr").text()) - 1, 6)
        }
    }
    if (window.search == true) { //if (window.search == true) 

        /*$.ajax({
            url: "/klinicki-centar/lekar/search/" + $("#kriterijum option:selected").text() + "/" + $("#search").val(),
            type: "post",
            success: function(data) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
                $("#treciBr").css('visibility', 'hidden');
                if (window.podaci == undefined) {
                    window.podaci = data;
                } else {
                    let stari = window.podaci;
                    window.podaci = []
                    for (let novi of data) {
                        for (let star of stari) {
                            if (star.id == novi.id) {
                                window.podaci.push(star);
                            }
                        }
                    }
                }

                window.search = true;
                // window.filter = false;
                $("#stranice").css('visibility', 'hidden');
                $("#tabela").css('visibility', 'hidden');
                $("#ROWDIV").empty();
                $("#tabela").empty();
                if ($("#idemo").is(":checked") == true) {
                    tabela(window.podaci);

                } else {
                    kartice(window.podaci);
                }
            }
        });
    }*/
    // if (window.filter == false) {
    //     $("#stranice").css('visibility', 'visible')
    //     if ($("#prviBr").hasClass("strong")) {
    //         dobavi(parseInt($("#prviBr").text()) - 1, 6)
    //     } else if ($("#drugiBr").hasClass("strong")) {
    //         dobavi(parseInt($("#drugiBr").text()) - 1, 6)
    //     } else if ($("#treciBr").hasClass("strong")) {
    //         dobavi(parseInt($("#treciBr").text()) - 1, 6)
    //     }
    // }
    if (window.filter == true) {
    	$("#ROWDIV").empty();
    	var selected = document.getElementById("osobljeSelect");
    	var filter = tipSelected.options[selected.selectedIndex].value;
    	if(filter == "lekar"){
    		$.ajax({
    	        type: "get",
    	        url: "/klinicki-centar/lekar/page",
    	        success: function (data) {
    	        	window.search = false;
    	            window.filter = false;
    	            $("#stranice").css('visibility', 'visible');
    	        	kartice(data, "lekar");
    	        },
    	        error: function(jqXHR) {
    	            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
    	        },	
    	    });
    	}
    	else if(filter == "sestra"){
    		$.ajax({
    	        type: "get",
    	        url: "/klinicki-centar/medicinskaSestra/page",
    	        success: function (data) {
    	        	window.search = false;
    	            window.filter = false;
    	            $("#stranice").css('visibility', 'visible');
    	        	kartice(data, "sestra");
    	        },
    	        error: function(jqXHR) {
    	            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
    	        },
    	    });
    	}
        /*$.ajax({
            url: "/klinicki-centar/lekari/filter/" + filter,
            type: "post",
            success: function(data) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
                $("#treciBr").css('visibility', 'hidden');
                if (window.podaci == undefined) {
                    window.podaci = data;
                } else {
                    let stari = window.podaci;
                    window.podaci = []
                    for (let novi of data) {
                        for (let star of stari) {
                            if (star.id == novi.id) {
                                window.podaci.push(star);
                            }
                        }
                    }
                }
                // window.search = false;
                window.filter = true;
                $("#stranice").css('visibility', 'hidden');
                $("#tabela").css('visibility', 'hidden');
                $("#ROWDIV").empty();
                $("#tabela").empty();
                if ($("#idemo").is(":checked") == true) {
                    tabela(window.podaci);

                } else {
                    kartice(window.podaci);
                }
            }*/
        }
    }
});




