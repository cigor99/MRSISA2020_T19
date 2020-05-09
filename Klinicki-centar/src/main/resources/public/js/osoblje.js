function ucitajOsoblje(){
	
	$.ajax({
        type: "get",
        url: "/klinicki-centar/lekar/page",
        success: function (data) {
        	kartice(data, "lekar");
        }
    });
	$.ajax({
        type: "get",
        url: "/klinicki-centar/medicinskaSestra/page",
        success: function (data) {
        	kartice(data, "sestra");
        }
    });
}

function kartice(data, x){
	
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
        del.attr("onclick", "ukloniLekara('${lekar.id}')");
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