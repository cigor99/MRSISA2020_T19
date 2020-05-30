$(document).ready(function () {
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
		        	prikaziZK();
		        },
		        async: false,
		 });
	}
});
function prikaziZK(){
	var holder = $("#table-holder");
	holder.empty();
	$.ajax({
        type: "get",
        url: "/klinicki-centar/karton/get/" + window.ulogovani.jmbg,
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
            krvTD.append(grupaIspis(data.krvnaGrupa));

            table.append(tr2)
            table.append(tr3)
            table.append(tr4)
            holder.append(table);
        }
	});
	
}

function grupaIspis(grupa){
	switch(grupa){
		case "NULTAPOZITIVNA": return "0+";
		case "NULTANEGATIVNA": return "0-";
		case "APOZITIVNA": return "A+";
		case "ANEGATIVNA": return "A-";
		case "BPOZITIVNA": return "B+";
		case "BNEGATIVNA": return "B-";
		case "ABPOZITIVNA": return "AB+";
		case "ABNEGATIVNA": return "AB-";
		default: return "GRESKA";
	}
}