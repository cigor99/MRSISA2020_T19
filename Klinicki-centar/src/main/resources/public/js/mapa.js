ymaps.ready(init);
function init(){ 
	console.log(window.adresaKlinike);
	//var adresa = 'Bulevar Oslobođenja 29, Novi Sad';
	var adresa = window.adresaKlinike;
	var adresaStr = adresa.replace(" ", "+");
	var c = [0, 0];
	//var geocode = 'https://geocode-maps.yandex.ru/1.x/?format=json&apikey=817c3e26-e066-4cd5-9960-b3219be96ec9&geocode=adresaStr&lang=en-US';
	var geocode = 'https://nominatim.openstreetmap.org/search?q='+adresaStr+'&format=json&polygon=1&addressdetails=1';

	$.getJSON(geocode, function(data) {
	  /*var pos = data.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos;
	  var l = pos.split(" ");
	  var a = parseFloat(l[0]);
	  var b = parseFloat(l[1]);
	  c = [b, a]
	  console.log(l);
	  
	  console.log(c);
	  console.log([45.250371, 19.7507498]);*/
	  
	  latlng = [data[0].lat, data[0].lon];
	  console.log(latlng);

	  // let's stringify it
	  //var latlngAsString = latlng.join(',');
	  //console.log(latlngAsString);

	  // the full results JSON
	  //console.log(data);
	  
	  
	  var myMap = new ymaps.Map("map", {
	        // The map center coordinates.
	        // Default order: “latitude, longitude”.
	        // To not manually determine the map center coordinates,
	        // use the Coordinate detection tool.
	        //center: [41.008122, 28.975582],
	        center: latlng,
	        //center: c,
	        // Zoom level. Acceptable values:
	        // from 0 (the entire world) to 19.
	        zoom: 17
	    }); 
	  
	  myPlacemark = new ymaps.Placemark(myMap.getCenter(), {
	        hintContent: window.nazivKlinike,
	    }, {
	        
	        iconLayout: 'default#image',
	        // Custom image for the placemark icon.
	        //iconImageHref: 'images/myIcon.gif',
	        // The size of the placemark.
	        iconImageSize: [30, 42],
	        
	        iconImageOffset: [-5, -38]
	    }),
	    
	    myMap.geoObjects.add(myPlacemark);
	  
	});
	
	
    
}