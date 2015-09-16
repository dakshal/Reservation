$(document).ready(function () {

    var map;
    var iterator = 0;
    var markers = [];
    var markerLatLng = [
        new google.maps.LatLng(52.511467, 13.467179),
        new google.maps.LatLng(52.519061, 13.422975),
        new google.maps.LatLng(52.497622, 13.396110),
        new google.maps.LatLng(52.513683, 13.354393)
    ];
    var contentString = [
        '<div class="infobox-inner"><a href="property-single.html"><img src="assets/img/icon-1.png" alt=""/><span>GrandEstate Villa</span></a></div>',
        '<div class="infobox-inner"><a href="property-single.html"><img src="assets/img/icon-2.png" alt=""/><span>GrandEstate Flat</span></div>',
        '<div class="infobox-inner"><a href="property-single.html"><img src="assets/img/icon-3.png" alt=""/><span>GEstate Commercial</span></div>',
        '<div class="infobox-inner"><a href="property-single.html"><img src="assets/img/icon-4.png" alt=""/><span>GEstate Appartment</span></a></div>'
    ];

    function initialize() {
        map = new google.maps.Map(document.getElementById('map-canvas'), {
            scrollwheel: false,
            zoom: 12,
            center: new google.maps.LatLng(52.520816, 13.410186) //Berlin
        });
        var infoWindow = new google.maps.InfoWindow({map: map});
   if (navigator.geolocation) {
   navigator.geolocation.getCurrentPosition(function(position) {
     var pos = {
       lat: position.coords.latitude,
       lng: position.coords.longitude
     };

     infoWindow.setPosition(pos);
     infoWindow.setContent('Location found.');
     map.setCenter(pos);
   }, function() {
     handleLocationError(true, infoWindow, map.getCenter());
   });
 } else {
   // Browser doesn't support Geolocation
   handleLocationError(false, infoWindow, map.getCenter());
 }
        setTimeout(function () {
            drop();
        }, 1000);

    }

    // animate markers
    function drop() {
        for (var i = 0; i < markerLatLng.length; i++) {
            setTimeout(function () {
                addMarker();
            }, i * 400);
        }
    }
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
     infoWindow.setPosition(pos);
     infoWindow.setContent(browserHasGeolocation ?
                           'Error: The Geolocation service failed.' :
                           'Error: Your browser doesn\'t support geolocation.');
   }
    function addMarker() {
        var marker = new google.maps.Marker({
            position: markerLatLng[iterator],
            map: map,
            icon: 'assets/img/marker.png',
            draggable: false
            //,animation: google.maps.Animation.DROP
        });
        markers.push(marker);

        var infoBubble = new InfoBubble({
            map: map,
            content: contentString[iterator],
            position: markerLatLng[iterator],
            disableAutoPan: true,
            hideCloseButton: true,
            shadowStyle: 0,
            padding: 0,
            borderRadius: 3,
            borderWidth: 1,
            borderColor: '#3f89d8',
            backgroundColor: '#ffffff',
            backgroundClassName: 'infobox-bg',
            minHeight: 35,
            maxHeight: 230,
            minWidth: 200,
            maxWidth: 300,
            arrowSize: 5,
            arrowPosition: 50,
            arrowStyle: 0
        });

        setTimeout(function () {
            infoBubble.open(map, marker);
        }, 200);

        google.maps.event.addListener(marker, 'click', function () {
            if (!infoBubble.isOpen()) {
                infoBubble.open(map, marker);
            }
            else {
                infoBubble.close(map, marker);
            }
        });

        iterator++;
    }

    google.maps.event.addDomListener(window, 'load', initialize);

});
