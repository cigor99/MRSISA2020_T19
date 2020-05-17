$(document).ready(function () {
	var klinika = null;
	var imeCoded = window.location.href.split("?")[1];
    var imeJednako = imeCoded.split("&")[0];
    var imeParam = imeJednako.split("=")[1];
    if(imeParam == undefined){
    	alert("Morate prvo izabrati kliniku")
    	window.location.replace("/klinicki-centar/pretragaKlinika.html");
    }else{
    	$.ajax({
    		type: "get",
    		url: "/klinicki-centar/klinika/getUpdate/" + imeParam,
    		success: function(data){
    			klinika = data;
    			//console.log(klinika);
    		},
    		error: function(data){
    			alert("error in get klinika");
    		},
    		async:false
    	});
    	//console.log(klinika)
    }
	//console.log(klinika)
	$.ajax({
		url: "/klinicki-centar/login/tipKorisnika",
        type: "get",
        success: function(data) {
        	window.tipKorisnika = data
        	console.log(data)
        },
        error: function(data){
        	alert("error getTipKorisnika")
        },
        async: false
	});
	
	if(window.tipKorisnika != "pacijent"){
		alert("Ovo je samo za pacijenta")
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
	}else{ 
	
		$.ajax({
		        url: "/klinicki-centar/login/getLoggedUser",
		        type: "get",
		        success: function(data) {
		            window.ulogovani = data;
		            
		            var naslov = $("#naslov");
		        	naslov.empty();
		    		naslov.append('<h1>'+window.ulogovani.ime+" " + window.ulogovani.prezime+'</h1>');
		    		naslov.append('<h1>Lekari</h1>');
		        }
		 });
	}
	
	window.podaci;
    $('#idemo').checked = true;
    $.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/pageForPacijent/" + 0 + "/" + 6 + "/" + klinika.id,
        success: function(data) {
            window.search = false;
            window.filter = false;
            $("#stranice").css('visibility', 'visible')
            kartice(data);
        },
        error: function(jqXHR) {
            alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
        },

    });

    
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

            $.ajax({
                url: "/klinicki-centar/lekar/searchLekaraForPacijent/" + $("#kriterijum").val() + "/"+$("#search").val() + "/" + klinika.id,
                type: "post",
                success: function(data) {
                	console.log(data);
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
        }
    });

    function kartice(data) {
        if (!$('#tabela').is(':empty')) {
            $("#tabela").empty()
            $("#tabela").css("visibility", 'hidden')
        }
        if ($("#ROWDIV").is(':empty')) {
            $("#ROWDIV").css("visibility", 'visible')
            let counter = 0;

            for (let lekar of data) {
                let row = $(".row");
                let col = $("<div></div>");
                col.attr("class", 'col-md-4');
                row.append(col);
                let well = $("<div></div>");
                well.attr("class", 'well');
                col.append(well);
                let img = $("<img></img>");
                img.attr("class", 'avatar')
                img.attr("src", '../lekar.png');
                let h4 = $("<h4>Lekar</h4>");
                let ime = $("<p></p>");
                ime.append("<strong>Ime: </strong>");
                ime.append(lekar.ime);
                let prezime = $("<p></p>");
                prezime.append("<strong>Prezime: </strong>");
                prezime.append(lekar.prezime);
                let email = $("<p></p>");
                email.append("<strong>Email: </strong>");
                email.append(lekar.email);
                let ocena = $("<div></div>");
                ocena.attr("class", 'ocena');
                ocena.append(lekar.prosecnaOcena);
                let zvezda = $("<img></img>");
                zvezda.attr("src", '../zvezda.png');
                ocena.append(zvezda);

                let ul = $("<ul></ul>")
                ul.attr("class", 'bottom')
                let karton = $("<li></li>")
                karton.attr("class", 'del');
                let a = $("<a>Profil</a>")
                a.attr("class", 'btn');
                a.attr("href", 'profilLekara.html?id=' + lekar.id +"&klinika=" + klinika.id)
                
                karton.append(a);
                ul.append(karton)

                well.append(img);
                well.append(h4);
                well.append(ime);
                well.append(prezime);
                well.append(email);
                well.append(ocena);
                well.append(ul);
            }
            counter = counter + 1;
        }
    }

    function tabela(data) {
        if (!$('#ROWDIV').is(':empty')) {
            $("#ROWDIV").empty()
            $("#ROWDIV").css("visibility", 'hidden')
        }
        if ($('#tabela').is(':empty')) {
            var div = $("#tabela")
            div.css("visibility", 'visible')
            let table = $("<table></table>")
            table.attr("class", 'table')
            table.attr("id", "#pacijenti")
            let imeTd = $("<td>Ime</td>")
            let prezimeTd = $("<td>Prezime</td>")
            let emailTd = $("<td>Email</td>")
            let ocenaTd = $("<td>Ocena</td>")
            let profilTD = $("<td>Profil leakra</td>")
                // let pregledTD = $("<td>Započni pregled</td>")
            let head = $("<thead></thead>")
            let trHead = $("<tr></tr>")
            head.append(trHead)

            trHead.append(imeTd);
            trHead.append(prezimeTd);
            trHead.append(emailTd);
            trHead.append(ocenaTd);
            trHead.append(profilTD);
            // trHead.append(pregledTD);
            table.append(head);
            let tbody = $("<tbody></tbody>")
            table.append(tbody);
            div.append(table);
            for (let lekar of data) {
                console.log(lekar)

                let tr = $("<tr id=\"tr" + lekar.id + "\"></tr>")

                // let idTD = $("<td>" + pacijent.id + "</td>")
                let imeTD = $("<td>" + lekar.ime + "</td>")
                let prezimeTD = $("<td>" + lekar.prezime + "</td>")
                let emailTD = $("<td>" + lekar.email + "</td>")
                   
                let ocenaTD = $("<td>" + lekar.prosecnaOcena + "</td>")

                // let TDkarton = $("<td>" + "<a href=\"pacijentProfil.html?id=" + pacijent.id + "\">Karton</a></td>")
                let TDprofil = $("<td></td>")
                let profil = $("<a>Profil lekara</a>")
                profil.attr("href", 'profilLekara.html?id=' + lekar.id +"&klinika=" + klinika.id)
                TDprofil.append(profil);

                // tr.append(idTD)
                tr.append(imeTD)
                tr.append(prezimeTD)
                    // tr.append(polTD)
                tr.append(emailTD)
                tr.append(ocenaTD)
                tr.append(TDprofil)
                tbody.append(tr)
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    //ZA ISPISIVANJE BROJA STRANICA
    //////////////////////////////////////////////////////////////////////
    let brojLekara;
    let brStr;
    $.ajax({
        type: "post",
        url: "/klinicki-centar/lekar/all" + "/" + klinika.id,
        success: function(data) {
            $("#stranice").css('visibility', 'visible')
            let div = $("#stranice")
            $("#prviBr").attr("class", 'strong')
            lekara = data.length;
            brStr = parseInt(brojLekara / 6);
            if (brojLekara % 6 > 0 && brojLekara > 6) {
                brStr = brStr + 1;
            }
            if (brStr == 0) {
                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);

            }
            if (brStr == 2) {
                let drugiBr = $("<a>2</a>");
                drugiBr.attr("id", 'drugiBr');
                div.append(drugiBr);

                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);

            } else if (brStr >= 3) {
                let drugiBr = $("<a>2</a>");
                drugiBr.attr("id", 'drugiBr');
                div.append(drugiBr);
                let treciBr = $("<a>3</a>");
                treciBr.attr("id", 'treciBr');
                div.append(treciBr);
                let sledeca = $("<a>»</a>")
                sledeca.attr('id', 'sledeci')
                let poslednja = $("<a>Poslednja</a>")
                poslednja.attr('id', 'poslednja')
                div.append(sledeca);
                div.append(poslednja);
            }
        },
        error: function(response) {
            alert("Error when pacijent/page called")
        },
        async: false,

    });



    $("#sledeci").click(function() {
        $("#treciBr").css('visibility', 'visible');
        if ($("#prviBr").hasClass("strong") && brStr == 0) {
            return;
        }
        if ($("#prviBr").hasClass("strong")) {
            $("#prviBr").removeClass('strong');
            $("#drugiBr").attr('class', 'strong');
            $("#ROWDIV").empty();
            $("#tabela").empty()
            dobavi(parseInt($("#drugiBr").text()) - 1, 6)

            if (parseInt($("#drugiBr").text()) == brStr) {
                $("#sledeci").css('visibility', 'hidden');
                $("#poslednja").css('visibility', 'hidden');
            }
            return;
        }
        let counter = 0;
        for (let str of $('div#stranice').children()) {
            if (counter == 2) {
                if (parseInt(str.text) + 3 <= brStr) {
                    str.text = parseInt(str.text) + 1
                }
            } else if (counter == 3) {
                if (parseInt(str.text) + 2 <= brStr) {
                    str.text = parseInt(str.text) + 1
                    if ($("#drugiBr").hasClass("strong")) {
                        $("#ROWDIV").empty();
                        $("#tabela").empty()
                        dobavi(parseInt(str.text) - 1, 6);
                    }
                }
            } else if (counter == 4) {
                if (parseInt(str.text) + 1 <= brStr) {
                    str.text = parseInt(str.text) + 1
                } else {
                    $("#prviBr").text($("#drugiBr").text());
                    $("#drugiBr").removeClass('strong');
                    $("#drugiBr").text($("#treciBr").text());
                    $("#drugiBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty();
                    dobavi(parseInt($("#drugiBr").text()) - 1, 6)
                    $("#treciBr").css('visibility', 'hidden');
                    $("#sledeci").css('visibility', 'hidden');
                    $("#poslednja").css('visibility', 'hidden');
                }
            }

            counter = counter + 1;
        }
    });

    $("#prethodni").click(function() {
        if (brStr == 2) {
            if ($("#prviBr").hasClass("strong")) {
                return;
            } else if ($("#drugiBr").hasClass("strong")) {
                $("#drugiBr").removeClass('strong');
                $("#prviBr").attr('class', 'strong');
                $("#ROWDIV").empty();
                $("#tabela").empty()
                dobavi(parseInt($("#prviBr").text()) - 1, 6)
                $("#sledeci").css('visibility', 'visible');
                $("#poslednja").css('visibility', 'visible');
            }
        }
        if ($("#treciBr").css("visibility") == "hidden") {
            if (brStr >= 3) {
                $("#treciBr").text(parseInt($("#drugiBr").text()) + 1);
                $("#treciBr").attr('class', 'strong');
                // $("#drugiBr").text($("#prviBr").text());
                $("#drugiBr").removeClass('strong');
                // $("#prviBr").text(parseInt($("#prviBr").text()) - 1);

            }
            $("#treciBr").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#sledeci").css('visibility', 'visible');
        }

        let counter = 0;
        for (let str of $('div#stranice').children()) {
            // if (str.text != "0" && counter == 0) {
            if (counter == 2) {
                if (parseInt(str.text) - 1 > 0) {
                    str.text = parseInt(str.text) - 1
                } else if ($("#drugiBr").hasClass("strong")) {
                    $("#drugiBr").removeClass('strong');
                    $("#prviBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty()
                    dobavi(parseInt($("#prviBr").text()) - 1, 6)

                }
            } else if (counter == 3) {
                if (parseInt(str.text) - 1 > 1) {
                    str.text = parseInt(str.text) - 1
                    if ($("#drugiBr").hasClass("strong")) {
                        $("#ROWDIV").empty();
                        $("#tabela").empty()
                        dobavi(parseInt(str.text) - 1, 6);
                    }
                }
            } else if (counter == 4) {
                if (parseInt(str.text) - 1 > 2) {
                    str.text = parseInt(str.text) - 1
                }
                if ($("#treciBr").hasClass("strong")) {
                    $("#treciBr").removeClass('strong');
                    $("#drugiBr").attr('class', 'strong');
                    $("#ROWDIV").empty();
                    $("#tabela").empty()
                    dobavi(parseInt($("#drugiBr").text()) - 1, 6)
                }
            }
            // }

            counter = counter + 1;
        }
    });

    // $("#prva").click(function() {
    //     // for (let str of $('div#stranice').children()) {
    //     $("#prviBr").text("1");
    //     $("#prviBr").attr("class", 'strong');
    //     $("#drugiBr").text("2");
    //     $("#drugiBr").removeClass("strong")
    //     $("#treciBr").text("3");
    //     $("#treciBr").removeClass("strong")
    //     $("#sledeci").css('visibility', 'visible');
    //     $("#poslednja").css('visibility', 'visible');
    //     $("#treciBr").css('visibility', 'visible');

    // });

    $("#poslednja").click(function() {
        if ($("#prviBr").hasClass("strong") && brStr == 0) {
            return;
        }

        $("#prviBr").removeClass('strong');
        $("#treciBr").removeClass("strong");
        $("#prviBr").text(brStr - 1);
        $("#drugiBr").text(brStr);
        $("#drugiBr").attr("class", 'strong');
        $("#treciBr").css('visibility', 'hidden');
        $("#sledeci").css('visibility', 'hidden');
        $("#poslednja").css('visibility', 'hidden');
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#drugiBr").text()) - 1, 6)

    });


    //////////////////////////////////////////////////////////////////////
    function dobavi(od, dokle) {
        if ($("#idemo").is(":checked") == true) {
            $.ajax({
                type: "get",
                url: "/klinicki-centar/lekar/pageForPacijent/" + od + "/" + dokle + "/" + klinika.id,
                success: function(data) {
                    tabela(data);
                },
                error: function(jqXHR) {
                    alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
                }
            });
            return;
        }
        $.ajax({
            type: "get",
            url: "/klinicki-centar/lekar/pageForPacijent/" + od + "/" + dokle + "/" + klinika.id,
            success: function(data) {
                kartice(data);
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
            }
        });
        return;
    }

    //////////////////DUGMAD///////////////////////////////////////

    $("#prviBr").click(function() {
        $("#prviBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#prviBr").text()) - 1, 6)
        $("#sledeci").css('visibility', 'visible');
        $("#poslednja").css('visibility', 'visible');
        $("#treciBr").css('visibility', 'visible');
    });

    $("#drugiBr").click(function() {
        $("#drugiBr").attr('class', 'strong');
        $("#prviBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#drugiBr").text()) - 1, 6)
        if (brStr == 2) {
            $("#sledeci").css('visibility', 'hidden');
            $("#poslednja").css('visibility', 'hidden');
            $("#treciBr").css('visibility', 'hidden');
        } else if (brStr > 2) {
            $("#sledeci").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#treciBr").css('visibility', 'visible');
        }

    });

    $("#treciBr").click(function() {
        $("#treciBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#prviBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#treciBr").text()) - 1, 6)
        if (brStr == 3) {
            $("#sledeci").css('visibility', 'hidden');
            $("#poslednja").css('visibility', 'hidden');
            $("#treciBr").css('visibility', 'hidden');
        } else if (brStr > 3) {
            $("#sledeci").css('visibility', 'visible');
            $("#poslednja").css('visibility', 'visible');
            $("#treciBr").css('visibility', 'visible');
        }
    });

    $("#prva").click(function() {
        $("#prviBr").attr('class', 'strong');
        $("#drugiBr").removeClass("strong");
        $("#treciBr").removeClass("strong");
        $("#ROWDIV").empty();
        $("#tabela").empty()
        dobavi(parseInt($("#prviBr").text()) - 1, 6)
        $("#sledeci").css('visibility', 'visible');
        $("#poslednja").css('visibility', 'visible');
        $("#treciBr").css('visibility', 'visible');
    });

    // $("#poslednja").click(function() {
    //     $("#prviBr").attr('class', 'strong');
    //     $("#drugiBr").removeClass("strong");
    //     $("#treciBr").removeClass("strong");
    //     $("#ROWDIV").empty();
    //     $("#tabela").empty()
    //     dobavi(brStr - 1, 6)
    // });

    function search() {
        $("#error").css('visibility', 'hidden')
        if ($("#search").val() == "") {
            $("#error").text("Polje pretrage ne sme biti prazno.").css("visibility", 'visible').css('color', 'red');
            return;
        }

        $.ajax({
            url: "/klinicki-centar/lekar/searchLekaraForPacijent/" + $("#kriterijum").val() + "/"+$("#search").val() + "/" + klinika.id,
            type: "post",
            success: function(data) {
            	console.log(data);
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
                $("#stranice").css('visibility', 'hidden')
                $("#tabela").css('visibility', 'hidden');
                $("#ROWDIV").empty();
                $("#tabela").empty()

                if ($("#idemo").is(":checked") == true) {
                    tabela(window.podaci);

                } else {
                    kartice(window.podaci);
                }
            },
            error: function(jqXHR) {
                alert("Error: " + jqXHR.status + ", " + jqXHR.responseText);
            }

        })
    }
    $("#dugme").click(function() {
        search();
    });

    // $("#search").keyup(function(event) {
    //     if (event.keyCode == 13) {
    //         $("#dugme").click();
    //     }
    // });
	
	
});




