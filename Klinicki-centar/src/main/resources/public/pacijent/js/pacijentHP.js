var trenutni = null;
var klinika = null;

$(document).ready(function () {
	$.ajax({
		type: "get",
        url: "/klinicki-centar/pacijent/getOnePacijent/1",
        success: function (data) {
        	trenutni = data;
        	var naslov = $("#naslov");
        	naslov.empty();
    		naslov.append('<h1>'+trenutni.ime+" " + trenutni.prezime+'</h1>');
    		var navbarList = $("#navbar-list");
    		console.log(trenutni.id)
    		navbarList.append("<li><a href='profil.html?id="+trenutni.id+"'>Profil</a></li>")
        	prikaziZK();
        }
	});
	
	
	
	
	$('a[href="#odabirKlinike"]').click(function(e){
		var naslov = $("#naslov");
    	naslov.empty();
		naslov.append('<h1>'+trenutni.ime+" " + trenutni.prezime+'</h1>');
		naslov.append('<h1>Klinike</h1>');
		prikaziKlinike();
	});
	
	$('a[href="#home"]').click(function(e){
		var naslov = $("#naslov");
    	naslov.empty();
		naslov.append('<h1>'+trenutni.ime+" " + trenutni.prezime+'</h1>');
		prikaziZK();
	});
	
	
	   

});

function pretraga(){
	var trazi = $('#trazi').val();
	console.log(trazi);
	if(trazi==""){
		return;
	}
	var holder = $("#table-holder");
	holder.empty();
	var table = $('<table id="klinike" name="klinike" class="table"></table>')
	var thead = $("<thead></thead>");
	var trHead= $("<tr></tr>");
	var idTH = $('<th id="ID">ID</th>');
    var nazivTH = $('<th id="naziv">Naziv</th>');
    var adresaTH = $('<th id="adresa">Adresa</th>');
    var opisTH = $('<th id="opis">Opis</th>');
    var izaberiTH = $('<th>Izaberi</th>');
	trHead.append(idTH);
	trHead.append(nazivTH);
	trHead.append(adresaTH);
	trHead.append(opisTH);
	trHead.append(izaberiTH);
	thead.append(trHead);
	table.append(thead);
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/klinicki-centar/klinika/search",
        dataType: 'json',
        data: trazi,
        success : function (data) {
            for (var klinika of data) {               
            	let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

                let idTD = $("<td>" + klinika.id + "</td>")
                let nazivTd = $("<td>" + klinika.naziv + "</td>")
                let adresaTD = $("<td>" + klinika.adresa + "</td>")
                let opisTD = $("<td>" + klinika.opis + "</td>")
                //let izaberiTD = $("<td>" + "<a href=\"#izabrana?id=" + klinika.id + ">Izaberi</a></td>")
                let izaberiTD = $("<td><a href='#'>Izaberi</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(adresaTD);
                tr.append(opisTD);
                tr.append(izaberiTD);
                table.append(tr);
                holder.append(table);
            }

		},
        
    });
} 

function prikaziZK(){
	var holder = $("#table-holder");
	holder.empty();
	$.ajax({
        type: "get",
        url: "/klinicki-centar/karton/get/" + trenutni.jmbg,
        success: function(data){
        	let table = $("<table class='table'></table>")


            let tr2 = $("<tr></tr>")
            let tezinaLabelTD = $("<td></td>")
            let tezinaTD = $("<td></td>")
            tezinaLabelTD.append("Tezina")
            tr2.append(tezinaLabelTD);
            tr2.append(tezinaTD)
            tezinaTD.append(data.tezina)

            let tr3 = $("<tr></tr>")
            let visinaLabelTD = $("<td></td>")
            let visinaTD = $("<td></td>")
            visinaLabelTD.append("Visina")
            tr3.append(visinaLabelTD)
            tr3.append(visinaTD)
            visinaTD.append(data.visina)

            let tr4 = $("<tr></tr>")
            let krvLabelTD = $("<td></td>")
            let krvTD = $("<td></td>")
            
            krvLabelTD.append("Krvna grupa")
            tr4.append(krvLabelTD)
            tr4.append(krvTD)
            krvTD.append(data.krvnaGrupa);

            table.append(tr2)
            table.append(tr3)
            table.append(tr4)
            holder.append(table);
        }
	});
	
}

function prikaziKlinike(){
	
	var holder = $("#table-holder");
	holder.empty();
	var table = $('<table id="klinike" name="klinike" class="table"></table>')
	var thead = $("<thead></thead>");
	var trHead= $("<tr></tr>");
	var idTH = $('<th id="ID">ID</th>');
    var nazivTH = $('<th id="naziv">Naziv</th>');
    var adresaTH = $('<th id="adresa">Adresa</th>');
    var opisTH = $('<th id="opis">Opis</th>');
    var izaberiTH = $('<th>Izaberi</th>');
	trHead.append(idTH);
	trHead.append(nazivTH);
	trHead.append(adresaTH);
	trHead.append(opisTH);
	trHead.append(izaberiTH);
	thead.append(trHead);
	table.append(thead);
	
	
	$.ajax({
        type: "get",
        url: "/klinicki-centar/klinika/page",
        success: function (data) {

            // alert(JSON.stringify(data));
            for (let klinika of data) {

                
                let tr = $("<tr id=\"tr" + klinika.id + "\"></tr>");

                let idTD = $("<td>" + klinika.id + "</td>")
                let nazivTd = $("<td>" + klinika.naziv + "</td>")
                let adresaTD = $("<td>" + klinika.adresa + "</td>")
                let opisTD = $("<td>" + klinika.opis + "</td>")
                //let izaberiTD = $("<td>" + "<a href=\"#izabrana?id=" + klinika.id + ">Izaberi</a></td>")
                let izaberiTD = $("<td><a href='#'>Izaberi</a></td>")
                tr.append(idTD);
                tr.append(nazivTd);
                tr.append(adresaTD);
                tr.append(opisTD);
                tr.append(izaberiTD);
                table.append(tr);
                holder.append(table);
                
               
            }

        }
    });
 
};
