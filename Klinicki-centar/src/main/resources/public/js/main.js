function ucitajZaglavlje(){
	 $.ajax({
	        url: "header.html",
	        method: "GET",	    
	        complete: function(data){
	            $("#navbardiv").append(data.responseText);
	        }
	 });	
}