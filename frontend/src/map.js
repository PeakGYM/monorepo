import React from 'react'
import { render } from 'react-dom'
import { Map, Marker, Popup, TileLayer } from 'react-leaflet'

const position = [50.0265321, 19.9489974]

export function loadMap(coaches) {

  let clientIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-violet.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });


  let coachMarkers =
      coaches.map(coach => {
        let pos = [coach.location.lat, coach.location.lng];

        return (
          <Marker riseOnHover={true} position={pos}>
            <Popup>{coach.name}</Popup>
          </Marker>
        );
      });


  let map =
    <Map center={position} zoom={16}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
      />
      <Marker icon={clientIcon} position={position}>
        <Popup>Your location</Popup>
      </Marker>
      {coachMarkers}
    </Map>;

  render(map, document.getElementById('map-container'));
};
