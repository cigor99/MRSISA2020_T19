function ucitajLokaciju(){
	var address = 'Ive Andrića 3, Novi Sad';
	//var address = "144 Welling High Street, Welling, DA16 1TN";
	var newStr = address.replace(" ", "+");
	var geocode = 'https://nominatim.openstreetmap.org/search?q='+newStr+'&format=json&polygon=1&addressdetails=1';
	//var location = 'London';
	//var geocode = 'http://open.mapquestapi.com/search?format=json&q=' + location;
	window.latlng = [];
	$.getJSON(geocode, function(data) {
	  // get lat + lon from first match
	  window.latlng = [data[0].lat, data[0].lon]
	  console.log(window.latlng);
	  // let's stringify it
	  var latlngAsString = latlng.join(',');
	  console.log(latlngAsString);
	  // the full results JSON
	  console.log(data);
	  prikazi();
	});
}

function prikazi(){
	var layer = new ol.layer.Tile({
        source: new ol.source.OSM(),
        noWrap: true,
        wrapX: false
      });
      /*var london = ol.proj.transform(
        [-0.12755, 51.507222],
        'EPSG:4326',
        'EPSG:3857'
      );*/
      console.log(window.latlng);
      var delhi = ol.proj.transform(
        //[77.209, 28.6139],
        [45.250371, 19.7507498],
        'EPSG:4326',
        'EPSG:3857'
      );
      var view = new ol.View({
        center: [45.250371, 19.7507498],
        zoom: 12
      });
      var map = new ol.Map({
        renderer: 'canvas',
        target: 'map',
        layers: [layer],
        view: view
      });

      function moveTo(location) {
      	map.getView().setCenter(location);
      	layer.setVisible(true);
      }

}