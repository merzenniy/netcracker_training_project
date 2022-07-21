let map;
let geocoder;
let directionsService;
let directionsRenderers = [];
let personMarker;
let distanceService;
let label = document.getElementById("distance-label");

async function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 46.4775, lng: 30.7326},
        zoom: 14,
        disableDefaultUI: true,
    });
    geocoder = new google.maps.Geocoder();
    directionsService = new google.maps.DirectionsService();
    distanceService = new google.maps.DistanceMatrixService();

    const control = document.getElementById("floating-panel");

    map.controls[google.maps.ControlPosition.TOP_CENTER].push(control);

    window.addEventListener("load", () => {
        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude,
                    };

                    personMarker = new google.maps.Marker({
                        position: pos,
                        map,
                        icon: {
                            url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                        },
                    });

                    map.setCenter(pos);
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                }
            );
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });

    const locations = createLocations();
    const addresses = await createAddresses(geocoder, locations);

    const infoWindow = new google.maps.InfoWindow({
        content: "",
        disableAutoPan: true,
    });
    const markers = locations.map((position, i) => {
        const title = addresses[i % addresses.length];
        const marker = new google.maps.Marker({
            position,
            title,
            map,
        });

        marker.addListener("click", () => {
            infoWindow.setContent(title);
            infoWindow.open(map, marker);

            let start = personMarker.position;
            let end = marker.position;
            calcRoute(start, end, directionsService, directionsRenderers, map);

            control.style.setProperty("display", "block", "important");
        });

        return marker;
    });

    displayAddresses(addresses);
}