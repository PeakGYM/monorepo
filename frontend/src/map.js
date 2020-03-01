import React from "react";
import { render } from "react-dom";
import { Map, Marker, Popup, TileLayer } from "react-leaflet";

let myPosition = [50.0265321, 19.9489974];

let textStyle = {
  textSize: "24px"
};

export function loadMap(position, gyms, onMapClick, onMarkerClick) {
  let clientIcon = new L.Icon({
    iconUrl:
      "https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-violet.png",
    shadowUrl:
      "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  let formatCoachCount = coachIds => {
    let count = coachIds.length;

    return count === 1 ? "1 coach" : `${count} coaches`;
  };

  let gymMarkers = gyms.map(gym => {
    let pos = [gym.location.lat, gym.location.lng];
    let text = `${gym.name} - ${formatCoachCount(gym.coachIds)}`

    return (
      <Marker
        key={gym.id}
        riseOnHover={true}
        position={pos}
        onClick={_ => onMarkerClick(gym)}
      >
        <Popup>
          <div style={textStyle}>
            {text}
          </div>
        </Popup>
      </Marker>
    );
  });

  let map = (
    <Map
      center={position}
      zoom={16}
      minZoom={0}
      maxZoom={17}
      onClick={onMapClick}
      doubleClickZoom={false}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
      />
      <Marker icon={clientIcon} position={myPosition}>
        <Popup>Your location</Popup>
      </Marker>
      {gymMarkers}
    </Map>
  );

  render(map, document.getElementById("map-container"));
}

export function windowHeight() {
  return window.innerHeight;
}
