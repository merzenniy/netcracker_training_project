function createLocations() {
    let locations = [];
    let coord = [];
    const markers = document.querySelectorAll(".coord");

    for (let i = 0; i < markers.length; i++) {
        coord = (markers[i].innerHTML.split(", "));
        locations.push({
            lat: parseFloat(coord[0]),
            lng: parseFloat(coord[1]),
        });
    }

    return locations;
}

async function createAddresses(geocoder, locations) {
    let addresses = [];
    for (let i = 0; i < locations.length; i++) {
        addresses.push(await getAddress(geocoder, locations[i]));
    }

    return addresses;
}

async function getAddress(geocoder, location) {
    let result;

    result = await new Promise((resolve, reject) => {
        geocoder.geocode({location: location}, (results, status) => {
            if (status === 'OK') {
                resolve(results[0].formatted_address);
            } else {
                reject(status);
            }
        });
    });

    return result;
}

function displayAddresses(addresses) {
    const addressDiv = document.querySelectorAll(".coord");

    for (let i = 0; i < addressDiv.length; i++) {
        addressDiv[i].innerHTML = addresses[i];
        addressDiv[i].style.opacity = "1";
    }
}

function calcRoute(start, end, ds, dr, map) {
    const colors = ['blue', 'red', 'green'];

    let request = {
        origin: start,
        destination: end,
        travelMode: 'DRIVING',
        provideRouteAlternatives: true
    };

    for (let i = 0; i < dr.length; i++) {
        clearRoute(dr[i]);
        label.innerHTML="";
    }

    ds.route(request, function (result, status) {
        if (status == 'OK') {
            result.routes.sort((a, b) => {
                a = a.legs[0].distance.value;
                b = b.legs[0].distance.value;
                return (a < b) ? -1 : (a > b) ? 1 : 0;
            });

            for (let i = 0; i < result.routes.length; i++) {
                dr[i] = new google.maps.DirectionsRenderer({
                    polylineOptions: {
                        strokeColor: colors[i],
                        //strokeOpacity: 0.5,
                    },
                    suppressMarkers: true,
                });
                dr[i].setDirections(result);
                dr[i].setRouteIndex(i);
                dr[i].setMap(map);

            }
        }
    });
}

function clearRoute(route) {
    route.setMap(null);
}

function leftOneRoute(i, dr, map) {


    for (let j = 0; j < dr.length; j++) {
        if (i == j + 1) {
            dr[j].setMap(map);
            label.innerHTML = dr[j].getDirections().routes[j].legs[0].distance.text;
        } else {
            clearRoute(dr[j]);
        }
    }
}